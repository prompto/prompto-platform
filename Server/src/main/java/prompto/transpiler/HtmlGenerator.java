package prompto.transpiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import prompto.code.ICodeStore;
import prompto.declaration.CategoryDeclaration;
import prompto.declaration.WrappingWidgetDeclaration;
import prompto.declaration.IDeclaration;
import prompto.declaration.IWidgetDeclaration;
import prompto.runtime.Context;
import prompto.runtime.Standalone;
import prompto.utils.Logger;
import prompto.utils.YamlUtils;

public class HtmlGenerator {

	static final Logger logger = new Logger();
	
	String userAgent;
	Map<String, Object> pageConfig;
	
	public HtmlGenerator(String userAgent, Map<String, Object> pageConfig) {
		this.userAgent = userAgent;
		this.pageConfig = pageConfig;
	}

	public void generate(Context context, File htmlFile) throws IOException {
		try(OutputStream output = new FileOutputStream(htmlFile) ) {
			try(Writer writer = new OutputStreamWriter(output)) {
				try(PrintWriter printer = new PrintWriter(writer)) {
					generate(context, printer);
				}
			}
		}
	}

	private void generate(Context context, PrintWriter printer) throws IOException {
		generateProlog(printer);
		generateHeader(context, printer);
		generateBody(printer);
		generateEpilog(printer);
	}

	private void generateRenderBody(PrintWriter printer, String widgetName) {
		printer.println("<script>");
		printer.println("function renderBody() {");
		// TODO call htmlEngine
		printer.print("var widget = new ");
		printer.print(widgetName); 
		printer.println("();");
		printer.print("ReactDOM.render(widget.render(), document.getElementById('body'));");
		printer.println("}");
		printer.println("</script>");
	}

	private void generateProlog(PrintWriter printer) {
		printer.println("<!DOCTYPE html>");
		printer.println("<html>");
	}

	private void generateHeader(Context context, PrintWriter printer) throws IOException {
		printer.println("<head>");
		generateTitle(printer);
		generateIcon(printer);
		generatePromptoScripts(printer);
		generateLibraries(printer);
		String widgetName = generateWidgetScript(context, printer);
		if(widgetName!=null)
			generateRenderBody(printer, widgetName);
		printer.println("</head>");
	}

	private String generateWidgetScript(Context context, PrintWriter printer) {
		Map<String, Object> body = getBodyConfig();
		if(body==null)
			return null;
		Object value = body.get("widget");
		if(value==null) {
			logger.warn(()->"Expected a 'widget' key");
			return null;
		} else if(value instanceof String)
			return generateWidgetScript(context, printer, (String)value);
		else {
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
			return null;
		}
	}
	
	
	
	private String generateWidgetScript(Context context, PrintWriter printer, String widgetName) {
		Iterable<IDeclaration> decls = ICodeStore.getInstance().fetchLatestDeclarations(widgetName);
		if(decls==null) {
			logger.warn(()->"No such declaration '"+ widgetName + "'!");
			return null;
		}
		Iterator<IDeclaration> iter = decls.iterator();
		if(!iter.hasNext()) {
			logger.warn(()->"No such declaration '"+ widgetName + "'!");
			return null;
		}
		IDeclaration decl = iter.next();
		if(decl instanceof CategoryDeclaration && !((CategoryDeclaration)decl).isAWidget(context)) {
			logger.warn(()->"Not a widget '"+ widgetName + "'!");
			return null;
		}
		IWidgetDeclaration widget = decl instanceof IWidgetDeclaration ? (IWidgetDeclaration)decl : new WrappingWidgetDeclaration((CategoryDeclaration)decl);
		generateWidgetScript(printer, widget);
		return widgetName;
	}


	

	private void generateWidgetScript(PrintWriter printer, IWidgetDeclaration declaration) {
		IJSEngine engine = IJSEngine.forUserAgent(userAgent);
		Context context = Standalone.getGlobalContext();
		Transpiler transpiler = new Transpiler(engine, context);
		declaration.declare(transpiler);
		printer.println("<script id='transpiled'>");
		transpiler.print(printer);
		printer.println("</script>");
	}

	private void generatePromptoScripts(PrintWriter printer) {
		printer.println("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/axios/0.17.1/axios.js\"></script>");
	}

