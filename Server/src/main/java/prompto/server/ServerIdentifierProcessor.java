package prompto.server;

import prompto.compiler.ClassConstant;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.ResultInfo;
import prompto.compiler.ResultInfo.Flag;
import prompto.java.JavaClassType;
import prompto.java.JavaIdentifierProcessor;
import prompto.runtime.Context;
import prompto.type.IType;

public class ServerIdentifierProcessor extends JavaIdentifierProcessor {
	
	public static void register() {
		JavaIdentifierProcessor.processors.put("$server", new ServerIdentifierProcessor());
	}

	@Override
	public IType check(Context context) {
		return new JavaClassType(AppServer.class);
	}
	
	@Override
	public Object interpret(Context context) {
		return AppServer.class;
	}
	
	@Override
	public ResultInfo compile(Context context, MethodInfo method) {
		// AppServer.class
		ClassConstant k = new ClassConstant(AppServer.class);
		method.addInstruction(Opcode.LDC, k);
		return new ResultInfo(AppServer.class, Flag.STATIC);
	}

}