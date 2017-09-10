package prompto.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Test;
import static org.junit.Assert.*;
import prompto.code.Version;
import prompto.config.IDebugConfiguration;
import prompto.config.IHttpConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.declaration.DeclarationList;
import prompto.expression.IExpression;
import prompto.libraries.Libraries;
import prompto.parser.ECleverParser;
import prompto.runtime.Application;
import prompto.runtime.Context;
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
			Context context = Application.getGlobalContext();
			decls.register(context);
			decls.check(context);
		}
	}
	
	private IServerConfiguration newServerConfiguration() {
		return new IServerConfiguration() {
			@Override public void setRuntimeLibsSupplier(Supplier<Collection<URL>> supplier) { }
			@Override public Supplier<Collection<URL>> getRuntimeLibsSupplier() { return ()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class); }
			@Override public IStoreConfiguration getCodeStoreConfiguration() { return null; }
			@Override public IStoreConfiguration getDataStoreConfiguration() { return null; }
			@Override public IDebugConfiguration getDebugConfiguration() { return null; }
			@Override public Map<String, String> getArguments() { return null; }
			@Override public String getApplicationName() { return "test"; }
			@Override public Version getApplicationVersion() { return Version.parse("1.0.0"); }
			@Override public boolean isTestMode() { return true; }
			@Override public URL[] getAddOnURLs() { return null; }
			@Override public URL[] getResourceURLs() { return null; }
			@Override public boolean isLoadRuntime() { return true; }
			@Override public IHttpConfiguration getHttpConfiguration() { return new IHttpConfiguration() {
				@Override public int getPort() { return -1; }
				@Override public String getOrigin() { return null; }
			}; }
			@Override public String getServerAboutToStartMethod() { return null; }
			@Override public String getWebSiteRoot() { return null; }
		};
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
				Context context = Application.getGlobalContext();
				decls.register(context);
			}
			IExpression args = new ExpressionValue(new DictType(TextType.instance()), NullValue.instance());
			int port = AppServer.startServer(config.getHttpConfiguration(), null, "serverAboutToStart", args, BaseServerTest::prepareHandler, null);
			consumer.accept(port);
		} catch(Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			Out.restore();
			AppServer.stop();
		}
	}
	
}
