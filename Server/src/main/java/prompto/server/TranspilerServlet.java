package prompto.server;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;

import prompto.config.TempDirectories;
import prompto.runtime.Mode;
import prompto.runtime.Standalone;
import prompto.transpiler.HtmlGenerator;
import prompto.utils.Logger;
import prompto.utils.YamlUtils;

@SuppressWarnings("serial")
public class TranspilerServlet extends CodeStoreServlet {

	static final Logger logger = new Logger();

	public TranspilerServlet() throws Exception {
		super();
	}
	
	@Override
	protected Resource getResource(HttpServletRequest request, String path) {
		File htmlFile = getTranspiledHtmlFile(path);
		// always rebuild in DEV/TEST mode
		if(htmlFile.exists() && Mode.get().ordinal()>=Mode.DEVELOPMENT.ordinal()) 
			htmlFile.delete();
		if(htmlFile.exists()) 
			return new PathResource(htmlFile);
		try {
			transpile(request, path, htmlFile);
			return new PathResource(htmlFile);
		} catch(Throwable t) {
			logger.error(()->"While transpiling '" + path + "'", t);
			return null;
		}
	}

	private File getTranspiledHtmlFile(String path) {
		File file = new File(TempDirectories.getTranspiledDir(), path.replace(".page", ".html"));
		File parent = file.toPath().getParent().toFile();
		if(!parent.exists()) {
			parent.mkdirs();
			if(!parent.exists())
				throw new RuntimeException("Could not creat dir at " + parent.getAbsolutePath());
		}
		return file;	
	}

	private void transpile(HttpServletRequest request, String path, File htmlFile) throws IOException {
		synchronized(this) {
			if(htmlFile.exists())
				return;
			Resource resource = super.getResource(request, path);
			if(!resource.exists())
				return;
			String userAgent = request.getHeader(HttpHeader.USER_AGENT.asString());
			Map<String, Object> pageConfig = YamlUtils.readResource(()->resource.getInputStream());
			HtmlGenerator generator = new HtmlGenerator(userAgent, pageConfig);
			generator.generate(Standalone.getGlobalContext(), htmlFile);
		}
	}

	
}
