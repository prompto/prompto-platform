package prompto.config;

import java.util.Collection;
import java.util.function.Supplier;

import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.security.BasicAuthenticationMethodFactory;
import prompto.security.FormAuthenticationMethodFactory;
import prompto.security.IAuthenticationMethodFactory;
import prompto.security.IAuthenticationSourceFactory;
import prompto.security.PasswordIsUserNameAuthenticationSourceFactory;
import prompto.security.StoredPasswordDigestAuthenticationSourceFactory;
import prompto.store.IStore;
import prompto.utils.Logger;


public class CodeStoreAuthenticationConfiguration extends IAuthenticationConfiguration.Inline {

	static final Logger logger = new Logger();

	IConfigurationReader source;
	StoredRecordConfigurationReader reader;
	Supplier<IAuthenticationSourceConfiguration> storedAuthenticationSourceConfiguration;
	Supplier<IAuthenticationMethodConfiguration> storedAuthenticationMethodConfiguration;
	Supplier<Collection<String>> storedWhiteList;
	
	public CodeStoreAuthenticationConfiguration(IConfigurationReader reader) {
		this.source = reader;
		this.authenticationSourceConfiguration = ()->readAuthenticationSourceConfiguration();
		this.authenticationMethodConfiguration = ()->readAuthenticationMethodConfiguration();
		this.whiteList = ()->readWhiteList();
	}
	
	private IStore locateStore() {
		ICodeStore codeStore = ICodeStore.instance.get();
		if(!(codeStore instanceof QueryableCodeStore)) {
			logger.error(()->"No code store to fetch config!");
			return null;
		}
		IStore store = ((QueryableCodeStore)codeStore).getStore();
		if(store==null){
			logger.error(()->"No underlying store to fetch config!");
			return null;
		}
		return store;
	}

	private boolean loadReader() {
		if(reader!=null)
			return true;
		IStore store = locateStore();
		if(store==null)
			return false;
		String dbId = source.getString("dbId");
		if(dbId==null) {
			logger.error(()->"No dbId to fetch config!");
			return false;
		}
		StoredRecordConfigurationReader app = new StoredRecordConfigurationReader(store, dbId);
		reader = app.getObject("authenticationSettings");
		return true;
	}
	
	public IAuthenticationMethodConfiguration readAuthenticationMethodConfiguration() {
		if(storedAuthenticationMethodConfiguration!=null) 
			return storedAuthenticationMethodConfiguration.get();
		IAuthenticationMethodConfiguration config = fetchAuthenticationMethodConfiguration();
		storedAuthenticationMethodConfiguration = ()->config;
		return config;
	}

	static enum AuthenticationMethod {
		NoAuthenticationMethod(null),
		BasicAuthenticationMethod(BasicAuthenticationMethodFactory.class.getName()),
		FormAuthenticationMethod(FormAuthenticationMethodFactory.class.getName());
		
		private String factoryName;

		AuthenticationMethod(String factoryName) {
			this.factoryName = factoryName;
		}
		
		public String getFactoryName() {
			return factoryName;
		}
	}
	

	private IAuthenticationMethodConfiguration fetchAuthenticationMethodConfiguration() {
		try {
			if(!loadReader())
				return null;
			Boolean skipAuthInDev = reader.getBooleanOrDefault("skipAuthInDev", Boolean.FALSE);
			if(skipAuthInDev)
				return null;
			StoredRecordConfigurationReader method = reader.getObject("authenticationMethod");
			if(method==null)
				return null;
			String category = method.readCategory();
			if(category==null) // not sure how that would happen
				return null;
			String factoryName = AuthenticationMethod.valueOf(category).getFactoryName();
			IAuthenticationMethodFactory factory = IAuthenticationMethodFactory.newFactory(factoryName);
			IAuthenticationMethodConfiguration config = factory.newConfiguration(source);
			factory.setConfiguration(config);
			return ()->factory; // ensure we don't try to read "factory" from the config
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public IAuthenticationSourceConfiguration readAuthenticationSourceConfiguration() {
		if(storedAuthenticationSourceConfiguration!=null)
			return storedAuthenticationSourceConfiguration.get();
		IAuthenticationSourceConfiguration config = fetchAuthenticationSourceConfiguration();
		storedAuthenticationSourceConfiguration = ()->config;
		return config;
	}

	static enum AuthenticationSource {
		DataStoreAuthenticationSource(StoredPasswordDigestAuthenticationSourceFactory.class.getName()),
		PasswordIsLoginAuthenticationSource(PasswordIsUserNameAuthenticationSourceFactory.class.getName());
		
		private String factoryName;

		AuthenticationSource(String factoryName) {
			this.factoryName = factoryName;
		}
		
		public String getFactoryName() {
			return factoryName;
		}
	}
	
	private IAuthenticationSourceConfiguration fetchAuthenticationSourceConfiguration() {
		try{
			if(!loadReader())
				return null;
			Boolean skipAuthInDev = reader.getBooleanOrDefault("skipAuthInDev", Boolean.FALSE);
			if(skipAuthInDev)
				return null;
			Boolean useTestSourceInDev = reader.getBooleanOrDefault("useTestSourceInDev", Boolean.FALSE);
			if(useTestSourceInDev) {
				IAuthenticationSourceConfiguration config = ()->new PasswordIsUserNameAuthenticationSourceFactory();
				storedAuthenticationSourceConfiguration = ()->config;
				return config;
			}
			StoredRecordConfigurationReader source = reader.getObject("authenticationSource");
			if(source==null)
				return null;
			String category = source.readCategory();
			if(category==null) // not sure how that would happen
				return null;
			String factoryName = AuthenticationSource.valueOf(category).getFactoryName();
			if(factoryName==null)
				return null;
			IAuthenticationSourceFactory factory = IAuthenticationSourceFactory.newFactory(factoryName);
			IAuthenticationSourceConfiguration config = factory.newConfiguration(source);
			factory.setConfiguration(config);
			return ()->factory; // ensure we don't try to read "factory" from the config
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public Collection<String> readWhiteList() {
		if(storedWhiteList!=null)
			return storedWhiteList.get();
		Collection<String> whiteList = fetchWhiteList();
		storedWhiteList = ()->whiteList;
		return whiteList;
	}
	
	
	private Collection<String> fetchWhiteList() {
		if(!loadReader())
			return null;
		Boolean useDefaultWhiteList = reader.getBooleanOrDefault("useDefaultWhiteList", Boolean.FALSE);
		if(useDefaultWhiteList)
			return DEFAULT_WHITE_LIST;
		Collection<String> whiteList = reader.getArray("whiteList");
		if(whiteList==null)
			return DEFAULT_WHITE_LIST;
		else
			return whiteList;
	}

}
