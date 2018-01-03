package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginModuleConfiguration;
import prompto.config.IStoredLoginModuleConfiguration;
import prompto.config.StoredLoginConfiguration;

public class StoredPasswordDigestLoginModuleFactory implements ILoginModuleFactory {

	IStoredLoginModuleConfiguration config;
	
	@Override
	public ILoginModuleConfiguration newConfiguration(IConfigurationReader reader) {
		return new StoredLoginConfiguration(reader);
	}

	@Override
	public void setConfiguration(ILoginModuleConfiguration config) {
		this.config = (IStoredLoginModuleConfiguration)config;
	}

	@Override
	public String installLoginModule() {
		String moduleName = StoredPasswordDigestLoginModule.class.getName();
		LoginModuleBase.install(moduleName, config);
		return moduleName;
	}



}
