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
import prompto.intrinsic.PromptoDbId;
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
		PromptoDbId metadataId = DataStore.getInstance().fetchLatestAuditMetadataId(stored.getDbId());
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
		PromptoDbId metadataId = DataStore.getInstance().fetchLatestAuditMetadataId(stored.getDbId());
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
		IAuditMetadata metadata = DataStore.getInstance().fetchAuditMetadata(record.getMetadataDbId());
		assertEquals("Hello", metadata.get("message"));
	}

	@Test
	public void auditIsEnabled() throws Exception {
		linkResourcesAndLoadPage("AuditIsEnabled", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("true", elem.getText());
	}
	
	@Test
	public void FetchesLatestAuditMetadataId() throws Exception {
		linkResourcesAndLoadPage("FetchesLatestAuditMetadataId", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("true", elem.getText());
	}


	@Test
	public void allAuditMetadataIdsAreFetched() throws Exception {
		linkResourcesAndLoadPage("AllAuditMetadataIdsAreFetched", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("3", elem.getText());
	}
	
	@Test
	public void fetchesAuditMetadata() throws Exception {
		linkResourcesAndLoadPage("FetchesAuditMetadata", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("Albert", elem.getText());
	}

	@Test
	public void fetchesDbIdsAffectedByAuditMetadataId() throws Exception {
		linkResourcesAndLoadPage("FetchesDbIdsAffectedByAuditMetadataId", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("/e1:true/e2:true", elem.getText());
	}
	
	@Test
	public void fetchesLatestAuditRecord() throws Exception {
		linkResourcesAndLoadPage("FetchesLatestAuditRecord", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("INSERT", elem.getText());
	}

	@Test
	public void fetchesAllAuditRecords() throws Exception {
		linkResourcesAndLoadPage("FetchesAllAuditRecords", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("UPDATE/INSERT", elem.getText());
	}
	
	@Test
	public void deletesAuditRecord() throws Exception {
		linkResourcesAndLoadPage("DeletesAuditRecord", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("/false/true", elem.getText());
	}

	@Test
	public void deletesAuditMetadata() throws Exception {
		linkResourcesAndLoadPage("DeletesAuditMetadata", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("/INSERT", elem.getText());
	}


	@Test
	public void fetchesAuditRecordsMatching() throws Exception {
		linkResourcesAndLoadPage("FetchesAuditRecordsMatching", Dialect.O);
		Thread.sleep(100);
		WebElement elem = waitElement(By.id("root"), 3);
		assertEquals("/false/true", elem.getText());
	}

}
