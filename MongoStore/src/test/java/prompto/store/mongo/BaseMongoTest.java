package prompto.store.mongo;

import java.io.IOException;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoDatabase;

import prompto.config.TempDirectories;
import prompto.runtime.Mode;
import prompto.store.AttributeInfo;
import prompto.store.DataStore;
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
	
	static protected MongodExecutable mongo = null;
	static protected int mongoPort = 0;
	static protected Timer mongoStopper = null;
	protected MongoStore store;
	protected MongoDatabase db;
	
	
	@BeforeClass
	public static synchronized void __before_class__() throws IOException {
		if(mongoPort==0)
			mongoPort = Network.getFreeServerPort();
		if(mongo==null)
			mongo = startMongo(mongoPort);
	}
	

	@AfterClass
	public static synchronized void __after_class__() throws IOException {
		
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

	public static void stopMongo(MongodExecutable mongo) {
		if (mongo != null) {
			System.out.println("Stopping shared Mongo instance");
			mongo.stop();
		}
	}

	@Before
	public synchronized void __before__() throws IOException {
		if(mongoStopper!=null) {
			mongoStopper.cancel();
			mongoStopper = null;
		}
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
	}
	
	@After
	public synchronized void __after__() throws IOException {
		if(mongoStopper==null) {
			mongoStopper = new Timer("Stop Mongo");
			mongoStopper.schedule(new TimerTask() {

				@Override
				public void run() {
					stopMongo(mongo);
					mongo = null;
					mongoPort = 0;
					mongoStopper.cancel();
					mongoStopper = null;
				}

			}, 5000);
		}
	}
	
	protected MongoStore createStore(String name) {
		System.out.println("Creating new store from shared Mongo instance");
		store = new MongoStore("localhost", mongoPort, name, false);
		DataStore.setGlobal(store);
		DataStore.useGlobal();
		db = store.db;
		return store;
	}


	protected void createField(String name, Family family, boolean collection) {
		AttributeInfo info = new AttributeInfo(name, family, collection, false, false, false, false);
		store.createOrUpdateAttributes(Collections.singletonList(info));
	}

	
}
