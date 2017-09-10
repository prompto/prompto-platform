package prompto.codeserver;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
		List<String> argsList = new ArrayList<>(Arrays.asList(args));
		argsList.add("-resourceURLs");
		argsList.add(getResourcesList());
		argsList.add("-applicationName");
		argsList.add("dev-center");
		argsList.add("-applicationVersion");
		argsList.add("1.0.0");
		argsList.add("-codeStore-dbName");
		argsList.add("ROOT");
		argsList.add("-dataStore-dbName");
		argsList.add("CODE");
		argsList.add("-serverAboutToStart");
		argsList.add("serverAboutToStart");
		args = argsList.toArray(new String[argsList.size()]);
		AppServer.main(args, CodeServer::redirectDataServlet); 
	}
	
	private static void redirectDataServlet(IServerConfiguration config) {
		try {
			IStoreConfiguration code = config.getDataStoreConfiguration(); // points to CODE
			IStoreFactory factory = prompto.runtime.Application.newStoreFactory(code.getFactory());
			DataServlet.store = factory.newStore(code.withDbName("DATA"));
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String getResourcesList() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, ModuleImporter.class);
		return urls.stream().map(URL::toExternalForm).collect(Collectors.joining(","));
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
