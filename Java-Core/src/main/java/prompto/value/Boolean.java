package prompto.value;

import prompto.error.SyntaxError;
import prompto.runtime.Context;
import prompto.type.BooleanType;

public class Boolean extends BaseValue implements Comparable<Boolean> {
	
	public static Boolean TRUE = new Boolean(true);
	public static Boolean FALSE = new Boolean(false);

	static {
		TRUE.not = FALSE;
		FALSE.not = TRUE;
	}

	public static Boolean Parse(String text) {
		return ValueOf(java.lang.Boolean.parseBoolean(text));
	}

	public static Boolean ValueOf(boolean value) {
		return value ? TRUE : FALSE;
	}

	boolean value;
	Boolean not;

	private Boolean(boolean value) {
		super(BooleanType.instance());
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public Boolean getNot() {
		return not;
	}

	@Override
	public int CompareTo(Context context, IValue value) throws SyntaxError {
		if (value instanceof Boolean)
			return compareTo((Boolean) value);
		else
			throw new SyntaxError("Illegal comparison: Boolean + " + value.getClass().getSimpleName());
	}

	@Override
	public int compareTo(Boolean other) {
		return java.lang.Boolean.compare(this.value, other.value);
	}

	@Override
	public Object ConvertTo(Class<?> type) {
		return value;
	}

	@Override
	public String toString() {
		return java.lang.Boolean.toString(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Boolean)
			return value == ((Boolean) obj).value;
		else
			return false;
	}

}