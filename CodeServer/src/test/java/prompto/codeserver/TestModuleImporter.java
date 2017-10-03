package prompto.codeserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prompto.code.ICodeStore;
import prompto.config.IRuntimeConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.memstore.MemStore;
import prompto.runtime.Standalone;

public class TestModuleImporter {

	@Before
	public void before() throws Exception {
		Standalone.bootstrapCodeStore(new MemStore(), newRuntimeConfig());

	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration.Inline()
			.withApplicationName("test")
			.withApplicationVersion(PromptoVersion.parse("1.0.0")); 
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
