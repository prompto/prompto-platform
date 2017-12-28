package prompto.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.jaas.JAASLoginService;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import prompto.cloud.Cloud;
import prompto.config.IConfigurationReader;
import prompto.config.IDebugConfiguration;
import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.IKeyStoreFactoryConfiguration;
import prompto.config.ILoginConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.ServerConfiguration;
import prompto.debug.DebugRequestServer;
import prompto.declaration.IMethodDeclaration;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.libraries.Libraries;
import prompto.runtime.Interpreter;
import prompto.runtime.Standalone;
import prompto.security.IKeyStoreFactory;
import prompto.security.ILoginMethodFactory;
import prompto.security.ISecretKeyFactory;
import prompto.utils.CmdLineParser;
import prompto.utils.Logger;
import prompto.utils.ObjectUtils;

public class AppServer {
	
	static final Logger logger = new Logger();
	
	public static final String WEB_SERVER_SUCCESSFULLY_STARTED = "Web server successfully started on port ";
	
	private static Server jettyServer;

	public static void main(String[] args) throws Throwable {
		main(args, null);
	}
	
	public static void main(String[] args, Consumer<IServerConfiguration> afterStart) throws Throwable {
		IServerConfiguration config = loadConfiguration(args);
		main(config, afterStart);
	}

	public static <T extends IServerConfiguration>void main(T config, Consumer<T> afterStart) throws Throwable {
		installCloudJARs();
		initialize(config);
		run(config);
		if(afterStart!=null)
			afterStart.accept(config);
	}

	private static void installCloudJARs() throws Exception {
		Cloud cloud = Cloud.current();
		if(cloud==null)
			return;
		Collection<URL> jars = cloud.getJarURsL();
		if(jars==null)
			return;
		jars = filterOutAlreadyLoadedJars(jars);
		addJarsToSystemClassLoader(jars);
	}
	
	private static Collection<URL> filterOutAlreadyLoadedJars(Collection<URL> jars) {
		URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Set<URL> alreadyLoaded = new HashSet<>(Arrays.asList(loader.getURLs()));
		return jars.stream()
				.filter(u->!alreadyLoaded.contains(u))
				.collect(Collectors.toList());
	}

