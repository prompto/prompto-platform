package prompto.config.mongo;

import prompto.config.IHostConfiguration;

public interface IMongoReplicaSetConfiguration {

	Iterable<IHostConfiguration> getNodes();
	boolean isSSL();
	String getName();

}
