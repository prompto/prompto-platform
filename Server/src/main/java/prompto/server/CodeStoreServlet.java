package prompto.server;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.resource.Resource;

import prompto.code.ICodeStore;
import prompto.code.ResourceReader;
import prompto.store.AttributeInfo;
import prompto.store.DataStore;
import prompto.store.Family;
import prompto.store.IQueryBuilder;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStore;
import prompto.store.IStored;


@SuppressWarnings("serial")
public class CodeStoreServlet extends ResourceServlet {

	public CodeStoreServlet(String welcomePage, String siteMap) {
		super(welcomePage, siteMap);
	}

	@Override
	protected Resource getResource(HttpServletRequest request, String path) {
		if(path.equals("/stub"))
			return getStubResource(request, path);
		else if(path.equalsIgnoreCase("/sitemap.xml"))
			return getSiteMapResource(request);
		else
			return getCodeResource(request, path);
	}
	
	private Resource getSiteMapResource(HttpServletRequest request) {
		if("GENERATED".equals(siteMap)) {
			String urlPrefix = request.getRequestURL().toString();
			return SiteMapResource.fromWebSitePages(urlPrefix.substring(0, 1 + urlPrefix.indexOf("/sitemap.xml")));
		} else
			return getCodeResource(request, siteMap);
	}

	protected Resource getStubResource(HttpServletRequest request, String path) {
		try {
			String moduleId = request.getParameter("moduleId");
			String resourceName = request.getParameter("resourceName");
			IStore store = DataStore.getInstance();
			IQueryBuilder builder = store.newQueryBuilder()
					.verify(new AttributeInfo("module", Family.CATEGORY, false, null), MatchOp.EQUALS, store.convertToDbId(moduleId))
					.verify(new AttributeInfo("name", Family.TEXT, false, null), MatchOp.EQUALS, resourceName)
					.and();
			IStored stored = store.fetchOne(builder.build());
			prompto.code.Resource res = ResourceReader.readResource(stored);
			return res==null ? null : new CodeStoreResource(res);
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	protected Resource getCodeResource(HttpServletRequest request, String path) {
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
