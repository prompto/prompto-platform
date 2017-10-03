package prompto.config;

import java.util.function.Supplier;

public interface IServerConfiguration extends IRuntimeConfiguration {

	IHttpConfiguration getHttpConfiguration();
	String getServerAboutToStartMethod();
	String getWebSiteRoot();
	
	<T extends IServerConfiguration> T withServerAboutToStartMethod(String method);
	<T extends IServerConfiguration> T withHttpConfiguration(IHttpConfiguration config);
	
	@SuppressWarnings("unchecked")
	public static class Inline extends IRuntimeConfiguration.Inline implements IServerConfiguration {

		Supplier<IHttpConfiguration> httpConfiguration = ()->null;
		Supplier<String> serverAboutToStartMethod = ()->null;
		Supplier<String> webSiteRoot = ()->null;

		@Override public IHttpConfiguration getHttpConfiguration() { return httpConfiguration.get(); }
		@Override public String getServerAboutToStartMethod() { return serverAboutToStartMethod.get(); }
		@Override public String getWebSiteRoot() { return webSiteRoot.get(); }
		
		@Override
		public <T extends IServerConfiguration> T withServerAboutToStartMethod(String method) {
			this.serverAboutToStartMethod = ()->method;
			return (T)this;
		}
		
		@Override
		public <T extends IServerConfiguration> T withHttpConfiguration(IHttpConfiguration config) {
			this.httpConfiguration = ()->config;
			return (T)this;
		}
		
	}

}
