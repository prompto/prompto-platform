package prompto.store;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlDocumentReader;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.server.HeadlessTests;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.parser.Dialect;
import prompto.server.BaseUITest;
import prompto.store.memory.MemStore;

@Category(HeadlessTests.class)
public class TestRemoteStore extends BaseUITest {

	BaseCodeStore tail;
	Path tempDir;
	
	@Before
	public void before() {
		tail = getCodeStoreTail();
		DataStore.setGlobal(new MemStore());
	}
	
	
	@After
	public void after() {
		if(tail!=null)
			tail.setNext(null);
	}
	
	private BaseCodeStore getCodeStoreTail() {
		ICodeStore store = ICodeStore.getInstance();
		while(store instanceof BaseCodeStore) {
			ICodeStore next = ((BaseCodeStore)store).getNext();
			if(next==null)
				return (BaseCodeStore)store;
			else
				store = next;	
		}
		return null;
	}

	private void linkResourcesAndLoadPage(String resourceName, Dialect dialect) throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("remote-store-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		URL pageResourceURL = Thread.currentThread().getContextClassLoader().getResource("remote-store-tests/" + resourceName + ".page");
		if(pageResourceURL==null)
			pageResourceURL = createTempPage(resourceName);
		ImmutableCodeStore pageResource = new ImmutableCodeStore(codeResource, ModuleType.LIBRARY, pageResourceURL, PromptoVersion.LATEST);
		tail.setNext(pageResource);
		String url = "http://localhost:" + HTTP_PORT + "/" + resourceName + ".page";
		webDriver.get(url);
	}

	private URL createTempPage(String resourceName) throws Exception {
		// create page resource
		YamlDocument template;
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("remote-store-tests/generic.page")) {
			template = new YamlDocumentReader(new InputStreamReader(input)).read();
		}
		YamlDocument body = (YamlDocument)template.getEntry("body").getValue();
		body.getEntry("widget").setValue(resourceName + "Page");
		// store it
		if(tempDir==null) 
			tempDir = Files.createTempDirectory("prompto_pages_");
		File pageFile = new File(tempDir.toFile(), resourceName + ".page");
		try(FileWriter writer = new FileWriter(pageFile)) {
			YamlConfig config = new YamlConfig();
			config.writeConfig.setWriteClassname(WriteClassName.NEVER);
			config.writeConfig.setAutoAnchor(false);
			new YamlWriter(writer, config).write(template);
		}
		// done
		return pageFile.toURI().toURL();
	}

	@Test
	public void nullIsStored() throws Exception {
		linkResourcesAndLoadPage("NullIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertTrue(stored.hasData("value"));
		assertEquals(null, stored.getRawData("value"));
	}

	@Test
	public void booleanIsStored() throws Exception {
		linkResourcesAndLoadPage("BooleanIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(true, stored.getRawData("value"));
	}

	@Test
	public void textIsStored() throws Exception {
		linkResourcesAndLoadPage("TextIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals("John", stored.getRawData("value"));
	}
	
	@Test
	public void integerIsStored() throws Exception {
		linkResourcesAndLoadPage("IntegerIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
			IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(123L, stored.getRawData("value"));
	}

	
	@Test
	public void decimalIsStored() throws Exception {
		linkResourcesAndLoadPage("DecimalIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(123.2d, stored.getRawData("value"));
	}

	@Test
	public void uuidIsStored() throws Exception {
		linkResourcesAndLoadPage("UuidIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(UUID.fromString("d5622e5d-9232-48de-bb47-77fc41005f5a"), stored.getRawData("value"));
	}
	
	@Test
	public void dateIsStored() throws Exception {
		linkResourcesAndLoadPage("DateIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(PromptoDate.parse("2016-02-25"), stored.getRawData("value"));
	}
	
	@Test
	public void timeIsStored() throws Exception {
		linkResourcesAndLoadPage("TimeIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(PromptoTime.parse("18:15:03"), stored.getRawData("value"));
	}
	
	@Test
	public void dateTimeIsStored() throws Exception {
		linkResourcesAndLoadPage("DateTimeIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(PromptoDateTime.parse("2016-02-25T18:15:03.000Z"), stored.getRawData("value"));
	}

	@Test
	public void listIsStored() throws Exception {
		linkResourcesAndLoadPage("ListIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals(Arrays.asList("John", "Gielgud"), stored.getRawData("value"));
	}
	
	@Test
	public void childIsStored() throws Exception {
		linkResourcesAndLoadPage("ChildIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		Object child = stored.getRawData("child");
		assertNotNull(child);
		stored = DataStore.getInstance().fetchUnique(child);
		assertNotNull(stored);
		assertEquals("John", stored.getRawData("value"));
	}
	
}
