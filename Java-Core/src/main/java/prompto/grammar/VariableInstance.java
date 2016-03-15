package prompto.grammar;

import prompto.compiler.ByteOperand;
import prompto.compiler.ClassConstant;
import prompto.compiler.IVerifierEntry.Type;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.ResultInfo;
import prompto.compiler.StackLocal;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.runtime.Context;
import prompto.runtime.Variable;
import prompto.type.IType;
import prompto.utils.CodeWriter;
import prompto.value.IValue;

public class VariableInstance implements IAssignableInstance {
	
	Identifier id;
	
	public VariableInstance(Identifier name) {
		this.id = name;
	}
	
	public Identifier getId() {
		return id;
	}
	
	@Override
	public void register(MethodInfo method, ResultInfo info) {
		method.registerLocal(id.getName(), Type.ITEM_Object, new ClassConstant(info.getType()));
	}
	
	@Override
	public ResultInfo compile(MethodInfo method, ResultInfo info) {
		StackLocal local = method.getRegisteredLocal(id.getName());
		switch(local.getIndex()) {
			case 0:
				method.addInstruction(Opcode.ASTORE_0);
				break;
			case 1:
				method.addInstruction(Opcode.ASTORE_1);
				break;
			case 2:
				method.addInstruction(Opcode.ASTORE_2);
				break;
			case 3:
				method.addInstruction(Opcode.ASTORE_3);
				break;
			default:
				// TODO: support ALOAD_W
				method.addInstruction(Opcode.ASTORE, new ByteOperand((byte)local.getIndex()));
		}
		return new ResultInfo(void.class, false);
	}
	
	@Override
	public void toDialect(CodeWriter writer, IExpression expression) {
		if(expression!=null) try {
			IType type = expression.check(writer.getContext());
			INamed actual = writer.getContext().getRegisteredValue(INamed.class,id);
			if(actual==null)
				writer.getContext().registerValue(new Variable(id, type));
		} catch(SyntaxError e) {
			// TODO warning
		}
		writer.append(id);
	}
	
	@Override
	public IType checkAssignValue(Context context, IExpression expression) throws SyntaxError {
		IType type = expression.check(context);
		INamed actual = context.getRegisteredValue(INamed.class,id);
		if(actual==null)
			context.registerValue(new Variable(id, type));
		else {
			// need to check type compatibility
			IType actualType = actual.getType(context);
			type.checkAssignableTo(context, actualType);
			type = actualType;
		}
		return type;
	}
	
	@Override
	public IType checkAssignMember(Context context, Identifier memberName) throws SyntaxError {
		INamed actual = context.getRegisteredValue(INamed.class, id);
		if(actual==null) 
			throw new SyntaxError("Unknown variable:" + this.id);
		IType parentType = actual.getType(context);
		return parentType.checkMember(context, memberName);
	}
	
	@Override
	public IType checkAssignElement(Context context) throws SyntaxError {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void assign(Context context, IExpression expression) throws PromptoError {
 		IValue value = expression.interpret(context);
		if(context.getRegisteredValue(INamed.class,id)==null)
			context.registerValue(new Variable(id, value.getType())); 
		context.setValue(id, value);
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		return context.getValue(id);
	}

}
