package prompto.config;

import java.util.Collection;
import java.util.function.Supplier;

import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.security.ILoginMethodFactory;
import prompto.security.ILoginSourceFactory;
import prompto.security.PasswordIsUserNameLoginSourceFactory;
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
			String factoryName = method.getString("factory");
			if(factoryName==null)
				return null;
			ILoginMethodFactory factory = ILoginMethodFactory.newFactory(factoryName);
			return factory.newConfiguration(method);
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
			IConfigurationReader source = reader.getObject("authenticationSource");
			if(source==null)
				return null;
			String factoryName = source.getString("factory");
			if(factoryName==null)
				return null;
			ILoginSourceFactory factory = ILoginSourceFactory.newFactory(factoryName);
			return factory.newConfiguration(source);
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
