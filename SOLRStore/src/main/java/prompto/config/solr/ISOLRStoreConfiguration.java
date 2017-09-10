package prompto.config.solr;

import prompto.config.IStoreConfiguration;

public interface ISOLRStoreConfiguration extends IStoreConfiguration {

	boolean isEmbedded();
	String getProtocol();
	String getDataRoot();
	int getCommitDelay();

}
