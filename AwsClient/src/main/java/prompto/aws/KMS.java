package prompto.aws;

import java.nio.ByteBuffer;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.AliasListEntry;
import com.amazonaws.services.kms.model.CreateAliasRequest;
import com.amazonaws.services.kms.model.CreateKeyResult;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.DeleteAliasRequest;
import com.amazonaws.services.kms.model.DisableKeyRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.services.kms.model.ListAliasesResult;

public class KMS {
	
	static Logger logger = LoggerFactory.getLogger(KMS.class);
	
	public static KMS newInstance(String awsRegion, String login, String password) {
		AWSKMSClientBuilder builder = AWSKMSClientBuilder.standard()
				.withRegion(awsRegion);
		login = verifyLogin(login);
		if(login!=null && password!=null) {
			log("Connecting to " + awsRegion + " with login "  + login);
			AWSCredentials credentials = new BasicAWSCredentials(login, password);
			builder = builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
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

	AWSKMS kms;

	public KMS(AWSKMS kms) {
		this.kms = kms;
	}
	
	
	public String newKeyARNWithAlias(String alias) {
		CreateKeyResult res = kms.createKey();
		String arn = res.getKeyMetadata().getArn();
		CreateAliasRequest req = new CreateAliasRequest()
				.withAliasName("alias/" + alias)
				.withTargetKeyId(arn);
		kms.createAlias(req);
		return arn;
	}
	
	
	public void deleteKeyARNWithAlias(String alias) {
		final String fullAlias = "alias/" + alias;
		ListAliasesResult res = kms.listAliases();
		AliasListEntry entry = res.getAliases().stream()
				.filter(a->fullAlias.equals(a.getAliasName()))
				.findFirst()
				.orElse(null);
		DeleteAliasRequest req1 = new DeleteAliasRequest()
				.withAliasName(entry.getAliasName());
		kms.deleteAlias(req1);
		DisableKeyRequest req2 = new DisableKeyRequest()
				.withKeyId(entry.getTargetKeyId());
		kms.disableKey(req2);
	}
	
	public String keyARNFromAlias(String alias) {
		logger.info("Fetching ARN for " + alias);
		final String fullAlias = "alias/" + alias;
		ListAliasesResult res = kms.listAliases();
		AliasListEntry entry = res.getAliases().stream()
				.filter(a->fullAlias.equals(a.getAliasName()))
				.findFirst()
				.orElse(null);
		if(entry==null) {
			log("No ARN found for " + alias);
			return null;
		}
		String prefix = entry.getAliasArn();
		prefix = prefix.substring(0, prefix.indexOf(":" + fullAlias));
		String result = prefix + ":key/" + entry.getTargetKeyId(); 
		log("Found ARN: " + result);
		return result;
	}
	
	public String encrypt(String masterKeyARN, String dataToEncrypt) {
		ByteBuffer bytes = ByteBuffer.wrap(dataToEncrypt.getBytes());
		EncryptRequest req = new EncryptRequest()
			.withKeyId(masterKeyARN)
			.withPlaintext(bytes);
		EncryptResult res = kms.encrypt(req);
		bytes = res.getCiphertextBlob();
		String result = Base64.getEncoder().encodeToString(bytes.array());
		log("Encrypted " + result);
		return result;
	}
	
	public String decrypt(String dataToDecrypt) {
		log("Decrypting " + dataToDecrypt);
		ByteBuffer bytes = ByteBuffer.wrap(Base64.getDecoder().decode(dataToDecrypt));
		DecryptRequest req = new DecryptRequest()
			.withCiphertextBlob(bytes);
		DecryptResult res = kms.decrypt(req);
		bytes = res.getPlaintext();
		return new String(bytes.array());
		
	}
	
	
}
