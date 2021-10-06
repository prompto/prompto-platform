package prompto.store.solr;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;

import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDbId;
import prompto.store.IStorable;
import prompto.store.IStore;

public class StorableDocument extends BaseDocument implements IStorable {

	SolrInputDocument document = null;
	IDbIdListener listener;
	String[] categories;
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
	public void setDbId(PromptoDbId dbId) {
		ensureDocument(null);
		document.setField(IStore.dbIdName, dbId);
	}
	
	@Override
	public PromptoDbId getOrCreateDbId() {
		ensureDocument(null);
		SolrInputField dbIdField = document.getField(IStore.dbIdName);
		return PromptoDbId.of(dbIdField.getValue());
	}
	
	@Override
	public void clear() {
		document = null;
		isUpdate = false;
	}

	public SolrInputDocument getDocument() {
		return document;
	}
	
	private void ensureDocument(IDbIdProvider provider) {
		if(document==null) {
			UUID dbId = provider==null ? null : (UUID)provider.get().getValue();
			// the scenario where we get an existing dbId is when  
			// an instance passes a provider when calling setData
			// in such a case, the scenario is an update scenario
			if(dbId!=null)
				this.isUpdate = true;
			else
				dbId = java.util.UUID.randomUUID();
			document = new SolrInputDocument();
			document.setField(IStore.dbIdName, dbId);
			if(categories!=null && !this.isUpdate)
				document.setField("category", categories); 
				
		}
	}

	@Override
	public boolean isDirty() {
		return document!=null;
	}

	@Override
	public void setData(String name, Object value) throws PromptoError {
		ensureDocument(null);
		if(value instanceof PromptoBinary)
			value = toBytes((PromptoBinary)value);
		if(isUpdate)
			document.setField(name, Collections.singletonMap("set", value));
		else
			document.setField(name, value);
	}
	
	@Override
	public void removeData(String name) throws PromptoError {
		ensureDocument(null);
		document.remove(name);
	}


	private byte[] toBytes(PromptoBinary binary) throws PromptoError {
		try {
			return new BinaryData(binary.getMimeType(), binary.getBytes()).toByteArray();
		} catch(IOException e) {
			throw new ReadWriteError(e.getMessage());
		}
	}

}
