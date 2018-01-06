package prompto.config;

import prompto.security.BasicAuthenticationMethodFactory;

public class AuthenticationConfiguration extends IAuthenticationConfiguration.Inline {

	IConfigurationReader reader;
	
	public AuthenticationConfiguration(IConfigurationReader reader) {
		this.reader = reader;
		this.authenticationSourceConfiguration = ()->readAuthenticationSourceConfiguration();
		this.authenticationMethodConfiguration = ()->readAuthenticationMethodConfiguration();
	}

	private IAuthenticationSourceConfiguration readAuthenticationSourceConfiguration() {
		IConfigurationReader child = reader.getObject("source");
		return child==null ? null : new AuthenticationSourceConfiguration(child);
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
