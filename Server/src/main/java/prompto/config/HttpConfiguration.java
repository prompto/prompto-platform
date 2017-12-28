package prompto.config;

import prompto.security.BasicLoginMethodFactory;

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
		this.loginModuleConfiguration = ()->readLoginModuleConfiguration();
		this.loginMethodConfiguration = ()->readLoginMethodConfiguration();
	}
	
	private IKeyStoreConfiguration readKeyStoreConfiguration() {
		IConfigurationReader child = reader.getObject("keyStore");
		return new KeyStoreConfiguration(child);
	}
	
	private IKeyStoreConfiguration readTrustStoreConfiguration() {
		IConfigurationReader child = reader.getObject("trustStore");
		return new KeyStoreConfiguration(child);
	}
	
	private ILoginModuleConfiguration readLoginModuleConfiguration() {
		IConfigurationReader child = reader.getObject("loginModule");
		return child==null ? null : new LoginModuleConfiguration(child);
	}
	
	private ILoginMethodConfiguration readLoginMethodConfiguration() {
		IConfigurationReader child = reader.getObject("loginMethod");
		// default to BASIC authentication method
		if(child==null)
			return ()->new BasicLoginMethodFactory();
		else
			return new LoginMethodConfiguration(child);
	}
}
