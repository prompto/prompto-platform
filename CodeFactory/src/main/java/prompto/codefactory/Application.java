package prompto.codefactory;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import prompto.code.BaseCodeStore;
import prompto.code.Dependency;
import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.Library;
import prompto.code.Module;
import prompto.code.QueryableCodeStore;
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
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.server.AppServer;
import prompto.server.DataServlet;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.store.memory.MemStore;
import prompto.store.memory.MemStoreFactory;
import prompto.utils.CmdLineParser;
import prompto.utils.Logger;
import prompto.utils.ResourceUtils;

public class Application {

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
					.withResourceURLs(Application.getResourceURLs());
		if(runtimeMode!=null)
			config = config.withRuntimeMode(runtimeMode);
		AppServer.main(config, Application::initDataServletStores); 
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
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, Application.class);
		return urls.toArray(new URL[urls.size()]);
	}

	public static void createLibraries() {
		try {
			ICodeStore codeStore = codeStoreUsingDataStore();
			createResourceLibraries(codeStore, "thesaurus/", "react-bootstrap-3/");
			if(isToolsDataStore())
				createToolsLibraries(codeStore);
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	
	private static void createResourceLibraries(ICodeStore codeStore, String ... resources) throws Exception {
		for(String resource : resources) {
			URL url = Thread.currentThread().getContextClassLoader().getResource(resource);
			doImportModule(codeStore, url);
		}
	}

	private static ICodeStore codeStoreUsingDataStore() {
		ICodeStore runtime = ImmutableCodeStore.bootstrapRuntime(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
		return new QueryableCodeStore(IDataStore.getInstance(), runtime, null, null, null);
	}

	private static void doImportModule(ICodeStore codeStore, URL url) throws Exception {
		ModuleImporter importer = new ModuleImporter(url);
		importer.importModule(codeStore);
	}

	public static void importSamples() {
		try {
			ICodeStore codeStore = codeStoreUsingDataStore();
			Collection<URL> samples = ResourceUtils.listResourcesAt("samples/", null);
			for(URL sample : samples)
				doImportModule(codeStore, sample);
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	private static void createToolsLibraries(ICodeStore codeStore) throws Exception {
		Module codeStoreLibrary = new Library();
		codeStoreLibrary.setName("CodeStore");
		codeStoreLibrary.setVersion(PromptoVersion.parse("1.0.0.0"));
		codeStoreLibrary.setDescription("Code store model");
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/CodeStore.pec");
		ModuleImporter importer = new ModuleImporter(codeStoreLibrary, null, url);
		importer.importModule(codeStore);
		Module appStoreLibrary = new Library();
		appStoreLibrary.setName("AppStore");
		appStoreLibrary.setVersion(PromptoVersion.parse("1.0.0.0"));
		appStoreLibrary.setDescription("App store model");
		Dependency dependency = new Dependency();
		dependency.setName(codeStoreLibrary.getName());
		dependency.setVersion(codeStoreLibrary.getVersion());
		appStoreLibrary.setDependencies(Collections.singletonList(dependency));
		url = Thread.currentThread().getContextClassLoader().getResource("libraries/AppStore.pec");
		importer = new ModuleImporter(appStoreLibrary, null, url);
		importer.importModule(codeStore);
	}

	private static boolean isToolsDataStore() {
		return config.getDataStoreConfiguration().getDbName().toLowerCase().contains("tools");
	}


}
