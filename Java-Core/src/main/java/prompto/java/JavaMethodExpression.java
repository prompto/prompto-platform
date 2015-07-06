package prompto.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import prompto.declaration.IDeclaration;
import prompto.declaration.NativeCategoryDeclaration;
import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.expression.IExpression;
import prompto.runtime.Context;
import prompto.type.CategoryType;
import prompto.type.IType;
import prompto.utils.CodeWriter;
import prompto.value.IValue;
import prompto.value.NativeInstance;


public class JavaMethodExpression extends JavaSelectorExpression {

	String name;
	JavaExpressionList arguments;
	
	public JavaMethodExpression(String name, JavaExpressionList arguments) {
		this.name = name;
		this.arguments = arguments!=null ? arguments : new JavaExpressionList();
	}

	@Override
	public void toDialect(CodeWriter writer) {
		parent.toDialect(writer);
		writer.append('.');
		writer.append(name);
		writer.append('(');
		arguments.toDialect(writer);
		writer.append(')');
	}
	
	@Override
	public String toString() {
		return parent.toString() + "." + name + "(" + arguments.toString() + ")";
	}
	
	@Override
	public IType check(Context context) throws SyntaxError {
		Method method = findMethod(context);
		if(method==null)
			return null;
		else
			return new JavaClassType(method.getReturnType());
	}
	
	@Override
	public Object interpret(Context context) throws PromptoError {
		Object instance = parent.interpret(context);
		if(instance instanceof NativeInstance)
			instance = ((NativeInstance)instance).getInstance();
		Method method = findMethod(context, instance);
		Object[] args = evaluate_arguments(context, method);
		Class<?> klass = instance instanceof Class<?> ? (Class<?>)instance : instance.getClass(); 
		if(klass==instance)
			instance = null;
		try {
			return method.invoke(instance, args);
		} catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	Object[] evaluate_arguments(Context context, Method method) throws PromptoError {
		Object[] args = new Object[arguments.size()];
		Class<?>[] types = method.getParameterTypes();
		for(int i=0;i<args.length;i++) {
			JavaExpression exp = arguments.get(i);
			args[i] = evaluate_argument(context, exp, types[i]);
		}
		return args;
	}

	  Object evaluate_argument(Context context, JavaExpression expression, Class<?> type) throws PromptoError
	    {
	        Object value = expression.interpret(context);
	        if (value instanceof IExpression)
	            value = ((IExpression)value).interpret(context);
	        if (value instanceof IValue)
	            value = ((IValue)value).ConvertTo(type);
	        return value;
	    }

	public Method findMethod(Context context) throws SyntaxError {
		IType type = parent.check(context);
		Class<?> klass = null;
		if (type instanceof JavaClassType)
			klass = ((JavaClassType)type).klass;
		else if(type instanceof CategoryType) {
			IDeclaration named = context.getRegisteredDeclaration(IDeclaration.class, type.getName());
			if(named instanceof NativeCategoryDeclaration) 
				klass = ((NativeCategoryDeclaration)named).getBoundClass(true);
		} else 
			klass = type.toJavaClass();
		return findMethod(context, klass);
	}
	
	public Method findMethod(Context context, Object instance) throws SyntaxError {
		if(instance instanceof Class<?>)
			return findMethod(context, (Class<?>)instance);
		else
			return findMethod(context, instance.getClass());
	}
	
	public Method findMethod(Context context, Class<?> klass) throws SyntaxError {
		if(klass==null)
			return null;
		Method[] methods = klass.getMethods();
		for(Method m : methods) {
			if(!name.equals(m.getName())) 
				continue;
			if(validPrototype(context,m))
				return m;
		}
		return null; 
	}
	
	boolean validPrototype(Context context,Method method) throws SyntaxError  {
		Class<?>[] types = method.getParameterTypes();
		if(types.length!=arguments.size())
			return false;
		for(int i=0;i<types.length;i++) {
			if(!validArgument(context, types[i], arguments.get(i)))
				return false;
		}
		return true;
	}
	
	boolean validArgument(Context context, Class<?> klass, JavaExpression argument) throws SyntaxError {
		IType type = argument.check(context);
		return klass.isAssignableFrom(type.toJavaClass());
	}
}