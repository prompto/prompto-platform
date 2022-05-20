package prompto.graphql;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import prompto.value.IValue;
import prompto.grammar.Identifier;
import prompto.runtime.ApplicationContext;
import prompto.value.ConcreteInstance;

public class GraphQLAttributeFetcher implements DataFetcher<Object> {

	Identifier attribute;
	
	public GraphQLAttributeFetcher(Identifier attribute) {
		this.attribute = attribute;
	}

	@Override
	public Object get(DataFetchingEnvironment environment) throws Exception {
		Object instance = environment.getSource();
		if(instance instanceof ConcreteInstance) {
			IValue value = ((ConcreteInstance)instance).getMember(ApplicationContext.get(), attribute, false);
			return GraphQLConverter.convertValue(value);
		} else
			return null;
	}

}
