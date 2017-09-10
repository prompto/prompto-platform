package prompto.server;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import prompto.code.Version;
import prompto.config.IDebugConfiguration;
import prompto.config.IRuntimeConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.libraries.Libraries;
import prompto.runtime.Application;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.mongo.BaseMongoTest;

public class TestMongoBootstrap extends BaseMongoTest {
	
	@Before
	public void before() throws Exception {
		createStore("CODE");
		Application.bootstrapCodeStore(store, newRuntimeConfig());
	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration() {
			@Override public void setRuntimeLibsSupplier(Supplier<Collection<URL>> supplier) { }
			@Override public boolean isTestMode() { return true; }
			@Override public boolean isLoadRuntime() { return false; }
			@Override public Supplier<Collection<URL>> getRuntimeLibsSupplier() { return ()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class); }
			@Override public URL[] getResourceURLs() { return null; }
			@Override public IDebugConfiguration getDebugConfiguration() { return null; }
			@Override public IStoreConfiguration getDataStoreConfiguration() { return null; }
			@Override public IStoreConfiguration getCodeStoreConfiguration() { return null; }
			@Override public Map<String, String> getArguments() { return null; }
			@Override public Version getApplicationVersion() { return Version.parse("1.0.0"); }
			@Override public String getApplicationName() { return "test"; }
			@Override public URL[] getAddOnURLs() { return null; }
		};
	}

	@Test
	public void testCodeStoreColumns() throws Throwable {
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
		assertEquals(Family.UUID, store.getColumnTypeFamily(IStore.dbIdName));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("name"));
		assertEquals(Family.TEXT, store.getColumnTypeFamily("version"));
		assertNull(Application.getGlobalContext().findAttribute("prototype"));
	}
}
