package prompto.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import prompto.code.Version;
import prompto.libraries.Libraries;
import prompto.runtime.Application;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.IStoreFactory.Type;
import prompto.store.mongo.BaseMongoTest;

public class TestMongoBootstrap extends BaseMongoTest {
	
	@Test
	public void testCodeStoreColumns() throws Throwable {
		createStore(Type.CODE.name());
		Application.bootstrapCodeStore(store, ()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class), "test", Version.parse("1.0.0"), true, null);
		assertEquals(Family.UUID, store.getColumnTypeFamily(IStore.dbIdName));
		assertEquals(Family.DATETIME, store.getColumnTypeFamily("timeStamp"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("category"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("name"));
		assertEquals(Family.BOOLEAN, store.getColumnTypeFamily("storable"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("version"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("prototype"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("dialect"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("body"));
	}
	
	@Test
	public void testCodeStoreIsolation() throws Throwable {
		createStore(Type.CODE.name());
		Application.bootstrapCodeStore(store, ()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class), "test", Version.parse("1.0.0"), true, null);
		assertEquals(Family.UUID, store.getColumnTypeFamily(IStore.dbIdName));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("name"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("version"));
		assertNull(Application.getGlobalContext().findAttribute("prototype"));
	}
}
