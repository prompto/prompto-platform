package prompto.config.mongo;

import java.util.Collection;
import java.util.Iterator;

import prompto.config.HostConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IHostConfiguration;

public class MongoReplicaSetConfiguration implements IMongoReplicaSetConfiguration {

	IConfigurationReader reader;
	
	public MongoReplicaSetConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}
	
	@Override
	public String getName() {
		return reader.getString("name");
	}
	
	@Override
	public boolean isSSL() {
		return reader.getBooleanOrDefault("ssl", true);
	}
	
	@Override
	public Iterable<IHostConfiguration> getNodes() {
		Collection<? extends IConfigurationReader> nodes = reader.getObjectsArray("nodes");
		if(nodes==null || nodes.isEmpty())
			return null;
		return new Iterable<IHostConfiguration>() {

			@Override
			public Iterator<IHostConfiguration> iterator() {
				Iterator<? extends IConfigurationReader> readers = nodes.iterator();
				return new Iterator<IHostConfiguration>() {
					@Override public boolean hasNext() { return readers.hasNext(); }
					@Override public IHostConfiguration next() { return new HostConfiguration(readers.next()); }
				};
			}
			
		};
	}

}
