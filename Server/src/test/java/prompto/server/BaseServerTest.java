package prompto.server;

import static org.junit.Assert.*;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.After;
import org.junit.Before;

import prompto.code.Version;
import prompto.memstore.MemStore;
import prompto.runtime.Application;

public abstract class BaseServerTest {
	
	int port = -1;
	
	@Before
	public void __before__() throws Throwable {
		bootstrapCodeStore();
		port = AppServer.startServer(port, null, null, null, ()->prepareHandler(null), null);
		Thread.sleep(100);
		assertTrue(AppServer.isStarted());
	}
	
	public static Handler prepareHandler(String webSite) {
		try {
			HandlerList list = new HandlerList();
			list.addHandler(getResourceHandler());
			list.addHandler(getServiceHandler());
			list.addHandler(new DefaultHandler());
			return list;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Handler getResourceHandler() throws Exception {
		return AppServer.newResourceHandler("/", null);
	}

	private static Handler getServiceHandler() {
		String thisPath = BaseServerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println("Loading classes from: " + thisPath);
		return AppServer.newServiceHandler("/ws", thisPath);
	}

	public static void bootstrapCodeStore() throws Exception {
		Application.bootstrapCodeStore(new MemStore(), "test", Version.parse("1.0.0"), true, null);
	}

	@After
	public void __after__() throws Exception {
		port = -1;
		if(AppServer.isStarted()) {
			AppServer.stop();
			Thread.sleep(100);
		}
	}
	

}
