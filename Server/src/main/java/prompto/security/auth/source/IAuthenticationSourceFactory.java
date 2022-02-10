package prompto.security.auth.source;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IConfigurationReader;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;

public interface IAuthenticationSourceFactory {

	static IAuthenticationSourceFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(IAuthenticationSourceFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not an authentication source factory: " + factoryName);
		return (IAuthenticationSourceFactory)klass.getDeclaredConstructor().newInstance();
	}

	IAuthenticationSourceConfiguration newConfiguration(IConfigurationReader reader);
	void setConfiguration(IAuthenticationSourceConfiguration config);
	String installJettyLoginModule();
	String getJettyLoginModuleName();
	YamlMapping toYaml() throws YamlException;

	IAuthenticationSource newAuthenticationSource();


}
