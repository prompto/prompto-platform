package prompto.aws;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.efs.EfsClient;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.route53.Route53Client;
import software.amazon.awssdk.services.s3.S3Client;

public abstract class AWSTestBase {

	public static final Region DEFAULT_AWS_REGION = Region.US_EAST_1;
	public static final String CREDENTIALS_FILE_PATH = "/users/ericvergnaud/Documents/Technical/Certificates/prompto-keys/aws/us-east-1/keys.properties";
	
	AwsCredentials credentials;
	Ec2Client ec2;
	KmsClient kms;
	EfsClient efs;
	Route53Client route53;
	S3Client s3;
	Properties props = new Properties();
	
	@Before
	public void before() throws Exception {
		try (InputStream input = new FileInputStream(CREDENTIALS_FILE_PATH)) {
			props.load(input);
		}
		credentials = AwsBasicCredentials.create(
				props.getProperty("accessKey"), 
				props.getProperty("secretKey"));
		ec2 = Ec2Client.builder()
				.region(DEFAULT_AWS_REGION)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
		kms = KmsClient.builder()
				.region(DEFAULT_AWS_REGION)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
		efs = EfsClient.builder()
				.region(DEFAULT_AWS_REGION)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
		route53 = Route53Client.builder()
				.region(Region.AWS_GLOBAL)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
		s3 = S3Client.builder()
				.region(DEFAULT_AWS_REGION)
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.build();
	}
	

}
