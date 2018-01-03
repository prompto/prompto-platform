package prompto.config;

public class CodeStoreLoginConfigurationFactory implements ILoginConfigurationFactory {

	@Override
	public ILoginConfiguration newConfiguration(IConfigurationReader child) {
		return new CodeStoreLoginConfiguration(child);
	}

}
