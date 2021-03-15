package prompto.aws;

import java.nio.ByteBuffer;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.KmsClientBuilder;
import software.amazon.awssdk.services.kms.model.AliasListEntry;
import software.amazon.awssdk.services.kms.model.CreateAliasRequest;
import software.amazon.awssdk.services.kms.model.CreateKeyResponse;
import software.amazon.awssdk.services.kms.model.DecryptRequest;
import software.amazon.awssdk.services.kms.model.DecryptResponse;
import software.amazon.awssdk.services.kms.model.DeleteAliasRequest;
import software.amazon.awssdk.services.kms.model.DisableKeyRequest;
import software.amazon.awssdk.services.kms.model.EncryptRequest;
import software.amazon.awssdk.services.kms.model.EncryptResponse;
import software.amazon.awssdk.services.kms.model.ListAliasesResponse;
import software.amazon.awssdk.services.kms.model.ScheduleKeyDeletionRequest;

public class KMS {
	
	static Logger logger = LoggerFactory.getLogger(KMS.class);
	
	public static KMS newInstance(String awsRegion, String login, String password) {
		KmsClientBuilder builder = KmsClient.builder()
				.region(Region.of(awsRegion));
		login = verifyLogin(login);
		if(login!=null && password!=null) {
			log("Connecting to " + awsRegion + " with login "  + login);
			AwsCredentials credentials = AwsBasicCredentials.create(login, password);
			builder = builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
		} else {
			log("Connecting to " + awsRegion + " through IAM");
		}
		return new KMS(builder.build());
	}
	
	private static String verifyLogin(String login) {
		if(login!=null) {
			if(login.trim().isEmpty() || "null".equals(login)) {
				log("Invalid login: " + login);
				return null;
			}
		}
		return login;
	}
	
	private static void log(String msg) {
		logger.info(msg);
		// logger may not be connected yet, so also copy to err
		System.err.println(msg);
	}

	KmsClient kms;

	public KMS(KmsClient kms) {
		this.kms = kms;
	}
	
	
	public String newKeyARNWithAlias(String alias) {
		CreateKeyResponse res = kms.createKey();
		String arn = res.keyMetadata().arn();
		CreateAliasRequest req = CreateAliasRequest.builder()
				.aliasName("alias/" + alias)
				.targetKeyId(arn)
				.build();
		kms.createAlias(req);
		return arn;
	}
	
	
	public void deleteKeyARNWithAlias(String alias) {
		final String fullAlias = "alias/" + alias;
		ListAliasesResponse listResponses = kms.listAliases();
		AliasListEntry entry = listResponses.aliases().stream()
				.filter(a->fullAlias.equals(a.aliasName()))
				.findFirst()
				.orElse(null);
		String keyId = entry.targetKeyId();
		DeleteAliasRequest deleteAliasRequest = DeleteAliasRequest.builder()
				.aliasName(entry.aliasName())
				.build();
		kms.deleteAlias(deleteAliasRequest);
		DisableKeyRequest disableRequest = DisableKeyRequest.builder()
				.keyId(keyId)
				.build();
		kms.disableKey(disableRequest);
		ScheduleKeyDeletionRequest deletionRequest = ScheduleKeyDeletionRequest.builder()
				.keyId(keyId)
				.pendingWindowInDays(7)
				.build();
		kms.scheduleKeyDeletion(deletionRequest);
	}
	
	public String keyARNFromAlias(String alias) {
		logger.info("Fetching ARN for " + alias);
		final String fullAlias = "alias/" + alias;
		ListAliasesResponse res = kms.listAliases();
		AliasListEntry entry = res.aliases().stream()
				.filter(a->fullAlias.equals(a.aliasName()))
				.findFirst()
				.orElse(null);
		if(entry==null) {
			log("No ARN found for " + alias);
			return null;
		}
		String prefix = entry.aliasArn();
		prefix = prefix.substring(0, prefix.indexOf(":" + fullAlias));
		String result = prefix + ":key/" + entry.targetKeyId(); 
		log("Found ARN: " + result);
		return result;
	}
	
	public String encrypt(String masterKeyARN, String dataToEncrypt) {
		ByteBuffer bytes = ByteBuffer.wrap(dataToEncrypt.getBytes());
		EncryptRequest req = EncryptRequest.builder()
			.keyId(masterKeyARN)
			.plaintext(SdkBytes.fromByteBuffer(bytes))
			.build();
		EncryptResponse res = kms.encrypt(req);
		String result = Base64.getEncoder().encodeToString(res.ciphertextBlob().asByteArray());
		log("Encrypted " + result);
		return result;
	}
	
	public String decrypt(String dataToDecrypt) {
		log("Decrypting " + dataToDecrypt);
		ByteBuffer bytes = ByteBuffer.wrap(Base64.getDecoder().decode(dataToDecrypt));
		DecryptRequest req = DecryptRequest.builder()
			.ciphertextBlob(SdkBytes.fromByteBuffer(bytes))
			.build();
		DecryptResponse res = kms.decrypt(req);
		return new String(res.plaintext().asByteArray());
		
	}
	
	
}
