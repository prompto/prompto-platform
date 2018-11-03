package prompto.server;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import prompto.config.IConfigurationReader;
import prompto.config.IServerConfiguration;
import prompto.config.ServerConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.server.AppServer;
import prompto.utils.CmdLineParser;

public abstract class BaseUITest extends BaseWebTest {

	@BeforeClass
	public static void startAppServer() throws Throwable {
		String[] args = {
				"-yamlConfigFile",
				"test-local.yml"
		};
		TestServer.main(args);
		HTTP_PORT = AppServer.getHttpPort();
	}
	
	@AfterClass
	public static void stopAppServer() throws Exception {
		AppServer.stop();
	}
	
	
	protected static int HTTP_PORT;

	static abstract class TestServer {
		
		public static void main(String[] args) throws Throwable {
			IServerConfiguration config = loadConfiguration(args);
			config = config.withApplicationName("test")
						.withApplicationVersion(PromptoVersion.parse("1.0.0"))
						// .withResourceURLs(Application.getResourceURLs())
						.withRuntimeMode(Mode.UNITTEST);
			AppServer.main(config, null); 
		}
		
		public static IServerConfiguration loadConfiguration(String[] args) throws Exception {
			Map<String, String> argsMap = CmdLineParser.read(args);
			IConfigurationReader reader = Standalone.readerFromArgs(argsMap);
			IServerConfiguration config = new ServerConfiguration(reader, argsMap);
			return config.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
		}
	}

}
