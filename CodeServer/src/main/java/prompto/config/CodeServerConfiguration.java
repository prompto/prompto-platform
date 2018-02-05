package prompto.config;

import java.util.Map;
import java.util.function.Supplier;

public class CodeServerConfiguration extends ServerConfiguration implements ICodeServerConfiguration {

	Supplier<IStoreConfiguration> targetStoreConfiguration;

	public CodeServerConfiguration(IConfigurationReader reader, Map<String, String> argsMap) {
		super(reader, argsMap);
		this.targetStoreConfiguration = ()->reader.readStoreConfiguration("targetStore");
	}
	
	@Override
	public IStoreConfiguration getTargetStoreConfiguration() {
		return targetStoreConfiguration==null ? null : targetStoreConfiguration.get();
	}

}
