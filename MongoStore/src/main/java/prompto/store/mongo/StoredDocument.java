package prompto.store.mongo;

import java.util.Set;
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
		else if(dbId instanceof UUID)
			return (UUID)dbId;
		else
			return UUID.fromString(dbId.toString());
	}
	
	@Override
	public boolean hasData(String fieldName) {
		return document.containsKey(fieldName);
	}

	
	@Override
	public Object getRawData(String fieldName) {
		return document.get(fieldName);
	}
	
	
	@Override
	public Object getData(String fieldName) throws PromptoError {
		Object data = document.get(fieldName);
		return data==null ? null : store.readFieldData(fieldName, data);
	}
	
	@Override
	public Set<String> getNames() throws PromptoError {
		Set<String> names = document.keySet();
		names.remove("category");
		names.remove("_id");
		return names;
	}

}
