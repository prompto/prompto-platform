package prompto.security.auth.source;

import prompto.utils.Instance;

public interface IAuthenticationSource {
	
	static Instance<IAuthenticationSource> instance = new Instance<>();
	
	void createLogin(String login, String password);
}
