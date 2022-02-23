package prompto.security.auth.source;

import prompto.utils.Instance;

public interface IAuthenticationSource {
	
	static Instance<IAuthenticationSource> instance = new Instance<>();
	boolean hasLogin(String login);
	boolean checkLogin(String login, String password);
	void createLogin(String login, String password);
	void updateLogin(String login, String password);
}
