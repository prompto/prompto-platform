package prompto.store.mongo;

import java.util.UUID;

import org.bson.BSONException;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.UuidRepresentation;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.internal.UuidHelper;

import prompto.intrinsic.PromptoDbId;

public class PromptoDbIdCodec implements Codec<PromptoDbId> {


    @Override
    public void encode(final BsonWriter writer, final PromptoDbId value, final EncoderContext encoderContext) {
    	Object dbId = value.getValue();
    	BsonBinary binaryData = null;
    	if(dbId instanceof BsonBinary)
    		binaryData = (BsonBinary)dbId;
    	else if(dbId instanceof UUID)
    		binaryData = new BsonBinary(BsonBinarySubType.UUID_STANDARD, UuidHelper.encodeUuidToBinary((UUID)dbId, UuidRepresentation.STANDARD));
    	else
    		throw new IllegalArgumentException(dbId.getClass().getName());
    	writer.writeBinaryData(binaryData);
    }

    @Override
    public PromptoDbId decode(final BsonReader reader, final DecoderContext decoderContext) {
        byte subType = reader.peekBinarySubType();

        if (subType != BsonBinarySubType.UUID_LEGACY.getValue() && subType != BsonBinarySubType.UUID_STANDARD.getValue()) {
            throw new BSONException("Unexpected BsonBinarySubType");
        }

        byte[] bytes = reader.readBinaryData().getData();

        return PromptoDbId.of(UuidHelper.decodeBinaryToUuid(bytes, subType, UuidRepresentation.STANDARD));
    }

    @Override
    public Class<PromptoDbId> getEncoderClass() {
        return PromptoDbId.class;
    }

    @Override
    public String toString() {
        return "PromptoDbIdCodec";
    }
}
