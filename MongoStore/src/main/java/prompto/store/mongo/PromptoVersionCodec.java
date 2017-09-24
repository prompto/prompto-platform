package prompto.store.mongo;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import prompto.intrinsic.PromptoVersion;

public class PromptoVersionCodec implements CollectibleCodec<PromptoVersion> {

	@Override
	public void encode(BsonWriter writer, PromptoVersion value, EncoderContext encoderContext) {
		int val = value.asInt();
		writer.writeInt32(val);
	}

	@Override
	public Class<PromptoVersion> getEncoderClass() {
		return PromptoVersion.class;
	}

	@Override
	public PromptoVersion decode(BsonReader reader, DecoderContext decoderContext) {
		int val = reader.readInt32();
		return PromptoVersion.parse(val);
	}

	@Override
	public PromptoVersion generateIdIfAbsentFromDocument(PromptoVersion document) {
		return null;
	}

	@Override
	public boolean documentHasId(PromptoVersion document) {
		return false;
	}

	@Override
	public BsonValue getDocumentId(PromptoVersion document) {
		return null;
	}

}
