package prompto.security;

import java.util.Collections;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;

import prompto.config.ILoginConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.store.IStore;
import prompto.store.IStoreFactory;

public class PasswordIsStoredLoginModule extends LoginModuleBase {

	IStore store;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		ILoginConfiguration config = (ILoginConfiguration)options.get("config");
		IStoreConfiguration storeConfig = config.getStoreConfiguration();
		try {
			store = IStoreFactory.newStoreFromConfig(storeConfig);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public UserInfo getUserInfo(String username) throws Exception {
		return new UserInfo(username, new PasswordDigestIsStoredCredential(username), Collections.singletonList("*"));
	}
	
	@SuppressWarnings("serial")
	static class PasswordDigestIsStoredCredential extends Credential {

		String username;
		
		public PasswordDigestIsStoredCredential(String username) {
			this.username = username;
		}

		@Override
		public boolean check(Object credentials) {
			return username.equals(credentials);
		}
		
	}


	
}
