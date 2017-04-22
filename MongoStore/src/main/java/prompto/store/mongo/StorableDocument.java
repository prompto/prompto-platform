package prompto.store.mongo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bson.Document;

import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;

import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.intrinsic.PromptoBinary;
import prompto.store.IStorable;
import prompto.store.IStore;

public class StorableDocument extends BaseDocument implements IStorable {

	Document document;
	IDbIdListener listener;
	List<String> categories;
	boolean isUpdate; // partial updates require operations instead of values
	
	public StorableDocument(List<String> categories, IDbIdListener listener) {
		this.categories = categories;
		this.listener = listener;
	}

	@Override
	public void setCategories(String[] categories) throws PromptoError {
		this.categories = Arrays.asList(categories);
	}
	
	@Override
	public UUID getOrCreateDbId() {
		ensureDocument(null);
		Object dbIdField = document.get(IStore.dbIdName);
		return (UUID)dbIdField;
	}
	
	@Override
	public void setDirty(boolean set) {
		if(!set) {
			document = null;
			isUpdate = false;
		} else 
			ensureDocument(null);
	}

	public Document getDocument() {
		return document;
	}
	
	private void ensureDocument(IDbIdProvider provider) {
		if(document==null) {
			UUID dbId = provider==null ? null : (UUID)provider.getDbId();
			// the scenario where we get an existing dbId is when  
			// an instance passes a provider when calling setData
			// in such a case, the scenario is an update scenario
			if(dbId!=null)
				this.isUpdate = true;
			else
				dbId = java.util.UUID.randomUUID();
			document = new Document();
			document.put(IStore.dbIdName, dbId);
			if(categories!=null && !this.isUpdate)
				document.put("category", categories); 
				
		}
	}

	@Override
	public boolean isDirty() {
		return document!=null;
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
		if(isUpdate)
			document.put(name, Collections.singletonMap("set", value));
		else
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
		if(this.isUpdate)
			throw new UnsupportedOperationException();
		else
			return new InsertOneModel<Document>(document);
	}

}
