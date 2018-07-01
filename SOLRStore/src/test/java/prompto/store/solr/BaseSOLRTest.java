package prompto.store.solr;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;

import prompto.config.TempDirectories;
import prompto.runtime.Mode;
import prompto.store.AttributeInfo;
import prompto.store.Family;

public abstract class BaseSOLRTest {
	
	File root = new File("target/test-classes/solr-test");
	EmbeddedSOLRStore store;
	
	protected void createStore(String coreName) {
		store = new EmbeddedSOLRStore(root, coreName);
		store.startContainer();
	}
	
	@Before
	public final void __before__() throws IOException {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
	}
	
	@After
	public final void __after__() throws IOException {
		store.shutdownServer();
		store.shutdownCore();
		store.shutdownContainer();
	}
	
	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}


	
}
