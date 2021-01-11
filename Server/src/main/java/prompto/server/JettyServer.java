package prompto.server;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.security.auth.login.LoginContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.eclipse.jetty.jaas.JAASLoginService;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import prompto.config.IDebugConfiguration;
import prompto.config.IDebugEventAdapterConfiguration;
import prompto.config.IDebugRequestListenerConfiguration;
import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.IKeyStoreFactoryConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.auth.IAuthenticationConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.debug.DebugEventServlet;
import prompto.debug.DebugRequestServlet;
import prompto.debug.HttpServletDebugRequestListenerFactory;
import prompto.debug.WebSocketDebugEventAdapterFactory;
import prompto.graphql.GraphQLServlet;
import prompto.runtime.Mode;
import prompto.security.IKeyStoreFactory;
import prompto.security.ISecretKeyFactory;
import prompto.security.auth.method.IAuthenticationMethodFactory;
import prompto.security.auth.source.IAuthenticationSource;
import prompto.store.DataStore;
import prompto.utils.Logger;

class JettyServer extends Server {

	static final Logger logger = new Logger();
	static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 RuxitSynthetic/1.0 v5282078223 t55095";
	
	IServerConfiguration config;
	Supplier<IAuthenticationConfiguration> auth = null;
	ServerConnector mainConnector;
	ServerConnector redirectConnector;
	ConstraintSecurityHandler securityHandler;
	HandlerList contentHandler;
	DebugRequestServlet debugRequestServlet;
	DebugEventServlet debugEventServlet;
	boolean startComplete = false;
	Thread serverThread = null;
	Throwable serverThrowable = null;
	
	
	public JettyServer(IServerConfiguration config) {
		this.config = config;
		this.setStopAtShutdown(true);
	}

