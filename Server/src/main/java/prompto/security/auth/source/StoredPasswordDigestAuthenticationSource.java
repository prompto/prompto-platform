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
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		cache = StoredUserInfoCache.initialize((IStoredAuthenticationSourceConfiguration) options.get("config"));
	}

	@Override
	public UserInfo getUserInfo(String username) throws Exception {
		return cache.getUserInfo(username);
	}
	
	@Override
	public void createLogin(String login, String password) {
		try {
			cache.createLogin(login, password);
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

}