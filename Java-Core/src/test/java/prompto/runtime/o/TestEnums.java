// generated: 2015-07-05T23:01:00.948
package prompto.runtime.o;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import prompto.parser.o.BaseOParserTest;
import prompto.runtime.utils.Out;

public class TestEnums extends BaseOParserTest {

	@Before
	public void before() {
		Out.init();
	}

	@After
	public void after() {
		Out.restore();
	}

	@Test
	public void testCategoryEnum() throws Exception {
		checkOutput("enums/categoryEnum.poc");
	}

	@Test
	public void testIntegerEnum() throws Exception {
		checkOutput("enums/integerEnum.poc");
	}

	@Test
	public void testTextEnum() throws Exception {
		checkOutput("enums/textEnum.poc");
	}

}
