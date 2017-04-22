package prompto.store.mongo;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import prompto.intrinsic.PromptoDate;

public class PromptoDateCodec implements CollectibleCodec<PromptoDate> {

	@Override
	public void encode(BsonWriter writer, PromptoDate value, EncoderContext encoderContext) {
		long utc = value.toJavaTime();
		writer.writeInt64(utc);
	}

	@Override
	public Class<PromptoDate> getEncoderClass() {
		return PromptoDate.class;
	}

	@Override
	public PromptoDate decode(BsonReader reader, DecoderContext decoderContext) {
		long utc = reader.readInt64();
		return PromptoDate.fromJavaTime(utc);
	}

	@Override
	public PromptoDate generateIdIfAbsentFromDocument(PromptoDate document) {
		return null;
	}

	@Override
	public boolean documentHasId(PromptoDate document) {
		return false;
	}

	@Override
	public BsonValue getDocumentId(PromptoDate document) {
		return null;
	}

}
