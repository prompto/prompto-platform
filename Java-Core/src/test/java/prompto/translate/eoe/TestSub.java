// generated: 2015-07-05T23:01:01.137
package prompto.translate.eoe;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestSub extends BaseEParserTest {

	@Test
	public void testSubDate() throws Exception {
		compareResourceEOE("sub/subDate.pec");
	}

	@Test
	public void testSubDateTime() throws Exception {
		compareResourceEOE("sub/subDateTime.pec");
	}

	@Test
	public void testSubDecimal() throws Exception {
		compareResourceEOE("sub/subDecimal.pec");
	}

	@Test
	public void testSubInteger() throws Exception {
		compareResourceEOE("sub/subInteger.pec");
	}

	@Test
	public void testSubPeriod() throws Exception {
		compareResourceEOE("sub/subPeriod.pec");
	}

	@Test
	public void testSubTime() throws Exception {
		compareResourceEOE("sub/subTime.pec");
	}

}
