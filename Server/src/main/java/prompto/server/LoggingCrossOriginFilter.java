package prompto.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import prompto.utils.Logger;

public class LoggingCrossOriginFilter extends CrossOriginFilter {

	static Logger logger = new Logger();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.info(()->"CrossOriginFilter: " + request.toString());
		logger.info(()->"Origin: " + ((HttpServletRequest)request).getHeader("Origin"));
		logger.info(()->"Authorization: " + ((HttpServletRequest)request).getHeader("Authorization"));
		logger.info(()->"X-Authorization: " + ((HttpServletRequest)request).getHeader("X-Authorization"));
		super.doFilter(request, response, chain);
	}
}
