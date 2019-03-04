package prompto.debug;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.config.IDebugConfiguration;
import prompto.config.IDebugEventAdapterConfiguration;
import prompto.config.IDebugRequestListenerConfiguration;
import prompto.config.IServerConfiguration;
import prompto.debug.IDebugEvent.Connected;
import prompto.intrinsic.PromptoVersion;
import prompto.runtime.Standalone;
import prompto.server.BaseServerTest;
import prompto.utils.Instance;
import prompto.utils.ManualTests;
import prompto.utils.StringUtils;

// not sure why this blocks in CI, individual tests are ok
@Category(ManualTests.class)
public class TestHttpDebugger extends TestDebuggerBase implements IDebugEventListener {

	static class DebuggingServer extends BaseServerTest {
		
		@Override
		protected IServerConfiguration getServerConfig() {
			return super.getServerConfig().withDebugConfiguration(
							new IDebugConfiguration.Inline()
								.withEventAdapterConfiguration(
									new IDebugEventAdapterConfiguration.Inline()
										.withFactory(WebSocketDebugEventAdapterFactory.class.getName())
								).withRequestListenerConfiguration(
									new IDebugRequestListenerConfiguration.Inline()
										.withFactory(HttpServletDebugRequestListenerFactory.class.getName())
								)
							);
		}
	}
	
	DebuggingServer server = new DebuggingServer();
	WebSocketDebugEventListener eventListener;
	Instance<String> output = new Instance<>();
	Instance<String> response = new Instance<>();
	
	@Before
	public void before() throws Throwable {
		ProcessDebugger.reset();
		// note that with this cinematic, we can't debug the server startup method
		server.__before__(); 
		Standalone.clearGlobalContext();
	}
	
	
	@After
	public void after() throws Exception {
		eventListener.stopListening();
		eventListener = null;
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
		Status status = debugger.getWorkerStatus(getDebuggedThread());
		while(status!=Status.SUSPENDED && status!=Status.TERMINATED) {
			Thread.sleep(100);
			status = debugger.getWorkerStatus(getDebuggedThread());
		}
	}

	@Override
	protected void start() throws Exception {
		// create request client
		debugger = new HttpDebugRequestClient("localhost", server.getPort(), ()->server.isAlive());
		// start listening to debug events
		this.eventListener = new WebSocketDebugEventListener("localhost", server.getPort(), this);
		eventListener.startListening();
		waitConnected();
		// run method
		callMainMethod();
	}
	
	@Override
	protected IWorker getDebuggedThread() {
		return new OnlyRemoteThread();
	}

	final Object lock = new Object();
	
	private void callMainMethod() throws Exception {
		final URL url = new URL("http://localhost:" + server.getPort() + "/ws/run/main");
		new Thread(()->{
			PrintStream oldOut = System.out;
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			System.setOut(new PrintStream(bytes));
			try(InputStream input = url.openStream()) {
				response.set(StringUtils.stringFromStream(input));
			} catch(Exception e) {
				response.set(e.getMessage());
			} finally {
				output.set(bytes.toString());
				System.setOut(oldOut);
			}
			synchronized (lock) {
				lock.notify();
			}		
		}).start();
	}


	@Override
	protected void join() throws Exception {
		synchronized (lock) {
			lock.wait();
		}
	}

	@Override
	protected void setDebuggedResource(String resourceName) throws Exception {
		// register code resource
		URL codeResourceURL = getResourceAsURL(resourceName);
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		server.getTail().setNext(codeResource);	
	}
	
	private void waitConnected() throws InterruptedException {
		synchronized (lock) {
			lock.wait();
		}
	}


	
	@Override
	public void handleConnectedEvent(Connected event) {
		((DebugRequestClient)debugger).setConnected(true);
		synchronized (lock) {
			lock.notify();
		}		
	}
	
	@Override
	public void handleReadyEvent() {
	}
	
	@Override
	public void handleStartedEvent(IWorker thread) {
	}

	@Override
	public void handleSuspendedEvent(IWorker thread, SuspendReason reason) {
	}
	
	@Override
	public void handleResumedEvent(IWorker thread, ResumeReason reason) {
	}
	
	@Override
	public void handleCompletedEvent(IWorker thread) {
	}
	
	@Override
	public void handleTerminatedEvent() {
	}

}
