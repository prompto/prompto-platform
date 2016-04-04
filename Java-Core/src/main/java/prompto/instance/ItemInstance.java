package prompto.instance;

import prompto.compiler.Flags;
import prompto.compiler.IOperand;
import prompto.compiler.MethodConstant;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.ResultInfo;
import prompto.error.IndexOutOfRangeError;
import prompto.error.InvalidValueError;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.expression.ItemSelector;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoAny;
import prompto.runtime.Context;
import prompto.type.AnyType;
import prompto.type.IType;
import prompto.type.IntegerType;
import prompto.utils.CodeWriter;
import prompto.value.IContainer;
import prompto.value.IValue;
import prompto.value.Integer;
import prompto.value.ListValue;

public class ItemInstance implements IAssignableSelector {

	IAssignableInstance parent;
	IExpression item;
	
	public ItemInstance(IExpression item) {
		this.item = item;
	}
	
	@Override
	public void setParent(IAssignableInstance parent) {
		this.parent = parent;
	}
	
	public IExpression getItem() {
		return item;
	}
	
	@Override
	public void toDialect(CodeWriter writer, IExpression expression) {
		parent.toDialect(writer, null);
		writer.append('[');
		item.toDialect(writer);
		writer.append(']');
	}
	
	@Override
	public IType checkAssignValue(Context context, IExpression expression) {
		IType elemType = parent.checkAssignElement(context);
		IType itemType = item.check(context);
		if(itemType!=IntegerType.instance())
			throw new SyntaxError("Expecting an Integer, got:" + itemType.toString());
		return elemType;
	}
	
	@Override
	public IType checkAssignMember(Context context, Identifier memberName) {
		return AnyType.instance(); // TODO 
	}
	
	@Override
	public IType checkAssignElement(Context context) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void assign(Context context, IExpression expression) throws PromptoError {
		IValue obj = parent.interpret(context);
		if(!(obj instanceof ListValue))
			throw new InvalidValueError("Expected a List, got:" + obj.getClass().getName());
		ListValue list = (ListValue)obj;
		IValue idx = item.interpret(context);
		if(!(idx instanceof Integer))
			throw new InvalidValueError("Expected an Integer, got:" + idx.getClass().getName());
		int index = (int)((Integer)idx).longValue();
		if(index<1 || index>list.getLength())
			throw new IndexOutOfRangeError();
		list.setItem(index-1, expression.interpret(context));
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		IValue parent = this.parent.interpret(context);
		IValue item = this.item.interpret(context);
        if (parent instanceof IContainer)
            return ((IContainer<?>)parent).getItem(context, item);
         else
 			throw new SyntaxError("Unknown item/key: " + item.getClass().getName());
	}
	
	@Override
	public ResultInfo compileParent(Context context, MethodInfo method, Flags flags) {
		ResultInfo parentInfo = this.parent.compileParent(context, method, flags);
		return ItemSelector.compileGetItem(context, method, flags, parentInfo, item);
	}
	
	@Override
	public ResultInfo compileAssign(Context context, MethodInfo method, Flags flags, IExpression value) {
		ResultInfo parentInfo = this.parent.compileParent(context, method, flags);
		if(PromptoAny.class==parentInfo.getType())
			return compileAssignAny(context, method, flags, item, value);
		else 
			throw new UnsupportedOperationException("Cannot compileAssign for " + parentInfo.getType().getTypeName());
	}
	
	private ResultInfo compileAssignAny(Context context, MethodInfo method, Flags flags, IExpression item, IExpression value) {
		item.compile(context, method, flags.withPrimitive(false));
		value.compile(context, method, flags.withPrimitive(false));
		IOperand oper = new MethodConstant(PromptoAny.class, "setItem", Object.class, Object.class, Object.class, void.class);
		method.addInstruction(Opcode.INVOKESTATIC, oper);
		return new ResultInfo(void.class);
	}
	
}
