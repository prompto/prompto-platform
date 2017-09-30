package prompto.store.mongo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.mongodb.client.MongoIterable;

import prompto.config.IHostConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.mongo.IMongoReplicaSetConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.utils.ManualTests;

@Category(ManualTests.class)
public class TestConfig {

	@Test
	public void testCanConnectUsingReplicaSetURI() throws Exception {
		
		IMongoStoreConfiguration config = new IMongoStoreConfiguration() {

			@Override public String getFactory() { return null; }
			@Override public String getHost() { return null; }
			@Override public Integer getPort() { return null; }
			@Override public String getDbName() { return "admin"; }
			@Override public String getUser() { return "ping"; }
			@Override public IStoreConfiguration withDbName(String dbName) { return null; }
			@Override public ISecretKeyConfiguration getSecretKeyConfiguration() {
				return new ISecretKeyConfiguration() {
					@Override public String getFactory() { return "prompto.security.PlainSecretKeyFactory"; }
					@Override public char[] getSecret() { return "ping".toCharArray(); }
				};
			}
			@Override public String getReplicaSetURI() { return "mongodb://seed-shard-00-00-cp8j5.mongodb.net:27017,"
					+ "seed-shard-00-01-cp8j5.mongodb.net:27017,"
					+ "seed-shard-00-02-cp8j5.mongodb.net:27017/"
					+ "test?ssl=true&replicaSet=Seed-shard-0&authSource=admin"; }
			@Override public IMongoStoreConfiguration withReplicaSetURI(String uri) { return null; }
			@Override public IMongoReplicaSetConfiguration getReplicaSetConfiguration() { return null; }
			@Override public IMongoStoreConfiguration withReplicaSetConfiguration(IMongoReplicaSetConfiguration config) { return null; }
		};
		
		MongoStore store = new MongoStore(config);
		MongoIterable<String> names = store.client.getDatabase("admin").listCollectionNames();
		assertNotNull(names);
	}
	
	@Test
	public void testCanConnectUsingReplicaSet() throws Exception {
		
		IMongoStoreConfiguration config = new IMongoStoreConfiguration() {

			String replicaSetURI;
			
			@Override public String getFactory() { return null; }
			@Override public String getHost() { return null; }
			@Override public Integer getPort() { return null; }
			@Override public String getDbName() { return "admin"; }
			@Override public String getUser() { return "ping"; }
			@Override public IStoreConfiguration withDbName(String dbName) { return null; }
			@Override public ISecretKeyConfiguration getSecretKeyConfiguration() {
				return new ISecretKeyConfiguration() {
					@Override public String getFactory() { return "prompto.security.PlainSecretKeyFactory"; }
					@Override public char[] getSecret() { return "ping".toCharArray(); }
				};
			}
			@Override public String getReplicaSetURI() { return replicaSetURI; }
			@Override public IMongoStoreConfiguration withReplicaSetURI(String uri) { replicaSetURI = uri; return this; }
			@Override public IMongoReplicaSetConfiguration getReplicaSetConfiguration() { return new IMongoReplicaSetConfiguration() {
				@Override
				public Iterable<IHostConfiguration> getNodes() {
					return Arrays.asList(new IHostConfiguration() {
						@Override public String getHost() { return "seed-shard-00-00-cp8j5.mongodb.net"; }
						@Override public Integer getPort() { return 27017; }
					}, new IHostConfiguration() {
						@Override public String getHost() { return "seed-shard-00-01-cp8j5.mongodb.net"; }
						@Override public Integer getPort() { return 27017; }
					}, new IHostConfiguration() {
						@Override public String getHost() { return "seed-shard-00-02-cp8j5.mongodb.net"; }
						@Override public Integer getPort() { return 27017; }
					});
				}
				@Override
				public boolean isSSL() { return true; }
				@Override
				public String getName() { return "Seed-shard-0"; }
			}; }
			@Override public IMongoStoreConfiguration withReplicaSetConfiguration(IMongoReplicaSetConfiguration config) { return null; }
		};
		
		MongoStore store = new MongoStore(config);
		MongoIterable<String> names = store.client.getDatabase("admin").listCollectionNames();
		assertNotNull(names);
	}
}
