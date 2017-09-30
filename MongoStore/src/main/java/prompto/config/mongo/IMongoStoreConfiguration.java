package prompto.config.mongo;

import prompto.config.IStoreConfiguration;

public interface IMongoStoreConfiguration extends IStoreConfiguration {

	String getReplicaSetURI();
	IMongoStoreConfiguration withReplicaSetURI(String uri);
	IMongoReplicaSetConfiguration getReplicaSetConfiguration();
	IMongoStoreConfiguration withReplicaSetConfiguration(IMongoReplicaSetConfiguration config);
}
