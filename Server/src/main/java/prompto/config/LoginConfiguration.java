package prompto.config;

import prompto.security.BasicLoginMethodFactory;
import prompto.security.ILoginMethodFactory;
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
	
	@Override
	public ILoginMethodFactory getLoginMethodFactory() {
		IConfigurationReader method = reader.getObject("method");
		if(method==null)
			return new BasicLoginMethodFactory();
		else try {
			String factoryName = method.getString("factory");
			if(factoryName==null)
				throw new IllegalArgumentException("Missing login module factory!");
			else try {
				ILoginMethodFactory factory = ILoginMethodFactory.newModuleFactory(factoryName);
				ILoginMethodConfiguration config = factory.newConfiguration(method);
				factory.setLoginMethodConfiguration(config);
				return factory;
			} catch(Throwable e) {
				throw new RuntimeException(e);
			}
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
