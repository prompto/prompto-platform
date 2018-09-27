package prompto.config.auth.method;

import prompto.config.IConfigurationReader;
import prompto.security.auth.method.IAuthenticationMethodFactory;

public class AuthenticationMethodConfiguration implements IAuthenticationMethodConfiguration {

	IConfigurationReader reader;
	
	public AuthenticationMethodConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public IAuthenticationMethodFactory getAuthenticationMethodFactory() {
		String factoryName = reader.getString("factory");
		if(factoryName==null)
			throw new IllegalArgumentException("Missing authentication method factory!");
		else 
			return getAuthenticationMethodFactory(factoryName);
	}
	
	public IAuthenticationMethodFactory getAuthenticationMethodFactory(String factoryName) {
		try {
			IAuthenticationMethodFactory factory = IAuthenticationMethodFactory.newFactory(factoryName);
			IAuthenticationMethodConfiguration config = factory.newConfiguration(reader);
			factory.setConfiguration(config);
			return factory;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
