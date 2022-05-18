package prompto.graphql;

import java.util.stream.Collectors;

import prompto.value.BooleanValue;
import prompto.value.ConcreteInstance;
import prompto.value.DecimalValue;
import prompto.value.IValue;
import prompto.value.IntegerValue;
import prompto.value.ListValue;
import prompto.value.SetValue;
import prompto.value.TextValue;

public abstract class GraphQLConverter {

	public static Object convertValue(IValue value) {
		// convert scalar values for which we don't provide a custom scalar
		if(value instanceof BooleanValue)
			return ((BooleanValue)value).getValue();
		else if(value instanceof IntegerValue)
			return ((IntegerValue)value).longValue();
		else if (value instanceof DecimalValue)
			return ((DecimalValue)value).doubleValue();
		else if(value instanceof TextValue)
			return ((TextValue)value).toString();
		else if(value instanceof ListValue)
			return ((ListValue)value).getItems().stream().map(GraphQLConverter::convertValue).collect(Collectors.toList());
		else if(value instanceof SetValue)
			return ((SetValue)value).getItems().stream().map(GraphQLConverter::convertValue).collect(Collectors.toList());
		else if(value instanceof ConcreteInstance)
			return value;
		else if(GraphQLScalars.get(value.getType())!=null)
			return value;
		else
			return value.toString();
	}


}
