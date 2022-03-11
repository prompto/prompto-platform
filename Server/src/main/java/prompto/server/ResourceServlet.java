package prompto.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

import prompto.libraries.Libraries;
import prompto.utils.Logger;
import prompto.utils.ObjectUtils;

@SuppressWarnings("serial")
public abstract class ResourceServlet extends CleverServlet {

	static final Logger logger = new Logger();
	static final MimeTypes mimeTypes = new MimeTypes();
   
    int minMemoryMappedContentLength = 1024;
    int minAsyncContentLength = 0;
    Resource builtIns;
    String welcomePage;
    String siteMap;
    
    public ResourceServlet(String welcomePage, String siteMap) {
    	builtIns = getBuiltInsResource();
    	this.welcomePage = welcomePage!=null ? welcomePage :  "/index.html";
    	this.siteMap = siteMap!=null ? siteMap :  "GENERATED";
	}
    
	protected abstract Resource getResource(HttpServletRequest request, String resourcePath);

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Thread.currentThread().setName(this.getClass().getSimpleName());

		boolean writeBody = false;
		
		switch(request.getMethod().toUpperCase()) {
			case "GET":
			case "POST": // required for auth error page
				writeBody = true;
			case "HEAD":
				break;
			default:
                super.service(request, response);
                return;
		}
		
		boolean tryGzip = writeBody && acceptsGzip(request);

		Resource resource = getResource(request, tryGzip);
        if (resource==null || !resource.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        writeHeaders(response, resource);
        if(writeBody)
        	writeBody(request, response, resource);
	}
	
	private boolean acceptsGzip(HttpServletRequest request) {
		String accept = request.getHeader(HttpHeader.ACCEPT_ENCODING.asString());
 		return accept!=null && accept.contains("gzip");
	}

