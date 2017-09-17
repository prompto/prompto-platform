package prompto.config;

public interface IServerConfiguration extends IRuntimeConfiguration {

	IHttpConfiguration getHttpConfiguration();
	String getServerAboutToStartMethod();
	String getWebSiteRoot();
	
	public static class Sourced extends IRuntimeConfiguration.Sourced<IServerConfiguration> implements IServerConfiguration {

		public Sourced(IServerConfiguration config) {
			super(config);
		}

		@Override public IHttpConfiguration getHttpConfiguration() { return source.getHttpConfiguration(); }
		@Override public String getServerAboutToStartMethod() { return source.getServerAboutToStartMethod(); }
		@Override public String getWebSiteRoot() { return source.getWebSiteRoot(); }
		
	}
}
