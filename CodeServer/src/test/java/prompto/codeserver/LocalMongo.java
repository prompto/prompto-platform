package prompto.codeserver;

import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	static final String REPO = "/Users/ericvergnaud/Development/prompto/mongo";

	public static void main(String[] args) throws Throwable {
		startMongo(REPO, 27017, false);
	}

	
	public static void startMongoForUnitTests() throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Exception> future = executor.submit(() -> {
			final String repo = Files.createTempDirectory("mongo_test_").toAbsolutePath().toString();
			return startMongo(repo, 27018, true);
		});
		Exception exception = future.get();
		if(exception!=null)
			throw exception;
	}
	
	public static Exception startMongo(String repo, int port, boolean asyncWaitFor) throws Exception {
		try {
			System.out.println("Starting local Mongo @" + repo);
			Storage storage = new Storage(repo, null, 16_000_000);
			IMongodConfig mongodConfig = new MongodConfigBuilder()
				.replication(storage)
				.version(Version.Main.PRODUCTION)
				.net(new Net(port, Network.localhostIsIPv6()))
				.build();	
			MongodStarter starter = MongodStarter.getDefaultInstance();
			MongodExecutable mongo = starter.prepare(mongodConfig);
			MongodProcess process = mongo.start();
			Runtime.getRuntime().addShutdownHook(new Thread(()->{
				System.out.println("Stopping local Mongo");
				mongo.stop();
			}));
			if(asyncWaitFor)
				new Thread(()->{
					try {
						process.waitFor();
					} catch(InterruptedException e) {
						e.printStackTrace(System.err);
					}
				}).start();
			else
				process.waitFor();
			return null;
		} catch(Exception e) {
			return e;
		}
		
	}

}
