package prompto.codeserver;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestModuleProcess {

	@Test
	public void testLocateYamlConfigFile() {
		String cmdLine = "prompto.devcenter.Application -yamlConfigFile /Users/ericvergnaud/Development/prompto/prompto-dev-center/DevCenter/src/test/resources/deploy-prompto-seed.yml";
		String location = "/Users/ericvergnaud/Development/prompto/prompto-dev-center/DevCenter/src/test/resources/deploy-prompto-seed.yml";
		assertEquals(location, ModuleProcess.extractCmdLineArgument(cmdLine, "-yamlConfigFile"));
	}
}