	private void generateLibraries(PrintWriter printer) throws IOException {
		Map<String, Object> header = getHeaderConfig();
		if(header==null)
			return;
		if(!generateWidgetLibrary(printer, header)) {
			generateHtmlEngine(printer, header);
			generateUiFramework(printer, header);
		}
		generateStyleSheets(printer, header);
		generateJavascripts(printer, header);
	}
	
	@SuppressWarnings("unchecked")
	private void generateStyleSheets(PrintWriter printer, Map<String, Object> config) {
		Object value = config.get("styleSheets");
		if(value==null)
			return;
		else if(value instanceof Collection) {
			((Collection<String>)value).forEach(item->{
				if(item instanceof String) {
					printer.print("<link href=\"");
					printer.print(item);
					printer.println("\" rel=\"stylesheet\"/>");
				} else {
					logger.warn(()->"Expected a String, got " + value.getClass().getName());
				}
			});
		} else
			logger.warn(()->"Expected a Collection, got " + value.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	private void generateJavascripts(PrintWriter printer, Map<String, Object> config) {
		Object value = config.get("javaScripts");
		if(value==null)
			return;
		else if(value instanceof Collection) {
			((Collection<String>)value).forEach(item->{
				if(item instanceof String) {
					printer.print("<script crossorigin src=\"");
					printer.print(item);
					printer.println("\"></script>");
				} else {
					logger.warn(()->"Expected a String, got " + value.getClass().getName());
				}
			});
		} else
			logger.warn(()->"Expected a Collection, got " + value.getClass().getName());
	}

	private void generateUiFramework(PrintWriter printer, Map<String, Object> config) throws IOException {
		Object value = config.get("uiFramework");
		if(value==null)
			return;
		else if(value instanceof String) {
			config = YamlUtils.readResource("uiFrameworks/" + value + ".yaml");
			generateStyleSheets(printer, config);
			generateJavascripts(printer, config);
		} else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
	}

	private void generateHtmlEngine(PrintWriter printer, Map<String, Object> config) throws IOException {
		Object value = config.get("htmlEngine");
		if(value==null)
			return;
		else if(value instanceof String) {
			config = YamlUtils.readResource("htmlEngines/" + value + ".yaml");
			generateStyleSheets(printer, config);
			generateJavascripts(printer, config);
		} else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
	}

	private boolean generateWidgetLibrary(PrintWriter printer, Map<String, Object> config) throws IOException {
		Object value = config.get("widgetLibrary");
		if(value==null)
			return false;
		else if(value instanceof String) {
			config = YamlUtils.readResource("widgetLibraries/" + value + ".yaml");
			generateHtmlEngine(printer, config);
			generateUiFramework(printer, config);
			generateStyleSheets(printer, config);
			generateJavascripts(printer, config);
		} else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
		return true;
	}

	private void generateIcon(PrintWriter printer) {
		Map<String, Object> header = getHeaderConfig();
		if(header==null)
			return;
		Object value = header.get("icon");
		if(value==null)
			return;
		if(value instanceof String) {
			printer.print("<link href=\"");
			printer.print(value);
			printer.println("\" rel=\"icon\" type=\"image/ico\"/>");
		} else {
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
		}
	}

	private void generateTitle(PrintWriter printer) {
		Map<String, Object> header = getHeaderConfig();
		if(header==null)
			return;
		Object value = header.get("title");
		if(value==null)
			return;
		else if(value instanceof String) {
			printer.print("<title>");
			printer.print(value);
			printer.println("</title>");
		} else {
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
		}
	}

	private Map<String, Object> getHeaderConfig() {
		return getConfig(pageConfig, "header");
	}
	
	private Map<String, Object> getBodyConfig() {
		return getConfig(pageConfig, "body");
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> getConfig(Map<String, Object> source, String key) {
		Object value = source.get(key);
		if(value==null)
			return null;
		else if(value instanceof Map)
			return (Map<String, Object>)value;
		else {
			logger.warn(()->"Expected a Map<String, Object>, got " + value.getClass().getName());
			return null;
		}
	}

	private void generateBody(PrintWriter printer) {
		printer.println("<body onLoad='renderBody()'>");
		printer.println("<div id=\"body\"></div>");
		printer.println("</body>");
	}

	private void generateEpilog(PrintWriter printer) {
		printer.println("</html>");
	}


}
