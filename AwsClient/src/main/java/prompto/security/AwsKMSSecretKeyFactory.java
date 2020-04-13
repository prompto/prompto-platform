package prompto.security;

import prompto.aws.KMS;
import prompto.config.IConfigurationReader;
import prompto.config.ISecretKeyConfiguration;
import prompto.security.ISecretKeyFactory;
import prompto.utils.Logger;

public class AwsKMSSecretKeyFactory implements ISecretKeyFactory {

	private static Logger logger = new Logger();

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
		String awsAccessKey = config.getAwsAccesKey();
		String awsSecretKey = config.getAwsSecretKey();
		logger.debug(()->"AWS KMS - decrypting secret " + new String(config.getSecret()) + " in region: " + awsRegion + " with access key: " + awsAccessKey);
		KMS kms = KMS.newInstance(awsRegion, awsAccessKey, awsSecretKey);
		String plainText = kms.decrypt(new String(config.getSecret()));
		if("true".equals(System.getenv("DEBUG_AWS_KMS")))
			logger.debug(()->"AWS KMS: decrypted secret " + new String(config.getSecret()) + " to " + plainText);
		return plainText;
	}


}
