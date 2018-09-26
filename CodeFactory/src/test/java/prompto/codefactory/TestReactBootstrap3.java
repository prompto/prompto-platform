package prompto.codefactory;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.intrinsic.PromptoVersion;
import prompto.parser.Dialect;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlDocumentReader;

@Category(HeadlessTests.class)
public class TestReactBootstrap3 extends BaseUITest {

	BaseCodeStore tail;
	Path tempDir;
	
	@Before
	public void before() {
		tail = getCodeStoreTail();
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


	@Test
	public void buttonOnClickCallbackIsCalled() throws Exception {
		linkResourcesAndLoadPage("ButtonOnClickCallbackIsCalled", Dialect.O);
		WebElement button = waitElement(By.id("button"), 2);
		button.click();
		WebElement out = waitElement(By.id("out"), 2);
		assertEquals("ok!", out.getText());
	}
	
	
	@Test
	public void buttonIsDisabled() throws Exception {
		linkResourcesAndLoadPage("ButtonIsDisabled", Dialect.O);
		WebElement button = waitElement(By.id("button"), 2);
		assertTrue(button.isEnabled());
		click(button, 100);
		assertFalse(button.isEnabled());
	}

	@Test
	public void buttonIsActive() throws Exception {
		linkResourcesAndLoadPage("ButtonIsActive", Dialect.O);
		WebElement button = waitElement(By.id("button"), 2);
		String klass = button.getAttribute("className");
		assertFalse(klass.contains("active"));
		click(button, 100);
		klass = button.getAttribute("className");
		assertTrue(klass.contains("active"));
	}

	@Test
	public void checkboxOnClickCallbackIsCalled() throws Exception {
		linkResourcesAndLoadPage("CheckboxOnClickCallbackIsCalled", Dialect.O);
		WebElement checkbox = waitElement(By.id("checkbox"), 2);
		assertFalse(checkbox.isSelected());
		checkbox.click();
		assertTrue(checkbox.isSelected());
		checkbox.click();
		assertFalse(checkbox.isSelected());
	}

	
	@Test
	public void checkboxIsDisabled() throws Exception {
		linkResourcesAndLoadPage("CheckboxIsDisabled", Dialect.O);
		WebElement checkbox = waitElement(By.id("checkbox"), 2);
		assertTrue(checkbox.isEnabled());
		click(checkbox, 100);
		assertFalse(checkbox.isEnabled());
	}


	@Test
	public void radioIsDisabled() throws Exception {
		linkResourcesAndLoadPage("RadioIsDisabled", Dialect.O);
		WebElement radio = waitElement(By.id("radio"), 2);
		assertTrue(radio.isEnabled());
		click(radio, 100);
		assertFalse(radio.isEnabled());
	}

	
	@Test
	public void radiosAreGrouped() throws Exception {
		linkResourcesAndLoadPage("RadiosAreGrouped", Dialect.O);
		WebElement radio1 = waitElement(By.id("radio1"), 2);
		WebElement radio2 = waitElement(By.id("radio2"), 2);
		assertFalse(radio1.isSelected());
		assertFalse(radio2.isSelected());
		click(radio1, 100);
		assertTrue(radio1.isSelected());
		assertFalse(radio2.isSelected());
		click(radio2, 100);
		assertFalse(radio1.isSelected());
		assertTrue(radio2.isSelected());
	}
	
	@Test
	public void radioOnChangeCallbackIsCalled() throws Exception {
		linkResourcesAndLoadPage("RadioOnChangeCallbackIsCalled", Dialect.O);
		WebElement radio1 = waitElement(By.id("radio1"), 2);
		WebElement radio2 = waitElement(By.id("radio2"), 2);
		WebElement out = waitElement(By.id("out"), 2);
		assertEquals("", out.getText());
		click(radio1, 100);
		assertEquals("radio1", out.getText());
		click(radio2, 100);
		assertEquals("radio1radio2", out.getText());
	}
	
	@Test
	public void inputIsDisabled() throws Exception {
		linkResourcesAndLoadPage("InputIsDisabled", Dialect.O);
		WebElement input = waitElement(By.id("input"), 2);
		assertTrue(input.isEnabled());
		WebElement button = waitElement(By.id("button"), 2);
		click(button, 100);
		assertFalse(input.isEnabled());
	}

	
	@Test
	public void inputOnChangeCallbackIsCalled() throws Exception {
		linkResourcesAndLoadPage("InputOnChangeCallbackIsCalled", Dialect.O);
		WebElement input = waitElement(By.id("input"), 2);
		WebElement out = waitElement(By.id("out"), 2);
		assertEquals("", out.getText());
		sendKeys(input, "H", 100);
		assertEquals("", out.getText());
		assertEquals("H", input.getAttribute("value"));
		sendKeys(input, "i", 100);
		assertEquals("H", out.getText());
		assertEquals("Hi", input.getAttribute("value"));
	}


	private void linkResourcesAndLoadPage(String resourceName, Dialect dialect) throws Exception {
		URL bootstrapURL = Thread.currentThread().getContextClassLoader().getResource("react-bootstrap-3/React-Bootstrap-3.pec");
		ImmutableCodeStore bootstrapResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, bootstrapURL, PromptoVersion.LATEST);
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("react-bootstrap-3-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(bootstrapResource, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		URL pageResourceURL = Thread.currentThread().getContextClassLoader().getResource("react-bootstrap-3-tests/" + resourceName + ".page");
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
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("react-bootstrap-3-tests/generic.page")) {
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


}
