package prompto.translate.ese;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestStore extends BaseEParserTest {

	@Test
	public void testDeleteRecords() throws Exception {
		compareResourceESE("store/deleteRecords.pec");
	}

	@Test
	public void testFlush() throws Exception {
		compareResourceESE("store/flush.pec");
	}

	@Test
	public void testListRecords() throws Exception {
		compareResourceESE("store/listRecords.pec");
	}

	@Test
	public void testManyRecords() throws Exception {
		compareResourceESE("store/manyRecords.pec");
	}

	@Test
	public void testSimpleRecord() throws Exception {
		compareResourceESE("store/simpleRecord.pec");
	}

	@Test
	public void testSlicedRecords() throws Exception {
		compareResourceESE("store/slicedRecords.pec");
	}

	@Test
	public void testSortedRecords() throws Exception {
		compareResourceESE("store/sortedRecords.pec");
	}

	@Test
	public void testSubRecord() throws Exception {
		compareResourceESE("store/subRecord.pec");
	}

	@Test
	public void testUntypedRecord() throws Exception {
		compareResourceESE("store/untypedRecord.pec");
	}

}

