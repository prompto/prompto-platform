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
	
	int mongoPort;
	MongodExecutable mongo;
	MongoStore store;
	
	@Before
	public void __before__() throws IOException {
		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongoPort = Network.getFreeServerPort();
		IMongodConfig mongodConfig = new MongodConfigBuilder()
			.version(Version.Main.PRODUCTION)
			.net(new Net(mongoPort, Network.localhostIsIPv6()))
			.build();	
		mongo = starter.prepare(mongodConfig);
		mongo.start();
	}
	
	@After
	public void __after__() throws IOException {
		if (mongo != null)
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
