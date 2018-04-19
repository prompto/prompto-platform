package prompto.store.datomic;

import java.util.Set;

import datomic.Entity;
import prompto.error.PromptoError;
import prompto.store.Family;
import prompto.store.IStored;

public class StoredDocument implements IStored {

	Entity entity;
	
	public StoredDocument(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Object getDbId() {
		// TODO Auto-generated method stub
		return null;
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
