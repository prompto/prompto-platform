package prompto.codeserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;

public class TestModuleProcess {

	@Test
	public void testLocateYamlConfigFile() {
		String cmdLine = "prompto.devcenter.Application -yamlConfigFile /Users/ericvergnaud/Development/prompto/prompto-dev-center/DevCenter/src/test/resources/deploy-prompto-seed.yml";
		String location = "/Users/ericvergnaud/Development/prompto/prompto-dev-center/DevCenter/src/test/resources/deploy-prompto-seed.yml";
		assertEquals(location, ModuleProcess.extractCmdLineArgument(cmdLine, "-yamlConfigFile"));
	}
	
	@Test
	public void testThatAValidPortIsReturned() throws IOException {
		int port = ModuleProcess.findAvailablePortInRange(8080,  9090);
		assertTrue(port >= 8080 && port <= 9090);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testThatInvalidRangeThrows() throws IOException {
		int port = ModuleProcess.findAvailablePortInRange(8080,  8079);
		assertTrue(port >= 8080 && port <= 9090);
	}

	@Test(expected=IOException.class)
	public void testThatTrulyNoPortThrows() throws IOException {
		try(ServerSocket s = new ServerSocket(0)) {
			s.setReuseAddress(true);
			ModuleProcess.findAvailablePortInRange(s.getLocalPort(),  s.getLocalPort());
		}
	}

} 
