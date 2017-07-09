package prompto.codeserver;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import prompto.server.AppServer;

public class TestAppStore extends BaseWebTest {

	@BeforeClass
	public static void startCodeServer() throws Throwable {
		String[] args = {
				"-testMode",
				"true",
				"-http_port",
				"-1",
				"-codeStoreFactory",
				"prompto.store.solr.SOLRStoreFactory",
				"-dataStoreFactory",
				"prompto.store.solr.SOLRStoreFactory",
				"-solr-root-embedded",
				"-solr-root-root",
				"target/test-classes/solr-test",
				"-solr-code-embedded",
				"-solr-code-root",
				"target/test-classes/solr-test"
		};
		CodeServer.main(args);
		HTTP_PORT = AppServer.getHttpPort();
	}
	
	@AfterClass
	public static void stopCodeServer() throws Exception {
		AppServer.stop();
	}
	
	static int HTTP_PORT;
	static final String ROOT_URL = "http://localhost:";
	
	@Ignore("Needs refactoring!!!")
	@Test
	public void testLoadAppStore() throws Exception {
		webDriver.get(ROOT_URL + HTTP_PORT + "/");
		webDriver.switchTo().frame("content-frame");
		String dbId = getDbIdForModule("Inventory");
		WebElement we = waitElement(By.id(dbId));
		assertTrue(we.getText().startsWith("Inventory"));
	}

}
