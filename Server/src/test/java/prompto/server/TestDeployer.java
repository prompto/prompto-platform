package prompto.server;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.declaration.IMethodDeclaration;
import prompto.intrinsic.PromptoVersion;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Standalone;
import prompto.store.DataStore;
import prompto.store.memory.MemStore;

public class TestDeployer extends BaseUITest {

	@Before
	public void before() throws Exception {
		ApplicationContext.reset();
		DataStore.setGlobal(new MemStore());
		DataStore.useGlobal();
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
	}

	private void linkResources(String ... resourceNames) throws Exception {
		for(String resourceName : resourceNames) {
			URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource(resourceName);
			ImmutableCodeStore codeResource = new ImmutableCodeStore(tail.getNext(), ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
			tail.setNext(codeResource);
		}
		Standalone.synchronizeSchema(ICodeStore.getInstance(), DataStore.getInstance());
	}


	@Test
	public void deployerCodeIsValid() throws Exception {
		linkResources("libraries/AwsEC2.pec", "deployer/AwsDeployer.pec");
		ICodeStore.getInstance().fetchDeclarations("stuffReleased").forEach(decl->((IMethodDeclaration)decl).check(ApplicationContext.get(), true));
	}
}
