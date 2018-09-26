package prompto.codefactory;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import prompto.codefactory.Application;
import prompto.server.AppServer;

public class TestAppStore extends BaseBrowserTest {

	@BeforeClass
	public static void startCodeServer() throws Throwable {
		String[] args = {
				"-testMode",
				"true",
				"-http-port",
				"-1",
				"-codeStore-factory",
				"prompto.store.solr.SOLRStoreFactory",
				"-codeStore-root",
				"target/test-classes/solr-test",
				"-codeStore-dbName",
				"APPS",
				"-dataStore-factory",
				"prompto.store.solr.SOLRStoreFactory",
				"-dataStore-root",
				"target/test-classes/solr-test",
				"-codeStore-dbName",
				"DATA"
		};
		Application.main(args);
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
		WebElement we = waitElement(By.id(dbId), 2);
		assertTrue(we.getText().startsWith("Inventory"));
	}

}
