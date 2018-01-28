package prompto.security;

import prompto.utils.Instance;

public interface IAuthenticationSource {
	
	static Instance<IAuthenticationSource> instance = new Instance<>();
	
	void createLogin(String login, String password);
}
