package prompto.config.auth;

import prompto.config.IConfigurationReader;

public class CodeStoreAuthenticationConfigurationFactory implements IAuthenticationConfigurationFactory {

	@Override
	public IAuthenticationConfiguration newConfiguration(IConfigurationReader child) {
		return new CodeStoreAuthenticationConfiguration(child);
	}

}
