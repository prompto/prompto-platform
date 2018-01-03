package prompto.config;

import java.util.Collection;

import prompto.code.ICodeStore;
import prompto.code.QueryableCodeStore;
import prompto.security.ILoginMethodFactory;
import prompto.security.ILoginModuleFactory;
import prompto.security.PasswordIsUserNameLoginModuleFactory;
import prompto.store.IStore;
import prompto.utils.Logger;

import com.google.common.base.Supplier;

public class CodeStoreLoginConfiguration extends ILoginConfiguration.Inline {

	static final Logger logger = new Logger();

	IConfigurationReader source;
	StoredRecordConfigurationReader reader;
	Supplier<ILoginModuleConfiguration> storedLoginModuleConfiguration;
	Supplier<ILoginMethodConfiguration> storedLoginMethodConfiguration;
	Supplier<Collection<String>> storedWhiteList;
	
	public CodeStoreLoginConfiguration(IConfigurationReader reader) {
		this.source = reader;
		this.loginModuleConfiguration = ()->readLoginModuleConfiguration();
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
		reader = new StoredRecordConfigurationReader(store, dbId);
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

	public ILoginModuleConfiguration readLoginModuleConfiguration() {
		if(storedLoginModuleConfiguration==null)
			return storedLoginModuleConfiguration.get();
		ILoginModuleConfiguration config = fetchLoginModuleConfiguration();
		storedLoginModuleConfiguration = ()->config;
		return config;
	}


	private ILoginModuleConfiguration fetchLoginModuleConfiguration() {
		try{
			if(!loadReader())
				return null;
			Boolean skipAuthInDev = reader.getBooleanOrDefault("skipAuthInDev", Boolean.FALSE);
			if(skipAuthInDev)
				return null;
			Boolean useTestSourceInDev = reader.getBooleanOrDefault("useTestSourceInDev", Boolean.FALSE);
			if(useTestSourceInDev) {
				ILoginModuleConfiguration config = ()->new PasswordIsUserNameLoginModuleFactory();
				storedLoginModuleConfiguration = ()->config;
				return config;
			}
			IConfigurationReader source = reader.getObject("authenticationSource");
			if(source==null)
				return null;
			String factoryName = source.getString("factory");
			if(factoryName==null)
				return null;
			ILoginModuleFactory factory = ILoginModuleFactory.newFactory(factoryName);
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
