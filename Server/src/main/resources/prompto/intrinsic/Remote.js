function readJSONValue(value) {
	if(value==null)
		return null;
	else if(Array.isArray(value)) {
		var items = value.map(readJSONValue);
		return new List(false, items);
	} else if(typeof(value)==typeof({})) {
		switch(value.type) {
			case "Uuid":
				return UUID.fromString(value.value);
			case "Date":
				return LocalDate.parse(value.value);
			case "Time":
				return LocalTime.parse(value.value);
			case "DateTime":
				return DateTime.parse(value.value);
			default:
				return readJSONInstance(value);
			}	
	} else
		return value;
}

function readJSONInstance(value) {
	var fct = eval(value.type);
	if(typeof(fct)!=='function')
		throw new Error("Unsupported: " + value.type);
	var stored = recordToStored(value);
	return new fct(null, stored, false);
}

function getTypeName(value) {
	var name = typeof(value);
	if(name==="object" && value.__proto__)
		name = value.__proto__.constructor.name;
	return name;
}

function writeJSONValue(value) {
	if(value==null)
		return value;
	else {
		var typeName = getTypeName(value);
		switch(typeName) {
		case "UUID":
			return { type: "Uuid", value: value.hex };
		case "LocalDate":
			return { type: "Date", value: value.toString() };
		case "LocalTime":
			return { type: "Time", value: value.toString() };
		case "DateTime":
			return { type: "DateTime", value: value.toString() };
		case "List":
			return value.map(writeJSONValue);
		default:	
			if(value instanceof $Root) {
				var dbId = value.storable.getOrCreateDbId();
				return { type: "%dbRef%", value: writeJSONValue(dbId) };
			} else
				return value;
		}
	}
	
}
