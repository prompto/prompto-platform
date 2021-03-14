package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TestAwsEC2Library {

	@Test
	public void EC2ClientCanBeCreated() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 client can be created\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void EC2instancesCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 instances can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	@Test
	public void EC2instancesCanBeFiltered() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 instances can be filtered\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	@Test
	public void EC2InstanceCanBeCreatedNamedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 instance can be created, named and dropped\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void EC2AddressesCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 addresses can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void EC2AddressCanBeFoundByIP() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 address can be found by ip\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}


	@Test
	public void EC2AddressCanBeCreatedNamedAssociatedDissociatedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 address can be created, named, associated, dissociated and dropped\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void ownedAMIsCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"owned AMIs can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void AMIsWithOwnerAndNameCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"AMIs with owner and name can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	@Test
	public void AMIsWithOwnerOnlyCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"AMIs with owner only can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	
	@Test
	public void AMIsWithNameOnlyCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"AMIs with name only can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	
	@Test
	public void AMICanBeCreated() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"AMI can be created\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	
	@Test
	public void AMIVersionIsExtracted() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"AMI version is extracted\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	

}
