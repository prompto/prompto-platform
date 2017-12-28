package prompto.config;

import prompto.security.ILoginMethodFactory;
import prompto.security.ILoginModuleFactory;


public interface ILoginConfiguration {

	ILoginModuleFactory getLoginModuleFactory();
	ILoginMethodFactory  getLoginMethodFactory();

}
