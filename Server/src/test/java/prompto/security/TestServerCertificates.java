package prompto.security;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.URLResource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestServerCertificates {

	@Before
	public void before() throws Exception {
		MockTrustManager.install();
	}

	@After
	public void after() {
		MockTrustManager.restore();
	}

	@Test
	public void testLoadCertificates() throws Throwable {
		Server server = createServer();
		Thread thread = new Thread(() -> {
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
		try (InputStream input = new URL(
				"https://localhost:8443/").openStream()) {
			try(Reader reader = new InputStreamReader(input)) {
				try(BufferedReader buffered = new BufferedReader(reader)) {
					assertEquals("Hello", buffered.readLine());
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		} finally {
			server.stop();
			thread.join();
		}
	}

	private Server createServer() {
		SslConnectionFactory ssl = createSSLFactory();
		HttpConnectionFactory https = createHttpsFactory();
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server, ssl, https);
		connector.setPort(8443);
		server.setConnectors(new Connector[] { connector });
		server.setHandler(createHandler());
		return server;
	}

	private Handler createHandler() {
		return new AbstractHandler() {

			@Override
			public void handle(String target, Request baseRequest,
					HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
				response.setContentType("text/plain;charset=ascii");
				response.setStatus(HttpServletResponse.SC_OK);
				baseRequest.setHandled(true);
				response.getWriter().println("Hello");
			}
		};

	}

	private HttpConnectionFactory createHttpsFactory() {
		HttpConfiguration https = new HttpConfiguration();
		https.addCustomizer(new SecureRequestCustomizer());
		return new HttpConnectionFactory(https);
	}

	private SslConnectionFactory createSSLFactory() {
		SslContextFactory factory = new SslContextFactory();
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("security/keystore_test.jks");
		Resource resource = URLResource.newResource(url);
		factory.setKeyStoreResource(resource);
		factory.setKeyStorePassword("password");
		url = Thread.currentThread().getContextClassLoader()
				.getResource("security/truststore_test.jks");
		resource = URLResource.newResource(url);
		factory.setTrustStoreResource(resource);
		factory.setTrustStorePassword("password");
		return new SslConnectionFactory(factory, "http/1.1");
	}

}
