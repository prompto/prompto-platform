package prompto.security;

import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;

import prompto.config.IConfigurationReader;
import prompto.config.IAuthenticationMethodConfiguration;

public class BasicAuthenticationMethodFactory implements IAuthenticationMethodFactory {

	@Override
	public IAuthenticationMethodConfiguration newConfiguration(IConfigurationReader reader) {
		return null; // no config
	}

	@Override
	public void setConfiguration(IAuthenticationMethodConfiguration config) {
		// nothing to do
	}

	@Override
	public Authenticator newAuthenticator(boolean withXAuthorization) {
		if(withXAuthorization)
			return new BasicAuthenticatorWithXAuthorization();
		else
			return new BasicAuthenticator();
	}

	
}
