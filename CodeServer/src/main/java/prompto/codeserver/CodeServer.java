package prompto.codeserver;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.config.CodeServerConfiguration;
import prompto.config.IAuthenticationConfiguration;
import prompto.config.IAuthenticationSourceConfiguration;
import prompto.config.ICodeServerConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IHttpConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.IStoredAuthenticationSourceConfiguration;
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
	static ICodeServerConfiguration config;
	
	public static void main(String[] args) throws Throwable {
		main(args, null);
	}
	
	public static void main(String[] args, Mode runtimeMode) throws Throwable {
		config = loadConfiguration(args);
		config = config.withServerAboutToStartMethod("serverAboutToStart")
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
			IStore store = fetchLoginStore(config);
			if(store!=null)
				stores.put("LOGIN", store);
			store = IDataStore.getInstance();
			if(store!=null)
				stores.put("APPS", store);
			store = newStore(config.getTargetStoreConfiguration());
			if(store!=null)
				stores.put("DATA", store);
			DataServlet.setStores(stores);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static IStore fetchLoginStore(ICodeServerConfiguration config) throws Throwable {
		IHttpConfiguration http = config.getHttpConfiguration();
		if(http==null)
			return null;
		IAuthenticationConfiguration auth = http.getAuthenticationConfiguration();
		if(auth==null)
			return null;
		IAuthenticationSourceConfiguration source = auth.getAuthenticationSourceConfiguration();
		if(source instanceof IStoredAuthenticationSourceConfiguration)
			return newStore(((IStoredAuthenticationSourceConfiguration)source).getStoreConfiguration());
		else
			return null;
	}

	private static IStore newStore(IStoreConfiguration config) throws Throwable {
		if(config==null)
			return null;
		else {
			IStoreFactory factory = IStoreFactory.newStoreFactory(config.getFactory());
			if(factory instanceof MemStoreFactory)
				return new MemStore();
			else 
				return factory.newStore(config);
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
