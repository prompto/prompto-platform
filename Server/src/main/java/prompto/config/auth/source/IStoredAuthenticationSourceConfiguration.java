package prompto.config.auth.source;

import prompto.config.IStoreConfiguration;

import com.esotericsoftware.yamlbeans.document.YamlMapping;

public interface IStoredAuthenticationSourceConfiguration extends IAuthenticationSourceConfiguration {

	IStoreConfiguration getStoreConfiguration();
	void toYaml(YamlMapping yaml) throws Throwable;
	
}
