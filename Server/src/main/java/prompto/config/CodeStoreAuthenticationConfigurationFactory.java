package prompto.config;

public class CodeStoreAuthenticationConfigurationFactory implements IAuthenticationConfigurationFactory {

	@Override
	public IAuthenticationConfiguration newConfiguration(IConfigurationReader child) {
		return new CodeStoreAuthenticationConfiguration(child);
	}

}
