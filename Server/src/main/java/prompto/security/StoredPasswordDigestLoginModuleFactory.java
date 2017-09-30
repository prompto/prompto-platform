package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginConfiguration;
import prompto.config.IStoredLoginConfiguration;
import prompto.config.StoredLoginConfiguration;

public class StoredPasswordDigestLoginModuleFactory implements ILoginModuleFactory {

	IStoredLoginConfiguration config;
	
	@Override
	public ILoginConfiguration newConfiguration(IConfigurationReader reader) {
		return new StoredLoginConfiguration(reader);
	}

	@Override
	public void setLoginConfiguration(ILoginConfiguration config) {
		this.config = (IStoredLoginConfiguration)config;
	}

	@Override
	public String installLoginModule() {
		String moduleName = StoredPasswordDigestLoginModule.class.getName();
		LoginModuleBase.install(moduleName, config);
		return moduleName;
	}



}
