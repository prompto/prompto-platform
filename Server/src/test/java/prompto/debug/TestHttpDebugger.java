package prompto.debug;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.intrinsic.PromptoVersion;
import prompto.runtime.Standalone;
import prompto.server.BaseServerTest;
import prompto.store.NullStoreFactory;
import prompto.utils.IOUtils;
import prompto.utils.ManualTests;


@Category(ManualTests.class)
public class TestHttpDebugger extends TestDebuggerBase {

	static class ServerTest extends BaseServerTest {}
	
	ServerTest base = new ServerTest();
	HttpDebugEventServer eventServer;
	
	@Before
	public void before() throws Throwable {
		base.__before__();
	}
	
	
	@After
	public void after() throws Exception {
		base.__after__();
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
	protected void waitBlockedOrKilled() throws Exception {
		Status status = debugger.getStatus(null);
		while(status!=Status.SUSPENDED && status!=Status.TERMINATED) {
			Thread.sleep(100);
			status = debugger.getStatus(null);
		}
	}

	@Override
	protected void start() throws Exception {
		/*
		process = builder.start();
		debugger = new DebugRequestClient(process, eventServer);
		waitConnected();
		*/
	}


	@Override
	protected void join() throws Exception {
		// process.waitFor();
	}

	@Override
	protected void debugResource(String resourceName) throws Exception {
		// start listening to debug events
		this.eventServer = new HttpDebugEventServer(null);
		final int port = eventServer.startListening();
		// register code resource
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		ImmutableCodeStore codeResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		base.getTail().setNext(codeResource);	
		// call main in debug mode
		
	}

}
