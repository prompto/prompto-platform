package prompto.server;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.servlet.ServletHolder;

@SuppressWarnings("serial")
public class HttpServletWithHolder extends HttpServlet {

	ServletHolder holder = new ServletHolder();
	
	protected HttpServletWithHolder() {
		holder.setServlet(this);
	}
	
	public ServletHolder getHolder() {
		return holder;
	}
	
	public void setMultipartConfig(MultipartConfigElement config) {
		holder.getRegistration().setMultipartConfig(config);
	}
}
