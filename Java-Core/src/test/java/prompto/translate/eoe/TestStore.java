package prompto.translate.eoe;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestStore extends BaseEParserTest {

	@Test
	public void testRecord() throws Exception {
		compareResourceEOE("store/record.pec");
	}

}
