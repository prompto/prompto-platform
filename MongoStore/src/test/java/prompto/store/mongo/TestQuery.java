package prompto.store.mongo;

import static org.junit.Assert.*;

import java.util.Arrays;
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
		createStore("TestQuery");
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
		store.delete(uuid);
		store.flush();
		String query = "fetch one where name = \"John\"";
		IStored result = fetchOne(query);
		assertNull(result);
	}

	@Test
	public void testFetchTextEquals() throws Exception {
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
	public void testFetchTextEqualsWithSpace() throws Exception {
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
	public void testFetchTextRoughly() throws Exception {
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
	public void testFetchTextContains() throws Exception {
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
	public void testFetchListHas() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("aliases", Arrays.asList("John", "Janet"));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where aliases has \"John\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}


	@Test
	public void testFetchTextLesser() throws Exception {
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
	public void testFetchTextGreater() throws Exception {
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
	public void testFetchTextInText() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where \"oh\" in name";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void testFetchTextInCollection() throws Exception {
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
	public void testFetchTextInTextCollection() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("aliases", Arrays.asList("Johnny", "Jim"));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where aliases contains \"Jim\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void testFetchNonTextEquals() throws Exception {
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
	public void testFetchNonTextLesser() throws Exception {
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
	public void testFetchNonTextGreater() throws Exception {
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
	public void testFetchNonTextInCollection() throws Exception {
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
	public void testFetchNonTextInNonTextCollection() throws Exception {
		Document doc = new Document();
		doc.put(IStore.dbIdName, UUID.randomUUID());
		doc.put("name", "John");
		doc.put("quantities", Arrays.asList(10L, 13L));
		store.insertDocuments(doc);
		store.flush();
		// Test the basics
		String query = "fetch one where quantities contains 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
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
	
	
}
