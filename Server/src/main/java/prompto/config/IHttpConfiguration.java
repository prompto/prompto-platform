package prompto.config;

import java.util.function.Supplier;

import prompto.config.auth.IAuthenticationConfiguration;

public interface IHttpConfiguration {

	String getProtocol();
	int getPort();
	String getWelcomePage();
	Integer getRedirectFrom();
	String getAllowedOrigins();
	IKeyStoreConfiguration getKeyStoreConfiguration();
	IKeyStoreConfiguration getTrustStoreConfiguration();
	IAuthenticationConfiguration getAuthenticationConfiguration();
	boolean getAllowsXAuthorization();
	boolean getSendsXAuthorization();
	
	IHttpConfiguration withProtocol(String protocol);
	IHttpConfiguration withPort(int port);
	IHttpConfiguration withWelcomePage(String welcomePage);
	IHttpConfiguration withSendsXAuthorization(boolean set);
	IHttpConfiguration withKeyStoreConfiguration(IKeyStoreConfiguration config);
	IHttpConfiguration withTrustStoreConfiguration(IKeyStoreConfiguration config);
	IHttpConfiguration withAuthenticationConfiguration(IAuthenticationConfiguration config);
	
	
	public static class Inline implements IHttpConfiguration {

		Supplier<String> protocol = ()->null;
		Supplier<Integer> port = ()->0;
		Supplier<String> welcomePage = ()->null;
		Supplier<Integer> redirectFrom = ()->null;
		Supplier<String> allowedOrigins = ()->null;
		Supplier<IKeyStoreConfiguration> keyStoreConfiguration = ()->null;
		Supplier<IKeyStoreConfiguration> trustStoreConfiguration = ()->null;
		Supplier<IAuthenticationConfiguration> authenticationConfiguration = ()->null;
		Supplier<Boolean> allowsXAuthorization = ()->false;
		Supplier<Boolean> sendsXAuthorization = ()->false;
		
		@Override public String getProtocol() { return protocol.get(); }
		@Override public int getPort() { return port.get(); }
		@Override public String getWelcomePage() { return welcomePage.get(); }
		@Override public Integer getRedirectFrom() { return redirectFrom.get(); }
		@Override public String getAllowedOrigins() { return allowedOrigins.get(); }
		@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { return keyStoreConfiguration.get(); }
		@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { return trustStoreConfiguration.get(); }
		@Override public IAuthenticationConfiguration getAuthenticationConfiguration() { return authenticationConfiguration.get(); }
		@Override public boolean getAllowsXAuthorization() { return allowsXAuthorization.get(); }
		@Override public boolean getSendsXAuthorization() { return sendsXAuthorization.get(); }
		
		@Override
		public IHttpConfiguration withProtocol(String proto) {
			protocol = ()->proto;
			return this;
		}
		
		@Override
		public IHttpConfiguration withPort(int num) {
			port = ()->num;
			return this;
		}

		@Override
		public IHttpConfiguration withWelcomePage(String welcomePage) {
			this.welcomePage = ()->welcomePage;
			return this;
		}
		
		@Override
		public IHttpConfiguration withSendsXAuthorization(boolean set) {
			sendsXAuthorization = ()->set;
			return this;
		}
		
		@Override
		public IHttpConfiguration withKeyStoreConfiguration(IKeyStoreConfiguration config) {
			keyStoreConfiguration = ()->config;
			return this;
		}
		
		@Override
		public IHttpConfiguration withTrustStoreConfiguration(IKeyStoreConfiguration config) {
			trustStoreConfiguration = ()->config;
			return this;
		}

		@Override
		public IHttpConfiguration withAuthenticationConfiguration(IAuthenticationConfiguration config) {
			authenticationConfiguration = ()->config;
			return this;
		}
	
	}

	
}
