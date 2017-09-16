package prompto.aws;

import java.nio.ByteBuffer;
import java.util.Base64;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;

public class KMS {
	
	public static KMS newInstance(String awsRegion, String login, String password) {
		AWSKMSClientBuilder builder = AWSKMSClientBuilder.standard()
				.withRegion(awsRegion);
		if(login!=null && password!=null) {
			AWSCredentials credentials = new BasicAWSCredentials(login, password);
			builder = builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
		}
		return new KMS(builder.build());
	}
	
	AWSKMS kms;

	public KMS(AWSKMS kms) {
		this.kms = kms;
	}
	
	public String encrypt(String masterKeyARN, String dataToEncrypt) {
		ByteBuffer bytes = ByteBuffer.wrap(dataToEncrypt.getBytes());
		EncryptRequest req = new EncryptRequest()
			.withKeyId(masterKeyARN)
			.withPlaintext(bytes);
		EncryptResult res = kms.encrypt(req);
		bytes = res.getCiphertextBlob();
		return Base64.getEncoder().encodeToString(bytes.array());
	}
	
	public String decrypt(String dataToDecrypt) {
		ByteBuffer bytes = ByteBuffer.wrap(Base64.getDecoder().decode(dataToDecrypt));
		DecryptRequest req = new DecryptRequest()
			.withCiphertextBlob(bytes);
		DecryptResult res = kms.decrypt(req);
		bytes = res.getPlaintext();
		return new String(bytes.array());
		
	}
	
	
}
