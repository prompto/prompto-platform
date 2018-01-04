package prompto.config;

import prompto.security.IAuthenticationMethodFactory;

@FunctionalInterface
public interface IAuthenticationMethodConfiguration {

	IAuthenticationMethodFactory  getAuthenticationMethodFactory();

}
