package prompto.store.datomic;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;

import prompto.config.TempDirectories;
import prompto.runtime.Mode;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import datomic.Database;
import datomic.Datom;
import datomic.Peer;

public abstract class BaseDatomicTest {
	
	File root = new File("target/test-classes/solr-test");
	BaseDatomicStore store;
	
	@Before
	public final void __before__() throws IOException {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		store = new FreeDatomicStore(this.getClass().getSimpleName());
		store.connect();
	}
	
	@After
	public final void __after__() throws IOException {
		store.close();
		Peer.deleteDatabase(store.uri);
	}
	
	
	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}
	

	public void dumpFacts(PrintStream output) {
		Iterable<Datom> data  = store.cnx.db().datoms(Database.EAVT);
		Iterator<Datom> iter = data.iterator();
		while(iter.hasNext()) {
			Datom d = iter.next();
			if(d.a().equals(10))
				output.println(d.e().toString() + " " + d.a().toString() + " " + d.v().toString());
		}
	}
	
}
