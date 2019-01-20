package prompto.debug;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.config.IDebugConfiguration;
import prompto.config.IServerConfiguration;
import prompto.debug.IDebugEvent.Connected;
import prompto.intrinsic.PromptoVersion;
import prompto.runtime.Standalone;
import prompto.server.BaseServerTest;
import prompto.store.NullStoreFactory;
import prompto.utils.IOUtils;
import prompto.utils.ManualTests;


@Category(ManualTests.class)
public class TestHttpDebugger extends TestDebuggerBase implements IDebugEventListener {

	static class DebuggingServer extends BaseServerTest {
		
		@Override
		protected IServerConfiguration getServerConfig() {
			return super.getServerConfig().withDebugConfiguration(
							new IDebugConfiguration.Inline()
								.withEventAdapterFactory(WebSocketDebugEventAdapterFactory.class.getName())
								.withRequestListenerFactory(HttpServletDebugRequestListenerFactory.class.getName())
							);
		}
	}
	
	DebuggingServer server = new DebuggingServer();
	WebSocketDebugEventListener eventListener;
	
	
	@Before
	public void before() throws Throwable {
		// start listening to debug events
		this.eventListener = new WebSocketDebugEventListener(this);
		eventListener.startListening();
		// with this cinematic, we can't debug the server startup method
		server.__before__(); 
	}
	
	
	@After
	public void after() throws Exception {
		server.__after__(); // stop server
	}

	@Override
	protected String readOut() throws IOException {
		/*String output = IOUtils.readFileToString(outputFile);
		String[] lines = output.split("\n");
		return lines.length>0 ? lines[lines.length-1] : "";
		*/
		return null;
	}
	
	
	@Override
	protected void waitSuspendedOrTerminated() throws Exception {
		Status status = debugger.getStatus(null);
		while(status!=Status.SUSPENDED && status!=Status.TERMINATED) {
			Thread.sleep(100);
			status = debugger.getStatus(null);
		}
	}

	@Override
	protected void start() throws Exception {
		debugger = new HttpClientDebugger(eventListener);
		waitConnected();
	}


	@Override
	protected void join() throws Exception {
		// process.waitFor();
	}

	@Override
	protected void setDebuggedResource(String resourceName) throws Exception {
		// register code resource
		URL codeResourceURL = getResourceAsURL(resourceName);
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		server.getTail().setNext(codeResource);	
}
	
	Object lock  = new Object();
	
	private void waitConnected() throws InterruptedException {
		synchronized (lock) {
			lock.wait();
		}
	}


	
	@Override
	public void handleConnectedEvent(Connected event) {
		synchronized (lock) {
			lock.notify();
		}		
	}

	@Override
	public void handleSuspendedEvent(SuspendReason reason) {
	}
	
	@Override
	public void handleResumedEvent(ResumeReason reason) {
	}
	
	@Override
	public void handleTerminatedEvent() {
	}

}
