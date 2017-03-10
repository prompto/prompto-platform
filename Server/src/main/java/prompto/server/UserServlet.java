package prompto.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import prompto.declaration.IMethodDeclaration;
import prompto.expression.IExpression;
import prompto.expression.MethodSelector;
import prompto.grammar.ArgumentAssignmentList;
import prompto.runtime.Application;
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
@MultipartConfig
public class UserServlet extends HttpServlet {

	IMethodDeclaration method;
	
	public UserServlet(IMethodDeclaration method) {
		this.method = method;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Context context = Application.getGlobalContext();
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
}
