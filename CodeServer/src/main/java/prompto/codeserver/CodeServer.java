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
import prompto.libraries.Libraries;
import prompto.memstore.MemStoreFactory;
import prompto.server.AppServer;
import prompto.server.DataServlet;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.store.IStoreFactory.Type;
import prompto.utils.ResourceUtils;

public class CodeServer {

	public static void main(String[] args) throws Throwable {
		List<String> argsList = new ArrayList<>(Arrays.asList(args));
		argsList.add("-resources");
		argsList.add(getResourcesList());
		argsList.add("-application");
		argsList.add("dev-center");
		argsList.add("-version");
		argsList.add("1.0.0");
		argsList.add("-codeStoreType");
		argsList.add(Type.ROOT.name());
		argsList.add("-dataStoreType");
		argsList.add(Type.CODE.name());
		argsList.add("-serverAboutToStart");
		argsList.add("serverAboutToStart");
		AppServer.main(argsList.toArray(new String[argsList.size()])); 
		redirectDataServlet(args);
	}
	
	private static void redirectDataServlet(String[] args) throws Throwable {
		String storeFactory = getDataStoreFactory(args);
		IStoreFactory factory = prompto.runtime.Application.newStoreFactory(storeFactory);
		DataServlet.store = factory.newStore(args, Type.DATA);
	}

	private static String getDataStoreFactory(String[] args) {
		// TODO use specific args, for now simply use same as data store
		for(int i=0; i<args.length; i++) {
			if("-dataStoreFactory".equals(args[i]))
				return args[i+1];
		}
		return MemStoreFactory.class.getName();
	}

	private static String getResourcesList() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, ModuleImporter.class);
		return urls.stream().map(URL::toExternalForm).collect(Collectors.joining(","));
	}

	public static void createThesaurusAndImportSamples() throws Exception {
		IStore dataStore = IDataStore.getInstance();
		ICodeStore codeStore = new QueryableCodeStore(dataStore, ()->Libraries.getPromptoLibraries(Libraries.class), "dev-center", "1.0.0", null);
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
