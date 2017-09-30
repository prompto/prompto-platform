package prompto.config;


public class StoredLoginConfiguration extends LoginConfiguration implements IStoredLoginConfiguration {

	public StoredLoginConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public IStoreConfiguration getStoreConfiguration() {
		return reader.readStoreConfiguration("store");
	}

}
