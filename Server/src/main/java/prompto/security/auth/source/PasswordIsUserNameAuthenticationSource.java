package prompto.security.auth.source;

import java.io.IOException;
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
	public boolean hasLogin(String login) {
		return true;
	}
	
	@Override
	public boolean checkLogin(String login, String password) {
		return true;
	}
	
	@Override
	public void createLogin(String login, String password) {
		// nothing to do
	}
	
	@Override
	public void updateLogin(String login, String password) {
		// nothing to do
	}
	
	@Override
	public void close() throws IOException {
		// nothing to do
	}

}
