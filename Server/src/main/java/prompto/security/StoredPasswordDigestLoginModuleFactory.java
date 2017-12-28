package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginModuleConfiguration;
import prompto.config.IStoredLoginConfiguration;
import prompto.config.StoredLoginConfiguration;

public class StoredPasswordDigestLoginModuleFactory implements ILoginModuleFactory {

	IStoredLoginConfiguration config;
	
	@Override
	public ILoginModuleConfiguration newConfiguration(IConfigurationReader reader) {
		return new StoredLoginConfiguration(reader);
	}

	@Override
	public void setLoginConfiguration(ILoginModuleConfiguration config) {
		this.config = (IStoredLoginConfiguration)config;
	}

	@Override
	public String installLoginModule() {
		String moduleName = StoredPasswordDigestLoginModule.class.getName();
		LoginModuleBase.install(moduleName, config);
		return moduleName;
	}



}
