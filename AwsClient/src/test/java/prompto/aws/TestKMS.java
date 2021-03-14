package prompto.aws;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Base64;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.config.ISecretKeyConfiguration;
import prompto.security.AwsKMSSecretKeyFactory;
import prompto.security.IAwsKMSSecretKeyConfiguration;
import prompto.security.ISecretKeyFactory;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kms.model.AliasListEntry;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;
import software.amazon.awssdk.services.kms.model.ListAliasesResponse;

@Category(AwsTest.class)
public class TestKMS extends AWSTestBase {
		
	@Test
	public void roundtrip() {
		String plainKey = "password";
		String encryptedKey = encrypt(plainKey);
		System.out.println("Encrypted: " + encryptedKey);
		String decryptedKey = decrypt(encryptedKey);
		assertEquals(plainKey, decryptedKey);
	}

	@Test
	public void aliasCanBeRead() throws Throwable {
		String arn = arnFromAlias(MASTER_KEY_ALIAS);
		assertEquals(MASTER_KEY_ARN, arn);
	}
	
	@Test
	public void awsKMSPasswordFactoryReturnsPlainPassword() throws Throwable {
		String encrypted = encrypt("admin");
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

	protected String arnFromAlias(String alias) {
		final String fullAlias = "alias/" + alias;
		ListAliasesResponse res = kms.listAliases();
		AliasListEntry entry = res.aliases().stream()
				.filter(a->fullAlias.equals(a.aliasName()))
				.findFirst()
				.orElse(null);
		if(entry==null)
			return null;
		String prefix = entry.aliasArn();
		prefix = prefix.substring(0, prefix.indexOf(":" + fullAlias));
		return prefix + ":key/" + entry.targetKeyId(); 
	}
	
	protected String encrypt(String plainKey) {
		ByteBuffer keyBytes = ByteBuffer.wrap(plainKey.getBytes());
		EncryptRequest req = EncryptRequest.builder().keyId(MASTER_KEY_ARN).plaintext(SdkBytes.fromByteBuffer(keyBytes)).build();
		EncryptResponse res = kms.encrypt(req);
		return Base64.getEncoder().encodeToString(res.ciphertextBlob().asByteArray());
	}
	
	protected String decrypt(String encryptedKey) {
		ByteBuffer encryptedBytes = ByteBuffer.wrap(Base64.getDecoder().decode(encryptedKey));
		DecryptRequest req2 = DecryptRequest.builder().ciphertextBlob(SdkBytes.fromByteBuffer(encryptedBytes)).build();
		DecryptResponse res2 = kms.decrypt(req2);
		return new String(res2.plaintext().asByteArray());
	}

}
