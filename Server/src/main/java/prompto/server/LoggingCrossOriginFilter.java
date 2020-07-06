package prompto.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import prompto.utils.Logger;

public class LoggingCrossOriginFilter extends CrossOriginFilter {

	static Logger logger = new Logger();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.debug(()->"CrossOriginFilter: " + request.toString());
		logRequestHeaders(request, "Origin", "Authorization", "X-Authorization");
		super.doFilter(request, response, chain);
		logResponseHeaders(response, CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER);
	}
	
	private void logRequestHeaders(ServletRequest request, String ... headers) {
		for(String header : headers)
			logger.debug(()->header + ": " + ((HttpServletRequest)request).getHeader(header));
		
	}

	private void logResponseHeaders(ServletResponse response, String ... headers) {
		for(String header : headers)
			logger.debug(()->header + ": " + ((HttpServletResponse)response).getHeader(header));
		
	}
}
