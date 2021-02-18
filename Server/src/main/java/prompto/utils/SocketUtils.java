package prompto.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.net.ServerSocketFactory;

public abstract class SocketUtils {

	public static int findAvailablePortInRange(int min, int max) throws IOException {
		Set<Integer> alreadyTried = new HashSet<>();
		for(;;) {
			int port = ThreadLocalRandom.current().nextInt(min, max + 1);
			if(!alreadyTried.add(port))
				continue;
			if(isAvailablePort(port))
				return port;
			if(alreadyTried.size() >= 1 + max - min)
				throw new IOException("No available port!");
		}
	}
	
	public static boolean isAvailablePort(int port) {
		try(ServerSocket s = ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost"))) {
			s.setReuseAddress(false);
			return true;
		} catch(IOException e) {
			return false;
		}
	}


}
