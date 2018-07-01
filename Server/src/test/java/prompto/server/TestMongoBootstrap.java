package prompto.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prompto.config.IRuntimeConfiguration;
import prompto.config.TempDirectories;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.mongo.BaseMongoTest;

public class TestMongoBootstrap extends BaseMongoTest {
	
	@Before
	public void before() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		createStore("APPS");
		Standalone.bootstrapCodeStore(store, newRuntimeConfig());
	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration.Inline()
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test")
			.withLoadRuntime(false)
			.withRuntimeMode(Mode.UNITTEST);
	}

	@Test
	public void testCodeStoreColumns() throws Throwable {
		assertEquals(Family.UUID, store.getAttributeInfo(IStore.dbIdName).getFamily());
		assertEquals(Family.DATETIME, store.getAttributeInfo("timeStamp").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("category").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("name").getFamily());
		assertEquals(Family.BOOLEAN, store.getAttributeInfo("storable").getFamily());
		assertEquals(Family.VERSION, store.getAttributeInfo("version").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("prototype").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("dialect").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("body").getFamily());
	}
	
	@Test
	public void testCodeStoreIsolation() throws Throwable {
		assertEquals(Family.UUID, store.getAttributeInfo(IStore.dbIdName).getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("name").getFamily());
		assertEquals(Family.VERSION, store.getAttributeInfo("version").getFamily());
		assertNull(Standalone.getGlobalContext().findAttribute("prototype"));
	}
}
