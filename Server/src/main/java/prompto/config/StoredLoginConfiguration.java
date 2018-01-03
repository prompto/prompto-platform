package prompto.config;


public class StoredLoginConfiguration extends LoginModuleConfiguration implements IStoredLoginModuleConfiguration {

	public StoredLoginConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public IStoreConfiguration getStoreConfiguration() {
		return reader.readStoreConfiguration("store");
	}

}
