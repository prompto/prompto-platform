package prompto.config;

import prompto.security.IPasswordFactory;


public class KeyStoreConfiguration implements IKeyStoreConfiguration {

	IConfigurationReader reader;
	
	public KeyStoreConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public IKeyStoreConfigurator getConfigurator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPasswordFactory getPasswordFactory() {
		// TODO Auto-generated method stub
		return null;
	}


}
