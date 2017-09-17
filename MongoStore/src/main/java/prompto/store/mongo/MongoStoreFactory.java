package prompto.store.mongo;

import prompto.config.IConfigurationReader;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.config.mongo.MongoStoreConfiguration;
import prompto.store.IStoreFactory;

public class MongoStoreFactory implements IStoreFactory {

	@Override
	public IStoreConfiguration newConfiguration(IConfigurationReader reader) {
		return new MongoStoreConfiguration(reader);
	}
	
	@Override
	public MongoStore newStore(IStoreConfiguration config) throws Exception {
		IMongoStoreConfiguration mongo = (IMongoStoreConfiguration)config;
		ISecretKeyConfiguration secret = config.getSecretKeyConfiguration();
		char[] password = secret==null ? null : secret.getSecretKey();
		return new MongoStore(mongo.getHost(), mongo.getPort(), mongo.getDbName(), mongo.getUser(), password);
	}
}
