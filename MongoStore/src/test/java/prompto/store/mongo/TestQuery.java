package prompto.store.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import prompto.declaration.AttributeDeclaration;
import prompto.error.SyntaxError;
import prompto.expression.FetchOneExpression;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.parser.ECleverParser;
import prompto.parser.EPromptoBuilder;
import prompto.runtime.Context;
import prompto.store.Family;
import prompto.store.IQuery;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.type.AnyType;
import prompto.type.IntegerType;
import prompto.type.ListType;
import prompto.type.TextType;

public class TestQuery extends BaseMongoTest {

	Context context;
	
	@Before
	public void before() throws Exception {
		createStore("TestQuery_" + System.currentTimeMillis());
		context = Context.newGlobalsContext();
		registerDbIdAttribute();
		registerNameAttribute();
		registerAliasesAttribute();
		registerQuantityAttribute();
		registerQuantitiesAttribute();
		createField("name", Family.TEXT, false);
		createField("aliases", Family.TEXT, true);
		createField("quantity", Family.INTEGER, false);
		createField("quantities", Family.INTEGER, true);
		createField("startFrom", Family.DATE, false);
		createField("timeStamp", Family.DATETIME, false);
	}
	
	private void registerDbIdAttribute() throws SyntaxError {
		AttributeDeclaration decl = new AttributeDeclaration( new Identifier(IStore.dbIdName), AnyType.instance());
		context.registerDeclaration(decl);
	}

	private void registerNameAttribute() throws SyntaxError {
		AttributeDeclaration decl = new AttributeDeclaration( new Identifier("name"), TextType.instance());
		decl.setStorable(true);
		context.registerDeclaration(decl);
	}

	
	private void registerAliasesAttribute() throws SyntaxError {
		AttributeDeclaration decl = new AttributeDeclaration( new Identifier("aliases"), new ListType(TextType.instance()));
		decl.setStorable(true);
		context.registerDeclaration(decl);
	}
	
	private void registerQuantityAttribute() throws SyntaxError {
		AttributeDeclaration decl = new AttributeDeclaration( new Identifier("quantity"), IntegerType.instance());
		decl.setStorable(true);
		context.registerDeclaration(decl);
	}

	private void registerQuantitiesAttribute() throws SyntaxError {
		AttributeDeclaration decl = new AttributeDeclaration( new Identifier("quantities"), new ListType(IntegerType.instance()));
		decl.setStorable(true);
		context.registerDeclaration(decl);
	}

	private IStored fetchOne(String query) throws Exception {
		ECleverParser parser = new ECleverParser(query);
		parser.getLexer().setAddLF(false);
		ParseTree tree = parser.fetch_expression();
		EPromptoBuilder builder = new EPromptoBuilder(parser);
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(builder, tree);
		FetchOneExpression fetch = builder.<FetchOneExpression>getNodeValue(tree);
		IQuery q = fetch.buildFetchOneQuery(context, store); 
		return store.fetchOne(q);
	}
	
	@Test
	public void testStore() throws Exception {
		IStorable storable = store.newStorable(new String[0], null);
		storable.setData("name", "John");
		store.store(storable);
		store.flush();
		IStoredIterable many = store.fetchMany(null);
		assertNotNull(many);
		assertEquals(1L, many.count());
	}

	@Test
	public void testDeleteOne() throws Exception {
		Document doc = new Document();
		UUID uuid = UUID.randomUUID();
		doc.put("_id", uuid);
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		store.delete(store.convertToDbId(uuid));
		store.flush();
		String query = "fetch one where name = \"John\"";
		IStored result = fetchOne(query);
		assertNull(result);
	}

