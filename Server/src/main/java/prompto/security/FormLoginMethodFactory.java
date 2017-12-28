package prompto.security;

import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;

import prompto.config.FormLoginMethodConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IFormLoginMethodConfiguration;
import prompto.config.ILoginMethodConfiguration;

public class FormLoginMethodFactory implements ILoginMethodFactory {

	IFormLoginMethodConfiguration config;
	
	@Override
	public ILoginMethodConfiguration newConfiguration(IConfigurationReader reader) {
		return new FormLoginMethodConfiguration(reader);
	}

	@Override
	public void setLoginMethodConfiguration(ILoginMethodConfiguration config) {
		this.config = (IFormLoginMethodConfiguration)config;
	}

	@Override
	public Authenticator newAuthenticator(boolean withXAuthorization) {
		return new FormAuthenticator(config.getLoginPage(), config.getErrorPage(), true);
	}

}
