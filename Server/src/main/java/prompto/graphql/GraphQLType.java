package prompto.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import graphql.Scalars;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLCodeRegistry.Builder;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLUnionType;
import prompto.declaration.AttributeDeclaration;
import prompto.declaration.CategoryDeclaration;
import prompto.declaration.IDeclaration;
import prompto.declaration.IEnumeratedDeclaration;
import prompto.expression.Symbol;
import prompto.grammar.Identifier;
import prompto.runtime.ApplicationContext;
import prompto.type.CategoryType;
import prompto.type.CursorType;
import prompto.type.IEnumeratedType;
import prompto.type.IType;
import prompto.type.ListType;
import prompto.value.ConcreteInstance;

public interface GraphQLType {

	Map<String, GraphQLType> TYPE_BY_NAME_MAP = new HashMap<>();
	
	static GraphQLInputType buildInputType(IType type, GraphQLCodeRegistry.Builder registry) {
		if(GraphQLScalars.isScalar(type))
			return GraphQLScalars.get(type);
		else
			return build(type, registry).inputType();
	}

	static GraphQLOutputType buildOutputType(IType type, GraphQLCodeRegistry.Builder registry) {
		if(GraphQLScalars.isScalar(type))
			return GraphQLScalars.get(type);
		else
			return build(type, registry).outputType();
	}

	
	GraphQLInputType inputType();
	GraphQLOutputType outputType();
	IType reflectedType();
	
	static GraphQLType build(IType type, GraphQLCodeRegistry.Builder registry) {
		synchronized(TYPE_BY_NAME_MAP) {
			if(!TYPE_BY_NAME_MAP.containsKey(type.getTypeName())) {
				TYPE_BY_NAME_MAP.put(type.getTypeName(), null); // avoid reentrance
				GraphQLType gql = doBuild(type, registry);
				TYPE_BY_NAME_MAP.put(type.getTypeName(), gql);
			}
			return TYPE_BY_NAME_MAP.get(type.getTypeName());
		}
	}

