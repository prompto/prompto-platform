package prompto.config;

import java.io.ByteArrayInputStream;

import prompto.store.AttributeInfo;
import prompto.store.IDataStore;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStore;
import prompto.store.IStored;

import com.esotericsoftware.yamlbeans.document.YamlMapping;


public class StoredAuthenticationConfiguration extends AuthenticationSourceConfiguration implements IStoredAuthenticationSourceConfiguration {

	public StoredAuthenticationConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public IStoreConfiguration getStoreConfiguration() {
		return loadStoreConfiguration(IDataStore.getInstance());
	}
	
	private IStoreConfiguration loadStoreConfiguration(IStore store) { 
		String storeName = reader.getString("storeName");
		IStored stored = fetchStoreRecord(store, storeName);
		String dbName = (String)stored.getData("dbName");
		String serverName = (String)stored.getData("dbServer");
		stored = fetchServerRecord(store, serverName);
		String yaml = (String)stored.getData("config");
		return new YamlConfigurationReader(new ByteArrayInputStream(yaml.getBytes()))
				.readStoreConfiguration()
				.withDbName(dbName);
	}

	private IStored fetchServerRecord(IStore store, String serverName) {
		IQuery query = store.newQueryBuilder()
				.verify(AttributeInfo.CATEGORY, MatchOp.CONTAINS, "DataServer")
				.verify(AttributeInfo.NAME, MatchOp.EQUALS, serverName)
				.and()
				.build();
		return store.fetchOne(query);
	}

	private IStored fetchStoreRecord(IStore store, String storeName) {
		IQuery query = store.newQueryBuilder()
				.verify(AttributeInfo.CATEGORY, MatchOp.CONTAINS, "DataStore")
				.verify(AttributeInfo.NAME, MatchOp.EQUALS, storeName)
				.and()
				.build();
		return store.fetchOne(query);
	}

	@Override
	public void toYaml(YamlMapping yaml) throws Throwable {
		yaml.setEntry("storeName", reader.getString("storeName"));
	}

}
