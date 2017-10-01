package prompto.config;

public class HttpConfiguration implements IHttpConfiguration {

	IConfigurationReader reader;
	
	public HttpConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public String getProtocol() {
		return reader.getStringOrDefault("protocol", "http");
	}
	
	@Override
	public int getPort() {
		return reader.getIntegerOrDefault("port", -1);
	}
	
	@Override
	public Integer getRedirectFrom() {
		return reader.getInteger("redirectFrom");
	}

	@Override
	public String getAllowedOrigin() {
		return reader.getString("allowedOrigin");
	}

	@Override
	public IKeyStoreConfiguration getKeyStoreConfiguration() {
		IConfigurationReader child = reader.getObject("keyStore");
		return new KeyStoreConfiguration(child);
	}
	
	@Override
	public IKeyStoreConfiguration getTrustStoreConfiguration() {
		IConfigurationReader child = reader.getObject("trustStore");
		return new KeyStoreConfiguration(child);
	}
	
	@Override
	public ILoginConfiguration getLoginConfiguration() {
		IConfigurationReader child = reader.getObject("login");
		return child==null ? null : new LoginConfiguration(child);
	}
}
