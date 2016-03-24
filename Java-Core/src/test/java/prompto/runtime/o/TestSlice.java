package prompto.runtime.o;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import prompto.parser.o.BaseOParserTest;
import prompto.runtime.utils.Out;

public class TestSlice extends BaseOParserTest {

	@Before
	public void before() {
		Out.init();
	}

	@After
	public void after() {
		Out.restore();
	}

	@Test
	public void testInterpretedSliceList() throws Exception {
		checkInterpretedOutput("slice/sliceList.poc");
	}

	@Test
	public void testInterpretedSliceRange() throws Exception {
		checkInterpretedOutput("slice/sliceRange.poc");
	}

	@Test
	public void testInterpretedSliceText() throws Exception {
		checkInterpretedOutput("slice/sliceText.poc");
	}

}

