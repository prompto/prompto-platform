package prompto.selenium;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
		URL url = Thread.currentThread().getContextClassLoader().getResource("prompto/selenium/drivers/" + osName + "/chromedriver");
		if("file".equals(url.getProtocol())) try {
			File file = new File(url.toURI());
			Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rwxr-xr-x"));
			return file;
		} catch(URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		} else
			throw new UnsupportedOperationException("Unsupported protocol: " + url.getProtocol());
	}

	@Override
	public WebDriver newDriver(Properties props) {
		return new ChromeDriver();
	}

}
