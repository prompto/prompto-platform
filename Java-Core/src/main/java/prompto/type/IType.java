package prompto.type;

import java.lang.reflect.Type;
import java.util.Comparator;

import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.grammar.Identifier;
import prompto.parser.ISection;
import prompto.runtime.Context;
import prompto.utils.CodeWriter;
import prompto.value.IValue;
import prompto.value.RangeBase;

import com.fasterxml.jackson.databind.JsonNode;

public interface IType {
	
	Identifier getId();
	String getName();
	Type getJavaType();

	IType checkAdd(Context context, IType other, boolean tryReverse) throws SyntaxError;
	IType checkSubstract(Context context, IType other) throws SyntaxError;
	IType checkDivide(Context context, IType other) throws SyntaxError;
	IType checkIntDivide(Context context, IType other) throws SyntaxError;
	IType checkMultiply(Context context, IType other, boolean tryReverse) throws SyntaxError;
	IType checkModulo(Context context, IType rt) throws SyntaxError;
	IType checkCompare(Context context, IType other, ISection section) throws SyntaxError;
	IType checkItem(Context context, IType itemType)  throws SyntaxError;
	IType checkRange(Context context, IType other) throws SyntaxError;
	IType checkContains(Context context, IType other) throws SyntaxError;
	IType checkContainsAllOrAny(Context context, IType other) throws SyntaxError;
	IType checkIterator(Context context) throws SyntaxError;
	IType checkSlice(Context context) throws SyntaxError;
	IType checkMember(Context context, Identifier name) throws SyntaxError;
	
	void checkUnique(Context context) throws SyntaxError;
	void checkExists(Context context) throws SyntaxError;

	void checkAssignableTo(Context context, IType other) throws SyntaxError;
	boolean isAssignableTo(Context context, IType other);
	boolean isMoreSpecificThan(Context context, IType other);
	
	RangeBase<?> newRange(Object first,Object last) throws SyntaxError;
	Comparator<? extends IValue> getComparator();
	IValue getMember(Context context, Identifier name) throws PromptoError;
	String toString(Object value);
	void toDialect(CodeWriter writer);
	IValue readJSONValue(Context context, JsonNode value);
	IValue convertJavaValueToPromptoValue(Object value);
}
 