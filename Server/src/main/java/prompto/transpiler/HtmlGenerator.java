package prompto.transpiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import prompto.code.ICodeStore;
import prompto.code.ModuleType;
import prompto.code.WebLibrary;
import prompto.declaration.CategoryDeclaration;
import prompto.declaration.IWidgetDeclaration;
import prompto.error.SyntaxError;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoVersion;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Context;
import prompto.store.DataStore;
import prompto.utils.HtmlUtils;
import prompto.utils.Logger;
import prompto.utils.YamlUtils;

public class HtmlGenerator {

	static final Logger logger = new Logger();
	
	String userAgent;
	Map<String, Object> pageConfig;
	String htmlEngine = null;
	Set<String> headerEntries = new HashSet<>();
	
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

	private void generatePageRenderScript(PrintWriter printer, String widgetName) {
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
		generateMetas(printer);
		generateTitle(printer);
		generateIcon(printer);
		generateName(printer);
		generatePromptoScripts(printer);
		try {
			generateLibraries(printer);
			Consumer<PrintWriter> bodyWriter = generatePageWidgetScript(context, printer);
			printer.println("</head>");
			return bodyWriter;
		} catch(Exception e) {
			return p->generateException(p, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void generateMetas(PrintWriter printer) {
		writeHeaderEntry(printer, "<meta charset='utf-8'>");
		Map<String, Object> config = getHeaderConfig();
		Object value = config.get("metas");
		if(value==null)
			return;
		else if(value instanceof Collection) {
			((Collection<String>)value).forEach(item->{
				if(item instanceof String)
					writeHeaderEntry(printer, item);
				else
					logger.warn(()->"Expected a String, got " + value.getClass().getName());
			});
		} else
			logger.warn(()->"Expected a Collection, got " + value.getClass().getName());
	}

	private void writeHeaderEntry(PrintWriter printer, String entry) {
		if(headerEntries.add(entry))
			printer.println(entry);
		else
			logger.warn(()->"Duplicate header entry: " + entry);
	}

	private Consumer<PrintWriter> generatePageWidgetScript(Context context, PrintWriter printer) {
		try {
			String widgetName = getWidgetName();
			generatePageWidgetScript(context, printer, widgetName);
			return this::generateBody;
		} catch(SyntaxError e) {
			logger.error(()->"While generating page", e);
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
		printer.println("Syntax error '" + HtmlUtils.encodeHtmlEntities(e.getMessage()) + "'");
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

	private void generatePageWidgetScript(Context context, PrintWriter printer, String widgetName) {
		IWidgetDeclaration widget = fetchWidgetDeclaration(context, widgetName);
		if(widget==null)
			throw new SyntaxError("No such widget '" + widgetName + "'");
		generatePageWidgetScript(printer, widget);
		generatePageRenderScript(printer, widgetName);
	}
	

	private IWidgetDeclaration fetchWidgetDeclaration(Context context, String widgetName) {
		CategoryDeclaration decl = context.getRegisteredDeclaration(CategoryDeclaration.class, new Identifier(widgetName));
		if(decl!=null && decl.isAWidget(context))
			return ((CategoryDeclaration)decl).asWidget();
		else
			return null;
		
	}

	private void generatePageWidgetScript(PrintWriter printer, IWidgetDeclaration declaration) {
		IJSEngine engine = IJSEngine.forUserAgent(userAgent);
		Context context = ApplicationContext.get().newLocalContext();
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
		writeHeaderEntry(printer, "<script src='https://cdnjs.cloudflare.com/ajax/libs/axios/0.17.1/axios.js'></script>");
		writeHeaderEntry(printer, "<script src='/js/lib/require.js'></script>");
		writeHeaderEntry(printer, "<script src='/js/lib/mousetrap.js'></script>");
	}

	private void generateLibraries(PrintWriter printer) throws Exception {
		Map<String, Object> config = getHeaderConfig();
		if(config==null)
			return;
		generateWebLibraries(printer, config);
		generateWidgetLibraries(printer, config);
		generateHtmlEngine(printer, config);
		generateUiFramework(printer, config);
		generateStyleSheets(printer, config);
		generateJavaScripts(printer, config);
	}
	
	@SuppressWarnings("unchecked")
	private void generateWebLibraries(PrintWriter printer, Map<String, Object> config) throws Exception {
		Object value = config.get("webLibraries");
		if(value==null)
			return;
		else if(value instanceof Collection) {
			for(Object item : (Collection<Object>)value) {
				generateWebLibrary(printer, item);
			}
		} else
			logger.warn(()->"Expected a Collection, got " + value.getClass().getName());
	}

	private void generateWebLibrary(PrintWriter printer, Object entry) throws Exception {
		if(entry instanceof String)
			generateWebLibrary(printer, (String)entry, PromptoVersion.LATEST);
		else if(entry instanceof Map) {
			Object name = ((Map<?,?>)entry).get("name");
			if(name instanceof String) {
				Object value = ((Map<?,?>)entry).get("version");
				if(value instanceof String) try {
					PromptoVersion version = PromptoVersion.parse((String)value);
					generateWebLibrary(printer, (String)name, version);
				} catch(InvalidParameterException e) {
					throw new SyntaxError("Invalid 'version' in webLibraries entry: " + entry.toString());
				} else
					throw new SyntaxError("Missing 'version' in webLibraries entry: " + entry.toString());
			} else
				throw new SyntaxError("Missing 'name' in webLibraries entry: " + entry.toString());
		} else
			throw new SyntaxError("Could not understand webLibraries entry: " + entry.toString());
	}

	private void generateWebLibrary(PrintWriter printer, String name, PromptoVersion version) throws Exception {
		WebLibrary webLibrary = ICodeStore.getInstance().fetchVersionedModule(ModuleType.WEBLIBRARY, name, version);
		if(webLibrary == null)
			throw new SyntaxError("Could not find webLibrary: " + name + ", version: " + version.toString());
		Map<String, Object> config = new HashMap<>();
		config.put("htmlEngine", webLibrary.getHtmlEngine());
		config.put("javaScripts", webLibrary.getJavaScripts());
		config.put("styleSheets", webLibrary.getStyleSheets());
		generateHtmlEngine(printer, config);
		generateStyleSheets(printer, config);
		generateJavaScripts(printer, config);
	}

	@SuppressWarnings("unchecked")
	private void generateStyleSheets(PrintWriter printer, Map<String, Object> config) {
		Object value = config.get("styleSheets");
		if(value==null)
			return;
		else if(value instanceof Collection) {
			((Collection<String>)value).forEach(item->{
				if(item instanceof String) {
					if(!(item.startsWith("http")|| item.startsWith("/")))
						item = "/" + item;
					String styleSheet = "<link href='" + item + "' rel='stylesheet'/>";
					writeHeaderEntry(printer, styleSheet);
				} else {
					logger.warn(()->"Expected a String, got " + value.getClass().getName());
				}
			});
		} else
			logger.warn(()->"Expected a Collection, got " + value.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	private void generateJavaScripts(PrintWriter printer, Map<String, Object> config) {
		Object value = config.get("javaScripts");
		if(value==null)
			return;
		else if(value instanceof Collection) {
			((Collection<String>)value).forEach(item->{
				if(item instanceof String) {
					if(!(item.startsWith("http")|| item.startsWith("/")))
						item = "/" + item;
					String javaScript = "<script" + (item.startsWith("http") ? " crossorigin " : "") + " src='" + item + "'></script>";
					writeHeaderEntry(printer, javaScript);
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
			generateJavaScripts(printer, config);
		} else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
	}

	private void generateHtmlEngine(PrintWriter printer, Map<String, Object> config) throws Exception {
		Object value = config.get("htmlEngine");
		if(value==null)
			return;
		else if(value instanceof String) {
			if(value.equals(htmlEngine))
				return; // already generated
			else if(htmlEngine!=null)
				throw new SyntaxError("HTML engine: " + value + " conflicts with: " + htmlEngine);
			else {
				htmlEngine = (String)value;
				config = YamlUtils.readResource("htmlEngines/" + value + ".yaml");
				generateStyleSheets(printer, config);
				generateJavaScripts(printer, config);
			}
		} else
			logger.warn(()->"Expected a String, got " + value.getClass().getName());
	}

	private void generateWidgetLibraries(PrintWriter printer, Map<String, Object> config) throws Exception {
		Object values = config.get("widgetLibraries");
		if(values==null)
			return;
		else if(values instanceof Collection) {
			for(Object value : (Collection<?>)values)
				generateWidgetLibrary(value, printer, config);
		} else
			logger.warn(()->"Expected a List, got " + values.getClass().getName());
	}

	private void generateWidgetLibrary(Object value, PrintWriter printer, Map<String, Object> config) throws Exception {
		if(value instanceof String) {
			config = YamlUtils.readResource("widgetLibraries/" + value + ".yaml");
			generateHtmlEngine(printer, config);
			generateUiFramework(printer, config);
			generateStyleSheets(printer, config);
			generateJavaScripts(printer, config);
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
			String icon = "<link href='" + value + "' rel='icon' type='image/ico'/>";
			writeHeaderEntry(printer, icon);
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
			String title = "<title>" + value + "</title>";
			writeHeaderEntry(printer, title);
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
			String name = "<script>\twindow.name = '" + value + "';\n</script>";
			writeHeaderEntry(printer, name);
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
		printer.println("<div id='body'></div>");
		printer.println("<div id='modal'></div>");
		printer.println("<div id='context'></div>");
		printer.println("</body>");
	}

	private void generateEpilog(PrintWriter printer) {
		printer.println("</html>");
	}


}
