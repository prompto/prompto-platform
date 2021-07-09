package prompto.store.mongo;

import java.lang.Thread.State;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoChangeStreamCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;

import prompto.utils.Logger;

public class MongoAuditor {

	static final Logger logger = new Logger();

	private final AtomicBoolean isTerminated = new AtomicBoolean(false);
	final MongoStore store;
	Thread thread;
	MongoChangeStreamCursor<ChangeStreamDocument<Document>> cursor;
	Document transaction;
	
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
		createTransactionRecordIfRequired(change);
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
		Document insert = new Document();
		insert.put("txnNumber", transaction.get("txnNumber"));
		insert.put("clusterTime", transaction.get("clusterTime"));
		insert.put("instanceId", change.getDocumentKey());
		insert.put("operation", change.getOperationType().name());
		insert.put("instance", change.getFullDocument());
		store.db.getCollection("audits").insertOne(insert);
	}

	private void createUpdateRecord(ChangeStreamDocument<Document> change) {
		Document insert = new Document();
		insert.put("txnNumber", transaction.get("txnNumber"));
		insert.put("clusterTime", transaction.get("clusterTime"));
		insert.put("instanceId", change.getDocumentKey());
		insert.put("operation", change.getOperationType().name());
		insert.put("instance", change.getFullDocument());
		insert.put("removed", change.getUpdateDescription().getRemovedFields());
		insert.put("updated", change.getUpdateDescription().getUpdatedFields());
		store.db.getCollection("audits").insertOne(insert);
	}

	private void createDeleteRecord(ChangeStreamDocument<Document> change) {
		Document insert = new Document();
		insert.put("txnNumber", transaction.get("txnNumber"));
		insert.put("clusterTime", transaction.get("clusterTime"));
		insert.put("instanceId", change.getDocumentKey());
		insert.put("operation", change.getOperationType().name());
		store.db.getCollection("audits").insertOne(insert);
	}
	
	private void createTransactionRecordIfRequired(ChangeStreamDocument<Document> change) {
		long txnNumber = change.getTxnNumber().longValue();
		if(transaction==null || txnNumber!=transaction.getLong("txnNumber")) {
			transaction = new Document();
			transaction.put("txnNumber", change.getTxnNumber().longValue());
			transaction.put("clusterTime", change.getClusterTime());
			// TODO populate transaction meta-data
			store.db.getCollection("transactions").insertOne(transaction);
		}
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

}
