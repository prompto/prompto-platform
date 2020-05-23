package prompto.store.mongo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestStore extends BaseMongoTest {

	@Before
	public void before() throws Exception {
		createStore("TestStore");
	}
	
	@Test
	public void nextSequenceIncrements() {
		long counter = store.nextSequenceValue("test");
		assertEquals(1L, counter);
		counter = store.nextSequenceValue("test");
		assertEquals(2L, counter);
		counter = store.nextSequenceValue("test2");
		assertEquals(1L, counter);
	}
	
	@Test
	public void initialConfigIsNull() {
		Map<String, Object> config = store.fetchConfiguration("config");
		assertNull(config);
	}
	
	
	@Test
	public void configIsStored() {
		Map<String, Object> config = new HashMap<>();
		config.put("derivedFrom", true);
		store.storeConfiguration("config", config);
		config.put("_id", "config");
		Map<String, Object> stored = store.fetchConfiguration("config");
		assertEquals(config, stored);
	}
	

}
