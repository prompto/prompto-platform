package prompto.store.solr;

import org.apache.solr.common.SolrDocument;

import prompto.error.PromptoError;
import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.store.IStored;
import prompto.value.IValue;

public class StoredDocument extends BaseDocument implements IStored {

	BaseSOLRStore store;
	SolrDocument document;
	
	public StoredDocument(BaseSOLRStore store, SolrDocument document) {
		this.store = store;
		this.document = document;
	}

	
	@Override
	public IValue getValue(Context context, Identifier id) throws PromptoError {
		Object data = getData(id.getName());
		return store.readData(id.getName(), data);
	}

	@Override
	public Object getData(String name) {
		return document.getFieldValue(name);
	}

}