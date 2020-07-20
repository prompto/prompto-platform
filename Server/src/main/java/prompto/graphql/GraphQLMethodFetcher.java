package prompto.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import prompto.declaration.IMethodDeclaration;
import prompto.runtime.ApplicationContext;

public class GraphQLMethodFetcher implements DataFetcher<Object> {

	IMethodDeclaration method;
	
	public GraphQLMethodFetcher(IMethodDeclaration method) {
		this.method = method;
	}

	@Override
	public Object get(DataFetchingEnvironment environment) throws Exception {
		return method.interpret(ApplicationContext.get());
	}

}
