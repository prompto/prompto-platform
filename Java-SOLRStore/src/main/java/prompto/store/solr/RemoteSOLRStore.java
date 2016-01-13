package prompto.store.solr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

import prompto.error.InternalError;
import prompto.error.PromptoError;
import prompto.store.IStoreFactory.Type;
import prompto.utils.Utils;


public class RemoteSOLRStore extends BaseSOLRStore {
	
	SolrClient client;
	String coreName;
	
	public RemoteSOLRStore(String protocol, String host, int port, Type type) {
		this(protocol, host, port, Utils.capitalizeFirst(type.name()));
	}

	public RemoteSOLRStore(String protocol, String host, int port, String coreName) {
		String baseURL =  protocol + "://" + host + ":" + port + "/solr/";
		this.client = new HttpSolrClient(baseURL);
		this.coreName = coreName;
	}

	@Override
	public void createCoreIfRequired() throws SolrServerException, IOException {
		if(!coreExists())
			createCore();
	}
	
	@Override
	public void dropCoreIfExists() throws SolrServerException, IOException {
		if(coreExists())
			CoreAdminRequest.unloadCore(coreName, true, true, client);
	}
	
	private void createCore() throws SolrServerException, IOException {
		CoreAdminRequest.Create create = new CoreAdminRequest.Create();
		create.setCoreName(coreName);
		create.setConfigSet(coreName);
		create.process(client);
	}

	private boolean coreExists() throws SolrServerException, IOException {
		CoreAdminRequest request = new CoreAdminRequest();
		request.setAction(CoreAdminAction.STATUS);
		CoreAdminResponse response = request.process(client);
		return response.getCoreStatus(coreName)!=null;
	}

	@Override
	public void deleteOne(Object dbId) throws PromptoError {
		try {
			client.deleteById(coreName, String.valueOf(dbId));
		} catch(IOException | SolrServerException e) {
			throw new InternalError(e);
		}
	}
	
	@Override
	public void deleteAll() throws PromptoError {
		try {
			client.deleteByQuery(coreName, "*:*");
		} catch(IOException | SolrServerException e) {
			throw new InternalError(e);
		}
	}
	
	@Override
	public QueryResponse query(SolrQuery query) throws SolrServerException, IOException {
		return client.query(coreName, query);
	}
	
	@Override
	public void addDocument(SolrInputDocument doc) throws SolrServerException, IOException {
		client.add(coreName, doc);
	}
	
	@Override
	public void commit() throws SolrServerException, IOException {
		client.commit(coreName);
	}

	@Override
	public boolean hasField(String fieldName) throws SolrServerException, IOException {
		SchemaRequest.Field getField = new SchemaRequest.Field(fieldName);
		SchemaResponse.FieldResponse response = getField.process(client, coreName);
	    Map<String, Object> field = response.getField();
		return field!=null && fieldName.equals(field.get("name"));
	}
	
	@Override
	public void addField(String fieldName, String fieldType, Map<String, Object> options) throws SolrServerException, IOException {
		Map<String, Object> props = new HashMap<>(options);
		props.put("name", fieldName);
		props.put("type", fieldType);
		SchemaRequest.AddField addField = new SchemaRequest.AddField(props);
		addField.process(client, coreName);
	}
	
	@Override
	public String getFieldType(String fieldName) throws SolrServerException, IOException {
		SchemaRequest.Field getField = new SchemaRequest.Field(fieldName);
		SchemaResponse.FieldResponse response = getField.process(client, coreName);
	    Map<String, Object> field = response.getField();
		return String.valueOf(field.get("type"));
	}
	
	@Override
	public void dropField(String fieldName) throws SolrServerException, IOException {
		SchemaRequest.DeleteField dropField = new SchemaRequest.DeleteField(fieldName);
		dropField.process(client, coreName);
	}
	
	public void dropUserFields() throws SolrServerException, IOException {
		SchemaRequest.Fields getFields = new SchemaRequest.Fields();
		SchemaResponse.FieldsResponse response = getFields.process(client, coreName);
		for(Map<String, Object> field : response.getFields()) {
			String name = String.valueOf(field.get("name"));
			if("dbId".equals(name) || "_version_".equals(name))
				continue;
			dropField(name);
		}
		
	}

}
