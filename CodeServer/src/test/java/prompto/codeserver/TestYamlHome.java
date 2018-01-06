package prompto.codeserver;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.runtime.Mode;
import prompto.server.AppServer;
import prompto.utils.ManualTests;

@Category(ManualTests.class)
public class TestYamlHome {

	@Test
	public void testThatCodeServerRunsWithYamlHome() throws Throwable {
		URL url = Thread.currentThread().getContextClassLoader().getResource("home.yml");
		String[] args = new String[] { "yamlConfigFile", url.getFile() };
		CodeServer.main(args, Mode.UNITTEST);
		assertTrue(AppServer.isStarted());
	}
}
