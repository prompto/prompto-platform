package prompto.debug;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
import prompto.utils.Instance;
import prompto.utils.ManualTests;
import prompto.utils.StringUtils;


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
	Instance<String> output = new Instance<>();
	
	@Before
	public void before() throws Throwable {
		// note that with this cinematic, we can't debug the server startup method
		server.__before__(); 
		Standalone.clearGlobalContext();
	}
	
	
	@After
	public void after() throws Exception {
		server.__after__(); // stop server
	}

	@Override
	protected String readOut() throws IOException {
		String result = output.get();
		if(result==null)
			return "";
		String[] lines = result.split("\n");
		return lines.length>0 ? lines[lines.length-1] : "";
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
		// create request client
		debugger = new HttpClientDebugger("localhost", server.getPort());
		// start listening to debug events
		this.eventListener = new WebSocketDebugEventListener("localhost", server.getPort(), this);
		eventListener.startListening();
		waitConnected();
		// run method
		callMainMethod();
	}


	private void callMainMethod() throws Exception {
		URL url = new URL("http://localhost:" + server.getPort() + "/ws/run/main");
		new Thread(()->{
			try(InputStream input = url.openStream()) {
				output.set(StringUtils.stringFromStream(input));
			} catch(Exception e) {
				output.set(e.getMessage());
			}
		}).start();
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
	
	Object lock = null;
	
	private void waitConnected() throws InterruptedException {
		lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
		lock = null;
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
