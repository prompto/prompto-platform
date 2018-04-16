package prompto.store.datomic;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class DatomicFacts {

	Object dbId;
	Map<String, Object> toAdd = new HashMap<>();
	
	public DatomicFacts(Object dbId) {
		this.dbId = dbId;
	}

	public Object getDbId() {
		return dbId;
	}
	
	public void setDbId(Object dbId) {
		this.dbId = dbId;
	}
	
	public void add(String name, Object value) {
		toAdd.put(":" + name, value);
	}

	public Stream<List<Object>> getAddedFacts() {
		Stream<List<Object>> singles = getSingleValueFacts();
		Stream<List<Object>> multiples = getMultiValueFacts();
		return Stream.concat(singles, multiples);
	}

	@SuppressWarnings("unchecked")
	private Stream<List<Object>> getMultiValueFacts() {
		return toAdd.entrySet().stream()
				.filter(e->e.getValue() instanceof Collection)
				.map(e->getMultiValueFacts(e.getKey(), (Collection<Object>)e.getValue()))
				.flatMap(Function.identity());

	}
	
	private Stream<List<Object>> getMultiValueFacts(Object key, Collection<Object> values) {
		return values.stream().map(v->Arrays.asList(":db/add", dbId, key, v));
	}

	private Stream<List<Object>> getSingleValueFacts() {
		return toAdd.entrySet().stream()
			.filter(e->!(e.getValue() instanceof Collection))
			.map(e->Arrays.asList(":db/add", dbId, e.getKey(), e.getValue()));
	}
	
	
}
