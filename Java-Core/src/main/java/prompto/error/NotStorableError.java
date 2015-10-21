package prompto.error;

import prompto.expression.IExpression;
import prompto.grammar.CategorySymbol;
import prompto.grammar.Identifier;
import prompto.runtime.Context;


public class NotStorableError extends ExecutionError {

	private static final long serialVersionUID = 1L;

	@Override
	public IExpression getExpression(Context context) {
		return context.getRegisteredValue(CategorySymbol.class, new Identifier("NOT_STORABLE"));
	}
}