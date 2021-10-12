package prompto.store;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.experimental.categories.Category;

import prompto.server.HeadlessTests;
import prompto.store.mongo.BaseMongoTest;
import prompto.store.mongo.MongoStore;

@Category(HeadlessTests.class)
public class TestRemoteMongoStore extends TestRemoteStoreBase {

	static class MongoTest extends BaseMongoTest {

		public static void beforeClass() throws IOException {
			__before_class__();
		}
		
		public static void afterClass() throws IOException {
			__after_class__();
		}

		public void before() throws IOException {
			__before__();
		}
		
		public void after() throws IOException {
			__after__();
		}
		
		public MongoStore createStore(String name) {
			return super.createStore(name);
		}
		
	}
	
	static MongoTest mongo = new MongoTest();
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		MongoTest.beforeClass();
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		MongoTest.afterClass();
	}
	
	@Before
	public void before() throws Exception {
		mongo.before();
	}

	@After
	public void after() throws Exception {
		mongo.after();
	}

	static int counter = 0;
	
	@Override
	protected IStore getDataStore() {
		return mongo.createStore("STUFF_" + counter++);
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
