package prompto.debug;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.debug.request.IDebugRequest;
import prompto.debug.response.IDebugResponse;
import prompto.server.CleverServlet;

@SuppressWarnings("serial")
public class DebugRequestServlet extends CleverServlet {

	IDebugger debugger;
	
	public void setDebugger(IDebugger debugger) {
		this.debugger = debugger;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(var stream = resp.getOutputStream()) {
			try {
				String contentType = req.getContentType();
				if(contentType.startsWith("application/json"))
					doPostJson(req, resp, stream);
				/*
				else if(contentType.startsWith("application/x-www-form-urlencoded"))
					doPostUrlEncoded(req, resp);
				else if(contentType.startsWith("multipart/form-data"))
					doPostMultipart(req, resp); */
				else
					resp.sendError(415);
			} catch(Throwable t) {
				t.printStackTrace();
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				writeJSONError(t.getMessage(), stream);
			}
		}
	}

	private void doPostJson(HttpServletRequest req, HttpServletResponse resp, OutputStream output) throws Exception {
		try(var input = req.getInputStream()) {
			IDebugRequest request = Serializer.readDebugRequest(input);
			IDebugResponse response = request.execute(debugger);
			resp.setContentType("application/json;charset=utf-8");
			Serializer.writeMessage(output, response);
		}
	}


}
