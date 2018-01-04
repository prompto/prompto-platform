package prompto.config;

public interface IFormAuthenticationMethodConfiguration extends IAuthenticationMethodConfiguration {

	String getLoginPage();
	String getErrorPage();
}
