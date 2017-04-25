package prompto.store.mongo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import prompto.error.PromptoError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;
import prompto.store.IStorable;
import prompto.store.IStorable.IDbIdListener;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;

public class MongoStore implements IStore {
	
	static final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		    MongoClient.getDefaultCodecRegistry(),
		    CodecRegistries.fromCodecs(
		    		new PromptoDateCodec(),
		    		new PromptoTimeCodec(),
		    		new PromptoDateTimeCodec())
		);
		 
	MongoClient client;
	MongoDatabase db;
	Map<String, AttributeInfo> fields = new HashMap<>();
	
	public MongoStore(String host, int port) {
		this(host, port, null, null);
	}
	
	public MongoStore(String host, int port, String user, String password) {
		ServerAddress address = new ServerAddress(host, port);
		MongoClientOptions options = MongoClientOptions.builder()
		                .codecRegistry(codecRegistry)
		                .build();
		if(user!=null && password!=null) {
			MongoCredential cred = MongoCredential.createCredential(user, "admin", password.toCharArray());
			client = new MongoClient(address, Collections.singletonList(cred), options);
		} else
			client = new MongoClient(address, options);
	}

	public void setDatabase(String name) {
		db = client.getDatabase(name);
	}


	@Override
	public Class<?> getDbIdClass() {
		return UUID.class;
	}

	@Override
	public Object convertToDbId(Object dbId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Family getColumnTypeFamily(String name) throws PromptoError {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createOrUpdateColumns(Collection<AttributeInfo> columns) throws PromptoError {
		columns.forEach((c)->fields.put(c.getName(), c));
	}

	@Override
	public IStorable newStorable(List<String> categories, IDbIdListener listener) {
		return new StorableDocument(categories, listener);
	}

	@Override
	public void store(Collection<?> deletables, Collection<IStorable> storables) throws PromptoError {
		List<WriteModel<Document>> operations = buildWriteModels(deletables, storables);
		if(!operations.isEmpty()) {
			MongoCollection<Document> coll = db.getCollection("instances");
			coll.bulkWrite(operations);
		}
	}

	private List<WriteModel<Document>> buildWriteModels(Collection<?> deletables, Collection<IStorable> storables) {
		Stream<WriteModel<Document>> deletes = null;
		Stream<WriteModel<Document>> upserts = null;
		if(deletables!=null)
			deletes = deletables.stream()
				.map((d)->new DeleteOneModel<>(Filters.eq(IStore.dbIdName, (Object)d)));
		if(storables!=null)
			upserts = storables.stream()
				.map((s)->((StorableDocument)s).toWriteModel());
		if(deletes==null && upserts==null)
			return Collections.emptyList();
		Stream<WriteModel<Document>> all;
		if(deletes==null)
			all = upserts;
		else if(upserts==null)
			all = deletes;
		else
			all = Stream.of(deletes, upserts)
					.flatMap(Function.identity());
		return all.collect(Collectors.toList());
	}

	@Override
	public void deleteAll() throws PromptoError {
		throw new UnsupportedOperationException();
	}

	@Override
	public PromptoBinary fetchBinary(Object dbId, String attr) throws PromptoError {
		throw new UnsupportedOperationException();
	}

	@Override
	public IStored fetchUnique(Object dbId) throws PromptoError {
		Bson filter = Filters.eq(IStore.dbIdName, dbId);
		return fetchOne(filter);
	}

	@Override
	public IQueryBuilder newQueryBuilder() {
		return new MongoQueryBuilder();
	}

	@Override
	public IStored fetchOne(IQuery query) throws PromptoError {
		return fetchOne(((MongoQuery)query).predicate);
	}
	
	private IStored fetchOne(Bson filter) throws PromptoError {
		MongoCollection<Document> coll = db.getCollection("instances");
		Iterator<Document> found = coll.find(filter)
			.limit(1)
			.iterator();
		if(found.hasNext())
			return new StoredDocument(this, found.next());
		else
			return null;
	}
	
	class StoredIterable implements IStoredIterable {

		MongoCollection<Document> collection;
		MongoQuery query;
		Long totalCount = null;
		Long count = null;
		
		StoredIterable(MongoCollection<Document> collection, MongoQuery query) {
			this.collection = collection;
			this.query = query;
		}
		
		@Override
		public Iterator<IStored> iterator() {
			FindIterable<Document> find = collection.find();
			if(query!=null) {
				if(query.predicate!=null)
					find = find.filter(query.predicate);
			}
			Iterator<Document> iter = find.iterator();

			return new Iterator<IStored>() {
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public IStored next() {
					return new StoredDocument(MongoStore.this, iter.next());
				}
			};
		}
		
		@Override
		public long totalLength() {
			if(totalCount==null) {
				if(query==null || query.predicate==null)
					totalCount = collection.count();
				else
					totalCount = collection.count(query.predicate);
			}
			return totalCount;
		}
		
		@Override
		public long length() {
			if(count==null) {
				if(query!=null && query.first!=null && query.last!=null) {
					count = 1 + query.last - query.first;
					if(count > totalLength())
						count = totalLength();
				} else
					count = totalLength();
			}
			return count;
		}
	};

	@Override
	public IStoredIterable fetchMany(IQuery query) throws PromptoError {
		MongoCollection<Document> coll = db.getCollection("instances");
		return new StoredIterable(coll, (MongoQuery)query);
	}

	@Override
	public void flush() throws PromptoError {
		// nothing to do;
	}

	static final Map<Family, Function<Object, Object>> readers = new HashMap<>();
	
	static {
		readers.put(Family.DATE, (o)->PromptoDate.fromJavaTime((Long)o));
		readers.put(Family.TIME, (o)->PromptoTime.fromMillisOfDay((Long)o));
		readers.put(Family.DATETIME, (o)->PromptoDateTime.parse(((Document)o).getString("text")));
	}
	
	public Object readFieldData(String fieldName, Object data) {
		AttributeInfo info = fields.get(fieldName);
		return readers.getOrDefault(info.getFamily(), (o)->o).apply(data);
	}
	
	public void insertDocuments(Document ... docs) {
		db.getCollection("instances").insertMany(Arrays.asList(docs));
	}


}
