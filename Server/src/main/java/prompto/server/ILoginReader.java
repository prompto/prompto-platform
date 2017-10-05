package prompto.server;

import javax.servlet.http.HttpServletRequest;

public interface ILoginReader {
	
	static ILoginReader instance = null;
	
	public static ILoginReader getInstance() {
		return instance;
	}
	
	String getUser(HttpServletRequest request);

}
