package prompto.config;

public interface IServerConfiguration extends IRuntimeConfiguration {

	IHttpConfiguration getHttpConfiguration();
	String getServerAboutToStartMethod();
	String getWebSiteRoot();
}
