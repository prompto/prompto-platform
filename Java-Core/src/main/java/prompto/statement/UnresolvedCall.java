package prompto.statement;

import prompto.declaration.CategoryDeclaration;
import prompto.declaration.ConcreteCategoryDeclaration;
import prompto.declaration.IDeclaration;
import prompto.declaration.TestMethodDeclaration;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.ConstructorExpression;
import prompto.expression.IAssertion;
import prompto.expression.IExpression;
import prompto.expression.MemberSelector;
import prompto.expression.MethodSelector;
import prompto.grammar.ArgumentAssignmentList;
import prompto.grammar.Identifier;
import prompto.grammar.UnresolvedIdentifier;
import prompto.runtime.Context;
import prompto.runtime.Context.InstanceContext;
import prompto.runtime.Context.MethodDeclarationMap;
import prompto.type.CategoryType;
import prompto.type.IType;
import prompto.utils.CodeWriter;
import prompto.value.IValue;

public class UnresolvedCall extends SimpleStatement implements IAssertion {
	
	IExpression resolved;
	IExpression caller;
	ArgumentAssignmentList assignments;
	
	public UnresolvedCall(IExpression caller, ArgumentAssignmentList assignments) {
		this.caller = caller;
		this.assignments = assignments;
	}
	
	public IExpression getCaller() {
		return caller;
	}
	
	public ArgumentAssignmentList getAssignments() {
		return assignments;
	}
	
	@Override
	public void toDialect(CodeWriter writer) {
		try {
			resolve(writer.getContext());
			resolved.toDialect(writer);
		} catch(SyntaxError error) {
			caller.toDialect(writer);
			if(assignments!=null)
				assignments.toDialect(writer);
		}
	}
	
	@Override
	public IType check(Context context) throws SyntaxError {
		return resolveAndCheck(context);
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		if(resolved==null)
			resolveAndCheck(context);
		return resolved.interpret(context);
	}

	@Override
	public boolean interpretAssert(Context context, TestMethodDeclaration testMethodDeclaration) throws PromptoError {
		if(resolved==null)
			resolveAndCheck(context);
		if(resolved instanceof IAssertion)
			return ((IAssertion)resolved).interpretAssert(context, testMethodDeclaration);
		else {
			CodeWriter writer = new CodeWriter(this.getDialect(), context);
			resolved.toDialect(writer);
			throw new SyntaxError("Cannot test '" + writer.toString() + "'");
		}
	}
	
	private IType resolveAndCheck(Context context) throws SyntaxError {
		resolve(context);
		return resolved.check(context);
	}
	
	
	private void resolve(Context context) throws SyntaxError {
		if(resolved!=null)
			return;
		if(caller instanceof UnresolvedIdentifier)
			resolved = resolveUnresolvedIdentifier(context);
		else if(caller instanceof MemberSelector)
			resolved = resolveMember(context);
	}
	
	private IExpression resolveUnresolvedIdentifier(Context context) throws SyntaxError {
		Identifier name = ((UnresolvedIdentifier)caller).getName();
		IDeclaration decl = null;
		// if this happens in the context of a member method, then we need to check for category members first
		if(context.getParentContext() instanceof InstanceContext) {
			decl = resolveUnresolvedMember((InstanceContext)context.getParentContext(), name);
			if(decl!=null)
				return new MethodCall(new MethodSelector(name), assignments);
		}
		decl = context.getRegisteredDeclaration(IDeclaration.class, name);
		if(decl==null)
			throw new SyntaxError("Unknown name:" + name);
		if(decl instanceof CategoryDeclaration)
			return new ConstructorExpression(new CategoryType(name), false, assignments);
		else
			return new MethodCall(new MethodSelector(name), assignments);
	}

	private IDeclaration resolveUnresolvedMember(InstanceContext context, Identifier name) throws SyntaxError {
		ConcreteCategoryDeclaration decl = context.getRegisteredDeclaration(ConcreteCategoryDeclaration.class, context.getInstanceType().getName());
		MethodDeclarationMap methods = decl.getMemberMethods(context, name);
		if(methods!=null && methods.size()>0)
			return methods;
		else
			return null;
	}

	private IExpression resolveMember(Context context) throws SyntaxError {
		IExpression parent = ((MemberSelector)caller).getParent();
		Identifier name = ((MemberSelector)caller).getName();
		return new MethodCall(new MethodSelector(parent, name), assignments);
	}

}