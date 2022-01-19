package prompto.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlDocumentReader;

import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.intrinsic.PromptoVersion;
import prompto.parser.Dialect;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Standalone;
import prompto.store.DataStore;
import prompto.store.memory.MemStore;

@Category(HeadlessTests.class)
public class TestRemoteExec extends BaseUITest {

	Path tempDir;	

	@Before
	public void before() throws Exception {
		ApplicationContext.reset();
		DataStore.setGlobal(new MemStore());
		DataStore.useGlobal();
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
	}
	
	
	private void linkResourcesAndLoadPage(String resourceName, Dialect dialect) throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("remote-exec-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		URL pageResourceURL = Thread.currentThread().getContextClassLoader().getResource("remote-exec-tests/" + resourceName + ".page");
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
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("remote-exec-tests/generic.page")) {
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
	public void voidIsExecuted() throws Exception {
		linkResourcesAndLoadPage("VoidIsExecuted", Dialect.O);
		Thread.sleep(1000);
		WebElement root = waitElement(By.id("body"), 3);
		assertEquals("EXECUTED", root.getText());
	}
	
	@Test
	public void textIsReturned() throws Exception {
		linkResourcesAndLoadPage("TextIsReturned", Dialect.O);
		Thread.sleep(1000);
		WebElement root = waitElement(By.id("body"), 3);
		assertEquals("RETURNED", root.getText());
	}

	@Test
	public void dbIdIsAccepted() throws Exception {
		linkResourcesAndLoadPage("DbIdIsAccepted", Dialect.O);
		Thread.sleep(1000);
		WebElement root = waitElement(By.id("body"), 3);
		assertEquals("ACCEPTED", root.getText());
	}

	@Test
	public void clientEntityIsNotDuplicated() throws Exception {
		assertEquals(0, DataStore.getInstance().fetchMany(null).count());
		linkResourcesAndLoadPage("ClientEntityIsNotDuplicated", Dialect.O);
		Thread.sleep(1000);
		WebElement root = waitElement(By.id("body"), 3);
		assertEquals("EXECUTED", root.getText());
		assertEquals(1, DataStore.getInstance().fetchMany(null).count());
	}

}
