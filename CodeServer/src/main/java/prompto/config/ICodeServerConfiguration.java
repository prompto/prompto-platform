package prompto.config;

public interface ICodeServerConfiguration extends IServerConfiguration {

	IStoreConfiguration getTargetDataStoreConfiguration();

}
