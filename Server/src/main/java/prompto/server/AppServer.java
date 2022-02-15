package prompto.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHolder;

import prompto.cloud.Cloud;
import prompto.config.IConfigurationReader;
import prompto.config.IDebugConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.ServerConfiguration;
import prompto.config.YamlConfigurationReader;
import prompto.debug.DebugEventServlet;
import prompto.debug.DebugRequestServlet;
import prompto.debug.HttpServletDebugRequestListener;
import prompto.debug.IDebugEventAdapter;
import prompto.debug.ProcessDebugger;
import prompto.debug.WebSocketDebugEventAdapter;
import prompto.declaration.IMethodDeclaration;
import prompto.error.ReadWriteError;
import prompto.error.TerminatedError;
import prompto.grammar.Identifier;
import prompto.libraries.Libraries;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Context;
import prompto.runtime.Interpreter;
import prompto.runtime.Standalone;
import prompto.security.auth.source.IAuthenticationSource;
import prompto.server.JettyServer.WebSiteContext;
import prompto.utils.JarLoader;
import prompto.utils.CmdLineParser;
import prompto.utils.ErrorLogger;
import prompto.utils.Logger;
import prompto.utils.OutLogger;
import prompto.value.DocumentValue;

public class AppServer {
	
	static final Logger logger = new Logger();
	
	public static final String WEB_SERVER_SUCCESSFULLY_STARTED = "Web server successfully started on port ";
	
	static JettyServer jettyServer;

	public static void main(String[] args) {
		main(args, null);
	}
	
	public static void main(String[] args, Consumer<IServerConfiguration> afterStart) {
		try {
			IServerConfiguration config = loadConfiguration(args);
			main(config, null, null, null, afterStart);
		} catch(Throwable t) {
			logger.error(()->"Uncaught exception!", t);
		}
	}

	public static <T extends IServerConfiguration> void main(T config, Runnable serverPrepared, Runnable serverStarted, Runnable serverStopped, Consumer<T> afterStart) throws Throwable {
		installCloudJARs();
		initialize(config);
		run(config, serverPrepared, serverStarted, serverStopped);
		if(afterStart!=null)
			afterStart.accept(config);
	}

	private static void installCloudJARs() throws Exception {
		Cloud cloud = Cloud.current();
		if(cloud==null)
			return;
		installCloudJARs(cloud);
	}
	
