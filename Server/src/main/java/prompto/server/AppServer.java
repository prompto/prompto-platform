package prompto.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

import prompto.debug.DebugRequestServer;
import prompto.declaration.IMethodDeclaration;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.libraries.Libraries;
import prompto.runtime.Application;
import prompto.runtime.Interpreter;

public class AppServer {
	
	static Server jettyServer;
	
	public static void main(String[] args) throws Throwable {
		Integer httpPort = null;
		Integer debugPort = null;
		String serverAboutToStart = null;
		String webSite = null;

		Map<String, String> argsMap = initialize(args);

		String debugHost = argsMap.getOrDefault("debug_host", "localhost");
		if(argsMap.containsKey("debug_port"))
			debugPort = Integer.parseInt(argsMap.get("debug_port"));
		if(argsMap.containsKey("http_port"))
			httpPort = Integer.parseInt(argsMap.get("http_port"));
		if(argsMap.containsKey("serverAboutToStart"))
			serverAboutToStart = argsMap.get("serverAboutToStart");
		if(argsMap.containsKey("web-site"))
			webSite = argsMap.get("web-site");

		if(httpPort==null) {
			showHelp(httpPort);
			System.exit(-1); // raise an error in whatever tool is used to launch this
		}
		// initialize server accordingly
		IExpression argsValue = Application.argsToArgValue(args);
		if(debugPort!=null)
			debugServer(debugHost, debugPort, httpPort, webSite, serverAboutToStart, argsValue, AppServer::prepareHandlers);
		else
			startServer(httpPort, webSite, serverAboutToStart, argsValue, AppServer::prepareHandlers, ()->{
				Application.getGlobalContext().notifyTerminated();
			});
	}

