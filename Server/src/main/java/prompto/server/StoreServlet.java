package prompto.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoDbId;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.store.AttributeInfo;
import prompto.store.DataStore;
import prompto.store.IAuditMetadata;
import prompto.store.IQueryBuilder;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStorable;
import prompto.store.IStorable.IDbIdFactory;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.type.BinaryType;
import prompto.utils.Logger;

@SuppressWarnings("serial")
public class StoreServlet extends CleverServlet {

	static Logger logger = new Logger();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir")));
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		doStuff(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		doStuff(req, resp);
	}
	
	protected void doStuff(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if(path!=null) switch(path) {
			case "/fetchOne":
				fetchOne(req, resp);
				break;
			case "/fetchMany":
				fetchMany(req, resp);
				break;
			case "/deleteAndStore":
				deleteAndStore(req, resp);
				break;
			default:
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void deleteAndStore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				deleteAndStoreJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				deleteAndStoreUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				deleteAndStoreMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}
	
	private void deleteAndStoreJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<Long, PromptoDbId> updatedDbIds = new HashMap<>();
		JsonNode json = readJsonStream(req);
		JsonNode toDelete = null;
		JsonNode toStore = null;
		if(json.has("toDelete")) 
			toDelete = json.get("toDelete");
		if(json.has("toStore")) 
			toStore = json.get("toStore");
		if(toDelete!=null || toStore!=null)
			updatedDbIds = deleteAndStoreJson(toDelete, toStore, null, json.get("withMeta"));
		writeJSONResult(updatedDbIds, resp.getOutputStream());
	}

	private Map<Long, PromptoDbId> deleteAndStoreJson(JsonNode toDelete, JsonNode toStore, Map<String, byte[]> parts, JsonNode withMeta) {
		Collection<PromptoDbId> deletables = collectDeletables(toDelete);
		Map<Long, PromptoDbId> updatedDbIds = new HashMap<>();
		List<IStorable> storables = collectStorables(toStore, parts, updatedDbIds);
		IAuditMetadata metadata = collectMetadata(withMeta, parts);
		DataStore.getInstance().deleteAndStore(deletables, storables, metadata);
		return updatedDbIds;
	}

	private Collection<PromptoDbId> collectDeletables(JsonNode toDelete) {
		if(toDelete==null)
			return null;
		else
			return jsonToDbIdsCollection(toDelete);
	}

	
	@SuppressWarnings("unchecked")
	private Collection<PromptoDbId> jsonToDbIdsCollection(JsonNode node) {
		Object converted = convertJsonToDbIds(node);
		if(converted==null)
			return Collections.emptyList();
		else if(converted instanceof Collection)
			return (Collection<PromptoDbId>)converted;
		else if(converted instanceof PromptoDbId)
			return Collections.singletonList((PromptoDbId)converted);
		else
			throw new UnsupportedOperationException(converted.getClass().getName());
	}
	
	
	private Object convertJsonToDbIds(JsonNode node) {
		if(node.isNumber())
			return convertJsonToDbId(node.asLong());
		else if(node.isTextual())
			return convertJsonToDbId(node.asText());
		else if(node.isArray()) {
			return StreamSupport.stream(node.spliterator(), false)
					.map(this::convertJsonToDbIds)
					.collect(Collectors.toList());
		} else {
			logger.error(()->"Could not convert: " + node.toString());
			return null;
		}
		
	}

	private Object convertJsonToDbId(Object dbId) {
		return DataStore.getInstance().convertToDbId(dbId);
	}

	private List<IStorable> collectStorables(JsonNode toStore, Map<String, byte[]> parts, Map<Long, PromptoDbId> updatedDbIds) {
		if(toStore==null)
			return null;
		else if(toStore.isArray())
			return StreamSupport.stream(toStore.spliterator(), false)
				.map(node->jsonToStorable(node, parts, updatedDbIds))
				.collect(Collectors.toList());
		else {
			logger.error(()->"Could not store: " + toStore.toString());
			return null;
		}
	}


	private IStorable jsonToStorable(JsonNode record, Map<String, byte[]> parts, Map<Long, PromptoDbId> updatedDbIds) {
		if(isRecordUpdate(record))
			return populateExistingStorable(record, parts, updatedDbIds);
		else
			return populateNewStorable(record, parts, updatedDbIds);
	}
	
