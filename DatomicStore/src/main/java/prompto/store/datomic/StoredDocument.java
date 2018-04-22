package prompto.store.datomic;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import prompto.error.PromptoError;
import prompto.store.Family;
import prompto.store.IStored;
import datomic.Entity;

public class StoredDocument implements IStored {

	Entity entity;
	
	public StoredDocument(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Object getDbId() {
		return entity.get(Constants.Db.ID.dbName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCategories() {
		Object categories = entity.get(":category/ordered");
		if(categories instanceof Collection)
			return ((Collection<String>)categories).stream()
						.map(OrderedCategory::new)
						.sorted()
						.map(OrderedCategory::category)
						.collect(Collectors.toList());
		else 
			throw new RuntimeException("Can't read categoies from " + categories.getClass().getName());
	}


	@Override
	public boolean hasData(String fieldName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getRawData(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getData(String fieldName) throws PromptoError {
		// TODO keep name/type in cache
		Object familyName = entity.get(":" + fieldName + "/family");
		if(familyName instanceof String) {
			Family family = Family.valueOf((String)familyName);
			FamilyHelper helper = FamilyHelper.HELPERS.get(family);
			return helper.nativeToPrompto(entity, fieldName);
		}
		return entity.get(":" + fieldName);
	}

	@Override
	public Set<String> getNames() throws PromptoError {
		// TODO Auto-generated method stub
		return null;
	}

}
