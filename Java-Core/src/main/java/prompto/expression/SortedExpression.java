package prompto.expression;

import java.lang.reflect.Type;
import java.util.Comparator;

import prompto.compiler.ClassConstant;
import prompto.compiler.ClassFile;
import prompto.compiler.CompilerUtils;
import prompto.compiler.Descriptor;
import prompto.compiler.Flags;
import prompto.compiler.IVerifierEntry;
import prompto.compiler.InterfaceConstant;
import prompto.compiler.MethodConstant;
import prompto.compiler.MethodInfo;
import prompto.compiler.Opcode;
import prompto.compiler.PromptoType;
import prompto.compiler.ResultInfo;
import prompto.compiler.Tags;
import prompto.declaration.CategoryDeclaration;
import prompto.declaration.IDeclaration;
import prompto.declaration.IMethodDeclaration;
import prompto.error.NullReferenceError;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.grammar.ArgumentAssignment;
import prompto.grammar.ArgumentAssignmentList;
import prompto.grammar.Identifier;
import prompto.grammar.UnresolvedIdentifier;
import prompto.intrinsic.PromptoList;
import prompto.runtime.Context;
import prompto.runtime.MethodFinder;
import prompto.statement.MethodCall;
import prompto.type.CategoryType;
import prompto.type.ContainerType;
import prompto.type.IType;
import prompto.type.ListType;
import prompto.type.SetType;
import prompto.utils.CodeWriter;
import prompto.value.ExpressionValue;
import prompto.value.IInstance;
import prompto.value.IValue;
import prompto.value.ListValue;
import prompto.value.SetValue;

public class SortedExpression implements IExpression {

	IExpression source;
	IExpression key;
	
	public SortedExpression(IExpression source) {
		this.source = source;
	}

	public SortedExpression(IExpression source, IExpression key) {
		this.source = source;
		this.key = key;
	}

	@Override
	public void toDialect(CodeWriter writer) {
		switch(writer.getDialect()) {
		case E:
			toEDialect(writer);
			break;
		case O:
			toODialect(writer);
			break;
		case S:
			toSDialect(writer);
			break;
		}
	}

	private void toEDialect(CodeWriter writer) {
		writer.append("sorted ");
		source.toDialect(writer);
		if(key!=null) {
			writer.append(" with ");
			IExpression keyExp = key;
			if(keyExp instanceof UnresolvedIdentifier) try {
				keyExp = ((UnresolvedIdentifier)keyExp).resolve(writer.getContext(), false);
			} catch (SyntaxError e) {
				// TODO add warning 
			}
			if(keyExp instanceof InstanceExpression)
				((InstanceExpression)keyExp).toDialect(writer, false);
			else
				keyExp.toDialect(writer);
			writer.append(" as key");
		}
	}	

	private void toODialect(CodeWriter writer) {
		writer.append("sorted (");
		source.toDialect(writer);
		if(key!=null) {
			writer.append(", key = ");
			key.toDialect(writer);
		}
		writer.append(")");
	}
	
	private void toSDialect(CodeWriter writer) {
		toODialect(writer);
	}
	
	
	@Override
	public IType check(Context context) throws SyntaxError {
		IType type = source.check(context);
		if(!(type instanceof ListType || type instanceof SetType))
			throw new SyntaxError("Unsupported type: " + type);
		return type;
	}
	
	@Override
	public IValue interpret(Context context) throws PromptoError {
		IType type = source.check(context);
		if(type instanceof ListType)
			return interpretList(context, (ListType)type);
		else if(type instanceof SetType)
			return interpretSet(context, (SetType)type);
		else
			throw new SyntaxError("Unsupported type: " + type);
	}

	
	private IValue interpretSet(Context context, SetType type) throws PromptoError {
		IValue value = source.interpret(context);
		if(value==null)
			throw new NullReferenceError();
		if(!(value instanceof SetValue))
			throw new InternalError("Unexpected type:" + value.getClass().getName());
		IType itemType = type.getItemType();
		Comparator<? extends IValue> cmp = getInterpretedComparator(context, itemType, value);
		PromptoList<? extends IValue> sorted = ((SetValue)value).getItems().sortUsing(cmp);
		return new ListValue(itemType, sorted);
	}

	private IValue interpretList(Context context, ListType type) throws PromptoError {
		IValue value = source.interpret(context);
		if(value==null)
			throw new NullReferenceError();
		if(!(value instanceof ListValue))
			throw new InternalError("Unexpected type:" + value.getClass().getName());
		IType itemType = type.getItemType();
		Comparator<? extends IValue> cmp = getInterpretedComparator(context, itemType, value);
		PromptoList<? extends IValue> sorted = ((ListValue)value).getItems().sortUsing(cmp);
		return new ListValue(itemType, sorted);
	}

	private Comparator<? extends IValue> getInterpretedComparator(Context context, IType itemType, IValue value) throws PromptoError {
		if(itemType instanceof CategoryType)
			return getCategoryComparator(context, (CategoryType)itemType, value);
		else
			return itemType.getComparator();	
	}

