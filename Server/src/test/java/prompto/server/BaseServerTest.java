package prompto.server;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.URLResource;
import org.junit.After;
import org.junit.Before;

import prompto.config.IConfigurationReader;
import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.IKeyStoreFactoryConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IServerConfiguration;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.memstore.MemStore;
import prompto.runtime.Standalone;
import prompto.security.IKeyStoreFactory;
import prompto.security.PlainSecretKeyFactory;
import prompto.utils.SocketUtils;

public abstract class BaseServerTest {
	
	protected int port = -1;
	boolean ssl = false;
	
	@Before
	public void __before__() throws Throwable {
		port = SocketUtils.findAvailablePortInRange(8000,  9000);
		IServerConfiguration config = getServerConfig();
		bootstrapCodeStore(config);
		AppServer.startServer(config.getHttpConfiguration(), null, null, null, this::prepareHandler, null);
		assertTrue(AppServer.isStarted());
	}
	
	protected IServerConfiguration getServerConfig() {
		return new IServerConfiguration.Inline()
			.withHttpConfiguration(ssl ? this.getHttpsConfiguration() : BaseServerTest.this.getHttpConfiguration())
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test")
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withTestMode(true)
			.withLoadRuntime(false);
	}

	protected IHttpConfiguration getHttpsConfiguration() {
		return new IHttpConfiguration.Inline()
				.withProtocol("https")
				.withPort(port)
				.withKeyStoreConfiguration( new IKeyStoreConfiguration() {

					@Override
					public IKeyStoreFactoryConfiguration getKeyStoreFactoryConfiguration() {
						return new IKeyStoreFactoryConfiguration() {

							@Override
							public IKeyStoreFactory getKeyStoreFactory() {
								return new IKeyStoreFactory() {
									
									@Override
									public KeyStore newInstance(IKeyStoreFactoryConfiguration config) {
										try {
											URL url = Thread.currentThread().getContextClassLoader().getResource("security/keystore_test.jks");
											Resource resource = URLResource.newResource(url);
											KeyStore ks = KeyStore.getInstance("JKS");
											ks.load(resource.getInputStream(), null);
											return ks;
										} catch(Exception e) {
											throw new RuntimeException(e);
										}
									}
									
									@Override public IKeyStoreFactoryConfiguration newConfiguration(IConfigurationReader reader) { return null; }
								};
							}
						};
					}
					
					@Override
					public ISecretKeyConfiguration getSecretKeyConfiguration() {
						return new ISecretKeyConfiguration() {
							@Override public String getFactory() { return PlainSecretKeyFactory.class.getName(); }
							@Override public char[] getSecret() { return "password".toCharArray(); }
						};
					}
					
				})
				.withTrustStoreConfiguration(new IKeyStoreConfiguration() {

					@Override
					public IKeyStoreFactoryConfiguration getKeyStoreFactoryConfiguration() {
						return new IKeyStoreFactoryConfiguration() {

							@Override
							public IKeyStoreFactory getKeyStoreFactory() {
								return new IKeyStoreFactory() {
									
									@Override
									public KeyStore newInstance(IKeyStoreFactoryConfiguration config) {
										try {
											URL url = Thread.currentThread().getContextClassLoader().getResource("security/truststore_test.jks");
											Resource resource = URLResource.newResource(url);
											KeyStore ks = KeyStore.getInstance("JKS");
											ks.load(resource.getInputStream(), null);
											return ks;
										} catch(Exception e) {
											throw new RuntimeException(e);
										}
									}
									
									@Override public IKeyStoreFactoryConfiguration newConfiguration(IConfigurationReader reader) { return null; }
								};
							}
						};
					}
					
					@Override
					public ISecretKeyConfiguration getSecretKeyConfiguration() {
						return new ISecretKeyConfiguration() {
							@Override public String getFactory() { return PlainSecretKeyFactory.class.getName(); }
							@Override public char[] getSecret() { return "password".toCharArray(); }
						};
					}
				});
	}

	protected IHttpConfiguration getHttpConfiguration() {
		return new IHttpConfiguration.Inline()
			.withProtocol("http")
			.withPort(port); 
	}

	public Handler prepareHandler() {
		return prepareHandler(ssl);
	}
	
	
	public static Handler prepareHandler(boolean ssl) {
		try {
			HandlerList list = new HandlerList();
			if(ssl)
				list.addHandler(new SecuredRedirectHandler());
			list.addHandler(getResourceHandler());
			list.addHandler(getServiceHandler());
			list.addHandler(new DefaultHandler());
			return list;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Handler getResourceHandler() throws Exception {
		return AppServer.newResourceHandler("/", null);
	}

	private static Handler getServiceHandler() throws MalformedURLException {
		URL thisPath = BaseServerTest.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println("Loading classes from: " + thisPath);
		return AppServer.newServiceHandler("/ws", thisPath, false);
	}

	public static void bootstrapCodeStore(IServerConfiguration config) throws Exception {
		Standalone.bootstrapCodeStore(new MemStore(), config);
	}

	@After
	public void __after__() throws Exception {
		port = -1;
		if(AppServer.isStarted()) {
			AppServer.stop();
			Thread.sleep(100);
		}
	}
	

}
