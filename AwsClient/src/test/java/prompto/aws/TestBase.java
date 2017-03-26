package prompto.aws;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

public abstract class TestBase {

	AmazonEC2 ec2;
	
	@Before
	public void before() throws Exception {
		Properties props = new Properties();
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
	}
	

}
