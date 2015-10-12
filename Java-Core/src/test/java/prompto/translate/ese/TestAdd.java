package prompto.translate.ese;

import org.junit.Test;

import prompto.parser.e.BaseEParserTest;

public class TestAdd extends BaseEParserTest {

	@Test
	public void testAddCharacter() throws Exception {
		compareResourceEOE("add/addCharacter.pec");
	}

	@Test
	public void testAddDate() throws Exception {
		compareResourceEOE("add/addDate.pec");
	}

	@Test
	public void testAddDateTime() throws Exception {
		compareResourceEOE("add/addDateTime.pec");
	}

	@Test
	public void testAddDecimal() throws Exception {
		compareResourceEOE("add/addDecimal.pec");
	}

	@Test
	public void testAddDict() throws Exception {
		compareResourceEOE("add/addDict.pec");
	}

	@Test
	public void testAddInteger() throws Exception {
		compareResourceEOE("add/addInteger.pec");
	}

	@Test
	public void testAddList() throws Exception {
		compareResourceEOE("add/addList.pec");
	}

	@Test
	public void testAddPeriod() throws Exception {
		compareResourceEOE("add/addPeriod.pec");
	}

	@Test
	public void testAddSet() throws Exception {
		compareResourceEOE("add/addSet.pec");
	}

	@Test
	public void testAddText() throws Exception {
		compareResourceEOE("add/addText.pec");
	}

	@Test
	public void testAddTime() throws Exception {
		compareResourceEOE("add/addTime.pec");
	}

	@Test
	public void testAddTuple() throws Exception {
		compareResourceEOE("add/addTuple.pec");
	}

}

