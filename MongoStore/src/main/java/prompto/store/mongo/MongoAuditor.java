package prompto.store.mongo;

import java.lang.Thread.State;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonTimestamp;
import org.bson.BsonType;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.conversions.Bson;
import org.bson.internal.UuidHelper;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoChangeStreamCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;

import prompto.intrinsic.PromptoDbId;
import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;
import prompto.store.IAuditMetadata;
import prompto.store.IAuditRecord;
import prompto.store.IAuditRecord.Operation;
import prompto.store.IStored;
import prompto.utils.Logger;

public class MongoAuditor {

	static final Logger logger = new Logger();
	static final String AUDIT_RECORDS_COLLECTION = "auditRecords";
	static final String AUDIT_METADATAS_COLLECTION = "auditMetadatas";
	static final String AUDIT_CONFIGS_COLLECTION = "auditConfigs";
	static final String METADATA_DBID_FIELD_NAME = "metadataDbId";
	static final String INSTANCE_DBID_FIELD_NAME = "instanceDbId";
	static final String UTC_TIMESTAMP_FIELD_NAME = "utcTimestamp";
	

	private final AtomicBoolean isTerminated = new AtomicBoolean(false);
	final MongoStore store;
	Thread thread;
	AuditMetadata metadata;
	
	public MongoAuditor(MongoStore store) {
		this.store = store;
	}
	
	public void start() {
		createThread();
		startThread();
	}

	private void createThread() {
		if(thread!=null)
			throw new IllegalStateException("Auditor thread already exists!");
		thread = new Thread(this::watchInstancesChanges, "Mongo auditor");		
	}

	private void startThread() {
		if(thread==null)
			throw new IllegalStateException("Auditor thread does not exist!");
		else if(thread.getState()!=State.NEW)
			throw new IllegalStateException("Auditor thread is already started!");
		thread.start();
	}

	private void watchInstancesChanges() {
		List<Bson> filters = Collections.singletonList(Aggregates.match(Filters.in("operationType", Arrays.asList("insert", "update", "delete"))));
		BsonTimestamp resumeTimestamp = fetchLastAuditTimestamp();
		if(resumeTimestamp==null) 
			resumeTimestamp = computeResumeTimestamp();
		ChangeStreamIterable<Document> stream = store.getInstancesCollection()
				.watch(filters)
				.startAtOperationTime(resumeTimestamp)
				.fullDocument(FullDocument.UPDATE_LOOKUP);
		try (MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor = stream.cursor()) {
			while(!isTerminated.get()) {
				consumeChanges(cursor);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch(InterruptedException ignored) {
					
				}
			}
		}
	}
	
	private void consumeChanges(MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor) {
		for(;;) {
			ChangeStreamDocument<Document> change = cursor.tryNext();
			if(change==null)
				return;
			auditInstanceChange(change);	
		}
	}

	private BsonTimestamp computeResumeTimestamp() {
		LocalDateTime dt = LocalDateTime.now().minusYears(1);
		long seconds = dt.toEpochSecond(ZoneOffset.UTC);
		return new BsonTimestamp((int)seconds, 0);
	}

	private BsonTimestamp fetchLastAuditTimestamp() {
		Bson filter = Filters.eq("name", "LAST_AUDIT_TIMESTAMP");
		Document record = store.db.getCollection(AUDIT_CONFIGS_COLLECTION).find(filter).first();
		if(record!=null)
			return record.get("timeStamp", BsonTimestamp.class);
		else 
			return null;
	}

	private void storeLastAuditTimestamp(ClientSession session, ChangeStreamDocument<Document> change) {
		Document record = new Document();
		record.put("name", "LAST_AUDIT_TIMESTAMP");
		record.put("timeStamp", change.getClusterTime());
		Document update = new Document("$set", record);
		Bson filter = Filters.eq("name", "LAST_AUDIT_TIMESTAMP");
		store.db.getCollection(AUDIT_CONFIGS_COLLECTION).updateOne(filter, update, new UpdateOptions().upsert(true));
		
	}

	private void auditInstanceChange(ChangeStreamDocument<Document> change) {
		loadMetadataRecordIfRequired(change);
		storeAuditRecord(change);
	}

