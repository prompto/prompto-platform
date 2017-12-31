package prompto.server;

import java.io.IOException;

import org.eclipse.jetty.server.Request;

import prompto.utils.Logger;

final class ConstraintSecurityHandlerWithXAuthorization extends ConstraintSecurityHandler {

	static final Logger logger = new Logger();

	protected boolean checkSecurity(Request request) {
		// when using XAuthorization, skip authentication during CORS pre-flight
		boolean test = super.checkSecurity(request) && !"OPTIONS".equals(request.getMethod());
		logger.debug(()->"ConstraintSecurityHandler, checking security: " + String.valueOf(test));
		return test;
	}

	public void handle(String pathInContext, 
			org.eclipse.jetty.server.Request baseRequest, 
			javax.servlet.http.HttpServletRequest request, 
			javax.servlet.http.HttpServletResponse response) throws IOException ,javax.servlet.ServletException {
		logger.debug(()->"ConstraintSecurityHandler: " + request.toString());
		logger.debug(()->"Origin: " + request.getHeader("Origin"));
		logger.debug(()->"Authorization: " + request.getHeader("Authorization"));
		logger.debug(()->"X-Authorization: " + request.getHeader("X-Authorization"));
		super.handle(pathInContext, baseRequest, request, response);
	}
}