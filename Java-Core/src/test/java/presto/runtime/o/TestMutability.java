package presto.runtime.o;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import presto.parser.o.BaseOParserTest;
import presto.runtime.utils.Out;

public class TestMutability extends BaseOParserTest {

	@Before
	public void before() {
		Out.init();
	}

	@After
	public void after() {
		Out.restore();
	}

	@Test
	public void testImmutable() throws Exception {
		checkOutput("mutability/immutable.poc");
	}

	@Test
	public void testImmutableMember() throws Exception {
		checkOutput("mutability/immutableMember.poc");
	}

	@Test
	public void testMutable() throws Exception {
		checkOutput("mutability/mutable.poc");
	}

	@Test
	public void testMutableMember() throws Exception {
		checkOutput("mutability/mutableMember.poc");
	}

}