	protected Resource getResource(HttpServletRequest request, boolean tryGzip) {
        String servletPath;
        String pathInfo;
        Boolean included = request.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI) != null;
        if (included != null && included.booleanValue())
        {
            servletPath = (String)request.getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH);
            pathInfo = (String)request.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);

            if (servletPath == null && pathInfo == null)
            {
                servletPath = request.getServletPath();
                pathInfo = request.getPathInfo();
            }
        }
        else
        {
            servletPath = request.getServletPath();
            pathInfo = request.getPathInfo();
        }
        String pathInContext = URIUtil.addPaths(servletPath, pathInfo);
        if(pathInContext=="/" && welcomePage!=null) {
        	if(welcomePage.endsWith(".page"))
        		return new TranspilerServlet().getResource(request, welcomePage);
        	else
        		pathInContext = welcomePage;
        }
        // don't auto close resource since it may be used asynchronously
        Resource resource = null;
        if(tryGzip)
        	resource = getClassPathResource(pathInContext + ".gz");
        if(resource!=null)
        	return resource;
    	resource = getClassPathResource(pathInContext);
        if(resource!=null)
        	return resource;
        else
        	return getResource(request, pathInContext);
	}

        
    @SuppressWarnings("resource")
	private void writeBody(HttpServletRequest request, HttpServletResponse response, Resource resource) throws IOException {
 		OutputStream out = response.getOutputStream();
    	if(out instanceof HttpOutput)
    		writeBody(request, response, (HttpOutput)out, resource);
    	else
    		resource.writeTo(out, 0, resource.length());
    }
	
	
	private void writeBody(HttpServletRequest request, HttpServletResponse response, HttpOutput out, Resource resource) throws IOException {
        int minAsyncSize = minAsyncContentLength==0 ? response.getBufferSize() : minAsyncContentLength;
        if (request.isAsyncSupported() &&  minAsyncSize > 0 && resource.length() >= minAsyncSize)
        	writeBodyAsync(request, out, resource, minAsyncSize);
        else {
           	writeBodySync(request, out, resource);
           	resource.close();
           	out.close();
        }
	}
	
	@SuppressWarnings("resource")
	private void writeBodyAsync(HttpServletRequest request, HttpOutput out, Resource resource, int minAsyncSize) throws IOException {
        final AsyncContext async = request.startAsync();
        async.setTimeout(0);
        Callback callback = new Callback()
        {
            @Override
            public void succeeded()
            {
                async.complete();
                resource.close();
                out.close();
            }

            @Override
            public void failed(Throwable x)
            {
                logger.warn(()->x.toString());
                logger.debug(()->x.toString(), x);
                async.complete();
                resource.close();
                out.close();
             }   
        };
        if(canUseMemoryMappedFile(resource, true)) {
            ByteBuffer buffer = BufferUtil.toMappedBuffer(resource.getFile());
            out.sendContent(buffer,callback);
        } else {
        	// Do a blocking write of a channel (if available) or input stream
            // Close of the channel/inputstream is done by the async sendContent
            ReadableByteChannel channel= resource.getReadableByteChannel();
            if (channel!=null)
                out.sendContent(channel,callback);
            else
                out.sendContent(resource.getInputStream(),callback);
        }
	}
	
	private boolean canUseMemoryMappedFile(Resource resource, boolean limitToMaxInt) {
		long length = resource.length();
		return minMemoryMappedContentLength > 0 
				&& length > minMemoryMappedContentLength 
				&& resource instanceof PathResource
				&& (!limitToMaxInt || length<Integer.MAX_VALUE);
	}

	@SuppressWarnings("resource")
	private void writeBodySync(HttpServletRequest request, HttpOutput out, Resource resource) throws IOException {
        if (canUseMemoryMappedFile(resource, false)) {
            ByteBuffer buffer = BufferUtil.toMappedBuffer(resource.getFile());
            out.sendContent(buffer);
        } else  {
        	// Do a blocking write of a channel (if available) or input stream
        	ReadableByteChannel channel= resource.getReadableByteChannel();
            if (channel!=null)
                out.sendContent(channel);
            else
                out.sendContent(resource.getInputStream());
        }
	}

	protected void writeHeaders(HttpServletResponse response, Resource resource) {
		writeContentType(response, resource);
		writeContentLength(response, resource);
		writeContentEncoding(response, resource);
		writeCacheControl(response, resource);
		writeEtags(response, resource);
     }

	private void writeContentEncoding(HttpServletResponse response, Resource resource) {
		if(resource.getName().endsWith(".gz")) {
			response.setHeader(HttpHeader.CONTENT_ENCODING.asString(), "gzip");
			if(resource.getName().endsWith(".js.gz"))
				response.setContentType("application/javascript");
		}
	}

	private void writeContentType(HttpServletResponse response, Resource resource) {
		if(resource instanceof MimeTypeProvider) {
			String mimeType = ((MimeTypeProvider)resource).getMimeType();
			if (mimeType!=null)
	            response.setContentType(mimeType);
		} else {
	       String mimeType = mimeTypes.getMimeByExtension(resource.toString());
	        if (mimeType!=null)
	            response.setContentType(mimeType);
		}
	}
	
	private void writeContentLength(HttpServletResponse response, Resource resource) {
        long length = resource.length();
        if (length>Integer.MAX_VALUE)
            response.setHeader(HttpHeader.CONTENT_LENGTH.asString(),Long.toString(length));
        else if (length>0)
            response.setContentLength((int)length);
	}

	private void writeCacheControl(HttpServletResponse response, Resource resource) {
        /* TODO
        if (_cacheControl!=null)
            response.setHeader(HttpHeader.CACHE_CONTROL.asString(),_cacheControl);
        */
	}

	private void writeEtags(HttpServletResponse response, Resource resource) {
        /* TODO
        long last_modified=resource.lastModified();
        String etag=null;
        if (_etags)
        {
            // simple handling of only a single etag
            String ifnm = request.getHeader(HttpHeader.IF_NONE_MATCH.asString());
            etag=resource.getWeakETag();
            if (ifnm!=null && resource!=null && ifnm.equals(etag))
            {
                response.setStatus(HttpStatus.NOT_MODIFIED_304);
                baseRequest.getResponse().getHttpFields().put(HttpHeader.ETAG,etag);
                return;
            }
        }
        
        // Handle if modified since 
        if (last_modified>0)
        {
            long if_modified=request.getDateHeader(HttpHeader.IF_MODIFIED_SINCE.asString());
            if (if_modified>0 && last_modified/1000<=if_modified/1000)
            {
                response.setStatus(HttpStatus.NOT_MODIFIED_304);
                return;
            }
        }
        */
    }

	private Resource getClassPathResource(String path) {
    	final String realPath = URIUtil.canonicalPath(path);
        try {
        	Resource resource = builtIns.addPath(realPath);
        	if(resource!=null && resource.exists())
        		return resource;
        	else
        		return null;
       } catch(Throwable t) {
    	   logger.error(()->"While looking for resource: " + realPath, t);
    	   return null;
       }
 	}


	private Resource getBuiltInsResource() {
		Stream<Class<?>> classes = Stream.concat(ObjectUtils.getClassesInCallStack().stream(), Stream.of(AppServer.class, Libraries.class));
		Set<Resource> resources = classes
				.filter(c->c.getName().startsWith("prompto"))
				.map(this::getClassResource)
				.collect(Collectors.toSet());
		resources.forEach(res->logger.info(()->"Adding resource root: " + res.toString()));
		return new ResourceCollection(resources.toArray(new Resource[resources.size()]));
	}
	
	private Resource getClassResource(Class<?> klass) {
		try {
			URL root = getClassResourceURL(klass);
			return Resource.newResource(root);
		} catch(Exception e) {
			logger.error(()->"Unable to load resources from " + klass.getName(), e);
			throw new RuntimeException(e);
		}
	}

	private URL getClassResourceURL(Class<?> klass) throws MalformedURLException {
		URL root = klass.getProtectionDomain().getCodeSource().getLocation();
		if(root.toExternalForm().endsWith(".jar"))
			root = new URL("jar:" + root.toExternalForm() + "!/");
		return root;
	}

	

}
