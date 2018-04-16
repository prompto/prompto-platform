package prompto.store.solr;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.junit.After;

import prompto.store.AttributeInfo;
import prompto.store.Family;

public abstract class BaseSOLRTest {
	
	File root = new File("target/test-classes/solr-test");
	EmbeddedSOLRStore store;
	
	protected void createStore(String coreName) {
		store = new EmbeddedSOLRStore(root, coreName);
		store.startContainer();
	}
	
	@After
	public void after() throws IOException {
		store.shutdownServer();
		store.shutdownCore();
		store.shutdownContainer();
	}
	
	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}


	
}