	public static void installCloudJARs(Cloud cloud) throws Exception {
		Collection<URL> urls = cloud.getJarURLs();
		if(urls==null)
			return;
		JarLoader.addURLs(urls);
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

	private static void run(IServerConfiguration config, Runnable serverPrepared, Runnable serverStarted, Runnable serverStopped) throws Throwable {
		Runnable stopped = ()->{ 
			if(serverStopped != null)
				serverStopped.run();
			ApplicationContext.get().notifyCompleted(); 
		};
		startServer(config, (jetty, list)->prepareWebHandlers(jetty, list), serverPrepared, serverStarted, stopped);
	}

	static int startServer(IServerConfiguration config, BiConsumer<JettyServer, HandlerList> handler, Runnable serverPrepared, Runnable serverStarted, Runnable serverStopped) throws Throwable {
		IDebugConfiguration debug = config.getDebugConfiguration();
		if(debug==null)
			return doStartServer(config, handler, ApplicationContext.get(), null, serverPrepared, serverStarted, serverStopped);
		else {
			ProcessDebugger debugger = Standalone.startProcessDebugger(debug);
			Context context = ApplicationContext.get().newLocalContext();
			return doStartServer(config, handler, context, debugger, AppServer::serverPrepared, ()->serverStarted(context), ()->serverStopped(serverStopped));			
		}
	}
	
	static void serverPrepared() {
		if(Standalone.getDebugRequestListener() instanceof HttpServletDebugRequestListener)
			((HttpServletDebugRequestListener)Standalone.getDebugRequestListener()).wire();
		if(Standalone.getDebugEventAdapter() instanceof WebSocketDebugEventAdapter)
			((WebSocketDebugEventAdapter)Standalone.getDebugEventAdapter()).wire();
	}
	
	static void serverStarted(Context context) {
		context.notifyCompleted();
	}
	
	static void serverStopped(Runnable runnable) {
		ApplicationContext.get().notifyCompleted();
		Standalone.stopProcessDebugger();
		if(runnable!=null)
			runnable.run();
	}
	
	static int doStartServer(IServerConfiguration config, BiConsumer<JettyServer, HandlerList> handler, Context context, ProcessDebugger debugger, Runnable serverPrepared, Runnable serverStarted, Runnable serverStopped) throws Throwable {
		logger.info(()->"Starting web server on port " + config.getHttpConfiguration().getPort() + "...");
		jettyServer = new JettyServer(config);
		jettyServer.prepare(handler);
		if(serverPrepared!=null)
			serverPrepared.run();
		AppServer.start(serverStopped);
		final int port = jettyServer.getHttpPort();
		System.out.println(WEB_SERVER_SUCCESSFULLY_STARTED + port); // CodeFactory launcher listens to System.out 
		logger.info(()->WEB_SERVER_SUCCESSFULLY_STARTED + port);
		if(!config.useConsole()) {
			OutLogger.install();
			ErrorLogger.install();
		}
		IDebugEventAdapter adapter = startDebugSession(debugger, context);
		callServerAboutToStart(config, context);
		if(serverStarted!=null)
			serverStarted.run();
		if(adapter!=null)
			adapter.onProcessReadyEvent();
		return port;
	}
	
	
	private static IDebugEventAdapter startDebugSession(ProcessDebugger debugger, Context context) {
		IDebugEventAdapter adapter = Standalone.getDebugEventAdapter();
		if(adapter instanceof WebSocketDebugEventAdapter) {
			((WebSocketDebugEventAdapter)adapter).waitSession();
			Standalone.wireProcessDebugger(debugger, context);
		}
		return adapter;
	}

	public static void callServerAboutToStart(IServerConfiguration config, Context context) {
		final String serverAboutToStartMethod = config.getServerAboutToStartMethod();
		if(serverAboutToStartMethod!=null) try {
			logger.info(()->"Calling startUp method '" + serverAboutToStartMethod + "'");
			Interpreter.interpretMethod(context, new Identifier(serverAboutToStartMethod), Standalone.argsToArgValue(config.getArguments()));
		} catch(TerminatedError e) {
			// not an error
		} catch(Throwable t) {
			logger.error(()->"While calling startUp method " + serverAboutToStartMethod + "'", t);
		} finally {
			context.notifyCompleted();
		}
	}


	static void prepareWebHandlers(JettyServer jetty, HandlerList list) {
		try {
			list.addHandler(jetty.newWebApiHandler());
			list.addHandler(jetty.newWebSiteHandler());
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
	
	
	public static DebugRequestServlet getDebugRequestServlet() {
		return jettyServer==null ? null : jettyServer.debugRequestServlet;
	}

	public static DebugEventServlet getDebugEventServlet() {
		return jettyServer==null ? null : jettyServer.debugEventServlet;
	}

	/* used by Server.pec */
	public static long getHttpPort() {
		return jettyServer!=null ? jettyServer.getHttpPort() : -1;
	}
	
	/* used by Server.pec */
	public static void installHandler(String path, IMethodDeclaration method) {
		// TODO check path (must start with '/') and method prototype
		logger.info(()->"Installing web service '" + method.getName() + "' at path '" + path + "'");
		// TODO move to WebApiContext
		WebSiteContext handler = jettyServer.getChildHandlerByClass(WebSiteContext.class);
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
			return getLoginFactory(new YamlConfigurationReader(input));
		} 
	}
	
	public static IAuthenticationSource getLoginFactory(IConfigurationReader reader) {
		return new ServerConfiguration(reader, Collections.emptyMap())
				.getHttpConfiguration()
				.getAuthenticationConfiguration()
				.getAuthenticationSourceConfiguration()
				.getAuthenticationSourceFactory()
				.newAuthenticationSource();
	}

	static ThreadLocal<String> httpUser = new ThreadLocal<>();
	
	/* used by Server.pec */
	public static String getHttpUser() {
		return httpUser.get();
	}
	
	public static void setHttpUser(String user) {
		httpUser.set(user);
	}
	
	static ThreadLocal<DocumentValue> httpSession = new ThreadLocal<>();

	/* used by Server.pec */
	public static DocumentValue getHttpSession() {
		return httpSession.get();
	}
	
	public static void setHttpSession(DocumentValue session) {
		httpSession.set(session);
	}
	
	/* used by Server.pec */
	public static String getHttpQueryParameter(String name) {
		HttpServletRequest request = CleverServlet.CURRENT_REQUEST.get();
		if(request==null)
			throw new ReadWriteError("Not invoked during server request!");
		return request.getParameter(name);
	}


	/* used by Server.pec */
	public static void httpRedirect(String path) throws IOException {
		HttpServletResponse response = CleverServlet.CURRENT_RESPONSE.get();
		if(response==null)
			throw new ReadWriteError("Not invoked during server request!");
		response.sendRedirect(path);
	}


	/* used by Server.pec */
	public static void httpLogout(String path) throws IOException {
		HttpServletRequest request = CleverServlet.CURRENT_REQUEST.get();
		if(request==null)
			throw new ReadWriteError("Not invoked during server request!");
		request.getSession().invalidate();
		httpRedirect(path);
	}
}
