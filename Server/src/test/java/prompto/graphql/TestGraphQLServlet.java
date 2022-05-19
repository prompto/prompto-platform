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
		return result.getData();
	}
	
	@Test
	public void returnsText() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("simple-schema", Dialect.O, "{ returnsText }");
		assertEquals("Hello", s.get("returnsText"));
	}
	
	@Test
	public void returnsInteger() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("simple-schema", Dialect.O, "{ returnsInteger }");
		assertEquals(123L, s.get("returnsInteger"));
	}

	@Test
	public void returnsInstance() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("simple-schema", Dialect.O, "{ returnsInstance { firstName, lastName, birthDate, age } }");
		Map<String, Object> i = (Map<String, Object>) s.get("returnsInstance");
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}
	
	@Test
	public void returnsInstances() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("simple-schema", Dialect.O, "{ returnsInstances { firstName, lastName, birthDate, age } }");
		List<Map<String, Object>> l = (List<Map<String, Object>>) s.get("returnsInstances");
		assertEquals(1, l.size());
		Map<String, Object> i = l.get(0);
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
		assertEquals("1960-01-01", i.get("birthDate"));
		assertEquals(62L, i.get("age"));
	}

	@Test
	public void returnsCursor() throws Exception {
		Map<String, Object> s = linkResourceAndRunQuery("cursor", Dialect.O, "{ fetchPersons { count, items { firstName, lastName } } }");
		Map<String, Object> i = (Map<String, Object>) s.get("fetchPersons");
		assertEquals(2, i.get("count"));
		var list = i.get("items");
		assertTrue(list instanceof List);
		i = ((List<Map<String, Object>>)list).get(0);
		assertEquals("Eric", i.get("firstName"));
		assertEquals("Clapton", i.get("lastName"));
	}
	

}
