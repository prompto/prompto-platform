package prompto.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.xerces.impl.dv.util.Base64;
import org.junit.Before;
import org.junit.Test;

import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.ILoginConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.memstore.MemStore;
import prompto.security.StoredPasswordDigestLoginModule;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStoreFactory;

public class TestLoginModule extends BaseServerTest {

	static IStore store = new MemStore(); // need a static to share MemStore across classes

	public static class StoreFactoryTest implements IStoreFactory {

		@Override
		public IStore newStore(IStoreConfiguration config) throws Exception {
			return store;
		}
		
	}
	
	@Override
	protected IHttpConfiguration getHttpConfiguration() {
		return new IHttpConfiguration() {
			@Override public String getProtocol() { return "http"; }
			@Override public int getPort() { return port; }
			@Override public Integer getRedirectFrom() { return null; }
			@Override public String getAllowedOrigin() { return null; }
			@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { return null; }
			@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { return null; }
			@Override public ILoginConfiguration getLoginConfiguration() { 
				return new ILoginConfiguration() {
					@Override
					public String getModuleName() {
						return StoredPasswordDigestLoginModule.class.getName();
					}
					@Override public IStoreConfiguration getStoreConfiguration() { 
						return new IStoreConfiguration() {
							@Override public IStoreConfiguration withDbName(String dbName) { return null; }
							@Override public String getUser() { return null; }
							@Override public Integer getPort() { return null; }
							@Override public ISecretKeyConfiguration getSecretKeyConfiguration() { return null; }
							@Override public String getHost() { return null; }
							@Override
							public String getFactory() {
								return StoreFactoryTest.class.getName();
							}
							@Override public String getDbName() { return null; }
						}; 
					}
				}; 
			}
		};
	}
	
	@Before
	public void before() {
		store.deleteAll();
	}

	@Test
	public void testThatUnknownUserIsRejected() throws Exception {
		int code = loadResource("john", "password");
		assertEquals(401, code);
	}

	@Test
	public void testThatKnownUserIsAllowedWhenUsingCorrectPassword() throws Exception {
		createUserDigestingPasswordWithPBKDF2("john", "password");
		int code = loadResource("john", "password");
		assertEquals(200, code);
	}

	@Test
	public void testThatKnownUserIsRejectedWhenUsingIncorrectPassword() throws Exception {
		createUserDigestingPasswordWithPBKDF2("john", "password");
		int code = loadResource("john", "wrong");
		assertEquals(401, code);
	}

	private void createUserDigestingPasswordWithPBKDF2(String login, String password) throws NoSuchAlgorithmException {
		String salt = StoredPasswordDigestLoginModule.newSalt();
		IStorable storable = store.newStorable(Arrays.asList("User"), null);
		storable.setData("login", "john");
		storable.setData("salt", salt);
		storable.setData("method", "PBKDF2");
		storable.setData("digest", StoredPasswordDigestLoginModule.digest_PBKDF2(password, salt));
		store.store(storable);
	}

	static final String HTTP_CODE_PREFIX = "Server returned HTTP response code: ";
	
	private int loadResource(String login, String password) throws Exception {
		URL url = new URL("http://localhost:" + port + "/js/lib/require.js");
		URLConnection cnx = url.openConnection();
		String authorization = Base64.encode((login + ":" + password).getBytes());
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


