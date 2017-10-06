package prompto.server;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import prompto.memstore.MemStore;
import prompto.store.IStorable;

public class TestDataServlet extends BaseServerTest {

	@Test
	public void testThatFetchAllOnEmptyStoreIsOk() throws Exception {
		DataServlet.store = new MemStore();
		JsonNode node = runQuery("fetch all");
		assertTrue(node.get("error").isNull());
		assertEquals(0, node.get("data").get("totalLength").asLong());
	}
	
	@Test
	public void testThatFetchAllWithCoreObjectIsOk() throws Exception {
		DataServlet.store = new MemStore();
		IStorable doc = DataServlet.store.newStorable(Collections.singletonList("Any"), id->{});
		doc.setData("name", "John");
		DataServlet.store.store(doc);
		JsonNode node = runQuery("fetch all");
		assertTrue(node.get("error").isNull());
		node = node.get("data");
		assertEquals(1, node.get("totalLength").asLong());
	}

	private JsonNode runQuery(String query) throws Exception {
		URL url = new URL("http://localhost:" + port + "/ws/data?query=" + URLEncoder.encode(query, "UTF-8"));
		try(InputStream input = url.openStream()) {
			return new ObjectMapper().readTree(input);
		}
	}
}
