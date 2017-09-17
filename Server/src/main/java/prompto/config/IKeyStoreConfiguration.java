package prompto.config;


public interface IKeyStoreConfiguration {

	IKeyStoreConfigurator getConfigurator();
	IPasswordFactory getPasswordFactory();

}
