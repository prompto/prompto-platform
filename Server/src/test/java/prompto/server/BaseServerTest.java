package prompto.server;

import static org.junit.Assert.*;

import java.net.URL;
import java.security.KeyStore;

import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.URLResource;
import org.junit.After;
import org.junit.Before;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

import prompto.code.BaseCodeStore;
import prompto.code.ICodeStore;
import prompto.config.IConfigurationReader;
import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.IKeyStoreFactoryConfiguration;
import prompto.config.ISecretKeyConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.TempDirectories;
import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.security.IKeyStoreFactory;
import prompto.security.PlainSecretKeyFactory;
import prompto.store.memory.MemStore;
import prompto.utils.SocketUtils;

public abstract class BaseServerTest {
	
	protected BaseCodeStore tail;
	protected int port = -1;
	boolean ssl = false;
	
	@Before
	public void __before__() throws Throwable {
		TempDirectories.create();
		port = SocketUtils.findAvailablePortInRange(8000,  9000);
		IServerConfiguration config = getServerConfig();
		AppServer.initialize(config);
		tail = findCodeStoreTail();
		AppServer.startServer(config, this::prepareHandlers, null, null, null);
		assertTrue(AppServer.isStarted());
	}
	
	@After
	public void __after__() throws Exception {
		if(tail!=null)
			tail.setNext(null);
		port = -1;
		if(AppServer.isStarted()) {
			AppServer.stop();
			Thread.sleep(100);
		}
	}
	
	public boolean isAlive() {
		return AppServer.isStarted();
	}
	
	public BaseCodeStore getTail() {
		return tail;
	}
	
	public int getPort() {
		return port;
	}

	private BaseCodeStore findCodeStoreTail() {
		ICodeStore store = ICodeStore.getInstance();
		while(store instanceof BaseCodeStore) {
			ICodeStore next = ((BaseCodeStore)store).getNext();
			if(next==null)
				return (BaseCodeStore)store;
			else
				store = next;	
		}
		return null;
	}
	
	protected IServerConfiguration getServerConfig() {
		return new IServerConfiguration.Inline()
			.withHttpConfiguration(ssl ? this.getHttpsConfiguration() : BaseServerTest.this.getHttpConfiguration())
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test")
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withLoadRuntime(false)
			.withRuntimeMode(Mode.UNITTEST);
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
							
							@Override
							public YamlMapping toYaml() throws YamlException {
								return null;
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
					
					@Override
					public YamlMapping toYaml() throws YamlException {
						return null;
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
							
							@Override
							public YamlMapping toYaml() {
								return null;
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
					
					@Override
					public YamlMapping toYaml() throws YamlException {
						return null;
					}
				});
	}

	protected IHttpConfiguration getHttpConfiguration() {
		return new IHttpConfiguration.Inline()
			.withProtocol("http")
			.withPort(port); 
	}

	public void prepareHandlers(JettyServer jetty, HandlerList list) {
		try {
			if(ssl)
				list.addHandler(new SecuredRedirectHandler());
			list.addHandler(jetty.newWebSiteHandler());
			list.addHandler(jetty.newWebApiHandler());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void bootstrapCodeStore(IServerConfiguration config) throws Exception {
		Standalone.bootstrapCodeStore(new MemStore(), config);
	}	

}