	static GraphQLType doBuild(IType type, GraphQLCodeRegistry.Builder registry) {
		return new GraphQLType() {
			
			GraphQLInputType _input;
			GraphQLOutputType _output;
			
			@Override
			public IType reflectedType() {
				return type;
			}
			
			@Override
			public GraphQLInputType inputType() {
				synchronized(this) {
					if(_input==null)
						_input = _buildInputType(type, registry);
					return _input;
				}
			}
			
			@Override
			public GraphQLOutputType outputType() {
				synchronized(this) {
					if(_output==null)
						_output = _buildOutputType(type, registry);
					return _output;
				}
			}

			private GraphQLOutputType _buildOutputType(IType type, GraphQLCodeRegistry.Builder registry) {
				if(type instanceof CursorType)
					return _buildOutputCursorType((CursorType)type, registry);
				else if(type instanceof CategoryType)
					return _buildOutputCategoryType((CategoryType)type, registry);
				else if(type instanceof ListType) {
					GraphQLOutputType itemType = buildOutputType(((ListType)type).getItemType(), registry);
					return GraphQLList.list(itemType);
				} else if(type instanceof IEnumeratedType)
					return _buildOutputEnumeratedType((IEnumeratedType)type, registry);
				else
					throw new UnsupportedOperationException("yet!");
			}
			
			private GraphQLOutputType _buildOutputCursorType(CursorType type, GraphQLCodeRegistry.Builder registry) {
				GraphQLOutputType itemType = buildOutputType(type.getItemType(), registry);
				return GraphQLObjectType.newObject()
						.name(type.getItemType().getTypeName() + "Cursor")
						.field(GraphQLFieldDefinition.newFieldDefinition().name("count").type(Scalars.GraphQLLong).build())
						.field(GraphQLFieldDefinition.newFieldDefinition().name("totalCount").type(Scalars.GraphQLLong).build())
						.field(GraphQLFieldDefinition.newFieldDefinition().name("items").type(GraphQLList.list(itemType)).build())
						.build();
			}

			private GraphQLOutputType _buildOutputCategoryType(CategoryType type, GraphQLCodeRegistry.Builder registry) {
				IDeclaration decl = ApplicationContext.get().getRegisteredDeclaration(IDeclaration.class, type.getTypeNameId());
				if(decl instanceof CategoryDeclaration)
					return _buildOutputCategoryType((CategoryDeclaration)decl, registry);
				else if(decl instanceof IEnumeratedDeclaration)
					return _buildOutputEnumeratedType((IEnumeratedDeclaration<?>)decl, registry);
				else
					throw new UnsupportedOperationException("Yet!");
									
			}

			private GraphQLOutputType _buildOutputCategoryType(CategoryDeclaration decl, GraphQLCodeRegistry.Builder registry) {
				var derived = ApplicationContext.get().fetchDerivedCategoryDeclarations(decl.getId());
				if(derived.isEmpty())
					return _buildOutputSimpleCategoryType(decl, registry);
				else {
					List<CategoryType> types = derived.stream()
							.map(IDeclaration::getId)
							.map(id -> new CategoryType(id))
							.collect(Collectors.toList());
					return _buildOutputUnionCategoryType(decl, types, registry);
				}
			}
			
			private GraphQLUnionType _buildOutputUnionCategoryType(CategoryDeclaration decl, List<CategoryType> derived, Builder registry) {
				var builder = GraphQLUnionType.newUnionType()
						.name(decl.getName() + "Union");
				if(!decl.isAbstract())
					builder = builder.possibleType(_buildOutputSimpleCategoryType(decl, registry));
				for(var type : derived) {
					var outputType = build(type, registry).outputType();
					if(outputType instanceof GraphQLObjectType)
						builder = builder.possibleType((GraphQLObjectType)outputType);
					else if(outputType instanceof GraphQLUnionType)
						builder = builder.possibleTypes(((GraphQLUnionType)outputType).getTypes().toArray(new GraphQLObjectType[0]));
				};
				GraphQLUnionType unionType = builder.build();
				registry.typeResolver(unionType, env -> {
					Object o = env.getObject();
					IType type = ((ConcreteInstance)o).getType();
					return env.getSchema().getObjectType(type.getTypeName());
				});
				return unionType;
			}

			private GraphQLObjectType _buildOutputSimpleCategoryType(CategoryDeclaration decl, GraphQLCodeRegistry.Builder registry) {
				return GraphQLObjectType.newObject()
						.name(type.getTypeName())
						.fields(_buildOutputFields(decl, registry))
						.build();
			}

			private GraphQLOutputType _buildOutputEnumeratedType(IEnumeratedType type, GraphQLCodeRegistry.Builder registry) {
				return _buildOutputCategoryType(new CategoryType(type.getTypeNameId()), registry);
			}
			
			private GraphQLOutputType _buildOutputEnumeratedType(IEnumeratedDeclaration<?> decl, GraphQLCodeRegistry.Builder registry) {
				return GraphQLEnumType.newEnum()
						.name(type.getTypeName())
						.values(_buildEnumValues(decl))
						.build();
			}

			private List<GraphQLEnumValueDefinition> _buildEnumValues(IEnumeratedDeclaration<?> decl) {
				return decl.getSymbolsList().stream()
						.map(this::_buildEnumValue)
						.collect(Collectors.toList());
			}
			
			private GraphQLEnumValueDefinition _buildEnumValue(Symbol symbol) {
				return GraphQLEnumValueDefinition.newEnumValueDefinition()
						.name(symbol.getName())
						.value(symbol)
						.build();
			
			}

			private List<GraphQLFieldDefinition> _buildOutputFields(CategoryDeclaration decl, GraphQLCodeRegistry.Builder registry) {
				return decl.getAllAttributes(ApplicationContext.get()).stream()
						.map(attr->_buildOutputField(decl, attr, registry))
						.filter(Objects::nonNull)
						.collect(Collectors.toList());
			}

			private GraphQLFieldDefinition _buildOutputField(CategoryDeclaration category, Identifier attr, GraphQLCodeRegistry.Builder registry) {
				registry.dataFetcherIfAbsent(FieldCoordinates.coordinates(category.getName(), attr.toString()), new GraphQLAttributeFetcher(attr));
				AttributeDeclaration decl = ApplicationContext.get().getRegisteredDeclaration(AttributeDeclaration.class, attr);
				return GraphQLFieldDefinition.newFieldDefinition()
						.name(attr.toString())
						.type(buildOutputType(decl.getType(), registry))
						.build();
			}

			private GraphQLInputType _buildInputType(IType type, GraphQLCodeRegistry.Builder registry) {
				if(!(type instanceof CategoryType))
					throw new IllegalStateException("Should never get there!");
				else {
					CategoryDeclaration decl = ApplicationContext.get().getRegisteredDeclaration(CategoryDeclaration.class, type.getTypeNameId());
					return GraphQLInputObjectType.newInputObject()
						.name(type.getTypeName() + "Input")
						.fields(_buildInputFields(decl, registry))
						.build();
				}
			}

			private List<GraphQLInputObjectField> _buildInputFields(CategoryDeclaration decl, GraphQLCodeRegistry.Builder registry) {
				return decl.getAllAttributes(ApplicationContext.get()).stream()
						.map(a->_buildInputField(a, registry))
						.filter(Objects::nonNull)
						.collect(Collectors.toList());
			}

			private GraphQLInputObjectField _buildInputField(Identifier attr, GraphQLCodeRegistry.Builder registry) {
				AttributeDeclaration decl = ApplicationContext.get().getRegisteredDeclaration(AttributeDeclaration.class, attr);
				return GraphQLInputObjectField.newInputObjectField()
						.name(attr.toString())
						.type(buildInputType(decl.getType(), registry))
						.build();
			}

		};
	}


}
