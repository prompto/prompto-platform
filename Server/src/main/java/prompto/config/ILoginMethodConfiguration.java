package prompto.config;

import prompto.security.ILoginMethodFactory;

@FunctionalInterface
public interface ILoginMethodConfiguration {

	ILoginMethodFactory  getLoginMethodFactory();

}
