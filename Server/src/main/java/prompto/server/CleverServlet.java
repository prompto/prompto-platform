package prompto.server;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.servlet.ServletHolder;

@SuppressWarnings("serial")
public class CleverServlet extends HttpServlet {

	ServletHolder holder;
	
	@Override
	public String getServletName() {
		return this.getClass().getSimpleName();
	}
	
	public void setHolder(ServletHolder holder) {
		this.holder = holder;
	}
	
	public void setMultipartConfig(MultipartConfigElement config) {
		holder.getRegistration().setMultipartConfig(config);
	}

	
}
