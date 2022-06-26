package prompto.graphql;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import graphql.Scalars;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoPeriod;
import prompto.intrinsic.PromptoTime;
import prompto.type.BinaryType;
import prompto.type.BlobType;
import prompto.type.BooleanType;
import prompto.type.DateTimeType;
import prompto.type.DateType;
import prompto.type.DecimalType;
import prompto.type.IType;
import prompto.type.ImageType;
import prompto.type.IntegerType;
import prompto.type.PeriodType;
import prompto.type.TextType;
import prompto.type.TimeType;
import prompto.type.UuidType;
import prompto.value.BinaryValue;
import prompto.value.DateTimeValue;
import prompto.value.DateValue;
import prompto.value.PeriodValue;
import prompto.value.TimeValue;
import prompto.value.UuidValue;

public abstract class GraphQLScalars {
	
	static final Map<IType, GraphQLScalarType> ALL = buildAll();

	static Map<IType, GraphQLScalarType> buildAll() {
		
		return Arrays.asList(
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(BooleanType.instance(), Scalars.GraphQLBoolean),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(IntegerType.instance(), Scalars.GraphQLLong),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(DecimalType.instance(), Scalars.GraphQLFloat),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(TextType.instance(), Scalars.GraphQLString),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(DateType.instance(), dateScalar()),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(TimeType.instance(), timeScalar()),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(DateTimeType.instance(), dateTimeScalar()),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(PeriodType.instance(), periodScalar()),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(BlobType.instance(), binaryScalar(BlobType.instance())),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(ImageType.instance(), binaryScalar(ImageType.instance())),
				new AbstractMap.SimpleEntry<IType, GraphQLScalarType>(UuidType.instance(), uuidScalar()))
		.stream()
				.collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
	}
	
	private static GraphQLScalarType binaryScalar(BinaryType type) {
		return GraphQLScalarType.newScalar()
				.name(type.getTypeName())
				.coercing(new Coercing<BinaryValue, String>() {

					@Override
					public String serialize(Object value) throws CoercingSerializeException {
						if(value instanceof BinaryValue)
							return ((BinaryValue)value).getSourceUrl();
						else
							return null;
					}

					@Override
					public BinaryValue parseValue(Object input) throws CoercingParseValueException {
						return null; // unsupported
					}

					@Override
					public BinaryValue parseLiteral(Object input) throws CoercingParseLiteralException {
						return null; // unsupported
					}
				}).build();
	}
	
	
	private static GraphQLScalarType uuidScalar() {
		return GraphQLScalarType.newScalar()
				.name(UuidType.instance().getTypeName())
				.coercing(new Coercing<UuidValue, String>() {

					@Override
					public String serialize(Object value) throws CoercingSerializeException {
						if(value instanceof UuidValue)
							return value.toString();
						else
							return null;
					}

					@Override
					public UuidValue parseValue(Object input) throws CoercingParseValueException {
						return new UuidValue(UUID.fromString(String.valueOf(input)));
					}

					@Override
					public UuidValue parseLiteral(Object input) throws CoercingParseLiteralException {
						return new UuidValue(UUID.fromString(String.valueOf(input)));
					}
				}).build();
	}

	private static GraphQLScalarType periodScalar() {
		return GraphQLScalarType.newScalar()
				.name(PeriodType.instance().getTypeName())
				.coercing(new Coercing<PeriodValue, String>() {

					@Override
					public String serialize(Object value) throws CoercingSerializeException {
						if(value instanceof PeriodValue)
							return value.toString();
						else
							return null;	}

					@Override
					public PeriodValue parseValue(Object input) throws CoercingParseValueException {
						return new PeriodValue(PromptoPeriod.parse(String.valueOf(input)));
					}

					@Override
					public PeriodValue parseLiteral(Object input) throws CoercingParseLiteralException {
						return new PeriodValue(PromptoPeriod.parse(String.valueOf(input)));
					}
				}).build();
	}

	private static GraphQLScalarType dateTimeScalar() {
		return GraphQLScalarType.newScalar()
				.name(DateTimeType.instance().getTypeName())
				.coercing(new Coercing<DateTimeValue, String>() {

					@Override
					public String serialize(Object value) throws CoercingSerializeException {
						if(value instanceof DateTimeValue)
							return value.toString();
						else
							return null;	}

					@Override
					public DateTimeValue parseValue(Object input) throws CoercingParseValueException {
						return new DateTimeValue(PromptoDateTime.parse(String.valueOf(input)));
					}

					@Override
					public DateTimeValue parseLiteral(Object input) throws CoercingParseLiteralException {
						return new DateTimeValue(PromptoDateTime.parse(String.valueOf(input)));
					}
				}).build();
	}

	private static GraphQLScalarType timeScalar() {
		return GraphQLScalarType.newScalar()
				.name(TimeType.instance().getTypeName())
				.coercing(new Coercing<TimeValue, String>() {

					@Override
					public String serialize(Object value) throws CoercingSerializeException {
						if(value instanceof TimeValue)
							return value.toString();
						else
							return null;		}

					@Override
					public TimeValue parseValue(Object input) throws CoercingParseValueException {
						return new TimeValue(PromptoTime.parse(String.valueOf(input)));
					}

					@Override
					public TimeValue parseLiteral(Object input) throws CoercingParseLiteralException {
						return new TimeValue(PromptoTime.parse(String.valueOf(input)));
					}
				}).build();
	}

	private static GraphQLScalarType dateScalar() {
		return GraphQLScalarType.newScalar()
				.name(DateType.instance().getTypeName())
				.coercing(new Coercing<DateValue, String>() {

					@Override
					public String serialize(Object value) throws CoercingSerializeException {
						if(value instanceof DateValue)
							return value.toString();
						else
							return null;	}

					@Override
					public DateValue parseValue(Object input) throws CoercingParseValueException {
						PromptoDate value = readDateInput(input);
						return new DateValue(value);
					}

				@Override
					public DateValue parseLiteral(Object input) throws CoercingParseLiteralException {
						PromptoDate value = readDateInput(input);
						return new DateValue(value);
					}

					private PromptoDate readDateInput(Object input) {
						String value = input instanceof StringValue ? ((StringValue)input).getValue() : String.valueOf(input); 
						return PromptoDate.parse(value);
					}

}).build();
	}

	public static boolean isScalar(IType type) {
		return ALL.containsKey(type);
	}
	
	public static GraphQLScalarType get(IType type) {
		return ALL.get(type);
	}
	

}
