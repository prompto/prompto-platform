package prompto.security;

import java.security.Provider;
import java.util.Collections;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;

import org.eclipse.jetty.jaas.spi.AbstractLoginModule;
import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;

public class PasswordIsUserNameLoginModule extends AbstractLoginModule {

	static {
		install();
	}
	
	private static void install() {
		Configuration current = Configuration.getConfiguration();
		Configuration wrapper = new Configuration() {

			@Override
			public String getType() {
				return current.getType();
			}
			
			@Override
			public Parameters getParameters() {
				return current.getParameters();
			}
			
			@Override
			public Provider getProvider() {
				return current.getProvider();
			}
			
			@Override
			public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
				AppConfigurationEntry[] entries = current.getAppConfigurationEntry(name);
				if(entries!=null)
					return entries;
				if(name.equals(PasswordIsUserNameLoginModule.class.getName())) {
					AppConfigurationEntry entry  = new AppConfigurationEntry(name, LoginModuleControlFlag.REQUIRED, Collections.emptyMap());
					return new AppConfigurationEntry[] { entry };
				} else
					return null;
			}
		};
		Configuration.setConfiguration(wrapper);
	}



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
