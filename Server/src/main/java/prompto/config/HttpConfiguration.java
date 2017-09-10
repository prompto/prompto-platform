package prompto.config;

public class HttpConfiguration implements IHttpConfiguration {

	IConfigurationReader reader;
	
	public HttpConfiguration(IConfigurationReader reader) {
		this.reader = reader;
	}

	@Override
	public int getPort() {
		return reader.getIntegerOrDefault("port", -1);
	}

	@Override
	public String getOrigin() {
		return reader.getString("origin");
	}
}
