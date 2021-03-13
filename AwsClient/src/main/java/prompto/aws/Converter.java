package prompto.aws;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDict;
import prompto.intrinsic.PromptoDocument;
import prompto.intrinsic.PromptoList;
import prompto.utils.Logger;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.SdkField;
import software.amazon.awssdk.core.SdkPojo;
import software.amazon.awssdk.core.protocol.MarshallingType;

public abstract class Converter {

	static final Logger logger = new Logger();

	public static PromptoDocument<String, Object> convertPojo(SdkPojo pojo) {
		PromptoDocument<String, Object> doc = new PromptoDocument<>();
		pojo.sdkFields().forEach(field -> {
			Object value = convertSdkField(pojo, field);
			doc.put(field.unmarshallLocationName(), value);
		});
		return doc;
	}
	
	static final Set<MarshallingType<?>> ATOMIC_TYPES = new HashSet<>(Arrays.asList(
			MarshallingType.NULL,
			MarshallingType.BOOLEAN,
					MarshallingType.LONG,
			MarshallingType.DOUBLE,
			MarshallingType.STRING));
							
			

	private static Object convertSdkField(SdkPojo pojo, SdkField<?> field) {
		Object value = field.getValueOrDefault(pojo);
		MarshallingType<?> type = field.marshallingType();
		if(ATOMIC_TYPES.contains(type))
			return value;
		else if(type == MarshallingType.INTEGER) {
			return value instanceof Integer ? ((Integer)value).longValue(): null;
		} else if(type == MarshallingType.FLOAT) {
			return value instanceof Float ? ((Float)value).doubleValue(): null;
		} else if(type == MarshallingType.BIG_DECIMAL) {
			return value instanceof BigDecimal ? ((BigDecimal)value).doubleValue(): null;
		} else if(type == MarshallingType.INSTANT) {
			return value instanceof Instant ? LocalDateTime.ofInstant((Instant)value, ZoneId.of("UTC")): null;
		} else if(type == MarshallingType.SDK_POJO) {
			return value instanceof SdkPojo ? convertPojo((SdkPojo)value): null;
		} else if(type == MarshallingType.LIST) {
			return value instanceof List ? convertList((List<?>)value): null;
		} else if(type == MarshallingType.MAP) {
			return value instanceof Map ? convertMap((Map<?,?>)value): null;
		} else if(type == MarshallingType.SDK_BYTES) {
			return value instanceof SdkBytes ? new PromptoBinary("application/binary", ((SdkBytes)value).asByteArray()) : null;
		} else {
			logger.error(() -> "Unsupported marshalling type: " + type.toString());
			return value;
		}
	}


	private static Object convertValue(Object value) {
		if(value instanceof Integer)
			return ((Integer)value).longValue();
		else if(value instanceof Float)
			return ((Float)value).doubleValue();
		else if(value instanceof BigDecimal)
			return ((BigDecimal)value).doubleValue();
		else if(value instanceof Instant)
			return LocalDateTime.ofInstant((Instant)value, ZoneId.of("UTC"));
		else if(value instanceof SdkPojo)
			return convertPojo((SdkPojo)value);
		else if(value instanceof List)
			return convertList((List<?>)value);
		else if(value instanceof Map)
			return convertMap((Map<?,?>)value);
		else if(value instanceof SdkBytes)
			return new PromptoBinary("application/binary", ((SdkBytes)value).asByteArray());
		else
			return value;
				
	}
	
	
	private static Object convertList(List<?> value) {
		List<?> converted = value.stream()
				.map(Converter::convertValue)
				.collect(Collectors.toList());
		return new PromptoList<Object>(converted, false);
	}
	
	private static Object convertMap(Map<?,?> value) {
		PromptoDict<String, Object> dict = new PromptoDict<>(true);
		value.entrySet().forEach(entry -> {
			Object converted = convertValue(entry.getValue());
			dict.put(String.valueOf(entry.getKey()), converted);
		});
		dict.setMutable(false);
		return dict;
	}


}
