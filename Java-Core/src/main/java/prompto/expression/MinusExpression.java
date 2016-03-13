package prompto.expression;

import java.util.HashMap;
import java.util.Map;

import prompto.compiler.Flags;
import prompto.compiler.IOperatorFunction;
import prompto.compiler.MethodInfo;
import prompto.compiler.ResultInfo;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoPeriod;
import prompto.intrinsic.PromptoTime;
import prompto.runtime.Context;
import prompto.type.IType;
import prompto.utils.CodeWriter;
import prompto.value.Date;
import prompto.value.DateTime;
import prompto.value.Decimal;
import prompto.value.IValue;
import prompto.value.Integer;
import prompto.value.Period;
import prompto.value.Time;

public class MinusExpression implements IExpression {

	IExpression left;
	IExpression right;
	
	public MinusExpression(IExpression left, IExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public void toDialect(CodeWriter writer) {
		left.toDialect(writer);
		writer.append(" - ");
		right.toDialect(writer);
	}
	
	@Override
	public IType check(Context context) throws SyntaxError {
		IType lt = left.check(context);
		IType rt = right.check(context);
		return lt.checkSubstract(context,rt);
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		IValue lval = left.interpret(context);
		IValue rval = right.interpret(context);
        return lval.minus(context, rval);
	}

	static Map<Class<?>, IOperatorFunction> minusers = createMinusers();
	
	private static Map<Class<?>, IOperatorFunction> createMinusers() {
		Map<Class<?>, IOperatorFunction> map = new HashMap<>();
		map.put(double.class, Decimal::compileMinus);
		map.put(Double.class, Decimal::compileMinus);
		map.put(long.class, Integer::compileMinus);
		map.put(Long.class, Integer::compileMinus);
		map.put(PromptoDate.class, Date::compileMinus);
		map.put(PromptoDateTime.class, DateTime::compileMinus); 
		map.put(PromptoTime.class, Time::compileMinus);
		map.put(PromptoPeriod.class, Period::compileMinus);
		return map;
	}

	@Override
	public ResultInfo compile(Context context, MethodInfo method, Flags flags) throws SyntaxError {
		ResultInfo lval = left.compile(context, method, flags);
		IOperatorFunction minuser = minusers.get(lval.getType());
		if(minuser==null) {
			System.err.println("Missing IOperatorFunction for minus " + lval.getType().getName());
			throw new SyntaxError("Cannot sub " + right.check(context).getName()  + " from " + lval.getType().getName() );
		}
		return minuser.compile(context, method, lval, right, flags);
	}

}
