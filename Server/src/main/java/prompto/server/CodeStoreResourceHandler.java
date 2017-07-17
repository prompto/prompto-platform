package prompto.server;

import java.net.MalformedURLException;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

import prompto.code.ICodeStore;

public class CodeStoreResourceHandler extends ResourceHandler {

	@Override
	public Resource getResource(String path) throws MalformedURLException {
		Resource r = super.getResource(path);
		if(r!=null && r.exists())
			return r;
		else
			return getStoredResource(path.substring(1));
	}

	private Resource getStoredResource(String path) {
		try {
			ICodeStore store = ICodeStore.getInstance();
			prompto.code.Resource res = store.fetchLatestResource(path);
			return res==null ? null : new CodeStoreResource(res);
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	
}