	private void storeAuditRecord(ChangeStreamDocument<Document> change) {
		try(ClientSession session = store.client.startSession()) {
			logger.debug(()->"Auditing change for record " + change.getDocumentKey().get("_id"));
			session.startTransaction();
			switch(change.getOperationType()) {
			case INSERT:
				storeInsertRecord(session, change);
				break;
			case UPDATE:
				storeUpdateRecord(session, change);
				break;
			case DELETE:
				storeDeleteRecord(session, change);
				break;
			default:
				logger.warn(()->"Unsupported operation: " + change.getOperationType().name());
			}
			storeLastAuditTimestamp(session, change);
			session.commitTransaction();
		}
	}

	private void storeInsertRecord(ClientSession session, ChangeStreamDocument<Document> change) {
		AuditRecord insert = newAuditRecord();
		insert.setInstanceDbId(store.convertToDbId(change.getDocumentKey().get("_id")));
		insert.setOperation(Operation.INSERT);
		insert.setInstance(new StoredDocument(store, change.getFullDocument()));
		store.db.getCollection(AUDIT_RECORDS_COLLECTION).insertOne(session, insert);
	}

	private void storeUpdateRecord(ClientSession session, ChangeStreamDocument<Document> change) {
		AuditRecord update = newAuditRecord();
		update.setInstanceDbId(store.convertToDbId(change.getDocumentKey().get("_id")));
		update.setOperation(Operation.UPDATE);
		update.setInstance(new StoredDocument(store, change.getFullDocument()));
		update.put("removedFields", change.getUpdateDescription().getRemovedFields());
		update.put("updatedFields", change.getUpdateDescription().getUpdatedFields());
		store.db.getCollection(AUDIT_RECORDS_COLLECTION).insertOne(update);
	}

	private void storeDeleteRecord(ClientSession session, ChangeStreamDocument<Document> change) {
		AuditRecord delete = newAuditRecord();
		delete.setInstanceDbId(store.convertToDbId(change.getDocumentKey().get("_id")));
		delete.setOperation(Operation.DELETE);
		store.db.getCollection(AUDIT_RECORDS_COLLECTION).insertOne(delete);
	}
	
	private AuditRecord newAuditRecord() {
		AuditRecord audit = new AuditRecord(store);
		audit.setDbId(store.convertToDbId(UUID.randomUUID()));
		if(metadata!=null) {
			audit.setMetadataDbId(metadata.getAuditMetadataId(store));
			audit.setUTCTimestamp(metadata.getUTCTimestamp());
		} else {
			audit.setMetadataDbId(null);
			audit.setUTCTimestamp(LocalDateTime.now());
		}	
		return audit;
	}

	private void loadMetadataRecordIfRequired(ChangeStreamDocument<Document> change) {
		BsonDocument lsId = change.getLsid();
		// if document was stored outside a session there is no associated metadata
		if(lsId==null)
			metadata = null;
		else {
			Object sessionId = null;
			if(lsId.get("id").getBsonType()==BsonType.BINARY) {
				BsonBinary binary = lsId.get("id").asBinary();
				sessionId = UuidHelper.decodeBinaryToUuid(binary.getData(), binary.getType(), UuidRepresentation.STANDARD);
			} else
				sessionId = lsId.toString();
			Object txnNumber = change.getTxnNumber();
			if(metadata!=null && !(sessionId.equals(metadata.get("mongoSessionId")) && txnNumber.equals(metadata.get("mongoTxnNumber"))))
				metadata = null;
			if(metadata==null) {
				Bson predicate = Filters.and(Filters.eq("mongoSessionId", sessionId), Filters.eq("mongoTxnNumber", txnNumber));
				Document data = store.db.getCollection(AUDIT_METADATAS_COLLECTION).find(predicate).first();
				if(data!=null)
					metadata = new AuditMetadata(data);
				else
					metadata = createMetadata(sessionId, txnNumber, change.getClusterTime());
			}
		}
	}

