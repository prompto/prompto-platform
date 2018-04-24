package prompto.store.datomic;


public class FreeDatomicStore extends BaseDatomicStore {
	
	public FreeDatomicStore(String dbName) {
		super("datomic:mem://" + dbName, true);
	}

}
