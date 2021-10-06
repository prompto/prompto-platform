package prompto.store.mongo;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.config.IConfigurationReader;
import prompto.config.IHostConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.YamlConfigurationReader;
import prompto.config.mongo.IMongoReplicaSetConfiguration;
import prompto.config.mongo.IMongoStoreConfiguration;
import prompto.config.mongo.MongoStoreConfiguration;
import prompto.utils.ManualTests;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlElement;
import com.esotericsoftware.yamlbeans.document.YamlMapping;
import com.mongodb.client.MongoIterable;

@Category(ManualTests.class)
public class TestConfig {

	@Test
	public void connectsUsingReplicaSetURI() throws Exception {
		IMongoStoreConfiguration config = configWithReplicaSetURI();
		MongoStore store = new MongoStore(config);
		MongoIterable<String> names = store.client.getDatabase("PING").listCollectionNames();
		store.close();
		assertNotNull(names);
	}
	
	@Test
	public void producesUriFromReplicaSetURI() throws Exception {
		IMongoStoreConfiguration config = configWithReplicaSetURI();
		String uri = MongoStore.uriFromConfig(config);
		String expected = "mongodb://ping:ping@seed-shard-00-00-cp8j5.mongodb.net:27017,"
				+ "seed-shard-00-01-cp8j5.mongodb.net:27017,"
				+ "seed-shard-00-02-cp8j5.mongodb.net:27017/"
				+ "PING?ssl=true&replicaSet=Seed-shard-0&authSource=admin";
		assertEquals(expected, uri);
	}

	
	private IMongoStoreConfiguration configWithReplicaSetURI() {
		return new IMongoStoreConfiguration() {

			@Override public String getFactory() { return null; }
			@Override public String getHost() { return null; }
			@Override public Integer getPort() { return null; }
			@Override public String getDbName() { return "PING"; }
			@Override public Boolean getAudit() { return false; }
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
					+ "PING?ssl=true&replicaSet=Seed-shard-0&authSource=admin"; }
			@Override public IMongoStoreConfiguration withReplicaSetURI(String uri) { return null; }
			@Override public IMongoReplicaSetConfiguration getReplicaSetConfiguration() { return null; }
			@Override public IMongoStoreConfiguration withReplicaSetConfiguration(IMongoReplicaSetConfiguration config) { return null; }
		};
	}

	@Test
	public void connectsUsingReplicaSetConfig() throws Exception {
		IMongoStoreConfiguration config = configWithReplicaSetConfig();
		MongoStore store = new MongoStore(config);
		MongoIterable<String> names = store.client.getDatabase("PING").listCollectionNames();
		store.close();
		assertNotNull(names);
	}
	
	@Test
	public void producesUriFromReplicaSetConfig() throws Exception {
		IMongoStoreConfiguration config = configWithReplicaSetConfig();
		String uri = MongoStore.uriFromConfig(config);
		String expected = "mongodb://ping:ping@seed-shard-00-00-cp8j5.mongodb.net:27017,"
				+ "seed-shard-00-01-cp8j5.mongodb.net:27017,"
				+ "seed-shard-00-02-cp8j5.mongodb.net:27017/"
				+ "PING?replicaSet=Seed-shard-0&ssl=true&authSource=admin";
		assertEquals(expected, uri);
	}

	
	private IMongoStoreConfiguration configWithReplicaSetConfig() {
		return new IMongoStoreConfiguration() {

			String replicaSetURI;
			
			@Override public String getFactory() { return null; }
			@Override public String getHost() { return null; }
			@Override public Integer getPort() { return null; }
			@Override public String getDbName() { return "PING"; }
			@Override public Boolean getAudit() { return false; }
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
						@Override public YamlElement toYaml() throws YamlException { return null; }
					}, new IHostConfiguration() {
						@Override public String getHost() { return "seed-shard-00-01-cp8j5.mongodb.net"; }
						@Override public Integer getPort() { return 27017; }
						@Override public YamlElement toYaml() throws YamlException { return null; }
					}, new IHostConfiguration() {
						@Override public String getHost() { return "seed-shard-00-02-cp8j5.mongodb.net"; }
						@Override public Integer getPort() { return 27017; }
						@Override public YamlElement toYaml() throws YamlException { return null; }
					});
				}
				@Override
				public boolean isSSL() { return true; }
				@Override
				public String getName() { return "Seed-shard-0"; }
				@Override
				public YamlMapping toYaml() throws YamlException { return null; }
			}; }
			@Override public IMongoStoreConfiguration withReplicaSetConfiguration(IMongoReplicaSetConfiguration config) { return null; }
		};
	}

	@Ignore
	@Test
	public void readsYamlReplicaSetConfig() throws Exception {
		try(InputStream input = new FileInputStream("/Users/ericvergnaud/Development/prompto/prompto-deploy/aws/deploy-prompto-seed.yml")) {
			IConfigurationReader reader = new YamlConfigurationReader(input);
			reader = reader.getObject("codeStore");
			MongoStoreConfiguration config = new MongoStoreConfiguration(reader);
			IMongoReplicaSetConfiguration rs = config.getReplicaSetConfiguration();
			assertNotNull(rs);
			assertEquals("Seed-shard-0", rs.getName());
			Iterable<IHostConfiguration> nodes = rs.getNodes();
			assertNotNull(nodes);
			AtomicInteger count = new AtomicInteger();
			nodes.forEach(n->{
				assertEquals(27017, n.getPort().intValue());
				assertTrue(n.getHost().startsWith("Seed-shard-"));
				assertTrue(n.getHost().endsWith("-cp8j5.mongodb.net"));
				count.incrementAndGet();
			});
			assertEquals(3, count.get());
		}
	}

}
