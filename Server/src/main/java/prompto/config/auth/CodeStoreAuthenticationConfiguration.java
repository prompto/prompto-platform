package prompto.config.auth;

import java.util.Collection;
import java.util.function.Supplier;

import com.esotericsoftware.yamlbeans.document.YamlMapping;
import com.esotericsoftware.yamlbeans.document.YamlSequence;

import prompto.code.ICodeStore;
import prompto.code.MutableCodeStore;
import prompto.config.IConfigurationReader;
import prompto.config.StoredRecordConfigurationReader;
import prompto.config.auth.method.IAuthenticationMethodConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.intrinsic.PromptoDbId;
import prompto.runtime.Mode;
import prompto.security.auth.method.BasicAuthenticationMethodFactory;
import prompto.security.auth.method.FormAuthenticationMethodFactory;
import prompto.security.auth.method.IAuthenticationMethodFactory;
import prompto.security.auth.source.IAuthenticationSourceFactory;
import prompto.security.auth.source.PasswordIsUserNameAuthenticationSourceFactory;
import prompto.security.auth.source.StoredPasswordDigestAuthenticationSourceFactory;
import prompto.store.IStore;
import prompto.utils.Logger;


public class CodeStoreAuthenticationConfiguration extends IAuthenticationConfiguration.Inline {

	static final Logger logger = new Logger();

	IConfigurationReader source;
	StoredRecordConfigurationReader reader;
	Supplier<IAuthenticationSourceConfiguration> storedAuthenticationSourceConfiguration;
	Supplier<IAuthenticationMethodConfiguration> storedAuthenticationMethodConfiguration;
	Supplier<Collection<String>> whiteList;
	
	public CodeStoreAuthenticationConfiguration(IConfigurationReader source) {
		this();
		this.source = source;
	}
	
	public CodeStoreAuthenticationConfiguration(StoredRecordConfigurationReader reader) {
		this();
		this.reader = reader.getObject("authenticationSettings");
	}

	private CodeStoreAuthenticationConfiguration() {
		this.authenticationSourceConfiguration = ()->readAuthenticationSourceConfiguration(null);
		this.authenticationMethodConfiguration = ()->readAuthenticationMethodConfiguration();
		this.whiteList = ()->readWhiteList();
	}
	
	private IStore locateStore() {
		ICodeStore codeStore = ICodeStore.instance.get();
		if(!(codeStore instanceof MutableCodeStore)) {
			logger.error(()->"No code store to fetch config!");
			return null;
		}
		IStore store = ((MutableCodeStore)codeStore).getStore();
		if(store==null){
			logger.error(()->"No underlying store to fetch config!");
			return null;
		}
		return store;
	}

	private boolean loadReader() {
		if(reader!=null)
			return true;
		if(source==null)
			return false;
		IStore store = locateStore();
		if(store==null)
			return false;
		String dbId = source.getString("dbId");
		if(dbId==null) {
			logger.error(()->"No dbId to fetch config!");
			return false;
		}
		StoredRecordConfigurationReader app = new StoredRecordConfigurationReader(store, PromptoDbId.of(dbId));
		reader = app.getObject("authenticationSettings");
		return reader!=null;
	}
	
	public YamlMapping toYaml(Mode mode) throws Throwable {
		if(!isEnabled(mode))
			return null;
		YamlMapping settings = new YamlMapping();
		IAuthenticationMethodConfiguration method = readAuthenticationMethodConfiguration(); 
		IAuthenticationSourceConfiguration source = readAuthenticationSourceConfiguration(mode);
		if(method!=null && source!=null) {
			YamlMapping yaml = method.getAuthenticationMethodFactory().toYaml();
			settings.setEntry("method", yaml);
			yaml = source.getAuthenticationSourceFactory().toYaml();
			settings.setEntry("source", yaml);
			YamlSequence list = new YamlSequence();
			for(String w : fetchWhiteList())
				list.addElement(w);
			settings.setEntry("whiteList", list);
			settings.setEntry("useDefaultWhiteList", usesDefaultWhiteList());
			return settings;
		} else
			return null;
	}

	public boolean isEnabled(Mode runtimeMode) {
		if(runtimeMode!=Mode.DEVELOPMENT)
			return true;
		if(!loadReader())
			return false;
		Boolean skipAuthInDev = reader.getBooleanOrDefault("skipAuthInDev", Boolean.FALSE);
		return !skipAuthInDev;
	}


	private IAuthenticationMethodConfiguration readAuthenticationMethodConfiguration() {
		if(storedAuthenticationMethodConfiguration!=null) 
			return storedAuthenticationMethodConfiguration.get();
		else {
			IAuthenticationMethodConfiguration config = fetchAuthenticationMethodConfiguration();
			storedAuthenticationMethodConfiguration = ()->config;
			return config;
		}
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
			StoredRecordConfigurationReader method = reader.getObject("authenticationMethod");
			if(method==null)
				return null;
			String category = method.readCategory();
			if(category==null) // not sure how that would happen
				return null;
			String factoryName = AuthenticationMethod.valueOf(category).getFactoryName();
			IAuthenticationMethodFactory factory = IAuthenticationMethodFactory.newFactory(factoryName);
			IAuthenticationMethodConfiguration config = factory.newConfiguration(method);
			factory.setConfiguration(config);
			return ()->factory; // ensure we don't try to read "factory" from the config
		} catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public IAuthenticationSourceConfiguration readAuthenticationSourceConfiguration(Mode runtimeMode) {
		if(storedAuthenticationSourceConfiguration!=null)
			return storedAuthenticationSourceConfiguration.get();
		IAuthenticationSourceConfiguration config = fetchAuthenticationSourceConfiguration(runtimeMode);
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
	
	private IAuthenticationSourceConfiguration fetchAuthenticationSourceConfiguration(Mode runtimeMode) {
		try{
			if(!loadReader())
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
		if(whiteList!=null)
			return whiteList.get();
		Collection<String> fetched = fetchWhiteList();
		whiteList = ()->fetched;
		return fetched;
	}
	
	
	private Collection<String> fetchWhiteList() {
		if(!loadReader())
			return null;
		else 
			return reader.getArray("whiteList");
	}

	private boolean usesDefaultWhiteList() {
		return reader.getBooleanOrDefault("useDefaultWhiteList", Boolean.FALSE);
	}


}
