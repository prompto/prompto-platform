package prompto.store.mongo;

import prompto.config.IStoreConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.store.IStoreFactory;

public class MongoStoreFactory implements IStoreFactory {

	@Override
	public MongoStore newStore(IStoreConfiguration config) throws Exception {
		IMongoStoreConfiguration mongo = (IMongoStoreConfiguration)config;
		return new MongoStore(mongo.getHost(), mongo.getPort(), mongo.getDbName(), mongo.getUser(), mongo.getPassword());
	}
}
