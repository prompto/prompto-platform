package prompto.store.datomic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import datomic.Peer;
import prompto.error.PromptoError;
import prompto.store.IStorable;
import prompto.store.datomic.Constants.DbPart;

public class StorableDocument implements IStorable  {

	DatomicFacts facts = null;
	List<String> categories;
	IDbIdListener listener;
	
	public StorableDocument(List<String> categories, IDbIdListener listener) {
		this.categories = categories;
		this.listener = listener;
	}

	@Override
	public void setCategories(String[] categories) throws PromptoError {
		this.categories = Arrays.asList(categories);
	}

	@Override
	public Object getOrCreateDbId() {
		ensureFacts(null);
		return facts.getDbId();
	}

	@Override
	public void setDirty(boolean dirty) {
		if(!dirty)
			facts = null;
		else 
			ensureFacts(null);
		
	}

	@Override
	public boolean isDirty() {
		return facts != null;
	}

	@Override
	public void setData(String name, Object value, IDbIdProvider provider) throws PromptoError {
		ensureFacts(provider);
		setData(name, value);
	}

	@Override
	public void setData(String name, Object value) throws PromptoError {
		ensureFacts(null);
		// TODO PromptoBinary
		/* if(value instanceof PromptoBinary)
			value = toBytes((PromptoBinary)value); */
		facts.add(name, value);
	}

	
	private void ensureFacts(IDbIdProvider provider) {
		if(facts==null) {
			Object dbId = provider==null ? null : provider.get();
			// the scenario where we get an existing dbId is when  
			// an instance passes a provider when calling setData
			// in such a case, the scenario is an update scenario
			boolean isUpdate = dbId!=null;
			if(dbId==null)
				dbId = Peer.tempid(DbPart.USER.dbName());
			facts = new DatomicFacts(dbId);
			if(categories!=null && !isUpdate)
				facts.add("category", categories); 
				
		}
	}
	
	public Stream<List<Object>> getAddedFacts() {
		return facts.getAddedFacts();
	}

	
}
