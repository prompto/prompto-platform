package prompto.security;

import org.eclipse.jetty.security.Authenticator;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginMethodConfiguration;

public interface ILoginMethodFactory {

	static ILoginMethodFactory newModuleFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(ILoginMethodFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not a login method factory: " + factoryName);
		return (ILoginMethodFactory)klass.newInstance();
	}

	ILoginMethodConfiguration newConfiguration(IConfigurationReader reader);
	void setLoginMethodConfiguration(ILoginMethodConfiguration config);
	Authenticator newAuthenticator(boolean withXAuthorization);

}
