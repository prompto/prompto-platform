package prompto.server;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

public abstract class HttpUserReader {
	
	public static void readAndSet(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String user = principal!=null ? principal.getName() : "<anonymous>";
		Server.setHttpUser(user);
		
	}

}
