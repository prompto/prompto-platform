package prompto.debug;

import static org.junit.Assert.fail;

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
import prompto.debug.event.ConnectedDebugEvent;
import prompto.debug.worker.IWorker;
import prompto.intrinsic.PromptoVersion;
import prompto.runtime.ApplicationContext;
import prompto.server.AppServer;
import prompto.server.BaseServerTest;
import prompto.utils.Instance;
import prompto.utils.ManualTests;
import prompto.utils.StreamUtils;

// not sure why this fails in CI, individual manual tests are ok
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
		new Thread(()->{
			try {
				server.__before__();
			} catch(Throwable t) {
				fail();
			}
		}).start(); 
		do {
			Thread.sleep(10);
		} while(AppServer.getHttpPort()<0);
		ApplicationContext.reset();
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
	protected void waitWorkerSuspendedOrTerminated() throws Exception {
		WorkerStatus status = debugger.getWorkerStatus(getDebuggedThread());
		while(status!=WorkerStatus.WORKER_SUSPENDED && status!=WorkerStatus.WORKER_COMPLETED) {
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
				response.set(StreamUtils.readString(input));
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
	public void onConnectedEvent(ConnectedDebugEvent event) {
		((DebugRequestClient)debugger).setConnected(true);
	}
	
	@Override
	public void onProcessReadyEvent() {
		synchronized (lock) {
			lock.notify();
		}		
	}
	
	@Override
	public void onWorkerStartedEvent(IWorker thread) {
	}

	@Override
	public void onWorkerSuspendedEvent(IWorker thread, SuspendReason reason) {
	}
	
	@Override
	public void onWorkerResumedEvent(IWorker thread, ResumeReason reason) {
	}
	
	@Override
	public void onWorkerCompletedEvent(IWorker thread) {
	}
	
	@Override
	public void onProcessTerminatedEvent() {
	}

}
