package prompto.store.mongo;

import java.io.IOException;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;

import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.intrinsic.PromptoBinary;
import prompto.store.IStorable;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

public class StorableDocument extends BaseDocument implements IStorable {

	Document document;
	String[] categories;
	IDbIdListener listener;
	boolean isUpdate; // partial updates require operations instead of values
	
	public StorableDocument(String[] categories, IDbIdListener listener) {
		this.categories = categories;
		this.listener = listener;
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
	public void setDbId(Object dbId) {
		ensureDocument(null);
		document.put("_id", dbId);
		// TODO call listener
	}
	
	@Override
	public UUID getOrCreateDbId() {
		ensureDocument(null);
		Object dbIdField = document.get("_id");
		return (UUID)dbIdField;
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
	
	private void ensureDocument(IDbIdProvider provider) {
		if(document==null) {
			UUID dbId = provider==null ? null : (UUID)provider.get();
			// the scenario where we get an existing dbId is when  
			// an instance passes a provider when calling setData
			// in such a case, the scenario is an update scenario
			if(dbId!=null)
				this.isUpdate = true;
			else
				dbId = java.util.UUID.randomUUID();
			document = new Document();
			document.put("_id", dbId);
			if(categories!=null && !this.isUpdate)
				document.put("category", categories); 
				
		}
	}

	@Override
	public void setData(String name, Object value, IDbIdProvider provider) throws PromptoError {
		ensureDocument(provider);
		setData(name, value);
	}
	
	@Override
	public void setData(String name, Object value) throws PromptoError {
		ensureDocument(null);
		if(value instanceof PromptoBinary)
			value = toBytes((PromptoBinary)value);
		document.put(name, value);
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
			return new UpdateOneModel<>(filter, new Document("$set", document));
		} else
			return new InsertOneModel<Document>(document);
	}

}
