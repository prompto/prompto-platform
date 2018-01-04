package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.IAuthenticationSourceConfiguration;
import prompto.config.IStoredAuthenticationSourceConfiguration;
import prompto.config.StoredAuthenticationConfiguration;

public class StoredPasswordDigestAuthenticationSourceFactory implements IAuthenticationSourceFactory {

	IStoredAuthenticationSourceConfiguration config;
	
	@Override
	public IAuthenticationSourceConfiguration newConfiguration(IConfigurationReader reader) {
		return new StoredAuthenticationConfiguration(reader);
	}

	@Override
	public void setConfiguration(IAuthenticationSourceConfiguration config) {
		this.config = (IStoredAuthenticationSourceConfiguration)config;
	}

	@Override
	public String installJettyLoginModule() {
		String moduleName = StoredPasswordDigestAuthenticationSource.class.getName();
		JettyLoginModuleBase.install(moduleName, config);
		return moduleName;
	}



}
