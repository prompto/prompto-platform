package prompto.store.mongo;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import prompto.code.Version;

public class PromptoVersionCodec implements CollectibleCodec<Version> {

	@Override
	public void encode(BsonWriter writer, Version value, EncoderContext encoderContext) {
		int val = value.asInt();
		writer.writeInt32(val);
	}

	@Override
	public Class<Version> getEncoderClass() {
		return Version.class;
	}

	@Override
	public Version decode(BsonReader reader, DecoderContext decoderContext) {
		int val = reader.readInt32();
		return Version.parse(val);
	}

	@Override
	public Version generateIdIfAbsentFromDocument(Version document) {
		return null;
	}

	@Override
	public boolean documentHasId(Version document) {
		return false;
	}

	@Override
	public BsonValue getDocumentId(Version document) {
		return null;
	}

}
