package prompto.codefactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import prompto.codefactory.Application;
import prompto.runtime.Mode;
import prompto.server.AppServer;

public abstract class BaseUITest extends BaseBrowserTest {

	@BeforeClass
	public static void startCodeServer() throws Throwable {
		String[] args = {
				"-yamlConfigFile",
				"test-local.yml"
		};
		Application.main(args, Mode.UNITTEST);
		HTTP_PORT = AppServer.getHttpPort();
	}
	
	@AfterClass
	public static void stopCodeServer() throws Exception {
		AppServer.stop();
	}
	
	
	static int HTTP_PORT;

}
