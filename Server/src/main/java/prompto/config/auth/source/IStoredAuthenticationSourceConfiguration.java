package prompto.config.auth.source;

import prompto.config.IStoreConfiguration;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

public interface IStoredAuthenticationSourceConfiguration extends IAuthenticationSourceConfiguration {

	IStoreConfiguration getStoreConfiguration();
	YamlMapping toYaml() throws YamlException;
	
}
