package prompto.security;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import org.junit.Before;
import org.junit.Test;

import prompto.config.IHttpConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.config.IStoredLoginConfiguration;
import prompto.memstore.MemStore;
import prompto.security.DigestMethod;
import prompto.security.ILoginModuleFactory;
import prompto.security.StoredPasswordDigestLoginModuleFactory;
import prompto.security.StoredUserInfoCache;
import prompto.server.BaseServerTest;
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
		return new IHttpConfiguration.Inline()
			.withProtocol("http")
			.withPort(port)
			.withLoginConfiguration(new IStoredLoginConfiguration() {

					@Override
					public ILoginModuleFactory getLoginModuleFactory() {
						ILoginModuleFactory factory = new StoredPasswordDigestLoginModuleFactory();
						factory.setLoginConfiguration(this);
						return factory;
					}
					
					@Override
					public IStoreConfiguration getStoreConfiguration() {
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
				});
	}
	
	@Before
	public void before() throws NoSuchAlgorithmException {
		StoredUserInfoCache.KEEP_ALIVE_DELAY = 10;
		store.deleteAll();
		createUserUsingPBKDF2DigestMethod("john", "password");
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

	private void createUserUsingPBKDF2DigestMethod(String login, String password) throws NoSuchAlgorithmException {
		String salt = DigestMethod.newSalt();
		IStorable storable = store.newStorable(Arrays.asList("User"), null);
		storable.setData("login", "john");
		storable.setData("salt", salt);
		storable.setData("method", "PBKDF2");
		String digest = DigestMethod.forName("PBKDF2").digest(password, salt);
		storable.setData("digest", digest);
		store.store(storable);
	}

	static final String HTTP_CODE_PREFIX = "Server returned HTTP response code: ";
	
	private int loadResource(String login, String password) throws Exception {
		URL url = new URL("http://localhost:" + port + "/js/lib/require.js");
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


