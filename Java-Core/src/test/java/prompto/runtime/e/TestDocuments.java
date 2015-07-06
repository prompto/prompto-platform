// generated: 2015-07-05T23:01:00.937
package prompto.runtime.e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import prompto.parser.e.BaseEParserTest;
import prompto.runtime.utils.Out;

public class TestDocuments extends BaseEParserTest {

	@Before
	public void before() {
		Out.init();
	}

	@After
	public void after() {
		Out.restore();
	}

	@Test
	public void testDeepItem() throws Exception {
		checkOutput("documents/deepItem.pec");
	}

	@Test
	public void testDeepVariable() throws Exception {
		checkOutput("documents/deepVariable.pec");
	}

	@Test
	public void testItem() throws Exception {
		checkOutput("documents/item.pec");
	}

	@Test
	public void testVariable() throws Exception {
		checkOutput("documents/variable.pec");
	}

}
