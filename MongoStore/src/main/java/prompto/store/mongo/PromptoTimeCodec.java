package prompto.store.mongo;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import prompto.intrinsic.PromptoTime;

public class PromptoTimeCodec implements CollectibleCodec<PromptoTime> {

	@Override
	public void encode(BsonWriter writer, PromptoTime value, EncoderContext encoderContext) {
		long utc = value.getNativeMillisOfDay();
		writer.writeInt64(utc);
	}

	@Override
	public Class<PromptoTime> getEncoderClass() {
		return PromptoTime.class;
	}

	@Override
	public PromptoTime decode(BsonReader reader, DecoderContext decoderContext) {
		long utc = reader.readInt64();
		return PromptoTime.fromMillisOfDay(utc);
	}

	@Override
	public PromptoTime generateIdIfAbsentFromDocument(PromptoTime document) {
		return null;
	}

	@Override
	public boolean documentHasId(PromptoTime document) {
		return false;
	}

	@Override
	public BsonValue getDocumentId(PromptoTime document) {
		return null;
	}

}
