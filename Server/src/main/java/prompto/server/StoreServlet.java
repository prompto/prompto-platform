package prompto.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.store.IDataStore;
import prompto.store.IQueryBuilder;
import prompto.store.IStoredIterable;
import prompto.utils.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class StoreServlet extends CleverServlet {

	static Logger logger = new Logger();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doStuff(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doStuff(req, resp);
	}
	
	protected void doStuff(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if(path!=null) switch(path) {
		case "/fetchMany":
			fetchMany(req, resp);
			break;
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	protected void fetchMany(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				fetchManyJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				fetchManyUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				fetchManyMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			writeJSONError(t.getMessage(), resp.getOutputStream());
		}

	}
	
	private void fetchManyMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	private void fetchManyUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}
	
	private void fetchManyJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonNode json = readJsonStream(req);
		IQueryBuilder builder = IDataStore.getInstance().newQueryBuilder();
		if(json.has("predicate"))
			readPredicateJson(builder, json.get("predicate"));
		if(json.has("first"))
			builder = builder.first(json.get("first").asLong());
		if(json.has("last"))
			builder = builder.last(json.get("last").asLong());
		if(json.has("orderBys"))
			readOrderBysJson(builder, json.get("orderBys"));
		resp.setContentType("application/json");
		IStoredIterable fetched = IDataStore.getInstance().fetchMany(builder.build());
		resp.setContentType("application/json");
		JsonRecordsWriter writer = new JsonRecordsWriter(resp.getOutputStream(), IDataStore.getInstance());
		writer.writeRecords(fetched);
	}
	
	private void readOrderBysJson(IQueryBuilder builder, JsonNode jsonNode) {
		// TODO Auto-generated method stub
		
	}
	
	private void readPredicateJson(IQueryBuilder builder, JsonNode jsonNode) {
		// TODO Auto-generated method stub
		
	}
	
	private JsonNode readJsonStream(HttpServletRequest req) throws IOException {
		try(InputStream input = req.getInputStream()) {
			return new ObjectMapper().readTree(input);
		}
	}
	
}
