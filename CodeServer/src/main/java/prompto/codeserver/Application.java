package prompto.codeserver;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.UpdatableCodeStore;
import prompto.libraries.Libraries;
import prompto.server.AppServer;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStoreFactory.Type;
import prompto.utils.ResourceUtils;

public class Application {

	public static void main(String[] args) throws Throwable {
		Type codeStoreType = Type.ROOT;
		Type dataStoreType = Type.CODE;
		// parse parameters
		for(int i=0; i<args.length; i++) {
			if(!args[i].startsWith("-"))
				continue;
			if(args[i].equalsIgnoreCase("-codeStoreType")) {
				codeStoreType = Type.valueOf(args[++i]);
			} else if(args[i].equalsIgnoreCase("-dataStoreType")) {
				dataStoreType = Type.valueOf(args[++i]);
			} 
		}
		List<String> argsList = new ArrayList<>(Arrays.asList(args));
		argsList.add("-resources");
		argsList.add(getResourcesList());
		argsList.add("-application");
		argsList.add("dev-center");
		argsList.add("-version");
		argsList.add("1.0.0");
		argsList.add("-codeStoreType");
		argsList.add(codeStoreType.name());
		argsList.add("-dataStoreType");
		argsList.add(dataStoreType.name());
		argsList.add("-serverAboutToStart");
		argsList.add("createThesaurusAndImportSamples");
		AppServer.main(argsList.toArray(new String[argsList.size()])/*, Application::aboutToStart*/); // TODO move to serverAboutToStartMethod
	}
	
	private static String getResourcesList() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, Application.class);
		return urls.stream().map(URL::toExternalForm).collect(Collectors.joining(","));
	}

	public static void createThesaurusAndImportSamples() throws Exception {
		IStore dataStore = IDataStore.getInstance();
		ICodeStore codeStore = new UpdatableCodeStore(dataStore, ()->Libraries.getPromptoLibraries(Libraries.class), "dev-center", "1.0.0", null);
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
