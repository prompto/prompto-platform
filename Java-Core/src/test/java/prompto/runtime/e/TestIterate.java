package prompto.runtime.e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import prompto.parser.e.BaseEParserTest;
import prompto.runtime.utils.Out;

public class TestIterate extends BaseEParserTest {

	@Before
	public void before() {
		Out.init();
	}

	@After
	public void after() {
		Out.restore();
	}

	@Test
	public void testInterpretedForEachCategoryList() throws Exception {
		checkInterpretedOutput("iterate/forEachCategoryList.pec");
	}

	@Test
	public void testInterpretedForEachIntegerList() throws Exception {
		checkInterpretedOutput("iterate/forEachIntegerList.pec");
	}

}

