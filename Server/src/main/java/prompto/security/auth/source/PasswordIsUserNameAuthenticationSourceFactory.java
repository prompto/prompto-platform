package prompto.security.auth.source;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IConfigurationReader;
import prompto.config.auth.source.AuthenticationSourceConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.security.auth.JettyLoginModuleBase;

public class PasswordIsUserNameAuthenticationSourceFactory implements IAuthenticationSourceFactory {

	IAuthenticationSourceConfiguration config;
	
	@Override
	public IAuthenticationSourceConfiguration newConfiguration(IConfigurationReader reader) {
		return new AuthenticationSourceConfiguration(reader);
	}

	@Override
	public void setConfiguration(IAuthenticationSourceConfiguration config) {
		this.config = config;
	}

	@Override
	public String installJettyLoginModule() {
		String moduleName = PasswordIsUserNameAuthenticationSource.class.getName();
		JettyLoginModuleBase.install(moduleName, config);
		return moduleName;
	}
	
	@Override
	public IAuthenticationSource newAuthenticationSource() {
		return new PasswordIsUserNameAuthenticationSource();
	}
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = new YamlMapping();
		yaml.setEntry("factory", PasswordIsUserNameAuthenticationSourceFactory.class.getName());
		return yaml;
	}
	

}
