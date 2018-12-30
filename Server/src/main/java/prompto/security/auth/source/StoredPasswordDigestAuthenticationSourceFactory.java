package prompto.security.auth.source;

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
		String moduleName = StoredPasswordDigestAuthenticationSource.class.getName();
		JettyLoginModuleBase.install(moduleName, config);
		return moduleName;
	}
	
	@Override
	public IAuthenticationSource newAuthenticationSource() {
		return new StoredPasswordDigestAuthenticationSource(config);
	}
	
	@Override
	public YamlMapping toYaml() throws Throwable {
		YamlMapping yaml = IAuthenticationSourceFactory.super.toYaml();
		config.toYaml(yaml);
		return yaml;
	}



}
