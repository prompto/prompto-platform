package prompto.store.mongo;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.mongodb.client.MongoCollection;

import prompto.declaration.AttributeDeclaration;
import prompto.declaration.ConcreteCategoryDeclaration;
import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.store.AttributeInfo;
import prompto.store.DataStore;
import prompto.store.Family;
import prompto.store.IStorable;
import prompto.store.IStored;
import prompto.type.AnyType;
import prompto.type.IType;
import prompto.type.TextType;
import prompto.utils.IdentifierList;
import prompto.utils.ManualTests;
import prompto.value.ConcreteInstance;
import prompto.value.IInstance;
import prompto.value.TextValue;

// running this test requires an accessible replicaSet
// for dev purpose, we use a local docker single container replicaSet
// the container must be started before testing using the following cmd line
// docker run -d -p 27001:27001 -p 27002:27002 -p 27003:27003 --name mongo-rs -v ~/mongo-docker-replicaset:/data -e "REPLICA_SET_NAME=mongo-rs" --restart=always flqw/docker-mongo-local-replicaset
// from docker, the connection uri follows: mongodb://mongo-rs:27001,mongo-rs:27002,mongo-rs:27003/db

@Category(ManualTests.class)
public class TestAudit {

	static final String DOCKER_MONGO_RS_URI = "mongodb://localhost:27001,localhost:27002,localhost:27003/TEST?replicaSet=mongo-rs";

	MongoStore store;
	Context context;
	
	@BeforeClass
	public static void beforeClass() {
		if(!MongoStore.ENABLE_AUDIT)
			throw new IllegalStateException("MongoStore.ENABLE_AUDIT is false!");
	}
	
	@Before
	public void before() {
		store = new MongoStore(DOCKER_MONGO_RS_URI, null, null);
		createField("category", Family.TEXT, true);
		DataStore.setInstance(store);
		store.db.getCollection("instances").drop();
		store.db.getCollection("transactions").drop();
		store.db.getCollection("audits").drop();
		context = Context.newGlobalsContext();
		AttributeDeclaration a = new AttributeDeclaration(new Identifier("dbId"), AnyType.instance());
		context.registerDeclaration(a);
	}
	
	@After
	public void after() {
		store.close();
	}
	
	@Test
	public void audits3Inserts() throws InterruptedException {
		createAttribute("name", TextType.instance());
		ConcreteCategoryDeclaration cat = createCategory("Test", "name");
		List<IStorable> instances = IntStream.of(1, 2, 3)
				.mapToObj(i -> {
					IInstance instance = new ConcreteInstance(context, cat);
					instance.setMutable(true);
					instance.setMember(context, new Identifier("name"), new TextValue("hello " + i));
					return instance.getStorable();
				})
				.collect(Collectors.toList());
		store.store(instances);
		Thread.sleep(3000L);
		MongoCollection<Document> coll = store.db.getCollection("transactions");
		assertEquals(1L, coll.estimatedDocumentCount());
		coll = store.db.getCollection("audits");
		assertEquals(3L, coll.estimatedDocumentCount());
	}
	
	@Test
	public void audits1Update() throws InterruptedException {
		createAttribute("name", TextType.instance());
		ConcreteCategoryDeclaration cat = createCategory("Test", "name");
		IInstance instance = new ConcreteInstance(context, cat);
		instance.setMutable(true);
		instance.setMember(context, new Identifier("name"), new TextValue("hello"));
		store.store(instance.getStorable());
		Thread.sleep(3000L);
		IStored stored = store.fetchUnique(instance.getStorable().getOrCreateDbId());
		instance = cat.newInstance(context, stored);
		instance.setMutable(true);
		instance.setMember(context, new Identifier("name"), new TextValue("bye"));
		store.store(instance.getStorable());
		Thread.sleep(3000L);
		MongoCollection<Document> coll = store.db.getCollection("transactions");
		assertEquals(2L, coll.estimatedDocumentCount());
		coll = store.db.getCollection("audits");
		assertEquals(2L, coll.estimatedDocumentCount());
	}

	@Test
	public void audits1Delete() throws InterruptedException {
		createAttribute("name", TextType.instance());
		ConcreteCategoryDeclaration cat = createCategory("Test", "name");
		IInstance instance = new ConcreteInstance(context, cat);
		instance.setMutable(true);
		instance.setMember(context, new Identifier("name"), new TextValue("hello"));
		store.store(instance.getStorable());
		Thread.sleep(3000L);
		store.delete(instance.getStorable().getOrCreateDbId());
		Thread.sleep(3000L);
		MongoCollection<Document> coll = store.db.getCollection("transactions");
		assertEquals(2L, coll.estimatedDocumentCount());
		coll = store.db.getCollection("audits");
		assertEquals(2L, coll.estimatedDocumentCount());
	}

	private ConcreteCategoryDeclaration createCategory(String name, String ... attrs) {
		IdentifierList list = IdentifierList.parse(String.join(",", attrs));
		ConcreteCategoryDeclaration decl = new ConcreteCategoryDeclaration(new Identifier(name), list, null, null);
		decl.setStorable(true);
		context.registerDeclaration(decl);
		return decl;
	}

	private AttributeDeclaration createAttribute(String name, IType type) {
		AttributeDeclaration decl = new AttributeDeclaration(new Identifier(name), type);
		decl.setStorable(true);
		context.registerDeclaration(decl);
		return decl;
	}

	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}


}
