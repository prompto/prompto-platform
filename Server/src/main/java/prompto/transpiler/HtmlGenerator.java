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
import java.util.UUID;
import java.util.function.Consumer;

import prompto.code.ICodeStore;
import prompto.declaration.CategoryDeclaration;
import prompto.declaration.WrappingWidgetDeclaration;
import prompto.declaration.IDeclaration;
import prompto.declaration.IWidgetDeclaration;
import prompto.error.SyntaxError;
import prompto.runtime.Context;
import prompto.runtime.Standalone;
import prompto.store.DataStore;
import prompto.utils.Logger;
import prompto.utils.YamlUtils;

public class HtmlGenerator {

	static final Logger logger = new Logger();
	
	String userAgent;
	Map<String, Object> pageConfig;
	String htmlEngine = null;
	
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
		Consumer<PrintWriter> bodyWriter = generateHeader(context, printer);
		bodyWriter.accept(printer);
		generateEpilog(printer);
	}

	private void generateRenderScript(PrintWriter printer, String widgetName) {
		printer.println("<script>");
		printer.println("function renderBody() {");
		// TODO call htmlEngine
		printer.print("var pageWidget = React.createElement( ");
		printer.print(widgetName); 
		printer.println(");");
		printer.print("ReactDOM.render(pageWidget, document.getElementById('body'));");
		printer.println("}");
		printer.println("</script>");
	}

	private void generateProlog(PrintWriter printer) {
		printer.println("<!DOCTYPE html>");
		printer.println("<html>");
	}

	private Consumer<PrintWriter> generateHeader(Context context, PrintWriter printer) throws IOException {
		printer.println("<head>");
		generateEncoding(printer);
		generateTitle(printer);
		generateIcon(printer);
		generateName(printer);
		generatePromptoScripts(printer);
		try {
			generateLibraries(printer);
			Consumer<PrintWriter> bodyWriter = generateWidgetScript(context, printer);
			printer.println("</head>");
			return bodyWriter;
		} catch(Exception e) {
			return p->generateException(p, e);
		}
	}
	
	private void generateEncoding(PrintWriter printer) {
		printer.println("<meta charset=\"utf-8\">");
	}

	private Consumer<PrintWriter> generateWidgetScript(Context context, PrintWriter printer) {
		try {
			String widgetName = getWidgetName();
			generateWidgetScript(context, printer, widgetName);
			return this::generateBody;
		} catch(SyntaxError e) {
			return p->generateSyntaxError(p, e);
		} catch(Exception e) {
			return p->generateException(p, e);
		}
	}
	
	private void generateException(PrintWriter printer, Exception e) {
		printer.println("<body>");
		e.printStackTrace(printer);
		printer.println("</body>");
	}

	
	private void generateSyntaxError(PrintWriter printer, SyntaxError e) {
		printer.println("<body>");
		printer.println("Syntax error '" + e.getMessage() + "'");
		printer.println("</body>");
	}

	
	private String getWidgetName() {
		Map<String, Object> body = getBodyConfig();
		if(body==null)
			throw new SyntaxError("Missing 'body' section in page descriptor");
		Object value = body.get("widget");
		if(value instanceof String)
			return (String)value;
		if(value==null) 
			throw new SyntaxError("Missing 'widget' entry in 'body' section of page descriptor");
		else
			throw new SyntaxError("Expected a String for 'widget', got " + value.getClass().getName());
	}

	private void generateWidgetScript(Context context, PrintWriter printer, String widgetName) {
		IWidgetDeclaration widget = fetchWidgetDeclaration(context, widgetName);
		if(widget==null)
			throw new SyntaxError("No such widget '" + widgetName + "'");
		generateWidgetScript(printer, widget);
		generateRenderScript(printer, widgetName);
	}
	

	private IWidgetDeclaration fetchWidgetDeclaration(Context context, String widgetName) {
		Iterable<IDeclaration> decls = ICodeStore.getInstance().fetchLatestDeclarations(widgetName);
		if(decls==null)
			return null;
		Iterator<IDeclaration> iter = decls.iterator();
		if(!iter.hasNext())
			return null;
		IDeclaration decl = iter.next();;
		if(!(decl instanceof CategoryDeclaration))
			return null;
		if(!((CategoryDeclaration)decl).isAWidget(context))
			return null;
		return decl instanceof IWidgetDeclaration ? (IWidgetDeclaration)decl : new WrappingWidgetDeclaration((CategoryDeclaration)decl);
		
	}

	private void generateWidgetScript(PrintWriter printer, IWidgetDeclaration declaration) {
		IJSEngine engine = IJSEngine.forUserAgent(userAgent);
		Context context = Standalone.getGlobalContext();
		Transpiler transpiler = new Transpiler(engine, context);
		declaration.declare(transpiler);
		if(transpiler.requires("DataStore")) {
			transpiler.require("RemoteStore");
			if(DataStore.getInstance().getDbIdClass()==UUID.class)
				transpiler.require("UUID");
		}
		printer.println("<script id='transpiled'>");
		try {
			transpiler.print(printer);
		} finally {
			printer.println("</script>");
		}
	}

	private void generatePromptoScripts(PrintWriter printer) {
		printer.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/axios/0.17.1/axios.js'></script>");
		printer.println("<script src='/js/lib/require.js'></script>");
	}

	private void generateLibraries(PrintWriter printer) throws Exception {
		Map<String, Object> header = getHeaderConfig();
		if(header==null)
			return;
		if(!generateWidgetLibraries(printer, header)) {
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

	private void generateHtmlEngine(PrintWriter printer, Map<String, Object> config) throws Exception {
		Object value = config.get("htmlEngine");
		if(value==null)
			return;
		else if(value instanceof String) {
			if(htmlEngine==null)
				htmlEngine = (String)value;
			else if(!htmlEngine.equals(value))
				throw new Exception("HTML engine: " + value + " conflicts with: " + htmlEngine);
			config = YamlUtils.readResource("htmlEngines/" + value + ".yaml");
			generateStyleSheets(printer, config);
			generateJavascripts(printer, config);
		} else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
	}

	private boolean generateWidgetLibraries(PrintWriter printer, Map<String, Object> config) throws Exception {
		Object values = config.get("widgetLibraries");
		if(values==null)
			return false;
		else if(values instanceof Collection) {
			for(Object value : (Collection<?>)values)
				generateWidgetLibrary(value, printer, config);
		} else
			logger.warn(()->"Expected a List, got " + values.getClass().getName());
		return true;
	}

	private void generateWidgetLibrary(Object value, PrintWriter printer, Map<String, Object> config) throws Exception {
		if(value instanceof String) {
			config = YamlUtils.readResource("widgetLibraries/" + value + ".yaml");
			generateHtmlEngine(printer, config);
			generateUiFramework(printer, config);
			generateStyleSheets(printer, config);
			generateJavascripts(printer, config);
		}
		else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
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

	private void generateName(PrintWriter printer) {
		Map<String, Object> header = getHeaderConfig();
		if(header==null)
			return;
		Object value = header.get("name");
		if(value==null)
			return;
		else if(value instanceof String) {
			printer.print("<script>");
			printer.print("\twindow.name = '");
			printer.print(value);
			printer.print("';");
			printer.println("</script>");
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
		printer.println("<div id=\"modal\"></div>");
		printer.println("</body>");
	}

	private void generateEpilog(PrintWriter printer) {
		printer.println("</html>");
	}


}
