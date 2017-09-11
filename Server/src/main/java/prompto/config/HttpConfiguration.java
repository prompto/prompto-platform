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
	public String getAllowedOrigin() {
		return reader.getString("origin");
	}
}
