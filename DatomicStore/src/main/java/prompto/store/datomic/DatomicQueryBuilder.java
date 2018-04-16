package prompto.store.datomic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import prompto.store.AttributeInfo;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;

public class DatomicQueryBuilder implements IQueryBuilder {

	Map<String, Object> variables = new HashMap<>();
	Queue<String> clauses = new LinkedList<>();
			
	public <T> String addVariable(T value) {
		String id = "v" + (1 + variables.size());
		variables.put(id,  value);
		return id;
	}
	
	@Override
	public <T> IQueryBuilder verify(AttributeInfo info, MatchOp match, T fieldValue) {
		String id = addVariable(fieldValue);
		String clause = "[?e :" + info.getName() + " ?" + id + "]";
		clauses.offer(clause);
		return this;
	}

	@Override
	public IQueryBuilder and() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder or() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder not() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder first(Long first) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder last(Long last) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder orderBy(AttributeInfo attribute, boolean descending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQuery build() {
		return new DatomicQuery();
	}

}
