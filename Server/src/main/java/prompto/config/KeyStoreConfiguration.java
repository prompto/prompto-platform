package prompto.config;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

public class KeyStoreConfiguration implements IKeyStoreConfiguration {

	IConfigurationReader reader;
	
	public KeyStoreConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}
	
	@Override
	public IKeyStoreFactoryConfiguration getKeyStoreFactoryConfiguration() {
		return reader.readKeyStoreFactoryConfiguration("provider");
	};
	
	@Override
	public ISecretKeyConfiguration getSecretKeyConfiguration() {
		return reader.readSecretKeyConfiguration("secretKey");
	}
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = new YamlMapping();
		IKeyStoreFactoryConfiguration provider = getKeyStoreFactoryConfiguration();
		if(provider!=null)
			yaml.setEntry("provider", provider.toYaml());
		ISecretKeyConfiguration secretKey = getSecretKeyConfiguration();
		if(secretKey!=null)
			yaml.setEntry("secretKey", secretKey.toYaml());
		return yaml;
	}
}
