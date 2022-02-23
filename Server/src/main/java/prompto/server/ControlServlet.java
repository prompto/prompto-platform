package prompto.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.graphql.GraphQLServlet;
import prompto.runtime.ApplicationContext;
import prompto.utils.Logger;

@SuppressWarnings("serial")
public class ControlServlet extends CleverServlet {

	static final Logger logger = new Logger();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		try {
			resp.setContentType("text/plain");
			try(var writer = resp.getWriter()) {
				String verb = req.getPathInfo();
				logger.info(()->"Executing control verb " + verb);
				switch(verb) {
					case "/exit":
						exitServer(writer);
						break;
					case "/clear-context":
						clearContext(writer);
						break;
					case "/version":
						version(writer);
						break;
					default:
						logger.error(()->"Invalid control verb: " + verb);
						resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
		} catch(Throwable t) {
			t.printStackTrace(System.err);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void version(PrintWriter writer) {
		writer.write("1.0.0");
		writer.flush();
	}

	private void clearContext(PrintWriter writer) {
		ApplicationContext.reset();
		GraphQLServlet.reset();
	}

	private void exitServer(PrintWriter writer) {
		
		writer.write("Exit command received\n");
		logger.info(()->"Trying to stop server...");
		writer.flush();
		writer.close();
		try {
			AppServer.stop();
			logger.info(()->"Exit command succeeded\n");
		} catch(Exception e) {
			logger.error(()->"Error while stopping server...", e);
		}
		Runtime.getRuntime().exit(0);
	}

}
