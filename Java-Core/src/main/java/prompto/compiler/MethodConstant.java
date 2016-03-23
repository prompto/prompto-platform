package prompto.compiler;

import java.lang.reflect.Type;

import prompto.compiler.Descriptor.Method;

public class MethodConstant implements ICodeConstant {

	ClassConstant className;
	NameAndTypeConstant methodNameAndType;
	int index;
	
	public MethodConstant(Type klass, String methodName, Type ... params) {
		this(new ClassConstant(klass), methodName, new Descriptor.Method(params));
	}
	
	public MethodConstant(Type klass, String methodName, Method method) {
		this(new ClassConstant(klass), methodName, method);
	}

	public MethodConstant(ClassConstant klass, String methodName, Type ... params) {
		this(klass, methodName, new Descriptor.Method(params));
	}

	public MethodConstant(ClassConstant klass, String methodName, Method method) {
		this(klass, new NameAndTypeConstant(methodName, method));
	}
	
	public MethodConstant(ClassConstant klass, NameAndTypeConstant nameAndType) {
		this.className = klass;
		this.methodNameAndType = nameAndType;
	}



	public short getArgumentsCount(boolean isStatic) {
		Type[] types = methodNameAndType.getDescriptor().getTypes();
		return (short)(types.length - (isStatic ? 1 : 0));
	}

	public StackEntry resultToStackEntry() {
		String descriptor = methodNameAndType.getDescriptor().getLastDescriptor();
		if("V".equals(descriptor))
			return null;
		IVerifierEntry.Type type = IVerifierEntry.Type.fromDescriptor(descriptor);
		StackEntry entry = type.newStackEntry(null);
		if(entry instanceof StackEntry.ObjectEntry)
			((StackEntry.ObjectEntry)entry).setClassName(new ClassConstant(methodNameAndType.getDescriptor().getLastType()));
		return entry;
	}

	@Override
	public String toString() {
		return className.toString() + ':' + methodNameAndType.toString();
	}
	
	@Override
	public int getIndexInConstantPool() {
		if(index==-1)
			throw new UnsupportedOperationException();
		return index;
	}
	
	@Override
	public void register(ConstantsPool pool) {
		index = pool.registerConstant(this);
		className.register(pool);
		methodNameAndType.register(pool);
	}
	
	@Override
	public void writeTo(ByteWriter writer) {
		/*
		CONSTANT_Methodref_info {
		    u1 tag;
		    u2 class_index;
		    u2 name_and_type_index;
		}		
		*/
		writer.writeU1(Tags.CONSTANT_Methodref);
		writer.writeU2(className.getIndexInConstantPool());
		writer.writeU2(methodNameAndType.getIndexInConstantPool());
	}



}
