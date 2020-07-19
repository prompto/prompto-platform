package prompto.graphql;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import prompto.code.ICodeStore;
import prompto.declaration.IDeclaration;
import prompto.declaration.IMethodDeclaration;
import prompto.param.IParameter;
import prompto.runtime.ApplicationContext;
import prompto.type.IType;

public class GraphQLSchemaBuilder {

	static final String QUERIES_TYPE_NAME = "Queries";
	static final String MUTATIONS_TYPE_NAME = "Mutations";
	
	public GraphQLSchema build() {
		GraphQLCodeRegistry.Builder registry = GraphQLCodeRegistry.newCodeRegistry(); 
		return GraphQLSchema.newSchema()
				.query(buildQueries(registry))
				.mutation(buildMutations(registry))
				.codeRegistry(registry.build())
				.build();
	}

	
	private GraphQLObjectType buildQueries(GraphQLCodeRegistry.Builder registry) {
		return buildRootType(registry, "@GraphQLQuery", QUERIES_TYPE_NAME);
	}

	private GraphQLObjectType buildMutations(GraphQLCodeRegistry.Builder registry) {
		return buildRootType(registry, "@GraphQLMutation", MUTATIONS_TYPE_NAME);
	}
	
	
	private GraphQLObjectType buildRootType(GraphQLCodeRegistry.Builder registry, String annotation, String typeName) {
		Iterable<IDeclaration> queries = ICodeStore.getInstance().fetchDeclarationsWithAnnotations(Collections.singleton(annotation));
		List<GraphQLFieldDefinition> fields = StreamSupport.stream(queries.spliterator(), false)
				.filter(d->d instanceof IMethodDeclaration)
				.map(d->(IMethodDeclaration)d)
				.filter(d->d.getMemberOf()==null)
				.map(d->buildRootField(d, typeName, registry))
				.collect(Collectors.toList());
		if(fields.isEmpty())
			return null;
		else {
			return GraphQLObjectType.newObject()
					.name(typeName)
					.fields(fields)
					.build();
		}
	}

	private GraphQLFieldDefinition buildRootField(IMethodDeclaration decl, String typeName, GraphQLCodeRegistry.Builder registry) {
		registry.dataFetcher(FieldCoordinates.coordinates(typeName, decl.getName()), new GraphQLMethodFetcher(decl));
		IType returnType = decl.check(ApplicationContext.get());
		return GraphQLFieldDefinition.newFieldDefinition()
				.name(decl.getName())
				.arguments(buildArguments(decl, registry))
				.type(GraphQLType.buildOutputType(returnType, registry))
				.build();
	}


	private List<GraphQLArgument> buildArguments(IMethodDeclaration decl, GraphQLCodeRegistry.Builder registry) {
		return decl.getParameters().stream()
			.map(param->buildArgument(param, registry))
			.collect(Collectors.toList());
	}


	private GraphQLArgument buildArgument(IParameter param, GraphQLCodeRegistry.Builder registry) {
		return GraphQLArgument.newArgument()
				.name(param.getName())
				.type(GraphQLType.buildInputType(param.getType(ApplicationContext.get()), registry))
				.build();
	}




}
