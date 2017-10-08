package prompto.store.mongo;

import java.io.IOException;
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
import org.bson.types.Binary;

import prompto.config.ISecretKeyConfiguration;
import prompto.config.mongo.IMongoReplicaSetConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.error.PromptoError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoList;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.security.ISecretKeyFactory;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;
import prompto.store.IStorable;
import prompto.store.IStorable.IDbIdListener;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.utils.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

public class MongoStore implements IStore {
	
	static final Logger logger = new Logger();
	static final String AUTH_DB_NAME = "admin";
	
	static final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		    MongoClient.getDefaultCodecRegistry(),
		    CodecRegistries.fromCodecs(
		    		new PromptoDateCodec(),
		    		new PromptoTimeCodec(),
		    		new PromptoDateTimeCodec(),
		    		new PromptoVersionCodec())
		);
		 
	MongoClient client;
	MongoDatabase db;
	Map<String, AttributeInfo> attributes = new HashMap<>();
	
	
	public MongoStore(IMongoStoreConfiguration config) throws Exception {
		char[] password = passwordFromConfig(config);
		IMongoReplicaSetConfiguration replicaConfig = config.getReplicaSetConfiguration();
		String replicaUri = config.getReplicaSetURI();
		if(replicaConfig!=null)
			connectWithReplicaSetConfig(config, password);
		else if(replicaUri!=null) 
			connectWithURI(config, password);
		else
			connectWithParams(config, password);
	}
	
	private char[] passwordFromConfig(IMongoStoreConfiguration config) throws Exception {
		ISecretKeyConfiguration secret = config.getSecretKeyConfiguration();
		return secret==null ? null : ISecretKeyFactory.plainPasswordFromConfig(secret).toCharArray();
	}

	private void connectWithReplicaSetConfig(IMongoStoreConfiguration config, char[] password) {
		IMongoReplicaSetConfiguration replicaConfig = config.getReplicaSetConfiguration();
		StringBuilder sb = new StringBuilder();
		sb.append("mongodb://");
		replicaConfig.getNodes().forEach(h->{
			sb.append(h.getHost());
			sb.append(':');
			sb.append(h.getPort());
			sb.append(',');
		});
		sb.setLength(sb.length()-1);
		sb.append('/');
		sb.append(config.getDbName());
		sb.append("?ssl=");
		sb.append(replicaConfig.isSSL());
		sb.append("&authSource=admin&replicaSet=");
		sb.append(replicaConfig.getName());
		String uri = sb.toString();
		connectWithURI(config.withReplicaSetURI(uri), password);
		
	}

	private void connectWithURI(IMongoStoreConfiguration config, char[] password) {
		MongoClientURI mcu = new MongoClientURI(config.getReplicaSetURI()) {
			public MongoCredential getCredentials() {
				if(password==null)
					return null;
				else
					return MongoCredential.createCredential(config.getUser(), AUTH_DB_NAME, password);
			};
			@Override
			public MongoClientOptions getOptions() {
				return MongoClientOptions.builder(super.getOptions())
		                .codecRegistry(codecRegistry)
		                .build();
			}
		};
		logger.info(()->"Connecting " + (config.getUser()==null ? "anonymously " : "user '" + config.getUser() + "'") + " to '" + config.getDbName() + "' database @" + mcu.getOptions().getRequiredReplicaSetName());
		client = new MongoClient(mcu);
		db = client.getDatabase(config.getDbName());
		loadAttributes();
	}
	
	public MongoStore(String host, int port, String database) {
		connectWithParams(host, port, database, null, null);
	}
	
	public MongoStore(String host, int port, String database, String user, char[] password) {
		connectWithParams(host, port, database, user, password);
	}
	
	private void connectWithParams(IMongoStoreConfiguration config, char[] password) {
		connectWithParams(config.getHost(), config.getPort(), config.getDbName(), config.getUser(), password);
	}

	private void connectWithParams(String host, int port, String database, String user, char[] password) {
		ServerAddress address = new ServerAddress(host, port);
		MongoClientOptions options = MongoClientOptions.builder()
		                .codecRegistry(codecRegistry)
		                .build();
		if(user!=null && password!=null) {
			logger.info(()->"Connecting user '" + user + "' to '" + database + "' database");
			MongoCredential cred = MongoCredential.createCredential(user, AUTH_DB_NAME, password);
			client = new MongoClient(address, Collections.singletonList(cred), options);
		} else {
			logger.info(()->"Connecting anonymously to '" + database + "' database");
			client = new MongoClient(address, options);
		}
		db = client.getDatabase(database);
		loadAttributes();
	}

	@Override
	public Class<?> getDbIdClass() {
		return UUID.class;
	}

	@Override
	public Object convertToDbId(Object dbId) {
		return UUID.fromString(String.valueOf(dbId));
	}

	@Override
	public AttributeInfo getAttributeInfo(String name) throws PromptoError {
		return attributes.get(name);
	}
	
	void loadAttributes() {
		// need 2 statement otherwice javac will fail on Travis with ambiguous forEach
		FindIterable<Document> docs = db.getCollection("attributes").find();
		docs.forEach(this::loadAttribute);
	}
	
	void loadAttribute(Document doc) {
		String name = doc.getString("name");
		Family family = Family.valueOf(doc.getString("family"));
		boolean collection = doc.getBoolean("collection", false);
		boolean key = doc.getBoolean(AttributeInfo.KEY, false);
		boolean value = doc.getBoolean(AttributeInfo.VALUE, false);
		boolean words = doc.getBoolean(AttributeInfo.WORDS, false);
		attributes.put(name, new AttributeInfo(name, family, collection, key, value, words));
	}
	
	@Override
	public void createOrUpdateAttributes(Collection<AttributeInfo> infos) throws PromptoError {
		// TODO check for discrepancies
		storeAttributes(infos);
		loadAttributes();
	}

	private void storeAttributes(Collection<AttributeInfo> infos) {
		List<UpdateOneModel<Document>> operations = infos.stream()
				.map(this::buildWriteModel)
				.collect(Collectors.toList());
		if(!operations.isEmpty()) {
			MongoCollection<Document> coll = db.getCollection("attributes");
			coll.bulkWrite(operations);
		}
	}

	private UpdateOneModel<Document> buildWriteModel(AttributeInfo attribute) {
		Document data = new Document();
		data.put("name", attribute.getName());
		data.put("family", attribute.getFamily().name());
		data.put("collection", attribute.isCollection());
		data.put(AttributeInfo.KEY, attribute.isKey());
		data.put(AttributeInfo.VALUE, attribute.isValue());
		data.put(AttributeInfo.WORDS, attribute.isWords());
		Bson filter = Filters.eq("name", attribute.getName());
		UpdateOneModel<Document> model = new UpdateOneModel<>(filter, new Document("$set", data));
		model.getOptions().upsert(true);
		return model;
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
				.map((d)->new DeleteOneModel<>(Filters.eq("_id", (Object)d)));
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
		Bson filter = Filters.eq("_id", dbId);
		MongoCollection<Document> coll = db.getCollection("instances");
		Iterator<Document> found = coll.find(filter)
			.limit(1)
			.projection(Projections.include(attr))
			.iterator();
		if(!found.hasNext())
			return null;
		Object data = found.next().get(attr);
		if(data==null)
			return null;
		data = readFieldData(attr, data);
		if(data instanceof PromptoBinary)
			return (PromptoBinary)data;
		else
			return null; // TODO warning
	}

	@Override
	public IStored fetchUnique(Object dbId) throws PromptoError {
		Bson filter = Filters.eq("_id", dbId);
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
		readers.put(Family.BLOB, MongoStore::binaryToPromptoBinary);
		readers.put(Family.IMAGE, MongoStore::binaryToPromptoBinary);
		readers.put(Family.VERSION, (o)->PromptoVersion.parse((int)o));
	}
	
	static Object binaryToPromptoBinary(Object o) {
		try {
			BinaryData bin = new BinaryData(((Binary)o).getData());
			return new PromptoBinary(bin.getMimeType(), bin.getData());
		} catch(IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	@SuppressWarnings("unchecked")
	public Object readFieldData(String fieldName, Object data) {
		AttributeInfo info = attributes.get(fieldName);
		if(info==null)
			throw new RuntimeException("Missing AttributeInfo for " + fieldName);
		if(info.isCollection() && data instanceof Collection)
			return readCollectionData(info, (Collection<Object>)data);
		else
			return readers.getOrDefault(info.getFamily(), (o)->o).apply(data);
	}
	
	private Object readCollectionData(AttributeInfo info, Collection<Object> data) {
		Function<Object, Object> reader = readers.getOrDefault(info.getFamily(), (o)->o);
		List<Object> list = data.stream()
				.map(reader::apply)
				.collect(Collectors.toList());
		return new PromptoList<Object>(list, false);
	}

	public void insertDocuments(Document ... docs) {
		db.getCollection("instances").insertMany(Arrays.asList(docs));
	}



}
