package prompto.codeserver;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

public abstract class LocalMongo {

	public static void main(String[] args) throws Throwable {
		startMongo();
	}

	static final String REPO = "/Users/ericvergnaud/Development/prompto/mongo";
	private static void startMongo() throws Exception {
		System.out.println("Starting local Mongo @" + REPO);
		Storage storage = new Storage(REPO, null, 16_000_000);
		IMongodConfig mongodConfig = new MongodConfigBuilder()
			.replication(storage)
			.version(Version.Main.PRODUCTION)
			.net(new Net(27017, Network.localhostIsIPv6()))
			.build();	
		MongodStarter starter = MongodStarter.getDefaultInstance();
		MongodExecutable mongo = starter.prepare(mongodConfig);
		MongodProcess process = mongo.start();
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			System.out.println("Stopping local Mongo");
			mongo.stop();
		}));
		process.waitFor();
	}

}
