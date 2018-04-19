package prompto.store.datomic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import prompto.store.AttributeInfo;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;

public class DatomicQueryBuilder implements IQueryBuilder {

	List<Object> values = new ArrayList<>();
	Queue<String> clauses = new LinkedList<>();
	
	public DatomicQueryBuilder() {
		values.add(null); // placeholder for db
	}
	
	public <T> String addValue(T value) {
		String id = "v" + values.size();
		values.add(value);
		return id;
	}
	
	@Override
	public <T> IQueryBuilder verify(AttributeInfo info, MatchOp match, T fieldValue) {
		String id = addValue(fieldValue);
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
		String query = "[:find ?e :in $ :where " + clauses.poll() + "]"; 
		return new DatomicQuery(query, values);
	}

}