	private Comparator<? extends IValue> getCategoryComparator(Context context, CategoryType itemType, IValue value) throws PromptoError {
		if(key==null)
			key = new UnresolvedIdentifier(new Identifier("key"));
		Identifier keyAsId = new Identifier(key.toString());
		IDeclaration d = itemType.getDeclaration(context);
		if(d instanceof CategoryDeclaration) {
			CategoryDeclaration decl = (CategoryDeclaration)d;
			if(decl.hasAttribute(context, keyAsId))
				return getCategoryAttributeComparator(context, keyAsId);
			else if(decl.hasMethod(context, keyAsId, null))
				return getCategoryMethodComparator(context, keyAsId);
			else {
				MethodCall call = createGlobalMethodCall(context, itemType, keyAsId);
				if(call!=null)
					return getCategoryGlobalMethodComparator(context, itemType, call);
				else
					return getCategoryExpressionComparator(context);
			}
		} else
			throw new UnsupportedOperationException(); // TODO
	}

	private Comparator<? extends IValue> getCategoryExpressionComparator(Context context) {
		return new Comparator<IInstance>() {
			@Override
			public int compare(IInstance o1, IInstance o2) {
				try {
					IValue key1 = interpret(o1);
					IValue key2 = interpret(o2);
					return IValue.compareValues(key1, key2);
				} catch(Throwable t) {
					throw new RuntimeException(t);
				}
			}

			private IValue interpret(IInstance o) throws PromptoError {
				Context co = context.newInstanceContext(o);
				return key.interpret(co);
			}
		};
	}

	private Comparator<? extends IValue> getCategoryMethodComparator(Context context, Identifier identifier) {
		throw new UnsupportedOperationException(); // TODO
	}

	private MethodCall createGlobalMethodCall(Context context, CategoryType itemType, Identifier methodName) {
		try {
			IExpression exp = new ExpressionValue(itemType, itemType.newInstance(context));
			ArgumentAssignment arg = new ArgumentAssignment(null, exp); // MethodCall supports first anonymous argument
			ArgumentAssignmentList args = new ArgumentAssignmentList(arg);
			MethodCall call = new MethodCall(new MethodSelector(methodName), args);
			MethodFinder finder = new MethodFinder(context, call);
			IMethodDeclaration decl = finder.findMethod(true);
			if(decl==null)
				return null;
			else
				return call;
		} catch (PromptoError e) {
			return null;
		}
	}

	private Comparator<? extends IValue> getCategoryGlobalMethodComparator(Context context, CategoryType itemType, MethodCall call) throws PromptoError {
		return new Comparator<IInstance>() {
			@Override
			public int compare(IInstance o1, IInstance o2) {
				try {
					IValue key1 = interpret(o1);
					IValue key2 = interpret(o2);
					return IValue.compareValues(key1,key2);
				} catch(Throwable t) {
					throw new RuntimeException(t);
				}
			}

			private IValue interpret(IInstance o) throws PromptoError {
				ArgumentAssignment assignment = call.getAssignments().getFirst();
				assignment.setExpression(new ExpressionValue(itemType, o));
				return call.interpret(context);
			}
		};
	}

	private Comparator<? extends IValue> getCategoryAttributeComparator(Context context, Identifier name) {
		return new Comparator<IInstance>() {
			@Override
			public int compare(IInstance o1, IInstance o2) {
				try {
					IValue key1 = o1.getMember(context, name, false);
					IValue key2 = o2.getMember(context, name, false);
					return IValue.compareValues(key1, key2);
				} catch(Throwable t) {
					throw new RuntimeException(t);
				}
			}
		};
	}


	@Override
	public ResultInfo compile(Context context, MethodInfo method, Flags flags) throws SyntaxError {
		IType type = source.check(context);
		IType itemType = ((ContainerType)type).getItemType();
		if(itemType instanceof CategoryType) 
			return compileSortCategory(context, method, flags, (CategoryType)itemType);
		else
			return compileSortNative(context, method, flags);
	}

	private ResultInfo compileSortNative(Context context, MethodInfo method, Flags flags) throws SyntaxError {
		ResultInfo info = source.compile(context, method, flags);
		MethodConstant m = new MethodConstant(info.getType(), "sort", PromptoList.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, m);
		return new ResultInfo(PromptoList.class);
	}

	private ResultInfo compileSortCategory(Context context, MethodInfo method, Flags flags, CategoryType itemType) throws SyntaxError {
		ResultInfo srcinfo = source.compile(context, method, flags);
		compileCategoryComparator(context, method, flags, itemType);
		MethodConstant m = new MethodConstant(srcinfo.getType(), "sortUsing", Comparator.class, PromptoList.class);
		method.addInstruction(Opcode.INVOKEVIRTUAL, m);
		return new ResultInfo(PromptoList.class);
	}

	private ResultInfo compileCategoryComparator(Context context, MethodInfo method, Flags flags, CategoryType itemType) throws SyntaxError {
		if(key==null)
			key = new UnresolvedIdentifier(new Identifier("key"));
		IDeclaration decl = itemType.getDeclaration(context);
		if(decl instanceof CategoryDeclaration) {
			Type cmpType = compileCategoryComparatorClass(context, method, flags, itemType, (CategoryDeclaration)decl);
			return CompilerUtils.compileNewInstance(method, cmpType);
		} else
			throw new UnsupportedOperationException(); // TODO
	}

