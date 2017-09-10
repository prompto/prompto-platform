package prompto.store.solr;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import prompto.config.IConfigurationReader;
import prompto.config.IStoreConfiguration;
import prompto.config.solr.ISOLRStoreConfiguration;
import prompto.config.solr.SOLRStoreConfiguration;
import prompto.store.IStoreFactory;
import prompto.utils.StringUtils;

public class SOLRStoreFactory implements IStoreFactory {

	@Override
	public IStoreConfiguration newConfiguration(IConfigurationReader reader) {
		return new SOLRStoreConfiguration(reader);
	}
	
	@Override
	public BaseSOLRStore newStore(IStoreConfiguration config) throws Exception {
		BaseSOLRStore store = newStoreFromConfig((ISOLRStoreConfiguration)config);
		store.createCoreIfRequired();
		return store;
	}

	private BaseSOLRStore newStoreFromConfig(ISOLRStoreConfiguration config) throws Exception {
		if(config.isEmbedded())
			return newEmbeddedStoreFromConfig(config);
		else
			return newRemoteStoreFromConfig(config);
	}
	
	private BaseSOLRStore newEmbeddedStoreFromConfig(ISOLRStoreConfiguration config) throws Exception {
		BaseSOLRStore store = newEmbeddedSOLRStore(config.getDataRoot(), config.getDbName());
		store.setCommitDelay(config.getCommitDelay());
		return store;
	}
	
	private BaseSOLRStore newRemoteStoreFromConfig(ISOLRStoreConfiguration config) throws Exception {
		BaseSOLRStore store = new RemoteSOLRStore(config.getProtocol(), config.getHost(), config.getPort(), config.getDbName());
		store.setCommitDelay(config.getCommitDelay());
		return store;
	}

	private BaseSOLRStore newEmbeddedSOLRStore(String root, String coreName) throws Exception {
		// this is test only, load class by name to avoid carrying SOLR jars with each executable
		@SuppressWarnings("unchecked")
		Class<? extends BaseSOLRStore> klass = (Class<? extends BaseSOLRStore>) Class.forName("prompto.store.solr.EmbeddedSOLRStore");
		Constructor<? extends BaseSOLRStore> ctor = klass.getConstructor(File.class, String.class);
		coreName = StringUtils.capitalizeFirst(coreName) + "Store";
		BaseSOLRStore store = ctor.newInstance(new File(root), coreName);
		Method method = klass.getDeclaredMethod("startContainer");
		method.invoke(store);
		method = klass.getDeclaredMethod("startServerWithEmptyCore");
		method.invoke(store);
		return store;
	}
}
