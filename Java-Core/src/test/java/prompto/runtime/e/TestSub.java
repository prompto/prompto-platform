package prompto.runtime.e;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import prompto.parser.e.BaseEParserTest;
import prompto.runtime.utils.Out;

public class TestSub extends BaseEParserTest {

	@Before
	public void before() {
		Out.init();
	}

	@After
	public void after() {
		Out.restore();
	}

	@Test
	public void testInterpretedSubDate() throws Exception {
		checkInterpretedOutput("sub/subDate.pec");
	}

	@Test
	public void testInterpretedSubDateTime() throws Exception {
		checkInterpretedOutput("sub/subDateTime.pec");
	}

	@Test
	public void testInterpretedSubDecimal() throws Exception {
		checkInterpretedOutput("sub/subDecimal.pec");
	}

	@Test
	public void testInterpretedSubInteger() throws Exception {
		checkInterpretedOutput("sub/subInteger.pec");
	}

	@Test
	public void testInterpretedSubPeriod() throws Exception {
		checkInterpretedOutput("sub/subPeriod.pec");
	}

	@Test
	public void testInterpretedSubTime() throws Exception {
		checkInterpretedOutput("sub/subTime.pec");
	}

}

