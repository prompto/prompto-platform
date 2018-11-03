package prompto.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.error.PromptoError;
import prompto.expression.FetchManyExpression;
import prompto.expression.IFetchExpression;
import prompto.literal.IntegerLiteral;
import prompto.parser.ECleverParser;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.utils.Logger;

@SuppressWarnings("serial")
public class DataServlet extends CleverServlet {

	static Logger logger = new Logger();
	
	static Map<String, IStore> stores;
	
	public static void setStores(Map<String, IStore> stores) {
		DataServlet.stores = stores;
	}
	
	public static Map<String, IStore> getStores() {
		return DataServlet.stores;
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if(path!=null) switch(path) {
		case "/fetch":
			doFetch(req, resp);
			break;
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	protected void doFetch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String store = req.getParameter("store");
			if(store==null || store.trim().isEmpty()) {
				writeJsonResponseError("No store specified!", resp.getOutputStream());
				return;
			}
			IStore dataStore = stores.get(store);
			if(dataStore==null) {
				writeJsonResponseError("Invalid store: " + store, resp.getOutputStream());
				return;
			}
			String query = req.getParameter("query");
			if(query==null || query.trim().isEmpty()) {
				writeJsonResponseError("Empty query!", resp.getOutputStream());
				return;
			}
			String first = req.getParameter("first");
			String last = req.getParameter("last");
			String format = req.getParameter("format");
			if(format==null)
				format = "list";
			ECleverParser parser = new ECleverParser(query);
			IFetchExpression fetch = parser.parse_fetch_store_expression();
			if(fetch==null) {
				writeJsonResponseError("Invalid query: " + query, resp.getOutputStream());
				return;
			}
			adjustQueryRange(fetch, first, last);
			logger.info(()->"Running query: " + fetch.toString());
			if("list".equals(format.toLowerCase())) {
				resp.setContentType("application/json");
				Object fetched = fetch.fetchRaw(dataStore);
				JsonRecordsWriter writer = new JsonRecordsWriter(resp.getOutputStream(), dataStore::getAttributeInfo, dataStore, false);
				writer.writeRecords(fetched);
			} else
				writeJsonResponseError("Invalid query!", resp.getOutputStream());
		} catch(PromptoError e) {
			writeJsonResponseError("Invalid query!", resp.getOutputStream());
		} catch(Throwable t) {
			t.printStackTrace(System.err);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private static void adjustQueryRange(IFetchExpression fetch, String first, String last) {
		if(fetch instanceof FetchManyExpression) {
			FetchManyExpression many = (FetchManyExpression)fetch;
			if(many.getFirst()==null && first!=null)
				many.setFirst(new IntegerLiteral(first));
			if(many.getLast()==null && last!=null)
				many.setLast(new IntegerLiteral(last));
		}
		
	}
	

	@SuppressWarnings("unchecked")
	static String readCategory(IStored value) {
		List<String> categories = (List<String>)((IStored)value).getData("category");
		if(categories==null || categories.size()<1)
			return "<undefined>";
		else
			return categories.get(categories.size()-1);
	}

	




}