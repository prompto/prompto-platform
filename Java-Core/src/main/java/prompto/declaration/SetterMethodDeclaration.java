package prompto.declaration;

import prompto.compiler.ClassConstant;
import prompto.compiler.ClassFile;
import prompto.compiler.CompilerUtils;
import prompto.compiler.FieldInfo;
import prompto.compiler.Flags;
import prompto.compiler.IVerifierEntry;
import prompto.compiler.MethodInfo;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.grammar.Identifier;
import prompto.runtime.Context;
import prompto.runtime.Variable;
import prompto.statement.IStatement;
import prompto.statement.StatementList;
import prompto.type.CategoryType;
import prompto.type.IType;
import prompto.utils.CodeWriter;

public class SetterMethodDeclaration extends ConcreteMethodDeclaration implements IExpression {

	public SetterMethodDeclaration(Identifier id, StatementList statements) {
		super(id, null, null, statements);
	}

	@Override
	protected void toODialect(CodeWriter writer) {
		writer.append("setter ");
		writer.append(getName());
		writer.append(" {\n");
		writer.indent();
		statements.toDialect(writer);
		writer.dedent();
		writer.append("}\n");
	}

	@Override
	protected void toEDialect(CodeWriter writer) {
		writer.append("define ");
		writer.append(getName());
		writer.append(" setter doing:\n");
		writer.indent();
		statements.toDialect(writer);
		writer.dedent();
	}	

	@Override
	protected void toSDialect(CodeWriter writer) {
		writer.append("def ");
		writer.append(getName());
		writer.append(" setter():\n");
		writer.indent();
		statements.toDialect(writer);
		writer.dedent();
	}	

	@Override
	public void check(ConcreteCategoryDeclaration category, Context context) {
		// TODO Auto-generated method stub
	}
	

	@Override
	public IType check(Context context) throws SyntaxError {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void compile(Context context, ClassFile classFile) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void compile(Context context, ClassFile classFile, Flags flags,
			CategoryType type, FieldInfo field) throws SyntaxError {
		String name = CompilerUtils.setterName(this.getName());
		String proto = "(" + field.getDescriptor().getValue() + ")V";
		MethodInfo method = new MethodInfo(name, proto);
		classFile.addMethod(method);
		method.registerLocal("this", IVerifierEntry.Type.ITEM_Object, classFile.getThisClass());
		AttributeDeclaration decl = context.getRegisteredDeclaration(AttributeDeclaration.class, getId());
		method.registerLocal(getName(), IVerifierEntry.Type.ITEM_Object, new ClassConstant(field.getClassName()));
		context = context.newCategoryContext(type).newChildContext();
		context.registerValue(new Variable(getId(), decl.getType()));
		for(IStatement stmt : statements)
			stmt.compile(context, method, flags.withSetter(field));
	}

}
