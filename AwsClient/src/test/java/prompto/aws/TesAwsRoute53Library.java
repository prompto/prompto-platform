package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Mode;
import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TesAwsRoute53Library {

	@Test
	public void testRoute53ClientCanBeCreated() throws Throwable {
		runTest("route53 client can be created");
	}
	
	@Test
	public void testRoute53ARecordCanBeRead() throws Throwable {
		runTest("route53 A record can be read");
	}

	@Test
	public void testRoute53ARecordCanBeCreatedAndDropped() throws Throwable {
		runTest("route53 A record can be created and dropped");
	}

	private void runTest(String testName) throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsRoute53.pec"); 
		String[] args = new String[] { "-testMethod", "\"" + testName + "\"", "-resourceURLs", url.toString(), "-runtimeMode", Mode.UNITTEST.name() };
		Standalone.main(args);
	}

}
