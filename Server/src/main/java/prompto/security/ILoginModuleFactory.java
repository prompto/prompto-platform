package prompto.security;

import javax.security.auth.spi.LoginModule;

public interface ILoginModuleFactory {

	LoginModule newLoginModule();

}
