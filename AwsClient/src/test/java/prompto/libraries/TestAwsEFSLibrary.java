package prompto.libraries;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.aws.AwsTest;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;

@Category(AwsTest.class)
public class TestAwsEFSLibrary {

	@Test
	public void EFSClientIsCreated() throws Throwable {
		runTest("EFS client is created");
	}
	
	@Test
	public void fileSystemsAreListed() throws Throwable {
		runTest("File systems are listed");
	}

	@Test
	public void fileSystemIsCreatedAndDropped() throws Throwable {
		runTest("File system is created and dropped");
	}

	@Test
	public void mountTargetIsCreatedAndDropped() throws Throwable {
		runTest("Mount target is created and dropped");
	}

	private void runTest(String testName) throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEFS.pec"); 
		String[] args = new String[] { "-testMethod", "\"" + testName + "\"", "-resourceURLs", url.toString(), "-runtimeMode", Mode.UNITTEST.name() };
		Standalone.main(args);
	}

}
