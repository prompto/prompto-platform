package prompto.security;

import java.util.Collections;

import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;

public class PasswordIsUserNameLoginSource extends LoginSourceBase {

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

}
