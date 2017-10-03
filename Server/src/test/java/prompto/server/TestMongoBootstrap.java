package prompto.server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prompto.config.IRuntimeConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Standalone;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.mongo.BaseMongoTest;

public class TestMongoBootstrap extends BaseMongoTest {
	
	@Before
	public void before() throws Exception {
		createStore("CODE");
		Standalone.bootstrapCodeStore(store, newRuntimeConfig());
	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration.Inline()
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test");
	}

	@Test
	public void testCodeStoreColumns() throws Throwable {
		assertEquals(Family.UUID, store.getColumnTypeFamily(IStore.dbIdName));
		assertEquals(Family.DATETIME, store.getColumnTypeFamily("timeStamp"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("category"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("name"));
		assertEquals(Family.BOOLEAN, store.getColumnTypeFamily("storable"));
		assertEquals(Family.VERSION, store.getColumnTypeFamily("version"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("prototype"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("dialect"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("body"));
	}
	
	@Test
	public void testCodeStoreIsolation() throws Throwable {
		assertEquals(Family.UUID, store.getColumnTypeFamily(IStore.dbIdName));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("name"));
		assertEquals(Family.VERSION, store.getColumnTypeFamily("version"));
		assertNull(Standalone.getGlobalContext().findAttribute("prototype"));
	}
}
