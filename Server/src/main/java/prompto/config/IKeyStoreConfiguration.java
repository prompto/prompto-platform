package prompto.config;

import prompto.security.IPasswordFactory;


public interface IKeyStoreConfiguration {

	IKeyStoreConfigurator getConfigurator();
	IPasswordFactory getPasswordFactory();

}
