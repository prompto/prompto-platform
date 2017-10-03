package prompto.config;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class ServerConfiguration extends RuntimeConfiguration implements IServerConfiguration {

	Supplier<IHttpConfiguration> httpConfiguration;
	Supplier<String> serverAboutToStartMethod;
	Supplier<String> webSiteRoot;

	public ServerConfiguration(IConfigurationReader reader, Map<String, String> arguments) {
		super(reader, arguments);
		this.httpConfiguration = ()->readHttpConfiguration();
		this.serverAboutToStartMethod = ()->reader.getString("serverAboutToStart");
		this.webSiteRoot = ()->reader.getString("webSiteRoot");
	}

	private IHttpConfiguration readHttpConfiguration() {
		IConfigurationReader child = reader.getObject("http");
		return child==null ? null : new HttpConfiguration(child);
	}

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
