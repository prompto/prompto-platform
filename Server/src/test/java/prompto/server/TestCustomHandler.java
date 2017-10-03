package prompto.server;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.ILoginConfiguration;
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
import prompto.value.ExpressionValue;
import prompto.value.NullValue;

public class TestCustomHandler {

	@Test
	public void testParseAndCheck() throws Throwable {
		IServerConfiguration config = newServerConfiguration();
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
	
	private IServerConfiguration newServerConfiguration() {
		return new IServerConfiguration.Inline()
			.withHttpConfiguration(new IHttpConfiguration() {
				@Override public String getProtocol() { return "http"; }
				@Override public int getPort() { return -1; }
				@Override public Integer getRedirectFrom() { return null; }
				@Override public String getAllowedOrigin() { return null; }
				@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { return null; }
				@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { return null; }
				@Override public ILoginConfiguration getLoginConfiguration() { return null; }
			})
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withApplicationName("test")
			.withApplicationVersion(PromptoVersion.parse("1.0.0"));
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
			IServerConfiguration config = newServerConfiguration();
			AppServer.initialize(config);
			try(InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("prompto/customHandler.pec")) {
				ECleverParser parser = new ECleverParser(input);
				DeclarationList decls = parser.parse_declaration_list();
				Context context = Standalone.getGlobalContext();
				decls.register(context);
			}
			IExpression args = new ExpressionValue(new DictType(TextType.instance()), NullValue.instance());
			int port = AppServer.startServer(config.getHttpConfiguration(), null, "serverAboutToStart", args, (secure)->BaseServerTest.prepareHandler(null, secure), null);
			consumer.accept(port);
		} catch(Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			Out.restore();
			AppServer.stop();
		}
	}
	
}
