package prompto.libraries;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.aws.AwsTest;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TestAwsS3Library {

	@Test
	public void S3ClientIsCreated() throws Throwable {
		runTest("S3 client is created");
	}
	
	@Test
	public void S3BucketNamesAreListed() throws Throwable {
		runTest("S3 bucket names are listed");
	}

	@Test
	public void S3BucketObjectsAreListed() throws Throwable {
		runTest("S3 bucket objects are listed");
	}

	@Test
	public void S3ObjectTextIsFetched() throws Throwable {
		runTest("S3 object text is fetched");
	}
	
	@Test
	public void S3ObjectIsStoredAndDeleted() throws Throwable {
		runTest("S3 test object text is stored and deleted");
	}

	private void runTest(String testName) throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsS3.pec"); 
		String[] args = new String[] { "-testMethod", "\"" + testName + "\"", "-resourceURLs", url.toString(), "-runtimeMode", Mode.UNITTEST.name() };
		Standalone.main(args);
	}

}
