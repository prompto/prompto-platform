package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginModuleConfiguration;

public class PasswordIsUserNameLoginModuleFactory implements ILoginModuleFactory {

	@Override
	public ILoginModuleConfiguration newConfiguration(IConfigurationReader reader) {
		return null; // no config
	}

	@Override
	public void setLoginConfiguration(ILoginModuleConfiguration config) {
		// nothing to do
	}

	@Override
	public String installLoginModule() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
