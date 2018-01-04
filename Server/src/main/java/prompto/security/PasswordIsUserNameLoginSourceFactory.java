package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginSourceConfiguration;
import prompto.config.LoginModuleSource;

public class PasswordIsUserNameLoginSourceFactory implements ILoginSourceFactory {

	ILoginSourceConfiguration config;
	
	@Override
	public ILoginSourceConfiguration newConfiguration(IConfigurationReader reader) {
		return new LoginModuleSource(reader);
	}

	@Override
	public void setConfiguration(ILoginSourceConfiguration config) {
		this.config = config;
	}

	@Override
	public String installLoginModule() {
		String moduleName = PasswordIsUserNameLoginSource.class.getName();
		LoginSourceBase.install(moduleName, config);
		return moduleName;
	}
	

}
