package prompto.javascript;

import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.runtime.Context;
import prompto.statement.NativeCall;
import prompto.type.IType;
import prompto.type.VoidType;
import prompto.utils.CodeWriter;
import prompto.value.IValue;

public class JavaScriptNativeCall extends NativeCall {

	JavaScriptStatement statement;
	
	public JavaScriptNativeCall(JavaScriptStatement statement) {
		this.statement = statement;
	}
	
	@Override
	public void toDialect(CodeWriter writer) {
		writer.append("JavaScript: ");
		statement.toDialect(writer);
	}
	
	@Override
	public IType check(Context context) throws SyntaxError {
		return VoidType.instance(); // TODO
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		throw new RuntimeException("Should never get there!");
	}

}