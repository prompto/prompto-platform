package prompto.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import prompto.grammar.Identifier;
import prompto.runtime.Standalone;
import prompto.utils.Logger;
import prompto.value.Document;

@SuppressWarnings("serial")
public class PromptoServlet extends HttpServletWithHolder {

	static final Logger logger = new Logger();
	
	public static ThreadLocal<String> REGISTERED_ORIGIN = ThreadLocal.withInitial(()->null);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir")));
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String origin = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
		REGISTERED_ORIGIN.set(origin);
		if(AppServer.HTTP_ALLOWED_ORIGIN!=null) {
			resp.setHeader("Access-Control-Allow-Origin", AppServer.HTTP_ALLOWED_ORIGIN);
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Origin");
		}
		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			authenticate(req);
			readSession(req);
			ExecutionMode mode = readMode(req);
			Identifier methodName = readMethod(req);
			boolean main = readMain(req);
			String[] httpParams = req.getParameterMap().get("params");
			String jsonParams = httpParams==null || httpParams.length==0 ? null : httpParams[0];
			RequestRouter handler = new RequestRouter(Standalone.getClassLoader(), Standalone.getGlobalContext());
			handler.route(mode, methodName, jsonParams, null, main, resp.getOutputStream());
			resp.setContentType("text/json");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getOutputStream().close();
			resp.flushBuffer();
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}
	
	
	private void writeJSONError(String message, ServletOutputStream output) throws IOException {
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeStringField("error", message);
		generator.writeNullField("data");
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	
	private void readSession(HttpServletRequest req) {
		Document doc = (Document)req.getSession(true).getAttribute("__prompto_http_session__");
		if(doc==null) {
			doc = new Document();
			req.getSession(true).setAttribute("__prompto_http_session__", doc);
		}
		Server.setHttpSession(doc);
	}

	private void authenticate(HttpServletRequest req) {
		IAuthenticator authenticator = IAuthenticator.getInstance();
		String user = authenticator==null ? "<anonymous>" : authenticator.authenticate(req);
		Server.setHttpUser(user);
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
		authenticate(req);
		readSession(req);
		Identifier methodName = readMethod(req);
		ExecutionMode mode = readMode(req);
		boolean main = readMain(req);
		Map<String, byte[]> parts = readParts(req);
		String jsonParams = new String(parts.get("params"));
		RequestRouter handler = new RequestRouter(Standalone.getClassLoader(), Standalone.getGlobalContext());
		resp.setContentType("application/json");
		resp.setStatus(200);
		handler.route(mode, methodName, jsonParams, parts, main, resp.getOutputStream());
		resp.flushBuffer();
		resp.getOutputStream().close();
	}

	private Identifier readMethod(HttpServletRequest req) {
		String method = req.getPathInfo();
		logger.info(()-> "Executing Prompto method: " + method);
		return new Identifier(method.substring(1));
	}

	private ExecutionMode readMode(HttpServletRequest req) {
		String mode = req.getParameter("mode");
		if(mode!=null)
			return ExecutionMode.valueOf(mode.toUpperCase());
		else
			return ExecutionMode.INTERPRET;
	}
	
	private boolean readMain(HttpServletRequest req) {
		String main = req.getParameter("main");
		if(main!=null)
			return Boolean.valueOf(main);
		else
			return false;
	}



	private void doPostJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}

	private void doPostUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}

	private Map<String, byte[]> readParts(HttpServletRequest req) throws ServletException, IOException {
		Map<String, byte[]> parts = new HashMap<>();
		for(Part part : req.getParts())
			parts.put(part.getName(), readPartData(part));
		return parts;
	}

	private byte[] readPartData(Part part) throws IOException {
		try(InputStream input = part.getInputStream()) {
			try(ByteArrayOutputStream output = new ByteArrayOutputStream()) {
				byte[] buffer = new byte[4096];
				while(true) {
					int read = input.read(buffer);
					if(read<0)
						break;
					if(read>0)
						output.write(buffer, 0, read);
				}
				output.flush();
				return output.toByteArray();
			}
		}
	}
}
