package prompto.store.mongo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;

public class MongoQueryBuilder implements IQueryBuilder {

	static Map<MatchOp, BiFunction<AttributeInfo, Object, Bson>> verifiers;
	
	static {
		verifiers = new HashMap<>();
		verifiers.put(MatchOp.EQUALS, MongoQueryBuilder::verify_EQUALS);
		verifiers.put(MatchOp.ROUGHLY, MongoQueryBuilder::verify_ROUGHLY);
		verifiers.put(MatchOp.CONTAINS, MongoQueryBuilder::verify_CONTAINS);
		verifiers.put(MatchOp.HAS, MongoQueryBuilder::verify_HAS);
		verifiers.put(MatchOp.HAS_ANY, MongoQueryBuilder::verify_HAS_ANY);
		verifiers.put(MatchOp.HAS_ALL, MongoQueryBuilder::verify_HAS_ALL);
		verifiers.put(MatchOp.IN, MongoQueryBuilder::verify_IN);
		verifiers.put(MatchOp.GREATER, MongoQueryBuilder::verify_GREATER);
		verifiers.put(MatchOp.LESSER, MongoQueryBuilder::verify_LESSER);
	}
	
	static Bson verify_EQUALS(AttributeInfo info, Object value) {
		return Filters.eq(getAttributeName(info), value);
	}
	
	static Bson verify_ROUGHLY(AttributeInfo info, Object value) {
		if(info.getFamily()==Family.TEXT)
			return Filters.regex(info.getName(), value.toString(), "i");
		else
			return Filters.eq(getAttributeName(info), value);
	}

	static Bson verify_CONTAINS(AttributeInfo info, Object value) {
		if(info.getFamily()==Family.TEXT)
			return Filters.regex(getAttributeName(info), ".*" + value + ".*", "i");
		else
			return Filters.eq(getAttributeName(info), value);
	}
	
	static Bson verify_HAS(AttributeInfo info, Object value) {
		return Filters.eq(getAttributeName(info), value);
	}


	@SuppressWarnings("unchecked")
	static Bson verify_HAS_ANY(AttributeInfo info, Object value) {
		if(value instanceof Collection)
			return Filters.or(((Collection<Object>)value).stream().map((v)->Filters.eq(getAttributeName(info), v)).collect(Collectors.toList()));
		else
			return Filters.eq(getAttributeName(info), value);
	}

	@SuppressWarnings("unchecked")
	static Bson verify_HAS_ALL(AttributeInfo info, Object value) {
		if(value instanceof Collection)
			return Filters.and(((Collection<Object>)value).stream().map((v)->Filters.eq(getAttributeName(info), v)).collect(Collectors.toList()));
		else
			return Filters.eq(getAttributeName(info), value);
	}

	@SuppressWarnings("unchecked")
	static Bson verify_IN(AttributeInfo info, Object value) {
		if(value instanceof Collection)
			return Filters.or(((Collection<Object>)value).stream().map((v)->Filters.eq(getAttributeName(info), v)).collect(Collectors.toList()));
		else
			return Filters.eq(getAttributeName(info), value);
	}

	static Bson verify_GREATER(AttributeInfo info, Object value) {
		return Filters.gt(getAttributeName(info), value);
	}
	
	
	static Bson verify_LESSER(AttributeInfo info, Object value) {
		return Filters.lt(getAttributeName(info), value);
	}
	
	

	private static String getAttributeName(AttributeInfo info) {
		String name = info.getName();
		return "dbId".equals(name) ? "_id" : name;
	}



	Stack<Bson> predicates = new Stack<>();
	Bson projection = null;
	List<Bson> orderBys = null;
	Long first;
	Long last;
	
	@Override
	public <T> MongoQueryBuilder verify(AttributeInfo info, MatchOp match, T fieldValue) {
		Bson predicate = verifiers.get(match).apply(info, fieldValue);
		predicates.push(predicate);
		return this;
	}

	@Override
	public MongoQueryBuilder and() {
		Bson right = predicates.pop();
		Bson left = predicates.pop();
		predicates.push(Filters.and(left, right));
		return this;
	}

	@Override
	public MongoQueryBuilder or() {
		Bson right = predicates.pop();
		Bson left = predicates.pop();
		predicates.push(Filters.or(left, right));
		return this;
	}

	@Override
	public MongoQueryBuilder not() {
		Bson top = predicates.pop();
		if(top.getClass().getSimpleName().endsWith("OrNorFilter"))
			predicates.push(norify(top));
		else
			predicates.push(Filters.not(top));
		return this;
	}

	private Bson norify(Bson predicate) {
		// Mongo does not support $not $or, so convert $or to $nor
		try {
			Class<?> klass = predicate.getClass();
			Field field = klass.getDeclaredField("operator");
			field.setAccessible(true);
			Enum<?> value = (Enum<?>)field.get(predicate);
			Enum<?>[] values = value.getDeclaringClass().getEnumConstants();
			if(value.ordinal()==0)
				value = values[1];
			else
				value = values[0];
			field.set(predicate, value);
			return predicate;
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@Override
	public MongoQueryBuilder first(Long first) {
		this.first = first;
		return this;
	}

	@Override
	public MongoQueryBuilder last(Long last) {
		this.last = last;
		return this;
	}
	
	
	@Override
	public IQueryBuilder project(List<String> projection) {
		Set<String> unique = new HashSet<>(projection);
		unique.remove("dbId");
		unique.add("_id");
		unique.add("category");
		this.projection = Projections.include(new ArrayList<String>(unique));
		return this;
	}

	@Override
	public MongoQueryBuilder orderBy(AttributeInfo attribute, boolean descending) {
		if(orderBys==null)
			orderBys = new ArrayList<>();
		Bson orderBy = descending ? Sorts.descending(attribute.getName()) : Sorts.ascending(attribute.getName());
		orderBys.add(orderBy);
		return this;
	}

	@Override
	public IQuery build() {
		Bson predicate = predicates.empty() ? null : predicates.pop();
		if(!predicates.empty())
			throw new IllegalStateException("Unused query predicates!");
		return new MongoQuery(predicate, first, last, projection, orderBys);
	}

}
