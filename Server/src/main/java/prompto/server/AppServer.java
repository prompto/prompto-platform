package prompto.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

import prompto.config.IConfigurationReader;
import prompto.config.IDebugConfiguration;
import prompto.config.IHttpConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.ServerConfiguration;
import prompto.debug.DebugRequestServer;
import prompto.declaration.IMethodDeclaration;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.libraries.Libraries;
import prompto.runtime.Application;
import prompto.runtime.Interpreter;
import prompto.utils.CmdLineParser;

public class AppServer {
	
	static Server jettyServer;
	public static String ALLOWED_ORIGIN = null;
	public static final String WEB_SERVER_SUCCESSFULLY_STARTED = "Web server successfully started on port ";
	
	public static void main(String[] args) throws Throwable {
		main(args, null);
	}
	
	public static void main(String[] args, Consumer<IServerConfiguration> afterStart) throws Throwable {
		IServerConfiguration config = loadConfiguration(args);
		initialize(config);
		run(config);
		if(afterStart!=null)
			afterStart.accept(config);
	}

	public static void initialize(IServerConfiguration config) throws Throwable {
		ServerIdentifierProcessor.register();
		Application.initialize(config);
	}

	private static IServerConfiguration loadConfiguration(String[] args) throws Exception {
		Map<String, String> argsMap = CmdLineParser.parse(args);
		IConfigurationReader reader = Application.readerFromArgs(argsMap);
		IServerConfiguration config = new ServerConfiguration(reader, argsMap);
		config.setRuntimeLibsSupplier(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
		return config;
	}

	private static void run(IServerConfiguration config) throws Throwable {
		String serverAboutToStart = config.getServerAboutToStartMethod();
		IExpression argsValue = Application.argsToArgValue(config.getArguments());
		String webSite = config.getWebSiteRoot();
		IHttpConfiguration http = config.getHttpConfiguration();
		IDebugConfiguration debug = config.getDebugConfiguration();
		if(debug!=null)
			debugServer(debug, http, webSite, serverAboutToStart, argsValue, AppServer::prepareHandlers);
		else
			startServer(http, webSite, serverAboutToStart, argsValue, AppServer::prepareHandlers, ()->{ Application.getGlobalContext().notifyTerminated(); });
	}

	static int debugServer(IDebugConfiguration debug, IHttpConfiguration http, String webSite, String serverAboutToStartMethod, IExpression argValue, Function<String, Handler> handler) throws Throwable {
		DebugRequestServer server = Application.startDebugging(debug.getHost(), debug.getPort());
		return startServer(http, webSite, serverAboutToStartMethod, argValue, handler, ()->{
			Application.getGlobalContext().notifyTerminated();
			server.stopListening();
		});
	}
	
	
	static int startServer(IHttpConfiguration http, String webSite, String serverAboutToStartMethod, IExpression argValue, Function<String, Handler> handler, Runnable serverStopped) throws Throwable {
		int port = http.getPort();
		System.out.println("Starting web server on port " + port + "...");
		ALLOWED_ORIGIN = http.getOrigin();
		if(port==-1) {
			jettyServer = new Server(port);
			ServerConnector sc = new ServerConnector(jettyServer);
			jettyServer.setConnectors(new Connector[] { sc });
			jettyServer.setHandler(handler.apply(webSite));
			callServerAboutToStart(serverAboutToStartMethod, argValue);
			AppServer.start(serverStopped);
			port = sc.getLocalPort();
		} else {
			jettyServer = new Server(port);
			jettyServer.setHandler(handler.apply(webSite));
			callServerAboutToStart(serverAboutToStartMethod, argValue);
			AppServer.start(serverStopped);
		}
		System.out.println(WEB_SERVER_SUCCESSFULLY_STARTED + port);
		return port;
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
			list.addHandler(newResourceHandler("/", webSite));
			list.addHandler(newServiceHandler("/ws/"));
			list.addHandler(new DefaultHandler());
			System.out.println("Web handlers successfully prepared.");
			return list;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Handler newServiceHandler(String path) throws Exception {
		URL url = getRootURL();
		return newServiceHandler(path, url.toExternalForm());
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
		System.out.println("Serving web site at: " + file.getCanonicalPath());
		if(!file.exists())
			throw new FileNotFoundException(webSite);
		return file.getCanonicalFile();
	}


	public static void installHandler(String path, IMethodDeclaration method) {
		// TODO check path (must start with '/') and method prototype
		ServletContextHandler handler = jettyServer.getChildHandlerByClass(ServletContextHandler.class);
        handler.addServlet(new UserServlet(method).getHolder(), path);       
	}
	
	public static Handler newServiceHandler(String path, String base) {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath(path);
        handler.setResourceBase(base);
        handler.addServlet(new ControlServlet().getHolder(), "/control/*");
        handler.addServlet(new BinaryServlet().getHolder(), "/bin/*");
        handler.addServlet(new DataServlet().getHolder(), "/data/*");       
        handler.addServlet(new PromptoServlet().getHolder(), "/run/*");       
		return handler;
	}

	static Handler newResourceHandler(String path, String webSite) throws Exception {
		Handler handler = webSite!=null ? newWebSiteResourceHandler(webSite) : newCodeStoreResourceHandler();
		ContextHandler ch = new ContextHandler(path);
		ch.setHandler(handler);
		return ch;
	}

	private static ResourceHandler newCodeStoreResourceHandler() {
		String rootPath = AppServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Resource resource = Resource.newResource(new File(rootPath));
		ResourceHandler rh = new CodeStoreResourceHandler();
		rh.setBaseResource(resource);
		rh.setDirectoriesListed(false);
		return rh;
	}

	private static ResourceHandler newWebSiteResourceHandler(String webSite) throws Exception {
		List<Resource> list = new ArrayList<>();
		File file = getWebSiteFile(webSite);
		Resource resource = Resource.newResource(file);
		list.add(resource);
		String rootPath = AppServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		resource = Resource.newResource(new File(rootPath));
		list.add(resource);
		resource = new ResourceCollection(list.toArray(new Resource[list.size()]));
		ResourceHandler rh = new ResourceHandler();
		rh.setBaseResource(resource);
		rh.setDirectoriesListed(false);
		return rh;
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
		if(jettyServer==null || !jettyServer.isStarted())
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
