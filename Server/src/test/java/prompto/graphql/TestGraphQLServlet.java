package prompto.graphql;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import graphql.ExecutionResult;
import graphql.GraphQL;
import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.intrinsic.PromptoVersion;
import prompto.parser.Dialect;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Standalone;
import prompto.server.BaseServerTest;
import prompto.store.DataStore;
import prompto.store.memory.MemStore;

@SuppressWarnings("unchecked")
public class TestGraphQLServlet extends BaseServerTest {

	@BeforeClass
	public static void beforeClass() {
		GraphQLServlet.FORCE_ENABLED = true;
	}
	
	@AfterClass
	public static void afterClass() {
		GraphQLServlet.FORCE_ENABLED = false;
	}

	@Before
	public void before() throws Exception {
		ApplicationContext.reset();
		DataStore.setGlobal(new MemStore());
		DataStore.useGlobal();
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
	}
	
	private <T> T linkResourceAndRunQuery(String resourceName, Dialect dialect, String query) throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("graphql-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		tail.setNext(codeResource);
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
		GraphQLServlet.reset();
		GraphQLServlet servlet = GraphQLServlet.instance.get();
		@SuppressWarnings("unused")
		var sdl = servlet.getSDL();
		GraphQL graphQL = servlet.getGraphQL();
		ExecutionResult result = graphQL.execute(query);
		assertNotNull(result);
		assertTrue(result.getErrors().isEmpty());
		return result.getData();
	}
	
	@Test
	public void writesVoid() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "mutation { writesVoid }");
		assertEquals("Hello", s.get("writesVoid"));
	}


	@Test
	public void readsText() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ readsText }");
		assertEquals("Hello", s.get("readsText"));
	}
	
	@Test
	public void writesText() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "mutation { writesText ( text: \"Hello\" ) }");
		assertEquals("Hello", s.get("writesText"));
	}

	@Test
	public void readsInteger() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ readsInteger }");
		assertEquals(123L, s.get("readsInteger"));
	}

	@Test
	public void writesInteger() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "mutation { writesInteger ( value: 1222 ) }");
		assertEquals(1222L, s.get("writesInteger"));
	}

	@Test
	public void readsDecimal() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ readsDecimal }");
		assertEquals(123.45, s.get("readsDecimal"));
	}

	@Test
	public void writesDecimal() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "mutation { writesDecimal ( value: 123.45 ) }");
		assertEquals(123.45, s.get("writesDecimal"));
	}

	@Test
	public void readsNativeEnum() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ readsNativeEnum }");
		assertEquals("NATIVE", s.get("readsNativeEnum"));
	}

	@Test
	public void readsInstance() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ readsInstance { firstName, lastName, birthDate, age } }");
		Map<String, Object> i = (Map<String, Object>) s.get("readsInstance");
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}
	
	@Test
	public void writesInstance() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "mutation { writesInstance (person : { firstName: \"Eric\", lastName: \"Clapton\", birthDate: \"1960-01-01\", age: 62 }) { firstName, lastName, birthDate, age } }");
		Map<String, Object> i = (Map<String, Object>) s.get("writesInstance");
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}
	
	@Test
	public void readsInstances() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ readsInstances { firstName, lastName, birthDate, age } }");
		List<Map<String, Object>> l = (List<Map<String, Object>>) s.get("readsInstances");
		assertEquals(1, l.size());
		Map<String, Object> i = l.get(0);
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}

	@Test
	public void readsAbstractChild() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ readsAbstractChild { __typename ... on Child { name, persons { firstName } } ... on GrandChild { name, persons { firstName } } } }");
		Map<String, Object> i = (Map<String, Object>) s.get("readsAbstractChild");
		assertEquals("Child", i.get("name"));
	}

	@Test
	public void readsAbstractChildren() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ readsAbstractChildren { __typename ... on Parent { name } ... on Child { name, persons { firstName } } ... on GrandChild { name, persons { firstName } } } }");
		List<Map<String, Object>> l = (List<Map<String, Object>>) s.get("readsAbstractChildren");
		assertEquals(3, l.size());
		Map<String, Object> i = l.get(0);
		assertEquals("Child", i.get("name"));
		i = l.get(1);
		assertEquals("GrandChild", i.get("name"));
		i = l.get(2);
		assertEquals("Parent", i.get("name"));
	}

	@Test
	public void readsCursor() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("cursor", Dialect.O, "{ fetchPersons { count, items { firstName, lastName } } }");
		Map<String, Object> i = (Map<String, Object>) s.get("fetchPersons");
		assertEquals(2L, i.get("count"));
		var list = i.get("items");
		assertTrue(list instanceof List);
		i = ((List<Map<String, Object>>)list).get(0);
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
	}
	

}
