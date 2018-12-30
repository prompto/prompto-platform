package prompto.security.auth.source;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.esotericsoftware.yamlbeans.document.YamlMapping;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.config.IHttpConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.auth.IAuthenticationConfiguration;
import prompto.config.auth.source.IStoredAuthenticationSourceConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.security.auth.StoredUserInfoCache;
import prompto.security.auth.method.BasicAuthenticationMethodFactory;
import prompto.security.auth.source.IAuthenticationSourceFactory;
import prompto.security.auth.source.StoredPasswordDigestAuthenticationSourceFactory;
import prompto.server.BaseServerTest;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.store.memory.MemStore;
import prompto.utils.JsonUtils;

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
	public void unknownUserIsRejected() throws Exception {
		int code = loadResource("eric", "password");
		assertEquals(401, code);
	}

	@Test
	public void knownUserIsAllowedWhenUsingCorrectPassword() throws Exception {
		int code = loadResource("john", "password");
		assertEquals(200, code);
	}

	@Test
	public void knownUserIsRejectedAfterDelay() throws Exception {
		int code = loadResource("john", "password");
		assertEquals(200, code);
		store.deleteAll();
		Thread.sleep(20);
		code = loadResource("john", "password");
		assertEquals(401, code);
	}

	@Test
	public void knownUserIsRejectedWhenUsingIncorrectPassword() throws Exception {
		int code = loadResource("john", "wrong");
		assertEquals(401, code);
	}
	
	@Test
	public void hasLoginReturnsTrueOrFalse() throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("login-factory-tests/default-login-factory.poc");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		tail.setNext(codeResource);	
		JsonNode node = runRemotely("checkHasLogin", paramAsMap("login", "login", "john"));
		assertTrue(node.get("data").asBoolean());
		node = runRemotely("checkHasLogin", paramAsMap("login", "login", "Eric"));
		assertFalse(node.get("data").asBoolean());
	}
	
	@Test
	public void checkLoginReturnsTrueOrFalse() throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("login-factory-tests/default-login-factory.poc");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		tail.setNext(codeResource);	
		JsonNode node = runRemotely("checkUserLogin", paramAsMap("login", "login", "john"), paramAsMap("password", "password", "password"));
		assertTrue(node.get("data").asBoolean());
		node = runRemotely("checkUserLogin", paramAsMap("login", "login", "john"), paramAsMap("password", "password", "wrong"));
		assertFalse(node.get("data").asBoolean());
	}
	

	@Test
	public void createsLogin() throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("login-factory-tests/default-login-factory.poc");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		tail.setNext(codeResource);	
		JsonNode node = runRemotely("checkHasLogin", paramAsMap("login", "login", "eric"));
		assertFalse(node.get("data").asBoolean());
		node = runRemotely("createUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password"));
		assertTrue(node.get("error").isNull());
		node = runRemotely("checkUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password"));
		assertTrue(node.get("data").asBoolean());
	}

	@Test
	public void updatesLogin() throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("login-factory-tests/default-login-factory.poc");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		tail.setNext(codeResource);	
		JsonNode node = runRemotely("createUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password"));
		assertTrue(node.get("error").isNull());
		node = runRemotely("checkUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password"));
		assertTrue(node.get("data").asBoolean());
		node = runRemotely("updateUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password2"));
		assertTrue(node.get("error").isNull());
		node = runRemotely("checkUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password"));
		assertFalse(node.get("data").asBoolean());
		node = runRemotely("checkUserLogin", paramAsMap("login", "login", "eric"), paramAsMap("password", "password", "password2"));
		assertTrue(node.get("data").asBoolean());
	}

	@SafeVarargs
	private final JsonNode runRemotely(String method, Map<String, Object> ... params) throws Exception {
		String paramsString = JsonUtils.objectToJson(Arrays.asList(params));
		URL url = new URL("http://localhost:" + port + "/ws/run/" + method + "?params=" + URLEncoder.encode(paramsString, "UTF-8"));
		URLConnection cnx = url.openConnection();
		String authorization = Base64.getEncoder().encodeToString("john:password".getBytes());
		cnx.setRequestProperty("Authorization", "Basic " + authorization);		
		try (InputStream input = cnx.getInputStream()) {
			return new ObjectMapper().readTree(input);
		}
	}

	private Map<String, Object> paramAsMap(String name, String type, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("type", type);
		map.put("value", value);
		return map;
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


