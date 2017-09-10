package prompto.dataserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import prompto.server.AppServer;

public class Application {

	public static void main(String[] args) throws Throwable {
		List<String> argsList = new ArrayList<>(Arrays.asList(args));
		argsList.add("-resourceURLs");
		argsList.add(getResourcesList());
		argsList.add("-applicationNqme");
		argsList.add("data-explorer");
		argsList.add("-applicationVersion");
		argsList.add("1.0.0");
		argsList.add("-webSite");
		argsList.add("../../prompto-data-explorer/web-site/");
		AppServer.main(argsList.toArray(new String[argsList.size()]));
	}

	private static String getResourcesList() {
		return "\"" + Thread.currentThread().getContextClassLoader().getResource("libraries/DataExplorer.pec").toExternalForm() + "\"";
	}

}
