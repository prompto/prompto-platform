package prompto.value;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;

import prompto.argument.IArgument;
import prompto.declaration.AttributeDeclaration;
import prompto.declaration.ConcreteCategoryDeclaration;
import prompto.declaration.GetterMethodDeclaration;
import prompto.declaration.IMethodDeclaration;
import prompto.declaration.SetterMethodDeclaration;
import prompto.error.NotMutableError;
import prompto.error.NotStorableError;
import prompto.error.PromptoError;
import prompto.error.ReadWriteError;
import prompto.error.SyntaxError;
import prompto.grammar.Identifier;
import prompto.grammar.Operator;
import prompto.runtime.Context;
import prompto.runtime.Variable;
import prompto.store.IDataStore;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.type.CategoryType;
import prompto.type.DecimalType;

public class ConcreteInstance extends BaseValue implements IInstance, IMultiplyable {

	ConcreteCategoryDeclaration declaration;
	Map<Identifier,IValue> values = new HashMap<Identifier,IValue>();
	IStorable storable = null;
	boolean mutable = false;
	
	public ConcreteInstance(Context context, ConcreteCategoryDeclaration declaration) {
		super(new CategoryType(declaration.getId()));
		this.declaration = declaration;
		if(declaration.isStorable()) {
			List<String> categories = declaration.collectCategories(context);
			storable = IDataStore.getInstance().newStorable(categories);
		}
	}
	
	@Override
	public Object getStorableData() throws NotStorableError {
		// this is called when storing the instance as a field value, so we just store the dbId
		// the instance data itself will be collected as part of collectStorables
		if(this.storable==null)
			throw new NotStorableError();
		else
			return this.getOrCreateDbId();
	}


	@Override
	public boolean setMutable(boolean mutable) {
		boolean result = this.mutable;
		this.mutable = mutable;
		return result;
	}
	
	public boolean isMutable() {
		return mutable;
	}
	
	@Override
	public IStorable getStorable() {
		return storable;
	}

	@Override
	public void collectStorables(List<IStorable> list) throws PromptoError {
		if(storable==null)
			throw new NotStorableError();
		if(storable.isDirty())
			list.add(storable);
		for(IValue value : values.values()) {
			if(value instanceof IInstance)
				((IInstance)value).collectStorables(list);
		}
	}
	
	public ConcreteCategoryDeclaration getDeclaration() {
		return declaration;
	}

	@Override
	public CategoryType getType() {
		return (CategoryType)this.type;
	}
	
	@Override
	public Set<Identifier> getMemberNames() {
		return values.keySet();
	}

	// don't call getters from getters, so register them
	ThreadLocal<Map<Identifier,Context>> activeGetters = new ThreadLocal<Map<Identifier,Context>>() {

		@Override
		protected Map<Identifier,Context> initialValue() {
			return new HashMap<Identifier,Context>();
		}
	};
	
	@Override
	public IValue getMember(Context context, Identifier attrName, boolean autoCreate) throws PromptoError {
		Map<Identifier,Context> activeGetters = this.activeGetters.get();
		Context stacked = activeGetters.get(attrName);
		boolean first = stacked==null;
		if(first)
			activeGetters.put(attrName, context);
		try {
			return getMemberAllowGetter(context, attrName, first);
		} finally {
			if(first)
				activeGetters.remove(attrName);
		}
	}
	
	protected IValue getMemberAllowGetter(Context context, Identifier attrName, boolean allowGetter) throws PromptoError {
		GetterMethodDeclaration getter = allowGetter ? declaration.findGetter(context, attrName) : null;
		if(getter!=null) {
			context = context.newInstanceContext(this).newChildContext(); // mimic method call
			return getter.interpret(context);
		} else
			return values.get(attrName);
	}
	
	// don't call setters from setters, so register them
	ThreadLocal<Map<Identifier,Context>> activeSetters = new ThreadLocal<Map<Identifier,Context>>() {

		@Override
		protected Map<Identifier,Context> initialValue() {
			return new HashMap<Identifier,Context>();
		}
	};
	
	@Override
	public void setMember(Context context, Identifier attrName, IValue value) throws PromptoError {
		if(!mutable)
			throw new NotMutableError();
		Map<Identifier,Context> activeSetters = this.activeSetters.get();
		Context stacked = activeSetters.get(attrName);
		boolean first = stacked==null;
		try {
			if(first)
				activeSetters.put(attrName, context);
			setMember(context, attrName, value, first);
		} finally {
			if(first)
				activeSetters.remove(attrName);
		}
	}
	
