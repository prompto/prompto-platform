package prompto.config;

public interface IHttpConfiguration {

	String getProtocol();
	int getPort();
	Integer getRedirectFrom();
	String getAllowedOrigin();
	IKeyStoreConfiguration getKeyStoreConfiguration();
	IKeyStoreConfiguration getTrustStoreConfiguration();
	ILoginConfiguration getLoginConfiguration();

}
