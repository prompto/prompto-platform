package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.IAuthenticationSourceConfiguration;
import prompto.config.AuthenticationSourceConfiguration;

public class PasswordIsUserNameAuthenticationSourceFactory implements IAuthenticationSourceFactory {

	IAuthenticationSourceConfiguration config;
	
	@Override
	public IAuthenticationSourceConfiguration newConfiguration(IConfigurationReader reader) {
		return new AuthenticationSourceConfiguration(reader);
	}

	@Override
	public void setConfiguration(IAuthenticationSourceConfiguration config) {
		this.config = config;
	}

	@Override
	public String installJettyLoginModule() {
		String moduleName = PasswordIsUserNameAuthenticationSource.class.getName();
		JettyLoginModuleBase.install(moduleName, config);
		return moduleName;
	}
	

}
