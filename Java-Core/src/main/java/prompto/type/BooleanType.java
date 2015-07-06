package prompto.type;

import prompto.error.PromptoError;
import prompto.runtime.Context;
import prompto.value.Boolean;
import prompto.value.ICollection;
import prompto.value.IValue;
import prompto.value.ListValue;

public class BooleanType extends NativeType {
	
	static BooleanType instance = new BooleanType();
	
	public static BooleanType instance() {
		return instance;
	}
	
	private BooleanType() {
		super("Boolean");
	}

	@Override
	public Class<?> toJavaClass() {
		return java.lang.Boolean.class;
	}
	
	@Override
	public boolean isAssignableTo(Context context, IType other) {
		return (other instanceof BooleanType) || (other instanceof AnyType);
	}
	
	@Override
	public ListValue sort(Context context, ICollection<IValue> list) throws PromptoError {
		return this.doSort(context,list);
	}
	
	@Override
	public IValue convertJavaValueToPromptoValue(Object value) {
        if (value instanceof java.lang.Boolean)
            return Boolean.ValueOf(((java.lang.Boolean)value).booleanValue());
        else
            return (IValue)value; // TODO for now
	}

}