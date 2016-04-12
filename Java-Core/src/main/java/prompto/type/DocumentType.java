package prompto.type;

import java.lang.reflect.Type;

import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoDocument;
import prompto.runtime.Context;

public class DocumentType extends NativeType {
	
	static DocumentType instance = new DocumentType();
	
	public static DocumentType instance() {
		return instance;
	}
	
	private DocumentType() {
		super(Family.DOCUMENT);
	}

	@Override
	public Type getJavaType(Context context) {
		return PromptoDocument.class;
	}
	
	@Override
	public boolean isAssignableTo(Context context, IType other) {
		return (other instanceof DocumentType) || (other instanceof AnyType);
	}
	
	@Override
	public IType checkMember(Context context, Identifier name) {
		return AnyType.instance();
	}
	

}
