package prompto.type;

import prompto.error.SyntaxError;
import prompto.runtime.Context;


public class SetType extends CollectionType {

	public SetType(IType itemType) {
		super(itemType.getName()+"<>", itemType);
	}
	
	@Override
	public Class<?> toJavaClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IType checkIterator(Context context) throws SyntaxError {
		return itemType;
	}
	
	@Override
	public IType checkAdd(Context context, IType other, boolean tryReverse) throws SyntaxError {
		if(other instanceof CollectionType) {
			IType itemType = ((CollectionType)other).getItemType();
			if((other instanceof ListType || other instanceof SetType) 
					&& this.getItemType().equals(itemType))
				return this;
		} 
		return super.checkAdd(context, other, tryReverse);
	}
	
	@Override
	public IType checkItem(Context context, IType other) throws SyntaxError {
		if(other==IntegerType.instance())
			return itemType;
		else
			return super.checkItem(context,other);
	}

	@Override
	public IType checkContainsAllOrAny(Context context, IType other) throws SyntaxError {
		return BooleanType.instance();
	}
	

}