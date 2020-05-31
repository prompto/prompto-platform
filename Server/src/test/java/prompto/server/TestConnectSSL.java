package prompto.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Test;

import prompto.security.TrustAllCertificatesManager;

public class TestConnectSSL extends BaseServerTest {
	
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
		HttpsURLConnection cnx = (HttpsURLConnection)url.openConnection();
		TrustAllCertificatesManager.install(cnx);
		InputStream input = cnx.getInputStream();
		try(Reader reader = new InputStreamReader(input)) {
			try(BufferedReader buffered = new BufferedReader(reader)) {
				assertEquals("1.0.0", buffered.readLine());
			}
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		} finally {
			cnx.disconnect();
		}
	}

}
