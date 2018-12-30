package prompto.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
public class TestRequire extends BaseUITest {

	Path tempDir;
	

	@Test
	public void urlContentIsLoaded() throws Exception {
		linkResourcesAndLoadPage("UrlContentIsLoaded", Dialect.O);
		WebElement out = waitElement(By.id("value"), 2);
		assertEquals("ok!", out.getText());
	}
	
	


	private void linkResourcesAndLoadPage(String resourceName, Dialect dialect) throws Exception {
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("require-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		URL pageResourceURL = Thread.currentThread().getContextClassLoader().getResource("require-tests/" + resourceName + ".page");
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
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("require-tests/generic.page")) {
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
