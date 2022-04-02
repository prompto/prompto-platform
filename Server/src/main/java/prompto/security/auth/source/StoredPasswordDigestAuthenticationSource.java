package prompto.security.auth.source;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

import org.eclipse.jetty.jaas.spi.UserInfo;

import prompto.config.auth.source.IStoredAuthenticationSourceConfiguration;
import prompto.security.auth.JettyLoginModuleBase;
import prompto.security.auth.StoredUserInfoCache;

public class StoredPasswordDigestAuthenticationSource extends JettyLoginModuleBase {

	StoredUserInfoCache cache;
	
	public StoredPasswordDigestAuthenticationSource() {
		// default constructor called by Jetty, followed by a call to initialize
	}
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		cache = StoredUserInfoCache.initialize((IStoredAuthenticationSourceConfiguration) options.get("config"));
	}

	// handy constructor for offline operations, not using shared cache
	public StoredPasswordDigestAuthenticationSource(IStoredAuthenticationSourceConfiguration config) {
		cache = StoredUserInfoCache.fromConfig(config);
	}

	// handy constructor for testing, not using shared cache
	public StoredPasswordDigestAuthenticationSource(StoredUserInfoCache cache) {
		this.cache = cache;
	}

	@Override
	public UserInfo getUserInfo(String username) throws Exception {
		return cache.getUserInfo(username);
	}
	
	@Override
	public boolean hasLogin(String login) {
		try {
			return cache.hasLogin(login);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	@Override
	public boolean checkLogin(String login, String password) {
		try {
			return cache.checkLogin(login, password);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	@Override
	public void createLogin(String login, String password) {
		try {
			cache.createLogin(login, password);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	@Override
	public void updateLogin(String login, String password) {
		try {
			cache.updateLogin(login, password);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	@Override
	public void finalize() {
		if(cache.isShared())
			return;
		try {
			cache.close();
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

}
