package prompto.server;

import java.io.InputStream;
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
	
	@Test
	public void testInterpret() throws Throwable {
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
			int port = AppServer.startServer(-1, "serverAboutToStart", Application.argsToArgValue(args), BaseServerTest::prepareHandler, null);
			URL url = new URL("http://localhost:" + port + "/ws/test/stuff");
			try(InputStream data = url.openStream()) {}
			assertTrue(Out.read().endsWith("received!"));
		} finally {
			Out.restore();
			AppServer.stop();
		}
	}
	
}
