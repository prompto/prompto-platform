package prompto.aws;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Properties;

import org.junit.Before;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.AliasListEntry;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.services.kms.model.ListAliasesResult;

public abstract class AWSTestBase {

	public static String MASTER_KEY_ARN = "arn:aws:kms:us-west-2:838901125615:key/8a9e7c55-0803-46cf-8c62-bd9c4c6097e5";
	public static String MASTER_KEY_ALIAS = "prompto/seed";
			
	AmazonEC2 ec2;
	AWSKMS kms;
	Properties props = new Properties();
	
	@Before
	public void before() throws Exception {
		try (InputStream input = new FileInputStream(
				"/Users/ericvergnaud/Development/prompto/prompto-keys/aws/keys.properties")) {
			props.load(input);
		}
		AWSCredentials credentials = new BasicAWSCredentials(
				props.getProperty("accessKey"), 
				props.getProperty("secretKey"));
		ec2 = AmazonEC2ClientBuilder.standard()
				.withRegion(Regions.US_WEST_2)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
		kms = AWSKMSClientBuilder.standard()
				.withRegion(Regions.US_WEST_2)
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
				
	}
	
	protected String arnFromAlias(String alias) {
		final String fullAlias = "alias/" + alias;
		ListAliasesResult res = kms.listAliases();
		AliasListEntry entry = res.getAliases().stream()
				.filter(a->fullAlias.equals(a.getAliasName()))
				.findFirst()
				.orElse(null);
		if(entry==null)
			return null;
		String prefix = entry.getAliasArn();
		prefix = prefix.substring(0, prefix.indexOf(":" + fullAlias));
		return prefix + ":key/" + entry.getTargetKeyId(); 
	}
	
	protected String encrypt(String plainKey) {
		ByteBuffer keyBytes = ByteBuffer.wrap(plainKey.getBytes());
		EncryptRequest req = new EncryptRequest().withKeyId(MASTER_KEY_ARN).withPlaintext(keyBytes);
		EncryptResult res = kms.encrypt(req);
		ByteBuffer encryptedBytes = res.getCiphertextBlob();
		return Base64.getEncoder().encodeToString(encryptedBytes.array());
	}
	
	protected String decrypt(String encryptedKey) {
		ByteBuffer encryptedBytes = ByteBuffer.wrap(Base64.getDecoder().decode(encryptedKey));
		DecryptRequest req2 = new DecryptRequest().withCiphertextBlob(encryptedBytes);
		DecryptResult res2 = kms.decrypt(req2);
		ByteBuffer keyBytes = res2.getPlaintext();
		return new String(keyBytes.array());
	}

}
