package prompto.store.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class StringArrayCodec implements CollectibleCodec<String[]> {

	@Override
	public void encode(BsonWriter writer, String[] value, EncoderContext encoderContext) {
		 writer.writeStartArray();
		 for(String s : value)
			 writer.writeString(s);
		 writer.writeEndArray();
	}

	@Override
	public Class<String[]> getEncoderClass() {
		return String[].class;
	}

	@Override
	public String[] decode(BsonReader reader, DecoderContext decoderContext) {
		List<String> value = new ArrayList<>();
		reader.readStartArray();
		while(reader.readBsonType()!=BsonType.END_OF_DOCUMENT)
			value.add(reader.readString());
		reader.readEndArray();
		return value.toArray(new String[0]);
	}

	@Override
	public String[] generateIdIfAbsentFromDocument(String[] document) {
		return null;
	}

	@Override
	public boolean documentHasId(String[] document) {
		return false;
	}

	@Override
	public BsonValue getDocumentId(String[] document) {
		return null;
	}

}
