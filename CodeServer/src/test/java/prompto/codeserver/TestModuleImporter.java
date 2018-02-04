package prompto.codeserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prompto.code.Batch;
import prompto.code.ICodeStore;
import prompto.code.Thesaurus;
import prompto.config.IRuntimeConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.memstore.MemStore;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;

public class TestModuleImporter {

	@Before
	public void before() throws Exception {
		Standalone.bootstrapCodeStore(new MemStore(), newRuntimeConfig());

	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration.Inline()
			.withApplicationName("test")
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class))
			.withRuntimeMode(Mode.UNITTEST);
	}

	@Test
	public void testSales() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Sales/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}

	@Test
	public void testInventory() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Inventory/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}

	@Test
	public void testSoup() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Soup/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}

	@Test
	public void testMyApp() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/MyApp/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}
	
	@Test
	public void testThesaurus() throws Exception {
		ModuleImporter importer = new ModuleImporter("thesaurus/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Thesaurus);
		assertNotNull(importer.module.getDbId());
	}
}
