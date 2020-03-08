package prompto.config;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.auth.AuthenticationConfiguration;
import prompto.config.auth.IAuthenticationConfiguration;
import prompto.config.auth.IAuthenticationConfigurationFactory;

public class HttpConfiguration extends IHttpConfiguration.Inline {

	IConfigurationReader reader;
	
	public HttpConfiguration(IConfigurationReader reader) {
		this.reader = reader;
		this.protocol = ()->reader.getStringOrDefault("protocol", "http");
		this.port = ()->reader.getIntegerOrDefault("port", -1);
		this.welcomePage = ()->reader.getString("welcomePage");
		this.redirectFrom = ()->reader.getInteger("redirectFrom");
		this.allowedOrigins = ()->reader.getString("allowedOrigins");
		this.allowsXAuthorization = ()->reader.getBooleanOrDefault("allowsXAuthorization", false);
		this.sendsXAuthorization = ()->reader.getBooleanOrDefault("sendsXAuthorization", false);
		this.keyStoreConfiguration = ()->readKeyStoreConfiguration();
		this.trustStoreConfiguration = ()->readTrustStoreConfiguration();
		this.authenticationConfiguration = ()->readAuthenticationConfiguration();
	}
	
	private IKeyStoreConfiguration readKeyStoreConfiguration() {
		IConfigurationReader child = reader.getObject("keyStore");
		return child==null ? null : new KeyStoreConfiguration(child);
	}
	
	private IKeyStoreConfiguration readTrustStoreConfiguration() {
		IConfigurationReader child = reader.getObject("trustStore");
		return child==null ? null : new KeyStoreConfiguration(child);
	}
	
	private IAuthenticationConfiguration readAuthenticationConfiguration() {
		IConfigurationReader child = reader.getObject("authentication");
		if(child==null)
			return null;
		String factoryName = child.getString("factory");
		if(factoryName==null)
			return new AuthenticationConfiguration(child);
		else try {
			IAuthenticationConfigurationFactory factory = IAuthenticationConfigurationFactory.newFactory(factoryName);
			return factory.newConfiguration(child);
		} catch(Throwable e) {
			throw new RuntimeException(e);
		}
			
	}
	
	@Override
	public YamlMapping toYaml() throws YamlException {
		YamlMapping yaml = new YamlMapping();
		String value = protocol.get();
		if(value!=null)
			yaml.setEntry("protocol", value);
		if(port.get()!=-1)
			yaml.setEntry("port", port.get());
		value = welcomePage.get();
		if(value!=null)
			yaml.setEntry("welcomePage", value);
		if(redirectFrom.get()!=null)
			yaml.setEntry("redirectFrom", redirectFrom.get());
		value = allowedOrigins.get();
		if(value!=null)
			yaml.setEntry("allowedOrigins", value);
		IKeyStoreConfiguration config = keyStoreConfiguration.get();
		if(config!=null)
			yaml.setEntry("keyStore", config.toYaml());
		config = trustStoreConfiguration.get();
		if(config!=null)
			yaml.setEntry("trustStore", config.toYaml());
		IAuthenticationConfiguration auth = authenticationConfiguration.get();
		if(auth!=null)
			yaml.setEntry("authentication", auth.toYaml());
		return yaml;
	}

}
