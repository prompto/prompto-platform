package prompto.config.auth.method;

import prompto.security.auth.method.IAuthenticationMethodFactory;

@FunctionalInterface
public interface IAuthenticationMethodConfiguration {

	IAuthenticationMethodFactory  getAuthenticationMethodFactory();

}
