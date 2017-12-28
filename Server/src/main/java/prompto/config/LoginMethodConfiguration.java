package prompto.config;

public class LoginMethodConfiguration implements ILoginMethodConfiguration {

	IConfigurationReader reader;
	
	public LoginMethodConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

}
