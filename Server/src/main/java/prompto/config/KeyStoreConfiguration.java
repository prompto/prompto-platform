package prompto.config;



public class KeyStoreConfiguration implements IKeyStoreConfiguration {

	IConfigurationReader reader;
	
	public KeyStoreConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}
	
	@Override
	public IKeyStoreFactoryConfiguration getKeyStoreFactoryConfiguration() {
		return reader.readKeyStoreFactoryConfiguration("provider");
	};
	
	@Override
	public ISecretKeyConfiguration getSecretKeyConfiguration() {
		return reader.readSecretKeyConfiguration("secretKey");
	}
}
