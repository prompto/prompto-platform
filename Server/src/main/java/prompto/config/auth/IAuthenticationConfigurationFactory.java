package prompto.config.auth;

import prompto.config.IConfigurationReader;

public interface IAuthenticationConfigurationFactory {

	static IAuthenticationConfigurationFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(IAuthenticationConfigurationFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not an authentication configuration factory: " + factoryName);
		return (IAuthenticationConfigurationFactory)klass.newInstance();
	}

	IAuthenticationConfiguration newConfiguration(IConfigurationReader child);

}
