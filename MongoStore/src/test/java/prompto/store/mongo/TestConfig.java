package prompto.store.mongo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.config.ISecretKeyConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.utils.ManualTests;

@Category(ManualTests.class)
public class TestConfig {

	@Test
	public void tectCanConnectUsingReplicaSetURI() {
		
		IMongoStoreConfiguration config = new IMongoStoreConfiguration() {

			@Override public String getFactory() { return null; }
			@Override public String getHost() { return null; }
			@Override public Integer getPort() { return null; }
			@Override public String getDbName() { return "Experimental"; }
			@Override public String getUser() { return "admin"; }
			@Override public IStoreConfiguration withDbName(String dbName) { return null; }
			@Override public ISecretKeyConfiguration getSecretKeyConfiguration() {
				return new ISecretKeyConfiguration() {
					@Override public String getFactory() { return "prompto.security.PlainSecretKeyFactory"; }
					@Override public char[] getSecret() { return "admin".toCharArray(); }
				};
			}
			@Override public String getReplicaSetURI() { return "mongodb://experimental-shard-00-00-cp8j5.mongodb.net:27017,experimental-shard-00-01-cp8j5.mongodb.net:27017,experimental-shard-00-02-cp8j5.mongodb.net:27017/test?ssl=true&replicaSet=Experimental-shard-0&authSource=admin"; }
			@Override public IMongoStoreConfiguration withReplicaSetURI(String uri) { return null; }
			
		};
		
		MongoStore store = new MongoStore(config);
		assertEquals("Experimental", store.client.listDatabaseNames().first());
	}
}
