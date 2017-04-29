package prompto.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



import org.junit.Test;



import static org.junit.Assert.*;
import prompto.declaration.DeclarationList;
import prompto.parser.ECleverParser;
import prompto.runtime.Application;
import prompto.runtime.Context;
import prompto.utils.Out;

public class TestCustomHandler {

	@Test
	public void testParseAndCheck() throws Throwable {
		String[] args = { "-application", "test", "-testMode", "true" };
		AppServer.initialize(args);
		try(InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("prompto/customHandler.pec")) {
			ECleverParser parser = new ECleverParser(input);
			DeclarationList decls = parser.parse_declaration_list();
			Context context = Application.getGlobalContext();
			decls.register(context);
			decls.check(context);
		}
	}
	
	@FunctionalInterface
	public interface Consumer<T> {
	    void accept(T t) throws Exception;
	}

	@Test
	public void testInterpret_GET() throws Throwable {
		testInterpret((port)->{
			URL url = new URL("http://localhost:" + port + "/ws/ec2/stuff?data=abc&doto=i-efg");
			try(InputStream data = url.openStream()) {}
			String out  = Out.read();
			assertTrue(out.contains("abc"));
			assertTrue(out.contains("i-efg"));
		});
	}	
	
	@Test
	public void testInterpret_POST_JSON() throws Throwable {
		testInterpret((port)->{
			URL url = new URL("http://localhost:" + port + "/ws/git/stuff");
			HttpURLConnection cnx = (HttpURLConnection)url.openConnection();
			cnx.setRequestMethod("POST");
			cnx.setDoInput(true);
			cnx.setDoOutput(true);
			cnx.addRequestProperty("Accept", "application/json");
			cnx.addRequestProperty("Content-type", "application/json");
			byte[] bytes = "{\"data\":123}".getBytes();
			cnx.addRequestProperty("Content-Length", String.valueOf(bytes.length));
			try(OutputStream output = cnx.getOutputStream()) {
				output.write(bytes);
			}
			try(InputStream data = cnx.getInputStream()) {
			}
			assertTrue(Out.read().endsWith("received!"));			
		});
	}		

	
	public void testInterpret(Consumer<Integer> consumer) throws Throwable {
		Out.init();
		try {
			String[] args = { "-application", "test", "-testMode", "true" };
			AppServer.initialize(args);
			try(InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("prompto/customHandler.pec")) {
				ECleverParser parser = new ECleverParser(input);
				DeclarationList decls = parser.parse_declaration_list();
				Context context = Application.getGlobalContext();
				decls.register(context);
			}
			int port = AppServer.startServer(-1, null, "serverAboutToStart", Application.argsToArgValue(args), BaseServerTest::prepareHandler, null);
			consumer.accept(port);
		} catch(Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			Out.restore();
			AppServer.stop();
		}
	}
	
}