	private Type compileCategoryComparatorClass(Context context, MethodInfo method, Flags flags, CategoryType itemType, CategoryDeclaration decl) {
		Identifier keyAsId = new Identifier(key.toString());
		if(decl.hasAttribute(context, keyAsId))
			return compileCategoryAttributeComparatorClass(context, method.getClassFile(), itemType);
		else if(decl.hasMethod(context, keyAsId, null))
			return compileCategoryMethodComparator(context, keyAsId);
		else {
			MethodCall call = createGlobalMethodCall(context, itemType, keyAsId);
			if(call!=null)
				return compileCategoryGlobalMethodComparator(context, itemType, call);
			else
				return compileCategoryExpressionComparator(context);
		}
	}

	private Type compileCategoryAttributeComparatorClass(Context context, ClassFile parentClass, CategoryType itemType) {
		int innerClassIndex = 1 + parentClass.getInnerClasses().size();
		String innerClassName = parentClass.getThisClass().getType().getTypeName() + '$' + innerClassIndex;
		Type innerClassType = new PromptoType(innerClassName); 
		ClassFile classFile = new ClassFile(innerClassType);
		classFile.setSuperClass(new ClassConstant(Object.class));
		classFile.addInterface(new ClassConstant(Comparator.class));
		CompilerUtils.compileEmptyConstructor(classFile);
		compileCategoryAttributeComparatorBridge(context, classFile, itemType.getJavaType());
		compileCategoryAttributeComparatorMethod(context, classFile, itemType.getJavaType());
		parentClass.addInnerClass(classFile);
		return innerClassType;
	}

	private void compileCategoryAttributeComparatorMethod(Context context, ClassFile classFile, Type paramType) {
		Descriptor.Method proto = new Descriptor.Method(paramType, paramType, int.class);
		MethodInfo method = classFile.newMethod("compare", proto);
		method.registerLocal("this", IVerifierEntry.Type.ITEM_Object, classFile.getThisClass());
		method.registerLocal("o1", IVerifierEntry.Type.ITEM_Object, new ClassConstant(paramType));
		method.registerLocal("o2", IVerifierEntry.Type.ITEM_Object, new ClassConstant(paramType));
		method.addInstruction(Opcode.ALOAD_1, new ClassConstant(paramType));
		Type fieldType = context.findAttribute(key.toString()).getType().getJavaType();
		String getterName = CompilerUtils.getterName(key.toString());
		InterfaceConstant getter = new InterfaceConstant(paramType, getterName, fieldType);
		method.addInstruction(Opcode.INVOKEINTERFACE, getter);
		method.addInstruction(Opcode.ALOAD_2, new ClassConstant(paramType));
		method.addInstruction(Opcode.INVOKEINTERFACE, getter);
		proto = new Descriptor.Method(fieldType, int.class);
		MethodConstant c = new MethodConstant(new ClassConstant(fieldType), "compareTo", proto);
		method.addInstruction(Opcode.INVOKEVIRTUAL, c);
		method.addInstruction(Opcode.IRETURN);
	}

	private void compileCategoryAttributeComparatorBridge(Context context, ClassFile classFile, Type paramType) {
		// create a bridge "compare" method to convert Object -> paramType
		Descriptor.Method proto = new Descriptor.Method(Object.class, Object.class, int.class);
		MethodInfo method = classFile.newMethod("compare", proto);
		method.addModifier(Tags.ACC_BRIDGE | Tags.ACC_SYNTHETIC);
		method.registerLocal("this", IVerifierEntry.Type.ITEM_Object, classFile.getThisClass());
		method.registerLocal("o1", IVerifierEntry.Type.ITEM_Object, new ClassConstant(Object.class));
		method.registerLocal("o2", IVerifierEntry.Type.ITEM_Object, new ClassConstant(Object.class));
		method.addInstruction(Opcode.ALOAD_0, classFile.getThisClass());
		method.addInstruction(Opcode.ALOAD_1, new ClassConstant(Object.class));
		method.addInstruction(Opcode.CHECKCAST, new ClassConstant(paramType));
		method.addInstruction(Opcode.ALOAD_2, new ClassConstant(Object.class));
		method.addInstruction(Opcode.CHECKCAST, new ClassConstant(paramType));
		proto = new Descriptor.Method(paramType, paramType, int.class);
		MethodConstant c = new MethodConstant(classFile.getThisClass(), "compare", proto);
		method.addInstruction(Opcode.INVOKEVIRTUAL, c);
		method.addInstruction(Opcode.IRETURN);
	}

	private Type compileCategoryMethodComparator(Context context, Identifier keyAsId) {
		throw new UnsupportedOperationException();
	}

	private Type compileCategoryGlobalMethodComparator(Context context, CategoryType itemType, MethodCall call) {
		throw new UnsupportedOperationException();
	}

	private Type compileCategoryExpressionComparator(Context context) {
		throw new UnsupportedOperationException();
	}
	
}
