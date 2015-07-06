package prompto.grammar;

import prompto.declaration.AttributeDeclaration;
import prompto.declaration.IDeclaration;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.parser.Dialect;
import prompto.runtime.Context;
import prompto.type.IType;
import prompto.utils.CodeWriter;
import prompto.utils.Utils;
import prompto.value.IValue;

public class AttributeArgument extends BaseArgument implements INamedArgument {
	
	public AttributeArgument(Identifier name) {
		super(name);
	}

	@Override
	public String getSignature(Dialect dialect) {
		return name.toString();
	}
	
	@Override
	public void toDialect(CodeWriter writer) {
		writer.append(name);
		if(defaultExpression!=null) {
			writer.append(" = ");
			defaultExpression.toDialect(writer);
		}
	}
	
	@Override
	public String toString() {
		return name.toString();
	}
	
	@Override
	public String getProto(Context context) {
		return name.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==this)
			return true;
		if(obj==null)
			return false;
		if(!(obj instanceof AttributeArgument))
			return false;
		AttributeArgument other = (AttributeArgument)obj;
		return Utils.equal(this.getIdentifier(),other.getIdentifier());
	}

	@Override
	public void register(Context context) throws SyntaxError {
		context.registerValue(this, true);
		if(defaultExpression!=null) try {
			context.setValue(name, defaultExpression.interpret(context));
		} catch(PromptoError error) {
			throw new SyntaxError("Unable to register default value: "+ defaultExpression.toString() + " for argument: " + name);
		}
	}
	
	@Override
	public void check(Context context) throws SyntaxError {
		AttributeDeclaration actual = context.getRegisteredDeclaration(AttributeDeclaration.class,name);
		if(actual==null)
			throw new SyntaxError("Unknown attribute: \"" + name + "\"");
	}
	
	@Override
	public IType getType(Context context) throws SyntaxError {
		IDeclaration named = context.getRegisteredDeclaration(IDeclaration.class,name);
		return named.getType(context);
	}
	
	@Override
	public IValue checkValue(Context context, IExpression expression) throws PromptoError {
		AttributeDeclaration actual = context.getRegisteredDeclaration(AttributeDeclaration.class,name);
		return actual.checkValue(context, expression);
	}
	
}