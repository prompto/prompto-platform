package prompto.server;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.resource.Resource;

import prompto.code.ICodeStore;


@SuppressWarnings("serial")
public class CodeStoreServlet extends ResourceServlet {

	public CodeStoreServlet() throws Exception {
		super();
	}

	@Override
	protected Resource getResource(HttpServletRequest request, String path) {
		try {
			if(path.startsWith("/"))
				path = path.substring(1);
			ICodeStore store = ICodeStore.getInstance();
			prompto.code.Resource res = store.fetchLatestResource(path);
			return res==null ? null : new CodeStoreResource(res);
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}


}
