package prompto.config;

import prompto.security.IAuthenticationSourceFactory;

@FunctionalInterface
public interface IAuthenticationSourceConfiguration {

	IAuthenticationSourceFactory getAuthenticationSourceFactory();

}
