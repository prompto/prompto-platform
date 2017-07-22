package prompto.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.security.auth.spi.LoginModule;

import org.eclipse.jetty.jaas.JAASLoginService;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
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
import org.eclipse.jetty.util.security.Constraint;

import prompto.debug.DebugRequestServer;
import prompto.declaration.IMethodDeclaration;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.libraries.Libraries;
import prompto.runtime.Application;
import prompto.runtime.Interpreter;
import prompto.security.PasswordIsUserNameLoginModule;

public class AppServer {
	
	static Server jettyServer;
	public static String ALLOWED_ORIGIN = null;
	public static final String WEB_SERVER_SUCCESSFULLY_STARTED = "Web server successfully started on port ";
	
	public static void main(String[] args) throws Throwable {
		Integer httpPort = null;
		Integer debugPort = null;
		String serverAboutToStart = null;
		String webSite = null;
		String origin = null;

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
		if(argsMap.containsKey("origin"))
			origin = argsMap.get("origin");
		
		if(httpPort==null) {
			showHelp(httpPort);
			System.exit(-1); // raise an error in whatever tool is used to launch this
		}
		// initialize server accordingly
		final String fws = webSite==null ? null : new String(webSite);
		IExpression argsValue = Application.argsToArgValue(args);
		if(debugPort!=null)
			debugServer(debugHost, debugPort, httpPort, origin, serverAboutToStart, argsValue, ()->prepareWebHandlers(fws));
		else
			startServer(httpPort, origin, serverAboutToStart, argsValue, ()->prepareWebHandlers(fws), ()->{
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

	static int debugServer(String debugHost, Integer debugPort, Integer httpPort, String origin, String serverAboutToStartMethod, IExpression argValue, Supplier<Handler> handler) throws Throwable {
		DebugRequestServer server = Application.startDebugging(debugHost, debugPort);
		return startServer(httpPort, origin, serverAboutToStartMethod, argValue, handler, ()->{
			Application.getGlobalContext().notifyTerminated();
			server.stopListening();
		});
	}
	
	
	static int startServer(Integer httpPort, String origin, String serverAboutToStartMethod, IExpression argValue, Supplier<Handler> handler, Runnable serverStopped) throws Throwable {
		System.out.println("Starting web server on port " + httpPort + "...");
		ALLOWED_ORIGIN = origin;
		if(httpPort==-1) {
			jettyServer = new Server(httpPort);
			ServerConnector sc = new ServerConnector(jettyServer);
			jettyServer.setConnectors(new Connector[] { sc });
			jettyServer.setHandler(prepareSecurityHandler(handler));
			callServerAboutToStart(serverAboutToStartMethod, argValue);
			AppServer.start(serverStopped);
			httpPort = sc.getLocalPort();
		} else {
			jettyServer = new Server(httpPort);
			jettyServer.setHandler(prepareSecurityHandler(handler));
			callServerAboutToStart(serverAboutToStartMethod, argValue);
			AppServer.start(serverStopped);
		}
		System.out.println(WEB_SERVER_SUCCESSFULLY_STARTED + httpPort);
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

	static Handler prepareSecurityHandler(Supplier<Handler> handler) {
		try {
			System.out.println("Preparing security handler...");
			ConstraintSecurityHandler security = new ConstraintSecurityHandler();
			security.setLoginService(prepareLoginService()); // where to check credentials
			security.setAuthenticator(prepareAuthenticator()); // how to request credentials
			security.setConstraintMappings(prepareContraintMappings()); // when to require security
			security.setHandler(handler.get());
			System.out.println("Security handler successfully prepared.");
			return security;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Authenticator prepareAuthenticator() {
		return new BasicAuthenticator();
	}

	private static List<ConstraintMapping> prepareContraintMappings() {
		  ConstraintMapping mapping = new ConstraintMapping();
	      mapping.setPathSpec("/*"); // for now protect all paths
	      mapping.setConstraint(prepareConstraint());
	      return Collections.singletonList(mapping);
	}

	private static Constraint prepareConstraint() {
		Constraint constraint = new Constraint();
	    constraint.setName("authenticate");
	    constraint.setAuthenticate(true);
	    constraint.setRoles(new String[] { "**" }); // roles not handled by JAAS, se 
		return constraint;
	}

	private static LoginService prepareLoginService() {
		String loginModuleName = prepareLoginModule();
		JAASLoginService loginService = new JAASLoginService("prompto.login.service");
		loginService.setIdentityService(prepareIdentityService());
		loginService.setLoginModuleName(loginModuleName);
		jettyServer.addBean(loginService);
		return loginService;
	}

	private static String prepareLoginModule() {
		LoginModule module = new PasswordIsUserNameLoginModule();
		return module.getClass().getName();
	}

	private static IdentityService prepareIdentityService() {
		return new DefaultIdentityService();
	}

	static Handler prepareWebHandlers(String webSite) {
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
