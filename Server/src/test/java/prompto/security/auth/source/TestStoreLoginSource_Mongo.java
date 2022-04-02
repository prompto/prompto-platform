package prompto.security.auth.source;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prompto.security.auth.StoredUserInfoCache;
import prompto.store.mongo.BaseMongoTest;

public class TestStoreLoginSource_Mongo extends BaseMongoTest {

	IAuthenticationSource source;
	
	@Before
	public void before() {
		createStore("LOGIN_" + System.currentTimeMillis());
		StoredUserInfoCache cache = new StoredUserInfoCache(store, false);
		source = new StoredPasswordDigestAuthenticationSource(cache);
	}
	
	@Test
	public void doesNotHaveLogin() {
		assertFalse(source.hasLogin("john"));
	}
	
	@Test
	public void createsLogin() {
		source.createLogin("john", "password");
		assertTrue(source.hasLogin("john"));
	}

	@Test
	public void checksLogin() {
		source.createLogin("john", "password");
		assertTrue(source.checkLogin("john", "password"));
	}

	@Test
	public void doesNotCheckLogin() {
		source.createLogin("john", "password");
		assertFalse(source.checkLogin("john", "password2"));
	}
	
	@Test
	public void updatesLogin() {
		source.createLogin("john", "password");
		assertTrue(source.checkLogin("john", "password"));
		source.updateLogin("john", "password2");
		assertFalse(source.checkLogin("john", "password"));
		assertTrue(source.checkLogin("john", "password2"));
	}

}
