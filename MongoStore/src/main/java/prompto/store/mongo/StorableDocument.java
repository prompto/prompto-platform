package prompto.store.mongo;

import java.io.IOException;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDbId;
import prompto.store.IStorable;

public class StorableDocument extends BaseDocument implements IStorable {

	static final String REMOVED_VALUE = "";
	
	Document document;
	String[] categories;
	IDbIdFactory dbIdFactory;
	boolean isUpdate; // partial updates require operations instead of values
	boolean hasRemovedValue;
	
	public StorableDocument(String[] categories, IDbIdFactory dbIdFactory) {
		this.categories = categories;
		this.dbIdFactory = dbIdFactory;
	}

	@Override
	public void setCategories(String[] categories) throws PromptoError {
		this.categories = categories;
	}
	
	@Override
	public String[] getCategories() {
		return categories;
	}
	
	@Override
	public void setDbId(PromptoDbId dbId) {
		ensureDocument(dbId);
	}
	
	@Override
	public PromptoDbId getOrCreateDbId() {
		ensureDocument(null);
		Object dbId = document.get("_id");
		return PromptoDbId.of(dbId);
	}
	
	@Override
	public void clear() {
		document = null;
		isUpdate = false;
	}

	@Override
	public boolean isDirty() {
		return document!=null;
	}


	public Document getDocument() {
		return document;
	}
	
	private void ensureDocument(Object dbId) {
		if(document==null) {
			if(dbId==null && dbIdFactory!=null)
				dbId = dbIdFactory.get();
			if(dbId!=null)
				isUpdate = dbIdFactory!=null ? dbIdFactory.isUpdate() : true;
			else {
				dbId = java.util.UUID.randomUUID();
				if(dbIdFactory!=null)
					dbIdFactory.accept(PromptoDbId.of(dbId));
			}
			document = new Document();
			document.put("_id", dbId);
			if(categories!=null && !isUpdate)
				document.put("category", categories); 
				
		} else if(dbId!=null)
			document.put("_id", dbId);
	}

	@Override
	public void setData(String name, Object value) throws PromptoError {
		ensureDocument(null);
		if(value instanceof PromptoBinary)
			value = toBytes((PromptoBinary)value);
		document.put(name, value);
	}
	
	@Override
	public void removeData(String name) throws PromptoError {
		ensureDocument(null);
		if(this.isUpdate) {
			setData(name, REMOVED_VALUE);
			hasRemovedValue = true;
		} else {
			ensureDocument(null);
			document.remove(name);
		}
	}


	private byte[] toBytes(PromptoBinary binary) throws PromptoError {
		try {
			return new BinaryData(binary.getMimeType(), binary.getBytes()).toByteArray();
		} catch(IOException e) {
			throw new ReadWriteError(e.getMessage());
		}
	}

	public WriteModel<Document> toWriteModel() {
		if(this.isUpdate) {
			Bson filter = Filters.eq("_id", document.get("_id"));
			if(hasRemovedValue) {
				Document set = document.entrySet().stream().filter(e -> e.getValue()!=REMOVED_VALUE).collect(MongoUtils.toDocument());
				Document unset = document.entrySet().stream().filter(e -> e.getValue()==REMOVED_VALUE).collect(MongoUtils.toDocument());
				Document updates = new Document();
				updates.put("$set", set);
				updates.put("$unset", unset);
				return new UpdateOneModel<>(filter, updates);
			} else
				return new UpdateOneModel<>(filter, new Document("$set", document));
		} else
			return new InsertOneModel<Document>(document);
	}

}
