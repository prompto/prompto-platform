package prompto.server;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.URLResource;
import org.junit.After;
import org.junit.Before;

import prompto.code.Version;
import prompto.config.IDebugConfiguration;
import prompto.config.IHttpConfiguration;
import prompto.config.IKeyStoreConfiguration;
import prompto.config.IKeyStoreConfigurator;
import prompto.config.ILoginConfiguration;
import prompto.config.IServerConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.libraries.Libraries;
import prompto.memstore.MemStore;
import prompto.runtime.Standalone;
import prompto.security.IPasswordFactory;

public abstract class BaseServerTest {
	
	int port = -1;
	boolean ssl = false;
	
	@Before
	public void __before__() throws Throwable {
		IServerConfiguration config = getServerConfig(port);
		bootstrapCodeStore(config);
		port = AppServer.startServer(config.getHttpConfiguration(), null, null, null, ()->prepareHandler(null), null);
		assertTrue(AppServer.isStarted());
	}
	
	protected IServerConfiguration getServerConfig(int port) {
		return new IServerConfiguration() {
			@Override public void setRuntimeLibsSupplier(Supplier<Collection<URL>> supplier) { }
			@Override public boolean isTestMode() { return true; }
			@Override public boolean isLoadRuntime() { return false; }
			@Override public Supplier<Collection<URL>> getRuntimeLibsSupplier() { return ()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class); }
			@Override public URL[] getResourceURLs() { return null; }
			@Override public IDebugConfiguration getDebugConfiguration() { return null; }
			@Override public IStoreConfiguration getDataStoreConfiguration() { return null; }
			@Override public IStoreConfiguration getCodeStoreConfiguration() { return null; }
			@Override public Map<String, String> getArguments() { return null; }
			@Override public Version getApplicationVersion() { return Version.parse("1.0.0"); }
			@Override public String getApplicationName() { return "test"; }
			@Override public URL[] getAddOnURLs() { return null; }
			@Override public IHttpConfiguration getHttpConfiguration() { return ssl ? getHttpsConfiguration() : BaseServerTest.this.getHttpConfiguration(); }
			@Override public String getServerAboutToStartMethod() { return null; }
			@Override public String getWebSiteRoot() { return null; }
		};
	}

	protected IHttpConfiguration getHttpsConfiguration() {
		return new IHttpConfiguration() {
			@Override public String getProtocol() { return "https"; }
			@Override public int getPort() { return port; }
			@Override public String getAllowedOrigin() { return null; }
			@Override public ILoginConfiguration getLoginConfiguration() { return null; }
			@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { 
				return new IKeyStoreConfiguration() {

					@Override
					public IKeyStoreConfigurator getConfigurator() {
						return (factory) -> {
									URL url = Thread.currentThread().getContextClassLoader().getResource("security/keystore_test.jks");
									Resource resource = URLResource.newResource(url);
									factory.setKeyStoreResource(resource);
								};
					}

					@Override
					public IPasswordFactory getPasswordFactory() {
						return () -> "password";
					}
					
				}; }
			@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { 
				return new IKeyStoreConfiguration() {

					@Override
					public IKeyStoreConfigurator getConfigurator() {
						return (factory) ->  {
							URL url = Thread.currentThread().getContextClassLoader().getResource("security/truststore_test.jks");
							Resource resource = URLResource.newResource(url);
							factory.setTrustStoreResource(resource);
						};
					}

					@Override
					public IPasswordFactory getPasswordFactory() {
						return () -> "password";
					}
					
				};
			}
		};
	}

	protected IHttpConfiguration getHttpConfiguration() {
		return new IHttpConfiguration() {
			@Override public String getProtocol() { return "http"; }
			@Override public int getPort() { return port; }
			@Override public String getAllowedOrigin() { return null; }
			@Override public IKeyStoreConfiguration getKeyStoreConfiguration() { return null; }
			@Override public IKeyStoreConfiguration getTrustStoreConfiguration() { return null; }
			@Override public ILoginConfiguration getLoginConfiguration() { return null; }
		};
	}

	public static Handler prepareHandler(String webSite) {
		try {
			HandlerList list = new HandlerList();
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

	private static Handler getServiceHandler() {
		String thisPath = BaseServerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.println("Loading classes from: " + thisPath);
		return AppServer.newServiceHandler("/ws", thisPath);
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
