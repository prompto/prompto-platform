package prompto.utils;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;

import org.junit.Test;

public class TestSocketUtils {

	@Test
	public void testThatAValidPortIsReturned() throws IOException {
		int port = SocketUtils.findAvailablePortInRange(8080,  9090);
		assertTrue(port >= 8080 && port <= 9090);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testThatInvalidRangeThrows() throws IOException {
		int port = SocketUtils.findAvailablePortInRange(8080,  8079);
		assertTrue(port >= 8080 && port <= 9090);
	}

	@Test(expected=IOException.class)
	public void testThatTrulyNoPortThrows() throws IOException {
		try(ServerSocket s = new ServerSocket(0)) {
			s.setReuseAddress(true);
			SocketUtils.findAvailablePortInRange(s.getLocalPort(),  s.getLocalPort());
		}
	}
	
	@Test
	public void testThatReturnedPortIsStillValid() throws IOException {
		int port = SocketUtils.findAvailablePortInRange(8080,  9090);
		try(ServerSocket s = ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost"))) {
			s.setReuseAddress(false);
		}
	}


}
