package prompto.codeserver;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.config.CodeServerConfiguration;
import prompto.config.ICodeServerConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IStoreConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.memstore.MemStore;
import prompto.memstore.MemStoreFactory;
import prompto.runtime.Mode;
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
		main(args, null);
	}
	
	public static void main(String[] args, Mode runtimeMode) throws Throwable {
		ICodeServerConfiguration config = loadConfiguration(args);
		config = config
				.withServerAboutToStartMethod("serverAboutToStart")
				.withHttpConfiguration(config.getHttpConfiguration().withSendsXAuthorization(true))
				.withApplicationName("dev-center")
				.withApplicationVersion(PromptoVersion.parse("1.0.0"))
				.withResourceURLs(CodeServer.getResourceURLs());
		if(runtimeMode!=null)
			config = config.withRuntimeMode(runtimeMode);
		AppServer.main(config, CodeServer::initDataServletStores); 
	}
	
	public static ICodeServerConfiguration loadConfiguration(String[] args) throws Exception {
		Map<String, String> argsMap = CmdLineParser.read(args);
		IConfigurationReader reader = Standalone.readerFromArgs(argsMap);
		ICodeServerConfiguration config = new CodeServerConfiguration(reader, argsMap);
		return config.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
	}

	
	private static void initDataServletStores(ICodeServerConfiguration config) {
		try {
			Map<String, IStore> stores = new HashMap<>();
			stores.put("TOOLS", newStore(config, "TOOLS"));
			stores.put("APPS", newStore(config, "APPS"));
			stores.put("DATA", newStore(config, "DATA"));
			stores.put("LOGIN", newStore(config, "LOGIN"));
			DataServlet.setStores(stores);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static IStore newStore(ICodeServerConfiguration cfg, String dbName) throws Throwable {
		if(cfg==null)
			return new MemStore();
		else {
			IStoreConfiguration config = cfg.getDataStoreConfiguration();
			IStoreFactory factory = IStoreFactory.newStoreFactory(config.getFactory());
			if(factory instanceof MemStoreFactory)
				return new MemStore();
			else 
				return factory.newStore(config.withDbName(dbName));
		}
	}

	private static URL[] getResourceURLs() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, ModuleImporter.class);
		return urls.toArray(new URL[urls.size()]);
	}

	public static void createThesaurusAndImportSamples() {
		try {
			IStore dataStore = IDataStore.getInstance();
			ModuleImporter importer = new ModuleImporter(Thread.currentThread().getContextClassLoader().getResource("thesaurus/"));
			importer.importModule(ICodeStore.getInstance());
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
