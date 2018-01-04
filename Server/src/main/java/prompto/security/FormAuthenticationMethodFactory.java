package prompto.security;

import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;

import prompto.config.FormAuthenticationMethodConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IFormAuthenticationMethodConfiguration;
import prompto.config.IAuthenticationMethodConfiguration;

public class FormAuthenticationMethodFactory implements IAuthenticationMethodFactory {

	IFormAuthenticationMethodConfiguration config;
	
	@Override
	public IAuthenticationMethodConfiguration newConfiguration(IConfigurationReader reader) {
		return new FormAuthenticationMethodConfiguration(reader);
	}

	@Override
	public void setConfiguration(IAuthenticationMethodConfiguration config) {
		this.config = (IFormAuthenticationMethodConfiguration)config;
	}

	@Override
	public Authenticator newAuthenticator(boolean withXAuthorization) {
		return new FormAuthenticator(config.getLoginPage(), config.getErrorPage(), true);
	}

}
