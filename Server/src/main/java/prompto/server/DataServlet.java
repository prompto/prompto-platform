package prompto.server;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.error.PromptoError;
import prompto.expression.FetchManyExpression;
import prompto.expression.IFetchExpression;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.literal.IntegerLiteral;
import prompto.parser.ECleverParser;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.utils.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

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
				Map<String, JsonWriter> writers = new HashMap<>();
				resp.setContentType("application/json");
				Object fetched = fetch.fetchRaw(dataStore);
				writeJsonResponseList(fetched, resp.getOutputStream(), dataStore, writers);
			} else
				writeJsonResponseError("Invalid query!", resp.getOutputStream());
		} catch(PromptoError e) {
			writeJsonResponseError("Invalid query!", resp.getOutputStream());
		} catch(Throwable t) {
			t.printStackTrace(System.err);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
	
	private void writeJsonResponseError(String error, OutputStream output) throws IOException {
		logger.warn(()->error);
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeStringField("error", error);
		generator.writeNullField("data");
		generator.writeEndObject();
		generator.flush();
		generator.close();
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

	private static void writeJsonResponseList(Object fetched, OutputStream output, IStore store, Map<String, JsonWriter> writers) throws IOException, PromptoError {
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeNullField("error");
		generator.writeFieldName("data");
		writeJsonList(generator, fetched, store, writers);
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	private static void writeJsonList(JsonGenerator generator, Object fetched, IStore store, Map<String, JsonWriter> writers) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Any[]"); // does not matter here
		generator.writeFieldName("totalLength");
		if(fetched==null) {
			generator.writeNumber(0);
			generator.writeNullField("value");
		} else if(fetched instanceof IStored) {
			generator.writeNumber(1);
			generator.writeFieldName("value");
			generator.writeStartArray();
			writeJsonStored(generator, (IStored)fetched, store, writers);
			generator.writeEndArray();
		} else if(fetched instanceof IStoredIterable) {
			generator.writeNumber(((IStoredIterable)fetched).totalLength());
			generator.writeFieldName("value");
			generator.writeStartArray();
			for(IStored stored : (IStoredIterable)fetched)
				writeJsonStored(generator, stored, store, writers);
			generator.writeEndArray();
		} else
			throw new InvalidParameterException("Type not supported: " + fetched.getClass().getName());
	}
	
	private static void writeJsonStored(JsonGenerator generator, IStored stored, IStore store, Map<String, JsonWriter> writers) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString(readCategory(stored));
		generator.writeFieldName("value");
		generator.writeStartObject();
		generator.writeFieldName("dbId");
		writeJsonRaw(generator, stored.getDbId());
		for(String name : stored.getNames()) {
			if("category".equals(name) || "dbId".equals(name))
				continue;
			writeJsonField(generator, name, stored.getData(name), store, writers);
		}
		generator.writeEndObject();
		generator.writeEndObject();
	}



	private static void writeJsonRaw(JsonGenerator generator, Object value) throws IOException {
		JsonWriter writer = writerForValue(value);
		if(writer!=null)
			writer.apply(generator, value);
		else try {
			generator.writeObject(value);
		} catch(IllegalStateException e) { 
			// No ObjectCodec defined
			generator.writeString(value.toString());
		}
	}

	private static void writeJsonField(JsonGenerator generator, String name, Object value, IStore store, Map<String, JsonWriter> writers) throws IOException {
		JsonWriter writer = writerForName(name, store, writers);
		if(writer==null)
			writer = writerForValue(value);
		generator.writeFieldName(name);
		writer.apply(generator, value);
	}

	static Map<Family, JsonWriter> familyWriters = Stream.of(
			newEntry(Family.BOOLEAN, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Family.INTEGER, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Family.DECIMAL, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(Family.TEXT, (g,o)->g.writeString((String)o)),
			newEntry(Family.UUID, DataServlet::writeUUID),
			newEntry(Family.DATE, DataServlet::writePromptoDate),
			newEntry(Family.TIME, DataServlet::writePromptoTime),
			newEntry(Family.DATETIME, DataServlet::writePromptoDateTime),
			newEntry(Family.VERSION, DataServlet::writePromptoVersion),
			newEntry(Family.CATEGORY, (g,o)->g.writeString("<instance>")), 
			newEntry(Family.IMAGE, DataServlet::writeImage),
			newEntry(Family.BLOB, DataServlet::writeBlob)
		 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	
	private static JsonWriter writerForName(String name, IStore store, Map<String, JsonWriter> writers) throws IOException {
		JsonWriter writer = writers.get(name);
		if(writer!=null)
			return writer;
		AttributeInfo info = store.getAttributeInfo(name);
		if(info==null)
			return null;
		writer = familyWriters.get(info.getFamily());
		if(writer==null)
			throw new IOException("No writer for " + info.getFamily().name());
		if(info.isCollection())
			writer = listJsonWriterFor(writer);
		writers.put(name, writer);
		return writer;
	}

	@SuppressWarnings("unchecked")
	private static JsonWriter listJsonWriterFor(JsonWriter writer) {
		return (g,o)->{
			g.writeStartArray();
			for(Object i : ((Collection<Object>)o))
				writer.apply(g, i);
			g.writeEndArray();
		};
	}

	interface JsonWriter {
		void apply(JsonGenerator generator, Object value) throws IOException;
	}
	
	static <T> Map.Entry<T, JsonWriter> newEntry(T key, JsonWriter writer) {
		return new AbstractMap.SimpleEntry<>(key, writer);
	}
	
	static Map<Class<?>, JsonWriter> classWriters = Stream.of(
			newEntry(Boolean.class, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Long.class, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Double.class, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(String.class, (g,o)->g.writeString((String)o)),
			newEntry(UUID.class, DataServlet::writeUUID),
			newEntry(PromptoDate.class, DataServlet::writePromptoDate),
			newEntry(PromptoTime.class, DataServlet::writePromptoTime),
			newEntry(PromptoDateTime.class, DataServlet::writePromptoDateTime),
			newEntry(PromptoVersion.class, DataServlet::writePromptoVersion),
			newEntry(PromptoBinary.class, DataServlet::writeBinary)
		 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	
	private static JsonWriter writerForValue(Object value) throws IOException {
		if(value==null) 
			return (g,o)->g.writeNull();
		else {
			JsonWriter writer = classWriters.get(value.getClass());
			if(writer==null && Iterable.class.isAssignableFrom(value.getClass()))
				writer = classWriters.get(Iterable.class);
			if(writer==null && IStored.class.isAssignableFrom(value.getClass()))
				writer = classWriters.get(IStored.class);
			if(writer==null)
				writer = (g,o)->g.writeString("<unsupported>");
			return writer;
		}
	}
	
	@SuppressWarnings("unchecked")
	private static String readCategory(IStored value) {
		List<String> categories = (List<String>)((IStored)value).getData("category");
		if(categories==null || categories.size()<1)
			return "<undefined>";
		else
			return categories.get(categories.size()-1);
	}

	
	private static void writeUUID(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("UUID");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writePromptoDate(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Date");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writePromptoTime(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Time");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writePromptoDateTime(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("DateTime");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writePromptoVersion(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Version");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}

	private static void writeImage(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Image");
		generator.writeFieldName("value");
		generator.writeString("<image>");
		generator.writeEndObject();
	}

	private static void writeBlob(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Bob");
		generator.writeFieldName("value");
		generator.writeString("<blob>");
		generator.writeEndObject();
	}

	private static void writeBinary(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Binary");
		generator.writeFieldName("value");
		generator.writeString("<binary>");
		generator.writeEndObject();
	}




}
