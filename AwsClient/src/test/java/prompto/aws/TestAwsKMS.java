package prompto.aws;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.Base64;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;

@Category(AwsTest.class)
public class TestAwsKMS extends AWSTestBase {
	
	public static String MASTER_KEY_ARN = "arn:aws:kms:us-west-2:838901125615:key/8a9e7c55-0803-46cf-8c62-bd9c4c6097e5";
	
	@Test
	public void roundtrip() {
		String plainKey = "password";
		ByteBuffer keyBytes = ByteBuffer.wrap(plainKey.getBytes());
		EncryptRequest req = new EncryptRequest().withKeyId(MASTER_KEY_ARN).withPlaintext(keyBytes);
		EncryptResult res = kms.encrypt(req);
		ByteBuffer encryptedBytes = res.getCiphertextBlob();
		String encryptedKey = Base64.getEncoder().encodeToString(encryptedBytes.array());
		System.out.println("Encrypted: " + encryptedKey);
		encryptedBytes = ByteBuffer.wrap(Base64.getDecoder().decode(encryptedKey));
		DecryptRequest req2 = new DecryptRequest().withCiphertextBlob(encryptedBytes);
		DecryptResult res2 = kms.decrypt(req2);
		keyBytes = res2.getPlaintext();
		String decryptedKey = new String(keyBytes.array());
		assertEquals(plainKey, decryptedKey);
	}

}
