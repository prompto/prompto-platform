package prompto.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import prompto.cloud.Cloud;
import prompto.config.IConfigurationReader;
import prompto.config.IDebugConfiguration;
import prompto.config.IHttpConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.ServerConfiguration;
import prompto.config.YamlConfigurationReader;
import prompto.debug.IDebugRequestListener;
import prompto.declaration.IMethodDeclaration;
import prompto.grammar.Identifier;
import prompto.libraries.Libraries;
import prompto.runtime.Interpreter;
import prompto.runtime.Standalone;
import prompto.security.auth.source.IAuthenticationSource;
import prompto.utils.CmdLineParser;
import prompto.utils.Logger;
import prompto.value.Document;

public class AppServer {
	
	static final Logger logger = new Logger();
	
	public static final String WEB_SERVER_SUCCESSFULLY_STARTED = "Web server successfully started on port ";
	
	static JettyServer jettyServer;

	public static void main(String[] args) throws Throwable {
		main(args, null);
	}
	
	public static void main(String[] args, Consumer<IServerConfiguration> afterStart) throws Throwable {
		IServerConfiguration config = loadConfiguration(args);
		main(config, afterStart);
	}

	public static <T extends IServerConfiguration> void main(T config, Consumer<T> afterStart) throws Throwable {
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
		/*
		String webSite = config.getWebSiteRoot();
		*/
		IDebugConfiguration debug = config.getDebugConfiguration();
		if(debug!=null)
			debugServer(debug, config, (jetty, list)->prepareWebHandlers(jetty, list));
		else
			startServer(config, (jetty, list)->prepareWebHandlers(jetty, list), ()->{ Standalone.getGlobalContext().notifyTerminated(); });
	}

	static int debugServer(IDebugConfiguration debug, IServerConfiguration config, BiConsumer<JettyServer, HandlerList> handler) throws Throwable {
		IDebugRequestListener listener = Standalone.startDebugging(debug);
		return startServer(config, handler, ()->{
			Standalone.getGlobalContext().notifyTerminated();
			listener.stopListening();
		});
	}
	
	
	static int startServer(IServerConfiguration config, BiConsumer<JettyServer, HandlerList> handler, Runnable serverStopped) throws Throwable {
		logger.info(()->"Starting web server on port " + config.getHttpConfiguration().getPort() + "...");
		jettyServer = new JettyServer(config);
		jettyServer.prepare(handler);
		callServerAboutToStart(config);
		AppServer.start(serverStopped);
		final int port = jettyServer.getHttpPort();
		logger.info(()->WEB_SERVER_SUCCESSFULLY_STARTED + port);
		return port;
	}
	
	
	public static void callServerAboutToStart(IServerConfiguration config) {
		final String serverAboutToStartMethod = config.getServerAboutToStartMethod();
		if(serverAboutToStartMethod!=null) {
			logger.info(()->"Calling startUp method '" + serverAboutToStartMethod + "'");
			Interpreter.interpretMethod(Standalone.getGlobalContext(), new Identifier(serverAboutToStartMethod), Standalone.argsToArgValue(config.getArguments()));
		}
	}


	static void prepareWebHandlers(JettyServer jetty, HandlerList list) {
		try {
			list.addHandler(jetty.newWebAppHandler());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static int start(Runnable serverStopped) throws Throwable  {
		if(jettyServer.isStarted())
			throw new RuntimeException("Server is already started!");
		jettyServer.jettyStart(serverStopped);
		return jettyServer.getHttpPort();
	}
	
	public static void stop() throws Exception {
		if(jettyServer==null || !jettyServer.isStarted())
			throw new RuntimeException("Server is not started!");
		jettyServer.jettyStop();
	}

	public static boolean isStarted() {
		return jettyServer!=null  && jettyServer.isStarted();
	}
	
	/* used by Server.pec */
	public static long getHttpPort() {
		return jettyServer.getHttpPort();
	}
	
	/* used by Server.pec */
	public static void installHandler(String path, IMethodDeclaration method) {
		// TODO check path (must start with '/') and method prototype
		logger.info(()->"Installing web service '" + method.getName() + "' at path '" + path + "'");
		WebAppContext handler = jettyServer.getChildHandlerByClass(WebAppContext.class);
		UserServlet servlet = new UserServlet(method);
		ServletHolder holder = new ServletHolder(servlet);
		servlet.setHolder(holder);
        handler.addServlet(holder, path);       
	}

	/* used by Server.pec */
	public static IAuthenticationSource getLoginFactory(String config) throws IOException {
		if(config==null)
			return IAuthenticationSource.instance.get();
		else try(InputStream input = new ByteArrayInputStream(config.getBytes())) {
			return new ServerConfiguration(new YamlConfigurationReader(input), Collections.emptyMap())
						.getHttpConfiguration()
						.getAuthenticationConfiguration()
						.getAuthenticationSourceConfiguration()
						.getAuthenticationSourceFactory()
						.newAuthenticationSource();
		} 
	}
	
	static ThreadLocal<String> httpUser = new ThreadLocal<>();
	
	/* used by Server.pec */
	public static String getHttpUser() {
		return httpUser.get();
	}
	
	public static void setHttpUser(String user) {
		httpUser.set(user);
	}
	
	static ThreadLocal<Document> httpSession = new ThreadLocal<>();

	/* used by Server.pec */
	public static Document getHttpSession() {
		return httpSession.get();
	}
	
	public static void setHttpSession(Document session) {
		httpSession.set(session);
	}
}
