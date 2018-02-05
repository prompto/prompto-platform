package prompto.appstore;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

import prompto.code.BaseCodeStore;
import prompto.config.IConfigurationReader;
import prompto.config.IServerConfiguration;
import prompto.config.ServerConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.server.AppServer;
import prompto.utils.CmdLineParser;
import prompto.utils.Logger;

public class AppStore {

	static Logger logger = new Logger();
	
	public static void main(String[] args) throws Throwable {
		main(args, null);
	}
	
	public static void main(String[] args, Mode runtimeMode) throws Throwable {
		IServerConfiguration config = loadConfiguration(args);
		config = config.withHttpConfiguration(config.getHttpConfiguration().withSendsXAuthorization(true))
					.withApplicationName("app-store")
					.withApplicationVersion(PromptoVersion.parse("1.0.0"))
					.withResourceURLs(AppStore.getResourceURLs());
		if(runtimeMode!=null)
			config = config.withRuntimeMode(runtimeMode);
		AppServer.main(config, null); 
	}
	
	public static IServerConfiguration loadConfiguration(String[] args) throws Exception {
		Map<String, String> argsMap = CmdLineParser.read(args);
		IConfigurationReader reader = Standalone.readerFromArgs(argsMap);
		IServerConfiguration config = new ServerConfiguration(reader, argsMap);
		return config.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
	}

	private static URL[] getResourceURLs() {
		Collection<URL> urls = Libraries.getPromptoLibraries(BaseCodeStore.class, AppStore.class);
		return urls.toArray(new URL[urls.size()]);
	}


}
