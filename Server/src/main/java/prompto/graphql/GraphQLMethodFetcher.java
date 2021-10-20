package prompto.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import prompto.declaration.IMethodDeclaration;
import prompto.java.JavaClassType;
import prompto.param.IParameter;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Context;
import prompto.type.IType;
import prompto.value.IValue;
import prompto.value.ListValue;
import prompto.value.NullValue;

public class GraphQLMethodFetcher implements DataFetcher<Object> {

	IMethodDeclaration method;
	
	public GraphQLMethodFetcher(IMethodDeclaration method) {
		this.method = method;
	}

	@Override
	public Object get(DataFetchingEnvironment environment) throws Exception {
		Context context = ApplicationContext.get().newLocalContext();
		method.getParameters().forEach(param->populateArgument(context, param, environment));
		Object result = method.interpret(context);
		if(result instanceof ListValue)
			result = ((ListValue)result).getItems();
		return result;
	}

	private void populateArgument(Context context, IParameter param, DataFetchingEnvironment environment) {
		context.registerValue(param);
		Object data = environment.getArgument(param.getName());
		IValue value = convert(context, data, param.getType(context));
		context.setValue(param.getId(), value);
	}

	private IValue convert(Context context, Object data, IType type) {
		if(data==null)
			return NullValue.instance();
		else
			return JavaClassType.convertJavaValueToPromptoValue(context, data, data.getClass(), type);
	}

}
