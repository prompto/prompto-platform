package prompto.store.mongo;

import java.lang.Thread.State;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
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
import org.bson.BsonType;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.conversions.Bson;
import org.bson.internal.UuidHelper;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoChangeStreamCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;

import prompto.store.IAuditMetadata;
import prompto.store.IAuditRecord;
import prompto.store.IAuditRecord.Operation;
import prompto.store.IStored;
import prompto.utils.Logger;

public class MongoAuditor {

	static final Logger logger = new Logger();
	static final String AUDIT_RECORDS_COLLECTION = "auditRecords";
	static final String AUDIT_METADATAS_COLLECTION = "auditMetadatas";
	

	private final AtomicBoolean isTerminated = new AtomicBoolean(false);
	final MongoStore store;
	Thread thread;
	MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor;
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
		List<Bson> filter = Collections.singletonList(Aggregates.match(Filters.in("operationType", Arrays.asList("insert", "update", "delete"))));
		cursor = store.getInstancesCollection()
				.watch(filter)
				.fullDocument(FullDocument.UPDATE_LOOKUP)
				.cursor();
		while(!isTerminated.get()) {
			consumeChanges();
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch(InterruptedException ignored) {
				
			}
		}
	}
	
	private void consumeChanges() {
		for(;;) {
			ChangeStreamDocument<Document> change = cursor.tryNext();
			if(change==null)
				return;
			auditInstanceChange(change);	
		}
	}

	private void auditInstanceChange(ChangeStreamDocument<Document> change) {
		loadMetadataRecordIfRequired(change);
		createAuditRecord(change);
	}

	private void createAuditRecord(ChangeStreamDocument<Document> change) {
		switch(change.getOperationType()) {
		case INSERT:
			createInsertRecord(change);
			break;
		case UPDATE:
			createUpdateRecord(change);
			break;
		case DELETE:
			createDeleteRecord(change);
			break;
		default:
			logger.warn(()->"Unsupported operation: " + change.getOperationType().name());
		}
	}

	private void createInsertRecord(ChangeStreamDocument<Document> change) {
		AuditRecord insert = newAuditRecord();
		insert.setInstanceDbId(change.getDocumentKey().get("_id"));
		insert.setOperation(Operation.INSERT);
		insert.setInstance(new StoredDocument(store, change.getFullDocument()));
		store.db.getCollection(AUDIT_RECORDS_COLLECTION).insertOne(insert);
	}

	private void createUpdateRecord(ChangeStreamDocument<Document> change) {
		AuditRecord insert = newAuditRecord();
		insert.setInstanceDbId(change.getDocumentKey().get("_id"));
		insert.setOperation(Operation.UPDATE);
		insert.setInstance(new StoredDocument(store, change.getFullDocument()));
		insert.put("removedFields", change.getUpdateDescription().getRemovedFields());
		insert.put("updatedFields", change.getUpdateDescription().getUpdatedFields());
		store.db.getCollection(AUDIT_RECORDS_COLLECTION).insertOne(insert);
	}

	private void createDeleteRecord(ChangeStreamDocument<Document> change) {
		AuditRecord insert = newAuditRecord();
		insert.setInstanceDbId(change.getDocumentKey().get("_id"));
		insert.setOperation(Operation.DELETE);
		store.db.getCollection(AUDIT_RECORDS_COLLECTION).insertOne(insert);
	}
	
	private AuditRecord newAuditRecord() {
		AuditRecord audit = new AuditRecord(store);
		audit.setAuditRecordId(UUID.randomUUID());
		audit.setAuditMetadataId(metadata.getAuditMetadataId());
		audit.setUTCTimestamp(metadata.getUTCTimestamp());
		return audit;
	}

	private boolean loadMetadataRecordIfRequired(ChangeStreamDocument<Document> change) {
		Object sessionId = null;
		BsonDocument lsId = change.getLsid();
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
				
		}
		return metadata!=null;
	}


	public void stop() {
		if(thread==null)
			return;
		isTerminated.set(true);
		try {
			thread.join();
		} catch(InterruptedException ignored) {
			
		}
		cursor.close();
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
		public void setAuditRecordId(Object id) {
			put("_id", id);
		}

		@Override
		public Object getAuditRecordId() {
			return get("_id");
		}

		@Override
		public void setAuditMetadataId(Object id) {
			put("auditMetaDataId", id);
		}

		@Override
		public Object getAuditMetadataId() {
			return get("auditMetaDataId");
		}

		@Override
		public void setUTCTimestamp(LocalDateTime timeStamp) {
			put("utcTimestamp", timeStamp);
		}

		@Override
		public LocalDateTime getUTCTimestamp() {
			Object timeStamp = get("utcTimestamp");
			return timeStamp==null ? null : convertUTCTimestamp(timeStamp);
		}

		private LocalDateTime convertUTCTimestamp(Object timeStamp) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setInstanceDbId(Object dbId) {
			put("instanceId", dbId);
		}

		@Override
		public Object getInstanceDbId() {
			return get("instanceId");
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
		
		
		
	}

	public AuditMetadata newAuditMetadata() {
		AuditMetadata meta = new AuditMetadata();
		meta.setAuditMetadataId(UUID.randomUUID());
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

	public Object fetchLatestAuditMetadataId(Object dbId) {
		Document data = store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq("instanceId", dbId))
				.projection(Projections.include("auditMetaDataId"))
				.sort(Sorts.orderBy(Sorts.descending("utcTimestamp")))
				.limit(1)
				.first();
		return data==null ? null : data.get("auditMetaDataId");
	}

	public Collection<Object> fetchAllAuditMetadataIds(Object dbId) {
		return StreamSupport.stream(store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq("instanceId", dbId))
				.projection(Projections.include("auditMetaDataId"))
				.sort(Sorts.orderBy(Sorts.descending("utcTimestamp")))
				.spliterator(), false)
				.map(data -> data.get("auditMetaDataId"))
				.collect(Collectors.toList());
	}

	public Collection<Object> fetchDbIdsAffectedByAuditMetadataId(Object auditId) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuditRecord fetchLatestAuditRecord(Object dbId) {
		Document data = store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq("instanceId", dbId))
				.sort(Sorts.orderBy(Sorts.descending("utcTimestamp")))
				.limit(1)
				.first();
		return new AuditRecord(store, data);
	}

	public Collection<AuditRecord> fetchAllAuditRecords(Object dbId) {
		return StreamSupport.stream(store.db.getCollection(AUDIT_RECORDS_COLLECTION)
				.find(Filters.eq("instanceId", dbId))
				.sort(Sorts.orderBy(Sorts.descending("utcTimestamp"))).spliterator(), false)
				.map(data -> new AuditRecord(store, data))
				.collect(Collectors.toList());
	}

	public Collection<AuditRecord> fetchAuditRecordsMatching(Map<String, Object> auditPredicates, Map<String, Object> instancePredicates) {
		if((auditPredicates==null ? 0 : auditPredicates.size()) + (instancePredicates==null ? 0 : instancePredicates.size())==0)
			return Collections.emptyList();
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
				.sort(Sorts.orderBy(Sorts.descending("utcTimestamp"))).spliterator(), false)
				.map(data -> new AuditRecord(store, data))
				.collect(Collectors.toList());
				
	}
	
	static Object convertQueryValue(Object value) {
		if(value instanceof Enum)
			return ((Enum<?>)value).name();
		else
			return value;
	}


}
