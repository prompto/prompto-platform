package prompto.security;

import prompto.config.ISecretKeyConfiguration;

public interface IAwsKMSSecretKeyConfiguration extends ISecretKeyConfiguration {
	
	String getAwsRegion();
	String getAwsAccesKey();
	String getAwsSecretKey();
	
}