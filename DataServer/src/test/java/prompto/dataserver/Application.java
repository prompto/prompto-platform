package prompto.dataserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import prompto.server.AppServer;

public class Application {

	public static void main(String[] args) throws Throwable {
		List<String> argsList = new ArrayList<>(Arrays.asList(args));
		argsList.add("-resources");
		argsList.add(getResourcesList());
		argsList.add("-application");
		argsList.add("data-explorer");
		argsList.add("-version");
		argsList.add("1.0.0");
		argsList.add("-web-site");
		argsList.add("../data-explorer/web-site/");
		AppServer.main(argsList.toArray(new String[argsList.size()]));
	}

	private static String getResourcesList() {
		return "\"" + Thread.currentThread().getContextClassLoader().getResource("libraries/DataExplorer.pec").toExternalForm() + "\"";
	}

}
