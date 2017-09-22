package prompto.config.mongo;

import prompto.config.IConfigurationReader;
import prompto.config.IStoreConfiguration;
import prompto.config.StoreConfiguration;

public class MongoStoreConfiguration extends StoreConfiguration implements IMongoStoreConfiguration {

	public MongoStoreConfiguration(IConfigurationReader reader) {
		super(reader);
	}
	
	@Override
	public String getReplicaSetURI() {
		return reader.getString("replicaSetURI");
	}
	
	public Integer getPort() {
		return reader.getIntegerOrDefault("port", 27017);
	};
	
	@Override
	public IStoreConfiguration withDbName(String dbName) {
		return new MongoStoreConfiguration(reader) {
			@Override
			public String getDbName() {
				return dbName;
			}
		};
	}
	
	@Override
	public IMongoStoreConfiguration withReplicaSetURI(String uri) {
		return new MongoStoreConfiguration(reader) {
			@Override
			public String getReplicaSetURI() {
				return uri;
			}
		};
	}
	
}
