package prompto.store.mongo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

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
		verifiers.put(MatchOp.CONTAINED, MongoQueryBuilder::verifyCONTAINED);
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

	@SuppressWarnings("unchecked")
	static Bson verifyCONTAINED(AttributeInfo info, Object value) {
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



	Stack<Bson> stack = new Stack<>();
	Long first;
	Long last;
	
	@Override
	public <T> void verify(AttributeInfo info, MatchOp match, T fieldValue) {
		Bson predicate = verifiers.get(match).apply(info, fieldValue);
		stack.push(predicate);
	}

	@Override
	public void and() {
		Bson right = stack.pop();
		Bson left = stack.pop();
		stack.push(Filters.and(left, right));
	}

	@Override
	public void or() {
		Bson right = stack.pop();
		Bson left = stack.pop();
		stack.push(Filters.or(left, right));
	}

	@Override
	public void not() {
		Bson top = stack.pop();
		stack.push(Filters.not(top));
	}

	@Override
	public void setFirst(Long first) {
		this.first = first;
	}

	@Override
	public void setLast(Long last) {
		this.last = last;
	}

	@Override
	public void addOrderByClause(AttributeInfo attribute, boolean descending) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IQuery build() {
		Bson predicate = stack.empty() ? null : stack.pop();
		if(!stack.empty())
			throw new IllegalStateException("Unused query predicates!");
		return new MongoQuery(predicate, first, last, null);
	}

}
