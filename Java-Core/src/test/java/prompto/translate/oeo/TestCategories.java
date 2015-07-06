// generated: 2015-07-05T23:01:00.890
package prompto.translate.oeo;

import org.junit.Test;

import prompto.parser.o.BaseOParserTest;

public class TestCategories extends BaseOParserTest {

	@Test
	public void testCopyFromAscendant() throws Exception {
		compareResourceOEO("categories/copyFromAscendant.poc");
	}

	@Test
	public void testCopyFromAscendantWithOverride() throws Exception {
		compareResourceOEO("categories/copyFromAscendantWithOverride.poc");
	}

	@Test
	public void testCopyFromDescendant() throws Exception {
		compareResourceOEO("categories/copyFromDescendant.poc");
	}

	@Test
	public void testCopyFromDescendantWithOverride() throws Exception {
		compareResourceOEO("categories/copyFromDescendantWithOverride.poc");
	}

}
