package prompto.config;

import prompto.security.ILoginSourceFactory;

public class LoginModuleSource implements ILoginSourceConfiguration {

	IConfigurationReader reader;
	
	public LoginModuleSource(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public ILoginSourceFactory getLoginSourceFactory() {
		String factoryName = reader.getString("factory");
		if(factoryName==null)
			throw new IllegalArgumentException("Missing login module factory!");
		else try {
			ILoginSourceFactory factory = ILoginSourceFactory.newFactory(factoryName);
			ILoginSourceConfiguration config = factory.newConfiguration(reader);
			factory.setConfiguration(config);
			return factory;
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}
	

}
