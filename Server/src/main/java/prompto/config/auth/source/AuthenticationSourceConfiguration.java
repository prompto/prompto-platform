package prompto.config.auth.source;

import prompto.config.IConfigurationReader;
import prompto.security.auth.source.IAuthenticationSourceFactory;

public class AuthenticationSourceConfiguration implements IAuthenticationSourceConfiguration {

	IConfigurationReader reader;
	
	public AuthenticationSourceConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public IAuthenticationSourceFactory getAuthenticationSourceFactory() {
		String factoryName = reader.getString("factory");
		if(factoryName==null)
			throw new IllegalArgumentException("Missing authentication source factory!");
		else try {
			IAuthenticationSourceFactory factory = IAuthenticationSourceFactory.newFactory(factoryName);
			IAuthenticationSourceConfiguration config = factory.newConfiguration(reader);
			factory.setConfiguration(config);
			return factory;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}
	

}
