package prompto.config.solr;

import prompto.config.IConfigurationReader;
import prompto.config.IStoreConfiguration;
import prompto.config.StoreConfiguration;

public class SOLRStoreConfiguration extends StoreConfiguration implements ISOLRStoreConfiguration {

	public SOLRStoreConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public boolean isEmbedded() {
		return getDataRoot()!=null;
	}

	@Override
	public String getProtocol() {
		return reader.getStringOrDefault("protocol", "http");
	}
	
	@Override
	public Integer getPort() {
		return reader.getIntegerOrDefault("port", 8983);
	}

	@Override
	public String getDataRoot() {
		return reader.getString("root");
	}

	@Override
	public int getCommitDelay() {
		return reader.getIntegerOrDefault("commitDelay", 15000);
	}
	
	@Override
	public IStoreConfiguration withDbName(String dbName) {
		return new SOLRStoreConfiguration(reader) {
			@Override
			public String getDbName() {
				return dbName;
			}
		};
	}
	
}
