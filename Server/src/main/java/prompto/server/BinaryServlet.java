package prompto.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDbId;
import prompto.store.DataStore;
import prompto.store.IStore;

@SuppressWarnings("serial")
public class BinaryServlet extends CleverServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		try(var stream = resp.getOutputStream()) {
			String dbIdString = req.getParameter(IStore.dbIdName);
			String attr = req.getParameter("attribute");
			String table = req.getParameter("table");
			IStore store = DataStore.getInstance();
			PromptoDbId dbId = store.convertToDbId(dbIdString);
			PromptoBinary binary = store.fetchBinary(table, dbId, attr);
			if(binary!=null) {
				resp.setContentType(binary.getMimeType());
				stream.write(binary.getBytes());
			} else
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch(Throwable t) {
			t.printStackTrace(System.err);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
