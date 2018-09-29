package prompto.security.auth.source;

import java.util.Collections;

import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;

import prompto.security.auth.JettyLoginModuleBase;

public class PasswordIsUserNameAuthenticationSource extends JettyLoginModuleBase {

	@Override
	public UserInfo getUserInfo(String username) throws Exception {
		return new UserInfo(username, new PasswordIsUserNameCredential(username), Collections.singletonList("*"));
	}
	
	@SuppressWarnings("serial")
	static class PasswordIsUserNameCredential extends Credential {

		String username;
		
		public PasswordIsUserNameCredential(String username) {
			this.username = username;
		}

		@Override
		public boolean check(Object credentials) {
			return username.equals(credentials);
		}
		
	}
	
	@Override
	public void createLogin(String login, String password) {
		// nothing to do
	}

}