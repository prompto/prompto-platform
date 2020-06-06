package prompto.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.grammar.Identifier;
import prompto.utils.Logger;

@SuppressWarnings("serial")
public class PromptoServlet extends CleverServlet {

	static final Logger logger = new Logger();
	
	boolean sendsXAutorization;
	
	
	public PromptoServlet(boolean sendsXAutorization) {
		this.sendsXAutorization = sendsXAutorization;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir")));
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(sendsXAutorization) {
			logger.debug(()->"PromptoServlet, Authorization: " + req.getHeader("Authorization"));
			if(req.getHeader("Authorization")!=null)
				resp.addHeader("X-Authorization", req.getHeader("Authorization"));
		}
		super.service(req, resp);
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ExecutionMode mode = readMode(req);
			Identifier methodName = readMethod(req);
			boolean main = readMain(req);
			String[] httpParams = req.getParameterMap().get("params");
			String jsonParams = httpParams==null || httpParams.length==0 ? null : httpParams[0];
			RequestRouter handler = new RequestRouter();
			handler.route(mode, methodName, jsonParams, null, main, resp);
			resp.getOutputStream().close();
			resp.flushBuffer();
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				doPostJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				doPostUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				doPostMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}

	private void doPostMultipart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Identifier methodName = readMethod(req);
		ExecutionMode mode = readMode(req);
		boolean main = readMain(req);
		Map<String, byte[]> parts = readParts(req);
		String jsonParams = new String(parts.get("params"));
		RequestRouter handler = new RequestRouter();
		handler.route(mode, methodName, jsonParams, parts, main, resp);
		resp.flushBuffer();
		resp.getOutputStream().close();
	}

	private void doPostJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}

	private void doPostUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}

	private boolean readMain(HttpServletRequest req) {
		String main = req.getParameter("main");
		if(main!=null)
			return Boolean.valueOf(main);
		else
			return false;
	}


	protected Identifier readMethod(HttpServletRequest req) {
		String method = req.getPathInfo();
		logger.debug(()-> "Executing Prompto method: " + method);
		return new Identifier(method.substring(1));
	}

	protected ExecutionMode readMode(HttpServletRequest req) {
		String mode = req.getParameter("mode");
		if(mode!=null)
			return ExecutionMode.valueOf(mode.toUpperCase());
		else
			return ExecutionMode.INTERPRET;
	}

}
