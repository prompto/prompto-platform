package prompto.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eclipse.jetty.servlet.ServletHolder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import prompto.store.DataStore;
import prompto.utils.Logger;
import prompto.value.DocumentValue;

@SuppressWarnings("serial")
public class CleverServlet extends HttpServlet {

	static final Logger logger = new Logger();

	public static ThreadLocal<String> REGISTERED_ORIGINS = ThreadLocal.withInitial(() -> null);
	public static ThreadLocal<HttpServletRequest> CURRENT_REQUEST = ThreadLocal.withInitial(() -> null);
	public static ThreadLocal<HttpServletResponse> CURRENT_RESPONSE = ThreadLocal.withInitial(() -> null);

	ServletHolder holder;

	public CleverServlet() {
		installThreadLocalCleaners();
	}
	
	private void installThreadLocalCleaners() {
		getServletContext().addListener(new ServletRequestListener() {

			@Override
			public void requestInitialized(ServletRequestEvent sre) {
				DataStore.useGlobal();
			}

			@Override
			public void requestDestroyed(ServletRequestEvent sre) {
				DataStore.useGlobal();
			}

			
		});
	}

	@Override
	public String getServletName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		HttpUserReader.readAndSet(req);
		readSession(req);
		CURRENT_REQUEST.set(req);
		CURRENT_RESPONSE.set(resp);
		REGISTERED_ORIGINS.set(readRegisteredOrigins(req));
		try {
			super.service(req, resp);
		} finally {
			REGISTERED_ORIGINS.set(null);
			CURRENT_REQUEST.set(null);
			CURRENT_RESPONSE.set(null);
		}
	}

	private void readSession(HttpServletRequest req) {
		DocumentValue doc = (DocumentValue) req.getSession(true).getAttribute("__prompto_http_session__");
		if (doc == null) {
			doc = new DocumentValue();
			req.getSession(true).setAttribute("__prompto_http_session__", doc);
		}
		AppServer.setHttpSession(doc);
	}

	private String readRegisteredOrigins(HttpServletRequest req) {
		return String.join(",", readRegisteredOrigin(req, false), readRegisteredOrigin(req, true));
	}

	private String readRegisteredOrigin(HttpServletRequest req, boolean withPort) {
		StringBuilder sb = new StringBuilder()
				.append(req.getScheme())
				.append("://")
				.append(req.getServerName());
		if (withPort)
			sb.append(":")
				.append(req.getServerPort());
		return sb.toString();
	}

	public void setHolder(ServletHolder holder) {
		this.holder = holder;
	}

	public void setMultipartConfig(MultipartConfigElement config) {
		holder.getRegistration().setMultipartConfig(config);
	}

	protected void writeJSONError(String message, ServletOutputStream output) throws IOException {
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeStringField("error", message);
		generator.writeNullField("data");
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	protected void writeJSONResult(Object result, ServletOutputStream output) throws IOException {
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.setCodec(new ObjectMapper());
		generator.writeStartObject();
		generator.writeNullField("error");
		generator.writeObjectField("data", result);
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	protected Map<String, byte[]> readParts(HttpServletRequest req) throws ServletException, IOException {
		Map<String, byte[]> parts = new HashMap<>();
		for (Part part : req.getParts())
			parts.put(part.getName(), readPartData(part));
		return parts;
	}

	private byte[] readPartData(Part part) throws IOException {
		try (InputStream input = part.getInputStream()) {
			try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
				byte[] buffer = new byte[4096];
				while (true) {
					int read = input.read(buffer);
					if (read < 0)
						break;
					if (read > 0)
						output.write(buffer, 0, read);
				}
				output.flush();
				return output.toByteArray();
			}
		}
	}

	protected void writeJsonResponseError(String error, OutputStream output) throws IOException {
		logger.warn(() -> error);
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeStringField("error", error);
		generator.writeNullField("data");
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

}