	public static Map<String, String> initialize(String[] args) throws Throwable  {
		ServerIdentifierProcessor.register();
		return Application.initialize(args, ()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
	}
	
	private static void showHelp(Integer httpPort) {
		if(httpPort==null)
			System.out.println("Missing argument: -http_port");
	}

	static int debugServer(String debugHost, Integer debugPort, Integer httpPort, String webSite, String serverAboutToStartMethod, IExpression argValue, Function<String, Handler> handler) throws Throwable {
		DebugRequestServer server = Application.startDebugging(debugHost, debugPort);
		return startServer(httpPort, webSite, serverAboutToStartMethod, argValue, handler, ()->{
			Application.getGlobalContext().notifyTerminated();
			server.stopListening();
		});
	}
	
	
	static int startServer(Integer httpPort, String webSite, String serverAboutToStartMethod, IExpression argValue, Function<String, Handler> handler, Runnable serverStopped) throws Throwable {
		System.out.println("Starting web server on port " + httpPort + "...");
		if(httpPort==-1) {
			jettyServer = new Server(httpPort);
			ServerConnector sc = new ServerConnector(jettyServer);
			jettyServer.setConnectors(new Connector[] { sc });
			jettyServer.setHandler(handler.apply(webSite));
			callServerAboutToStart(serverAboutToStartMethod, argValue);
			AppServer.start(serverStopped);
			httpPort = sc.getLocalPort();
		} else {
			jettyServer = new Server(httpPort);
			jettyServer.setHandler(handler.apply(webSite));
			callServerAboutToStart(serverAboutToStartMethod, argValue);
			AppServer.start(serverStopped);
		}
		System.out.println("Web server successfully started on port " + httpPort);
		return httpPort;
	}
	
	public static void callServerAboutToStart(String serverAboutToStartMethod, IExpression argValue) {
		if(serverAboutToStartMethod!=null) {
			System.out.println("Calling startUp method " + serverAboutToStartMethod);
			Interpreter.interpretMethod(Application.getGlobalContext(), new Identifier(serverAboutToStartMethod), argValue);
		}
	}

	public static int getHttpPort() {
		for(Connector c : jettyServer.getConnectors()) {
			if(c instanceof ServerConnector)
				return ((ServerConnector)c).getLocalPort();
		}
		return 0;
	}

	static HandlerList prepareHandlers(String webSite) {
		try {
			System.out.println("Preparing web handlers...");
			HandlerList list = new HandlerList();
			Handler hh = prepareResourceHandler("/", webSite);
			if(hh!=null)
				list.addHandler(hh);
			list.addHandler(prepareServiceHandler("/ws/"));
			list.addHandler(new DefaultHandler());
			System.out.println("Web handlers successfully prepared.");
			return list;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Handler prepareServiceHandler(String path) throws Exception {
		URL url = getRootURL();
		return prepareServiceHandler(path, url.toExternalForm());
	}
	
	private static URL getRootURL() throws IOException {
		URL url = AppServer.class.getResource("/js/lib/require.js");
		url = new URL(url.toExternalForm().replace("/js/lib/require.js", "/"));
		// ugly work around for unit tests
		if(url.toExternalForm().contains("/test-classes/"))
			url = new URL(url.toExternalForm().replace("/test-classes/", "/classes/"));
		return url;
	}

	private static File getWebSiteFile(String webSite) throws IOException {
		if(webSite==null)
			return null;
		File file = new File(webSite);
		if(!file.exists())
			throw new FileNotFoundException(webSite);
		return file.getCanonicalFile();
	}


	public static void installHandler(String path, IMethodDeclaration method) {
		// TODO check path (must start with '/') and method prototype
		ServletContextHandler handler = jettyServer.getChildHandlerByClass(ServletContextHandler.class);
        handler.addServlet(new ServletHolder(new UserServlet(method)), path);       
	}
	
	public static Handler prepareServiceHandler(String path, String base) {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath(path);
        handler.setResourceBase(base);
        handler.addServlet(new ServletHolder(new BinaryServlet()), "/bin/*");
        handler.addServlet(new ServletHolder(new DataServlet()), "/data/*");       
        handler.addServlet(new ServletHolder(new PromptoServlet()), "/run/*");       
 		return handler;
	}

	static Handler prepareResourceHandler(String path, String webSite) throws Exception {
		Resource resource = getResource(webSite);
		ResourceHandler rh = new ResourceHandler();
		rh.setBaseResource(resource);
		rh.setDirectoriesListed(false);
		rh.setWelcomeFiles(new String[] {"index.html"});
		ContextHandler ch = new ContextHandler(path);
		ch.setHandler(rh);
		return ch;
	}

	private static Resource getResource(String webSite) throws IOException {
		List<Resource> list = new ArrayList<>();
		if(webSite!=null) {
			File file = getWebSiteFile(webSite);
			Resource resource = Resource.newResource(file);
			list.add(resource);
		}
		String rootPath = AppServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Resource resource = Resource.newResource(new File(rootPath));
		list.add(resource);
		return new ResourceCollection(list.toArray(new Resource[list.size()]));
	}

	static boolean startComplete = false;
	static Thread serverThread = null;
	static Throwable serverThrowable = null;
	
	public static void start(Runnable serverStopped) throws Throwable  {
		if(jettyServer.isStarted())
			throw new RuntimeException("Server is already started!");
		serverThrowable = null;
		startComplete = false;
		Object sync = new Object();
		serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Web server about to start...");
				try {
					try {
						jettyServer.start();
						System.out.println("Web server started...");
					} finally {
						System.out.println("Signaling start completion...");
						synchronized (sync) {
							startComplete = true;
							sync.notify();
						}
					}
					System.out.println("Web server thread waiting for completion...");
					jettyServer.join();
					System.out.println("Web server thread complete.");
					if(serverStopped!=null)
						serverStopped.run();
				} catch(Throwable t) {
					serverThrowable = t;
				} finally {
					serverThread = null;
				}
			}
		}, "HTTP Server");
		serverThread.start();
		System.out.println("Waiting for start completion signal...");
		synchronized (sync) {
			while(!startComplete)
				sync.wait();
		}
		System.out.println("Start completion signalled...");
		if(serverThrowable!=null) {
			Throwable t = serverThrowable;
			serverThrowable = null;
			throw t;
		}
	}

	public static void stop() throws Exception {
		if(!jettyServer.isStarted())
			throw new RuntimeException("Server is not started!");
		System.out.println("Stopping web server...");
		jettyServer.stop();
		System.out.println("Web server stopped, waiting for completion...");
		jettyServer.join();
		System.out.println("Web server stop complete.");
	}

	public static boolean isStarted() {
		return jettyServer.isStarted();
	}

}
