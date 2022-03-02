package prompto.selenium;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public interface WebDriverFactory {

	WebDriver newDriver(Properties props);

	default boolean isRunningInCI() {
		return Arrays.asList("CIRCLECI", "TRAVIS")
				.stream()
				.map(System::getenv)
				.filter(Objects::nonNull)
				.anyMatch(s -> s.equals("true"));
	}
}
