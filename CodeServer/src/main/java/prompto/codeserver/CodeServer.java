package prompto.codeserver;

import java.net.URL;
import java.util.Collection;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.code.Version;
import prompto.config.IServerConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.libraries.Libraries;
import prompto.server.AppServer;
import prompto.server.DataServlet;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.utils.ResourceUtils;

public class CodeServer {

	public static void main(String[] args) throws Throwable {
		IServerConfiguration config = AppServer.loadConfiguration(args);
		config = new IServerConfiguration.Sourced(config) {
			
			@Override public String getApplicationName() { return "dev-center"; }
			@Override public Version getApplicationVersion() { return Version.parse("1.0.0"); }
			@Override public URL[] getResourceURLs() { return CodeServer.getResourceURLs(); }
			@Override public String getServerAboutToStartMethod() { return "serverAboutToStart";  }
		};
		AppServer.main(args, CodeServer::redirectDataServlet); 
	}
	
	private static void redirectDataServlet(IServerConfiguration config) {
		try {
			IStoreConfiguration code = config.getDataStoreConfiguration(); // points to CODE
			IStoreFactory factory = IStoreFactory.newStoreFactory(code.getFactory());
			DataServlet.store = factory.newStore(code.withDbName("DATA"));
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static URL[] getResourceURLs() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, ModuleImporter.class);
		return urls.toArray(new URL[urls.size()]);
	}

	public static void createThesaurusAndImportSamples() throws Exception {
		IStore dataStore = IDataStore.getInstance();
		ICodeStore codeStore = new QueryableCodeStore(dataStore, ()->Libraries.getPromptoLibraries(Libraries.class), "dev-center", Version.parse("1.0.0"), null);
		ModuleImporter importer = new ModuleImporter(Thread.currentThread().getContextClassLoader().getResource("thesaurus/"));
		importer.importModule(codeStore);
		Collection<URL> samples = ResourceUtils.listResourcesAt("samples/", null);
		for(URL sample : samples) {
			importer = new ModuleImporter(sample);
			importer.importModule(codeStore);
		}
		dataStore.flush();
	}


}
