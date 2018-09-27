package prompto.config.auth.source;

import prompto.security.auth.source.IAuthenticationSourceFactory;

@FunctionalInterface
public interface IAuthenticationSourceConfiguration {

	IAuthenticationSourceFactory getAuthenticationSourceFactory();

}
