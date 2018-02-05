package prompto.security;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

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
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = super.toYaml();
		yaml.setEntry("awsRegion", reader.getString("awsRegion"));
		yaml.setEntry("awsAccessKey", reader.getString("awsAccessKey"));
		yaml.setEntry("awsSecretKey", reader.getString("awsSecretKey"));
		return yaml;
	}
	
}