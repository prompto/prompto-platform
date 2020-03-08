package prompto.config;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

public interface IKeyStoreConfiguration {

	IKeyStoreFactoryConfiguration getKeyStoreFactoryConfiguration();
	ISecretKeyConfiguration getSecretKeyConfiguration();
	YamlMapping toYaml() throws YamlException;

}
