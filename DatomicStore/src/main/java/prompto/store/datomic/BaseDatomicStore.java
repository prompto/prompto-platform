package prompto.store.datomic;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import datomic.Connection;
import datomic.Database;
import datomic.Datom;
import datomic.Entity;
import datomic.Peer;
import datomic.QueryRequest;
import datomic.query.EntityMap;
import prompto.error.InternalError;
import prompto.error.PromptoError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDbId;
import prompto.store.AttributeInfo;
import prompto.store.IAuditMetadata;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;
import prompto.store.IStorable;
import prompto.store.IStorable.IDbIdFactory;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.store.datomic.Constants.Db;
import prompto.store.datomic.Constants.DbCardinality;

public abstract class BaseDatomicStore implements IStore {

	String uri;
	Connection cnx;
	Map<Object, AttributeInfo> attributesByDbId = new HashMap<>();
	Map<String, Object> attributesByName = new HashMap<>();
	
	public BaseDatomicStore(String uri, boolean create) {
		this.uri = uri;
		if(create && !Peer.createDatabase(uri))
			throw new RuntimeException("Unable to create db at: " + uri);
	}
	
	public void connect() {
		cnx = Peer.connect(this.uri);
	}

	@Override
	public boolean checkConnection() {
		return cnx!=null;
	}

	@Override
	public Class<?> getNativeDbIdClass() {
		return Object.class;
	}

