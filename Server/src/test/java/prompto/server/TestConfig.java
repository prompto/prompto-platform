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
import prompto.runtime.Mode;
import prompto.security.IKeyStoreFactory;

public class TestConfig {
	
	@Before
	public final void __before__() throws IOException {
		TempDirectories.create();
		Mode.set(Mode.UNITTEST);
	}

	@Test
	public void testFullYamlConfig() throws IOException {
		try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.yml")) {
			assertNotNull(input);
			IConfigurationReader reader = new YamlConfigurationReader(input);
			IHttpConfiguration config = new HttpConfiguration(reader.getObject("http"));
			assertEquals("https", config.getProtocol());
			assertEquals(443, config.getPort());
			assertEquals("somewhere", config.getAllowedOrigins());
			// keystore
			IKeyStoreConfiguration ksc = config.getKeyStoreConfiguration();
			checkKeyStoreConfiguration(ksc);
			// truststore
			ksc = config.getTrustStoreConfiguration();
			checkKeyStoreConfiguration(ksc);
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
