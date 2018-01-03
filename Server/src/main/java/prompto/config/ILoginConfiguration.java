package prompto.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface ILoginConfiguration {
	
	ILoginSourceConfiguration getLoginSourceConfiguration();
	ILoginMethodConfiguration getLoginMethodConfiguration();
	Collection<String> getWhiteList();
	
	ILoginConfiguration withLoginSourceConfiguration(ILoginSourceConfiguration config);
	ILoginConfiguration withLoginMethodConfiguration(ILoginMethodConfiguration config);
	ILoginConfiguration withWhiteList(Collection<String> whiteList);

	public static class Inline implements ILoginConfiguration {

		Supplier<ILoginSourceConfiguration> loginSourceConfiguration = ()->null;
		Supplier<ILoginMethodConfiguration> loginMethodConfiguration = ()->null;
		Supplier<Collection<String>> whiteList = ()->DEFAULT_WHITE_LIST;
		
		@Override public ILoginSourceConfiguration getLoginSourceConfiguration() { return loginSourceConfiguration.get(); }
		@Override public ILoginMethodConfiguration getLoginMethodConfiguration() { return loginMethodConfiguration.get(); }
		@Override public Collection<String> getWhiteList() { return whiteList.get(); }
		
		@Override
		public ILoginConfiguration withLoginSourceConfiguration(ILoginSourceConfiguration config) {
			loginSourceConfiguration = ()->config;
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
