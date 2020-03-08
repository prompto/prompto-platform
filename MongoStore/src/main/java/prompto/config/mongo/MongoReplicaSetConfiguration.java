package prompto.config.mongo;

import java.util.Collection;
import java.util.Iterator;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;
import com.esotericsoftware.yamlbeans.document.YamlSequence;

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
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = new YamlMapping();
		yaml.setEntry("name", getName());
		yaml.setEntry("ssl", isSSL());
		Iterable<IHostConfiguration> nodes = getNodes();
		YamlSequence sequence = new YamlSequence();
		for(IHostConfiguration host : nodes) {
			sequence.addElement(host.toYaml());
		}
		yaml.setEntry("nodes", sequence);
		return yaml;
	}

}
