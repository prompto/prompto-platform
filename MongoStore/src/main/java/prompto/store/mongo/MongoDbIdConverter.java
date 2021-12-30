package prompto.store.mongo;

import java.util.UUID;

import org.bson.BsonBinary;
import org.bson.types.ObjectId;

public abstract class MongoDbIdConverter {

	public static Object toNative(Object dbId) {
		if(dbId == null)
			return null;
		else if(dbId instanceof UUID)
			return dbId;
		else if(dbId instanceof ObjectId)
			return ((ObjectId) dbId).toHexString(); // NOT a UUID!
		else if(dbId instanceof String)
			return UUID.fromString((String)dbId);
		else if(dbId instanceof BsonBinary)
			return ((BsonBinary)dbId).asUuid();
		else
			return UUID.fromString(String.valueOf(dbId));
	}

}
