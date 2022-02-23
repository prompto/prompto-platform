package prompto.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;

import prompto.config.IRuntimeConfiguration;
import prompto.config.TempDirectories;
import prompto.declaration.DeclarationList;
import prompto.declaration.IDeclaration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.parser.Dialect;
import prompto.parser.ECleverParser;
import prompto.runtime.Context;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.store.memory.MemStore;
import prompto.store.mongo.BaseMongoTest;

public class TestCodeStore extends BaseMongoTest {
	
	@Before
	public void before() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		createStore("APPS");
	}
 

	@Test
	public void fetchesDeclarationsWithAnnotationsFromResource() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		Standalone.bootstrapCodeStore(new MemStore(), newRuntimeConfig("prompto/graphQLMethods.pec"));
		Context context = Context.newGlobalsContext();
		Iterable<IDeclaration> decls = context.getRegisteredDeclarationsWithAnnotations("@GraphQLQuery", "@GraphQLMutation");
		Set<String> names = StreamSupport.stream(decls.spliterator(), false).map(IDeclaration::getName).collect(Collectors.toSet());
		Set<String> expected = new HashSet<>(Arrays.asList("someGraphQLQuery", "someGraphQLMutation"));
		assertEquals(expected, names);
		
	}

	@Test
	public void fetchesDeclarationsWithAnnotationsFromMemStore() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		ICodeStore codeStore = Standalone.bootstrapCodeStore(new MemStore(), newRuntimeConfig(null));
		DeclarationList read = parseEResource("prompto/graphQLMethods.pec");
		codeStore.storeDeclarations(read, Dialect.E, "ModuleId");
		Context context = Context.newGlobalsContext();
		Iterable<IDeclaration> decls = context.getRegisteredDeclarationsWithAnnotations("@GraphQLQuery", "@GraphQLMutation");
		Set<String> names = StreamSupport.stream(decls.spliterator(), false).map(IDeclaration::getName).collect(Collectors.toSet());
		Set<String> expected = new HashSet<>(Arrays.asList("someGraphQLQuery", "someGraphQLMutation"));
		assertEquals(expected, names);
	}

	@Test
	public void fetchesDeclarationsWithAnnotationsFromMongoStore() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		ICodeStore codeStore = Standalone.bootstrapCodeStore(store, newRuntimeConfig(null));
		DeclarationList read = parseEResource("prompto/graphQLMethods.pec");
		codeStore.storeDeclarations(read, Dialect.E, "ModuleId");
		Context context = Context.newGlobalsContext();
		Iterable<IDeclaration> decls = context.getRegisteredDeclarationsWithAnnotations("@GraphQLQuery", "@GraphQLMutation");
		Set<String> names = StreamSupport.stream(decls.spliterator(), false).map(IDeclaration::getName).collect(Collectors.toSet());
		Set<String> expected = new HashSet<>(Arrays.asList("someGraphQLQuery", "someGraphQLMutation"));
		assertEquals(expected, names);
		
	}

	public DeclarationList parseEResource(String resourceName) throws Exception {
		try(var input = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
			assertNotNull("resource not found:" + resourceName, input);
			ECleverParser parser = new ECleverParser(input);
			return parser.parse_declaration_list();
		}
	}

	private IRuntimeConfiguration newRuntimeConfig(String testResourcePath) {
		List<URL> runtimeLibs = new ArrayList<>();
		runtimeLibs.addAll(Libraries.getPromptoLibraries(Libraries.class, BaseCodeStore.class));
		if(testResourcePath!=null)
			runtimeLibs.add(Thread.currentThread().getContextClassLoader().getResource(testResourcePath));
		return new IRuntimeConfiguration.Inline()
				.withApplicationName("TestCodeStore")
				.withApplicationVersion(PromptoVersion.parseInt(1))
				.withRuntimeLibs(()->runtimeLibs);
	}
}
