package prompto.codeserver;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import prompto.code.ICodeStore;
import prompto.config.IDebugConfiguration;
import prompto.config.IRuntimeConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.memstore.MemStore;
import prompto.runtime.Standalone;

public class TestModuleImporter {

	@Before
	public void before() throws Exception {
		Standalone.bootstrapCodeStore(new MemStore(), newRuntimeConfig());

	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration() {
			@Override public void setRuntimeLibsSupplier(Supplier<Collection<URL>> supplier) {}
			@Override public Supplier<Collection<URL>> getRuntimeLibsSupplier() { return ()->Libraries.getPromptoLibraries(Libraries.class); }
			@Override public IStoreConfiguration getCodeStoreConfiguration() { return null; }
			@Override public IStoreConfiguration getDataStoreConfiguration() { return null; }
			@Override public IDebugConfiguration getDebugConfiguration() { return null; }
			@Override public Map<String, String> getArguments() { return null; }
			@Override public String getApplicationName() { return "test"; }
			@Override public PromptoVersion getApplicationVersion() { return PromptoVersion.parse("1.0.0"); }
			@Override public boolean isTestMode() { return true; }
			@Override public URL[] getAddOnURLs() { return null; }
			@Override public URL[] getResourceURLs() { return null; }
			@Override public boolean isLoadRuntime() { return true; }
		};
	}

	@Test
	public void testSales() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Sales/");
		importer.importModule(ICodeStore.getInstance());
		assertNotNull(importer.module);
		assertNotNull(importer.module.getDbId());
	}

	@Test
	public void testInventory() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Inventory/");
		importer.importModule(ICodeStore.getInstance());
		assertNotNull(importer.module);
		assertNotNull(importer.module.getDbId());
	}

	@Test
	public void testSoup() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Soup/");
		importer.importModule(ICodeStore.getInstance());
		assertNotNull(importer.module);
		assertNotNull(importer.module.getDbId());
	}

	@Test
	public void testMyApp() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/MyApp/");
		importer.importModule(ICodeStore.getInstance());
		assertNotNull(importer.module);
		assertNotNull(importer.module.getDbId());
	}
	
	@Test
	public void testThesaurus() throws Exception {
		ModuleImporter importer = new ModuleImporter("thesaurus/");
		importer.importModule(ICodeStore.getInstance());
		assertNotNull(importer.module);
		assertNotNull(importer.module.getDbId());
	}
}