	private AuditMetadata createMetadata(Object sessionId, Object txnNumber, BsonTimestamp timestamp) {
		AuditMetadata metadata = newAuditMetadata();
		metadata.put("description", "<pre-existing-records>");
		metadata.put("mongoSessionId", sessionId);
		metadata.put("mongoTxnNumber", txnNumber);
		Instant instant = Instant.ofEpochSecond(timestamp.getTime());
		metadata.setUTCTimestamp(instant.atOffset(ZoneOffset.UTC).toLocalDateTime());
		store.db.getCollection(AUDIT_METADATAS_COLLECTION).insertOne(metadata);
		return metadata;
	}


	public void stop() {
		if(thread==null)
			return;
		isTerminated.set(true);
		try {
			thread.join();
		} catch(InterruptedException ignored) {
			
		}
	}
	
	@SuppressWarnings("serial")
	static class AuditRecord extends Document implements IAuditRecord {

		final MongoStore store;
		
		public AuditRecord(MongoStore store) {
			this.store = store;
		}

		public AuditRecord(MongoStore store, Document data) {
			super(data);
			this.store = store;
		}

		@Override
		public void setDbId(PromptoDbId id) {
			put("_id", id);
		}

		@Override
		public PromptoDbId getDbId() {
			return store.convertToDbId(get("_id"));
		}

		@Override
		public void setMetadataDbId(PromptoDbId id) {
			put(METADATA_DBID_FIELD_NAME, id);
		}

		@Override
		public PromptoDbId getMetadataDbId() {
			return store.convertToDbId(get(METADATA_DBID_FIELD_NAME));
		}

		@Override
		public void setUTCTimestamp(LocalDateTime timeStamp) {
			put(UTC_TIMESTAMP_FIELD_NAME, timeStamp);
		}

		@Override
		public LocalDateTime getUTCTimestamp() {
			Object timeStamp = get(UTC_TIMESTAMP_FIELD_NAME);
			return timeStamp==null ? null : convertUTCTimestamp(timeStamp);
		}

		private LocalDateTime convertUTCTimestamp(Object timeStamp) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setInstanceDbId(PromptoDbId dbId) {
			put(INSTANCE_DBID_FIELD_NAME, dbId);
		}

		@Override
		public PromptoDbId getInstanceDbId() {
			return store.convertToDbId(get(INSTANCE_DBID_FIELD_NAME));
		}

		@Override
		public void setOperation(Operation operation) {
			put("operation", operation.name());
		}

		@Override
		public Operation getOperation() {
			Object operation = get("operation");
			return operation==null ? null : Operation.valueOf(operation.toString());
		}

		@Override
		public void setInstance(IStored stored) {
			put("instance", ((StoredDocument)stored).document);
		}

		@Override
		public IStored getInstance() {
			Object instance = get("instance");
			return instance instanceof Document ? new StoredDocument(store, (Document)instance) : null;
		}

		@Override
		public PromptoDocument<String, Object> toDocument() {
			return new PromptoDocument<>(this);
		}

	}

	@SuppressWarnings("serial")
	static class AuditMetadata extends Document implements IAuditMetadata {

		public AuditMetadata() {
		}
		
		public AuditMetadata(Map<String, Object> data) {
			super(data);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T get(String fieldName, Class<T> resultClass) {
			if(resultClass == LocalDateTime.class) {
				Object value = get(fieldName);
				if(value instanceof Date)
					return (T)LocalDateTime.ofInstant(((Date)value).toInstant(), ZoneId.of("UTC"));
			} 
			return super.get(fieldName, resultClass);
		}

		@Override
		public PromptoDocument<String, Object> toDocument() {
			return new PromptoDocument<String, Object>(this);
		}		
		
	}

	public AuditMetadata newAuditMetadata() {
		AuditMetadata meta = new AuditMetadata();
		meta.setAuditMetadataId(store.convertToDbId(UUID.randomUUID()));
		return meta;
	}

	public AuditMetadata populateAuditMetadata(ClientSession session, AuditMetadata auditMetadata) {
		if(auditMetadata==null)
			auditMetadata = newAuditMetadata();
		auditMetadata.setUTCTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
		auditMetadata.put("mongoSessionId", session.getServerSession().getIdentifier().get("id"));
		auditMetadata.put("mongoTxnNumber", session.getServerSession().getTransactionNumber());
		return auditMetadata;
	}

