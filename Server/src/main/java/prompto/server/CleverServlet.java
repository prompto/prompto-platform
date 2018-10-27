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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.eclipse.jetty.servlet.ServletHolder;

import prompto.utils.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class CleverServlet extends HttpServlet {

	static final Logger logger = new Logger();

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

	protected void writeJsonResponseError(String error, OutputStream output) throws IOException {
		logger.warn(()->error);
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeStringField("error", error);
		generator.writeNullField("data");
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	
}
