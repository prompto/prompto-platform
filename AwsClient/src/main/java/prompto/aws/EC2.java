package prompto.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;

public class EC2 {
	
	public static EC2 newInstance(String ec2Region, String login, String password) {
		AmazonEC2ClientBuilder builder = AmazonEC2ClientBuilder.standard()
				.withRegion(ec2Region);
		if(login!=null && password!=null) {
			AWSCredentials credentials = new BasicAWSCredentials(login, password);
			builder = builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
		}
		return new EC2(builder.build());
	}
	
	AmazonEC2 ec2;

	public EC2(AmazonEC2 ec2) {
		this.ec2 = ec2;
	}

}
