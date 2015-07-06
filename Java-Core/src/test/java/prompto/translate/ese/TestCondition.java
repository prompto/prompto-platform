// generated: 2015-07-05T23:01:00.907
package prompto.translate.ese;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestCondition extends BaseEParserTest {

	@Test
	public void testComplexIf() throws Exception {
		compareResourceESE("condition/complexIf.pec");
	}

	@Test
	public void testElseIf() throws Exception {
		compareResourceESE("condition/elseIf.pec");
	}

	@Test
	public void testReturnIf() throws Exception {
		compareResourceESE("condition/returnIf.pec");
	}

	@Test
	public void testSimpleIf() throws Exception {
		compareResourceESE("condition/simpleIf.pec");
	}

	@Test
	public void testSwitch() throws Exception {
		compareResourceESE("condition/switch.pec");
	}

	@Test
	public void testTernary() throws Exception {
		compareResourceESE("condition/ternary.pec");
	}

}
