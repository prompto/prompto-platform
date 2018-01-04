package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.IAuthenticationSourceConfiguration;

public interface IAuthenticationSourceFactory {

	static IAuthenticationSourceFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(IAuthenticationSourceFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not an authentication source factory: " + factoryName);
		return (IAuthenticationSourceFactory)klass.newInstance();
	}

	IAuthenticationSourceConfiguration newConfiguration(IConfigurationReader reader);
	void setConfiguration(IAuthenticationSourceConfiguration config);
	String installJettyLoginModule();


}
