package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Mode;
import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TestAwsEC2Library {

	@Test
	public void EC2ClientCanBeCreated() throws Throwable {
		runTest("ec2 client can be created");
	}
	
	@Test
	public void EC2instancesCanBeListed() throws Throwable {
		runTest("ec2 instances can be listed");
	}

	@Test
	public void EC2instancesCanBeFiltered() throws Throwable {
		runTest("ec2 instances can be filtered");
	}

	@Test
	public void EC2InstanceCanBeCreatedNamedAndDropped() throws Throwable {
		runTest("ec2 instance can be created, named and dropped");
	}
	
	@Test
	public void EC2AddressesCanBeListed() throws Throwable {
		runTest("ec2 addresses can be listed");
	}
	
	@Test
	public void EC2AddressCanBeFoundByIP() throws Throwable {
		runTest("ec2 address can be found by ip");
	}


	@Test
	public void EC2AddressCanBeCreatedNamedAssociatedDissociatedAndDropped() throws Throwable {
		runTest("ec2 address can be created, named, associated, dissociated and dropped");
	}
	
	@Test
	public void ownedAMIsCanBeListed() throws Throwable {
		runTest("owned AMIs can be listed");
	}
	
	@Test
	public void AMIsWithOwnerAndNameCanBeListed() throws Throwable {
		runTest("AMIs with owner and name can be listed");
	}

	@Test
	public void AMIsWithOwnerOnlyCanBeListed() throws Throwable {
		runTest("AMIs with owner only can be listed");
	}

	
	@Test
	public void AMIsWithNameOnlyCanBeListed() throws Throwable {
		runTest("AMIs with name only can be listed");
	}

	
	@Test
	public void AMICanBeCreated() throws Throwable {
		runTest("AMI can be created");
	}

	
	@Test
	public void AMIVersionIsExtracted() throws Throwable {
		runTest("AMI version is extracted");
	}
	
	@Test
	public void availabilityZonesAreListed() throws Throwable {
		runTest("Availability zones are listed");
	}
	
	@Test
	public void subnetsAreListed() throws Throwable {
		runTest("Subnets are listed");
	}
	
	private void runTest(String testName) throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"" + testName + "\"", "-resourceURLs", url.toString(), "-runtimeMode", Mode.UNITTEST.name() };
		Standalone.main(args);
	}

}
