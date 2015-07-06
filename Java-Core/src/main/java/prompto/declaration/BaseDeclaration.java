package prompto.declaration;

import prompto.grammar.Identifier;
import prompto.parser.Section;

public abstract class BaseDeclaration extends Section implements IDeclaration {

	private Identifier name;
	
	protected BaseDeclaration(Identifier name) {
		this.name = name;
	}
		
	@Override
	public Identifier getIdentifier() {
		return name;
	}
	
	public String getName() {
		return name.getName();
	}
	
	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}
	
}