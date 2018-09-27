package prompto.config.auth.method;


public interface IFormAuthenticationMethodConfiguration extends IAuthenticationMethodConfiguration {

	String getLoginPage();
	String getErrorPage();
}
