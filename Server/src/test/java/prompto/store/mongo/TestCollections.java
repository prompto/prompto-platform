package prompto.store.mongo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import prompto.code.ICodeStore;
import prompto.config.IStandaloneConfiguration;
import prompto.config.TempDirectories;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Interpreter;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.store.DataStore;
import prompto.store.IStore;
public class TestCollections extends BaseMongoTest {

	@Before
	public void before() throws Throwable {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		IStore dataStore = createStore("APPS");
		ICodeStore codeStore = Standalone.bootstrapCodeStore(dataStore, newStandaloneConfig());
		dataStore = createStore("DATA");
		DataStore.setInstance(dataStore);
		Standalone.main(newStandaloneConfig(), codeStore, dataStore);
	}
	
	private IStandaloneConfiguration newStandaloneConfig() {
		return new IStandaloneConfiguration.Inline()
			.withMainMethod("onStart")
			.withRuntimeLibs(()->{
				List<URL> urls = new ArrayList<>();
				urls.addAll(Libraries.getPromptoLibraries(Libraries.class));
				urls.add(Thread.currentThread().getContextClassLoader().getResource("mongoEmbeddedCollection.poc"));
				return urls;
			}).withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test")
			.withLoadRuntime(true)
			.withRuntimeMode(Mode.UNITTEST);
	}
	
	@Test
	public void testTheProblem() throws Throwable {
		Interpreter.interpretMethod(ApplicationContext.get(), new Identifier("theProblem"), "");
	}
}
