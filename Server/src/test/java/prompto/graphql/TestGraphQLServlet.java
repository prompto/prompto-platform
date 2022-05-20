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
		GraphQL graphQL = servlet.getGraphQL();
		ExecutionResult result = graphQL.execute(query);
		assertNotNull(result);
		assertTrue(result.getErrors().isEmpty());
		return result.getData();
	}
	
	@Test
	public void returnsText() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ returnsText }");
		assertEquals("Hello", s.get("returnsText"));
	}
	
	@Test
	public void returnsInteger() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ returnsInteger }");
		assertEquals(123L, s.get("returnsInteger"));
	}

	@Test
	public void returnsNativeEnum() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("scalar", Dialect.O, "{ returnsNativeEnum }");
		assertEquals("NATIVE", s.get("returnsNativeEnum"));
	}

	@Test
	public void returnsInstance() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ returnsInstance { firstName, lastName, birthDate, age } }");
		Map<String, Object> i = (Map<String, Object>) s.get("returnsInstance");
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}
	
	@Test
	public void returnsInstances() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ returnsInstances { firstName, lastName, birthDate, age } }");
		List<Map<String, Object>> l = (List<Map<String, Object>>) s.get("returnsInstances");
		assertEquals(1, l.size());
		Map<String, Object> i = l.get(0);
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}

	@Test
	public void returnsAbstractChild() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ returnsAbstractChild { __typename ... on Child1 { name, persons { firstName } } ... on Child2 { name, persons { firstName } } } }");
		Map<String, Object> i = (Map<String, Object>) s.get("returnsAbstractChild");
		assertEquals("Child1", i.get("name"));
	}

	@Test
	public void returnsAbstractChildren() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("instance", Dialect.O, "{ returnsAbstractChildren { __typename ... on Parent { name } ... on Child1 { name, persons { firstName } } ... on Child2 { name, persons { firstName } } } }");
		List<Map<String, Object>> l = (List<Map<String, Object>>) s.get("returnsAbstractChildren");
		assertEquals(3, l.size());
		Map<String, Object> i = l.get(0);
		assertEquals("Child1", i.get("name"));
		i = l.get(1);
		assertEquals("Child2", i.get("name"));
		i = l.get(2);
		assertEquals("Parent", i.get("name"));
	}

	@Test
	public void returnsCursor() throws Exception {
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
