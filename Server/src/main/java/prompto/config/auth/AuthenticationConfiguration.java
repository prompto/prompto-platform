package prompto.config.auth;

import java.util.Collection;

import prompto.config.IConfigurationReader;
import prompto.config.auth.method.AuthenticationMethodConfiguration;
import prompto.config.auth.method.IAuthenticationMethodConfiguration;
import prompto.config.auth.source.AuthenticationSourceConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.security.auth.method.BasicAuthenticationMethodFactory;
import prompto.security.auth.source.IAuthenticationSourceFactory;

public class AuthenticationConfiguration extends IAuthenticationConfiguration.Inline {

	IConfigurationReader reader;
	
	public AuthenticationConfiguration(IConfigurationReader reader) {
		this.reader = reader;
		this.authenticationSourceConfiguration = ()->readAuthenticationSourceConfiguration();
		this.authenticationMethodConfiguration = ()->readAuthenticationMethodConfiguration();
		this.whiteList = ()->readWhiteList();
	}

	private Collection<String> readWhiteList() {
		Collection<String> list = reader.getArray("whiteList");
		return list!=null ? list : DEFAULT_WHITE_LIST;
	}

	private IAuthenticationSourceConfiguration readAuthenticationSourceConfiguration() {
		IConfigurationReader child = reader.getObject("source");
		return child==null ? null : readAuthenticationSourceConfiguration(child);
	}
	
	private IAuthenticationSourceConfiguration readAuthenticationSourceConfiguration(IConfigurationReader reader) {
		String className = reader.getString("factory");
		if(className==null)
			return new AuthenticationSourceConfiguration(reader);
		else try {
			return IAuthenticationSourceFactory.newFactory(className).newConfiguration(reader);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
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