	void jettyStart(Runnable onServerStopped) throws Throwable  {
		serverThrowable = null;
		startComplete = false;
		Object startFlag = new Object();
		serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info(()->"Web server about to start...");
				try {
					try {
						start();
						logger.info(()->"Web server started.");
					} finally {
						logger.info(()->"Signaling start completion...");
						synchronized (startFlag) {
							startComplete = true;
							startFlag.notify();
						}
					}
					logger.info(()->"Web server waiting ready...");
					join();
					logger.info(()->"Web server stop complete.");
					if(onServerStopped!=null)
						onServerStopped.run();
				} catch(Throwable t) {
					t.printStackTrace();
					serverThrowable = t;
				} finally {
					serverThread = null;
				}
			}
		}, "HTTP Server");
		serverThread.start();
		logger.info(()->"Waiting for start completion signal...");
		synchronized (startFlag) {
			while(!startComplete)
				startFlag.wait();
		}
		logger.info(()->"Start completion signalled.");
		forceLoginModuleInitialization();
		if(serverThrowable!=null) {
			Throwable t = serverThrowable;
			serverThrowable = null;
			throw t;
		}
	}
	
	

	private void forceLoginModuleInitialization() {
		IHttpConfiguration http = config.getHttpConfiguration();
		IAuthenticationConfiguration auth = http.getAuthenticationConfiguration();
		if(auth==null) {
			logger.info(()->"No auth required, no need to force LoginModule initialization");
			return; // no need or not possible
		} else {
			doForceLoginModuleInitialization(auth);
			if(IAuthenticationSource.instance.get()==null)
				logger.warn(()->"No authentication source configured!");
			else
				logger.info(()->"Authentication source successfully initialized");
		}
	}

	private void doForceLoginModuleInitialization(IAuthenticationConfiguration auth) {
		try {
			String serviceName = auth.getAuthenticationSourceConfiguration().getAuthenticationSourceFactory().getJettyLoginModuleName();
			LoginContext lc = new LoginContext(serviceName); 
			lc.login();
		} catch(Throwable t) {
			logger.debug(()->"During force LoginModule initialization", t);
		} 
	}
	
	void jettyStop() throws Exception {
		logger.info(()->"Stopping web server...");
		stop();
		// don't join here since it would create a deadlock
	}

	public void prepare(BiConsumer<JettyServer, HandlerList> handler) throws Exception {
		prepareConnectors();
		prepareHandlers(handler);
	}
	
	private void prepareHandlers(BiConsumer<JettyServer, HandlerList> handler) {
		prepareSecurityHandler();
		prepareContentHandler(handler);
		setHandler(contentHandler);
	}

	private void prepareConnectors() throws Exception {
		mainConnector = prepareMainConnector();
		redirectConnector = prepareRedirectConnector();
		if(redirectConnector==null)
			setConnectors(new Connector[] { mainConnector });
		else
			setConnectors(new Connector[] { mainConnector, redirectConnector });
	}

	private ServerConnector prepareMainConnector() throws Exception {
		ServerConnector sc = "http".equalsIgnoreCase(config.getHttpConfiguration().getProtocol()) ?
				prepareHttpConnector() :
				prepareHttpsConnector();
		if(config.getHttpConfiguration().getPort()!=-1)
			sc.setPort(config.getHttpConfiguration().getPort());
		return sc;
	}
	
	private ServerConnector prepareRedirectConnector() {
		if(config.getHttpConfiguration().getRedirectFrom()==null)
			return null;
		else {
			logger.info(()->"Preparing redirection from port " + config.getHttpConfiguration().getRedirectFrom() + " to port " + config.getHttpConfiguration().getPort());
			HttpConfiguration http = new HttpConfiguration();
			http.setSecurePort(config.getHttpConfiguration().getPort());
			http.setSecureScheme("https");
			http.addCustomizer(new SecureRequestCustomizer());
		    ServerConnector sc = new ServerConnector(this, new HttpConnectionFactory(http));
			sc.setPort(config.getHttpConfiguration().getRedirectFrom());
			return sc;
		}
	}

	private ServerConnector prepareHttpConnector() {
		return new ServerConnector(this);
	}


	private ServerConnector prepareHttpsConnector() throws Exception {
		SslConnectionFactory ssl = createSSLFactory();
		HttpConnectionFactory https = createHttpsFactory();
		return new ServerConnector(this, ssl, https);
	}
	
	private SslConnectionFactory createSSLFactory() throws Exception {
		SslContextFactory context = new SslContextFactory();
		context.setSslSessionTimeout(180000);
		IKeyStoreConfiguration ksc = config.getHttpConfiguration().getKeyStoreConfiguration();
		IKeyStoreFactoryConfiguration ksfc = ksc.getKeyStoreFactoryConfiguration();
		IKeyStoreFactory factory = ksfc.getKeyStoreFactory();
		context.setKeyStore(factory.newInstance(ksfc));
		ISecretKeyConfiguration secret = ksc.getSecretKeyConfiguration();
		context.setKeyStorePassword(ISecretKeyFactory.plainPasswordFromConfig(secret));
		ksc = config.getHttpConfiguration().getTrustStoreConfiguration();
		// trust store is optional
		if(ksc!=null) {
			ksfc = ksc.getKeyStoreFactoryConfiguration();
			factory = ksfc.getKeyStoreFactory();
			context.setTrustStore(factory.newInstance(ksfc));
			secret = ksc.getSecretKeyConfiguration();
			context.setTrustStorePassword(ISecretKeyFactory.plainPasswordFromConfig(secret));
		}
		return new SslConnectionFactory(context, "http/1.1");
	}

	private HttpConnectionFactory createHttpsFactory() {
		HttpConfiguration https = new HttpConfiguration();
		https.addCustomizer(new SecureRequestCustomizer());
		return new HttpConnectionFactory(https);
	}

	public int getHttpPort() {
		return mainConnector.getLocalPort();
	}

	private void prepareSecurityHandler() {
		logger.info(()->"Preparing security handler...");
		securityHandler = config.getHttpConfiguration().getAllowsXAuthorization() && config.getHttpConfiguration().getAllowedOrigins()!=null ?
			new ConstraintSecurityHandlerWithXAuthorization() :
			new ConstraintSecurityHandler();	
		if(getAuthenticationConfiguration()!=null)
			configureSecurityHandler();
		else
			logger.info(()->"Not using authentication!");
		logger.info(()->"Security handler successfully prepared.");
	}
	
	private IAuthenticationConfiguration getAuthenticationConfiguration() {
		if(auth==null) {
			IAuthenticationConfiguration instance = config.getHttpConfiguration().getAuthenticationConfiguration();
			auth = ()->instance;
		}
		return auth.get();
	}

	private void configureSecurityHandler() {
		try {
			securityHandler.setLoginService(prepareJettyLoginService()); // where to check credentials
			securityHandler.setAuthenticator(prepareAuthenticator()); // how to request credentials
			securityHandler.setConstraintMappings(prepareAuthConstraintMappings()); // when to require security
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void prepareContentHandler(BiConsumer<JettyServer, HandlerList> handler) {
		logger.info(()->"Preparing web handlers...");
		HandlerList list = new HandlerList();
		if(config.getHttpConfiguration().getRedirectFrom()!=null)
			list.addHandler(new SecuredRedirectHandler());
		handler.accept(this, list);
		contentHandler = list;
		logger.info(()->"Web handlers successfully prepared.");
	}

	private Authenticator prepareAuthenticator() {
		boolean xauth = config.getHttpConfiguration().getAllowsXAuthorization();
		IAuthenticationMethodFactory factory = getAuthenticationConfiguration().getAuthenticationMethodConfiguration().getAuthenticationMethodFactory();
		return factory.newAuthenticator(xauth);
	}

	private LoginService prepareJettyLoginService() throws Exception {
		IAuthenticationSourceConfiguration login = getAuthenticationConfiguration().getAuthenticationSourceConfiguration();
		String loginModuleName = login.getAuthenticationSourceFactory().installJettyLoginModule();
		JAASLoginService loginService = new JAASLoginService("prompto.login.service");
		loginService.setIdentityService(prepareIdentityService());
		loginService.setLoginModuleName(loginModuleName);
		addBean(loginService);
		return loginService;
	}

	private IdentityService prepareIdentityService() {
		return new DefaultIdentityService();
	}
	
	private List<ConstraintMapping> prepareAuthConstraintMappings() {
		Stream<ConstraintMapping> allowed = prepareAllowedConstraintMappings();
		ConstraintMapping protect = new ConstraintMapping();
		protect.setPathSpec("/"); // protect all paths
		protect.setConstraint(prepareAuthenticationConstraint());
		return Stream.concat(allowed, Stream.of(protect))
				.collect(Collectors.toList());
	}
	
	private Stream<ConstraintMapping> prepareAllowedConstraintMappings() {
		Constraint allow = prepareNoAuthenticationConstraint();
		Stream<String> whiteList = getAuthenticationConfiguration().getWhiteList().stream();
		if(config.getRuntimeMode()==Mode.DEVELOPMENT)
			whiteList = Stream.concat(whiteList, Collections.singletonList("/ws/control/*").stream());
		return whiteList.map(path->{
					logger.info(()->"Allowing free access to '" + path + "'");
					ConstraintMapping cm = new ConstraintMapping();
					cm.setPathSpec(path);
					cm.setConstraint(allow);
					return cm;
				});
	}

	private Constraint prepareNoAuthenticationConstraint() {
		Constraint constraint = new Constraint();
	    constraint.setName("no-authentication");
	    constraint.setAuthenticate(false);
	    constraint.setRoles(new String[] { "*" }); // all roles  
		return constraint;
	}

	private Constraint prepareAuthenticationConstraint() {
		Constraint constraint = new Constraint();
	    constraint.setName("authentication");
	    constraint.setAuthenticate(true);
	    constraint.setRoles(new String[] { "**" }); // all authenticated roles 
		return constraint;
	}

	static abstract class WebAppContextBase extends org.eclipse.jetty.webapp.WebAppContext {
		
		public ServletHolder addServlet(CleverServlet servlet, String pathSpec) {
			ServletHolder holder = new ServletHolder(servlet);
			this.addServlet(holder, pathSpec);
			servlet.setHolder(holder);
			return holder;
		};

	}
	
	public static class WebSiteContext extends WebAppContextBase {
		
	}

	public static class WebApiContext extends WebAppContextBase {
		
	}
	
	public static class ThreadLocalCleaner implements ServletRequestListener {

		@Override
		public void requestInitialized(ServletRequestEvent sre) {
			DataStore.useGlobal();
		}

		@Override
		public void requestDestroyed(ServletRequestEvent sre) {
			DataStore.useGlobal();
		}

	}
	

	public WebApiContext newWebApiHandler() throws Exception {
		WebApiContext handler = new WebApiContext();
		handler.setContextPath("/api");
		handler.setResourceBase(getResourceBase());
		handler.addEventListener(new ThreadLocalCleaner());
		// TODO handler.setSecurityHandler(securityHandler);
		if(GraphQLServlet.isEnabled()) {
			logger.info(()->"Starting GraphQL server...");
			handler.addServlet(new GraphQLServlet(), "/graphql");
		} else 
			logger.info(()->"No GraphQL method");
		return handler;		
	}
	
	public WebSiteContext newWebSiteHandler() throws Exception {
		WebSiteContext handler = new WebSiteContext();
		handler.setContextPath("/");
		handler.setResourceBase(getResourceBase());
		handler.addEventListener(new ThreadLocalCleaner());
		handler.setSecurityHandler(securityHandler);
		String welcomePage = config.getHttpConfiguration().getWelcomePage();
		if(config.getWebSiteRoot()!=null)
			handler.addServlet(new WebSiteServlet(config.getWebSiteRoot(), welcomePage), "/");
		else
			handler.addServlet(new CodeStoreServlet(welcomePage), "/");
		handler.addServlet(new TranspilerServlet(), "*.page");   
		handler.addServlet(new ControlServlet(), "/ws/control/*");
		handler.addServlet(new BinaryServlet(), "/ws/bin/*");
		handler.addServlet(new DataServlet(), "/ws/data/*");   
		handler.addServlet(new StoreServlet(), "/ws/store/*"); 
		newDebuggerServlets(handler);
		boolean sendsXAutorization = config.getHttpConfiguration().getSendsXAuthorization();
        handler.addServlet(new PromptoServlet(sendsXAutorization), "/ws/run/*");  
        FilterHolder filterHolder = newCrossOriginHandler();
        if(filterHolder!=null)
        	handler.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
        SessionManager manager = new SessionManager(config.getHttpConfiguration());
        handler.getSessionHandler().setSessionManager(manager);
    	return handler;
	}
	
   private void newDebuggerServlets(WebSiteContext handler) throws Exception {
		IDebugConfiguration debug = config.getDebugConfiguration();
		if(debug==null)
			return;
		// if there is a config, create the servlets and map them
		// they will be wired with the actual debug component instances later
		IDebugRequestListenerConfiguration listener = debug.getRequestListenerConfiguration();
		if(listener!=null) {
			String factoryName = listener.getFactory();
			if(HttpServletDebugRequestListenerFactory.class.getName().equals(factoryName)) {
				debugRequestServlet = new DebugRequestServlet();
				handler.addServlet(debugRequestServlet, "/ws/debug-request/*");  
			}
		}
		IDebugEventAdapterConfiguration adapter = debug.getEventAdapterConfiguration();
		if(adapter!=null) {
			String factoryName = adapter.getFactory();
			if(WebSocketDebugEventAdapterFactory.class.getName().equals(factoryName)) {
				debugEventServlet = new DebugEventServlet();
				ServletHolder servletHolder = new ServletHolder(debugEventServlet);
				handler.addServlet(servletHolder, "/ws/debug-event/*");  
			}
		}
	}


   private FilterHolder newCrossOriginHandler() {
    	final String allowedOrigins = config.getHttpConfiguration().getAllowedOrigins();
    	if(allowedOrigins==null)
			return null;
		logger.info(()->"Setting allowed origins to: "  + allowedOrigins);
		FilterHolder holder = new FilterHolder();
		holder.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
		holder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, allowedOrigins);
		holder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,POST,HEAD,OPTIONS");
		holder.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,X-Authorization,Content-Type,Accept,Origin,Access-Control-Allow-Origin");
		// holder.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, "false");
		holder.setFilter(new LoggingCrossOriginFilter());
		return holder;
	 }

    private String getResourceBase() throws IOException {
    	return getRootURL().toExternalForm();
    }

	private URL getRootURL() throws IOException {
		URL url = AppServer.class.getResource("/js/lib/require.js");
		url = new URL(url.toExternalForm().replace("/js/lib/require.js", "/"));
		// ugly work around for unit tests
		if(url.toExternalForm().contains("/test-classes/"))
			url = new URL(url.toExternalForm().replace("/test-classes/", "/classes/"));
		return url;
	}

	public DefaultHandler newDefaultHandler() {
		return new DefaultHandler();
	}


}