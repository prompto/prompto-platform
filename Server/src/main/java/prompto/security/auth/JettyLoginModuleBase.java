package prompto.security.auth;

import java.security.Provider;
import java.util.Collections;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag;
import javax.security.auth.login.Configuration;

import org.eclipse.jetty.jaas.spi.AbstractLoginModule;

import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.security.auth.source.IAuthenticationSource;
import prompto.utils.Logger;

public abstract class JettyLoginModuleBase extends AbstractLoginModule implements IAuthenticationSource {

	static final Logger logger = new Logger();
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		if(IAuthenticationSource.instance.get()!=this) {
			logger.debug(()->"Initializing AuthenticationSource");
			IAuthenticationSource.instance.set(this);
		}
	}
	
	public static void install(final String loginModuleClassName, final IAuthenticationSourceConfiguration config) {
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
