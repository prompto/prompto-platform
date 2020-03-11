package prompto.store;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import prompto.server.HeadlessTests;
import prompto.store.mongo.BaseMongoTest;

@Category(HeadlessTests.class)
public class TestRemoteMongoStore extends TestRemoteStoreBase {

	static class MongoTest extends BaseMongoTest {

		public void before() throws IOException {
			__before__();
		}
		
		public void after() throws IOException {
			__after__();
		}
		
		public IStore newStore(String name) {
			return createStore(name);
		}
		
	}
	
	static MongoTest mongo = new MongoTest();
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		mongo.before();
	}
	
	@AfterClass
	public static void after() throws IOException {
		mongo.after();
	}
	
	static int counter = 0;
	
	@Override
	protected IStore getDataStore() {
		return mongo.newStore("STUFF_" + counter++);
	}

	@Ignore
	@Override
	public void dateTimeIsStored() throws Exception {
	}
	
	@Ignore
	@Override
	public void timeIsStored() throws Exception {
	}
	
	@Ignore
	@Override
	public void dateIsStored() throws Exception {
	}
	
	@Ignore
	@Override
	public void dateTimeIsFetched() throws Exception {
	}
	
	@Ignore
	@Override
	public void timeIsFetched() throws Exception {
	}
	
	@Ignore
	@Override
	public void dateIsFetched() throws Exception {
	}


}
