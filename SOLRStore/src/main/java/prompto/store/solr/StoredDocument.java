package prompto.store.solr;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.apache.solr.common.SolrDocument;

import prompto.error.PromptoError;
import prompto.intrinsic.PromptoDbId;
import prompto.store.IStore;
import prompto.store.IStored;

public class StoredDocument extends BaseDocument implements IStored {

	BaseSOLRStore store;
	SolrDocument document;
	
	public StoredDocument(BaseSOLRStore store, SolrDocument document) {
		this.store = store;
		this.document = document;
	}

	
	@Override
	public PromptoDbId getDbId() {
		Object dbId = document.getFieldValue(IStore.dbIdName);
		if(dbId!=null && !(dbId instanceof UUID))
			dbId = UUID.fromString(dbId.toString());
		return PromptoDbId.of(dbId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String[] getCategories() {
		Object categories = getData("category");
		if(categories instanceof Collection) {
			Collection<String> coll = (Collection<String>)categories;
			return coll.toArray(new String[0]);
		} else 
			throw new RuntimeException("Can't read categoies from " + categories.getClass().getName());
	}

	
	@Override
	public boolean hasData(String name) {
		return document.containsKey(name);
	}

	@Override
	public Object getRawData(String fieldName) {
		return document.getFieldValue(fieldName);
	}
	
	@Override
	public Object getData(String fieldName) throws PromptoError {
		Object data = document.getFieldValue(fieldName);
		return store.readFieldData(fieldName, data);
	}

	@Override
	public Set<String> getNames() throws PromptoError {
		Set<String> names = document.keySet();
		names.remove("category");
		names.remove("dbId");
		return names;
	}
}
