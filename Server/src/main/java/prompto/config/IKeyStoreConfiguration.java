package prompto.config;



public interface IKeyStoreConfiguration {

	IKeyStoreFactoryConfiguration getKeyStoreFactoryConfiguration();
	ISecretKeyConfiguration getSecretKeyConfiguration();

}
