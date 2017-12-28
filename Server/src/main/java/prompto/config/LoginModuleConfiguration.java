package prompto.config;

import prompto.security.ILoginModuleFactory;

public class LoginModuleConfiguration implements ILoginModuleConfiguration {

	IConfigurationReader reader;
	
	public LoginModuleConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public ILoginModuleFactory getLoginModuleFactory() {
		String factoryName = reader.getString("factory");
		if(factoryName==null)
			throw new IllegalArgumentException("Missing login module factory!");
		else try {
			ILoginModuleFactory factory = ILoginModuleFactory.newModuleFactory(factoryName);
			ILoginModuleConfiguration config = factory.newConfiguration(reader);
			factory.setLoginConfiguration(config);
			return factory;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}
	

}
