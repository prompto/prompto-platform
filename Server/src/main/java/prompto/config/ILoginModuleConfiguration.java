package prompto.config;

import prompto.security.ILoginModuleFactory;

@FunctionalInterface
public interface ILoginModuleConfiguration {

	ILoginModuleFactory getLoginModuleFactory();

}
