package prompto.libraries;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ServerLibraries {

	public static Collection<URL> getRuntimeResources() {
		Collection<URL> runtime = Libraries.getRuntimeResources();
		Collection<URL> server = Libraries.getRuntimeResources(ServerLibraries.class);
		List<URL> all = new ArrayList<>();
		all.addAll(runtime);
		all.addAll(server);
		return all;
	}

}
