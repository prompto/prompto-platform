package prompto.selenium;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

public interface WebDriverFactory {

	WebDriver newDriver(Properties props);

	default boolean isRunningInCI() {
		return "true".equals(System.getenv("TRAVIS"));
	}
}
