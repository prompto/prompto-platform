package prompto.store.mongo;

import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import prompto.intrinsic.PromptoDateTime;

import com.mongodb.MongoClientSettings;

public class PromptoDateTimeCodec implements CollectibleCodec<PromptoDateTime> {

	static Codec<Document> documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
	
	@Override
	public void encode(BsonWriter writer, PromptoDateTime value, EncoderContext encoderContext) {
		long utc = value.toJavaTime();
		String text = value.toString();
		Document document = new Document(); 
		document.put("utc", utc);
		document.put("text", text);
		documentCodec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<PromptoDateTime> getEncoderClass() {
		return PromptoDateTime.class;
	}

	@Override
	public PromptoDateTime decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = documentCodec.decode(reader, decoderContext);
		String text = document.getString("text");
		return PromptoDateTime.parse(text);
	}

	@Override
	public PromptoDateTime generateIdIfAbsentFromDocument(PromptoDateTime document) {
		return null;
	}

	@Override
	public boolean documentHasId(PromptoDateTime document) {
		return false;
	}

	@Override
	public BsonValue getDocumentId(PromptoDateTime document) {
		return null;
	}

}
