// generated: 2015-07-05T23:01:00.906
package prompto.translate.eoe;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestCondition extends BaseEParserTest {

	@Test
	public void testComplexIf() throws Exception {
		compareResourceEOE("condition/complexIf.pec");
	}

	@Test
	public void testElseIf() throws Exception {
		compareResourceEOE("condition/elseIf.pec");
	}

	@Test
	public void testReturnIf() throws Exception {
		compareResourceEOE("condition/returnIf.pec");
	}

	@Test
	public void testSimpleIf() throws Exception {
		compareResourceEOE("condition/simpleIf.pec");
	}

	@Test
	public void testSwitch() throws Exception {
		compareResourceEOE("condition/switch.pec");
	}

	@Test
	public void testTernary() throws Exception {
		compareResourceEOE("condition/ternary.pec");
	}

}
