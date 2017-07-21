package prompto.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.runtime.Application;

@SuppressWarnings("serial")
public class ControlServlet extends HttpServletWithHolder {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(AppServer.ALLOWED_ORIGIN!=null) {
			resp.setHeader("Access-Control-Allow-Origin", AppServer.ALLOWED_ORIGIN);
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin");
		}
		super.service(req, resp);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("text/plain");
			PrintWriter writer = resp.getWriter();
			String verb = req.getPathInfo();
			switch(verb) {
				case "/exit":
					exitServer(writer);
					break;
				case "/clear-context":
					clearContext(writer);
					break;
				default:
					System.err.println("Invalid verb: " + verb);
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} catch(Throwable t) {
			t.printStackTrace(System.err);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void clearContext(PrintWriter writer) {
		Application.clearGlobalContext();
	}

	private void exitServer(PrintWriter writer) {
		writer.write("ok");
		writer.flush();
		new Thread(()->{
			try {
				AppServer.stop();
			} catch(Throwable t) {
				t.printStackTrace();
			}
		})
		.start();
	}

}
