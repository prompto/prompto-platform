package prompto.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlDocumentReader;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.intrinsic.PromptoVersion;
import prompto.parser.Dialect;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Standalone;
import prompto.server.BaseUITest;

public abstract class TestRemoteStoreBase extends BaseUITest {

	Path tempDir;	

	@Before
	public void __before__() throws Throwable {
		ApplicationContext.reset();
		DataStore.setGlobal(getDataStore());
		DataStore.useGlobal();
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
		tail = getCodeStoreTail();
	}

	protected abstract IStore getDataStore();

	@After
	public void __after__() {
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

	protected void linkResourcesAndLoadPage(String resourceName, Dialect dialect) throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("remote-store-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		URL pageResourceURL = Thread.currentThread().getContextClassLoader().getResource("remote-store-tests/" + resourceName + ".page");
		if(pageResourceURL==null)
			pageResourceURL = createTempPage(resourceName);
		ImmutableCodeStore pageResource = new ImmutableCodeStore(codeResource, ModuleType.LIBRARY, pageResourceURL, PromptoVersion.LATEST);
		tail.setNext(pageResource);
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
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
	public void nullIsFetched() throws Exception {
		linkResourcesAndLoadPage("NullIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("null", elem.getText());
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
	public void booleanIsFetched() throws Exception {
		linkResourcesAndLoadPage("BooleanIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("true", elem.getText());
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
	public void textIsFetched() throws Exception {
		linkResourcesAndLoadPage("TextIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
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
	public void integerIsFetched() throws Exception {
		linkResourcesAndLoadPage("IntegerIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("123", elem.getText());
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
	public void decimalIsFetched() throws Exception {
		linkResourcesAndLoadPage("DecimalIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("123.4", elem.getText());
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
	public void uuidIsFetched() throws Exception {
		linkResourcesAndLoadPage("UuidIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("d5622e5d-9232-48de-bb47-77fc41005f5a", elem.getText());
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
	public void dateIsFetched() throws Exception {
		linkResourcesAndLoadPage("DateIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("2016-02-25", elem.getText());
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
	public void timeIsFetched() throws Exception {
		linkResourcesAndLoadPage("TimeIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("18:15:03.000", elem.getText());
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
	public void dateTimeIsFetched() throws Exception {
		linkResourcesAndLoadPage("DateTimeIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("2016-02-25T18:15:03.000Z", elem.getText());
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
	public void listIsFetched() throws Exception {
		linkResourcesAndLoadPage("ListIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("[John, Gielgud]", elem.getText());
	}

	
	@Test
	public void enumIsStored() throws Exception {
		linkResourcesAndLoadPage("EnumIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		assertEquals("THING", stored.getRawData("value"));
	}
	
	
	@Test
	public void enumIsFetched() throws Exception {
		linkResourcesAndLoadPage("EnumIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("thing", elem.getText());
	}

	
	@Test
	public void childIsStored() throws Exception {
		linkResourcesAndLoadPage("ChildIsStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		Object child = stored.getRawData("child");
		assertNotNull(child);
		stored = DataStore.getInstance().fetchUnique(DataStore.getInstance().convertToDbId(child));
		assertNotNull(stored);
		assertEquals("John", stored.getRawData("value"));
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void childrenAreStored() throws Exception {
		linkResourcesAndLoadPage("ChildrenAreStored", Dialect.O);
		waitElement(By.id("root"), 3);
		IStored stored = DataStore.getInstance().fetchMany(null).iterator().next();
		assertNotNull(stored);
		Object children = stored.getRawData("children");
		assertTrue(children instanceof List);
		stored = DataStore.getInstance().fetchUnique(DataStore.getInstance().convertToDbId(((List<Object>)children).get(0)));
		assertNotNull(stored);
		assertEquals("John", stored.getRawData("value"));
		stored = DataStore.getInstance().fetchUnique(DataStore.getInstance().convertToDbId(((List<Object>)children).get(1)));
		assertNotNull(stored);
		assertEquals("Jane", stored.getRawData("value"));
	}
	
	@Test
	public void childIsFetched() throws Exception {
		linkResourcesAndLoadPage("ChildIsFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}

	@Test
	public void childrenAreFetched() throws Exception {
		linkResourcesAndLoadPage("ChildrenAreFetched", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John, Jane", elem.getText());
	}

	@Test
	public void parentIsFiltered() throws Exception {
		linkResourcesAndLoadPage("ParentIsFiltered", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Tommy, Bernard", elem.getText());
	}

	@Test
	public void dbIdIsPopulated() throws Exception {
		linkResourcesAndLoadPage("DbIdIsPopulated", Dialect.O);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Gielgud", elem.getText());
	}
	
	@Test
	public void recordIsStoredAsync() throws Exception {
		linkResourcesAndLoadPage("RecordIsStoredAsync", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}
	
	@Test
	public void recordIsFetchedAsync() throws Exception {
		linkResourcesAndLoadPage("RecordIsFetchedAsync", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}
	
	@Test
	public void recordsAreFetched() throws Exception {
		linkResourcesAndLoadPage("RecordsAreFetched", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John, Gielgud", elem.getText());
	}
	
	
	@Test
	public void recordsAreFetchedAsync() throws Exception {
		linkResourcesAndLoadPage("RecordsAreFetchedAsync", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John, Gielgud", elem.getText());
	}
	
	@Test
	public void recordIsUpdated() throws Exception {
		linkResourcesAndLoadPage("RecordIsUpdated", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Gielgud", elem.getText());
	}
	
	@Test
	public void equalsIsSupported() throws Exception {
		linkResourcesAndLoadPage("EqualsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}
	
	@Test
	public void notEqualsIsSupported() throws Exception {
		linkResourcesAndLoadPage("NotEqualsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Lucy", elem.getText());
	}

	@Test
	public void greaterIsSupported() throws Exception {
		linkResourcesAndLoadPage("GreaterIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Lucy", elem.getText());
	}

	
	@Test
	public void greaterEqualsIsSupported() throws Exception {
		linkResourcesAndLoadPage("GreaterEqualsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("JohnLucy", elem.getText());
	}
	
	@Test
	public void lesserIsSupported() throws Exception {
		linkResourcesAndLoadPage("LesserIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}

	@Test
	public void lesserEqualsIsSupported() throws Exception {
		linkResourcesAndLoadPage("LesserEqualsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("LucyJohn", elem.getText());
	}
	

	@Test
	public void inIsSupported() throws Exception {
		linkResourcesAndLoadPage("InIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("BrianJohn", elem.getText());
	}
	
	@Test
	public void notInIsSupported() throws Exception {
		linkResourcesAndLoadPage("NotInIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Lucy", elem.getText());
	}


	@Test
	public void isIsSupported() throws Exception {
		linkResourcesAndLoadPage("IsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}
	
	@Test
	public void isNotIsSupported() throws Exception {
		linkResourcesAndLoadPage("IsNotIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Lucy", elem.getText());
	}

	@Test
	public void hasIsSupported() throws Exception {
		linkResourcesAndLoadPage("HasIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Gielgud", elem.getText());
	}
	
	@Test
	public void notHasIsSupported() throws Exception {
		linkResourcesAndLoadPage("NotHasIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Lucy", elem.getText());
	}

	@Test
	public void containsIsSupported() throws Exception {
		linkResourcesAndLoadPage("ContainsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("JohnJohnny", elem.getText());
	}
	
	@Test
	public void notContainsIsSupported() throws Exception {
		linkResourcesAndLoadPage("NotContainsIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Lucy", elem.getText());
	}
	
	@Test
	public void roughlyIsSupported() throws Exception {
		linkResourcesAndLoadPage("RoughlyIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
	}

	@Test
	public void includeInFetchOneIsSupported() throws Exception {
		linkResourcesAndLoadPage("IncludeInFetchOneIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("{value:DeMurga}", elem.getText());
	}
	
	@Test
	public void includeInFetchManyIsSupported() throws Exception {
		linkResourcesAndLoadPage("IncludeInFetchManyIsSupported", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("{value:DeMurga}", elem.getText());
	}

}
