package prompto.security.auth.source;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IConfigurationReader;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.config.auth.source.IStoredAuthenticationSourceConfiguration;
import prompto.config.auth.source.StoredAuthenticationSourceConfiguration;
import prompto.security.auth.JettyLoginModuleBase;

public class StoredPasswordDigestAuthenticationSourceFactory implements IAuthenticationSourceFactory {

	IStoredAuthenticationSourceConfiguration config;
	
	@Override
	public IAuthenticationSourceConfiguration newConfiguration(IConfigurationReader reader) {
		return new StoredAuthenticationSourceConfiguration(reader);
	}

	@Override
	public void setConfiguration(IAuthenticationSourceConfiguration config) {
		this.config = (IStoredAuthenticationSourceConfiguration)config;
	}

	@Override
	public String installJettyLoginModule() {
		String moduleName = getJettyLoginModuleName();
		JettyLoginModuleBase.install(moduleName, config);
		return moduleName;
	}
	
	@Override
	public String getJettyLoginModuleName() {
		return StoredPasswordDigestAuthenticationSource.class.getName();
	}
	
	@Override
	public IAuthenticationSource newAuthenticationSource() {
		return new StoredPasswordDigestAuthenticationSource(config);
	}
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		return config.toYaml();
	}



}
