package prompto.store.mongo;

import java.io.IOException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;

import prompto.store.AttributeInfo;
import prompto.store.Family;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public abstract class BaseMongoTest {
	
	protected int mongoPort;
	MongodExecutable mongo;
	protected MongoStore store;
	
	@Before
	public void __before__() throws IOException {
		if(mongoPort==0)
			mongoPort = Network.getFreeServerPort();
		mongo = startMongo(mongoPort);
	}
	
	public static MongodExecutable startMongo(int mongoPort) throws IOException {
		MongodStarter starter = MongodStarter.getDefaultInstance();
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

	protected void createStore(String name) {
		store = new MongoStore("localhost", mongoPort, name);
	}


	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false);
		store.createOrUpdateColumns(Collections.singletonList(info));
	}

	
}
