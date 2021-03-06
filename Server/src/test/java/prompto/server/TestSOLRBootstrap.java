package prompto.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import prompto.config.IRuntimeConfiguration;
import prompto.config.TempDirectories;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.store.Family;
import prompto.store.IStore;
import prompto.store.solr.EmbeddedSOLRStore;

@Ignore("Until we need it")
public class TestSOLRBootstrap {
	
	EmbeddedSOLRStore store;
	
	@Before
	public void before() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
		store = newEmbeddedStore();
		((EmbeddedSOLRStore)store).startContainer();
		((EmbeddedSOLRStore)store).startServerWithEmptyCore();
		Standalone.bootstrapCodeStore(store, newRuntimeConfig());
	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration.Inline()
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test")
			.withLoadRuntime(false);
	}

	private EmbeddedSOLRStore newEmbeddedStore() {
		return new EmbeddedSOLRStore(new File("target/test-classes/solr-test"), "APPS");
	}

	@After
	public void after() throws IOException {
		store.shutdownServer();
		store.shutdownCore();
		store.shutdownContainer();
	}

	@Test
	public void testCodeStoreColumns() throws Throwable {
		assertEquals(Family.UUID, store.getAttributeInfo(IStore.dbIdName).getFamily());
		assertEquals(Family.DATETIME, store.getAttributeInfo("timeStamp").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("category").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("name").getFamily());
		assertEquals(Family.BOOLEAN, store.getAttributeInfo("storable").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("version").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("prototype").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("dialect").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("body").getFamily());
	}
	
	@Test
	public void testCodeStoreIsolation() throws Throwable {
		assertEquals(Family.UUID, store.getAttributeInfo(IStore.dbIdName).getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("name").getFamily());
		assertEquals(Family.TEXT, store.getAttributeInfo("version").getFamily());
		assertNull(ApplicationContext.get().findAttribute("prototype"));
	}
}
