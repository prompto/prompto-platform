package prompto.debug;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.server.CleverServlet;

@SuppressWarnings("serial")
public class DebugRequestServlet extends CleverServlet {

	IDebugger debugger;
	
	public void setDebugger(IDebugger debugger) {
		this.debugger = debugger;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				doPostJson(req, resp);
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
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}
	}

	private void doPostJson(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		IDebugRequest request = Serializer.readDebugRequest(req.getInputStream());
		IDebugResponse response = request.execute(debugger);
		resp.setContentType("application/json");
		Serializer.writeDebugResponse(resp.getOutputStream(), response);
	}

}
