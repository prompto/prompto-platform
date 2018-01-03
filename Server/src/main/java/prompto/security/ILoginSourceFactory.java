package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginSourceConfiguration;

public interface ILoginSourceFactory {

	static ILoginSourceFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(ILoginSourceFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not a login module factory: " + factoryName);
		return (ILoginSourceFactory)klass.newInstance();
	}

	ILoginSourceConfiguration newConfiguration(IConfigurationReader reader);
	void setConfiguration(ILoginSourceConfiguration config);
	String installLoginModule();


}
