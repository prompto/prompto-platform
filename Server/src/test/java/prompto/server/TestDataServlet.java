package prompto.server;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
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
	public void testThatFetchAllWithAnyIsOk() throws Exception {
		DataServlet.store = new MemStore();
		IStorable doc = DataServlet.store.newStorable(Collections.singletonList("Any"), id->{});
		doc.setData("name", "John");
		DataServlet.store.store(doc);
		JsonNode node = runQuery("fetch all");
		assertTrue(node.get("error").isNull());
		node = node.get("data");
		assertEquals(1, node.get("totalLength").asLong());
	}
	
	@Test
	public void testThatFetchAllWithCategoryIsOk() throws Exception {
		DataServlet.store = new MemStore();
		IStorable doc = DataServlet.store.newStorable(Collections.singletonList("MyCategory"), id->{});
		doc.setData("text", "someName");
		doc.setData("integer", 987654321L);
		doc.setData("decimal", 987654321.654);
		doc.setData("date", new PromptoDate(2017, 3, 14));
		doc.setData("time", new PromptoTime(16, 32, 45, 11));
		doc.setData("datetime", PromptoDateTime.parse("2017-03-14T16:32:45.011+08:00"));
		DataServlet.store.store(doc);
		JsonNode node = runQuery("fetch all");
		assertTrue(node.get("error").isNull());
		node = node.get("data");
		assertEquals(1, node.get("totalLength").asLong());
		node = node.get("value").get(0);
		assertEquals("someName", node.get("text").asText());	
		assertEquals(987654321L, node.get("integer").asLong());	
		assertEquals(987654321.654, node.get("decimal").asDouble(), 0.01);	
		assertEquals(new PromptoDate(2017, 3, 14), PromptoDate.parse(node.get("date").get("value").asText()));	
		assertEquals(new PromptoTime(16, 32, 45, 11), PromptoTime.parse(node.get("time").get("value").asText()));	
		assertEquals(PromptoDateTime.parse("2017-03-14T16:32:45.011+08:00"), PromptoDateTime.parse(node.get("datetime").get("value").asText()));	
	}
	
	

	private JsonNode runQuery(String query) throws Exception {
		URL url = new URL("http://localhost:" + port + "/ws/data?query=" + URLEncoder.encode(query, "UTF-8"));
		try(InputStream input = url.openStream()) {
			return new ObjectMapper().readTree(input);
		}
	}
}
