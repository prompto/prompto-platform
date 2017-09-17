package prompto.server;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import prompto.security.MockTrustManager;

public class TestConnectSSL extends BaseServerTest {
	
	@Before
	public void before() throws Exception {
		MockTrustManager.install();
	}

	@After
	public void after() {
		MockTrustManager.restore();
	}

	public TestConnectSSL() {
		this.ssl = true;
	}
	
	@Test
	public void testStartAndStop() throws Throwable {
		AppServer.stop();
		assertFalse(AppServer.isStarted());
		AppServer.start(null);
		assertTrue(AppServer.isStarted());
		AppServer.stop();
		assertFalse(AppServer.isStarted());
	}

	@Test
	public void testResource() throws Exception {
		URL url = new URL("https://localhost:" + port + "/ws/control/version");
		try (InputStream input = url.openStream()) {
			try(Reader reader = new InputStreamReader(input)) {
				try(BufferedReader buffered = new BufferedReader(reader)) {
					assertEquals("1.0.0", buffered.readLine());
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		} 
	}

}
