package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.ILoginSourceConfiguration;
import prompto.config.IStoredLoginSourceConfiguration;
import prompto.config.StoredLoginConfiguration;

public class StoredPasswordDigestLoginSourceFactory implements ILoginSourceFactory {

	IStoredLoginSourceConfiguration config;
	
	@Override
	public ILoginSourceConfiguration newConfiguration(IConfigurationReader reader) {
		return new StoredLoginConfiguration(reader);
	}

	@Override
	public void setConfiguration(ILoginSourceConfiguration config) {
		this.config = (IStoredLoginSourceConfiguration)config;
	}

	@Override
	public String installLoginModule() {
		String moduleName = StoredPasswordDigestLoginSource.class.getName();
		LoginSourceBase.install(moduleName, config);
		return moduleName;
	}



}
