package prompto.store.datomic;

// map Datomic names to Enums to avoid strings all over the place
public class Constants {

	public static enum  Db {
		ID,
		IDENT,
		VALUETYPE("valueType"),
		CARDINALITY,
		INDEX,
		FULLTEXT,
		UNIQUE;
		
		String name;
		
		Db() {
			this.name = ":db/" + name().toLowerCase();
		}
		
		Db(String name) {
			this.name = ":db/" + name;
		}
		
		public String dbName() {
			return name;
		}
		
	}
	
	public static enum  DbInstall {
		ATTRIBUTE;
		
		public String dbName() {
			return ":db.install/_" + name().toLowerCase();
		}
		
	}
	
	public static enum  DbPart {
		USER,
		DB,
		TX;
		
		public String dbName() {
			return ":db.part/" + name().toLowerCase();
		}
	}
	
	/*
	public static enum  DbType implements DbValue<String> {
		BOOLEAN(new Helper<Boolean>(Boolean.class)),
		LONG(new Helper<Long>(Long.class)),
		BIGINT(null),
		FLOAT(new Helper<Float>(Float.class)),
		DOUBLE(new Helper<Double>(Double.class)),
		BIGDEC(null),
		INSTANT(new Helper<DateTime>(DateTime.class)),
		STRING(new Helper<String>(String.class)),
		BYTES(null),
		REF(null),
		URI(new Helper<java.net.URI>(java.net.URI.class)),
		UUID(new Helper<java.util.UUID>(java.util.UUID.class));
	
		Helper<?> helper;
		
		DbType(Helper<?> helper) {
			this.helper = helper;
		}
		
		public String dbValue() {
			return ":db.type/" + name().toLowerCase();
		}
		
		public static DbType dbValueOf(String dbValue) {
			String name = dbValue.split("/")[1];
			return DbType.valueOf(name.toUpperCase());
		}
		
		@SuppressWarnings("unchecked")
		public <T> Value<T> newInstance() {
			return (Value<T>)helper.newInstance();
		}
		
		static class Helper<T> {
			
			Class<T> klass;
			
			Helper(Class<T> klass) {
				this.klass = klass;
			}
			
			public Value<T> newInstance() {
				try {
					return new Value<T>(klass.newInstance());
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}
			
		}
		

	}
	*/
	
	public static enum DbUnique {
		VALUE;
		
		public String dbName() {
			return ":db.unique/" + name().toLowerCase();
		}
		
	}

	public static enum DbCardinality {
		ONE,
		MANY;
		
		public String dbName() {
			return ":db.cardinality/" + name().toLowerCase();
		}
		
		public static DbCardinality dbValueOf(String dbValue) {
			String name = dbValue.split("/")[1];
			return DbCardinality.valueOf(name.toUpperCase());
		}
	
	}

	public static enum Indexing {
		NONE,
		INDEX,
		FULLTEXT
	}

}