	 private static void addJarsToSystemClassLoader(Collection<URL> jars) throws Exception {
		URLClassLoader loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true); /*promote the method to public access*/
        for(URL url : jars) {
        	logger.debug(()->"Adding JAR " + url.toString() + " to system class loader...");
        	method.invoke(loader, new Object[] { url });
        }
    }

	public static void initialize(IServerConfiguration config) throws Throwable {
		ServerIdentifierProcessor.register();
		Standalone.initialize(config);
	}

	public static IServerConfiguration loadConfiguration(String[] args) throws Exception {
		Map<String, String> argsMap = CmdLineParser.read(args);
		IConfigurationReader reader = Standalone.readerFromArgs(argsMap);
		IServerConfiguration config = new ServerConfiguration(reader, argsMap);
		return config.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class));
	}

	private static void run(IServerConfiguration config) throws Throwable {
		IHttpConfiguration http = config.getHttpConfiguration();
		if(http==null) {
			logger.error(()->"Missing http configuration!");
			throw new RuntimeException();
		}
		String serverAboutToStart = config.getServerAboutToStartMethod();
		IExpression argsValue = Standalone.argsToArgValue(config.getArguments());
		String webSite = config.getWebSiteRoot();
		IDebugConfiguration debug = config.getDebugConfiguration();
		if(debug!=null)
			debugServer(debug, http, webSite, serverAboutToStart, argsValue, ()->prepareWebHandlers(config));
		else
			startServer(http, webSite, serverAboutToStart, argsValue, ()->prepareWebHandlers(config), ()->{ Standalone.getGlobalContext().notifyTerminated(); });
	}

	static int debugServer(IDebugConfiguration debug, IHttpConfiguration http, String webSite, String serverAboutToStartMethod, IExpression argValue, Supplier<Handler> handler) throws Throwable {
		DebugRequestServer server = Standalone.startDebugging(debug.getHost(), debug.getPort());
		return startServer(http, webSite, serverAboutToStartMethod, argValue, handler, ()->{
			Standalone.getGlobalContext().notifyTerminated();
			server.stopListening();
		});
	}
	
	
	static int startServer(IHttpConfiguration http, String webSite, String serverAboutToStartMethod, IExpression argValue, Supplier<Handler> handler, Runnable serverStopped) throws Throwable {
		logger.info(()->"Starting web server on port " + http.getPort() + "...");
		jettyServer = new Server(http.getPort());
		ServerConnector sc = prepareMainConnector(http);
		ServerConnector sc2 = prepareRedirectConnector(http);
		if(sc2==null)
			jettyServer.setConnectors(new Connector[] { sc });
		else
			jettyServer.setConnectors(new Connector[] { sc, sc2 });
		jettyServer.setHandler(prepareSecurityHandler(http, handler));
		callServerAboutToStart(serverAboutToStartMethod, argValue);
		AppServer.start(serverStopped);
		final int port = sc.getLocalPort();
		logger.info(()->WEB_SERVER_SUCCESSFULLY_STARTED + port);
		return port;
	}
	
	private static ServerConnector prepareRedirectConnector(IHttpConfiguration http) {
		if(http.getRedirectFrom()==null)
			return null;
		else {
			logger.info(()->"Preparing redirection from port " + http.getRedirectFrom() + " to port " + http.getPort());
			HttpConfiguration config = new HttpConfiguration();
			config.setSecurePort(http.getPort());
			config.setSecureScheme("https");
			ServerConnector sc = new ServerConnector(jettyServer, new HttpConnectionFactory(config));
			sc.setPort(http.getRedirectFrom());
			return sc;
		}
	}

	private static ServerConnector prepareMainConnector(IHttpConfiguration http) throws Exception {
		ServerConnector sc = "http".equalsIgnoreCase(http.getProtocol()) ?
				prepareHttpConnector(http) :
				prepareHttpsConnector(http);
		if(http.getPort()!=-1)
			sc.setPort(http.getPort());
		return sc;
	}
	
	private static ServerConnector prepareHttpConnector(IHttpConfiguration http) {
		return new ServerConnector(jettyServer);
	}

	private static ServerConnector prepareHttpsConnector(IHttpConfiguration http) throws Exception {
		SslConnectionFactory ssl = createSSLFactory(http);
		HttpConnectionFactory https = createHttpsFactory();
		return new ServerConnector(jettyServer, ssl, https);
	}

	
	private static SslConnectionFactory createSSLFactory(IHttpConfiguration http) throws Exception {
		SslContextFactory context = new SslContextFactory();
		context.setSslSessionTimeout(180000);
		IKeyStoreConfiguration ksc = http.getKeyStoreConfiguration();
		IKeyStoreFactoryConfiguration ksfc = ksc.getKeyStoreFactoryConfiguration();
		IKeyStoreFactory factory = ksfc.getKeyStoreFactory();
		context.setKeyStore(factory.newInstance(ksfc));
		ISecretKeyConfiguration secret = ksc.getSecretKeyConfiguration();
		context.setKeyStorePassword(ISecretKeyFactory.plainPasswordFromConfig(secret));
		ksc = http.getTrustStoreConfiguration();
		ksfc = ksc.getKeyStoreFactoryConfiguration();
		factory = ksfc.getKeyStoreFactory();
		context.setTrustStore(factory.newInstance(ksfc));
		secret = ksc.getSecretKeyConfiguration();
		context.setTrustStorePassword(ISecretKeyFactory.plainPasswordFromConfig(secret));
		return new SslConnectionFactory(context, "http/1.1");
	}

	private static HttpConnectionFactory createHttpsFactory() {
		HttpConfiguration https = new HttpConfiguration();
		https.addCustomizer(new SecureRequestCustomizer());
		return new HttpConnectionFactory(https);
	}

	public static void callServerAboutToStart(String serverAboutToStartMethod, IExpression argValue) {
		if(serverAboutToStartMethod!=null) {
			logger.info(()->"Calling startUp method " + serverAboutToStartMethod);
			Interpreter.interpretMethod(Standalone.getGlobalContext(), new Identifier(serverAboutToStartMethod), argValue);
		}
	}

	public static int getHttpPort() {
		for(Connector c : jettyServer.getConnectors()) {
			if(c instanceof ServerConnector)
				return ((ServerConnector)c).getLocalPort();
		}
		return 0;
	}

	static Handler prepareSecurityHandler(IHttpConfiguration config, Supplier<Handler> handler) {
		ILoginConfiguration login = config.getLoginConfiguration();
		if(login==null) {
			logger.info(()->"Not using security handler!");
			return handler.get();
		} else try {
			logger.info(()->"Preparing security handler...");
			ConstraintSecurityHandler security = config.getAllowsXAuthorization() && config.getAllowedOrigins()!=null ?
				new ConstraintSecurityHandlerWithXAuthorization() :
				new ConstraintSecurityHandler();	
			security.setLoginService(prepareLoginService(login)); // where to check credentials
			security.setAuthenticator(prepareAuthenticator(config, login)); // how to request credentials
			security.setConstraintMappings(prepareConstraintMappings()); // when to require security
			security.setHandler(handler.get());
			logger.info(()->"Security handler successfully prepared.");
			return security;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Authenticator prepareAuthenticator(IHttpConfiguration config, ILoginConfiguration login) {
		boolean xauth = config.getAllowsXAuthorization();
		ILoginMethodFactory factory = login.getLoginMethodFactory();
		return factory.newAuthenticator(xauth);
	}

	private static List<ConstraintMapping> prepareConstraintMappings() {
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

	private static LoginService prepareLoginService(ILoginConfiguration login) throws Exception {
		String loginModuleName = login.getLoginModuleFactory().installLoginModule();
		JAASLoginService loginService = new JAASLoginService("prompto.login.service");
		loginService.setIdentityService(prepareIdentityService());
		loginService.setLoginModuleName(loginModuleName);
		jettyServer.addBean(loginService);
		return loginService;
	}

	private static IdentityService prepareIdentityService() {
		return new DefaultIdentityService();
	}
	
	static Handler prepareWebHandlers(IServerConfiguration config) {
		try {
			logger.info(()->"Preparing web handlers...");
			HandlerList list = new HandlerList();
			if(config.getHttpConfiguration().getRedirectFrom()!=null)
				list.addHandler(new SecuredRedirectHandler());
			list.addHandler(newResourceHandler("/", config.getWebSiteRoot()));
			list.addHandler(newServiceHandler("/ws/", config.getHttpConfiguration()));
			list.addHandler(new DefaultHandler());
			logger.info(()->"Web handlers successfully prepared.");
			return list;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	static ServletContextHandler newServiceHandler(String path, IHttpConfiguration config) throws Exception {
		URL url = getRootURL();
		ServletContextHandler sch = newServiceHandler(path, url, config.getSendsXAuthorization());
		if(config.getAllowedOrigins()!=null)
			installCrossOriginHandler(sch, path, config.getAllowedOrigins());
		return sch;
	}
	
    private static void installCrossOriginHandler(ServletContextHandler handler, String path, String allowedOrigins) {
		logger.info(()->"Setting allowed origins to: "  + allowedOrigins);
		FilterHolder holder = new FilterHolder();
		holder.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
		holder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, allowedOrigins);
		holder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD,OPTIONS");
		holder.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,X-Authorization,Content-Type,Accept,Origin,Access-Control-Allow-Origin");
		// holder.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, "false");
		holder.setFilter(new LoggingCrossOriginFilter());
		handler.addFilter(holder, "/*", EnumSet.of(DispatcherType.REQUEST));
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
		logger.info(()->"Serving web site at: " + file.getAbsolutePath());
		if(!file.exists())
			throw new FileNotFoundException(webSite);
		return file.getCanonicalFile();
	}


	public static void installHandler(String path, IMethodDeclaration method) {
		// TODO check path (must start with '/') and method prototype
		ServletContextHandler handler = jettyServer.getChildHandlerByClass(ServletContextHandler.class);
        handler.addServlet(new UserServlet(method).getHolder(), path);       
	}
	
	public static ServletContextHandler newServiceHandler(String path, URL base, boolean sendsXAutorization) {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath(path);
        handler.setResourceBase(base.toExternalForm());
        handler.addServlet(new ControlServlet().getHolder(), "/control/*");
        handler.addServlet(new BinaryServlet().getHolder(), "/bin/*");
        handler.addServlet(new DataServlet().getHolder(), "/data/*");       
        handler.addServlet(new PromptoServlet(sendsXAutorization).getHolder(), "/run/*");    
		return handler;
	}

	static Handler newResourceHandler(String path, String webSite) throws Exception {
		Handler handler = webSite!=null ? getWebSiteResourceHandler(webSite) : getCodeStoreResourceHandler();
		ContextHandler ch = new ContextHandler(path);
		ch.setHandler(handler);
		return ch;
	}

	private static ResourceHandler getCodeStoreResourceHandler() throws Exception {
		ResourceHandler rh = new CodeStoreResourceHandler();
		rh.setBaseResource(getBuiltInsResource());
		rh.setDirectoriesListed(false);
		return rh;
	}

	private static ResourceHandler getWebSiteResourceHandler(String webSite) throws Exception {
		List<Resource> list = new ArrayList<>();
		File file = getWebSiteFile(webSite);
		Resource resource = Resource.newResource(file);
		list.add(resource);
		list.add(getBuiltInsResource());
		resource = new ResourceCollection(list.toArray(new Resource[list.size()]));
		ResourceHandler rh = new ResourceHandler();
		rh.setBaseResource(resource);
		rh.setWelcomeFiles(new String[] { "index.html" });
		rh.setDirectoriesListed(false);
		return rh;
	}
	
	private static Resource getBuiltInsResource() throws Exception {
		List<Resource> resources = ObjectUtils.getClassesInCallStack().stream()
				.filter(c->c.getName().startsWith("prompto"))
				.map(AppServer::getClassResource)
				.collect(Collectors.toList());
		if(resources.isEmpty()) {
			return getClassResource(AppServer.class); // happens with JUnit
		} else if(resources.size()==1)
			return resources.get(0);
		else
			return new ResourceCollection(resources.toArray(new Resource[resources.size()]));
	}
	
	private static Resource getClassResource(Class<?> klass) {
		try {
			URL root = getClassResourceURL(klass);
			logger.info(()->"Adding resource root: " + root.toExternalForm());
			return Resource.newResource(root);
		} catch(Exception e) {
			logger.error(()->"Unable to load resources from " + klass.getName(), e);
			throw new RuntimeException(e);
		}
	}

	private static URL getClassResourceURL(Class<?> klass) throws MalformedURLException {
		URL root = klass.getProtectionDomain().getCodeSource().getLocation();
		if(root.toExternalForm().endsWith(".jar"))
			root = new URL("jar:" + root.toExternalForm() + "!/");
		return root;
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
				logger.info(()->"Web server about to start...");
				try {
					try {
						jettyServer.start();
						logger.info(()->"Web server started...");
					} finally {
						logger.info(()->"Signaling start completion...");
						synchronized (sync) {
							startComplete = true;
							sync.notify();
						}
					}
					logger.info(()->"Web server thread waiting for completion...");
					jettyServer.join();
					logger.info(()->"Web server thread complete.");
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
		logger.info(()->"Waiting for start completion signal...");
		synchronized (sync) {
			while(!startComplete)
				sync.wait();
		}
		logger.info(()->"Start completion signalled...");
		if(serverThrowable!=null) {
			Throwable t = serverThrowable;
			serverThrowable = null;
			throw t;
		}
	}

	public static void stop() throws Exception {
		if(jettyServer==null || !jettyServer.isStarted())
			throw new RuntimeException("Server is not started!");
		logger.info(()->"Stopping web server...");
		jettyServer.stop();
		logger.info(()->"Web server stopped, waiting for completion...");
		jettyServer.join();
		logger.info(()->"Web server stop complete.");
	}

	public static boolean isStarted() {
		return jettyServer.isStarted();
	}

}
