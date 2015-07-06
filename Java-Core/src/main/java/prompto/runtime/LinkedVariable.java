package prompto.runtime;

import prompto.error.SyntaxError;
import prompto.grammar.INamed;
import prompto.grammar.Identifier;
import prompto.type.IType;

/* used for downcast */
public class LinkedVariable implements INamed {

	IType type;
	INamed linked;
	
	public LinkedVariable(IType type, INamed linked) {
		this.type = type;
		this.linked = linked;
	}

	@Override
	public Identifier getIdentifier() {
		return linked.getIdentifier();
	}
	
	@Override
	public IType getType(Context context) throws SyntaxError {
		return type;
	}
}