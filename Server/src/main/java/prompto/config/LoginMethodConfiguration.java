package prompto.config;

import prompto.security.ILoginMethodFactory;

public class LoginMethodConfiguration implements ILoginMethodConfiguration {

	IConfigurationReader reader;
	
	public LoginMethodConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public ILoginMethodFactory getLoginMethodFactory() {
		String factoryName = reader.getString("factory");
		if(factoryName==null)
			throw new IllegalArgumentException("Missing login module factory!");
		else try {
			ILoginMethodFactory factory = ILoginMethodFactory.newModuleFactory(factoryName);
			ILoginMethodConfiguration config = factory.newConfiguration(reader);
			factory.setLoginMethodConfiguration(config);
			return factory;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}

}