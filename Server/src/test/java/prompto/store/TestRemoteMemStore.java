package prompto.store;

import org.junit.experimental.categories.Category;

import prompto.server.HeadlessTests;
import prompto.store.memory.MemStore;

@Category(HeadlessTests.class)
public class TestRemoteMemStore extends TestRemoteStoreBase {


	@Override
	protected IStore getDataStore() {
		return new MemStore();
	}
	
}
