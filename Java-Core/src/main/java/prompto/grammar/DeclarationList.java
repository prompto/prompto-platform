package prompto.grammar;

import java.util.LinkedList;

import prompto.declaration.ConcreteMethodDeclaration;
import prompto.declaration.IDeclaration;
import prompto.error.SyntaxError;
import prompto.runtime.Context;
import prompto.utils.CodeWriter;


public class DeclarationList extends LinkedList<IDeclaration> {

	private static final long serialVersionUID = 1L;

	public DeclarationList() {
	}

	public DeclarationList(IDeclaration item) {
		this.add(item);
	}

	@Override
	public boolean add(IDeclaration decl) {
		if(decl!=null)
			return super.add(decl);
		else
			return false;
	}
	
	public void register(Context context) throws SyntaxError {
		for(IDeclaration declaration : this) {
			declaration.register(context);
		}
	}
	
	public void check(Context context) throws SyntaxError {
		for(IDeclaration declaration : this) {
			declaration.check(context);
		}
	}
	
	public ConcreteMethodDeclaration findMain() {
		for(IDeclaration declaration : this) {
			if(!(declaration instanceof ConcreteMethodDeclaration))
				continue;
			ConcreteMethodDeclaration method = (ConcreteMethodDeclaration)declaration;
			if(!(method.getIdentifier().equals("main")))
				continue;
			// TODO check proto
			return method;
		}
		return null;
	}

	public void toDialect(CodeWriter writer) {
		for(IDeclaration declaration : this) {
			declaration.toDialect(writer);
			writer.append("\n");
		}
	}
	

}