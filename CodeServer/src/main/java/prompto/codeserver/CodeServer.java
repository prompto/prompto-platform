package prompto.codeserver;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.config.CodeServerConfiguration;
import prompto.config.ICodeServerConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IStoreConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.memstore.MemStoreFactory;
import prompto.runtime.Standalone;
import prompto.server.AppServer;
import prompto.server.DataServlet;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.utils.CmdLineParser;
import prompto.utils.Logger;

public class CodeServer {

	static Logger logger = new Logger();
	
	public static void main(String[] args) throws Throwable {
		main(args, false);
	}
	
	public static void main(String[] args, boolean testMode) throws Throwable {
		ICodeServerConfiguration config = loadConfiguration(args);
		config = config
				.withServerAboutToStartMethod("serverAboutToStart")
				.withHttpConfiguration(config.getHttpConfiguration().withSendsXAuthorization(true))
				.withApplicationName("dev-center")
				.withApplicationVersion(PromptoVersion.parse("1.0.0"))
				.withResourceURLs(CodeServer.getResourceURLs())
				.withTestMode(testMode);
		AppServer.main(config, CodeServer::redirectDataServlet); 
	}
	
	public static ICodeServerConfiguration loadConfiguration(String[] args) throws Exception {
		Map<String, String> argsMap = CmdLineParser.read(args);
		IConfigurationReader reader = Standalone.readerFromArgs(argsMap);
		ICodeServerConfiguration config = new CodeServerConfiguration(reader, argsMap);
		return config.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
	}

	
	private static void redirectDataServlet(ICodeServerConfiguration codeServerConfig) {
		IStoreConfiguration targetStoreConfig = codeServerConfig.getTargetDataStoreConfiguration(); 
		if(targetStoreConfig==null)
			redirectDataServletToDataStore(codeServerConfig);
		else
			redirectDataServletToTargetStore(codeServerConfig);
	}

	private static void redirectDataServletToDataStore(ICodeServerConfiguration codeServerConfig) {
		IStoreConfiguration targetStoreConfig = codeServerConfig.getDataStoreConfiguration(); 
		logger.warn(()->"Could not locate target data store configuration, reverting to " + targetStoreConfig.getDbName() + " store.");
		redirectDataServlet(targetStoreConfig, ICodeStore.getInstance());
	}

	private static void redirectDataServletToTargetStore(ICodeServerConfiguration codeServerConfig) {
		IStoreConfiguration targetStoreConfig = codeServerConfig.getTargetDataStoreConfiguration(); 
		logger.info(()->"Redirecting data servlet to " + targetStoreConfig.getDbName() + ".");
		ICodeStore codeStore = new QueryableCodeStore(IDataStore.getInstance(), codeServerConfig.getRuntimeLibs(), "Thesaurus", PromptoVersion.LATEST, null, (URL[])null);
		redirectDataServlet(targetStoreConfig, codeStore);
	}

	private static void redirectDataServlet(IStoreConfiguration targetStoreConfig, ICodeStore codeStore) {
		try {
			IStoreFactory factory = IStoreFactory.newStoreFactory(targetStoreConfig.getFactory());
			if(factory instanceof MemStoreFactory)
				DataServlet.useDataStore(IDataStore.getInstance());
			else {
				IStore store = factory.newStore(targetStoreConfig);
				store.setAttributeInfoSupplier(codeStore::fetchAttributeInfo);
				DataServlet.useDataStore(store);
			}
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static URL[] getResourceURLs() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, ModuleImporter.class);
		return urls.toArray(new URL[urls.size()]);
	}

	public static void createThesaurusAndImportSamples() {
		try {
			IStore dataStore = IDataStore.getInstance();
			ICodeStore codeStore = new QueryableCodeStore(dataStore, ()->Libraries.getPromptoLibraries(Libraries.class), "dev-center", PromptoVersion.parse("1.0.0"), null);
			ModuleImporter importer = new ModuleImporter(Thread.currentThread().getContextClassLoader().getResource("thesaurus/"));
			importer.importModule(codeStore);
			/* TODO split in 2 methods, making samples optional
			Collection<URL> samples = ResourceUtils.listResourcesAt("samples/", null);
			for(URL sample : samples) {
				importer = new ModuleImporter(sample);
				importer.importModule(codeStore);
			}
			*/
			dataStore.flush();
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}


}
