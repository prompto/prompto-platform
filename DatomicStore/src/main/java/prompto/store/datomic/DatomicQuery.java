package prompto.store.datomic;

import java.util.List;

import prompto.store.IQuery;

public class DatomicQuery implements IQuery {

	Object query;
	List<Object> inputs;
	
	public DatomicQuery(Object query, List<Object> inputs) {
		this.query = query;
		this.inputs = inputs;
	}
	
	public Object getQuery() {
		return query;
	}
	
	public List<Object> getInputs() {
		return inputs;
	}
}
