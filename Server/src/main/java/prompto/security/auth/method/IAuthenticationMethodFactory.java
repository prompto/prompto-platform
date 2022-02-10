package prompto.security.auth.method;

import org.eclipse.jetty.security.Authenticator;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IConfigurationReader;
import prompto.config.auth.method.IAuthenticationMethodConfiguration;

public interface IAuthenticationMethodFactory {

	static IAuthenticationMethodFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(IAuthenticationMethodFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not an authentication method factory: " + factoryName);
		return (IAuthenticationMethodFactory)klass.getDeclaredConstructor().newInstance();
	}

	IAuthenticationMethodConfiguration newConfiguration(IConfigurationReader reader);
	void setConfiguration(IAuthenticationMethodConfiguration config);
	Authenticator newAuthenticator(boolean withXAuthorization);
	default YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = new YamlMapping();
		yaml.setEntry("factory", this.getClass().getName());
		return yaml;
	}

}
