package prompto.server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import prompto.selenium.WebDriverFactory;
import prompto.store.AttributeInfo;
import prompto.store.DataStore;
import prompto.store.IQueryBuilder;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStore;
import prompto.store.IStored;

public abstract class BaseWebTest {

	protected static WebDriver webDriver;
	protected static Properties properties;
	
	static void readProperties() throws IOException {
		properties = new Properties();
		try(InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("selenium.properties")) {
			properties.load(input);
		}
	}
	
	@SuppressWarnings("unchecked")
	static void loadWebDriver() throws Exception {
		String className = properties.getProperty("web-driver-factory");
		Class<? extends WebDriverFactory> klass = (Class<? extends WebDriverFactory>)Class.forName(className);
		webDriver = klass.getDeclaredConstructor().newInstance().newDriver(properties);
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		webDriver.manage().window().setSize(new Dimension(1300, 900));
	}
	
	static void closeWebDriver() {
		if(webDriver!=null) {
			webDriver.quit();
			webDriver = null;
		}
	}

	@BeforeClass
	public static void __beforeClass__() throws Exception {
		readProperties();
		loadWebDriver();
	}
	
	
	@AfterClass
	public static void __afterClass__() {
		closeWebDriver();
	}
	
	protected void click(WebElement we, int waitMillis) throws InterruptedException {
		// Actions actions = new Actions(webDriver);
		// actions.moveToElement(we).perform();
		we.click();
		Thread.sleep(waitMillis);
	}

	protected void sendKeys(WebElement we, String keysToSend, int waitMillis) throws InterruptedException {
		// Actions actions = new Actions(webDriver);
		// actions.moveToElement(we).perform();
		we.sendKeys(keysToSend);
		Thread.sleep(waitMillis);
	}
	
	protected WebElement waitElement(By by, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(seconds));
			return wait.until( new ExpectedCondition<WebElement>() {
	
				@Override public WebElement apply(WebDriver input) {
					return webDriver.findElement(by);
				}
			});
		} catch(Throwable t) {
			String source = webDriver.getPageSource();
			if(source==null || source.isEmpty())
				source = "<empty source>";
			System.out.println(source);
			throw t;
		}
	}

	protected WebElement waitElement(WebElement start, By by, int seconds) {
		try {
			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(seconds));
			return wait.until( new ExpectedCondition<WebElement>() {
	
				@Override public WebElement apply(WebDriver input) {
					return start.findElement(by);
				}
			});
		} catch(Throwable t) {
			String source = webDriver.getPageSource();
			if(source==null || source.isEmpty())
				System.out.println("<empty source>");
			else try {
				Path path = Files.createTempFile(this.getClass().getSimpleName(), "-test.js");
				try(FileWriter writer = new FileWriter(path.toFile())) {
					writer.write(source);
				}
				System.out.println("Failing source dumped @ " + path.toString());
			} catch(Exception e) {
				e.printStackTrace();
			}
			throw t;
		}
	}

	protected String getDbIdForModule(String name) throws Exception {
		IStore store = DataStore.getInstance();
		store.flush();
		IQueryBuilder builder = store.newQueryBuilder();
		builder.verify(AttributeInfo.CATEGORY, MatchOp.CONTAINS, "Module");
		builder.verify(AttributeInfo.NAME, MatchOp.EQUALS, name);
		builder.and();
		IStored stored = store.fetchOne(builder.build());
		return stored!=null ? stored.getDbId().toString() : null;
	}



}
