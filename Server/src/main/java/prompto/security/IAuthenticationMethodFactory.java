package prompto.security;

import org.eclipse.jetty.security.Authenticator;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IConfigurationReader;
import prompto.config.IAuthenticationMethodConfiguration;

public interface IAuthenticationMethodFactory {

	static IAuthenticationMethodFactory newFactory(String factoryName) throws Throwable {
		Class<?> klass = Class.forName(factoryName, true, Thread.currentThread().getContextClassLoader());
		if(!(IAuthenticationMethodFactory.class.isAssignableFrom(klass)))
			throw new RuntimeException("Not an authentication method factory: " + factoryName);
		return (IAuthenticationMethodFactory)klass.newInstance();
	}

	IAuthenticationMethodConfiguration newConfiguration(IConfigurationReader reader);
	void setConfiguration(IAuthenticationMethodConfiguration config);
	Authenticator newAuthenticator(boolean withXAuthorization);
	default void toYaml(YamlMapping yaml) throws YamlException {
		yaml.setEntry("factory", this.getClass().getName());
	}

}
