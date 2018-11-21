package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TestAwsEC2 {

	@Test
	public void testEC2ClientCanBeCreated() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 client can be created\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void testEC2instancesCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 instances can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	@Test
	public void testEC2instancesCanBeFiltered() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 instances can be filtered\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	@Test
	public void testEC2InstanceCanBeCreatedNamedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 instance can be created, named and dropped\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void testEC2AddressesCanBeListed() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 addresses can be listed\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void testEC2AddressCanBeFoundByIP() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 address can be found by ip\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}


	@Test
	public void testEC2AddressCanBeCreatedNamedAssociatedDissociatedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEC2.pec"); 
		String[] args = new String[] { "-testMethod", "\"ec2 address can be created, named, associated, dissociated and dropped\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
}
