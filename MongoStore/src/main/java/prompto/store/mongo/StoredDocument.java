package prompto.store.mongo;

import java.util.UUID;

import org.bson.Document;

import prompto.error.PromptoError;
import prompto.store.IStored;

public class StoredDocument extends BaseDocument implements IStored {

	MongoStore store;
	Document document;

	public StoredDocument(MongoStore store, Document document) {
		this.store = store;
		this.document = document;
	}

	
	@Override
	public UUID getDbId() {
		Object dbId = document.get("_id");
		if(dbId==null)
			return null;
		else
			return UUID.fromString(dbId.toString());
	}

	@Override
	public Object getData(String fieldName) throws PromptoError {
		Object data = document.get(fieldName);
		return store.readFieldData(fieldName, data);
	}

}
