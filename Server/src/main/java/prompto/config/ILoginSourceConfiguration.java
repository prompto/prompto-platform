package prompto.config;

import prompto.security.ILoginSourceFactory;

@FunctionalInterface
public interface ILoginSourceConfiguration {

	ILoginSourceFactory getLoginSourceFactory();

}
