package prompto.server;

import java.io.InputStream;

import org.junit.Test;

import prompto.declaration.DeclarationList;
import prompto.parser.ECleverParser;
import prompto.runtime.Application;
import prompto.runtime.Context;

public class TestCustomHandler {

	@Test
	public void test() throws Throwable {
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
}
