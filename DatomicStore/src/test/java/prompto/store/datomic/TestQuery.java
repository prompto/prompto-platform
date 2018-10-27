package prompto.store.datomic;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;

import datomic.Peer;
import prompto.declaration.AttributeDeclaration;
import prompto.error.SyntaxError;
import prompto.expression.FetchOneExpression;
import prompto.grammar.Identifier;
import prompto.parser.ECleverParser;
import prompto.parser.EPromptoBuilder;
import prompto.runtime.Context;
import prompto.store.Family;
import prompto.store.IDataStore;
import prompto.store.IQuery;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.store.datomic.Constants.DbPart;
import prompto.type.AnyType;
import prompto.type.IntegerType;
import prompto.type.ListType;
import prompto.type.TextType;

public class TestQuery extends BaseDatomicTest {

	Context context;
	
	@Before
	public void before() throws Exception {
		IDataStore.setInstance(store);
		context = Context.newGlobalContext();
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
		ParseTree tree = parser.fetch_store_expression();
		EPromptoBuilder builder = new EPromptoBuilder(parser);
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(builder, tree);
		FetchOneExpression fetch = builder.<FetchOneExpression>getNodeValue(tree);
		IQuery q = fetch.buildFetchOneQuery(context, store); 
		return store.fetchOne(q);
	}
	
	@Test
	public void testStore() throws Exception {
		IStorable storable = store.newStorable(new String[] { "SimpleStuff" }, null);
		storable.setData("name", "John");
		store.store(storable);
		store.flush();
		IStoredIterable many = store.fetchMany(null);
		assertNotNull(many);
		assertEquals(1L, many.count());
	}

	@Test
	public void testDeleteOne() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		store.delete(facts.getDbId());
		store.flush();
		String query = "fetch one where name = \"John\"";
		IStored result = fetchOne(query);
		assertNull(result);
	}

	@Test
	public void testFetchTextEquals() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where name = \"John\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void testFetchTextEqualsWithSpace() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John Smith");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where name = \"John Smith\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John Smith", result.getData("name"));
	}

	@Test
	public void testFetchTextRoughly() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where name ~ \"joHn\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void testFetchTextContains() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where name contains \"oh\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void testFetchListHas() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		facts.add("aliases", Arrays.asList("John", "Janet"));
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where aliases has \"John\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}


	@Test
	public void testFetchTextLesser() throws Exception {
		DatomicFacts facts1 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts1.add("name", "John");
		DatomicFacts facts2 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts2.add("name", "Lionel");
		store.storeFacts(facts1, facts2);
		store.flush();
		// Test the basics
		String query = "fetch one where name < \"King\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	
	@Test
	public void testFetchTextGreater() throws Exception {
		DatomicFacts facts1 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts1.add("name", "John");
		DatomicFacts facts2 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts2.add("name", "Lionel");
		store.storeFacts(facts1, facts2);
		store.flush();
		// Test the basics
		String query = "fetch one where name > \"King\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lionel", result.getData("name"));
	}
	

	@Test
	public void testFetchTextInText() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where \"oh\" in name";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void testFetchTextInCollection() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where name in [\"John\", \"Jim\"]";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	@Test
	public void testFetchTextInTextCollection() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		facts.add("aliases", Arrays.asList("Johnny", "Jim"));
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where aliases contains \"Jim\"";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void testFetchNonTextEquals() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		facts.add("quantity", 3L);
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity = 3";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}


	@Test
	public void testFetchNonTextLesser() throws Exception {
		DatomicFacts facts1 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts1.add("name", "John");
		facts1.add("quantity", 3L);
		DatomicFacts facts2 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts2.add("name", "Lionel");
		facts2.add("quantity", 13L);
		store.storeFacts(facts1, facts2);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity < 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void testFetchNonTextGreater() throws Exception {
		DatomicFacts facts1 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts1.add("name", "John");
		facts1.add("quantity", 3L);
		DatomicFacts facts2 = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts2.add("name", "Lionel");
		facts2.add("quantity", 13L);
		store.storeFacts(facts1, facts2);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity > 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("Lionel", result.getData("name"));
	}
	
	@Test
	public void testFetchNonTextInCollection() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		facts.add("quantity", 13L);
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where quantity in [10, 13]";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}

	@Test
	public void testFetchNonTextInNonTextCollection() throws Exception {
		DatomicFacts facts = new DatomicFacts(Peer.tempid(DbPart.USER.dbName()));
		facts.add("name", "John");
		facts.add("quantities", Arrays.asList(10L, 13L));
		store.storeFacts(facts);
		store.flush();
		// Test the basics
		String query = "fetch one where quantities contains 10";
		IStored result = fetchOne(query);
		assertNotNull(result);
		assertEquals("John", result.getData("name"));
	}
	
	
}