	private boolean isRecordUpdate(JsonNode record) {
		JsonNode dbIdField = record.get(IStore.dbIdName);
		return dbIdField!=null && !dbIdField.has("tempDbId");
	}


	private IStorable populateExistingStorable(JsonNode record, Map<String, byte[]> parts, Map<Long, PromptoDbId> updatedDbIds) {
		// use dbId received from client
		Object rawDbId = readJsonValue(record.get(IStore.dbIdName), parts, updatedDbIds);
		PromptoDbId dbId = DataStore.getInstance().convertToDbId(rawDbId);
		// dbId factory
		IDbIdFactory dbIdFactory = new IDbIdFactory() {
			@Override public void accept(PromptoDbId dbId) { throw new IllegalStateException(); }
			@Override public PromptoDbId get() { return dbId; }
			@Override public boolean isUpdate() { return true; }
		};
		// populate storable
		List<String> categories = readJsonCategories(record);
		IStorable storable = DataStore.getInstance().newStorable(categories, dbIdFactory);
		Iterator<String> fieldNames = record.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if(IStore.dbIdName.equals(fieldName))
				continue;
			Object value = readJsonValue(record.get(fieldName), parts, updatedDbIds);
			storable.setData(fieldName, value);
		}
		return storable;
	}
	
	private IStorable populateNewStorable(JsonNode record, Map<String, byte[]> parts, Map<Long, PromptoDbId> updatedDbIds) {
		// use potentially existing dbId allocated by a dbRef in another storable
		Long tempDbId = record.get(IStore.dbIdName).get("tempDbId").asLong();
		PromptoDbId dbId = DataStore.getInstance().convertToDbId(updatedDbIds.getOrDefault(tempDbId, null));
		// dbId factory
		IDbIdFactory dbIdFactory = new IDbIdFactory() {
			@Override public void accept(PromptoDbId newDbId) { updatedDbIds.put(tempDbId, newDbId); }
			@Override public PromptoDbId get() { return dbId; }
			@Override public boolean isUpdate() { return false; }
		};
		// populate storable
		List<String> categories = readJsonCategories(record);
		IStorable storable = DataStore.getInstance().newStorable(categories, dbIdFactory );
		Iterator<String> fieldNames = record.fieldNames();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			if(IStore.dbIdName.equals(fieldName))
				continue;
			else {
				Object value = readJsonValue(record.get(fieldName), parts, updatedDbIds);
				storable.setData(fieldName, value);
			}
		}
		return storable;
	}

	private Object readJsonValue(JsonNode fieldValue, Map<String, byte[]> parts, Map<Long, PromptoDbId> updatedDbIds) {
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
		else if(fieldValue.isObject()) {
			if(fieldValue.has("name"))
				return fieldValue.get("name").asText();
			else
				return readJsonValue(fieldValue.get("type").asText(), fieldValue.get("value"), parts, updatedDbIds);
		} else if(fieldValue.isArray())
			return StreamSupport.stream(fieldValue.spliterator(), false)
					.map(node->readJsonValue(node, parts, updatedDbIds))
					.collect(Collectors.toList());
		else
			throw new UnsupportedOperationException(fieldValue.getNodeType().name());
	}

	private Object readJsonValue(String type, JsonNode fieldValue, Map<String, byte[]> parts, Map<Long, PromptoDbId> updatedDbIds) {
		switch(type) {
		case "Uuid":
			return UUID.fromString(fieldValue.asText());
		case "Date":
			return PromptoDate.parse(fieldValue.asText());
		case "Time":
			return PromptoTime.parse(fieldValue.asText());
		case "DateTime":
			return PromptoDateTime.parse(fieldValue.asText());
		case "Version":
			return PromptoVersion.parse(fieldValue.asText());
		case "Blob":
		case "Image":
			return BinaryType.readJSONValue(fieldValue, parts);
		case "DbId":
		case "%dbRef%":
			return DataStore.getInstance().convertToNativeDbId(readJsonValue(fieldValue, parts, updatedDbIds));
		default:
			throw new UnsupportedOperationException(type);
		}
	}
	
	
	private List<String> readJsonCategories(JsonNode node) {
		return StreamSupport.stream(node.get("category").spliterator(), false)
			.map(JsonNode::asText)
			.collect(Collectors.toList());
	}

	private IAuditMetadata collectMetadata(JsonNode withMeta, Map<String, byte[]> parts) {
		if(withMeta!=null) {
			if(withMeta.isObject()) {
				IAuditMetadata metadata = DataStore.getInstance().newAuditMetadata();
				withMeta.fields().forEachRemaining(e -> collectMetadata(metadata, e, parts));
				return metadata;
			} else
				logger.error(()->"Could not convert: " + withMeta.toString());
		} 
		return null;
	}


	private void collectMetadata(IAuditMetadata metadata, Entry<String, JsonNode> entry, Map<String, byte[]> parts) {
		metadata.put(entry.getKey(), readJsonValue(entry.getValue(), parts, Collections.emptyMap()));
	}

	private void deleteAndStoreMultipart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<Long, Object> result = new HashMap<>();
		Map<String, byte[]> parts = readPartsAsBytes(req);
		JsonNode toDelete = null;
		JsonNode toStore = null;
		JsonNode withMeta = null;
		if(parts.containsKey("toDelete"))
			toDelete = new ObjectMapper().readTree(parts.get("toDelete"));
		if(parts.containsKey("toStore"))
			toStore = new ObjectMapper().readTree(parts.get("toStore"));
		if(toDelete!=null || toStore!=null) {
			if(parts.containsKey("withMeta"))
				withMeta = new ObjectMapper().readTree(parts.get("withMeta"));
			Map<Long, PromptoDbId> updatedDbIds = deleteAndStoreJson(toDelete, toStore, null, withMeta);
			updatedDbIds.forEach((k,v)->result.put(k, v.getValue()));
		}
		writeJSONResult(result, resp.getOutputStream());
	}
	
	private void deleteAndStoreUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
	
	private void fetchManyMultipart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		JsonRecordsWriter writer = new JsonRecordsWriter(resp.getOutputStream(), this::fetchAttributeInfo, DataStore.getInstance(), true);
		writer.writeRecords(fetched);
	}
	
	
	protected void fetchOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				fetchOneJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				fetchOneUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				fetchOneMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}
	
	private void fetchOneMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	private void fetchOneUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	
	private void fetchOneJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonNode json = readJsonStream(req);
		IQueryBuilder builder = DataStore.getInstance().newQueryBuilder();
		if(json.has("predicate") && !json.get("predicate").isNull())
			readPredicateJson(builder, json.get("predicate"));
		resp.setContentType("application/json");
		IStored fetched = DataStore.getInstance().fetchOne(builder.build());
		JsonRecordsWriter writer = new JsonRecordsWriter(resp.getOutputStream(), this::fetchAttributeInfo, DataStore.getInstance(), true);
		writer.writeRecord(fetched);
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
		case "AndPredicate":
			readAndPredicateJson(builder, jsonNode);
			break;
		case "OrPredicate":
			readOrPredicateJson(builder, jsonNode);
			break;
		case "NotPredicate":
			readNotPredicateJson(builder, jsonNode);
			break;
		default:
			throw new UnsupportedOperationException(type);
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	private void readMatchPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		String name = jsonNode.get("info").get("name").asText();
		AttributeInfo info = fetchAttributeInfo(name);
		MatchOp matchOp = MatchOp.valueOf(jsonNode.get("matchOp").get("name").asText());
		Object value = readJsonValue(jsonNode.get("value"), null, new HashMap<>());
		if(IStore.dbIdName.equals(name) && value!=null) {
			if(value instanceof Collection)
				value = ((Collection<Object>)value).stream()
						.map(DataStore.getInstance()::convertToNativeDbId)
						.collect(Collectors.toList());
			else
				value = DataStore.getInstance().convertToNativeDbId(value);
		}
		builder.verify(info, matchOp, value);
	}
	
	private AttributeInfo fetchAttributeInfo(String name) {
		return DataStore.getInstance().getAttributeInfo(name);	
	}

	private void readAndPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		readPredicateJson(builder, jsonNode.get("left"));
		readPredicateJson(builder, jsonNode.get("right"));
		builder.and();
	}	
	

	private void readOrPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		readPredicateJson(builder, jsonNode.get("left"));
		readPredicateJson(builder, jsonNode.get("right"));
		builder.or();
	}	

	private void readNotPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		readPredicateJson(builder, jsonNode.get("predicate"));
		builder.not();
	}	

	private JsonNode readJsonStream(HttpServletRequest req) throws IOException {
		try(InputStream input = req.getInputStream()) {
			return new ObjectMapper().readTree(input);
		}
	}
	
}
