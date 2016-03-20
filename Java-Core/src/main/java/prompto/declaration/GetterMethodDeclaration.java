package prompto.declaration;

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
import prompto.statement.IStatement;
import prompto.statement.StatementList;
import prompto.type.CategoryType;
import prompto.type.IType;
import prompto.utils.CodeWriter;

public class GetterMethodDeclaration extends ConcreteMethodDeclaration implements IExpression {

	public GetterMethodDeclaration(Identifier id, StatementList statements) {
		super(id, null, null, statements);
	}
	
	public static String getNameAsKey(Identifier id) {
		return "getter:" + id.getName();
	}

	@Override
	public String getNameAsKey() {
		return getNameAsKey(getId());
	}

	@Override
	protected void toODialect(CodeWriter writer) {
		writer.append("getter ");
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
		writer.append(" getter doing:\n");
		writer.indent();
		statements.toDialect(writer);
		writer.dedent();
	}

	@Override
	protected void toSDialect(CodeWriter writer) {
		writer.append("def ");
		writer.append(getName());
		writer.append(" getter():\n");
		writer.indent();
		statements.toDialect(writer);
		writer.dedent();
	}

	@Override
	public void check(ConcreteCategoryDeclaration declaration, Context context) throws SyntaxError {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IType check(Context context) throws SyntaxError {
		AttributeDeclaration decl = context.getRegisteredDeclaration(AttributeDeclaration.class, getId());
		return decl.getType();
	}

	public void compile(Context context, ClassFile classFile, Flags flags, CategoryType type, FieldInfo field) throws SyntaxError {
		String name = CompilerUtils.getterName(this.getName());
		String proto = "()" + field.getDescriptor().getValue();
		MethodInfo method = new MethodInfo(name, proto);
		classFile.addMethod(method);
		method.registerLocal("this", IVerifierEntry.Type.ITEM_Object, classFile.getThisClass());
		context = context.newCategoryContext(type).newChildContext();
		for(IStatement stmt : statements)
			stmt.compile(context, method, flags);
	}


}
