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

function StoredDocument(categories) {
	// use reserved keyword explicitly
	this.category = categories;
    return this;
}


StoredDocument.prototype.getData = function(name) {
    return this[name] || null;
};

StoredDocument.prototype.matches = function(predicate) {
    if(predicate==null)
        return true;
    else
        return predicate.matches(this);
};

function StorableDocument(categories, dbIdListener) {
    if(!categories)
        throw new Error("!!!");
    // use 'category' reserved keyword voluntarily
    this.category = categories;
    this.dbIdListener = dbIdListener;
    this.document = null;
    return this;
}

Object.defineProperty(StorableDocument.prototype, "dirty", {
    get : function() {
        return this.document != null;
    },
    set : function(value) {
        if (value) {
            if(!this.document) {
                this.document = new StoredDocument(this.category);
                this.document.dbId = this.getOrCreateDbId();
            }
        } else
            this.document = null;
    }
});

StorableDocument.prototype.getDbId = function() {
	return this.document ? (this.document.dbId || null) : null;
};


StorableDocument.prototype.getOrCreateDbId = function() {
	var dbId = this.getDbId();
	if(dbId==null) {
       	dbId = DataStore.instance.nextDbId();
        this.dbIdListener(dbId);
        this.setData("dbId", dbId);
    }
    return dbId;
};


StorableDocument.prototype.setData = function(name, value) {
    this.dirty = true;
    this.document[name] = writeJSONValue(value);
};

StorableDocument.prototype.updateDbIds = function(dbIds) {
	Object.getOwnPropertyNames(this.document).forEach(function(name) {
		var value = this.document[name];
		if(value && value.tempDbId && dbIds[value.tempDbId]) {
			var dbId = dbIds[value.tempDbId];
			this.document[name] = dbId;
			if(name==="dbId")
				this.dbIdListener(dbId);
		}
	}, this);
};


function recordToStored(record) {
	var stored = new StoredDocument([record.type]);
	Object.getOwnPropertyNames(record.value)
		.forEach(function(name) {
			var value = record.value[name];
			stored[name] = readJSONValue(value);
		});
	return stored;
};

function StoredIterable(records) {
	this.index = 0;
	this.count = function() { return records.count; };
	this.totalCount = function() { return records.totalCount; };
	this.hasNext = function() { return this.index < records.count; };
	this.next = function() { 
		var record = records.value[this.index++];
		return recordToStored(record);
	};
	return this;
}

function RemoteStore() {
	this.lastDbId = 0;
	this.nextDbId = function() {
		return { tempDbId: --this.lastDbId };
	};
	this.newStorableDocument = function(categories, dbIdListener) {
		return new StorableDocument(categories, dbIdListener);
	};
	this.newQueryBuilder = function() {
		return new RemoteQueryBuilder();
	};
	this.fetchSync = function(url, body) {
		var response = null;
		var request  = new XMLHttpRequest();
		request.open("POST", url, false); // must be synchronous
		request.setRequestHeader("Content-type", "application/json; charset=utf-8");
		request.onload = function() { 
			if (this.status == 200)
				response = JSON.parse(this.responseText); 
			else
				throw new Error(this.statusText);
		};
		request.onerror = function() {
			throw this.error;
		};
		request.send(body);
		return response;
	};
	this.fetchAsync = function(url, body, onLoad) {
		var request  = new XMLHttpRequest();
		request.open("POST", url, true);
		request.setRequestHeader("Content-type", "application/json; charset=utf-8");
		request.onload = function() { 
			if (this.status == 200) {
				response = JSON.parse(this.responseText); 
				onLoad(response);
			}
			else
				throw new Error(this.statusText);
		};
		request.onerror = function() {
			throw this.error;
		};
		request.send(body);
	};
	this.store = function(toDel, toStore) {
		var data = {};
		if(toDel)
			data.toDelete = toDel;
		if(toStore)
			data.toStore = Array.from(toStore).map(function(thing) { return thing.document; });
		var response = this.fetchSync("/ws/store/deleteAndStore", JSON.stringify(data));
		toStore.forEach(function(storable) { storable.updateDbIds(response.data); });
	};
	this.storeAsync = function(toDel, toStore, andThen) {
		var data = {};
		if(toDel)
			data.toDelete = toDel;
		if(toStore)
			data.toStore = Array.from(toStore).map(function(thing) { return thing.document; });
		this.fetchAsync("/ws/store/deleteAndStore", JSON.stringify(data), function(response) {
			toStore.forEach(function(storable) { storable.updateDbIds(response.data); });
			andThen();
		});
	};
	this.fetchOne = function(query) {
		var response = this.fetchSync("/ws/store/fetchOne", JSON.stringify(query));
		return recordToStored(response.data);
	};
	this.fetchMany = function(query) {
		var response = this.fetchSync("/ws/store/fetchMany", JSON.stringify(query));
		return new StoredIterable(response.data);
	};
	return this; 
}

function RemoteQueryBuilder() {
    this.orderBys = null;
    this.predicates = null;
    this.first = null;
    this.last = null;
	return this;
}

RemoteQueryBuilder.prototype.verify = function(fieldInfo, matchOp, value) {
    if(this.predicates==null)
        this.predicates = [];
    this.predicates.push(new MatchPredicate(fieldInfo, matchOp, value));
};

RemoteQueryBuilder.prototype.and = function() {
    var right = this.predicates.pop();
    var left = this.predicates.pop();
    this.predicates.push(new AndPredicate(left, right));
};

RemoteQueryBuilder.prototype.or = function() {
    var right = this.predicates.pop();
    var left = this.predicates.pop();
    this.predicates.push(new OrPredicate(left, right));
};

RemoteQueryBuilder.prototype.not = function() {
    var top = this.predicates.pop();
    this.predicates.push(new NotPredicate(top));
};


RemoteQueryBuilder.prototype.setFirst = function(value) {
    this.first = value;
};

RemoteQueryBuilder.prototype.setLast = function(value) {
    this.last = value;
};


RemoteQueryBuilder.prototype.build = function() {
    return {
        predicate: this.predicates==null ? null : this.predicates.pop(),
        first: this.first,
        last: this.last,
        orderBys : this.orderBys
    };
};

RemoteQueryBuilder.prototype.addOrderByClause = function(info, descending) {
    if (this.orderBys == null)
        this.orderBys = [];
    this.orderBys.push({info: info, descending: descending});
};

function AndPredicate(left, right) {
	this.type = "AndPredicate";
    this.left = left;
    this.right = right;
    return this;
}

function OrPredicate(left, right) {
	this.type = "OrPredicate";
    this.left = left;
    this.right = right;
    return this;
}

function NotPredicate(predicate) {
	this.type = "NotPredicate";
    this.predicate = predicate;
    return this;
}

function MatchPredicate(info, matchOp, value) {
	this.type = "MatchPredicate";
    this.info = info;
    this.matchOp = MatchOp[matchOp.name];
    this.value = writeJSONValue(value);
    return this;
}



DataStore.instance = new RemoteStore();