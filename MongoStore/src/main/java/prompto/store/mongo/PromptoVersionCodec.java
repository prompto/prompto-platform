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
		writer.writeInt32(value.asInt());
	}

	@Override
	public Class<PromptoVersion> getEncoderClass() {
		return PromptoVersion.class;
	}

	@Override
	public PromptoVersion decode(BsonReader reader, DecoderContext decoderContext) {
		return PromptoVersion.parseInt(reader.readInt32());
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
