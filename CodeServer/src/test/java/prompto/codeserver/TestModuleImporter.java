package prompto.codeserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prompto.code.Batch;
import prompto.code.ICodeStore;
import prompto.code.Thesaurus;
import prompto.code.WebLibrary;
import prompto.config.IRuntimeConfiguration;
import prompto.config.TempDirectories;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.store.memory.MemStore;

public class TestModuleImporter {

	@Before
	public void before() throws Exception {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
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
	public void salesAppIsImported() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Sales/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}

	@Test
	public void inventoryAppIsImported() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Inventory/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}

	@Test
	public void soupAppIsImported() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/Soup/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}

	@Test
	public void myAppIsImported() throws Exception {
		ModuleImporter importer = new ModuleImporter("samples/MyApp/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Batch);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((Batch)importer.module).getStartMethod());
	}
	
	@Test
	public void thesaurusIsImported() throws Exception {
		ModuleImporter importer = new ModuleImporter("thesaurus/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof Thesaurus);
		assertNotNull(importer.module.getDbId());
	}
	
	@Test
	public void reactBootstrapIsImported() throws Exception {
		ModuleImporter importer = new ModuleImporter("react-bootstrap-3/");
		importer.importModule(ICodeStore.getInstance());
		assertTrue(importer.module instanceof WebLibrary);
		assertNotNull(importer.module.getDbId());
		assertNotNull(((WebLibrary)importer.module).getWidgetLibrary());
		assertNull(((WebLibrary)importer.module).getHtmlEngine());
		assertNull(((WebLibrary)importer.module).getUIFramework());
	}
}
