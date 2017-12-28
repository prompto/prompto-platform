package prompto.security;

import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginMethodConfiguration;

public class BasicLoginMethodFactory implements ILoginMethodFactory {

	@Override
	public ILoginMethodConfiguration newConfiguration(IConfigurationReader reader) {
		return new ILoginMethodConfiguration() {};
	}

	@Override
	public void setLoginMethodConfiguration(ILoginMethodConfiguration config) {
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
