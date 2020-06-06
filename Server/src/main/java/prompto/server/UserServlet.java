package prompto.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.debug.ProcessDebugger;
import prompto.declaration.IMethodDeclaration;
import prompto.expression.ValueExpression;
import prompto.expression.IExpression;
import prompto.expression.MethodSelector;
import prompto.grammar.ArgumentList;
import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDocument;
import prompto.reader.JSONReader;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Context;
import prompto.runtime.Interpreter;
import prompto.runtime.Standalone;
import prompto.runtime.VoidResult;
import prompto.statement.MethodCall;
import prompto.type.DocumentType;
import prompto.type.TextType;
import prompto.utils.Logger;
import prompto.value.BinaryValue;
import prompto.value.DocumentValue;
import prompto.value.IValue;
import prompto.value.ListValue;
import prompto.value.TextValue;
import prompto.value.TupleValue;


@SuppressWarnings("serial")
public class UserServlet extends CleverServlet {

	static final Logger logger = new Logger();

	IMethodDeclaration method;
	
	public UserServlet(IMethodDeclaration method) {
		this.method = method;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setMultipartConfig(new MultipartConfigElement(System.getProperty("java.io.tmpdir")));
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Thread.currentThread().setName(this.getClass().getSimpleName());
			logger.info(()->"Processing GET " + req.getRequestURI());
			Context local = getWorkerContext();
			DocumentValue document = paramsToDocument(local, req.getParameterMap());
			IValue value = interpret(local, document);
			if(value!=null)
				sendValue(req, resp, value);
		} catch(Throwable t) {
			logger.error(()->"While processing GET " + req.getRequestURI(), t);
			resp.setStatus(500);
		}
	}
	
	private Context getWorkerContext() {
		Context context = ApplicationContext.get().newLocalContext();
		ProcessDebugger processDebugger = ProcessDebugger.getInstance();
		if(processDebugger!=null)
			Standalone.startWorkerDebugger(Thread.currentThread(), context);
		return context;
	}

	private IValue interpret(Context context, DocumentValue document) {
		IExpression args = new ValueExpression(DocumentType.instance(), document);
		ArgumentList arguments = Interpreter.buildArguments(method, args);
		MethodCall call = new MethodCall(new MethodSelector(method.getId()), arguments);
		return call.interpret(context);	
	}

	private DocumentValue paramsToDocument(Context context, Map<String, String[]> params) {
		DocumentValue document = new DocumentValue();
		params.forEach((n, l)->{
			TextValue name = new TextValue(n);
			if(l.length==1)
				document.setItem(context, name, new TextValue(l[0]));
			else {
				ListValue list = new ListValue(TextType.instance());
				for(String value : l)
					list.addItem(new TextValue(value));
				document.setItem(context, name, list);
			}
		});
		return document;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			logger.info(()->"Processing POST " + req.getRequestURI());
			IValue value = null;
			String contentType = req.getContentType();
			if(contentType==null)
				contentType = "application/x-www-form-urlencoded";
			if(contentType.startsWith("application/json"))
				value = doPostJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				value = doPostUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				value = doPostMultipart(req, resp);
			else
				resp.sendError(415);
			if(value!=null)
				sendValue(req, resp, value);
		} catch(Throwable t) {
			logger.error(()->"While processing POST " + req.getRequestURI(), t);
			resp.setStatus(500);
		}
	}

	private void sendValue(HttpServletRequest req, HttpServletResponse resp, IValue value) throws IOException {
		if(value==null || value==VoidResult.instance())
			return;
		if(value instanceof TextValue) {
			resp.setContentType("text/plain");
			resp.getWriter().write(((TextValue)value).getStorableData());
			return;
		}
		if(value instanceof BinaryValue) {
			PromptoBinary binary = ((BinaryValue)value).getData();
			resp.setContentType(binary.getMimeType());
			resp.getOutputStream().write(binary.getBytes());
			return;
		} 
		if(value instanceof TupleValue) {
			TupleValue tuple = (TupleValue)value;
			if(tuple.getLength()>=2) {
				IValue content = tuple.getItem(0);
				IValue mimeType = tuple.getItem(1);
				if(content.getType()==TextType.instance() && mimeType.getType()==TextType.instance()) {
					resp.setContentType(((TextValue)mimeType).getStorableData());
					resp.getWriter().write(((TextValue)content).getStorableData());
					return;
				}
			}
		}
		resp.getWriter().write("Unsupported result: " + value.getType().getTypeName());
	}

	private IValue doPostMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
		return null;
	}

	private IValue doPostUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Context context = getWorkerContext();
		DocumentValue document = paramsToDocument(context, req.getParameterMap());
		return interpret(context, document);
	}

	private IValue doPostJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Object object = JSONReader.read(req.getInputStream());
		if(object instanceof PromptoDocument) {
			Context context = getWorkerContext();
			DocumentValue document = new DocumentValue(context, (PromptoDocument<?,?>)object, true);
			return interpret(context, document);
		} else {
			resp.sendError(415);
			return null;
		}
	}
	
}