	public void setMember(Context context, Identifier attrName, IValue value, boolean allowSetter) throws PromptoError {
		AttributeDeclaration decl = context.getRegisteredDeclaration(AttributeDeclaration.class, attrName);
		SetterMethodDeclaration setter = allowSetter ? declaration.findSetter(context,attrName) : null;
		if(setter!=null) {
			// use attribute name as parameter name for incoming value
			context = context.newInstanceContext(this).newChildContext(); // mimic method call
			context.registerValue(new Variable(attrName, decl.getType())); 
			context.setValue(attrName, value);
			value = setter.interpret(context);
		}
		value = autocast(decl, value);
		values.put(attrName, value);
		if(storable!=null && decl.isStorable()) {
			storable.setValue(attrName, value, this::getDbId);
		}
	}
	
	private IValue getDbId() {
		return values.get(IStore.dbIdIdentifier);
	}
	
	private IValue getOrCreateDbId() {
		IValue dbId = values.get(IStore.dbIdIdentifier);
		return dbId!=null ? dbId : this.storable.getOrCreateDbId();
	}

	private IValue autocast(AttributeDeclaration decl, IValue value) {
		if(value!=null && value instanceof prompto.value.Integer && decl.getType()==DecimalType.instance())
			value = new Decimal(((prompto.value.Integer)value).doubleValue());
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ConcreteInstance))
			return false;
		return this.values.equals(((ConcreteInstance)obj).values);
	}
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry<Identifier, IValue> kvp : this.values.entrySet())
        {
            sb.append(kvp.getKey().toString());
            sb.append(":");
            sb.append(kvp.getValue().toString());
            sb.append(", ");
        }
        if(sb.length()>2)
            sb.setLength( sb.length() - 2);
        sb.append("}");
        return sb.toString();
	}
	
	@Override
	public IValue multiply(Context context, IValue value) throws PromptoError {
		try {
			return interpretOperator(context, value, Operator.MULTIPLY);
		} catch(SyntaxError e) {
			return super.multiply(context, value);
		}
	}
	
	@Override
	public IValue divide(Context context, IValue value) throws PromptoError {
		try {
			return interpretOperator(context, value, Operator.DIVIDE);
		} catch(SyntaxError e) {
			return super.divide(context, value);
		}
	}
	
	@Override
	public IValue intDivide(Context context, IValue value) throws PromptoError {
		try {
			return interpretOperator(context, value, Operator.IDIVIDE);
		} catch(SyntaxError e) {
			return super.intDivide(context, value);
		}
	}
	
	@Override
	public IValue modulo(Context context, IValue value) throws PromptoError {
		try {
			return interpretOperator(context, value, Operator.MODULO);
		} catch(SyntaxError e) {
			return super.modulo(context, value);
		}
	}
	
	@Override
	public IValue plus(Context context, IValue value) throws PromptoError {
		try {
			return interpretOperator(context, value, Operator.PLUS);
		} catch(SyntaxError e) {
			return super.plus(context, value);
		}
	}
	
	@Override
	public IValue minus(Context context, IValue value) throws PromptoError {
		try {
			return interpretOperator(context, value, Operator.MINUS);
		} catch(SyntaxError e) {
			return super.minus(context, value);
		}
	}
	
	private IValue interpretOperator(Context context, IValue value, Operator operator) throws PromptoError {
		IMethodDeclaration decl = declaration.findOperator(context, operator, value.getType());
		context = context.newInstanceContext(this);
		Context local = context.newChildContext();
		decl.registerArguments(local);
		IArgument arg = decl.getArguments().getFirst();
		local.setValue(arg.getId(), value);
		return decl.interpret(local);
	}

	@Override
	public void toJson(Context context, JsonGenerator generator, IInstance instance, Identifier name) throws PromptoError {
		try {
			generator.writeStartObject();
			generator.writeFieldName("type");
			generator.writeString(this.getType().getName());
			generator.writeFieldName("value");
			generator.writeStartObject();
			for(Entry<Identifier, IValue> entry : values.entrySet()) {
				generator.writeFieldName(entry.getKey().getName());
				IValue value = entry.getValue();
				if(value==null)
					generator.writeNull();
				else
					value.toJson(context, generator, this, entry.getKey());
			}
			generator.writeEndObject();
			generator.writeEndObject();
		} catch(IOException e) {
			throw new ReadWriteError(e.getMessage());
		}
	}
}

