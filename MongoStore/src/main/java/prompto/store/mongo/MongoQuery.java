package prompto.store.mongo;

import java.util.List;

import org.bson.conversions.Bson;

import prompto.store.IQuery;

public class MongoQuery implements IQuery {

	Bson predicate;
	Long first;
	Long last;
	List<Bson> orderBys;
	
	public MongoQuery(Bson predicate, Long first, Long last, List<Bson> orderBy) {
		this.predicate = predicate;
		this.first = first;
		this.last = last;
		this.orderBys = orderBy;
	}

}
