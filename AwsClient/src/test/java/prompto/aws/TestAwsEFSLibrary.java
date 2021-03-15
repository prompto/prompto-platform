package prompto.aws;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
	public void fileSystemIsCreatedAndDestroyed() throws Throwable {
		runTest("File system is created and destroyed");
	}

	@Test
	public void mountTargetIsCreatedAndDestroyed() throws Throwable {
		runTest("Mount target is created and destroyed");
	}

	private void runTest(String testName) throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("libraries/AwsEFS.pec"); 
		String[] args = new String[] { "-testMethod", "\"" + testName + "\"", "-resourceURLs", url.toString(), "-runtimeMode", Mode.UNITTEST.name() };
		Standalone.main(args);
	}

}
