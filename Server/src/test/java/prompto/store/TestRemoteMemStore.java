package prompto.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import prompto.code.ICodeStore;
import prompto.parser.Dialect;
import prompto.runtime.ApplicationContext;
import prompto.server.HeadlessTests;
import prompto.store.IAuditRecord.Operation;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.memory.MemStore;

@Category(HeadlessTests.class)
public class TestRemoteMemStore extends TestRemoteStoreBase {


	@Override
	protected IStore getDataStore() {
		return new MemStore(()->true);
	}
	
	@Test
	public void insertIsAudited() throws Exception {
		linkResourcesAndLoadPage("InsertIsAudited", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
		IQueryBuilder builder = DataStore.getInstance().newQueryBuilder();
		AttributeInfo info = ICodeStore.getInstance().fetchAttributeInfo(ApplicationContext.get(), "value");
		builder.verify(info, MatchOp.EQUALS, "John");
		IStored stored = DataStore.getInstance().fetchOne(builder.build());
		assertNotNull(stored);
		Object metadataId = DataStore.getInstance().fetchLatestAuditMetadataId(stored.getDbId());
		assertNotNull(metadataId);
		IAuditMetadata metadata = DataStore.getInstance().fetchAuditMetadata(metadataId);
		assertEquals("Hello", metadata.get("message"));
		IAuditRecord record = DataStore.getInstance().fetchLatestAuditRecord(stored.getDbId());
		assertEquals(Operation.INSERT, record.getOperation());
	}


	@Test
	public void updateIsAudited() throws Exception {
		linkResourcesAndLoadPage("UpdateIsAudited", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
		IQueryBuilder builder = DataStore.getInstance().newQueryBuilder();
		AttributeInfo info = ICodeStore.getInstance().fetchAttributeInfo(ApplicationContext.get(), "value");
		builder.verify(info, MatchOp.EQUALS, "John");
		IStored stored = DataStore.getInstance().fetchOne(builder.build());
		assertNotNull(stored);
		Object metadataId = DataStore.getInstance().fetchLatestAuditMetadataId(stored.getDbId());
		assertNotNull(metadataId);
		IAuditMetadata metadata = DataStore.getInstance().fetchAuditMetadata(metadataId);
		assertEquals("Hello", metadata.get("message"));
		IAuditRecord record = DataStore.getInstance().fetchLatestAuditRecord(stored.getDbId());
		assertEquals(Operation.UPDATE, record.getOperation());
	}

	@Test
	public void deleteIsAudited() throws Exception {
		linkResourcesAndLoadPage("DeleteIsAudited", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("John", elem.getText());
		List<? extends IAuditRecord> records = DataStore.getInstance().fetchAuditRecordsMatching(Collections.singletonMap("operation", "DELETE"), null);
		assertEquals(1, records.size());
		IAuditRecord record = records.get(0);
		assertEquals(Operation.DELETE, record.getOperation());
		IAuditMetadata metadata = DataStore.getInstance().fetchAuditMetadata(record.getAuditMetadataId());
		assertEquals("Hello", metadata.get("message"));
	}

}
