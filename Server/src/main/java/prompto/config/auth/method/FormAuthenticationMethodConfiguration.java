package prompto.config.auth.method;

import prompto.config.IConfigurationReader;

public class FormAuthenticationMethodConfiguration extends AuthenticationMethodConfiguration implements IFormAuthenticationMethodConfiguration {

	public FormAuthenticationMethodConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public String getLoginPage() {
		return reader.getString("loginPage");
	}

	@Override
	public String getErrorPage() {
		return reader.getString("errorPage");
	}

}
