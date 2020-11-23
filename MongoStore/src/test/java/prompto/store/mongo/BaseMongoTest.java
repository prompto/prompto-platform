package prompto.store.mongo;

import java.io.IOException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoDatabase;

import prompto.config.TempDirectories;
import prompto.runtime.Mode;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.runtime.Network;

public abstract class BaseMongoTest {
	
	protected int mongoPort;
	MongodExecutable mongo;
	protected MongoStore store;
	protected MongoDatabase db;
	
	@Before
	public void __before__() throws IOException {
		if(mongoPort==0)
			mongoPort = Network.getFreeServerPort();
		mongo = startMongo(mongoPort);
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
	}
	
	public static MongodExecutable startMongo(int mongoPort) throws IOException {
		IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
			.defaultsWithLogger(Command.MongoD, LoggerFactory.getLogger(BaseMongoTest.class.getName()))
			.processOutput(ProcessOutput.getDefaultInstanceSilent())
			.build();
		MongodStarter starter = MongodStarter.getInstance(runtimeConfig);
		IMongodConfig mongodConfig = new MongodConfigBuilder()
			.version(Version.Main.PRODUCTION)
			.net(new Net(mongoPort, Network.localhostIsIPv6()))
			.build();	
		MongodExecutable mongo = starter.prepare(mongodConfig);
		mongo.start();
		return mongo;
	}

	@After
	public void __after__() throws IOException {
		if (mongo != null)
			stopMongo(mongo);
	}
	
	public static void stopMongo(MongodExecutable mongo) {
		mongo.stop();
	}

	protected MongoStore createStore(String name) {
		store = new MongoStore("localhost", mongoPort, name);
		db = store.db;
		return store;
	}


	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}

	
}