	@Test
	public void fetchesTextEquals() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name = \"John\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesTextNotEquals() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name <> \"Johnny\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesTextEqualsWithSpace() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John Smith");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name = \"John Smith\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John Smith", result.getData("name"));
	}

	@Test
	public void fetchesTextRoughly() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name ~ \"joHn\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesTextRoughlyWithParenthesis() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John (Doe)");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name ~ \"joHn (doe)\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John (Doe)", result.getData("name"));
	}

	@Test
	public void fetchesTextContains() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name contains \"oh\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void fetchesTextNotContains() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lucy");
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where name not contains \"oh\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lucy", result.getData("name"));
	}

	@Test
	public void fetchesListHas() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		doc1.put("aliases", Arrays.asList("John", "Janet"));
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lucy");
		doc2.put("aliases", Arrays.asList("Sky", "Diamond"));
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where aliases has \"John\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}


	@Test
	public void fetchesListNotHas() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		doc1.put("aliases", Arrays.asList("John", "Janet"));
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lucy");
		doc2.put("aliases", Arrays.asList("Sky", "Diamond"));
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where aliases not has \"John\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lucy", result.getData("name"));
	}

	@Test
	public void fetchesTextLesser() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where name < \"King\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesTextLesserEqual() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where name <= \"King\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}


	@Test
	public void fetchesTextGreater() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where name > \"King\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lionel", result.getData("name"));
	}
	

	@Test
	public void fetchesTextGreaterEqual() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where name >= \"King\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lionel", result.getData("name"));
	}

	@Test
	public void fetchesContains() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name contains \"oh\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void fetchesNotContains() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name not contains \"ah\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesTextInCollection() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name in [\"John\", \"Jim\"]";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void fetchesTextNotInCollection() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where name not in [\"Lucy\", \"Jim\"]";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesIntegerEquals() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("quantity", 3L);
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity = 3";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}


	@Test
	public void fetchesIntegerLesser() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		doc1.put("quantity", 3L);
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		doc2.put("quantity", 13L);
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity < 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesIntegerLesserEqual() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		doc1.put("quantity", 3L);
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		doc2.put("quantity", 13L);
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity <= 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesIntegerGreater() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		doc1.put("quantity", 3L);
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		doc2.put("quantity", 13L);
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity > 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lionel", result.getData("name"));
	}
	
	@Test
	public void fetchesIntegerGreaterEqual() throws Exception {
		Document doc1 = new Document();
		doc1.put(IStore.dbIdName, UUID.randomUUID());
		doc1.put("name", "John");
		doc1.put("quantity", 3L);
		Document doc2 = new Document();
		doc2.put(IStore.dbIdName, UUID.randomUUID());
		doc2.put("name", "Lionel");
		doc2.put("quantity", 13L);
		store.insertDocuments(doc1, doc2);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity >= 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lionel", result.getData("name"));
	}

	@Test
	public void fetchesIntegerInCollection() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("quantity", 13L);
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity in [10, 13]";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesIntegerNotInCollection() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("quantity", 13L);
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity not in [10, 14]";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void fetchesIntegerListHas() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("quantities", Arrays.asList(10L, 13L));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where quantities has 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void fetchesIntegerListNotHas() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("quantities", Arrays.asList(20L, 13L));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where quantities not has 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void filtersDateLesserGreater() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("startFrom", PromptoDate.parse("2020-10-10"));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where startFrom > '2020-10-09'";
		IStored result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where startFrom > '2020-10-10'";
		result = fetchOne(query);
		assertNull(result);
		query = "fetch one where startFrom >= '2020-10-10'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where startFrom < '2020-10-10'";
		result = fetchOne(query);
		assertNull(result);
		query = "fetch one where startFrom <= '2020-10-10'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where startFrom < '2020-10-11'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void filtersDateTimeLesserGreater() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("timeStamp", PromptoDateTime.parse("2020-10-10T12:00:00"));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where timeStamp > '2020-10-09T00:00:00'";
		IStored result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where timeStamp > '2020-10-10T12:00:00'";
		result = fetchOne(query);
		assertNull(result);
		query = "fetch one where timeStamp >= '2020-10-10T12:00:00'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where timeStamp < '2020-10-10T12:00:00'";
		result = fetchOne(query);
		assertNull(result);
		query = "fetch one where timeStamp <= '2020-10-10T12:00:00'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where timeStamp < '2020-10-11T12:00:00'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void filtersDateTimeLesserGreaterThanDate() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("timeStamp", PromptoDateTime.parse("2020-10-10T00:00:00"));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where timeStamp > '2020-10-09'";
		IStored result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where timeStamp > '2020-10-10'";
		result = fetchOne(query);
		assertNull(result);
		query = "fetch one where timeStamp >= '2020-10-10'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where timeStamp < '2020-10-10'";
		result = fetchOne(query);
		assertNull(result);
		query = "fetch one where timeStamp <= '2020-10-10'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		query = "fetch one where timeStamp < '2020-10-11'";
		result = fetchOne(query);
		assertEquals("John", result.getData("name"));
	}
	
	
	@Test
	public void fetchesOnlyIncludedFields() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("category", Collections.singletonList("Person"));
		doc.put("name", "John");
		doc.put("value", "Other");
		store.insertDocuments(doc);
		store.flush();
		String query = "fetch one Person where name = \"John\" include name";
		IStored result = fetchOne(query);
		assertEquals("John", result.getData("name"));
		assertNull(result.getData("value"));
	}
}
