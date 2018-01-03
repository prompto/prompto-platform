package prompto.config;

public interface ILoginConfigurationFactory {

	static ILoginConfigurationFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(ILoginConfigurationFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not a login configuration factory: " + factoryName);
		return (ILoginConfigurationFactory)klass.newInstance();
	}

	ILoginConfiguration newConfiguration(IConfigurationReader child);

}
