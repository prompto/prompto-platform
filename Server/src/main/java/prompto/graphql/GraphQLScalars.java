package prompto.graphql;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import graphql.Scalars;
import graphql.schema.GraphQLScalarType;
import prompto.type.BooleanType;
import prompto.type.DecimalType;
import prompto.type.IType;
import prompto.type.IntegerType;
import prompto.type.TextType;

public abstract class GraphQLScalars {
	
	static final Map<IType, GraphQLScalarType> ALL = buildAll();

	static Map<IType, GraphQLScalarType> buildAll() {
		return Arrays.asList(
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(BooleanType.instance(), Scalars.GraphQLBoolean),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(IntegerType.instance(), Scalars.GraphQLLong),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(DecimalType.instance(), Scalars.GraphQLFloat),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(TextType.instance(), Scalars.GraphQLString))
				.stream()
				.collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
	}
	
	public static boolean isScalar(IType type) {
		return ALL.containsKey(type);
	}
	
	public static GraphQLScalarType get(IType type) {
		return ALL.get(type);
	}

}
