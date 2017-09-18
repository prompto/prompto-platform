package prompto.aws;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.config.ISecretKeyConfiguration;
import prompto.security.AwsKMSSecretKeyFactory;
import prompto.security.IAwsKMSSecretKeyConfiguration;
import prompto.security.ISecretKeyFactory;

@Category(AwsTest.class)
public class TestAwsKMS extends AWSTestBase {
		
	@Test
	public void roundtrip() {
		String plainKey = "password";
		String encryptedKey = encrypt(plainKey);
		System.out.println("Encrypted: " + encryptedKey);
		String decryptedKey = decrypt(encryptedKey);
		assertEquals(plainKey, decryptedKey);
	}

	@Test
	public void testThatAwsKMSPasswordFactoryReturnsPlainPassword() throws Throwable {
		String encrypted = encrypt("password");
		ISecretKeyConfiguration config = new IAwsKMSSecretKeyConfiguration() {

			@Override public String getFactory() { return AwsKMSSecretKeyFactory.class.getName(); }
			@Override public char[] getSecret() { return encrypted.toCharArray(); }
			@Override public String getAwsRegion() { return null; }
			@Override public String getAwsAccesKey() { return props.getProperty("accessKey"); }
			@Override public String getAwsSecretKey() { return props.getProperty("secretKey"); }
			
		};
		assertEquals("password", ISecretKeyFactory.plainPasswordFromConfig(config));
	}

}
