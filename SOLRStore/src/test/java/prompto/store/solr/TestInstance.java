package prompto.store.solr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import prompto.declaration.AttributeDeclaration;
import prompto.declaration.ConcreteCategoryDeclaration;
import prompto.expression.EqualsExpression;
import prompto.expression.ValueExpression;
import prompto.expression.FetchOneExpression;
import prompto.expression.IExpression;
import prompto.expression.UnresolvedIdentifier;
import prompto.grammar.EqOp;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.literal.BooleanLiteral;
import prompto.literal.DateLiteral;
import prompto.literal.DateTimeLiteral;
import prompto.literal.DecimalLiteral;
import prompto.literal.IntegerLiteral;
import prompto.literal.TextLiteral;
import prompto.literal.TimeLiteral;
import prompto.runtime.Context;
import prompto.store.Family;
import prompto.store.DataStore;
import prompto.store.IQuery;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStored;
import prompto.store.IStoredIterable;
import prompto.type.AnyType;
import prompto.type.BooleanType;
import prompto.type.CategoryType;
import prompto.type.DateTimeType;
import prompto.type.DateType;
import prompto.type.IType;
import prompto.type.IntegerType;
import prompto.type.TextType;
import prompto.type.TimeType;
import prompto.type.UuidType;
import prompto.utils.IdentifierList;
import prompto.value.BooleanValue;
import prompto.value.ConcreteInstance;
import prompto.value.DateValue;
import prompto.value.DecimalValue;
import prompto.value.IInstance;
import prompto.value.IValue;
import prompto.value.IntegerValue;
import prompto.value.TextValue;
import prompto.value.TimeValue;

public class TestInstance extends BaseSOLRTest {

	Context context;
	
	@Before
	public void before() throws Exception {
		createStore("TestInstance");
		store.startServerWithEmptyCore();
		createField("category", Family.TEXT, true);
		DataStore.setInstance(store);
		context = Context.newGlobalsContext();
	}
	
