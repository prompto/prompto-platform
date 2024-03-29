package prompto.config.auth.source;

import java.io.ByteArrayInputStream;

import prompto.config.IConfigurationReader;
import prompto.config.IStoreConfiguration;
import prompto.config.YamlConfigurationReader;
import prompto.security.auth.source.StoredPasswordDigestAuthenticationSourceFactory;
import prompto.store.AttributeInfo;
import prompto.store.DataStore;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStore;
import prompto.store.IStored;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;


public class StoredAuthenticationSourceConfiguration extends AuthenticationSourceConfiguration implements IStoredAuthenticationSourceConfiguration {

	public StoredAuthenticationSourceConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public IStoreConfiguration getStoreConfiguration() {
		if(reader.hasKey("storeName"))
			return fetchStoreConfigurationFromStoredRecord(DataStore.getInstance());
		else if(reader.hasKey("store"))
			return reader.readStoreConfiguration("store");
		else
			return null;
	}
	
	private IStoreConfiguration fetchStoreConfigurationFromStoredRecord(IStore store) { 
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
	public YamlMapping toYaml() throws YamlException {
		IStoreConfiguration store = getStoreConfiguration();
		if(store!=null) {
			YamlMapping yaml = new YamlMapping();
			yaml.setEntry("factory", StoredPasswordDigestAuthenticationSourceFactory.class.getName());
			yaml.setEntry("store", store.toYaml());
			return yaml;
		} else
			return null;
	}

}
