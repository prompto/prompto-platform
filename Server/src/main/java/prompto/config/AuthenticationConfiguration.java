package prompto.config;

import prompto.security.BasicAuthenticationMethodFactory;

public class AuthenticationConfiguration extends IAuthenticationConfiguration.Inline {

	IConfigurationReader reader;
	
	public AuthenticationConfiguration(IConfigurationReader reader) {
		this.reader = reader;
		this.authenticationSourceConfiguration = ()->readAuthenticationModuleConfiguration();
		this.authenticationMethodConfiguration = ()->readAuthenticationMethodConfiguration();
	}

	private IAuthenticationSourceConfiguration readAuthenticationModuleConfiguration() {
		IConfigurationReader child = reader.getObject("module");
		return child==null ? null : new AuthenticationModuleSource(child);
	}
	
	private IAuthenticationMethodConfiguration readAuthenticationMethodConfiguration() {
		IConfigurationReader child = reader.getObject("method");
		// default to BASIC authentication method
		if(child==null)
			return ()->new BasicAuthenticationMethodFactory();
		else
			return new AuthenticationMethodConfiguration(child);
	}

}