	@Test
	public void testStoreTextField() throws Exception {
		String fieldName = "msg";
		String fieldValue = "hello";
		createField(fieldName, Family.TEXT, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, TextType.instance());
		instance.setMember(context, new Identifier(fieldName), new TextValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new TextLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	@Test
	public void testStoreIntegerField() throws Exception {
		String fieldName = "int";
		long fieldValue = 123;
		createField(fieldName, Family.INTEGER, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, IntegerType.instance());
		instance.setMember(context, new Identifier(fieldName), new IntegerValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new IntegerLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	@Test
	public void testStoreDecimalField() throws Exception {
		String fieldName = "decimal";
		double fieldValue = 123.0;
		createField(fieldName, Family.DECIMAL, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, IntegerType.instance());
		instance.setMember(context, new Identifier(fieldName), new DecimalValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new DecimalLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	@Test
	public void testStoreBooleanField() throws Exception {
		String fieldName = "bool";
		boolean fieldValue = true;
		createField(fieldName, Family.BOOLEAN, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, BooleanType.instance());
		instance.setMember(context, new Identifier(fieldName), BooleanValue.valueOf(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new BooleanLiteral("true"));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	@Test
	public void testStoreDateField() throws Exception {
		String fieldName = "date";
		PromptoDate fieldValue = PromptoDate.parse("2015-03-12");
		createField(fieldName, Family.DATE, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, DateType.instance());
		instance.setMember(context, new Identifier(fieldName), new DateValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new DateLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName)); // value will be converted by reader
	}

	@Test
	public void testStoreTimeField() throws Exception {
		String fieldName = "time";
		PromptoTime fieldValue = PromptoTime.parse("13:15:16.012");
		createField(fieldName, Family.TIME, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, TimeType.instance());
		instance.setMember(context, new Identifier(fieldName), new TimeValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new TimeLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	@Test
	public void testStoreDateTimeField() throws Exception {
		String fieldName = "datetime";
		PromptoDateTime fieldValue = PromptoDateTime.parse("2015-03-12T13:15:16.012Z");
		createField(fieldName, Family.DATETIME, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, DateTimeType.instance());
		instance.setMember(context, new Identifier(fieldName), new prompto.value.DateTimeValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new DateTimeLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	@Test
	public void testStoreUUIDField() throws Exception {
		String fieldName = "uuid";
		UUID fieldValue = UUID.randomUUID();
		createField(fieldName, Family.UUID, false);
		IInstance instance = createInstanceWith1Attribute(fieldName, UuidType.instance());
		instance.setMember(context, new Identifier(fieldName), new prompto.value.UuidValue(fieldValue));
		store.store(instance.getStorable());
		store.flush();
		IStored stored = fetchOne(fieldName, new ValueExpression(UuidType.instance(), new prompto.value.UuidValue(fieldValue)));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
	}

	private static boolean isDump() {
		return false;
	}
	
	private static void dumpDbIds(String which, IInstance instance) {
		if(isDump()) {
			IValue value = instance.getMember(null, new Identifier("dbId"), false);
			Object dbId = value==null ? null : value.getStorableData();
			System.err.print(which + ": ivalue: " + String.valueOf(dbId));
			IStorable storable = instance.getStorable();
			SolrInputDocument document = storable==null ? null : ((StorableDocument)storable).getDocument();
			dbId = document == null ? null : document.getFieldValue("dbId");
			System.err.println(", dbvalue: " + String.valueOf(dbId));
		}
	}
	
	@Test
	public void testStoreChildField() throws Exception {
		CategoryType type = new CategoryType(new Identifier("Test"));
		String fieldName = "textField";
		String fieldValue = "textValue";
		String childValue = "childValue";
		String childName = "childField";
		createField(fieldName, Family.TEXT, false);
		createField(childName, Family.CATEGORY, false);
		IInstance parent = createInstanceWith2Attributes(fieldName, TextType.instance(), childName, type);
		dumpDbIds("parent", parent);
		ConcreteCategoryDeclaration cd = context.getRegisteredDeclaration(
				ConcreteCategoryDeclaration.class, new Identifier("Test"), false);
		ConcreteInstance child = new ConcreteInstance(context, cd);
		dumpDbIds("child", child);
		child.setMutable(true);
		child.setMember(context, new Identifier(fieldName), new TextValue(childValue));
		dumpDbIds("child", child);
		parent.setMember(context, new Identifier(fieldName), new TextValue(fieldValue));
		dumpDbIds("parent", parent);
		parent.setMember(context, new Identifier(childName), child);
		dumpDbIds("parent", parent);
		dumpDbIds("child", child);
		Map<Object, IStorable> storables = new HashMap<>();
		parent.collectStorables(s->storables.put(s.getOrCreateDbId(), s));
		dumpDbIds("parent", parent);
		dumpDbIds("child", child);
		store.store(null, storables.values());
		store.flush();
		IStored stored = fetchOne(fieldName, new TextLiteral(fieldValue));
		assertNotNull(stored);
		assertEquals(fieldValue, stored.getData(fieldName));
		parent = (ConcreteInstance)type.newInstance(context, stored);
		IValue v = parent.getMember(context, new Identifier(childName), false);
		assertNotNull(v);
		assertTrue(v instanceof IInstance);
		assertEquals(new TextValue(childValue), v.getMember(context, new Identifier(fieldName), false));
	}

	private IStored fetchOne(String field, IExpression value) throws Exception {
		FetchOneExpression expression = new FetchOneExpression(
				new CategoryType(new Identifier("Test")), 
				new EqualsExpression(new UnresolvedIdentifier(new Identifier(field)), EqOp.EQUALS, value));
		IQuery query = expression.buildFetchOneQuery(context, store);
		return store.fetchOne(query);
	}
	
	@SuppressWarnings("unused")
	private IStoredIterable fetchAll() throws Exception {
		IQuery query = store.newQueryBuilder().build();
		return store.fetchMany(query);
	}

	private IInstance createInstanceWith1Attribute(String name, IType type) throws Exception {
		AttributeDeclaration a = new AttributeDeclaration(new Identifier(name), type);
		a.setStorable(true);
		context.registerDeclaration(a);
		IdentifierList as = new IdentifierList(new Identifier(name));
		ConcreteCategoryDeclaration d = new ConcreteCategoryDeclaration(new Identifier("Test"), as, null, null);
		d.setStorable(true);
		context.registerDeclaration(d);
		ConcreteInstance i = new ConcreteInstance(context, d);
		i.setMutable(true);
		return i;
	}

	private IInstance createInstanceWith2Attributes(String name1, IType type1, String name2, IType type2) throws Exception {
		AttributeDeclaration a = new AttributeDeclaration(new Identifier(IStore.dbIdName), AnyType.instance());
		context.registerDeclaration(a);
		a = new AttributeDeclaration(new Identifier(name1), type1);
		a.setStorable(true);
		context.registerDeclaration(a);
		a = new AttributeDeclaration(new Identifier(name2), type2);
		a.setStorable(true);
		context.registerDeclaration(a);
		IdentifierList as = new IdentifierList(new Identifier(name1));
		as.add(new Identifier(name2));
		ConcreteCategoryDeclaration d = new ConcreteCategoryDeclaration(new Identifier("Test"), as, null, null);
		d.setStorable(true);
		context.registerDeclaration(d);
		ConcreteInstance i = new ConcreteInstance(context, d);
		i.setMutable(true);
		return i;
	}

}