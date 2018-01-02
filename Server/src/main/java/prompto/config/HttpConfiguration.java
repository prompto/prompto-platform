package prompto.config;


public class HttpConfiguration extends IHttpConfiguration.Inline {

	IConfigurationReader reader;
	
	public HttpConfiguration(IConfigurationReader reader) {
		this.reader = reader;
		this.protocol = ()->reader.getStringOrDefault("protocol", "http");
		this.port = ()->reader.getIntegerOrDefault("port", -1);
		this.redirectFrom = ()->reader.getInteger("redirectFrom");
		this.allowedOrigins = ()->reader.getString("allowedOrigins");
		this.allowsXAuthorization = ()->reader.getBooleanOrDefault("allowsXAuthorization", false);
		this.sendsXAuthorization = ()->reader.getBooleanOrDefault("sendsXAuthorization", false);
		this.keyStoreConfiguration = ()->readKeyStoreConfiguration();
		this.trustStoreConfiguration = ()->readTrustStoreConfiguration();
		this.loginConfiguration = ()->readLoginConfiguration();
	}
	
	private IKeyStoreConfiguration readKeyStoreConfiguration() {
		IConfigurationReader child = reader.getObject("keyStore");
		return new KeyStoreConfiguration(child);
	}
	
	private IKeyStoreConfiguration readTrustStoreConfiguration() {
		IConfigurationReader child = reader.getObject("trustStore");
		return new KeyStoreConfiguration(child);
	}
	
	private ILoginConfiguration readLoginConfiguration() {
		IConfigurationReader child = reader.getObject("login");
		return child==null ? null : new LoginConfiguration(child);
	}

}
