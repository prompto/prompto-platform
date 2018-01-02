package prompto.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface ILoginConfiguration {
	
	ILoginModuleConfiguration getLoginModuleConfiguration();
	ILoginMethodConfiguration getLoginMethodConfiguration();
	Collection<String> getWhiteList();
	
	ILoginConfiguration withLoginModuleConfiguration(ILoginModuleConfiguration config);
	ILoginConfiguration withLoginMethodConfiguration(ILoginMethodConfiguration config);
	ILoginConfiguration withWhiteList(Collection<String> whiteList);

	public static class Inline implements ILoginConfiguration {

		Supplier<ILoginModuleConfiguration> loginModuleConfiguration = ()->null;
		Supplier<ILoginMethodConfiguration> loginMethodConfiguration = ()->null;
		Supplier<Collection<String>> whiteList = ()->DEFAULT_WHITE_LIST;
		
		@Override public ILoginModuleConfiguration getLoginModuleConfiguration() { return loginModuleConfiguration.get(); }
		@Override public ILoginMethodConfiguration getLoginMethodConfiguration() { return loginMethodConfiguration.get(); }
		@Override public Collection<String> getWhiteList() { return whiteList.get(); }
		
		@Override
		public ILoginConfiguration withLoginModuleConfiguration(ILoginModuleConfiguration config) {
			loginModuleConfiguration = ()->config;
			return this;
		}
		
		@Override
		public ILoginConfiguration withLoginMethodConfiguration(ILoginMethodConfiguration config) {
			loginMethodConfiguration = ()->config;
			return this;
		}
		
		@Override
		public ILoginConfiguration withWhiteList(Collection<String> list) {
			whiteList = ()->list;
			return this;
		}
	
	}

	static final Collection<String> DEFAULT_WHITE_LIST = Arrays.asList( "jpg", "jpeg", "ico", "png", "tif", "tiff", "js", "jsx", "css" )
			.stream().map(s->"*." + s).collect(Collectors.toList());

}
