package prompto.config;

import prompto.security.ILoginModuleFactory;


public interface ILoginConfiguration {

	ILoginModuleFactory getLoginModuleFactory();

}
