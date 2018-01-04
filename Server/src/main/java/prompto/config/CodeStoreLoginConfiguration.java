package prompto.config;

import java.util.Collection;
import java.util.function.Supplier;

import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.security.BasicLoginMethodFactory;
import prompto.security.FormLoginMethodFactory;
import prompto.security.ILoginMethodFactory;
import prompto.security.ILoginSourceFactory;
import prompto.security.PasswordIsUserNameLoginSourceFactory;
import prompto.security.StoredPasswordDigestLoginSourceFactory;
import prompto.store.IStore;
import prompto.utils.Logger;


public class CodeStoreLoginConfiguration extends ILoginConfiguration.Inline {

	static final Logger logger = new Logger();

	IConfigurationReader source;
	StoredRecordConfigurationReader reader;
	Supplier<ILoginSourceConfiguration> storedLoginSourceConfiguration;
	Supplier<ILoginMethodConfiguration> storedLoginMethodConfiguration;
	Supplier<Collection<String>> storedWhiteList;
	
	public CodeStoreLoginConfiguration(IConfigurationReader reader) {
		this.source = reader;
		this.loginSourceConfiguration = ()->readLoginSourceConfiguration();
		this.loginMethodConfiguration = ()->readLoginMethodConfiguration();
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
	
	public ILoginMethodConfiguration readLoginMethodConfiguration() {
		if(storedLoginMethodConfiguration!=null) 
			return storedLoginMethodConfiguration.get();
		ILoginMethodConfiguration config = fetchLoginMethodConfiguration();
		storedLoginMethodConfiguration = ()->config;
		return config;
	}

	static enum AuthenticationMethod {
		NoAuthenticationMethod(null),
		BasicAuthenticationMethod(BasicLoginMethodFactory.class.getName()),
		FormAuthenticationMethod(FormLoginMethodFactory.class.getName());
		
		private String factoryName;

		AuthenticationMethod(String factoryName) {
			this.factoryName = factoryName;
		}
		
		public String getFactoryName() {
			return factoryName;
		}
	}
	

	private ILoginMethodConfiguration fetchLoginMethodConfiguration() {
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
			ILoginMethodFactory factory = ILoginMethodFactory.newFactory(factoryName);
			ILoginMethodConfiguration config = factory.newConfiguration(source);
			factory.setConfiguration(config);
			return ()->factory; // ensure we don't try to read "factory" from the config
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public ILoginSourceConfiguration readLoginSourceConfiguration() {
		if(storedLoginSourceConfiguration!=null)
			return storedLoginSourceConfiguration.get();
		ILoginSourceConfiguration config = fetchLoginSourceConfiguration();
		storedLoginSourceConfiguration = ()->config;
		return config;
	}

	static enum AuthenticationSource {
		DataStoreAuthenticationSource(StoredPasswordDigestLoginSourceFactory.class.getName()),
		PasswordIsLoginAuthenticationSource(PasswordIsUserNameLoginSourceFactory.class.getName());
		
		private String factoryName;

		AuthenticationSource(String factoryName) {
			this.factoryName = factoryName;
		}
		
		public String getFactoryName() {
			return factoryName;
		}
	}
	
	private ILoginSourceConfiguration fetchLoginSourceConfiguration() {
		try{
			if(!loadReader())
				return null;
			Boolean skipAuthInDev = reader.getBooleanOrDefault("skipAuthInDev", Boolean.FALSE);
			if(skipAuthInDev)
				return null;
			Boolean useTestSourceInDev = reader.getBooleanOrDefault("useTestSourceInDev", Boolean.FALSE);
			if(useTestSourceInDev) {
				ILoginSourceConfiguration config = ()->new PasswordIsUserNameLoginSourceFactory();
				storedLoginSourceConfiguration = ()->config;
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
			ILoginSourceFactory factory = ILoginSourceFactory.newFactory(factoryName);
			ILoginSourceConfiguration config = factory.newConfiguration(source);
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
