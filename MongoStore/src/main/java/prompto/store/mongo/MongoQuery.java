package prompto.store.mongo;

import org.bson.conversions.Bson;

import prompto.store.IQuery;

public class MongoQuery implements IQuery {

	Bson predicate;
	Long first;
	Long last;
	Bson orderBy;
	
	public MongoQuery(Bson predicate, Long first, Long last, Bson orderBy) {
		this.predicate = predicate;
		this.first = first;
		this.last = last;
		this.orderBy = orderBy;
	}

}
