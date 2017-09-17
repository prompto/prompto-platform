package prompto.security;

import prompto.config.IConfigurationReader;
import prompto.config.SecretKeyConfiguration;

public class AwsKMSSecretKeyConfiguration extends SecretKeyConfiguration implements IAwsKMSSecretKeyConfiguration {

	public AwsKMSSecretKeyConfiguration(IConfigurationReader reader) {
		super(reader);
	}

	@Override
	public String getAwsRegion() {
		return reader.getString("awsRegion");
	}

	@Override
	public String getAwsAccesKey() {
		return reader.getString("awsAccessKey");
	}

	@Override
	public String getAwsSecretKey() {
		return reader.getString("awsSecretKey");
	}
	
}