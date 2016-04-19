package prompto.intrinsic;

import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;

public interface ISerializable {

	public void toJson(JsonGenerator generator, Object instanceId, String fieldName, Map<String, byte[]> binaries);

}
