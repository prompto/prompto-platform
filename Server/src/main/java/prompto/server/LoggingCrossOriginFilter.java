package prompto.server;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

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
		if(logger.isDebugEnabled()) {
			logger.debug(()->"CrossOriginFilter: " + request.toString());
			logRequestHeaders((HttpServletRequest)request, "*"); // CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "Origin", "Authorization", "X-Authorization");
		}
		super.doFilter(request, response, chain);
		if(logger.isDebugEnabled())
			logResponseHeaders((HttpServletResponse)response, CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER);
	}
	
	private void logRequestHeaders(HttpServletRequest request, String ... headers) {
		if(headers.length==1 && headers[0].equals("*")) {
			Enumeration<String> names = request.getHeaderNames();
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				logger.debug(()->"Request " + name + ": " + request.getHeader(name));
			}
		} else {
			for(String header : headers)
				logger.debug(()->"Request " + header + ": " + ((HttpServletRequest)request).getHeader(header));
		}
		
	}

	private void logResponseHeaders(HttpServletResponse response, String ... headers) {
		if(headers.length==1 && headers[0].equals("*")) {
			Collection<String> names = response.getHeaderNames();
			for(String name : names)
				logger.debug(()->"Request " + name + ": " + ((HttpServletResponse)response).getHeader(name));
		} else {
			for(String header : headers)
				logger.debug(()->"Response " + header + ": " + ((HttpServletResponse)response).getHeader(header));
		}
	}
}
