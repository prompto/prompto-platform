package prompto.server;

import java.io.IOException;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.Request;

final class ConstraintSecurityHandlerWithXAuthorization extends ConstraintSecurityHandler {

	protected boolean checkSecurity(Request request) {
		// when using XAuthorization, skip authentication during CORS pre-flight
		boolean test = super.checkSecurity(request) && !"OPTIONS".equals(request.getMethod());
		AppServer.logger.debug(()->"ConstraintSecurityHandler, checking security: " + String.valueOf(test));
		return test;
	}

	public void handle(String pathInContext, 
			org.eclipse.jetty.server.Request baseRequest, 
			javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response) throws IOException ,javax.servlet.ServletException {
		AppServer.logger.debug(()->"ConstraintSecurityHandler: " + request.toString());
		AppServer.logger.debug(()->"Origin: " + request.getHeader("Origin"));
		AppServer.logger.debug(()->"Authorization: " + request.getHeader("Authorization"));
		AppServer.logger.debug(()->"X-Authorization: " + request.getHeader("X-Authorization"));
		super.handle(pathInContext, baseRequest, request, response);
	}
}