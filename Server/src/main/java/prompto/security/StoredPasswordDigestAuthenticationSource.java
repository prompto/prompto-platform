package prompto.security;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

import org.eclipse.jetty.jaas.spi.UserInfo;

import prompto.config.IStoredAuthenticationSourceConfiguration;

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

}
