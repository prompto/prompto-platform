package prompto.config;

import prompto.security.BasicLoginMethodFactory;

public class LoginConfiguration extends ILoginConfiguration.Inline {

	IConfigurationReader reader;
	
	public LoginConfiguration(IConfigurationReader reader) {
		this.reader = reader;
		this.loginModuleConfiguration = ()->readLoginModuleConfiguration();
		this.loginMethodConfiguration = ()->readLoginMethodConfiguration();
	}

	private ILoginModuleConfiguration readLoginModuleConfiguration() {
		IConfigurationReader child = reader.getObject("module");
		return child==null ? null : new LoginModuleConfiguration(child);
	}
	
	private ILoginMethodConfiguration readLoginMethodConfiguration() {
		IConfigurationReader child = reader.getObject("method");
		// default to BASIC authentication method
		if(child==null)
			return ()->new BasicLoginMethodFactory();
		else
			return new LoginMethodConfiguration(child);
	}

}
