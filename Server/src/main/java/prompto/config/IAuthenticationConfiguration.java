package prompto.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface IAuthenticationConfiguration {
	
	IAuthenticationMethodConfiguration getAuthenticationMethodConfiguration();
	IAuthenticationSourceConfiguration getAuthenticationSourceConfiguration();
	Collection<String> getWhiteList();
	
	IAuthenticationConfiguration withAuthenticationMethodConfiguration(IAuthenticationMethodConfiguration config);
	IAuthenticationConfiguration withAuthenticationSourceConfiguration(IAuthenticationSourceConfiguration config);
	IAuthenticationConfiguration withWhiteList(Collection<String> whiteList);

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
	
	}

	static final Collection<String> DEFAULT_WHITE_LIST = Arrays.asList( "jpg", "jpeg", "ico", "png", "tif", "tiff", "js", "jsx", "css" )
			.stream().map(s->"*." + s).collect(Collectors.toList());



}
