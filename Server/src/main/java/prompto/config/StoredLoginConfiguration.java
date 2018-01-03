package prompto.config;


public class StoredLoginConfiguration extends LoginModuleSource implements IStoredLoginSourceConfiguration {

	public StoredLoginConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public IStoreConfiguration getStoreConfiguration() {
		return reader.readStoreConfiguration("store");
	}

}
