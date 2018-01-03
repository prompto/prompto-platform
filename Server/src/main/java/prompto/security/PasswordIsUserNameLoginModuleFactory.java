package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginModuleConfiguration;
import prompto.config.LoginModuleConfiguration;

public class PasswordIsUserNameLoginModuleFactory implements ILoginModuleFactory {

	ILoginModuleConfiguration config;
	
	@Override
	public ILoginModuleConfiguration newConfiguration(IConfigurationReader reader) {
		return new LoginModuleConfiguration(reader);
	}

	@Override
	public void setConfiguration(ILoginModuleConfiguration config) {
		this.config = config;
	}

	@Override
	public String installLoginModule() {
		String moduleName = PasswordIsUserNameLoginModule.class.getName();
		LoginModuleBase.install(moduleName, config);
		return moduleName;
	}


}
