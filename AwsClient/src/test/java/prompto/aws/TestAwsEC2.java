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
		String[] args = new String[] { "-test", "\"test ec2 client can be created\"", "-resources", url.toString() };
		Application.main(args);
	}
}
