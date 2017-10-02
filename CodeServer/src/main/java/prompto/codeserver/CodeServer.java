package prompto.codeserver;

import java.net.URL;
import java.util.Collection;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.config.IServerConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.server.AppServer;
import prompto.server.DataServlet;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.utils.Logger;
import prompto.utils.ResourceUtils;

public class CodeServer {

	static Logger logger = new Logger();
	
	public static void main(String[] args) throws Throwable {
		main(args, false);
	}
	
	public static void main(String[] args, boolean testMode) throws Throwable {
		IServerConfiguration config = AppServer.loadConfiguration(args);
		config = new IServerConfiguration.Sourced(config) {
			
			@Override public String getApplicationName() { return "dev-center"; }
			@Override public PromptoVersion getApplicationVersion() { return PromptoVersion.parse("1.0.0"); }
			@Override public URL[] getResourceURLs() { return CodeServer.getResourceURLs(); }
			@Override public String getServerAboutToStartMethod() { return "serverAboutToStart";  }
			@Override public boolean isTestMode() { return testMode; }
		};
		AppServer.main(config, CodeServer::redirectDataServlet); 
	}
	
	private static void redirectDataServlet(IServerConfiguration config) {
		try {
			IStoreConfiguration code = config.getDataStoreConfiguration(); // originally points to TOOLS or CODE
			IStoreConfiguration data = code.withDbName("DATA");
			logger.info(()->"Redirecting data servlet to " + data.getDbName() + ".");
			IStoreFactory factory = IStoreFactory.newStoreFactory(data.getFactory());
			DataServlet.store = factory.newStore(data);
			// TODO ICodeStore codeStore = new QueryableCodeStore(IDataStore.getInstance(), null, "someApp", PromptoVersion.LATEST, new URL[]{}, new URL[]{});
			// TODO Standalone.synchronizeSchema(codeStore, DataServlet.store);
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
			Collection<URL> samples = ResourceUtils.listResourcesAt("samples/", null);
			for(URL sample : samples) {
				importer = new ModuleImporter(sample);
				importer.importModule(codeStore);
			}
			dataStore.flush();
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}


}
