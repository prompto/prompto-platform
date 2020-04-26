package prompto.server;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.utils.StringUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class JsonRecordsWriter {
	
	private static interface JsonWriter {
		void accept(JsonGenerator gen, Object val) throws IOException;
	}
	
	JsonGenerator generator;
	Function<String, AttributeInfo> fetcher;
	IStore store;
	boolean loadChildren;
	Map<String, JsonWriter> writers = new HashMap<>();
	
	public JsonRecordsWriter(OutputStream output, Function<String, AttributeInfo> fetcher, IStore store, boolean loadChildren) throws IOException {
		this.generator = new JsonFactory().createGenerator(output);
		this.fetcher = fetcher;
		this.store = store;
		this.loadChildren = loadChildren;
	}

	public void writeRecords(Object fetched) throws IOException {
		writeJsonListRecords(fetched);
	}
	
	public void writeRecord(IStored fetched) throws IOException {
		generator.writeStartObject();
		generator.writeNullField("error");
		if(fetched==null)
			generator.writeNullField("data");
		else {
			generator.writeFieldName("data");
			writeJsonRecord(fetched);
		}
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	private void writeJsonListRecords(Object fetched) throws IOException {
		generator.writeStartObject();
		generator.writeNullField("error");
		generator.writeFieldName("data");
		writeJsonList(fetched);
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

	private void writeJsonList(Object fetched) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Any[]"); // does not matter here
		if(fetched==null) {
			generator.writeFieldName("count");
			generator.writeNumber(0);
			generator.writeFieldName("totalCount");
			generator.writeNumber(0);
			generator.writeNullField("value");
		} else if(fetched instanceof IStored) {
			generator.writeFieldName("count");
			generator.writeNumber(1);
			generator.writeFieldName("totalCount");
			generator.writeNumber(1);
			generator.writeFieldName("value");
			generator.writeStartArray();
			writeJsonRecord((IStored)fetched);
			generator.writeEndArray();
		} else if(fetched instanceof IStoredIterable) {
			generator.writeFieldName("count");
			generator.writeNumber(((IStoredIterable)fetched).count());
			generator.writeFieldName("totalCount");
			generator.writeNumber(((IStoredIterable)fetched).totalCount());
			generator.writeFieldName("value");
			generator.writeStartArray();
			for(IStored stored : (IStoredIterable)fetched)
				writeJsonRecord(stored);
			generator.writeEndArray();
		} else
			throw new InvalidParameterException("Type not supported: " + fetched.getClass().getName());
	}

	private void writeJsonRecord(IStored stored) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString(DataServlet.readCategory(stored));
		generator.writeFieldName("value");
		generator.writeStartObject();
		generator.writeFieldName("dbId");
		Object rawDbId = stored.getDbId();
		writeJsonRaw(rawDbId);
		String dbId = rawDbId.toString();
		for(String name : stored.getNames()) {
			if("category".equals(name) || "dbId".equals(name))
				continue;
			writeJsonField(name, stored.getData(name), dbId);
		}
		generator.writeEndObject();
		generator.writeEndObject();
	}

	private void writeJsonRaw(Object value) throws IOException {
		JsonWriter writer = writerForValue(value);
		if(writer!=null)
			writer.accept(generator, value);
		else try {
			generator.writeObject(value);
		} catch(IllegalStateException e) { 
			// No ObjectCodec defined
			generator.writeString(value.toString());
		}
	}

	private void writeJsonField(String name, Object value, String dbId) throws IOException {
		generator.writeFieldName(name);
		if(value==null)
			generator.writeNull();
		else {
			JsonWriter writer = writerForName(name, dbId);
			if(writer==null)
				writer = writerForValue(value);
			writer.accept(generator, value);
		}
	}

	private JsonWriter writerForName(String name, String dbId) throws IOException {
		JsonWriter writer = writers.get(name);
		if(writer!=null)
			return writer;
		AttributeInfo info = fetcher.apply(name);
		if(info==null)
			return null;
		Family family = info.getFamily();
		if(family==Family.IMAGE || family==Family.BLOB)
			return (g,o) -> writeBinary(g, o, family, name, dbId); // don't register dbId closure
		if(loadChildren && (family==Family.CATEGORY || family==Family.RESOURCE))
			writer = this::writeChild;
		else
			writer = familyWriters.get(family);
		if(writer==null)
			throw new IOException("No writer for " + info.getFamily().name());
		if(info.isCollection())
			writer = listJsonWriterFor(writer);
		writers.put(name, writer);
		return writer;
	}
	
	static <T> Map.Entry<T, JsonWriter> newEntry(T key, JsonWriter writer) {
		return new AbstractMap.SimpleEntry<>(key, writer);
	}
	
	static Map<Family, JsonWriter> familyWriters = Stream.of(
			newEntry(Family.BOOLEAN, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Family.INTEGER, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Family.DECIMAL, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(Family.TEXT, (g,o)->g.writeString((String)o)),
			newEntry(Family.UUID, JsonRecordsWriter::writeUuid),
			newEntry(Family.DATE, JsonRecordsWriter::writePromptoDate),
			newEntry(Family.TIME, JsonRecordsWriter::writePromptoTime),
			newEntry(Family.DATETIME, JsonRecordsWriter::writePromptoDateTime),
			newEntry(Family.VERSION, JsonRecordsWriter::writePromptoVersion),
			newEntry(Family.ENUMERATED, JsonRecordsWriter::writeEnumerated),
			newEntry(Family.CATEGORY, (g,o)->g.writeString("<instance>")), 
			newEntry(Family.RESOURCE, (g,o)->g.writeString("<instance>"))
		 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	
	static Map<Class<?>, JsonWriter> classWriters = Stream.of(
			newEntry(Boolean.class, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Long.class, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Double.class, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(String.class, (g,o)->g.writeString((String)o)),
			newEntry(UUID.class, JsonRecordsWriter::writeUuid),
			newEntry(PromptoDate.class, JsonRecordsWriter::writePromptoDate),
			newEntry(PromptoTime.class, JsonRecordsWriter::writePromptoTime),
			newEntry(PromptoDateTime.class, JsonRecordsWriter::writePromptoDateTime),
			newEntry(PromptoVersion.class, JsonRecordsWriter::writePromptoVersion),
			newEntry(PromptoBinary.class, JsonRecordsWriter::writeBinary)
		 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	
	static JsonWriter writerForValue(Object value) throws IOException {
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
	

	private void writeChild(JsonGenerator generator, Object value) throws IOException {
		IStored stored = store.fetchUnique(value);
		if(stored==null)
			generator.writeNull();
		else
			writeJsonRecord(stored);
	}
	
	private static void writeUuid(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Uuid");
		generator.writeFieldName("value");
		generator.writeString(value.toString());
		generator.writeEndObject();
	}
	
	private static void writeEnumerated(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("name");
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

	private static void writeBinary(JsonGenerator generator, Object value) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString("Binary");
		generator.writeFieldName("value");
		generator.writeString("<binary>");
		generator.writeEndObject();
	}

	private static void writeBinary(JsonGenerator generator, Object value, Family family, String fieldName, String dbId) throws IOException {
		if(value instanceof PromptoBinary) {
			generator.writeStartObject();
			generator.writeStringField("type", StringUtils.capitalizeFirst(family.name()));
			generator.writeFieldName("value");
			generator.writeStartObject();
			generator.writeStringField("mimeType", ((PromptoBinary)value).getMimeType());
			generator.writeStringField("url", "/ws/bin/data?dbId=" + dbId + "&attribute=" + fieldName);
			generator.writeEndObject();
			generator.writeEndObject();
		} else
			writeBinary(generator, value);
	}

	@SuppressWarnings("unchecked")
	static JsonWriter listJsonWriterFor(JsonWriter writer) {
		return (g,o)->{
			if(o==null)
				g.writeNull();
			else {
				g.writeStartArray();
				for(Object i : ((Collection<Object>)o))
					writer.accept(g, i);
				g.writeEndArray();
			}
		};
	}


}