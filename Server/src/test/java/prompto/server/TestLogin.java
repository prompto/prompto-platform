package prompto.server;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

import org.apache.xerces.impl.dv.util.Base64;
import org.junit.Test;

import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.ILoginConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.security.PasswordIsUserNameLoginModule;
import prompto.utils.Instance;

public class TestLogin extends BaseServerTest {

	@Override
	protected IHttpConfiguration getHttpConfiguration() {
		return new IHttpConfiguration() {
			@Override public String getProtocol() { return "http"; }
			@Override public int getPort() { return port; }
			@Override public String getAllowedOrigin() { return null; }
			@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { return null; }
			@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { return null; }
			@Override public ILoginConfiguration getLoginConfiguration() { 
				return new ILoginConfiguration() {
					@Override
					public String getModuleName() {
						return TestLoginModule.class.getName();
					}
					@Override public IStoreConfiguration getStoreConfiguration() { return null; }
				}; 
			}
		};
	}
	
	static Instance<Map<String, ?>> moduleOptions = new Instance<>();

	public static class TestLoginModule extends PasswordIsUserNameLoginModule {
		
		@Override
		public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
			super.initialize(subject, callbackHandler, sharedState, options);
			moduleOptions.set(options);
		}
	}

	@Test
	public void testThatConfigIsUsedByLoginModule() throws Exception {
		loadResource();
		Map<String, ?> options = moduleOptions.get();
		assertNotNull(options);
		ILoginConfiguration config = (ILoginConfiguration)options.get("config");
		assertNotNull(config);
	}

	private void loadResource() throws Exception {
		URL url = new URL("http://localhost:" + port + "/js/lib/require.js");
		URLConnection cnx = url.openConnection();
		String authorization = Base64.encode("a:a".getBytes());
		cnx.setRequestProperty("Authorization", "Basic " + authorization);		
		try (InputStream input = cnx.getInputStream()) {
		}
	}
}
