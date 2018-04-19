package prompto.store.datomic;

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
		builtins.add(getFamilyAttributes());
		return builtins.stream();
	}

	private List<Object> getFamilyAttributes() {
		Map<String, Object> type = new HashMap<>();
		type.put(Db.IDENT.dbName(), ":prompto/family");
		type.put(Db.VALUETYPE.dbName(), ":db.type/string");
		type.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
		return Collections.singletonList(type);
	}

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
		Collection<Collection<Object>> all = fetch(query);
		Iterator<Collection<Object>> iter = all.iterator();
		if(!iter.hasNext())
			return null;
		Collection<Object> one = iter.next();
		Object dbId = one.iterator().next();
		Entity entity = cnx.db().entity(dbId);
		return new StoredDocument(entity);
	}

	public IStoredIterable fetchMany(IQuery query) throws PromptoError {
		throw new UnsupportedOperationException();
	}

	private Collection<Collection<Object>> fetch(IQuery query) {
		DatomicQuery d = (DatomicQuery)query;
		Object q = d.getQuery();
		List<Object> inputs = d.getInputs();
		inputs.set(0, cnx.db());
		QueryRequest r = QueryRequest.create(q, inputs.toArray());
		return Peer.query(r);
	}


	public void flush() throws PromptoError {
		// no action required
	}


}
