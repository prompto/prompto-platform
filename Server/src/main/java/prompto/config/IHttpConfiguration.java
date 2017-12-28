package prompto.config;

import java.util.function.Supplier;

public interface IHttpConfiguration {

	String getProtocol();
	int getPort();
	Integer getRedirectFrom();
	String getAllowedOrigins();
	IKeyStoreConfiguration getKeyStoreConfiguration();
	IKeyStoreConfiguration getTrustStoreConfiguration();
	ILoginModuleConfiguration getLoginModuleConfiguration();
	ILoginMethodConfiguration getLoginMethodConfiguration();
	boolean getAllowsXAuthorization();
	boolean getSendsXAuthorization();
	
	IHttpConfiguration withProtocol(String protocol);
	IHttpConfiguration withPort(int port);
	IHttpConfiguration withSendsXAuthorization(boolean set);
	IHttpConfiguration withKeyStoreConfiguration(IKeyStoreConfiguration config);
	IHttpConfiguration withTrustStoreConfiguration(IKeyStoreConfiguration config);
	IHttpConfiguration withLoginModuleConfiguration(ILoginModuleConfiguration config);
	IHttpConfiguration withLoginMethodConfiguration(ILoginMethodConfiguration config);
	
	
	public static class Inline implements IHttpConfiguration {

		Supplier<String> protocol = ()->null;
		Supplier<Integer> port = ()->0;
		Supplier<Integer> redirectFrom = ()->null;
		Supplier<String> allowedOrigins = ()->null;
		Supplier<IKeyStoreConfiguration> keyStoreConfiguration = ()->null;
		Supplier<IKeyStoreConfiguration> trustStoreConfiguration = ()->null;
		Supplier<ILoginModuleConfiguration> loginModuleConfiguration = ()->null;
		Supplier<ILoginMethodConfiguration> loginMethodConfiguration = ()->null;
		Supplier<Boolean> allowsXAuthorization = ()->false;
		Supplier<Boolean> sendsXAuthorization = ()->false;
		
		@Override public String getProtocol() { return protocol.get(); }
		@Override public int getPort() { return port.get(); }
		@Override public Integer getRedirectFrom() { return redirectFrom.get(); }
		@Override public String getAllowedOrigins() { return allowedOrigins.get(); }
		@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { return keyStoreConfiguration.get(); }
		@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { return trustStoreConfiguration.get(); }
		@Override public ILoginModuleConfiguration getLoginModuleConfiguration() { return loginModuleConfiguration.get(); }
		@Override public ILoginMethodConfiguration getLoginMethodConfiguration() { return loginMethodConfiguration.get(); }
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
		public IHttpConfiguration withLoginModuleConfiguration(ILoginModuleConfiguration config) {
			loginModuleConfiguration = ()->config;
			return this;
		}
		@Override
		public IHttpConfiguration withLoginMethodConfiguration(ILoginMethodConfiguration config) {
			loginMethodConfiguration = ()->config;
			return this;
		}
	
	}

	

	

	

}
