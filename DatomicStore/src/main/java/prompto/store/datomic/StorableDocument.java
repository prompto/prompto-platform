package prompto.store.datomic;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import prompto.error.PromptoError;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.store.Family;
import prompto.store.IStorable;
import prompto.store.datomic.Constants.DbPart;
import datomic.Peer;

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
		if(value instanceof PromptoDate) {
			facts.add(name + "/family", Family.DATE.name());
			value = ((PromptoDate)value).toJavaTime();
			facts.add(name, value);
		} else if(value instanceof PromptoTime) {
			facts.add(name + "/family", Family.TIME.name());
			value = ((PromptoTime)value).getNativeMillisOfDay();
			facts.add(name, value);
		} else if(value instanceof PromptoDateTime) {
			facts.add(name + "/family", Family.DATETIME.name());
			String zone = ((PromptoDateTime)value).getTzName();
			if(zone!=null)
				facts.add(name + "/zone", zone);
			else {
				Long offset = ((PromptoDateTime)value).getTzOffset();
				if(offset!=null)
					facts.add(name + "/offset", offset);
			}
			value = new Date(((PromptoDateTime)value).toJavaTime());
			facts.add(name, value);
		} else if(value instanceof PromptoBinary) {
			// TODO PromptoBinary value = toBytes((PromptoBinary)value);
		} else
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
			if(categories!=null && !isUpdate) {
				facts.add("category", categories); 
				final AtomicInteger counter = new AtomicInteger(0);
				facts.add("category/ordered", categories.stream().map(c->"" + counter.incrementAndGet() + ":" + c).collect(Collectors.toSet())); 
			}
		}
	}
	
	public Stream<List<Object>> getAddedFacts() {
		return facts.getAddedFacts();
	}

	
}
