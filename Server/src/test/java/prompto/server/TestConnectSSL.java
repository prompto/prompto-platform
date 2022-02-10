package prompto.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.http.HttpRequest;

import org.junit.Test;

import prompto.utils.SSLUtils;

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
		HttpRequest request = HttpRequest.newBuilder(new URI("https://localhost:" + port + "/ws/control/version")).build();
		assertTrue(SSLUtils.trustingAllCertificates(request, s -> {
			return "1.0.0".equals(s);
		}));
	}

}
