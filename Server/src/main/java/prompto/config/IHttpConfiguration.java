package prompto.config;

public interface IHttpConfiguration {

	String getProtocol();
	int getPort();
	String getAllowedOrigin();
	IKeyStoreConfiguration getKeyStoreConfiguration();
	IKeyStoreConfiguration getTrustStoreConfiguration();
	ILoginConfiguration getLoginConfiguration();

}
