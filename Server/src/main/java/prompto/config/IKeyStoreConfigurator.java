package prompto.config;

import org.eclipse.jetty.util.ssl.SslContextFactory;

@FunctionalInterface
public interface IKeyStoreConfigurator {

	void configure(SslContextFactory factory);

}
