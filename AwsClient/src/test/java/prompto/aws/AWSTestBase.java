package prompto.aws;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Properties;

import org.junit.Before;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.AliasListEntry;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;
import software.amazon.awssdk.services.kms.model.ListAliasesResponse;
import software.amazon.awssdk.services.route53.Route53Client;

public abstract class AWSTestBase {

	public static String MASTER_KEY_ARN = "arn:aws:kms:us-east-1:838901125615:key/fd4e13e3-72c3-41ee-90de-4531f5c4c93a";
	public static String MASTER_KEY_ALIAS = "prompto/seed";
	public static Region MASTER_KEY_REGION = Region.US_EAST_1;
			
	AwsCredentials credentials;
	Ec2Client ec2;
	Route53Client route53;
	KmsClient kms;
	Properties props = new Properties();
	
	@Before
	public void before() throws Exception {
		try (InputStream input = new FileInputStream("/users/ericvergnaud/Documents/Technical/Certificates/prompto-keys/aws/us-east-1/keys.properties")) {
			props.load(input);
		}
		credentials = AwsBasicCredentials.create(
				props.getProperty("accessKey"), 
				props.getProperty("secretKey"));
		ec2 = Ec2Client.builder()
				.region(Region.US_EAST_1)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
		kms = KmsClient.builder()
				.region(Region.US_EAST_1)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
		route53 = Route53Client.builder()
				.region(Region.AWS_GLOBAL)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
				
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
