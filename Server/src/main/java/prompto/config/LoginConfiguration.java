package prompto.config;

import prompto.security.ILoginModuleFactory;

public class LoginConfiguration implements ILoginConfiguration {

	IConfigurationReader reader;
	
	public LoginConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public ILoginModuleFactory getLoginModuleFactory() {
		String factoryName = reader.getString("factory");
		if(factoryName==null)
			throw new IllegalArgumentException("Missing login module factory!");
		else try {
			ILoginModuleFactory factory = ILoginModuleFactory.newModuleFactory(factoryName);
			ILoginConfiguration config = factory.newConfiguration(reader);
			factory.setLoginConfiguration(config);
			return factory;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}
	


}
