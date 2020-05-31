package prompto.security;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.function.Supplier;

import javax.net.ssl.KeyManagerFactory;
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
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.utils.Instance;
import prompto.utils.ManualTests;

@Category(ManualTests.class)
public class TestServerCertificates {

	static String CERTS_DIR = "/Users/ericvergnaud/Prompto/certificates/latest/JavaCertificates/";
	static Instance<String> password = new Instance<>();
	static Supplier<String> PASSWORD = () -> { 
		if(password.get()==null) try {
			System.out.println("Enter keystore password:");
			String entered = new BufferedReader(new InputStreamReader(System.in)).readLine();
			password.set(entered);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return password.get();
	};
	
	@Test
	public void testLoadCertificate() throws Throwable {
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		KeyStore ks = loadKeyStore(CERTS_DIR + "keystore.jks");
		kmf.init(ks, PASSWORD.get().toCharArray());

	}
	
	
	@Test
	public void testRealCertificates() throws Throwable {
		Server server = new Server();
		ServerConnector connector = createConnector(server);
		server.setConnectors(new Connector[] { connector });
		server.setHandler(createHandler());
		Thread thread = new Thread(() -> {
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
		while(!server.isStarted())
			Thread.sleep(10);
		server.join();
	}

	private ServerConnector createConnector(Server server) throws Exception {
		SslConnectionFactory ssl = createSSLFactory();
		HttpConnectionFactory https = createHttpsFactory();
		ServerConnector sc = new ServerConnector(server, ssl, https);
		sc.setPort(8443);
		return sc;
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

	private SslConnectionFactory createSSLFactory() throws Exception {
		SslContextFactory factory = new SslContextFactory();
		KeyStore ks = loadKeyStore(CERTS_DIR + "keystore.jks");
		factory.setKeyStore(ks);
		factory.setKeyStorePassword(PASSWORD.get()); 
		ks = loadKeyStore(CERTS_DIR + "truststore.jks");
		factory.setTrustStore(ks);
		factory.setTrustStorePassword(PASSWORD.get()); 
		return new SslConnectionFactory(factory, "http/1.1");
	}

	private KeyStore loadKeyStore(String filePath) throws Exception {
		try(InputStream input = new FileInputStream(filePath)) {
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(input, null);
			return ks;
		}
	}

}
