package prompto.value;

import java.io.IOException;
import java.text.Collator;

import prompto.compiler.CompilerUtils;
import prompto.compiler.Flags;
import prompto.compiler.IOperand;
import prompto.compiler.InterfaceConstant;
import prompto.compiler.MethodConstant;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.PromptoType;
import prompto.compiler.ResultInfo;
import prompto.compiler.ShortOperand;
import prompto.compiler.StackState;
import prompto.error.IndexOutOfRangeError;
import prompto.error.InvalidDataError;
import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.intrinsic.IterableWithLength;
import prompto.intrinsic.IteratorWithLength;
import prompto.intrinsic.PromptoString;
import prompto.runtime.Context;
import prompto.store.IStorable;
import prompto.type.TextType;

import com.fasterxml.jackson.core.JsonGenerator;


public class Text extends BaseValue implements Comparable<Text>, IContainer<Character>, ISliceable<Character>, IMultiplyable {

	String value;

	public Text(String value) {
		super(TextType.instance());
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public void storeValue(Context context, String name, IStorable storable) throws PromptoError {
		storable.setData(name, value);
	}
		
	@Override
	public long getLength() {
		return value.length();
	}
	
	@Override
	public IValue plus(Context context, IValue value) {
		return new Text(this.value + value.toString());
	}

	public static ResultInfo compilePlus(Context context, MethodInfo method, Flags flags, 
			ResultInfo left, IExpression exp) throws SyntaxError {
		ResultInfo right = exp.compile(context, method, flags.withPrimitive(false));
		// convert right to String
		if(String.class!=right.getType()) {
			if(right.getType() instanceof PromptoType) {
				InterfaceConstant oper = new InterfaceConstant(right.getType(), "toString", String.class);
				method.addInstruction(Opcode.INVOKEINTERFACE, oper);
			} else {
				MethodConstant oper = new MethodConstant(Object.class, "toString", String.class);
				method.addInstruction(Opcode.INVOKEVIRTUAL, oper);
			}
		}
		// and call concat
		MethodConstant oper = new MethodConstant(String.class, "concat", 
				String.class, String.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, oper);
		return new ResultInfo(String.class);
	}
	
	@Override
	public IValue multiply(Context context, IValue value) throws PromptoError {
		if (value instanceof Integer) {
			int count = (int) ((Integer) value).longValue();
			if (count < 0)
				throw new SyntaxError("Negative repeat count:" + count);
			return new Text(PromptoString.multiply(this.value, count));
		} else
			throw new SyntaxError("Illegal: Chararacter * " + value.getClass().getSimpleName());
	}

	public static ResultInfo compileMultiply(Context context, MethodInfo method, Flags flags, 
			ResultInfo left, IExpression exp) throws SyntaxError {
		ResultInfo right = exp.compile(context, method, flags);
		if(Long.class==right.getType())
			CompilerUtils.LongToint(method);
		else if(long.class==right.getType())
			CompilerUtils.longToint(method);
		MethodConstant oper = new MethodConstant(PromptoString.class, 
				"multiply", 
				String.class, int.class, String.class);
		method.addInstruction(Opcode.INVOKESTATIC, oper);
		return new ResultInfo(String.class);
	}
	
	
	public int compareTo(Text obj) {
		return value.compareTo(obj.getValue());
	}

	@Override
	public int compareTo(Context context, IValue value) throws PromptoError {
		if (value instanceof Text)
			return this.value.compareTo(((Text) value).value);
		else
			throw new SyntaxError("Illegal comparison: Text + " + value.getClass().getSimpleName());
	}
	
	public static ResultInfo compileCompareTo(Context context, MethodInfo method, Flags flags, 
			ResultInfo left, IExpression exp) throws SyntaxError {
		exp.compile(context, method, flags);
		IOperand oper = new MethodConstant(String.class, 
				"compareTo", String.class, int.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, oper);
		return BaseValue.compileCompareToEpilogue(method, flags);
	}

	public boolean hasItem(Context context, IValue value) throws PromptoError {
		if (value instanceof Character)
			return this.value.indexOf(((Character) value).value) >= 0;
		else if (value instanceof Text)
			return this.value.indexOf(((Text) value).value) >= 0;
		else
			throw new SyntaxError("Illegal contain: Text + " + value.getClass().getSimpleName());
	}

	@Override
	public IValue getMember(Context context, Identifier id, boolean autoCreate) throws PromptoError {
		String name = id.toString();
		if ("length".equals(name))
			return new Integer(value.length());
		else
			throw new InvalidDataError("No such member:" + name);
	}

	public Character getItem(Context context, IValue index) throws PromptoError {
		try {
			if (index instanceof Integer)
				return new Character(value.charAt((int) ((Integer) index).longValue() - 1));
			else
				throw new InvalidDataError("No such item:" + index.toString());
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfRangeError();
		}

	}
	
	public static ResultInfo compileItem(Context context, MethodInfo method, Flags flags, 
			ResultInfo left, IExpression exp) throws SyntaxError {
		ResultInfo right = exp.compile(context, method, flags);
		if(Long.class==right.getType())
			CompilerUtils.LongToint(method);
		else if(long.class==right.getType())
			CompilerUtils.longToint(method);
		MethodConstant oper = new MethodConstant(String.class, 
				"charAt", 
				int.class, char.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, oper);
		if(flags.toPrimitive())
			return new ResultInfo(char.class);
		else
			return CompilerUtils.charToCharacter(method);
	}
	
	@Override
	public IterableWithLength<Character> getIterable(Context context) {
		return new CharacterIterable(context);
	}

	class CharacterIterable implements IterableWithLength<Character> {

		Context context;
		
		public CharacterIterable(Context context) {
			this.context = context;
		}
		
		@Override
		public IteratorWithLength<Character> iterator() {
			return new CharacterIterator();
		}
		
		class CharacterIterator implements IteratorWithLength<Character> {
			
			int index = -1;
			
			@Override
			public long getLength() {
				return value.length();
			}
			
			@Override
			public boolean hasNext() {
				return index < value.length() - 1;
			}
			
			@Override
			public Character next() {
				return new Character(value.charAt(++index));
			}
			
			@Override
			public void remove() {
				throw new InternalError("Should never get there!");
			}
		}
	}
	
	@Override
	public Object convertTo(Class<?> type) {
		return value;
	}

	public ISliceable<Character> slice(Integer fi, Integer li) throws PromptoError {
		int first = checkSliceFirst(fi);
		int last = checkSliceLast(li);
		return new Text(value.substring(first - 1, last ));
	}
	
	private int checkSliceFirst(Integer fi) throws IndexOutOfRangeError {
		int value = (fi == null) ? 1 : (int) fi.longValue();
		if (value < 1 || value > this.value.length())
			throw new IndexOutOfRangeError();
		return value;
	}

	private int checkSliceLast(Integer li) throws IndexOutOfRangeError {
		int value = (li == null) ? this.value.length() : (int) li.longValue();
		if (value < 0)
			value = this.value.length() + 1 + (int) li.longValue();
		if (value < 1 || value > this.value.length())
			throw new IndexOutOfRangeError();
		return value;
	}

	public static ResultInfo compileSlice(Context context, MethodInfo method, Flags flags, 
			ResultInfo parent, IExpression first, IExpression last) throws SyntaxError {
		compileTextSliceFirst(context, method, flags, first);
		compileTextSliceLast(context, method, flags, last);
		MethodConstant m = new MethodConstant(String.class, "substring", 
				int.class, int.class, String.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, m);
		return parent;
	}
	
	private static void compileTextSliceFirst(Context context, MethodInfo method, Flags flags, IExpression first) throws SyntaxError {
		if(first==null)
			method.addInstruction(Opcode.ICONST_0);
		else {
			ResultInfo finfo = first.compile(context, method, flags.withPrimitive(true));
			finfo = CompilerUtils.numberToint(method, finfo);
			// convert from 1 based to 0 based
			method.addInstruction(Opcode.ICONST_M1);
			method.addInstruction(Opcode.IADD);
		}
	}

	private static void compileTextSliceLast(Context context, MethodInfo method, Flags flags, IExpression last) throws SyntaxError {
		// always compile last index since we need to manage negative values
		compileSliceMaxIndex(method);
		// stack is now obj, int, int (max)
		if(last!=null) {
			ResultInfo linfo = last.compile(context, method, flags.withPrimitive(true));
			linfo = CompilerUtils.numberToint(method, linfo);
			// stack is now obj, int, int (max), int (last)
			// manage negative index
			method.addInstruction(Opcode.DUP); // push last -> OIIII
			method.addInstruction(Opcode.IFGE, new ShortOperand((short)9)); // consume last -> OIII
			StackState branchState = method.captureStackState();
			method.addInstruction(Opcode.IADD); // add max to negative last -> OII
			method.addInstruction(Opcode.ICONST_1); // -> OIII
			method.addInstruction(Opcode.IADD); // add 1 to last -> OII
			method.addInstruction(Opcode.GOTO, new ShortOperand((short)5));
			method.restoreStackState(branchState);
			method.placeLabel(branchState);
			method.addInstruction(Opcode.SWAP); // swap max and last -> OIII
			method.addInstruction(Opcode.POP); // forget max -> OII
			StackState lastState = method.captureStackState();
			method.placeLabel(lastState);			
		}
	}

	private static void compileSliceMaxIndex(MethodInfo method) {
		// stack is obj, int we need obj, int, obj
		method.addInstruction(Opcode.SWAP); // -> int, obj
		method.addInstruction(Opcode.DUP_X1); // obj, int, obj
		MethodConstant m = new MethodConstant(String.class, "length", int.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, m);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Text)
			return value.equals(((Text) obj).value);
		else
			return value.equals(obj);
	}
	
	public static ResultInfo compileEquals(Context context, MethodInfo method, Flags flags, 
			ResultInfo left, IExpression exp) throws SyntaxError {
		exp.compile(context, method, flags);
		IOperand oper = flags.isRoughly() ?
				new MethodConstant(String.class, "equalsIgnoreCase", String.class, boolean.class) :
				new MethodConstant(String.class, "equals", Object.class, boolean.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, oper);
		if(flags.isReverse())
			CompilerUtils.reverseBoolean(method);
		if(flags.toPrimitive())
			return new ResultInfo(boolean.class);
		else
			return CompilerUtils.booleanToBoolean(method);
	}

    @Override
    public boolean roughly(Context context, IValue obj) throws PromptoError {
        if (obj instanceof Character || obj instanceof Text) {
        	Collator c = Collator.getInstance();
        	c.setStrength(Collator.PRIMARY);
        	return c.compare(value, obj.toString())==0;
        } else
            return false;
    }

	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public void toJson(Context context, JsonGenerator generator, IInstance instance, Identifier name) throws PromptoError {
		try {
			generator.writeString(value);
		} catch(IOException e) {
			throw new ReadWriteError(e.getMessage());
		}
	}

}


