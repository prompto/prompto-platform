package prompto.codeserver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;

import prompto.server.AppServer;

@Category(SeleniumTests.class)
public abstract class BaseUITest extends BaseWebTest {

	@BeforeClass
	public static void startCodeServer() throws Throwable {
		LocalMongo.startMongoForUnitTests();
		String[] args = {
				"-yamlConfigFile",
				"test-local.yml",
				"-testMode",
				"true"
		};
		CodeServer.main(args);
		HTTP_PORT = AppServer.getHttpPort();
	}
	
	@AfterClass
	public static void stopCodeServer() throws Exception {
		AppServer.stop();
	}
	
	
	static int HTTP_PORT;

}
