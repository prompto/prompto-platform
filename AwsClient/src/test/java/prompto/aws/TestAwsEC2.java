package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Application;

@Category(AwsTest.class)
public class TestAwsEC2 {

	@Test
	public void testEC2ClientCanBeCreated() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-test", "\"ec2 client can be created\"", "-resources", url.toString() };
		Application.main(args);
	}
	
	@Test
	public void testEC2instancesCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-test", "\"ec2 instances can be listed\"", "-resources", url.toString() };
		Application.main(args);
	}

	@Test
	public void testEC2InstanceCanBeCreatedNamedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-test", "\"ec2 instance can be created, named and dropped\"", "-resources", url.toString() };
		Application.main(args);
	}
	
	@Test
	public void testEC2AddressesCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-test", "\"ec2 addresses can be listed\"", "-resources", url.toString() };
		Application.main(args);
	}
	
	@Test
	public void testEC2AddressCanBeFoundByIP() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-test", "\"ec2 address can be found by ip\"", "-resources", url.toString() };
		Application.main(args);
	}


	@Test
	public void testEC2AddressCanBeCreatedAssociatedDissociatedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-test", "\"ec2 address can be created, associated, dissociated and dropped\"", "-resources", url.toString() };
		Application.main(args);
	}
}
