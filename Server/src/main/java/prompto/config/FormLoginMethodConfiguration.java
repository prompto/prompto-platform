package prompto.config;

public class FormLoginMethodConfiguration extends LoginMethodConfiguration implements IFormLoginMethodConfiguration {

	public FormLoginMethodConfiguration(IConfigurationReader reader) {
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
