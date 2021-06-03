package prompto.config;

import java.util.Map;
import java.util.function.Supplier;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

@SuppressWarnings("unchecked")
public class ServerConfiguration extends RuntimeConfiguration implements IServerConfiguration {

	Supplier<IHttpConfiguration> httpConfiguration;
	Supplier<String> serverAboutToStartMethod;
	Supplier<String> webSiteRoot;
	Supplier<Boolean> useConsole;

	public ServerConfiguration(IConfigurationReader reader, Map<String, String> arguments) {
		super(reader, arguments);
		this.httpConfiguration = ()->readHttpConfiguration();
		this.serverAboutToStartMethod = ()->reader.getString("serverAboutToStart");
		this.webSiteRoot = ()->reader.getString("webSiteRoot");
		this.useConsole = ()->reader.getBooleanOrDefault("useConsole", false);
	}

	private IHttpConfiguration readHttpConfiguration() {
		IConfigurationReader child = reader.getObject("http");
		return child==null ? null : new HttpConfiguration(child);
	}

	@Override public IHttpConfiguration getHttpConfiguration() { return httpConfiguration.get(); }
	@Override public String getServerAboutToStartMethod() { return serverAboutToStartMethod.get(); }
	@Override public String getWebSiteRoot() { return webSiteRoot.get(); }
	@Override public boolean useConsole() { return useConsole.get(); }
	
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
	
	@Override
	public <T extends IServerConfiguration> T withUseConsole(boolean set) {
		this.useConsole = ()->set;
		return (T)this;
	}

	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = super.toYaml();
		IHttpConfiguration http = httpConfiguration.get();
		if(http!=null)
			yaml.setEntry("http", http.toYaml());
		String value = serverAboutToStartMethod.get();
		if(value!=null)
			yaml.setEntry("serverAboutToStart", value);
		value = webSiteRoot.get();
		if(value!=null)
			yaml.setEntry("webSiteRoot", value);
		boolean flag = useConsole.get();
		if(flag)
			yaml.setEntry("useConsole", flag);
		return yaml;
	}


}
