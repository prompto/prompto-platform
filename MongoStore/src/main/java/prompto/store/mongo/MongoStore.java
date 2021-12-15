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
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.BsonBinary;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CollationStrength;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;

import prompto.config.ISecretKeyConfiguration;
import prompto.config.mongo.IMongoReplicaSetConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.error.AuditDisabledError;
import prompto.error.PromptoError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoDbId;
import prompto.intrinsic.PromptoList;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.security.ISecretKeyFactory;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IAuditMetadata;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;
import prompto.store.IStorable;
import prompto.store.IStorable.IDbIdFactory;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.store.mongo.MongoAuditor.AuditMetadata;
import prompto.store.mongo.MongoAuditor.AuditRecord;
import prompto.utils.Logger;

public class MongoStore implements IStore {
	
	static final Logger logger = new Logger();
	static final String AUTH_DB_NAME = "admin";
	static final char[] EMPTY_PASSWORD = "<empty>".toCharArray();
	
	static final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		    CodecRegistries.fromCodecs(
				   	new PromptoDbIdCodec(),
		 			new PromptoDateCodec(),
		    		new PromptoTimeCodec(),
		    		new PromptoDateTimeCodec(),
		    		new PromptoVersionCodec(),
		    		new UuidCodec(UuidRepresentation.STANDARD),
		    		new StringArrayCodec()
	    		), MongoClientSettings.getDefaultCodecRegistry()
		);
		 

	public static String uriFromConfig(IMongoStoreConfiguration config) {
		try {
			char[] password = passwordFromConfig(config);
			String replicaUri = config.getReplicaSetURI();
			IMongoReplicaSetConfiguration replicaConfig = config.getReplicaSetConfiguration();
			if(replicaUri!=null) 
				return uriFromURIConfig(config, password);
			else if(replicaConfig!=null)
				return uriFromReplicaSetConfig(config, password);
			else
				return uriFromParams(config, password);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private static String uriFromParams(IMongoStoreConfiguration config, char[] password) {
		StringBuilder sb = new StringBuilder();
		sb.append("mongodb://");
		if(password!=null) {
			sb.append(config.getUser())
				.append(":")
				.append(password)
				.append("@");
		}
		sb.append(config.getHost())
			.append(":")
			.append(config.getPort())
			.append("/")
			.append(config.getDbName());
		if(password!=null)
			sb.append("?authSource=admin");
		return sb.toString();
	}

	private static String uriFromReplicaSetConfig(IMongoStoreConfiguration config, char[] password) {
		IMongoReplicaSetConfiguration replicaConfig = config.getReplicaSetConfiguration();
		StringBuilder sb = new StringBuilder();
		sb.append("mongodb://");
		if(password!=null && password!=EMPTY_PASSWORD) {
			sb.append(config.getUser())
				.append(":")
				.append(password)
				.append("@");
		}
		replicaConfig.getNodes().forEach(h->{
			sb.append(h.getHost())
				.append(':')
				.append(h.getPort())
				.append(',');
		});
		sb.setLength(sb.length()-1);
		sb.append('/')
			.append(config.getDbName())
			.append("?replicaSet=")
			.append(replicaConfig.getName());
		if(password!=null)
			sb.append("&ssl=")
			.append(replicaConfig.isSSL())
			.append("&authSource=admin");
		return sb.toString();
	}

	private static String uriFromURIConfig(IMongoStoreConfiguration config, char[] password) {
		String uri = config.getReplicaSetURI(); 
		if(password!=null)
			uri = uri.replace("mongodb://", "mongodb://" + config.getUser() + ":" + new String(password) + "@");
		return uri;
	}

	private static char[] passwordFromConfig(IMongoStoreConfiguration config) throws Exception {
		ISecretKeyConfiguration secret = config.getSecretKeyConfiguration();
		return secret==null ? null : ISecretKeyFactory.plainPasswordFromConfig(secret).toCharArray();
	}


	MongoClient client;
	ClientSession session;
	MongoDatabase db;
	Map<String, AttributeInfo> attributes = new HashMap<>();
	MongoAuditor auditor = null;
	
	public MongoStore(IMongoStoreConfiguration config) throws Exception {
		char[] password = passwordFromConfig(config);
		String replicaUri = config.getReplicaSetURI();
		IMongoReplicaSetConfiguration replicaConfig = config.getReplicaSetConfiguration();
		if(replicaUri!=null) 
			connectWithURI(config, password);
		else if(replicaConfig!=null)
			connectWithReplicaSetConfig(config, password);
		else 
			connectWithParams(config, password);
		startAuditor(config::getAudit);
		Runtime.getRuntime().addShutdownHook(new Thread(()->close()));
	}
	
	public MongoStore(String host, int port, String database, boolean audit) {
		this(host, port, database, audit, null, null);
	}
	
	public MongoStore(String host, int port, String database, boolean audit, String user, char[] password) {
		connectWithParams(host, port, database, user, password);
		startAuditor(()->audit);
		Runtime.getRuntime().addShutdownHook(new Thread(()->close()));
	}
	
	public MongoStore(String uri, boolean audit, String user, char[] password) {
		connectWithURI(uri, user, password);
		startAuditor(()->audit);
		Runtime.getRuntime().addShutdownHook(new Thread(()->close()));
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}
	
	@Override
	public synchronized void close() {
		stopAuditor();
		if(session!=null) {
			session.close();
			session = null;
		}
		if(client!=null) {
			client.close();
			client = null;
		}
	}

	private void connectWithReplicaSetConfig(IMongoStoreConfiguration config, char[] password) {
		String uri = uriFromReplicaSetConfig(config, password==null ? null : EMPTY_PASSWORD);
		connectWithURI(config.withReplicaSetURI(uri), password);
		
	}

	private void connectWithURI(IMongoStoreConfiguration config, char[] password) {
		ConnectionString conn = new ConnectionString(config.getReplicaSetURI());
		MongoClientSettings.Builder builder = MongoClientSettings.builder()
				.applyConnectionString(conn)
                .codecRegistry(codecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyToSocketSettings(socketBuilder -> socketBuilder
                		.readTimeout(6, TimeUnit.MINUTES)
                		.connectTimeout(6, TimeUnit.MINUTES));
 		if(password != null) {
 			MongoCredential credential = MongoCredential.createCredential(config.getUser(), AUTH_DB_NAME, password);
 			builder = builder.credential(credential);
		}
 		MongoClientSettings settings = builder.build();
 		String replicaSet = settings.getClusterSettings().getRequiredReplicaSetName();
		// we use 'admin' for default connection when no dbname is specified
		final String dbName = config.getDbName()!=null ? config.getDbName() : conn.getDatabase()!=null ? conn.getDatabase() : "admin";
 		logger.info(()->"Connecting " + (config.getUser()==null ? "anonymously" : "user '" + config.getUser() + "'") + " to '" + dbName+ "' database @" + replicaSet);
 		client = MongoClients.create(settings);
		if(replicaSet != null)
			session = client.startSession();
		db = client.getDatabase(dbName);
		if(!"admin".equals(dbName))
			loadAttributes();
		logger.info(()->"Connected to database @" + replicaSet);
	}
	
	private void connectWithURI(String uri, String user, char[] password) {
		ConnectionString conn = new ConnectionString(uri);
		MongoClientSettings.Builder builder = MongoClientSettings.builder()
				.applyConnectionString(conn)
                .codecRegistry(codecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyToSocketSettings(socketBuilder -> socketBuilder
                		.readTimeout(6, TimeUnit.MINUTES)
                		.connectTimeout(6, TimeUnit.MINUTES));
 		if(user != null && password != null) {
 			MongoCredential credential = MongoCredential.createCredential(user, AUTH_DB_NAME, password);
 			builder = builder.credential(credential);
		}
 		MongoClientSettings settings = builder.build();
 		String replicaSet = settings.getClusterSettings().getRequiredReplicaSetName();
		// we use 'admin' for default connection when no dbname is specified
		final String dbName = conn.getDatabase()!=null ? conn.getDatabase() : "admin";
 		logger.info(()->"Connecting " + (user==null ? "anonymously " : "user '" + user + "'") + " to '" + dbName+ "' database @" + settings.getClusterSettings().getRequiredReplicaSetName());
 		client = MongoClients.create(settings);
		if(replicaSet != null)
			session = client.startSession();
		db = client.getDatabase(dbName);
		if(!"admin".equals(dbName))
			loadAttributes();
		logger.info(()->"Connected to database @" + replicaSet);
	}
		
	private void connectWithParams(IMongoStoreConfiguration config, char[] password) {
		connectWithParams(config.getHost(), config.getPort(), config.getDbName(), config.getUser(), password);
	}
	
	private void connectWithParams(String host, int port, String database, String user, char[] password) {
		// we use 'admin' for default connection
		final String dbName = database==null ? "admin" : database;
		MongoClientSettings.Builder builder = MongoClientSettings.builder()
		                .codecRegistry(codecRegistry)
		                .uuidRepresentation(UuidRepresentation.STANDARD)
		                .applyToClusterSettings(clusterBuilder -> clusterBuilder
		                		.hosts(Collections.singletonList(new ServerAddress(host, port))))
		                .applyToSocketSettings(socketBuilder -> socketBuilder
		                		.readTimeout(6, TimeUnit.MINUTES)
		                		.connectTimeout(6, TimeUnit.MINUTES));
		if(user!=null && password!=null) {
			logger.info(()->"Connecting user '" + user + "' to '" + dbName + "' database");
			MongoCredential credential = MongoCredential.createCredential(user, AUTH_DB_NAME, password);
			builder = builder.credential(credential);
		} else 
			logger.info(()->"Connecting anonymously to '" + dbName + "' database");
		MongoClientSettings settings = builder.build();
		client = MongoClients.create(settings);
		session = null; // unnecessary but explicit, transactions require a replicaSet
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
	public Class<?> getNativeDbIdClass() {
		return UUID.class;
	}

	
	@Override
	public Object newNativeDbId() {
		return UUID.randomUUID();
	}
	
	@Override
	public Object convertToNativeDbId(Object dbId) {
		if(dbId == null)
			return null;
		else if(dbId instanceof UUID)
			return dbId;
		else if(dbId instanceof ObjectId)
			return ((ObjectId) dbId).toHexString(); // NOT a UUID!
		else if(dbId instanceof String)
			return UUID.fromString((String)dbId);
		else if(dbId instanceof BsonBinary)
			return ((BsonBinary)dbId).asUuid();
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
		boolean general = doc.getBoolean(AttributeInfo.GENERAL, false);
		boolean key = doc.getBoolean(AttributeInfo.KEY, false);
		boolean value = doc.getBoolean(AttributeInfo.VALUE, false);
		boolean words = doc.getBoolean(AttributeInfo.WORDS, false);
		attributes.put(name, new AttributeInfo(name, family, collection, general, key, value, words));
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
		if(info.isGeneral())
			createGeneralIndexIfRequired(info.getName());
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
	
	private void createGeneralIndexIfRequired(String name) {
		createKeyIndexIfRequired(name);
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
	
	void startAuditor(Supplier<Boolean> config) {
		Boolean enabled = config.get();
		if(enabled!=null && enabled)
			startAuditorIfSupported();
		else
			logger.info(()->"Not starting Auditor because it is disabled");
	}
	
	
	private void startAuditorIfSupported() {
		if(supportsAudit()) {
			auditor = new MongoAuditor(this);
			auditor.start();
			logger.info(()->"Starting Auditor");
		} else
			logger.info(()->"Not starting Auditor because it is not supported");
	}


	void stopAuditor() {
		if(auditor!=null) {
			auditor.stop();
			auditor = null;
		}
	}
	
	@Override
	public IStorable newStorable(String[] categories, IDbIdFactory dbIdFactory) {
		return new StorableDocument(categories, dbIdFactory);
	}

	@Override
	public void deleteAndStore(Collection<PromptoDbId> deletables, Collection<IStorable> storables, IAuditMetadata auditMetadata) throws PromptoError {
		List<WriteModel<Document>> operations = buildWriteModels(deletables, storables);
		if(!operations.isEmpty())
			writeOperations((AuditMetadata)auditMetadata, operations);
	}

	private void writeOperations(AuditMetadata auditMetadata, List<WriteModel<Document>> operations) {
		if(session!=null)
			writeTransaction(auditMetadata, operations);
		else
			writeBulk(operations);
	}

	private void writeTransaction(AuditMetadata auditMetadata, List<WriteModel<Document>> operations) {
		TransactionOptions txnOptions = TransactionOptions.builder()
		        .readPreference(ReadPreference.primary())
		        .readConcern(ReadConcern.LOCAL)
		        .writeConcern(WriteConcern.MAJORITY)
		        .build();
		session.startTransaction(txnOptions);
		try {
			if(auditor!=null) {
				auditMetadata = auditor.populateAuditMetadata(session, auditMetadata);
				db.getCollection(MongoAuditor.AUDIT_METADATAS_COLLECTION).insertOne(auditMetadata);
			}
			getInstancesCollection().bulkWrite(session, operations);
			session.commitTransaction();
		} catch (Throwable t) {
			session.abortTransaction();
			logger.error(()->"While writing transaction...", t);
		}
	}

	private void writeBulk(List<WriteModel<Document>> operations) {
		getInstancesCollection().bulkWrite(operations);
	}

	private List<WriteModel<Document>> buildWriteModels(Collection<PromptoDbId> deletables, Collection<IStorable> storables) {
		Stream<WriteModel<Document>> deletes = null;
		Stream<WriteModel<Document>> upserts = null;
		if(deletables!=null)
			deletes = deletables.stream()
				.map( d -> new DeleteOneModel<>(Filters.eq("_id", d.getValue())));
		if(storables!=null)
			upserts = storables.stream()
				.map((s)->((StorableDocument)s).toWriteModel());
		if(deletes==null && upserts==null)
			return Collections.emptyList();
		Stream<WriteModel<Document>> all = null;
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
	public PromptoBinary fetchBinary(PromptoDbId dbId, String attr) throws PromptoError {
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
	public IStored fetchUnique(PromptoDbId dbId) throws PromptoError {
		Bson filter = Filters.eq("_id", dbId);
		return fetchOne(filter, null);
	}

	@Override
	public IQueryBuilder newQueryBuilder() {
		return new MongoQueryBuilder();
	}

	@Override
	public IStored fetchOne(IQuery query) throws PromptoError {
		MongoQuery q = (MongoQuery)query;
		return fetchOne(q.predicate, q.projection);
	}
	
	private IStored fetchOne(Bson filter, Bson projection) throws PromptoError {
		FindIterable<Document> iterable = getInstancesCollection().find(filter)
			.limit(1);
		if(projection != null)
			iterable = iterable.projection(projection);
		Iterator<Document> found = iterable.iterator();
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
					totalCount = collection.estimatedDocumentCount();
				else
					totalCount = collection.countDocuments(query.predicate);
			}
			return totalCount;
		}
		
		@Override
		public long count() {
			if(count==null) {
				if(query!=null && query.first!=null && query.last!=null) {
					count = 1 + query.last - query.first;
					if(query.first + count - 1 > totalCount())
						count = totalCount() + 1 - query.first;
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

	MongoCollection<Document> getInstancesCollection() {
		return db.getCollection("instances");
	}
	
	@Override
	public long nextSequenceValue(String name) {
		MongoCollection<Document> sequences = db.getCollection("sequences");
		Bson filter = Filters.eq("_id", name);
		Bson update = Updates.inc("sequence", 1);
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
				.upsert(true)
				.returnDocument(ReturnDocument.AFTER);
		Document result = sequences.findOneAndUpdate(filter, update, options);
		return ((Number)result.get("sequence")).longValue();
	}

	@Override
	public void flush() throws PromptoError {
		// nothing to do;
	}

	static final Map<Family, Function<Object, Object>> readers = new HashMap<>();
	
	static {
		readers.put(Family.DATE, (o)->o instanceof Long ? PromptoDate.fromJavaTime((Long)o) : null);
		readers.put(Family.TIME, (o)->o instanceof Long ? PromptoTime.fromMillisOfDay((Long)o) : null);
		readers.put(Family.DATETIME, (o)->o instanceof Document ? PromptoDateTime.parse(((Document)o).getString("text")) : null);
		readers.put(Family.BLOB, MongoStore::binaryToPromptoBinary);
		readers.put(Family.IMAGE, MongoStore::binaryToPromptoBinary);
		readers.put(Family.VERSION, (o)->PromptoVersion.parseInt((int)o));
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
			logger.error(()->"Missing AttributeInfo for: " + fieldName);
			return null;
		}
		if(info.isCollection() && data instanceof Collection)
			return readCollectionData(info, (Collection<Object>)data);
		else
			return readers.getOrDefault(info.getFamily(), o->o).apply(data);
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


	@Override
	public Map<String, Object> fetchConfiguration(String name) {
		MongoCollection<Document> configs = db.getCollection("configurations");
		FindIterable<Document> find = configs.find()
				.filter(Filters.eq("_id", name))
				.limit(1);
		Iterator<Document> iter = find.iterator();
		if(iter.hasNext())
			return iter.next();
		else
			return null;
	}
	
	@Override
	public void storeConfiguration(String name, Map<String, Object> data) {
		Document config = new Document();
		config.putAll(data);
		Bson filter = Filters.eq("_id", name);
		MongoCollection<Document> configs = db.getCollection("configurations");
		ReplaceOptions options = new ReplaceOptions().upsert(true);
		configs.replaceOne(filter, config, options);
	}

	@Override 
	public boolean isAuditEnabled() {
		return auditor != null;
	}
	
	boolean supportsAudit() {
		// Mongo watch only works with replicaSets
		String rsName = client.getClusterDescription().getClusterSettings().getRequiredReplicaSetName();
		return rsName!=null;
	}

	private void checkAuditEnabled() {
		if(auditor==null)
			throw new AuditDisabledError();
	}

	@Override
	public AuditMetadata newAuditMetadata() {
		checkAuditEnabled();
		return auditor.newAuditMetadata();
	}

	@Override
	public PromptoDbId fetchLatestAuditMetadataId(PromptoDbId dbId) {
		checkAuditEnabled();
		return auditor.fetchLatestAuditMetadataId(dbId);
	}

	@Override
	public PromptoList<PromptoDbId> fetchAllAuditMetadataIds(PromptoDbId dbId) {
		checkAuditEnabled();
		return auditor.fetchAllAuditMetadataIds(dbId);
	}

	@Override
	public IAuditMetadata fetchAuditMetadata(PromptoDbId metaId) {
		checkAuditEnabled();
		return auditor.newAuditMetadata();
	}

	@Override
	public PromptoList<PromptoDbId> fetchDbIdsAffectedByAuditMetadataId(PromptoDbId auditId) {
		checkAuditEnabled();
		return auditor.fetchDbIdsAffectedByAuditMetadataId(auditId);
	}

	@Override
	public AuditRecord fetchLatestAuditRecord(PromptoDbId dbId) {
		checkAuditEnabled();
		return auditor.fetchLatestAuditRecord(dbId);
	}

	@Override
	public PromptoList<AuditRecord> fetchAllAuditRecords(PromptoDbId dbId) {
		checkAuditEnabled();
		return auditor.fetchAllAuditRecords(dbId);
	}

	@Override
	public PromptoList<AuditRecord> fetchAuditRecordsMatching(Map<String, Object> auditPredicates, Map<String, Object> instancePredicates) {
		checkAuditEnabled();
		return auditor.fetchAuditRecordsMatching(auditPredicates, instancePredicates);
	}
	
}
