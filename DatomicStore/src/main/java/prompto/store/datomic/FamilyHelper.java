package prompto.store.datomic;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import datomic.Entity;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.datomic.Constants.Db;
import prompto.store.datomic.Constants.DbCardinality;

public interface FamilyHelper {

	static Map<Family, FamilyHelper> HELPERS = getHelpers();
	
	static Map<Family, FamilyHelper> getHelpers() {
		Map<Family, FamilyHelper> helpers = new HashMap<>();
		helpers.put(Family.TEXT, new TextHelper());
		helpers.put(Family.BOOLEAN, new BooleanHelper());
		helpers.put(Family.INTEGER, new IntegerHelper());
		helpers.put(Family.DECIMAL, new DecimalHelper());
		helpers.put(Family.UUID, new UUIDHelper());
		helpers.put(Family.DATE, new DateHelper());
		helpers.put(Family.TIME, new TimeHelper());
		helpers.put(Family.DATETIME, new DateTimeHelper());
		return helpers;
	}

	List<Object> collectAttributeFacts(AttributeInfo attribute);
	Object nativeToPrompto(Entity entity, String fieldName);
	
	static abstract class SimpleHelper implements FamilyHelper {

		@Override
		public List<Object> collectAttributeFacts(AttributeInfo attribute) {
			Map<String, Object> valueFacts = new HashMap<>();
			valueFacts.put(Db.IDENT.dbName(), ":" + attribute.getName());
			valueFacts.put(Db.VALUETYPE.dbName(), getDatomicType());
			// facts.put(Db.COMPONENT.dbName(), familyComponents.getOrDefault(attribute.getFamily(), false));
			valueFacts.put(Db.CARDINALITY.dbName(), attribute.isCollection() ? DbCardinality.MANY.dbName() : DbCardinality.ONE.dbName());
			return Collections.singletonList(valueFacts);	
		}

		protected abstract String getDatomicType();
		
		@Override
		public Object nativeToPrompto(Entity entity, String fieldName) {
			return entity.get(":" + fieldName);
		}
		
	}
	
	static class TextHelper extends SimpleHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/string";
		}
				
	}
	
	static class IntegerHelper extends SimpleHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/long";
		}
				
	}

	static class DecimalHelper extends SimpleHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/double";
		}
				
	}
	
	static class BooleanHelper extends SimpleHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/boolean";
		}
				
	}

	static class UUIDHelper extends SimpleHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/uuid";
		}
				
	}

	static abstract class TypedHelper extends SimpleHelper {
		
		@Override
		public List<Object> collectAttributeFacts(AttributeInfo attribute) {
			Map<String, Object> valueFacts = new HashMap<>();
			valueFacts.put(Db.IDENT.dbName(), ":" + attribute.getName());
			valueFacts.put(Db.VALUETYPE.dbName(), getDatomicType());
			valueFacts.put(Db.CARDINALITY.dbName(), attribute.isCollection() ? DbCardinality.MANY.dbName() : DbCardinality.ONE.dbName());
			Map<String, Object> familyFacts = new HashMap<>();
			familyFacts.put(Db.IDENT.dbName(), ":" + attribute.getName() + "/family");
			familyFacts.put(Db.VALUETYPE.dbName(), ":db.type/string");
			familyFacts.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
			return Arrays.asList(valueFacts, familyFacts);	
		}
		
	}
	
	static class DateHelper extends TypedHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/long";
		}
		
		@Override
		public Object nativeToPrompto(Entity entity, String fieldName) {
			Object value = entity.get(":" + fieldName);
			if(value instanceof Long)
				return PromptoDate.fromJavaTime((Long)value);
			else
				return null;
		}
				
	}
	
	static class TimeHelper extends TypedHelper {

		@Override
		protected String getDatomicType() {
			return ":db.type/long";
		}
		
		@Override
		public Object nativeToPrompto(Entity entity, String fieldName) {
			Object value = entity.get(":" + fieldName);
			if(value instanceof Long)
				return PromptoTime.fromMillisOfDay((Long)value);
			else
				return null;
		}
				
	}
	
	
	static class DateTimeHelper implements FamilyHelper {
		
		@Override
		public List<Object> collectAttributeFacts(AttributeInfo attribute) {
			if(attribute.isCollection()) 
				return collectCollectionAttributeFacts(attribute);
			else
				return collectSingleAttributeFacts(attribute);
		}
		
		List<Object> collectCollectionAttributeFacts(AttributeInfo attribute) {
				throw new UnsupportedOperationException();
		}
		
		List<Object> collectSingleAttributeFacts(AttributeInfo attribute) {
			Map<String, Object> valueFacts = new HashMap<>();
			valueFacts.put(Db.IDENT.dbName(), ":" + attribute.getName());
			valueFacts.put(Db.VALUETYPE.dbName(), ":db.type/instant");
			valueFacts.put(Db.CARDINALITY.dbName(), attribute.isCollection() ? DbCardinality.MANY.dbName() : DbCardinality.ONE.dbName());
			Map<String, Object> zoneFacts = new HashMap<>();
			zoneFacts.put(Db.IDENT.dbName(), ":" + attribute.getName() + "/zone");
			zoneFacts.put(Db.VALUETYPE.dbName(), ":db.type/string");
			zoneFacts.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
			Map<String, Object> offsetFacts = new HashMap<>();
			offsetFacts.put(Db.IDENT.dbName(), ":" + attribute.getName() + "/offset");
			offsetFacts.put(Db.VALUETYPE.dbName(), ":db.type/long");
			offsetFacts.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
			Map<String, Object> familyFacts = new HashMap<>();
			familyFacts.put(Db.IDENT.dbName(), ":" + attribute.getName() + "/family");
			familyFacts.put(Db.VALUETYPE.dbName(), ":db.type/string");
			familyFacts.put(Db.CARDINALITY.dbName(), DbCardinality.ONE.dbName());
			return Arrays.asList(valueFacts, zoneFacts, familyFacts);	
		}

		@Override
		public Object nativeToPrompto(Entity entity, String fieldName) {
			Object value = entity.get(":" + fieldName);
			if(value instanceof Date) {
				Object info = entity.get(":" + fieldName + "/zone");
				if(info instanceof String) {
					DateTimeZone zone = DateTimeZone.forTimeZone(TimeZone.getTimeZone(ZoneId.of((String)info)));
					DateTime dt = new DateTime(((Date)value).getTime(), zone);
					return new PromptoDateTime(dt);
				} else {
					info = entity.get(":" + fieldName + "/offset");
					if(info instanceof Long) {
						DateTimeZone zone = DateTimeZone.forOffsetMillis((int)((Long)info*1000));
						DateTime dt = new DateTime(((Date)value).getTime(), zone);
						return new PromptoDateTime(dt);
					} else {
						return new PromptoDateTime(new DateTime(((Date)value).getTime(), DateTimeZone.UTC));
					}
				}
					
			} else
				return null;
		}

			
		
	}


}
