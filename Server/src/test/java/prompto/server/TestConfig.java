package prompto.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.Before;
import org.junit.Test;

import prompto.config.HttpConfiguration;
import prompto.config.IConfigurationReader;
import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.IKeyStoreFactoryConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.TempDirectories;
import prompto.config.YamlConfigurationReader;
import prompto.config.auth.IAuthenticationConfiguration;
import prompto.config.auth.source.IAuthenticationSourceConfiguration;
import prompto.runtime.Mode;
import prompto.security.IKeyStoreFactory;
import prompto.security.auth.source.IAuthenticationSource;
import prompto.security.auth.source.IAuthenticationSourceFactory;

public class TestConfig {
	
	@Before
	public final void __before__() throws IOException {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
	}

	@Test
	public void testFullYamlConfig() throws IOException {
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("configs/test-config.yml")) {
			assertNotNull(input);
			IConfigurationReader reader = new YamlConfigurationReader(input);
			IHttpConfiguration httpConfig = new HttpConfiguration(reader.getObject("http"));
			assertEquals("https", httpConfig.getProtocol());
			assertEquals(443, httpConfig.getPort());
			assertEquals("somewhere", httpConfig.getAllowedOrigins());
			// keystore
			IKeyStoreConfiguration keyStoreConfig = httpConfig.getKeyStoreConfiguration();
			checkKeyStoreConfiguration(keyStoreConfig);
			// truststore
			keyStoreConfig = httpConfig.getTrustStoreConfiguration();
			checkKeyStoreConfiguration(keyStoreConfig);
			// authentication
			IAuthenticationConfiguration authConfig = httpConfig.getAuthenticationConfiguration();
			IAuthenticationSourceConfiguration sourceConfig = authConfig.getAuthenticationSourceConfiguration();
			IAuthenticationSourceFactory factory = sourceConfig.getAuthenticationSourceFactory();
			IAuthenticationSource source = factory.newAuthenticationSource();
			assertNotNull(source);
		}
		
	}

	private void checkKeyStoreConfiguration(IKeyStoreConfiguration ksc) {
		assertNotNull(ksc);
		SslContextFactory ssl = new SslContextFactory();
		IKeyStoreFactoryConfiguration ksfc = ksc.getKeyStoreFactoryConfiguration();
		assertNotNull(ksfc);
		IKeyStoreFactory ksf = ksfc.getKeyStoreFactory();
		assertNotNull(ksf);
		ssl.setKeyStore(ksf.newInstance(ksfc));
		ISecretKeyConfiguration skf = ksc.getSecretKeyConfiguration();
		assertNotNull(skf);
		char[] secretKey = skf.getSecret();
		ssl.setKeyStorePassword(new String(secretKey));
	}

}
