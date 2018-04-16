package prompto.store.datomic;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
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
import datomic.Peer;
import prompto.error.InternalError;
import prompto.error.PromptoError;
import prompto.intrinsic.PromptoBinary;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.store.IStorable.IDbIdListener;
import prompto.store.datomic.Constants.Db;
import prompto.store.datomic.Constants.DbCardinality;

public abstract class BaseDatomicStore implements IStore {

	static Map<Family, String> familyTypes = new HashMap<>();
	static Map<String, Family> typeFamilies = new HashMap<>();
	
	static {
		familyTypes.put(Family.TEXT, ":db.type/string");
		
		familyTypes.forEach((f,t)->typeFamilies.put(t, f));
	}
			
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
		this.cnx = Peer.connect(this.uri);
	}

	public void disconnect() {
		this.cnx = null;
	}

	public boolean checkConnection() {
		return cnx!=null;
	}

	public Class<?> getDbIdClass() {
		return Object.class;
	}

	public Object convertToDbId(Object dbId) {
		throw new UnsupportedOperationException();
	}

	public AttributeInfo getAttributeInfo(String name) throws PromptoError {
		// TODO Auto-generated method stub
		return null;
	}

	public void createOrUpdateAttributes(Collection<AttributeInfo> attributes) throws PromptoError {
		List<Object> data = attributes.stream()
				.map(this::createOrUpdateAttribute)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		if(!data.isEmpty()) try {
			cnx.transact(data).get();
		} catch(Exception e) {
			throw new InternalError(e);
		}
	}
	
	public void dumpFacts(PrintStream output) {
		Iterable<Datom> data = cnx.db().datoms(Database.EAVT);
		Iterator<Datom> iter = data.iterator();
		while(iter.hasNext()) {
			Datom d = iter.next();
			output.println(d.e().toString() + " " + d.a().toString() + " " + d.v().toString());
		}
	}

	public Object createOrUpdateAttribute(AttributeInfo attribute) {
		AttributeInfo info = getAttributeInfo(attribute.getName());
		if(attribute.equals(info))
			return null;
		Map<String, Object> facts = new HashMap<>();
		facts.put(Db.IDENT.dbName(), ":" + attribute.getName());
		facts.put(Db.VALUETYPE.dbName(), familyTypes.get(attribute.getFamily()));
		facts.put(Db.CARDINALITY.dbName(), attribute.isCollection() ? DbCardinality.MANY.dbName() : DbCardinality.ONE.dbName());
		return facts;
	}

	public IStorable newStorable(List<String> categories, IDbIdListener listener) {
		return new StorableDocument(categories, listener);
	}

	public void store(Collection<?> deletables, Collection<IStorable> storables) throws PromptoError {
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
			cnx.transact(list).get();
		} catch(Exception e) {
			throw new InternalError(e);
		}
	}

	public void deleteAll() throws PromptoError {
		throw new UnsupportedOperationException();
	}

	public PromptoBinary fetchBinary(Object dbId, String attr)
			throws PromptoError {
		throw new UnsupportedOperationException();
	}

	public IStored fetchUnique(Object dbId) throws PromptoError {
		throw new UnsupportedOperationException();
	}

	public IQueryBuilder newQueryBuilder() {
		return new DatomicQueryBuilder();
	}

	public IStored fetchOne(IQuery query) throws PromptoError {
		throw new UnsupportedOperationException();
	}

	public IStoredIterable fetchMany(IQuery query) throws PromptoError {
		throw new UnsupportedOperationException();
	}

	public void flush() throws PromptoError {
		// no action required
	}


}