	@Override
	public Object newNativeDbId() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object convertToNativeDbId(Object dbId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeInfo getAttributeInfo(String name) throws PromptoError {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrUpdateAttributes(Collection<AttributeInfo> attributes) throws PromptoError {
		Stream<List<Object>> builtins = collectBuiltinAttributesFacts();
		Stream<List<Object>> custom = attributes.stream()
				.map(this::collectCustomAttributeFacts);
		List<Object> data = Stream.concat(builtins,  custom)
				.filter(Objects::nonNull)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		if(!data.isEmpty()) try {
			cnx.transact(data).get();
		} catch(Exception e) {
			throw new InternalError(e);
		}
	}
	
	private Stream<List<Object>> collectBuiltinAttributesFacts() {
		List<List<Object>> builtins = new ArrayList<>();
		builtins.add(getCategoryAttributes());
		// TODO see https://gist.github.com/favila/f33518c7e72a4079b5948d2f853053b0
		// builtins.add(getSequenceAttributes());
		return builtins.stream();
	}

	private List<Object> getCategoryAttributes() {
		Map<String, Object> unordered = new HashMap<>();
		unordered.put(Db.IDENT.dbName(), ":category");
		unordered.put(Db.VALUETYPE.dbName(), ":db.type/string");
		unordered.put(Db.CARDINALITY.dbName(), DbCardinality.MANY.dbName());
		Map<String, Object> ordered = new HashMap<>();
		ordered.put(Db.IDENT.dbName(), ":category/ordered");
		ordered.put(Db.VALUETYPE.dbName(), ":db.type/string");
		ordered.put(Db.CARDINALITY.dbName(), DbCardinality.MANY.dbName());
		return Arrays.asList(unordered, ordered);
	}
	
	/*
	private List<Object> getSequenceAttributes() {
		// sequence name
		Map<String, Object> sequenceName = new HashMap<>();
		sequenceName.put(Db.IDENT.dbName(), ":sequence/name");
		sequenceName.put(Db.VALUETYPE.dbName(), ":db.type/string");
		sequenceName.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
		sequenceName.put(Db.UNIQUE.dbName(), DbUnique.VALUE.dbName());
		// sequence value
		Map<String, Object> sequenceNonce = new HashMap<>();
		sequenceNonce.put(Db.IDENT.dbName(), ":sequence/nonce");
		sequenceNonce.put(Db.VALUETYPE.dbName(), ":db.type/uuid");
		sequenceNonce.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
		sequenceNonce.put(":db/noHistory", true);
		// sequence value
		Map<String, Object> sequenceValue = new HashMap<>();
		sequenceValue.put(Db.IDENT.dbName(), ":sequence/value");
		sequenceValue.put(Db.VALUETYPE.dbName(), ":db.type/long");
		sequenceValue.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
		sequenceValue.put(":db/noHistory", true);
		// increment code
		Map<String, Object> functionCode = new HashMap<>();
		functionCode.put(":lang", "clojure");
		functionCode.put(":params", "'[db entity attr sequence-name]");
		functionCode.put(":require", "'[[datomic.api :as d]]");
		functionCode.put(":imports", "'[[java.util UUID]]");
		String code = "'(let " + 
				"[sequence (d/entity db [:sequence/name sequence-name]) value (:sequence/value sequence)]" +
				"[{:db/id (:db/id sequence) :sequence/nonce (UUID/randomUUID) :sequence/value (inc value)} [:db/add entity attr value]]" +
				")";
		functionCode.put(":code", code);
		// increment function
		Map<String, Object> sequenceFunction = new HashMap<>();
		sequenceFunction.put(Db.IDENT.dbName(), ":sequence/increment");
		sequenceFunction.put(Db.VALUETYPE.dbName(), ":db.type/fn");
		sequenceFunction.put(":db/fn", functionCode);

		return Arrays.asList(sequenceName, sequenceNonce, sequenceValue, sequenceFunction);
	}
	*/

	public void dumpFacts(PrintStream output) {
		Iterable<Datom> data = cnx.db().datoms(Database.EAVT);
		Iterator<Datom> iter = data.iterator();
		while(iter.hasNext()) {
			Datom d = iter.next();
			output.println(d.e().toString() + " " + d.a().toString() + " " + d.v().toString());
		}
	}

	public List<Object> collectCustomAttributeFacts(AttributeInfo attribute) {
		AttributeInfo info = getAttributeInfo(attribute.getName());
		if(attribute.equals(info))
			return Collections.emptyList();
		FamilyHelper helper = FamilyHelper.HELPERS.get(attribute.getFamily());
		return helper.collectAttributeFacts(attribute);
	}

	@Override
	public IStorable newStorable(String[] categories, IDbIdFactory factory) {
		return new StorableDocument(categories, factory);
	}

	@Override
	public void deleteAndStore(Collection<PromptoDbId> deletables, Collection<IStorable> storables, IAuditMetadata audit) throws PromptoError {
		Stream<Object> retractions = null;
		Stream<Object> additions = null;
		if(deletables!=null)
			retractions = deletables.stream()
				.map(d->Arrays.asList(":db.fn/retractEntity", d));
		if(storables!=null)
			additions = storables.stream()
				.map((s)->((StorableDocument)s).getAddedFacts())
				.flatMap(Function.identity());
		if(retractions==null && additions==null)
			return;
		Stream<Object> all;
		if(retractions==null)
			all = additions;
		else if(additions==null)
			all = retractions;
		else
			all = Stream.of(retractions, additions)
					.flatMap(Function.identity());
		List<Object> list = all.collect(Collectors.toList());
		if(!list.isEmpty()) try {
			@SuppressWarnings("rawtypes")
			Map result = cnx.transact(list).get();
			if(storables!=null) {
				Database dbAfter = (Database)result.get(Connection.DB_AFTER);
				Object tempids = result.get(Connection.TEMPIDS);
				for(IStorable storable : storables) {
					Object dbId = Peer.resolveTempid(dbAfter, tempids, storable.getOrCreateDbId().getValue());
					storable.setDbId(this.convertToDbId(dbId));
				}
			}
		} catch(Exception e) {
			throw new InternalError(e);
		}
	}
	
	public void storeFacts(DatomicFacts ... storables) throws PromptoError {
		List<Object> list = Stream.of(storables)
				.map(DatomicFacts::getFacts)
				.flatMap(Function.identity())
				.collect(Collectors.toList());
		try {
			@SuppressWarnings("rawtypes")
			Map result = cnx.transact(list).get();
			Database dbAfter = (Database)result.get(Connection.DB_AFTER);
			Object tempids = result.get(Connection.TEMPIDS);
			for(DatomicFacts storable : storables)
				storable.setDbId(Peer.resolveTempid(dbAfter, tempids, storable.getDbId()));
		} catch(Exception e) {
			throw new InternalError(e);
		}
	}

	@Override
	public void deleteAll() throws PromptoError {
		throw new UnsupportedOperationException();
	}

	@Override
	public PromptoBinary fetchBinary(String table, PromptoDbId dbId, String attr)
			throws PromptoError {
		throw new UnsupportedOperationException();
	}

	@Override
	public IStored fetchUnique(PromptoDbId dbId) throws PromptoError {
		Object id = dbId.getValue();
		if(id instanceof EntityMap)
			id = ((EntityMap)id).valAt(Constants.Db.ID.dbName());
		Entity entity = cnx.db().entity(id);
		return new StoredDocument(entity);
	}

	@Override
	public IQueryBuilder newQueryBuilder() {
		return new DatomicQueryBuilder();
	}

	@Override
	public IStored fetchOne(IQuery query) throws PromptoError {
		Collection<Collection<Object>> all = fetch(query);
		Iterator<Collection<Object>> iter = all.iterator();
		if(!iter.hasNext())
			return null;
		Collection<Object> one = iter.next();
		Object dbId = one.iterator().next();
		Entity entity = cnx.db().entity(dbId);
		return new StoredDocument(entity);
	}

	@Override
	public IStoredIterable fetchMany(IQuery query) throws PromptoError {
		return new StoredIterable((DatomicQuery)query);
	}
	
	private Collection<Collection<Object>> fetch(IQuery query) {
		if(query==null)
			return Peer.query("[:find ?e :in $ :where [?e :category _]]", cnx.db());
		else {
			DatomicQuery d = (DatomicQuery)query;
			Object q = d.getQuery();
			List<Object> inputs = d.getInputs();
			inputs.set(0, cnx.db());
			QueryRequest r = QueryRequest.create(q, inputs.toArray());
			return Peer.query(r);
		}
	}
	
	
	@Override
	public long nextSequenceValue(String prefix) {
		throw new UnsupportedOperationException("yet!");
	}
	
	@Override
	public Map<String, Object> fetchConfiguration(String name) {
		throw new UnsupportedOperationException("yet!");
	}
	
	@Override
	public void storeConfiguration(String name, Map<String, Object> data) {
		throw new UnsupportedOperationException("yet!");
	}
	


	@Override
	public void flush() throws PromptoError {
		// no action required
	}
	
	@Override
	public void close() throws IOException {
		cnx.release();
		cnx = null;
	}

	class StoredIterable implements IStoredIterable {

		DatomicQuery query;
		Collection<Collection<Object>> entities;
		Long totalCount = null;
		
		StoredIterable(DatomicQuery query) {
			this.query = query;
			this.entities = fetch(query);
		}
		
		@Override
		public Iterator<IStored> iterator() {
			Iterator<Collection<Object>> iter = entities.iterator();

			return new Iterator<IStored>() {
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}
				
				@Override
				public IStored next() {
					Collection<Object> one = iter.next();
					Object dbId = one.iterator().next();
					Entity entity = cnx.db().entity(dbId);
					return new StoredDocument(entity);
				}
			};
		}
		
		@Override
		public long totalCount() {
			/* if(totalCount==null) {
				if(query==null || query.predicate==null)
					totalCount = collection.count();
				else
					totalCount = collection.count(query.predicate);
			} */
			return totalCount;
		}
		
		@Override
		public long count() {
			return entities.size();
		}
	};

	@Override 
	public boolean isAuditEnabled() {
		return false; // TODO Datomic naturally supports audit, just a matter of wiring and testing it
	}

}
