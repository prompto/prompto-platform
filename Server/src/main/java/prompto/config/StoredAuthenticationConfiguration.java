package prompto.config;


public class StoredAuthenticationConfiguration extends AuthenticationModuleSource implements IStoredAuthenticationSourceConfiguration {

	public StoredAuthenticationConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public IStoreConfiguration getStoreConfiguration() {
		return reader.readStoreConfiguration("storeName");
	}

}
