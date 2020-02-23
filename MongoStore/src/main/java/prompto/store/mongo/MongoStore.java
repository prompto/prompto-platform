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
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

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
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

public class MongoStore implements IStore {
	
	static final Logger logger = new Logger();
	static final String AUTH_DB_NAME = "admin";
	
	static final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		    CodecRegistries.fromCodecs(
			    		new PromptoDateCodec(),
			    		new PromptoTimeCodec(),
			    		new PromptoDateTimeCodec(),
			    		new PromptoVersionCodec(),
			    		new UuidCodec(UuidRepresentation.STANDARD),
			    		new StringArrayCodec()
		    		), MongoClient.getDefaultCodecRegistry()
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
		Runtime.getRuntime().addShutdownHook(new Thread(()->close()));
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}
	
	private synchronized void close() {
		if(client!=null) {
			client.close();
			client = null;
		}
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
			sb.append(h.getHost())
				.append(':')
				.append(h.getPort())
				.append(',');
		});
		sb.setLength(sb.length()-1);
		sb.append('/')
			.append(config.getDbName())
			.append("?ssl=")
			.append(replicaConfig.isSSL())
			.append("&authSource=admin&replicaSet=")
			.append(replicaConfig.getName());
		String uri = sb.toString();
		connectWithURI(config.withReplicaSetURI(uri), password);
		
	}

	private void connectWithURI(IMongoStoreConfiguration config, char[] password) {
		// we use 'admin' for default connection
		final String dbName = config.getDbName()==null ? "admin" : config.getDbName();
		MongoClientURI mcu = new MongoClientURI(config.getReplicaSetURI()) {
			@Override
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
		                .socketTimeout(360000)
		                .connectTimeout(360000)
		                .build();
			}
		};
		logger.info(()->"Connecting " + (config.getUser()==null ? "anonymously " : "user '" + config.getUser() + "'") + " to '" + dbName+ "' database @" + mcu.getOptions().getRequiredReplicaSetName());
		client = new MongoClient(mcu);
		db = client.getDatabase(dbName);
		if(!"admin".equals(dbName))
			loadAttributes();
		logger.info(()->"Connected to database @" + mcu.getOptions().getRequiredReplicaSetName());
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
		// we use 'admin' for default connection
		final String dbName = database==null ? "admin" : database;
		ServerAddress address = new ServerAddress(host, port);
		MongoClientOptions options = MongoClientOptions.builder()
		                .codecRegistry(codecRegistry)
		                .socketTimeout(360000)
		                .connectTimeout(360000)
		                .build();
		if(user!=null && password!=null) {
			logger.info(()->"Connecting user '" + user + "' to '" + dbName + "' database");
			MongoCredential cred = MongoCredential.createCredential(user, AUTH_DB_NAME, password);
			client = new MongoClient(address, Collections.singletonList(cred), options);
		} else {
			logger.info(()->"Connecting anonymously to '" + dbName + "' database");
			client = new MongoClient(address, options);
		}
		db = client.getDatabase(dbName);
		if(!"admin".equals(dbName))
			loadAttributes();
		logger.info(()->"Connected to '" + dbName + "' database");
	}

	@Override
	public boolean checkConnection() {
		try {
			return client.getDatabase("admin")!=null;
		} catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public Class<?> getDbIdClass() {
		return UUID.class;
	}

	
	@Override
	public Object newDbId() {
		return UUID.randomUUID();
	}
	
	@Override
	public Object convertToDbId(Object dbId) {
		if(dbId instanceof UUID)
			return dbId;
		else if(dbId instanceof ObjectId)
			return ((ObjectId) dbId).toHexString(); // NOT a UUID!
		else if(dbId instanceof String)
			return UUID.fromString((String)dbId);
		else
			return UUID.fromString(String.valueOf(dbId));
	}

	@Override
	public AttributeInfo getAttributeInfo(String name) throws PromptoError {
		return attributes.get(name);
	}
	
	void loadAttributes() {
		// need explicit loop otherwise javac will fail on Travis with ambiguous forEach
		for(Document doc : db.getCollection("attributes").find())
			loadAttribute(doc);
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
	
	void loadAttribute(String name) {
		Document doc = db.getCollection("attributes").find().filter(Filters.eq("name", name)).first();
		if(doc!=null)
			loadAttribute(doc);
	}
	
	
	@Override
	public void createOrUpdateAttributes(Collection<AttributeInfo> infos) throws PromptoError {
		// TODO check for discrepancies
		storeAttributes(infos);
		loadAttributes();
		createIndicesIfRequired();
	}

	private void createIndicesIfRequired() {
		attributes.values().stream()
			.filter(AttributeInfo::isIndexed)
			.forEach(this::createIndexIfRequired);
	}

	private void createIndexIfRequired(AttributeInfo info) {
		if(info.isKey())
			createKeyIndexIfRequired(info.getName());
		if(info.isValue())
			createValueIndexIfRequired(info.getName());
		 /*
		TODO Mongo only supports 1 full text index per collection
		TODO disable this for now as it throws a fatal error on startup
		TODO potential workaround could be to create a { dbId, fieldName, fullText } collection 
		if(info.isWords())
			createWordsIndexIfRequired(info.getName());
		*/
	}
	
	/*
	private void createWordsIndexIfRequired(String name) {
		String indexName = name + "_words";
		if(!indexExists(indexName)) {
			IndexOptions options = new IndexOptions()
					.unique(false)
					.name(indexName);
			Bson keys = Indexes.text(name);
			getInstancesCollection().createIndex(keys, options);
		}
	}
	*/
	
	private boolean indexExists(String indexName) {
		ListIndexesIterable<Document> indices = getInstancesCollection().listIndexes();
		return StreamSupport.stream(indices.spliterator(), false)
				.map(doc->doc.get("key"))
				.map(o->(Document)o)
				.map(Document::keySet)
				.anyMatch(s->s.contains(indexName));
	}

	private void createValueIndexIfRequired(String name) {
		String indexName = name + "_value";
		if(!indexExists(indexName)) {
			Collation collation = Collation.builder()
					.locale("en")
					.collationStrength(CollationStrength.PRIMARY)
					.build();
			IndexOptions options = new IndexOptions()
					.unique(false)
					.collation(collation)
					.name(indexName);
			Bson keys = Indexes.ascending(name);
			getInstancesCollection().createIndex(keys, options);
		}
	}

	private void createKeyIndexIfRequired(String name) {
		String indexName = name + "_key";
		if(!indexExists(indexName)) {
			IndexOptions options = new IndexOptions()
					.unique(false)
					.name(indexName);
			Bson keys = Indexes.ascending(name);
			getInstancesCollection().createIndex(keys, options);
		}
		
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
	public IStorable newStorable(String[] categories, IDbIdListener listener) {
		return new StorableDocument(categories, listener);
	}

	@Override
	public void store(Collection<?> deletables, Collection<IStorable> storables) throws PromptoError {
		List<WriteModel<Document>> operations = buildWriteModels(deletables, storables);
		if(!operations.isEmpty())
			getInstancesCollection().bulkWrite(operations);
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
		Iterator<Document> found = getInstancesCollection().find(filter)
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
		Iterator<Document> found = getInstancesCollection().find(filter)
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
				if(query.first!=null && query.last!=null) {
					if(query.first > 1)
						find = find.skip(query.first.intValue() - 1);
					find = find.limit((int)(1 + query.last - query.first));
				}
				if(query.orderBys!=null)
					find = find.sort(Sorts.orderBy(query.orderBys));
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
		public long totalCount() {
			if(totalCount==null) {
				if(query==null || query.predicate==null)
					totalCount = collection.count();
				else
					totalCount = collection.count(query.predicate);
			}
			return totalCount;
		}
		
		@Override
		public long count() {
			if(count==null) {
				if(query!=null && query.first!=null && query.last!=null) {
					count = 1 + query.last - query.first;
					if(count > totalCount())
						count = totalCount();
				} else
					count = totalCount();
			}
			return count;
		}
	};

	@Override
	public IStoredIterable fetchMany(IQuery query) throws PromptoError {
		MongoCollection<Document> coll = getInstancesCollection(); 
		return new StoredIterable(coll, (MongoQuery)query);
	}

	private MongoCollection<Document> getInstancesCollection() {
		return db.getCollection("instances");
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
		if(info==null) {
			loadAttribute(fieldName);
			info = attributes.get(fieldName);
		}
		if(info==null) {
			logger.error(()->"Missing AttributeInfo for " + fieldName);
			return null;
		}
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
		getInstancesCollection().insertMany(Arrays.asList(docs));
	}



}
