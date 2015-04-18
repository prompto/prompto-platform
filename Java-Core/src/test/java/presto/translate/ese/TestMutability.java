package presto.translate.ese;

import org.junit.Test;

import presto.parser.e.BaseEParserTest;

public class TestMutability extends BaseEParserTest {

	@Test
	public void testImmutable() throws Exception {
		compareResourceESE("mutability/immutable.pec");
	}

	@Test
	public void testImmutableMember() throws Exception {
		compareResourceESE("mutability/immutableMember.pec");
	}

	@Test
	public void testMutable() throws Exception {
		compareResourceESE("mutability/mutable.pec");
	}

	@Test
	public void testMutableMember() throws Exception {
		compareResourceESE("mutability/mutableMember.pec");
	}

}
