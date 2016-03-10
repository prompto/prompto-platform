package prompto.value;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;

import prompto.compiler.CompilerUtils;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.ResultInfo;
import prompto.compiler.ShortOperand;
import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.store.IStorable;
import prompto.type.BooleanType;

public class Boolean extends BaseValue implements Comparable<Boolean> {
	
	public static Boolean TRUE = new Boolean(true);
	public static Boolean FALSE = new Boolean(false);

	static {
		TRUE.not = FALSE;
		FALSE.not = TRUE;
	}

	public static Boolean parse(String text) {
		return valueOf(java.lang.Boolean.parseBoolean(text));
	}

	public static Boolean valueOf(boolean value) {
		return value ? TRUE : FALSE;
	}

	boolean value;
	Boolean not;

	private Boolean(boolean value) {
		super(BooleanType.instance());
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public Boolean getNot() {
		return not;
	}

	@Override
	public void storeValue(Context context, String name, IStorable storable) throws PromptoError {
		storable.setData(name, value);
	}
	
	@Override
	public int compareTo(Context context, IValue value) throws SyntaxError {
		if (value instanceof Boolean)
			return compareTo((Boolean) value);
		else
			throw new SyntaxError("Illegal comparison: Boolean + " + value.getClass().getSimpleName());
	}

	@Override
	public int compareTo(Boolean other) {
		return java.lang.Boolean.compare(this.value, other.value);
	}

	@Override
	public Object convertTo(Class<?> type) {
		return value;
	}

	@Override
	public String toString() {
		return java.lang.Boolean.toString(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Boolean)
			return value == ((Boolean) obj).value;
		else
			return false;
	}
	
	@Override
	public void toJson(Context context, JsonGenerator generator, IInstance instance, Identifier name) throws PromptoError {
		try {
			generator.writeBoolean(value);
		} catch(IOException e) {
			throw new ReadWriteError(e.getMessage());
		}
	}
	
	public static ResultInfo compileEquals(Context context, MethodInfo method, ResultInfo left, IExpression exp, boolean toNative) throws SyntaxError {
		if(Boolean.class==left.getType())
			CompilerUtils.BooleanToboolean(method);
		ResultInfo right = exp.compile(context, method, true);
		if(Boolean.class==right.getType())
			CompilerUtils.BooleanToboolean(method);
		method.addInstruction(Opcode.IF_ICMPNE, new ShortOperand((short)4));
		method.addInstruction(Opcode.ICONST_1);
		method.addInstruction(Opcode.GOTO, new ShortOperand((short)1));
		method.addInstruction(Opcode.ICONST_0);
		if(toNative)
			return new ResultInfo(boolean.class, false);
		else
			return CompilerUtils.booleanToBoolean(method);
	}

}
