package prompto.server;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.AbstractMap;
import java.util.Collections;
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
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.utils.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

@SuppressWarnings("serial")
public class DataServlet extends HttpServletWithHolder {

	static Logger logger = new Logger();
	
	public static IStore store;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		store = IDataStore.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String query = req.getParameter("query");
			String first = req.getParameter("first");
			String last = req.getParameter("last");
			String format = req.getParameter("format");
			if(format==null)
				format = "raw";
			ECleverParser parser = new ECleverParser(query);
			IFetchExpression fetch = parser.parse_fetch_store_expression();
			adjustQueryRange(fetch, first, last);
			logger.info(()->"Running query: " + fetch.toString());
			if("raw".equals(format.toLowerCase())) {
				resp.setContentType("application/json");
				Object fetched = fetch.fetchRaw(store);
				writeJsonResponseRaw(fetched, resp.getOutputStream());
			} else
				throw new InvalidParameterException("Format not supported: " + format);
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

	private static void writeJsonResponseRaw(Object fetched, OutputStream output) throws IOException, PromptoError {
		JsonGenerator generator = new JsonFactory().createGenerator(output);
		generator.writeStartObject();
		generator.writeNullField("error");
		generator.writeFieldName("data");
		writeJsonDataRaw(generator, fetched);
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	private static void writeJsonDataRaw(JsonGenerator generator, Object fetched) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Any[]");
		generator.writeFieldName("totalLength");
		if(fetched==null) {
			generator.writeNumber(0);
			generator.writeNullField("value");
			return;
		} else if(fetched instanceof IStored) {
			generator.writeNumber(1);
			fetched = Collections.singletonList(fetched); // always return an array
		} else if(fetched instanceof IStoredIterable) {
			generator.writeNumber(((IStoredIterable)fetched).totalLength());
		} else
			throw new InvalidParameterException("Type not supported: " + fetched.getClass().getName());
		generator.writeFieldName("value");
		writeJsonRaw(generator, fetched);
	}

	interface JsonWriter {
		void apply(JsonGenerator generator, Object value) throws IOException;
	}
	
	@SuppressWarnings("rawtypes")
	static Map.Entry<Class, JsonWriter> newEntry(Class klass, JsonWriter writer) {
		return new AbstractMap.SimpleEntry<>(klass, writer);
	}
	
	static Map<Class<?>, JsonWriter> rawWriters = Stream.of(
			newEntry(IStored.class, DataServlet::writeStoredRaw),
			newEntry(Map.class, DataServlet::writeMapRaw),
			newEntry(Iterable.class, DataServlet::writeIterableRaw),
			newEntry(Boolean.class, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Long.class, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Double.class, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(String.class, (g,o)->g.writeString((String)o)),
			newEntry(UUID.class, DataServlet::writeUUIDRaw),
			newEntry(PromptoDate.class, DataServlet::writeDateRaw),
			newEntry(PromptoTime.class, DataServlet::writeTimeRaw),
			newEntry(PromptoDateTime.class, DataServlet::writeDateTimeRaw),
			newEntry(PromptoVersion.class, DataServlet::writeVersionRaw),
			newEntry(PromptoBinary.class, DataServlet::writeBinaryRaw)
		 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	
	private static void writeJsonRaw(JsonGenerator generator, Object value) throws IOException {
		if(value==null) 
			generator.writeNull();
		else {
			JsonWriter writer = rawWriters.get(value.getClass());
			if(writer==null && Iterable.class.isAssignableFrom(value.getClass()))
				writer = rawWriters.get(Iterable.class);
			if(writer==null && IStored.class.isAssignableFrom(value.getClass()))
				writer = rawWriters.get(IStored.class);
			if(writer==null)
				throw new IOException("No writer for " + value.getClass().getName());
			else
				writer.apply(generator, value);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void writeMapRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		for(Map.Entry<String, Object> entry : ((Map<String, Object>)value).entrySet()) {
			generator.writeFieldName(entry.getKey());
			writeJsonRaw(generator, entry.getValue());
		}
		generator.writeEndObject();
	}

	private static void writeStoredRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString(readCategory((IStored)value));
		generator.writeFieldName("value");
		generator.writeStartObject();
		for(String name : ((IStored)value).keySet()) {
			if("category".equals(name))
				continue;
			generator.writeFieldName(name);
			Object field = ((IStored)value).getData(name);
			writeJsonRaw(generator, field);
		}
		generator.writeEndObject();
		generator.writeEndObject();
	}

	@SuppressWarnings("unchecked")
	private static String readCategory(IStored value) {
		List<String> categories = (List<String>)((IStored)value).getData("category");
		return categories.get(categories.size()-1);
	}

	@SuppressWarnings("unchecked")
	private static void writeIterableRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartArray();
		for(Object item : (Iterable<Object>)value)
			writeJsonRaw(generator, item);
		generator.writeEndArray();
	}
	
	private static void writeUUIDRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("UUID");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writeDateRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Date");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writeTimeRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Time");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writeDateTimeRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("DateTime");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writeVersionRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Version");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}

	private static void writeBinaryRaw(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Binary");
		generator.writeFieldName("value");
		generator.writeString("<binary>");
		generator.writeEndObject();
	}


}
