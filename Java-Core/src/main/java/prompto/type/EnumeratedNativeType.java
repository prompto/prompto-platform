package prompto.type;

import prompto.declaration.IDeclaration;
import prompto.declaration.IEnumeratedDeclaration;
import prompto.error.InvalidDataError;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.value.IValue;

public class EnumeratedNativeType extends BaseType {

	NativeType derivedFrom;

	public EnumeratedNativeType(Identifier name, NativeType derivedFrom) {
		super(name);
		this.derivedFrom = derivedFrom;
	}

	public NativeType getDerivedFrom() {
		return derivedFrom;
	}

	@Override
	public Class<?> toJavaClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkUnique(Context context) throws SyntaxError {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkExists(Context context) throws SyntaxError {
		// TODO Auto-generated method stub

	}

	@Override
	public IType checkMember(Context context, Identifier id) throws SyntaxError {
		String name = id.toString();
		if ("symbols".equals(name))
			return new ListType(derivedFrom);
		else if ("value".equals(name))
			return this;
		else if ("name".equals(name))
			return TextType.instance();
		else
			return super.checkMember(context, id);
	}
	
	@Override
	public IValue getMember(Context context, Identifier id) throws PromptoError {
		String name = id.toString();
		IDeclaration decl = context.getRegisteredDeclaration(IDeclaration.class, this.name);
		if(!(decl instanceof IEnumeratedDeclaration))
			throw new SyntaxError(name + " is not an enumerated type!");
		if ("symbols".equals(name))
			return ((IEnumeratedDeclaration)decl).getSymbols();
		else
			throw new InvalidDataError("No such member:" + name);
	}
	
	@Override
	public boolean isAssignableTo(Context context, IType other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMoreSpecificThan(Context context, IType other) {
		// TODO Auto-generated method stub
		return false;
	}

}