package prompto.grammar;

import prompto.error.PromptoError;
import prompto.intrinsic.IterableWithLength;
import prompto.runtime.Context;
import prompto.store.IStorable;
import prompto.type.IType;
import prompto.utils.IValueIterable;
import prompto.utils.ObjectList;
import prompto.value.IInstance;
import prompto.value.IIterable;
import prompto.value.ISliceable;
import prompto.value.IValue;

import com.fasterxml.jackson.core.JsonGenerator;

@SuppressWarnings("serial")
public abstract class SymbolList <T extends Symbol> extends ObjectList<T> implements IValue, IIterable<IValue> {
	
	IType type;
	
	public SymbolList(T symbol) {
		this.add(symbol);
	}

	@Override
	public void storeValue(Context context, String name, IStorable storable) {
		throw new UnsupportedOperationException("Cannot store " + this.getClass().getSimpleName());
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
	
	@Override
	public IterableWithLength<IValue> getIterable(Context context) {
		return new IValueIterable<>(context, this);
	}
	
	
	public IValue plus(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Add not supported by " + this.getClass().getSimpleName());
	}

	public IValue minus(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Subtract not supported by " + this.getClass().getSimpleName());
	}

	public IValue multiply(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Multiply not supported by " + this.getClass().getSimpleName());
	}

	public IValue divide(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Divide not supported by " + this.getClass().getSimpleName());
	}

	public IValue intDivide(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Integer divide not supported by " + this.getClass().getSimpleName());
	}

	public IValue modulo(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Integer divide not supported by " + this.getClass().getSimpleName());
	}

	public int compareTo(Context context, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("Compare not supported by " + this.getClass().getSimpleName());
	}

	@Override
	public void setMember(Context context, Identifier name, IValue value) throws PromptoError {
		throw new UnsupportedOperationException("No member support for " + this.getClass().getSimpleName());
	}

	@Override
	public IValue getMember(Context context, Identifier name, boolean autoCreate) throws PromptoError {
		throw new UnsupportedOperationException("No member support for " + this.getClass().getSimpleName());
	}

	public Object convertTo(Class<?> type) {
		return this;
	}
	
	@Override
	public boolean roughly(Context context, IValue value) throws PromptoError {
		return this.equals(value);
	}
	
	@Override
	public ISliceable<IValue> asSliceable(Context context) throws PromptoError {
		return null;
	}
	
	@Override
	public void toJson(Context context, JsonGenerator generator, IInstance instance, Identifier name) throws PromptoError {
		throw new UnsupportedOperationException("toJson not supported by " + this.getClass().getSimpleName());
	}

}
