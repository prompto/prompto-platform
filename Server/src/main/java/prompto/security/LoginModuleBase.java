package prompto.security;

import java.security.Provider;
import java.util.Collections;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;

import org.eclipse.jetty.jaas.spi.AbstractLoginModule;

import prompto.config.ILoginConfiguration;

public abstract class LoginModuleBase extends AbstractLoginModule {

	public static void install(final String loginModuleClassName, final ILoginConfiguration config) {
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
				if(name.equals(loginModuleClassName)) {
					AppConfigurationEntry entry  = new AppConfigurationEntry(name, LoginModuleControlFlag.REQUIRED, Collections.singletonMap("config", config));
					return new AppConfigurationEntry[] { entry };
				} else
					return null;
			}
		};
		Configuration.setConfiguration(wrapper);
	}



	
}
