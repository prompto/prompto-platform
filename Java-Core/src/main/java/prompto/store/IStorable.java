package prompto.store;

import prompto.error.PromptoError;
import prompto.grammar.Identifier;
import prompto.value.IValue;


public interface IStorable {

	IValue getOrCreateDbId();

	void setDirty(boolean dirty);
	boolean isDirty();
	
	default void setValue(Identifier name, IValue value) throws PromptoError {
		setValue(name, value, null);
	}
	
	void setValue(Identifier name, IValue value, IDbIdProvider provider) throws PromptoError;
	
	void setData(String name, Object value) throws PromptoError;


	public static interface IDbIdProvider {
		IValue getDbId();
	}
	
}
