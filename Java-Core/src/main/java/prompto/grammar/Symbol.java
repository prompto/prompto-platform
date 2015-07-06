package prompto.grammar;

import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.parser.ISection;
import prompto.parser.Section;
import prompto.runtime.Context;
import prompto.type.IType;
import prompto.value.ISliceable;
import prompto.value.IValue;

public abstract class Symbol extends Section implements IExpression, INamed, IValue, ISection {

	Identifier symbol;
	IType type;

	protected Symbol(Identifier symbol) {
		this.symbol = symbol;
	}

	@Override
	public boolean isMutable() {
		return false;
	}
	
	public String getSymbol() {
		return symbol.toString();
	}
	
	@Override
	public Identifier getIdentifier() {
		return symbol;
	}
	
	@Override
	public IType getType() {
		return type;
	}
	
	@Override
	public IType getType(Context context) throws SyntaxError {
		return type;
	}

	public void setType(IType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return symbol.toString();
	}
	
	public void register(Context context) throws SyntaxError {
		context.registerValue(this);
	}

	@Override
	public IValue Add(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Add not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public IValue Subtract(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Subtract not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public IValue Multiply(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Multiply not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public IValue Divide(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Divide not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public IValue IntDivide(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Integer divide not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public IValue Modulo(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Integer divide not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public int CompareTo(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Compare not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public void setMember(Context context, Identifier name, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("No member support for " + this.getClass().getSimpleName());
	}

	@Override
	public IValue getMember(Context context, Identifier name) throws PromptoError {
		throw new UnsupportedOperationException("No member support for " + this.getClass().getSimpleName());
	}
	
	@Override
	public Object ConvertTo(Class<?> type) {
		return this;
	}
	
	@Override
	public boolean Roughly(Context context, IValue value) throws PromptoError {
		return this.equals(value);
	}
	
	@Override
	public ISliceable<IValue> asSliceable(Context context) throws PromptoError {
		return null;
	}
	
}