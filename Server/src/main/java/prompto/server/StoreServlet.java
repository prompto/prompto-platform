package prompto.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.store.AttributeInfo;
import prompto.store.DataStore;
import prompto.store.IQueryBuilder;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStoredIterable;
import prompto.utils.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class StoreServlet extends CleverServlet {

	static Logger logger = new Logger();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doStuff(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doStuff(req, resp);
	}
	
	protected void doStuff(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if(path!=null) switch(path) {
		case "/fetchMany":
			fetchMany(req, resp);
			break;
		case "/deleteAndStore":
			store(req, resp);
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				storeJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				storeUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				storeMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}
	
	private void storeJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<Long, Object> updatedDbIds = new HashMap<>();
		JsonNode json = readJsonStream(req);
		if(json.has("toDelete")) 
			deleteJson(json.get("toDelete"));
		if(json.has("toStore")) 
			updatedDbIds = storeJson(json.get("toStore"));
		writeJSONResult(updatedDbIds, resp.getOutputStream());
	}

	private Map<Long, Object> storeJson(JsonNode toStore) {
		Map<Long, Object> updatedDbIds = new HashMap<>();
		if(toStore.isArray()) {
			List<IStorable> storables = StreamSupport.stream(toStore.spliterator(), false)
				.map(node->jsonToStorable(node, updatedDbIds))
				.collect(Collectors.toList());
			DataStore.getInstance().store(storables);
		} else
			logger.error(()->"Could not delete: " + toStore.toString());
		return updatedDbIds;
	}

	private IStorable jsonToStorable(JsonNode node, Map<Long, Object> updatedDbIds) {
		List<String> categories = readJsonCategories(node);
		IStorable storable = DataStore.getInstance().newStorable(categories, null);
		populateStorable(node, storable, updatedDbIds);
		return storable;
	}
	
	private void populateStorable(JsonNode record, IStorable storable, Map<Long, Object> updatedDbIds) {
		if(isRecordUpdate(record))
			populateExistingStorable(record, storable, updatedDbIds);
		else
			populateNewStorable(record, storable, updatedDbIds);
	}
	
	
	private boolean isRecordUpdate(JsonNode record) {
		JsonNode dbIdField = record.get(IStore.dbIdName);
		return dbIdField!=null && !dbIdField.has("tempDbId");
	}

	private void populateExistingStorable(JsonNode record, IStorable storable, Map<Long, Object> updatedDbIds) {
		Object dbId = DataStore.getInstance().convertToDbId(record.get(IStore.dbIdName).asText());
		Iterator<String> fieldNames = record.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if(IStore.dbIdName.equals(fieldName))
				continue;
			Object value = readJsonValue(record.get(fieldName), updatedDbIds);
			storable.setData(fieldName, value, ()->dbId);
		}
	}

	private void populateNewStorable(JsonNode record, IStorable storable, Map<Long, Object> updatedDbIds) {
		Iterator<String> fieldNames = record.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			Object value = readJsonValue(record.get(fieldName), updatedDbIds);
			if(IStore.dbIdName.equals(fieldName))
				storable.setDbId(DataStore.getInstance().convertToDbId(value));
			else
				storable.setData(fieldName, value);
		}
	}

	private Object readJsonValue(JsonNode fieldValue, Map<Long, Object> updatedDbIds) {
		if(fieldValue.has("tempDbId"))
			return updatedDbIds.computeIfAbsent(fieldValue.get("tempDbId").asLong(), k->DataStore.getInstance().newDbId());
		else if(fieldValue.isDouble() || fieldValue.isFloat())
			return fieldValue.asDouble();
		else if(fieldValue.isLong() || fieldValue.isInt())
			return fieldValue.asLong();
		else if(fieldValue.isBoolean())
			return fieldValue.asBoolean();
		else if(fieldValue.isTextual())
			return fieldValue.asText();
		else if(fieldValue.isNull())
			return null;
		else if(fieldValue.isArray())
			return StreamSupport.stream(fieldValue.spliterator(), false)
					.map(node->readJsonValue(node, updatedDbIds))
					.collect(Collectors.toList());
		else
			throw new UnsupportedOperationException(fieldValue.getNodeType().name());
	}

	private List<String> readJsonCategories(JsonNode node) {
		return StreamSupport.stream(node.get("category").spliterator(), false)
			.map(JsonNode::asText)
			.collect(Collectors.toList());
	}

	private void deleteJson(JsonNode toDelete) {
		if(toDelete.isNumber())
			DataStore.getInstance().delete(toDelete.asLong());
		else if(toDelete.isTextual())
			DataStore.getInstance().delete(toDelete.asText());
		else 
			logger.error(()->"Could not delete: " + toDelete.toString());
		
	}

	private void storeMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	private void storeUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	

	protected void fetchMany(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				fetchManyJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				fetchManyUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				fetchManyMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}
	
	private void fetchManyMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	private void fetchManyUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	private void fetchManyJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonNode json = readJsonStream(req);
		IQueryBuilder builder = DataStore.getInstance().newQueryBuilder();
		if(json.has("predicate") && !json.get("predicate").isNull())
			readPredicateJson(builder, json.get("predicate"));
		if(json.has("first") && !json.get("first").isNull())
			builder = builder.first(json.get("first").asLong());
		if(json.has("last") && !json.get("last").isNull())
			builder = builder.last(json.get("last").asLong());
		if(json.has("orderBys") && !json.get("orderBys").isNull())
			readOrderBysJson(builder, json.get("orderBys"));
		resp.setContentType("application/json");
		IStoredIterable fetched = DataStore.getInstance().fetchMany(builder.build());
		resp.setContentType("application/json");
		JsonRecordsWriter writer = new JsonRecordsWriter(resp.getOutputStream(), DataStore.getInstance());
		writer.writeRecords(fetched);
	}
	
	private void readOrderBysJson(IQueryBuilder builder, JsonNode orderBys) {
		for(JsonNode orderBy : orderBys) {
			AttributeInfo info = DataStore.getInstance().getAttributeInfo(orderBy.get("info").get("name").asText());
			builder.orderBy(info, orderBy.get("descending").asBoolean());
		}
	}
	
	private void readPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		String type = jsonNode.get("type").asText();
		switch(type) {
		case "MatchPredicate":
			readMatchPredicateJson(builder, jsonNode);
			break;
		default:
			throw new UnsupportedOperationException(type);
		}
		
	}
	
	private void readMatchPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		AttributeInfo info = DataStore.getInstance().getAttributeInfo(jsonNode.get("info").get("name").asText());
		MatchOp matchOp = MatchOp.valueOf(jsonNode.get("matchOp").get("name").asText());
		Object value = readJsonValue(jsonNode.get("value"), new HashMap<>());
		builder.verify(info, matchOp, value);
	}

	private JsonNode readJsonStream(HttpServletRequest req) throws IOException {
		try(InputStream input = req.getInputStream()) {
			return new ObjectMapper().readTree(input);
		}
	}
	
}
