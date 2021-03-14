package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TesAwsRoute53Library {

	@Test
	public void testRoute53ClientCanBeCreated() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsRoute53.pec"); 
		String[] args = new String[] { "-testMethod", "\"route53 client can be created\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
	
	@Test
	public void testRoute53ARecordCanBeRead() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsRoute53.pec"); 
		String[] args = new String[] { "-testMethod", "\"route53 A record can be read\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}

	@Test
	public void testRoute53ARecordCanBeCreatedAndDropped() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsRoute53.pec"); 
		String[] args = new String[] { "-testMethod", "\"route53 A record can be created and dropped\"", "-resourceURLs", url.toString() };
		Standalone.main(args);
	}
}
