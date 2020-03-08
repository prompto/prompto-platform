package prompto.config;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import prompto.intrinsic.PromptoDocument;
import prompto.runtime.Standalone;

@SuppressWarnings("unchecked")
public class TestApplicationConfigurationAsDocument {
	
	@Test
	public void convertsConfigToDocument() throws IOException {
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("full-config.yml")) {
			IConfigurationReader reader = new YamlConfigurationReader(input);
			Standalone.configuration = new ServerConfiguration(reader, null);
			PromptoDocument<String, Object> config = Standalone.getApplicationConfiguration();
			assertNotNull(config);
			PromptoDocument<String, Object> http = (PromptoDocument<String, Object>)config.get("http");
			assertNotNull(http);
			PromptoDocument<String, Object> auth = (PromptoDocument<String, Object>)http.get("authentication");
			assertNotNull(auth);
			PromptoDocument<String, Object> method = (PromptoDocument<String, Object>)auth.get("method");
			assertNotNull(method);
			assertEquals("/auth/errorPage.html", method.get("errorPage"));
			// not 100% bullet proof, but good enough to start
		}
		
	}

}
