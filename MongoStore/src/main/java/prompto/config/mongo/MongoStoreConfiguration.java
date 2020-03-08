package prompto.config.mongo;

import java.util.function.Supplier;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IConfigurationReader;
import prompto.config.StoreConfiguration;

public class MongoStoreConfiguration extends StoreConfiguration implements IMongoStoreConfiguration {

	Supplier<String> replicaSetURI;
	Supplier<IMongoReplicaSetConfiguration> replicaSetConfig;
	
	public MongoStoreConfiguration(IConfigurationReader reader) {
		super(reader);
		port = ()->reader.getIntegerOrDefault("port", 27017);
		replicaSetURI = ()->reader.getString("replicaSetURI");
		replicaSetConfig = ()->{
			IConfigurationReader child = reader.getObject("replicaSet");
			return child==null ? null : new MongoReplicaSetConfiguration(child);
		};
	}
	
	@Override
	public String getReplicaSetURI() {
		return replicaSetURI.get();
	}

	@Override
	public IMongoReplicaSetConfiguration getReplicaSetConfiguration() {
		return replicaSetConfig.get();
	}

	@Override
	public IMongoStoreConfiguration withReplicaSetURI(String uri) {
		replicaSetURI = ()->uri;
		return this;
	}

	@Override
	public IMongoStoreConfiguration withReplicaSetConfiguration(IMongoReplicaSetConfiguration config) {
		replicaSetConfig = ()->config;
		return this;
	}
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping mapping = super.toYaml();
		IMongoReplicaSetConfiguration config = replicaSetConfig.get();
		if(config!=null) {
			mapping.deleteEntry("host");
			mapping.deleteEntry("port");
			mapping.setEntry("replicaSet", config.toYaml());
		} else if(replicaSetURI.get()!=null) {
			mapping.deleteEntry("host");
			mapping.deleteEntry("port");
			mapping.setEntry("replicaSetURI", replicaSetURI.get());
		} 
		return mapping;
	}


	
}
