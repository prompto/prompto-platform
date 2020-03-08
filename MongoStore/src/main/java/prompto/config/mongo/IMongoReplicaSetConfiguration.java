package prompto.config.mongo;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.IHostConfiguration;

public interface IMongoReplicaSetConfiguration {

	Iterable<IHostConfiguration> getNodes();
	boolean isSSL();
	String getName();
	YamlMapping toYaml() throws YamlException;

}
