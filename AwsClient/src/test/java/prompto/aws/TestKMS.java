package prompto.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.config.ISecretKeyConfiguration;
import prompto.security.AwsKMSSecretKeyFactory;
import prompto.security.IAwsKMSSecretKeyConfiguration;
import prompto.security.ISecretKeyFactory;
import software.amazon.awssdk.regions.Region;

@Category(AwsTest.class)
public class TestKMS extends AWSTestBase {
		
	public static String MASTER_KEY_ARN = "arn:aws:kms:us-east-1:838901125615:key/fd4e13e3-72c3-41ee-90de-4531f5c4c93a";
	public static String MASTER_KEY_ALIAS = "prompto/seed";
	public static Region MASTER_KEY_REGION = Region.US_EAST_1;
			

	@Test
	public void roundtrips() {
		KMS awsKms = new KMS(kms);
		String plainKey = "password";
		String encryptedKey = awsKms.encrypt(MASTER_KEY_ARN, plainKey);
		String decryptedKey = awsKms.decrypt(encryptedKey);
		assertEquals(plainKey, decryptedKey);
	}
	
	@Test
	public void readsARNFromAlias() throws Throwable {
		KMS awsKms = new KMS(kms);
		String arn = awsKms.keyARNFromAlias(MASTER_KEY_ALIAS);
		assertEquals(MASTER_KEY_ARN, arn);
	}
	
	@Test
	public void createsAndDestroysARNWithAlias() throws Throwable {
		KMS awsKms = new KMS(kms);
		String alias = "prompo/test";
		String createdARN = awsKms.newKeyARNWithAlias(alias);
		assertNotNull(createdARN);
		String readARN = awsKms.keyARNFromAlias(alias);
		awsKms.deleteKeyARNWithAlias(alias);
		assertEquals(readARN, createdARN);
	}

	
	@Test
	public void AwsKMSSecretKeyFactoryConvertsPassword() throws Throwable {
		KMS awsKms = new KMS(kms);
		String encrypted = awsKms.encrypt(MASTER_KEY_ARN, "admin");
		System.out.println("Encrypted: " + encrypted);
		ISecretKeyConfiguration config = new IAwsKMSSecretKeyConfiguration() {

			@Override public String getFactory() { return AwsKMSSecretKeyFactory.class.getName(); }
			@Override public char[] getSecret() { return encrypted.toCharArray(); }
			@Override public String getAwsRegion() { return MASTER_KEY_REGION.id(); }
			@Override public String getAwsAccesKey() { return props.getProperty("accessKey"); }
			@Override public String getAwsSecretKey() { return props.getProperty("secretKey"); }
			
		};
		assertEquals("admin", ISecretKeyFactory.plainPasswordFromConfig(config));
	}

}
