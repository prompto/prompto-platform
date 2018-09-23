package prompto.codeserver;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.ModuleType;
import prompto.intrinsic.PromptoVersion;
import prompto.parser.Dialect;

@Category(SeleniumTests.class)
public class TestReactBootstrap3 extends BaseUITest {

	BaseCodeStore tail;
	
	@Before
	public void before() {
		tail = getCodeStoreTail();
	}
	
	
	@After
	public void after() {
		if(tail!=null)
			tail.setNext(null);
	}
	
	private BaseCodeStore getCodeStoreTail() {
		ICodeStore store = ICodeStore.getInstance();
		while(store instanceof BaseCodeStore) {
			ICodeStore next = ((BaseCodeStore)store).getNext();
			if(next==null)
				return (BaseCodeStore)store;
			else
				store = next;	
		}
		return null;
	}



	@Test
	public void buttonOnClickCallbackIsCalled() throws Exception {
		linkResources("ButtonOnClickCallbackIsCalled", Dialect.O);
		loadPage("ButtonOnClickCallbackIsCalled");
	}
	

	private void loadPage(String pageName) {
		String url = "http://localhost:" + HTTP_PORT + "/" + pageName + ".page";
		webDriver.get(url);
	}


	private void linkResources(String resourceName, Dialect dialect) {
		URL bootstrapURL = Thread.currentThread().getContextClassLoader().getResource("react-bootstrap-3/React-Bootstrap-3.pec");
		ImmutableCodeStore bootstrapResource = new ImmutableCodeStore(null, ModuleType.LIBRARY, bootstrapURL, PromptoVersion.LATEST);
		URL codeResourceURL = Thread.currentThread().getContextClassLoader().getResource("react-bootstrap-3-tests/" + resourceName + ".p" + dialect.name().toLowerCase() + "c");
		ImmutableCodeStore codeResource = new ImmutableCodeStore(bootstrapResource, ModuleType.LIBRARY, codeResourceURL, PromptoVersion.LATEST);
		URL pageResourceURL = Thread.currentThread().getContextClassLoader().getResource("react-bootstrap-3-tests/" + resourceName + ".page");
		ImmutableCodeStore pageResource = new ImmutableCodeStore(codeResource, ModuleType.LIBRARY, pageResourceURL, PromptoVersion.LATEST);
		tail.setNext(pageResource);
	}


}
