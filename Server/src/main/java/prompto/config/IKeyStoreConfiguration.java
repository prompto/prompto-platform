package prompto.config;

import prompto.security.ISecretKeyFactory;


public interface IKeyStoreConfiguration {

	IKeyStoreConfigurator getConfigurator();
	ISecretKeyFactory getPasswordFactory();

}
