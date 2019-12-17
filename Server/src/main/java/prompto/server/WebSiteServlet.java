package prompto.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;


@SuppressWarnings("serial")
public class WebSiteServlet extends ResourceServlet {

	Resource base;
	
	public WebSiteServlet(String root, String welcomePage) throws IOException {
		super(welcomePage);
		this.base = getResourceBase(root);
	}

	private Resource getResourceBase(String webSite) throws IOException {
		File file = new File(webSite);
		logger.info(()->"Serving web site at: " + file.getAbsolutePath());
		if(!file.exists())
			throw new FileNotFoundException(webSite);
		return Resource.newResource(file.getCanonicalFile());
	}


	@Override
	protected Resource getResource(HttpServletRequest request, String path) {
	   	final String realPath = URIUtil.canonicalPath(path);
        try {
            Resource resource = base.addPath(realPath);
        	if(resource!=null && resource.exists())
        		return resource;
        	else
        		return null;
        } catch(Throwable t) {
    	   logger.error(()->"While looking for resource: " + realPath, t);
    	   return null;
       }
	}

}
