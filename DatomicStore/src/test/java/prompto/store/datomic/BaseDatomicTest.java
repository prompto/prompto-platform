package prompto.store.datomic;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;

import prompto.store.AttributeInfo;
import prompto.store.Family;
import datomic.Peer;

public abstract class BaseDatomicTest {
	
	File root = new File("target/test-classes/solr-test");
	BaseDatomicStore store;
	
	@Before
	public final void __before__() {
		store = new FreeDatomicStore(this.getClass().getSimpleName());
		store.connect();
	}
	
	@After
	public void after() throws IOException {
		store.disconnect();
		Peer.deleteDatabase(store.uri);
	}
	
	
	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}
	

	
}
