package prompto.type;

import prompto.error.SyntaxError;
import prompto.grammar.Identifier;
import prompto.runtime.Context;


public class EnumeratedCategoryType extends CategoryType {

	public EnumeratedCategoryType(Identifier name) {
		super(name); 
	}
	
	@Override
	public IType checkMember(Context context, Identifier id) throws SyntaxError {
		String name = id.toString();
		if ("value".equals(name))
			return this;
		else if ("name".equals(name))
			return TextType.instance();
		else
			return super.checkMember(context, id);
	}

}