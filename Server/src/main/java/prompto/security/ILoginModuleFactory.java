package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginModuleConfiguration;

public interface ILoginModuleFactory {

	static ILoginModuleFactory newModuleFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(ILoginModuleFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not a login module factory: " + factoryName);
		return (ILoginModuleFactory)klass.newInstance();
	}

	ILoginModuleConfiguration newConfiguration(IConfigurationReader reader);
	void setLoginConfiguration(ILoginModuleConfiguration config);
	String installLoginModule();


}
