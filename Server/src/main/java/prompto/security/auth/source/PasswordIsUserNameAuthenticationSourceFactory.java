package prompto.security.auth.source;

import prompto.config.IConfigurationReader;
import prompto.config.auth.source.AuthenticationSourceConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.security.auth.JettyLoginModuleBase;

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
