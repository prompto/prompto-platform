package prompto.intrinsic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.parser.ECleverParser;
import prompto.type.CharacterType;
import prompto.type.DocumentType;
import prompto.type.IType;
import prompto.utils.IOUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class PromptoDocument<K,V> extends HashMap<K,V> implements ISerializable {

	public V getOrCreate(K key, Class<? extends V> autoCreate) {
		V v = super.get(key);
		if(v==null && autoCreate!=null) try {
			v = autoCreate.newInstance();
			super.put(key, v);
		} catch(IllegalAccessException | InstantiationException e) {
			throw new RuntimeException(e);
		}
		return v;
	}
	
	@Override
	public void toJson(JsonGenerator generator, Object instanceId, String fieldName, Map<String, byte[]> binaries) {
		try {
			generator.writeStartObject();
			generator.writeFieldName("type");
			generator.writeString(DocumentType.instance().getTypeName());
			generator.writeFieldName("value");
			generator.writeStartObject();
			for(Entry<K, V> entry : entrySet()) {
				fieldName = String.valueOf(entry.getKey());
				generator.writeFieldName(fieldName);
				V value = entry.getValue();
				if(value==null)
					generator.writeNull();
				else
					valueToJson(value, generator, System.identityHashCode(this), fieldName, binaries);
			}
			generator.writeEndObject();
			generator.writeEndObject();
		} catch(IOException e) {
			throw new ReadWriteError(e.getMessage());
		}
	}

	private void valueToJson(V value, JsonGenerator generator, Object instanceId, String fieldName, Map<String, byte[]> binaries) throws IOException {
		if(value instanceof ISerializable)
			((ISerializable)value).toJson(generator, instanceId, fieldName, binaries);
		else if(value instanceof Boolean)
			generator.writeBoolean(((Boolean)value).booleanValue());
		else if(value instanceof Long)
			generator.writeNumber(((Long)value).longValue());
		else if(value instanceof Double)
			generator.writeNumber(((Double)value).doubleValue());
		else if(value instanceof String)
			generator.writeString((String)value);
		else if(value instanceof Character) {
			generator.writeStartObject();
			generator.writeFieldName("type");
			generator.writeString(CharacterType.instance().getTypeName());
			generator.writeFieldName("value");
			generator.writeNumber(value.toString());
			generator.writeEndObject();
		} else
			throw new UnsupportedOperationException("valueToJson for " + value.getClass().getName());
	}
	
	public void populateFrom(Object value) {
		if(value instanceof PromptoBinary)
			populateFromBinary((PromptoBinary)value);
		else
			throw new UnsupportedOperationException();
	}

	private void populateFromBinary(PromptoBinary blob) {
		if(!"application/zip".equals(blob.getMimeType()))
			throw new UnsupportedOperationException();
		try {
			Map<String, byte[]> parts = readParts(blob);
			JsonNode value = readValue(parts);
			JsonNode field = value.get("type");
			if(field==null)
				throw new InvalidParameterException("Expecting a 'type' field!");
			IType type = new ECleverParser(field.asText()).parse_standalone_type();
			if(type!=DocumentType.instance())
				throw new InvalidParameterException("Expecting a Document type!");
			field = value.get("value");
			if(field==null)
				throw new InvalidParameterException("Expecting a 'value' field!");
			readJSONValue(field, parts);
		} catch(Exception e) {
			throw new ReadWriteError(e.getMessage());
		}	
	}

	@SuppressWarnings("unchecked")
	private void readJSONValue(JsonNode value, Map<String, byte[]> parts) {
		Iterator<Map.Entry<String, JsonNode>> fields = value.fields();
		while(fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			Object item = readJSONField(field.getValue(), parts);
			this.put((K)field.getKey(), (V)item);
		}
	}
	
	public static Object readJSONField(JsonNode fieldData, Map<String, byte[]> parts) throws PromptoError {
		if(fieldData==null || fieldData.isNull())
			return null;
		else if(fieldData.isBoolean())
			return Boolean.valueOf(fieldData.asBoolean());
		else if(fieldData.isInt() || fieldData.isLong())
			return new Long(fieldData.asLong());
		else if(fieldData.isFloat() || fieldData.isDouble())
			return new Double(fieldData.asDouble());
		else if(fieldData.isTextual())
			return fieldData.asText();
		else if(fieldData.isArray()) {
			throw new UnsupportedOperationException();
		} else if(fieldData.isObject()) {
			throw new UnsupportedOperationException();
		} else
			throw new UnsupportedOperationException();
	}

	public static Map<String, byte[]> readParts(PromptoBinary blob) throws IOException {
		Map<String, byte[]> binaries = new HashMap<>();
		try(ByteArrayInputStream input = new ByteArrayInputStream(blob.getBytes())) {
			try (ZipInputStream zip = new ZipInputStream(input)) {
				for(;;) {
					ZipEntry entry = zip.getNextEntry();
					if(entry==null)
						break;
					byte[] data = IOUtils.readStreamFully(zip);
					binaries.put(entry.getName(), data);
					zip.closeEntry();
				}
				return binaries;
			}
		} 
	}

	public static JsonNode readValue(Map<String, byte[]> parts) throws IOException {
		byte[] data = parts.get("value.json");
		if(data==null)
			throw new InvalidParameterException("Expecting a 'value.json' part!");
		JsonParser parser = new ObjectMapper().getFactory().createParser(data);
		return parser.readValueAsTree();
	}

}
