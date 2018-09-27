package test.jetty;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.eclipse.jetty.jaas.JAASLoginService;
import org.eclipse.jetty.security.DefaultIdentityService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import prompto.security.auth.JettyLoginModuleBase;
import prompto.security.auth.source.PasswordIsUserNameAuthenticationSource;


/* compare what happens when using web.xml vs embedded Jetty */
@Ignore("For exploration only!")
public class TestWebXml {

	public static void main(String[] args) throws Throwable {
		
		WebXmServer server = new WebXmServer();
		server.start();
		
	}
	
	static class WebXmServer {
		
		Server server;
		int port = 8080;

		public void start() throws Throwable {
			server = new Server(port);
			// add login service
			JettyLoginModuleBase.install(PasswordIsUserNameAuthenticationSource.class.getName(), ()->null);
			JAASLoginService loginService = new JAASLoginService("prompto.login.service");
			loginService.setIdentityService(new DefaultIdentityService());
			loginService.setLoginModuleName(PasswordIsUserNameAuthenticationSource.class.getName());
			server.addBean(loginService);
			// add web app
			URL resource = Thread.currentThread().getContextClassLoader().getResource("jetty/WEB-INF/web.xml");
			String war = resource.toString();
			war = war.substring(0, war.lastIndexOf('/'));
			war = war.substring(0, war.lastIndexOf('/'));
			WebAppContext webapp = new WebAppContext();
			webapp.setWar(war);
			webapp.setContextPath("/");
			server.setHandler(webapp);
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						server.start();
						server.join();
					} catch(Throwable t) {
						t.printStackTrace(System.err);
					}
				}
			});
			thread.start();
			Thread.sleep(500);
		}
		
		public void stop() throws Throwable {
			server.stop();
		}

		public int getPort() {
			return port;
		}
		
	}
	
	WebXmServer server;
	
	@Before
	public void before() throws Throwable {
		server = new WebXmServer();
		server.start();
	}
	
	@After
	public void after() throws Throwable {
		server.stop();
	}
	
	@Test
	public void testResource() throws Exception {
		URL url = new URL("http://localhost:" + server.getPort() + "/stuff.txt");
		URLConnection cnx = url.openConnection();
		try(InputStream input = cnx.getInputStream()) {
			assertNotNull(input);
			byte[] data = new byte[8];
			int read = input.read(data);
			assertEquals("stuff".length(), read);
		}
	}

}
