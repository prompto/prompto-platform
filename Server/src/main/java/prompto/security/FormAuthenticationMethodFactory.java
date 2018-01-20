package prompto.security;

import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.FormAuthenticationMethodConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IFormAuthenticationMethodConfiguration;
import prompto.config.IAuthenticationMethodConfiguration;

public class FormAuthenticationMethodFactory implements IAuthenticationMethodFactory {

	IFormAuthenticationMethodConfiguration config;
	
	@Override
	public IAuthenticationMethodConfiguration newConfiguration(IConfigurationReader reader) {
		return new FormAuthenticationMethodConfiguration(reader);
	}

	@Override
	public void setConfiguration(IAuthenticationMethodConfiguration config) {
		this.config = (IFormAuthenticationMethodConfiguration)config;
	}

	@Override
	public Authenticator newAuthenticator(boolean withXAuthorization) {
		String loginPage = config.getLoginPage();
		if(!loginPage.startsWith("/"))
			loginPage = "/" + loginPage;
		String errorPage = config.getErrorPage();
		if(!errorPage.startsWith("/"))
			errorPage = "/" + errorPage;
		return new FormAuthenticator(loginPage, errorPage, true);
	}
	
	@Override
	public void toYaml(YamlMapping yaml) throws YamlException {
		IAuthenticationMethodFactory.super.toYaml(yaml);
		yaml.setEntry("loginPage", config.getLoginPage());
		yaml.setEntry("errorPage", config.getErrorPage());
	}

}
