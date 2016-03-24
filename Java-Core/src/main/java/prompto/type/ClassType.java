package prompto.type;

import java.lang.reflect.Type;

import prompto.error.SyntaxError;
import prompto.runtime.Context;

public class ClassType extends BaseType {

	static ClassType instance = new ClassType();
	
	public static ClassType instance() {
		return instance;
	}
	
	private ClassType() {
		super(Family.TYPE);
	}

	@Override
	public Type getJavaType() {
		return Class.class;
	}

	@Override
	public void checkUnique(Context context) throws SyntaxError {
	}

	@Override
	public void checkExists(Context context) throws SyntaxError {
	}

	@Override
	public boolean isAssignableTo(Context context, IType other) {
		return false;
	}

	@Override
	public boolean isMoreSpecificThan(Context context, IType other) {
		return false;
	}

}
