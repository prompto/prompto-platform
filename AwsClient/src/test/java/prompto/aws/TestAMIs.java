package prompto.aws;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;

@Category(AwsTest.class)
public class TestAMIs extends AWSTestBase {

	@Test
	public void listsOwnedAMIs() throws Exception {
		EC2 awsEc2 = new EC2(ec2);
		PromptoList<PromptoDocument<String, Object>> result = awsEc2.listOwnedAMIs();
		result.forEach(System.out::println);
		assertFalse(result.isEmpty());
	}
	
	
	@Test
	public void listsAMIsWithOwnerAndName() throws Exception {
		try(Ec2Client client = Ec2Client.builder()
			.region(Region.AP_SOUTHEAST_1)
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.build()) {
			EC2 awsEc2 = new EC2(client);
			PromptoList<PromptoDocument<String, Object>> result = awsEc2.listAMIsWithOwnerAndName("838901125615", "centos-prompto-*");
			result.forEach(System.out::println);
			assertFalse(result.isEmpty());
			result = awsEc2.listAMIsWithOwnerAndName("838901125615", "centos-prompto-*");
			result.forEach(System.out::println);
			assertFalse(result.isEmpty());
		}
	}
	
	@Test
	public void createsAMIsetsTagAndWaitsForAvailability() {
		EC2 awsEc2 = new EC2(ec2);
		String imageId = awsEc2.createAMI("i-0f97960da0e736ceb", "prompto-test-ami", true);
		assertNotNull(imageId);
	}

}
