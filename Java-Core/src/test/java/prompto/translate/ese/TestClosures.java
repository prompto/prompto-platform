// generated: 2015-07-05T23:01:00.898
package prompto.translate.ese;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestClosures extends BaseEParserTest {

	@Test
	public void testGlobalClosureNoArg() throws Exception {
		compareResourceESE("closures/globalClosureNoArg.pec");
	}

	@Test
	public void testGlobalClosureWithArg() throws Exception {
		compareResourceESE("closures/globalClosureWithArg.pec");
	}

	@Test
	public void testInstanceClosureNoArg() throws Exception {
		compareResourceESE("closures/instanceClosureNoArg.pec");
	}

}
