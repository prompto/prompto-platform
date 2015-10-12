package prompto.translate.ese;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestClosures extends BaseEParserTest {

	@Test
	public void testGlobalClosureNoArg() throws Exception {
		compareResourceEOE("closures/globalClosureNoArg.pec");
	}

	@Test
	public void testGlobalClosureWithArg() throws Exception {
		compareResourceEOE("closures/globalClosureWithArg.pec");
	}

	@Test
	public void testInstanceClosureNoArg() throws Exception {
		compareResourceEOE("closures/instanceClosureNoArg.pec");
	}

}

