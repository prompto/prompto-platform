package prompto.store.mongo;

import java.util.List;

import org.bson.conversions.Bson;

import prompto.store.IQuery;

public class MongoQuery implements IQuery {

	Bson predicate;
	Long first;
	Long last;
	Bson projection;
	List<Bson> orderBys;
	
	public MongoQuery(Bson predicate, Long first, Long last, Bson projection, List<Bson> orderBy) {
		this.predicate = predicate;
		this.first = first;
		this.last = last;
		this.projection = projection;
		this.orderBys = orderBy;
	}

}
