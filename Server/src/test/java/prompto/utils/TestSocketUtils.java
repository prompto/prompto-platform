package prompto.utils;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ServerSocketFactory;

import org.junit.Before;
import org.junit.Test;

import prompto.config.TempDirectories;
import prompto.runtime.Mode;

public class TestSocketUtils {

	@Before
	public void __before__() throws Throwable {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
	}
	
	
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
		int port = 8000;
		try(ServerSocket s = ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost"))) {
			s.setReuseAddress(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try { 
						System.out.println("accepting");
						s.accept(); 
					} catch (IOException e) {
					}
				}
			}).start();
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					try { 
						System.out.println("closing");
						s.close(); 
					} catch (IOException e) {
					}
				}
			}, 100); 
			System.out.println("grabbing");
			SocketUtils.findAvailablePortInRange(port,  port);
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
