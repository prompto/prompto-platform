package prompto.security.auth.source;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IHttpConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.auth.IAuthenticationConfiguration;
import prompto.config.auth.source.IStoredAuthenticationSourceConfiguration;
import prompto.security.auth.StoredUserInfoCache;
import prompto.security.auth.method.BasicAuthenticationMethodFactory;
import prompto.security.auth.source.IAuthenticationSourceFactory;
import prompto.security.auth.source.StoredPasswordDigestAuthenticationSourceFactory;
import prompto.server.BaseServerTest;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.store.memory.MemStore;

public class TestStoredLoginSource extends BaseServerTest {

	static IStore store = new MemStore(); // need a static to share MemStore across classes

	public static class StoreFactoryTest implements IStoreFactory {

		@Override
		public IStore newStore(IStoreConfiguration config) throws Exception {
			return store;
		}
		
	}
	
	@Override
	protected IHttpConfiguration getHttpConfiguration() {
		return new IHttpConfiguration.Inline()
			.withProtocol("http")
			.withPort(port)
			.withAuthenticationConfiguration(new IAuthenticationConfiguration.Inline()
				.withAuthenticationMethodConfiguration(() -> new BasicAuthenticationMethodFactory())
				.withAuthenticationSourceConfiguration(new IStoredAuthenticationSourceConfiguration() {

					@Override
					public IAuthenticationSourceFactory getAuthenticationSourceFactory() {
						IAuthenticationSourceFactory factory = new StoredPasswordDigestAuthenticationSourceFactory();
						factory.setConfiguration(this);
						return factory;
					}
					
					@Override
					public IStoreConfiguration getStoreConfiguration() {
						return new IStoreConfiguration.Inline() {
							@Override
							public String getFactory() {
								return StoreFactoryTest.class.getName();
							}
						}; 
					}
					
					@Override
					public void toYaml(YamlMapping yaml) {
						throw new RuntimeException("Should never get there!");
					}
				}));
	}
	
	@Before
	public void before() throws Exception {
		Field field = StoredUserInfoCache.class.getDeclaredField("KEEP_ALIVE_DELAY");
		field.setAccessible(true);
		field.set(null, 10l);
		store.deleteAll();
		StoredUserInfoCache.createLogin(store, "john", "password");
	}

	@Test
	public void testThatUnknownUserIsRejected() throws Exception {
		int code = loadResource("eric", "password");
		assertEquals(401, code);
	}

	@Test
	public void testThatKnownUserIsAllowedWhenUsingCorrectPassword() throws Exception {
		int code = loadResource("john", "password");
		assertEquals(200, code);
	}

	@Test
	public void testThatKnownUserIsRejectedAfterDelay() throws Exception {
		int code = loadResource("john", "password");
		assertEquals(200, code);
		store.deleteAll();
		Thread.sleep(20);
		code = loadResource("john", "password");
		assertEquals(401, code);
	}

	@Test
	public void testThatKnownUserIsRejectedWhenUsingIncorrectPassword() throws Exception {
		int code = loadResource("john", "wrong");
		assertEquals(401, code);
	}

	static final String HTTP_CODE_PREFIX = "Server returned HTTP response code: ";
	
	private int loadResource(String login, String password) throws Exception {
		URL url = new URL("http://localhost:" + port + "/ws/run/getAllAttributes");
		URLConnection cnx = url.openConnection();
		String authorization = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
		cnx.setRequestProperty("Authorization", "Basic " + authorization);		
		try (InputStream input = cnx.getInputStream()) {
			return 200;
		} catch(IOException e) {
			String msg = e.getMessage();
			if(msg.startsWith(HTTP_CODE_PREFIX)) {
				msg = msg.substring(HTTP_CODE_PREFIX.length()) ;
				return Integer.parseInt(msg.substring(0, 3));
			} else
				throw e;
		}
	}
}