	public PromptoDbId fetchLatestAuditMetadataId(PromptoDbId dbId) {
		Document data = store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq(INSTANCE_DBID_FIELD_NAME, dbId.getValue()))
				.projection(Projections.include(METADATA_DBID_FIELD_NAME))
				.sort(Sorts.orderBy(Sorts.descending(UTC_TIMESTAMP_FIELD_NAME)))
				.limit(1)
				.first();
		return data==null ? null : store.convertToDbId(data.get(METADATA_DBID_FIELD_NAME));
	}

	public PromptoList<PromptoDbId> fetchAllAuditMetadataIds(PromptoDbId dbId) {
		return StreamSupport.stream(store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq(INSTANCE_DBID_FIELD_NAME, dbId.getValue()))
				.projection(Projections.include(METADATA_DBID_FIELD_NAME))
				.sort(Sorts.orderBy(Sorts.descending(UTC_TIMESTAMP_FIELD_NAME)))
				.spliterator(), false)
				.map(data -> data.get(METADATA_DBID_FIELD_NAME))
				.map(PromptoDbId::of)
				.collect(PromptoList.collector());
	}

	public PromptoList<PromptoDbId> fetchDbIdsAffectedByAuditMetadataId(PromptoDbId dbId) {
		return StreamSupport.stream(store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq(METADATA_DBID_FIELD_NAME, dbId.getValue()))
				.projection(Projections.include(INSTANCE_DBID_FIELD_NAME))
				.sort(Sorts.orderBy(Sorts.descending(UTC_TIMESTAMP_FIELD_NAME)))
				.spliterator(), false)
				.map(data -> data.get(INSTANCE_DBID_FIELD_NAME))
				.map(PromptoDbId::of)
				.collect(PromptoList.collector());
	}

	public AuditRecord fetchLatestAuditRecord(PromptoDbId dbId) {
		Document data = store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq(INSTANCE_DBID_FIELD_NAME, dbId.getValue()))
				.sort(Sorts.orderBy(Sorts.descending(UTC_TIMESTAMP_FIELD_NAME)))
				.limit(1)
				.first();
		return new AuditRecord(store, data);
	}

	public PromptoList<AuditRecord> fetchAllAuditRecords(PromptoDbId dbId) {
		return StreamSupport.stream(store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq(INSTANCE_DBID_FIELD_NAME, dbId.getValue()))
				.sort(Sorts.orderBy(Sorts.descending(UTC_TIMESTAMP_FIELD_NAME))).spliterator(), false)
				.map(data -> new AuditRecord(store, data))
				.collect(PromptoList.collector());
	}

	public PromptoList<AuditRecord> fetchAuditRecordsMatching(Map<String, Object> auditPredicates, Map<String, Object> instancePredicates) {
		if((auditPredicates==null ? 0 : auditPredicates.size()) + (instancePredicates==null ? 0 : instancePredicates.size())==0)
			return new PromptoList<>(false);
		List<Bson> auditFilters = auditPredicates==null ? Collections.emptyList() : auditPredicates.entrySet().stream()
				.map(e -> Filters.eq(e.getKey(), convertQueryValue(e.getValue())))
				.collect(Collectors.toList());
		List<Bson> instanceFilters = instancePredicates==null ? Collections.emptyList() : instancePredicates.entrySet().stream()
				.map(e -> Filters.eq("instance." + e.getKey(), convertQueryValue(e.getValue())))
				.collect(Collectors.toList());
		List<Bson> filters = Stream.concat(auditFilters.stream(), instanceFilters.stream()).collect(Collectors.toList());
		Bson filter = filters.size() > 1 ? Filters.and(filters) : filters.get(0);
		return StreamSupport.stream(store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(filter)
				.sort(Sorts.orderBy(Sorts.descending(UTC_TIMESTAMP_FIELD_NAME))).spliterator(), false)
				.map(data -> new AuditRecord(store, data))
				.collect(PromptoList.collector());
				
	}
	
	static Object convertQueryValue(Object value) {
		if(value instanceof Enum)
			return ((Enum<?>)value).name();
		else
			return value;
	}


}
