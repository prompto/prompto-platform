package prompto.config.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.config.auth.method.IAuthenticationMethodConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;

public interface IAuthenticationConfiguration {
	
	IAuthenticationMethodConfiguration getAuthenticationMethodConfiguration();
	IAuthenticationSourceConfiguration getAuthenticationSourceConfiguration();
	Collection<String> getWhiteList();
	
	IAuthenticationConfiguration withAuthenticationMethodConfiguration(IAuthenticationMethodConfiguration config);
	IAuthenticationConfiguration withAuthenticationSourceConfiguration(IAuthenticationSourceConfiguration config);
	IAuthenticationConfiguration withWhiteList(Collection<String> whiteList);

	YamlMapping toYaml() throws YamlException;

	public static class Inline implements IAuthenticationConfiguration {

		Supplier<IAuthenticationMethodConfiguration> authenticationMethodConfiguration = ()->null;
		Supplier<IAuthenticationSourceConfiguration> authenticationSourceConfiguration = ()->null;
		Supplier<Collection<String>> whiteList = ()->DEFAULT_WHITE_LIST;
		
		@Override public IAuthenticationMethodConfiguration getAuthenticationMethodConfiguration() { return authenticationMethodConfiguration.get(); }
		@Override public IAuthenticationSourceConfiguration getAuthenticationSourceConfiguration() { return authenticationSourceConfiguration.get(); }
		@Override public Collection<String> getWhiteList() { return whiteList.get(); }
		
		@Override
		public IAuthenticationConfiguration withAuthenticationMethodConfiguration(IAuthenticationMethodConfiguration config) {
			authenticationMethodConfiguration = ()->config;
			return this;
		}
		
		@Override
		public IAuthenticationConfiguration withAuthenticationSourceConfiguration(IAuthenticationSourceConfiguration config) {
			authenticationSourceConfiguration = ()->config;
			return this;
		}
		
		@Override
		public IAuthenticationConfiguration withWhiteList(Collection<String> list) {
			whiteList = ()->list;
			return this;
		}
		
		@Override
		public YamlMapping toYaml() throws YamlException {
			return null;
		}
	
	}

	static final Collection<String> DEFAULT_WHITE_LIST = Arrays.asList( "jpg", "jpeg", "ico", "png", "tif", "tiff", "js", "jsx", "css", "svg" )
			.stream().map(s->"*." + s).collect(Collectors.toList());




}
