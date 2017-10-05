package prompto.server;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Handler;
import org.junit.Test;

import prompto.config.IHttpConfiguration;
import prompto.config.IServerConfiguration;
import prompto.declaration.DeclarationList;
import prompto.expression.IExpression;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.parser.ECleverParser;
import prompto.runtime.Context;
import prompto.runtime.Standalone;
import prompto.type.DictType;
import prompto.type.TextType;
import prompto.utils.Out;
import prompto.utils.SocketUtils;
import prompto.value.ExpressionValue;
import prompto.value.NullValue;

public class TestCustomHandler {

	@Test
	public void testParseAndCheck() throws Throwable {
		int port = SocketUtils.findAvailablePortInRange(8000, 9000);
		IServerConfiguration config = newServerConfiguration(port);
		AppServer.initialize(config);
		try(InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("prompto/customHandler.pec")) {
			ECleverParser parser = new ECleverParser(input);
			DeclarationList decls = parser.parse_declaration_list();
			Context context = Standalone.getGlobalContext();
			decls.register(context);
			decls.check(context);
		}
	}
	
	private IServerConfiguration newServerConfiguration(int port) {
		return new IServerConfiguration.Inline()
			.withHttpConfiguration(new IHttpConfiguration.Inline() 
				.withProtocol("http")
				.withPort(port))
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withApplicationName("test")
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withTestMode(true);
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
			int port = SocketUtils.findAvailablePortInRange(8000, 9000);
			IServerConfiguration config = newServerConfiguration(port);
			AppServer.initialize(config);
			try(InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("prompto/customHandler.pec")) {
				ECleverParser parser = new ECleverParser(input);
				DeclarationList decls = parser.parse_declaration_list();
				Context context = Standalone.getGlobalContext();
				decls.register(context);
			}
			IExpression args = new ExpressionValue(new DictType(TextType.instance()), NullValue.instance());
			AppServer.startServer(config.getHttpConfiguration(), null, "serverAboutToStart", args, this::prepareHandler, null);
			consumer.accept(port);
		} catch(Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			Out.restore();
			AppServer.stop();
		}
	}
	
	Handler prepareHandler() {
		return BaseServerTest.prepareHandler(false);
	}
	
}
