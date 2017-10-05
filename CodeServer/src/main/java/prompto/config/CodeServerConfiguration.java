package prompto.config;

import java.util.Map;

public class CodeServerConfiguration extends ServerConfiguration implements ICodeServerConfiguration {

	public CodeServerConfiguration(IConfigurationReader reader, Map<String, String> argsMap) {
		super(reader, argsMap);
	}

	@Override
	public String getTargetDbName() {
		return reader.getStringOrDefault("targetDBName", "DATA");
	}

	
}
