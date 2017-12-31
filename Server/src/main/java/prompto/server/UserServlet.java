package prompto.server;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.declaration.IMethodDeclaration;
import prompto.expression.IExpression;
import prompto.expression.MethodSelector;
import prompto.grammar.ArgumentAssignmentList;
import prompto.intrinsic.PromptoDocument;
import prompto.reader.JSONReader;
import prompto.runtime.Standalone;
import prompto.runtime.Context;
import prompto.runtime.Interpreter;
import prompto.statement.MethodCall;
import prompto.type.DocumentType;
import prompto.type.TextType;
import prompto.value.Document;
import prompto.value.ExpressionValue;
import prompto.value.ListValue;
import prompto.value.Text;


@SuppressWarnings("serial")
public class UserServlet extends CleverServlet {

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
			Context context = Standalone.getGlobalContext();
			Document document = new Document();
			req.getParameterMap().forEach((n, l)->{
				Text name = new Text(n);
				if(l.length==1)
					document.setItem(context, name, new Text(l[0]));
				else {
					ListValue list = new ListValue(TextType.instance());
					for(String value : l)
						list.addItem(new Text(value));
					document.setItem(context, name, list);
				}
			});
			IExpression args = new ExpressionValue(DocumentType.instance(), document);
			ArgumentAssignmentList assignments = Interpreter.buildAssignments(method, args);
			MethodCall call = new MethodCall(new MethodSelector(method.getId()), assignments);
			call.interpret(context);	
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(500);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String contentType = req.getContentType();
			if(contentType.startsWith("application/json"))
				doPostJson(req, resp);
			else if(contentType.startsWith("application/x-www-form-urlencoded"))
				doPostUrlEncoded(req, resp);
			else if(contentType.startsWith("multipart/form-data"))
				doPostMultipart(req, resp);
			else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(500);
		}
	}

	private void doPostMultipart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}

	private void doPostUrlEncoded(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(415);
	}

	private void doPostJson(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			Object object = JSONReader.read(req.getInputStream());
			if(object instanceof PromptoDocument) {
				Context context = Standalone.getGlobalContext();
				Document document = new Document(context, (PromptoDocument<?,?>)object);
				IExpression args = new ExpressionValue(DocumentType.instance(), document);
				ArgumentAssignmentList assignments = Interpreter.buildAssignments(method, args);
				MethodCall call = new MethodCall(new MethodSelector(method.getId()), assignments);
				call.interpret(context);	
			} else
				resp.sendError(415);
		} catch(Throwable t) {
			t.printStackTrace();
			resp.setStatus(500);
		}		
	}
	
	
}
