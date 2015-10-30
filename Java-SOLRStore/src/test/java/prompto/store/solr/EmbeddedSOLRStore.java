package prompto.store.solr;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;

public class EmbeddedSOLRStore extends BaseSOLRStore {

	File root;
	CoreContainer container;
	SolrCore core;
	EmbeddedSolrServer server;

	public EmbeddedSOLRStore(File root) {
		this.root = root;
	}

	public void startContainer() {
		if(container==null) {
			CoreContainer container = new CoreContainer(root.getAbsolutePath());
			container.load();
			this.container = container;
		}
	}

	public void startServerWithEmptyCore(String coreName) throws IOException {
		if(server==null) {
			File coreDir = new File(root, coreName);
			if(coreDir.exists())
				delete(coreDir);
			File confDir = new File(coreDir, "conf");
			confDir.mkdir();
			copyResourceToFile("solr/solrconfig.xml", new File(confDir, "solrconfig.xml"));
			copyResourceToFile("solr/emptyschema.xml", new File(confDir, "schema.xml"));
			copyResourceToFile("solr/stopwords.txt", new File(confDir, "stopwords.txt"));
			CoreDescriptor cd = new CoreDescriptor(container, coreName, coreDir.getAbsolutePath());
			core = container.create(cd);
			server = new EmbeddedSolrServer(container, coreName);
		}
	}
	
	private void delete(File file) {
		if(!file.exists())
			return;
		if(file.isDirectory()) for(String name : file.list())
			delete(new File(file, name));
		file.delete();
	}

	private void copyResourceToFile(String resourcePath, File destination) throws IOException {
		URL inputUrl = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
		FileUtils.copyURLToFile(inputUrl, destination);	
	}

	public void shutdownServer() {
		if(server!=null) {
			server.shutdown();
			server = null;
		}
	}
	
	public void shutdownCore() {
		if(core!=null) {
			if(!core.isClosed())
				core.close();
			core = null;
		}
	}
	
	public void shutdownContainer() {
		if(container!=null) {
			if(!container.isShutDown())
				container.shutdown();
			container = null;
		}
	}

	@Override
	public QueryResponse query(ModifiableSolrParams params) throws SolrServerException {
		return server.query(params);
	}

	@Override
	public void addDocument(SolrInputDocument doc) throws SolrServerException, IOException {
		server.add(doc);
	}

	@Override
	public void commit() throws SolrServerException, IOException {
		server.commit();
	}

	@Override
	public void addField(String fieldName, String fieldType, Map<String, Object> options) {
		IndexSchema schema = core.getLatestSchema();
		Object lock = schema.getSchemaUpdateLock();
		synchronized(lock) {
			SchemaField field = schema.newField(fieldName, fieldType, options);
			schema = schema.addField(field);
			schema.refreshAnalyzers();
		}
		core.setLatestSchema(schema);
	}
	
	@Override
	public String getFieldType(String fieldName) {
		IndexSchema schema = core.getLatestSchema();
		SchemaField field = schema.getField(fieldName);
		return field.getType().getTypeName() + (field.multiValued() ? "[]" : "");
	}
}