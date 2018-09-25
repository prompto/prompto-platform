package prompto.codeserver;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import prompto.code.ICodeStore;
import prompto.code.ImmutableCodeStore;
import prompto.code.Module;
import prompto.code.ModuleType;
import prompto.utils.Logger;
import prompto.value.Image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModuleImporter {

	static Logger logger = new Logger();

	Module module;
	URL imageResource;
	URL codeResource;
	
	public ModuleImporter(Module module, URL imageResource, URL codeResource) {
		this.module = module;
		this.imageResource = imageResource;
		this.codeResource = codeResource;
	}
	
	public ModuleImporter(String resourcePath) {
		this(Thread.currentThread().getContextClassLoader().getResource(resourcePath));
	}
	
	public ModuleImporter(URL url) {
		try {
			JsonNode descriptor = readDescriptor(url);
			Module module = createModule(descriptor);
			populateModule(module, descriptor);
			populateResources(url, descriptor);
			// done
			this.module = module;
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}

	private void populateResources(URL url, JsonNode descriptor) throws MalformedURLException {
		if(descriptor.get("imageResource")!=null)
			this.imageResource = new URL(url, descriptor.get("imageResource").asText());
		if(descriptor.get("codeResource")!=null)
			this.codeResource = new URL(url, descriptor.get("codeResource").asText());
	}

	private void populateModule(Module module, JsonNode descriptor) throws Exception {
		ModulePopulator populator = ModulePopulator.forType(module);
		populator.populate(module, descriptor);
	}
	
	

	private Module createModule(JsonNode descriptor) throws InstantiationException, IllegalAccessException {
		String typeName = descriptor.get("type").asText();
		ModuleType type = ModuleType.valueOf(typeName);
		return type.getModuleClass().newInstance();
	}

	private JsonNode readDescriptor(URL path) throws JsonProcessingException, IOException {
		URL json = new URL(path, "module.json");
		try(InputStream input = json.openStream()) {
			return new ObjectMapper().readTree(input);
		}
	}

	public boolean importModule(ICodeStore codeStore) throws Exception {
		Module existing = codeStore.fetchModule(module.getType(), module.getName(), module.getVersion());
		if(existing!=null)
			return false;
		logger.info(()->"Importing module: " + module.getName() + " - " + module.getVersion());
		if(imageResource!=null)
			module.setImage(Image.fromURL(imageResource).getStorableData());
		codeStore.storeModule(module);	
		if(codeResource!=null)
			storeAssociatedCode(codeStore, codeResource);
		return true;
	}

	private void storeAssociatedCode(ICodeStore codeStore, URL codeResource) throws Exception {
		ImmutableCodeStore rcs = new ImmutableCodeStore(null, module.getType(), codeResource, module.getVersion());
		codeStore.storeDeclarations(rcs.getDeclarations(), rcs.getModuleDialect(), module.getVersion(), module.getDbId());
	}

}
