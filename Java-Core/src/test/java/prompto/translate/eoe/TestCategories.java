// generated: 2015-07-05T23:01:00.886
package prompto.translate.eoe;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestCategories extends BaseEParserTest {

	@Test
	public void testCopyFromAscendant() throws Exception {
		compareResourceEOE("categories/copyFromAscendant.pec");
	}

	@Test
	public void testCopyFromAscendantWithOverride() throws Exception {
		compareResourceEOE("categories/copyFromAscendantWithOverride.pec");
	}

	@Test
	public void testCopyFromDescendant() throws Exception {
		compareResourceEOE("categories/copyFromDescendant.pec");
	}

	@Test
	public void testCopyFromDescendantWithOverride() throws Exception {
		compareResourceEOE("categories/copyFromDescendantWithOverride.pec");
	}

}
