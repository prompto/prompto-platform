package prompto.store.mongo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;

public class MongoQueryBuilder implements IQueryBuilder {

	static Map<MatchOp, BiFunction<AttributeInfo, Object, Bson>> verifiers;
	
	static {
		verifiers = new HashMap<>();
		verifiers.put(MatchOp.EQUALS, MongoQueryBuilder::verifyEQUALS);
		verifiers.put(MatchOp.ROUGHLY, MongoQueryBuilder::verifyROUGHLY);
		verifiers.put(MatchOp.CONTAINS, MongoQueryBuilder::verifyCONTAINS);
		verifiers.put(MatchOp.HAS, MongoQueryBuilder::verifyHAS);
		verifiers.put(MatchOp.IN, MongoQueryBuilder::verifyIN);
		verifiers.put(MatchOp.GREATER, MongoQueryBuilder::verifyGREATER);
		verifiers.put(MatchOp.LESSER, MongoQueryBuilder::verifyLESSER);
	}
	
	static Bson verifyEQUALS(AttributeInfo info, Object value) {
		return Filters.eq(getAttributeName(info), value);
	}
	
	static Bson verifyROUGHLY(AttributeInfo info, Object value) {
		if(info.getFamily()==Family.TEXT)
			return Filters.regex(info.getName(), value.toString(), "i");
		else
			return Filters.eq(getAttributeName(info), value);
	}

	static Bson verifyCONTAINS(AttributeInfo info, Object value) {
		if(info.getFamily()==Family.TEXT)
			return Filters.regex(getAttributeName(info), ".*" + value + ".*", "i");
		else
			return Filters.eq(getAttributeName(info), value);
	}
	
	static Bson verifyHAS(AttributeInfo info, Object value) {
		return Filters.eq(getAttributeName(info), value);
	}


	@SuppressWarnings("unchecked")
	static Bson verifyIN(AttributeInfo info, Object value) {
		if(value instanceof Collection)
			return Filters.or(((Collection<Object>)value).stream().map((v)->Filters.eq(getAttributeName(info), v)).collect(Collectors.toList()));
		else
			return Filters.eq(getAttributeName(info), value);
	}

	static Bson verifyGREATER(AttributeInfo info, Object value) {
		return Filters.gt(getAttributeName(info), value);
	}
	
	
	static Bson verifyLESSER(AttributeInfo info, Object value) {
		return Filters.lt(getAttributeName(info), value);
	}
	
	

	private static String getAttributeName(AttributeInfo info) {
		String name = info.getName();
		return "dbId".equals(name) ? "_id" : name;
	}



	Stack<Bson> predicates = new Stack<>();
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
		predicates.push(Filters.not(top));
		return this;
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
		return new MongoQuery(predicate, first, last, orderBys);
	}

}
