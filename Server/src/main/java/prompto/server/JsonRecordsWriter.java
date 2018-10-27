package prompto.server;

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class JsonRecordsWriter {
	
	private static interface JsonWriter {
		void accept(JsonGenerator gen, Object val) throws IOException;
	}
	
	JsonGenerator generator;
	IStore store;
	Map<String, JsonWriter> writers = new HashMap<>();
	
	public JsonRecordsWriter(OutputStream output, IStore store) throws IOException {
		this.generator = new JsonFactory().createGenerator(output);
		this.store = store;
	}

	public void writeRecords(Object fetched) throws IOException {
		writeJsonListRecord(fetched);
	}
	
	private void writeJsonListRecord(Object fetched) throws IOException {
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
			writeJsonStored((IStored)fetched);
			generator.writeEndArray();
		} else if(fetched instanceof IStoredIterable) {
			generator.writeFieldName("count");
			generator.writeNumber(((IStoredIterable)fetched).count());
			generator.writeFieldName("totalCount");
			generator.writeNumber(((IStoredIterable)fetched).totalCount());
			generator.writeFieldName("value");
			generator.writeStartArray();
			for(IStored stored : (IStoredIterable)fetched)
				writeJsonStored(stored);
			generator.writeEndArray();
		} else
			throw new InvalidParameterException("Type not supported: " + fetched.getClass().getName());
	}

	private void writeJsonStored(IStored stored) throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("type");
		generator.writeString(DataServlet.readCategory(stored));
		generator.writeFieldName("value");
		generator.writeStartObject();
		generator.writeFieldName("dbId");
		writeJsonRaw( stored.getDbId());
		for(String name : stored.getNames()) {
			if("category".equals(name) || "dbId".equals(name))
				continue;
			writeJsonField(name, stored.getData(name));
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

	private void writeJsonField(String name, Object value) throws IOException {
		JsonWriter writer = writerForName(name);
		if(writer==null)
			writer = writerForValue(value);
		generator.writeFieldName(name);
		writer.accept(generator, value);
	}

	private JsonWriter writerForName(String name) throws IOException {
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
	
	static <T> Map.Entry<T, JsonWriter> newEntry(T key, JsonWriter writer) {
		return new AbstractMap.SimpleEntry<>(key, writer);
	}
	
	static Map<Family, JsonWriter> familyWriters = Stream.of(
			newEntry(Family.BOOLEAN, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Family.INTEGER, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Family.DECIMAL, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(Family.TEXT, (g,o)->g.writeString((String)o)),
			newEntry(Family.UUID, JsonRecordsWriter::writeUUID),
			newEntry(Family.DATE, JsonRecordsWriter::writePromptoDate),
			newEntry(Family.TIME, JsonRecordsWriter::writePromptoTime),
			newEntry(Family.DATETIME, JsonRecordsWriter::writePromptoDateTime),
			newEntry(Family.VERSION, JsonRecordsWriter::writePromptoVersion),
			newEntry(Family.ENUMERATED, (g,o)->g.writeString((String)o)), 
			newEntry(Family.CATEGORY, (g,o)->g.writeString("<instance>")), 
			newEntry(Family.RESOURCE, (g,o)->g.writeString("<instance>")), 
			newEntry(Family.IMAGE, JsonRecordsWriter::writeImage),
			newEntry(Family.BLOB, JsonRecordsWriter::writeBlob)
		 ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	
	static Map<Class<?>, JsonWriter> classWriters = Stream.of(
			newEntry(Boolean.class, (g,o)->g.writeBoolean((Boolean)o)),
			newEntry(Long.class, (g,o)->g.writeNumber(((Number)o).longValue())),
			newEntry(Double.class, (g,o)->g.writeNumber(((Number)o).doubleValue())),
			newEntry(String.class, (g,o)->g.writeString((String)o)),
			newEntry(UUID.class, JsonRecordsWriter::writeUUID),
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

	@SuppressWarnings("unchecked")
	static JsonWriter listJsonWriterFor(JsonWriter writer) {
		return (g,o)->{
			g.writeStartArray();
			for(Object i : ((Collection<Object>)o))
				writer.accept(g, i);
			g.writeEndArray();
		};
	}


}