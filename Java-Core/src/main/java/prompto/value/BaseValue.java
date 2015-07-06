package prompto.value;

import prompto.error.PromptoError;
import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.type.IType;

public abstract class BaseValue implements IValue {
	
	IType type;
	
	protected BaseValue(IType type) {
		this.type = type;
	}
	
	@Override
	public boolean isMutable() {
		return false;
	}
	
	public void setType(IType type) {
		this.type = type;
	}
	
	@Override
	public IType getType() {
		return type;
	}
	
	public IValue Add(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Add not supported by " + this.getClass().getSimpleName());
	}

	public IValue Subtract(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Subtract not supported by " + this.getClass().getSimpleName());
	}

	public IValue Multiply(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Multiply not supported by " + this.getClass().getSimpleName());
	}

	public IValue Divide(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Divide not supported by " + this.getClass().getSimpleName());
	}

	public IValue IntDivide(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Integer divide not supported by " + this.getClass().getSimpleName());
	}

	public IValue Modulo(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Integer divide not supported by " + this.getClass().getSimpleName());
	}

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
	public boolean Roughly(Context context, IValue value) throws PromptoError {
		return this.equals(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISliceable<IValue> asSliceable(Context context) throws PromptoError {
		return (this instanceof ISliceable) ? (ISliceable<IValue>)this : null;
	}
	
	public Object ConvertTo(Class<?> type) {
		return this;
	}
	

}