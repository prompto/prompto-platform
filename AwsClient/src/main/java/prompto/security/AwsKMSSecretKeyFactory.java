package prompto.security;

import prompto.aws.KMS;
import prompto.config.IConfigurationReader;
import prompto.config.ISecretKeyConfiguration;
import prompto.security.ISecretKeyFactory;

public class AwsKMSSecretKeyFactory implements ISecretKeyFactory {

	IAwsKMSSecretKeyConfiguration config;
	
	public AwsKMSSecretKeyFactory() {
	}
	
	public AwsKMSSecretKeyFactory(ISecretKeyConfiguration config) {
		this.config = (IAwsKMSSecretKeyConfiguration) config;
	}
	
	@Override
	public ISecretKeyConfiguration newConfiguration(IConfigurationReader reader) {
		return new AwsKMSSecretKeyConfiguration(reader);
	}

	@Override
	public String getAsPlainText() {
		String awsRegion = config.getAwsRegion();
		KMS kms = KMS.newInstance(awsRegion, null, null);
		return kms.decrypt(new String(config.getSecretKey()));
	}


}
