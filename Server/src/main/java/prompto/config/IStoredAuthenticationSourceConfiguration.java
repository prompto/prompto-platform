package prompto.config;

public interface IStoredAuthenticationSourceConfiguration extends IAuthenticationSourceConfiguration {

	IStoreConfiguration getStoreConfiguration();
	
}
