package prompto.selenium;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import prompto.utils.ResourceUtils;

public class ChromeWebDriverFactory implements WebDriverFactory {

	static {
		setChromeDriverLocationProperty();
	}
	
	static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static void setChromeDriverLocationProperty() {
		if(System.getProperty(CHROME_DRIVER_PROPERTY)!=null)
			return;
		File file = locateChromeDriver();
		System.setProperty(CHROME_DRIVER_PROPERTY, file.getAbsolutePath());
	}

	private static File locateChromeDriver() {
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.startsWith("mac"))
			return locateChromeDriver("macos");
		else if(osName.startsWith("linux"))
			return locateChromeDriver("linux");
		else
			throw new UnsupportedOperationException("Unsupported OS: " + osName);
	}

	private static File locateChromeDriver(String osName) {
		String resourceName = "selenium/drivers/" + osName + "/chromedriver";
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		if(url==null)
			throw new UnsupportedOperationException("Driver not found: " + resourceName);
		else if("file".equals(url.getProtocol())) 
			return driverFile(url);
		else if("jar".equals(url.getProtocol())) try {
			Path temp = Files.createTempFile("chromedriver_", "");
			ResourceUtils.copyResourceToFile(url, temp);
			return driverFile(temp.toUri().toURL());
		} catch(IOException e) {
			throw new UnsupportedOperationException(e.getMessage());
		} else
			throw new UnsupportedOperationException("Unsupported protocol: " + url.getProtocol());
	}

	private static File driverFile(URL url) {
		try {
			File file = new File(url.toURI());
			Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rwxr-xr-x"));
			return file;
		} catch(URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public WebDriver newDriver(Properties props) {
		ChromeOptions chromeOptions = new ChromeOptions();
		if(isRunningInCI()) {
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--no-sandbox");
		}
		return new ChromeDriver(chromeOptions);
	}


}
