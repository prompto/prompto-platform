package prompto.codeserver;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

import de.flapdoodle.embed.mongo.MongodExecutable;
import prompto.server.AppServer;
import prompto.store.mongo.BaseMongoTest;

public class TestYamlLocal {

	@Test
	public void testThatCodeServerRunsWithYamlLocal() throws Throwable {
		MongodExecutable mongo = BaseMongoTest.startMongo(27017);
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("local.yml");
			String[] args = new String[] { "yamlConfigFile", url.getFile() };
			CodeServer.main(args, true);
			assertTrue(AppServer.isStarted());
		} finally {
			BaseMongoTest.stopMongo(mongo);
		}
	}
	
}