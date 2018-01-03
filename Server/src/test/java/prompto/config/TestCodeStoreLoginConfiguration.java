package prompto.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import prompto.intrinsic.PromptoVersion;
import prompto.libraries.Libraries;
import prompto.runtime.Standalone;
import prompto.security.FormLoginMethodFactory;
import prompto.security.PasswordIsUserNameLoginSourceFactory;
import prompto.server.AppServer;
import prompto.store.mongo.BaseMongoTest;
import prompto.store.mongo.MongoUtils;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlMapping;
import com.mongodb.client.MongoCollection;

public class TestCodeStoreLoginConfiguration extends BaseMongoTest {

	@Before
	public void before() throws Exception {
		createStore("APPS");
		Standalone.bootstrapCodeStore(store, newRuntimeConfig());
	}
	
	private IRuntimeConfiguration newRuntimeConfig() {
		return new IRuntimeConfiguration.Inline()
			.withRuntimeLibs(()->Libraries.getPromptoLibraries(Libraries.class, AppServer.class))
			.withApplicationVersion(PromptoVersion.parse("1.0.0"))
			.withApplicationName("test")
			.withLoadRuntime(false)
			.withTestMode(true);
	}


	@Test
	public void storedAuthenticationSettingsCanBeRead() throws Exception {
		Object dbId = storeSettings();
		File file = generateYamlConfig(dbId);
		String[] args = { "-yamlConfigFile", file.getAbsolutePath() };
		IServerConfiguration config = AppServer.loadConfiguration(args);
		assertNotNull(config);
		IHttpConfiguration http = config.getHttpConfiguration();
		assertNotNull(http);
		ILoginConfiguration login = http.getLoginConfiguration();
		assertNotNull(login);
		ILoginMethodConfiguration method = login.getLoginMethodConfiguration();
		assertTrue(method.getLoginMethodFactory() instanceof FormLoginMethodFactory);
		ILoginSourceConfiguration source = login.getLoginSourceConfiguration();
		assertTrue(source.getLoginSourceFactory() instanceof PasswordIsUserNameLoginSourceFactory);
	}

	private File generateYamlConfig(Object dbId) throws Exception {
		YamlMapping login = new YamlMapping();
		login.setEntry("factory", CodeStoreLoginConfigurationFactory.class.getName());
		login.setEntry("dbId", dbId.toString());
		YamlMapping http = new YamlMapping();
		http.setEntry("login", login);
		YamlDocument settings = new YamlMapping();
		settings.setEntry("http", http);
		File file = File.createTempFile("settings-", ".yml");
		try(Writer writer = new FileWriter(file)) {
			YamlConfig config = new YamlConfig();
			config.writeConfig.setWriteClassname(WriteClassName.NEVER);
			config.writeConfig.setAutoAnchor(false);
			YamlWriter yaml = new YamlWriter(writer, config);
			yaml.write(settings);		
		}
		return file;
	}

	private Object storeSettings() throws IOException {
		MongoCollection<Document> collection = db.getCollection("instances");
		Document root = MongoUtils.readResource("config/app.json");
		String ref = (String)root.get("authenticationSettings");
		Document settings = MongoUtils.readResource(ref);
		ref = (String)settings.get("authenticationMethod");
		Document method = MongoUtils.readResource(ref);
		method.put("_id", UUID.randomUUID());
		collection.insertOne(method);
		settings.put("authenticationMethod", method.get("_id"));
		ref = (String)settings.get("authenticationSource");
		Document source = MongoUtils.readResource(ref);
		source.put("_id", UUID.randomUUID());
		collection.insertOne(source);
		settings.put("authenticationSource", source.get("_id"));
		settings.put("_id", UUID.randomUUID());
		collection.insertOne(settings);
		root.put("authenticationSettings", settings.get("_id"));
		root.put("_id", UUID.randomUUID());
		collection.insertOne(root);
		return root.get("_id");
	}

}
