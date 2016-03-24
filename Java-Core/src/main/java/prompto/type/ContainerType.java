package prompto.type;

import prompto.error.SyntaxError;
import prompto.runtime.Context;

public abstract class ContainerType extends IterableType {

	protected ContainerType(Family family, IType itemType, String fullName) {
		super(family, itemType, fullName);
	}
	
	@Override
	public IType checkContains(Context context, IType other) throws SyntaxError {
		if(itemType.isAssignableTo(context, other))
			return BooleanType.instance();
		else
			return super.checkContains(context, other);
	}
	
}
