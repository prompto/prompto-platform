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
	public void testInterpretedDeepItem() throws Exception {
		checkInterpretedOutput("documents/deepItem.pec");
	}

	@Test
	public void testInterpretedDeepVariable() throws Exception {
		checkInterpretedOutput("documents/deepVariable.pec");
	}

	@Test
	public void testInterpretedItem() throws Exception {
		checkInterpretedOutput("documents/item.pec");
	}

	@Test
	public void testInterpretedVariable() throws Exception {
		checkInterpretedOutput("documents/variable.pec");
	}

}

