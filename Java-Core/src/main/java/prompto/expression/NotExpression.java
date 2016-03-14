package prompto.expression;

import prompto.compiler.CompilerUtils;
import prompto.compiler.Flags;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.ResultInfo;
import prompto.declaration.TestMethodDeclaration;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.parser.Dialect;
import prompto.runtime.Context;
import prompto.type.BooleanType;
import prompto.type.IType;
import prompto.utils.CodeWriter;
import prompto.value.Boolean;
import prompto.value.IValue;

public class NotExpression implements IUnaryExpression, IAssertion {

	IExpression expression;
	
	public NotExpression(IExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public void toDialect(CodeWriter writer) {
		writer.append(operatorToDialect(writer.getDialect()));
		expression.toDialect(writer);
	}

	private String operatorToDialect(Dialect dialect) {
		switch(dialect) {
		case E:
		case S:
			return "not ";
		case O:
			return "!";
		default:
			throw new RuntimeException("Unsupported: " + dialect.name());	
		}
	}

	@Override
	public IType check(Context context) throws SyntaxError {
		IType type = expression.check(context);
		if(!(type instanceof BooleanType))
			throw new SyntaxError("Cannot negate " + type.getId());
		return BooleanType.instance();
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		IValue val = expression.interpret(context);
		return interpret(val);
	}
	
	private IValue interpret(IValue val) throws PromptoError {
		if(val instanceof Boolean) 
			return ((Boolean)val).getNot();
		else
			throw new SyntaxError("Illegal: not " + val.getClass().getSimpleName());
	}

	@Override
	public ResultInfo compile(Context context, MethodInfo method, Flags flags) throws SyntaxError {
		ResultInfo info = expression.compile(context, method, flags.withNative(true));
		if(Boolean.class==info.getType())
			CompilerUtils.BooleanToboolean(method);
		// perform 1-0
		method.addInstruction(Opcode.ICONST_1);
		method.addInstruction(Opcode.SWAP);
		method.addInstruction(Opcode.ISUB);
		if(flags.toNative())
			return new ResultInfo(boolean.class, false);
		else
			return CompilerUtils.booleanToBoolean(method);
	}
	
	@Override
	public boolean interpretAssert(Context context, TestMethodDeclaration test) throws PromptoError {
		IValue val = expression.interpret(context);
		IValue result = interpret(val);
		if(result==Boolean.TRUE) 
			return true;
		CodeWriter writer = new CodeWriter(test.getDialect(), context);
		this.toDialect(writer);
		String expected = writer.toString();
		String actual = operatorToDialect(test.getDialect()) + val.toString();
		test.printFailure(context, expected, actual);
		return false;	
	}
}
