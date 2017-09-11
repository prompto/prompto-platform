package prompto.config;

import java.util.Map;

public class ServerConfiguration extends RuntimeConfiguration implements IServerConfiguration {

	public ServerConfiguration(IConfigurationReader reader, Map<String, String> arguments) {
		super(reader, arguments);
	}

	@Override
	public IHttpConfiguration getHttpConfiguration() {
		IConfigurationReader child = reader.getObject("http");
		return child==null ? null : new HttpConfiguration(child);
	}

	@Override
	public String getServerAboutToStartMethod() {
		return reader.getString("serverAboutToStart");
	}

	@Override
	public String getWebSiteRoot() {
		return reader.getString("webSite");
	}

}
