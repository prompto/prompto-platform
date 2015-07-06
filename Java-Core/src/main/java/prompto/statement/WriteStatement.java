package prompto.statement;

import prompto.error.InvalidResourceError;
import prompto.error.NullReferenceError;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.runtime.Context;
import prompto.type.IType;
import prompto.type.ResourceType;
import prompto.type.VoidType;
import prompto.utils.CodeWriter;
import prompto.value.IResource;
import prompto.value.IValue;

public class WriteStatement extends SimpleStatement {

	IExpression content;
	IExpression resource;
	
	public WriteStatement(IExpression content, IExpression resource) {
		this.content = content;
		this.resource = resource;
	}
	

	@Override
	public void toDialect(CodeWriter writer) {
		writer.append("write ");
		switch(writer.getDialect()) {
		case E:
		case S:
			content.toDialect(writer);
			break;
		case O:
			writer.append("(");
			content.toDialect(writer);
			writer.append(")");
			break;
		}
		writer.append(" to ");
		resource.toDialect(writer);
	}
	
	@Override
	public IType check(Context context) throws SyntaxError {
		context = context.newResourceContext();
		IType resourceType = resource.check(context);
		if(!(resourceType instanceof ResourceType))
			throw new SyntaxError("Not a resource!");
		return VoidType.instance();
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		context = context.newResourceContext();
		Object o = resource.interpret(context);
		if(o==null)
			throw new NullReferenceError();
		if(!(o instanceof IResource))
			throw new InternalError("Illegal write source: " + o);
		IResource res = (IResource)o;
		if(!res.isWritable())
			throw new InvalidResourceError("Not writable");
		res.writeFully(content.interpret(context).toString());
		return null;
	}
}