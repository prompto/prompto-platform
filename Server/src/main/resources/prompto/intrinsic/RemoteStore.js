function StoredDocument(categories) {
	this.category = categories;
    return this;
}


StoredDocument.prototype.getData = function(name) {
    return this.hasOwnProperty(name) ? this[name] : null;
};

StoredDocument.prototype.matches = function(predicate) {
    if(predicate==null)
        return true;
    else
        return predicate.matches(this);
};

function StorableDocument(categories, dbIdFactory) {
    if(!categories)
        throw new Error("!!!");
    this.category = categories;
    this.dbIdFactory = dbIdFactory;
    this.document = null;
    return this;
}


StorableDocument.prototype.isDirty = function() {
    return this.document!=null;
};

StorableDocument.prototype.clear = function() {
    this.document = null;
};

StorableDocument.prototype.getDbId = function() {
	return this.document ? (this.document.dbId || null) : null;
};


StorableDocument.prototype.getOrCreateDbId = function() {
	var dbId = this.getDbId();
	if(dbId==null) {
		if(this.dbIdFactory)
			dbId = this.dbIdFactory.provider();
       	if(dbId!=null) 
			this.setDbId(dbId);
		else {
			dbId = $DataStore.instance.nextDbId();
	        if(this.dbIdFactory)
	        	this.dbIdFactory.listener(dbId);
			this.setData("dbId", dbId, dbId);
        }
		
    }
    return dbId;
};


StorableDocument.prototype.setDbId = function(dbId) {
	if(this.document)
		this.document.dbId  = writeJSONValue(dbId, true);
};


StorableDocument.prototype.setData = function(name, value, dbId) {
    if(!this.document) {
        this.document = new StoredDocument(this.category);
        this.document.dbId = dbId || this.getOrCreateDbId();
    }
    this.document[name] = writeJSONValue(value, true);
};

StorableDocument.prototype.updateDbIds = function(dbIds) {
	Object.getOwnPropertyNames(this.document)
		.forEach(function(name) { this.updateDbId(dbIds, name); }, this);
};

StorableDocument.prototype.updateDbId = function(dbIds, name) {
	var value = this.document[name]; 
	if(value) {
		values = Array.isArray(value) ? value : [value]; };
		values.filter(function(value) { return value.tempDbId; }, this)
		.forEach(function(value) { 
			var dbId = dbIds[value.tempDbId];
			if(dbId) {
				this.document[name] = dbId;
				if(name==="dbId" && this.dbIdFactory)
					this.dbIdFactory.listener(dbId);
			} 
		}, this);
};


function recordToStored(record) {
	if(record==null)
		return null;
	else {
		var stored = new StoredDocument([record.type]);
		Object.getOwnPropertyNames(record.value)
			.forEach(function(name) {
				var value = record.value[name];
				stored[name] = readJSONValue(value);
			});
		return stored;
	}
};

function StoredIterable(records) {
	this.count = function() { return records.count; };
	this.totalCount = function() { return records.totalCount; };
	this.iterator = function() {
		var index = 0;
		return {
			hasNext: function() { return index < records.count; },
			next: function() { 
				var record = records.value[index++];
				return recordToStored(record);
			}
		};
	}
	return this;
}

function RemoteStore() {
	this.lastDbId = 0;
	this.nextDbId = function() {
		tempDbId = --this.lastDbId;
		return { tempDbId: tempDbId, getText: function() { return "" + tempDbId; } };
	};
	this.newStorableDocument = function(categories, dbIdFactory) {
		return new StorableDocument(categories, dbIdFactory);
	};
	this.newQueryBuilder = function() {
		return new RemoteQueryBuilder();
	};
	this.fetchSync = function(url, body) {
		var response = null;
		var request  = new XMLHttpRequest();
		request.open("POST", url, false); // must be synchronous
		if(!(body instanceof FormData))
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
		if(!(body instanceof FormData))
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
	this.convertStorables = function(toStore, formData) {
		return Array.from(toStore).map(function(storable) { return this.convertStorable(storable, formData); }, this);
	};
	this.convertStorable = function(storable, formData) {
		var doc = storable.document; 
	 	if(typeof(doc.dbId) === "object" && !doc.dbId.tempDbId && (!doc.dbId.type || !doc.dbId.value)) {
	 		doc.dbId = doc.dbId.toString();
	 	}
		Object.getOwnPropertyNames(doc).map(function(key) {
			var data = doc[key];
			if(data && (data.type==="Image" || data.type==="Blob")) {
				value = data.value;
				if(value) {
					if (value.binaryFile) {
						value.partName = '@' + value.binaryFile.name;
						formData.append(value.partName, value.binaryFile);
						delete value.binaryFile;
					} else if (value.url)
						delete doc[key]; // the binary was not modified
				}
			}
		});
	 	return doc;
	};
	this.prepareStore = function(toDel, toStore, withMeta) {
		var formData = new FormData();
		if(toDel)
			formData.append("toDelete", JSON.stringify(toDel));
		if(toStore) {
			toStore = this.convertStorables(toStore, formData);
			formData.append("toStore", JSON.stringify(toStore));
		}
		if(withMeta) {
			var json = writeJSONValue(withMeta, false, formData);
			formData.append("withMeta", JSON.stringify(json));
		}
			
		return formData;
	};
	this.deleteAndStore = function(toDel, toStore, withMeta) {
		var formData = this.prepareStore(toDel, toStore, withMeta);
		var response = this.fetchSync("/ws/store/deleteAndStore", formData);
		toStore.forEach(function(storable) { storable.updateDbIds(response.data); });
	};
	this.deleteAndStoreAsync = function(toDel, toStore, withMeta, andThen) {
		var formData = this.prepareStore(toDel, toStore, withMeta);
		this.fetchAsync("/ws/store/deleteAndStore", formData, function(response) {
			if(toStore)
				toStore.forEach(function(storable) { storable.updateDbIds(response.data); });
			andThen();
		});
	};
	this.fetchOne = function(query) {
		var response = this.fetchSync("/ws/store/fetchOne", JSON.stringify(query));
		return recordToStored(response.data);
	};
	this.fetchOneAsync = function(query, andThen) {
		this.fetchAsync("/ws/store/fetchOne", JSON.stringify(query), function(response) {
			record = recordToStored(response.data);
			andThen(record);
		});

	};
	this.fetchMany = function(query, mutable) {
		var response = this.fetchSync("/ws/store/fetchMany", JSON.stringify(query));
		var iterable = new StoredIterable(response.data);
		return new Cursor(mutable, iterable);
	};
	this.fetchManyAsync = function(query, mutable, andThen) {
		this.fetchAsync("/ws/store/fetchMany", JSON.stringify(query), function(response) {
			var iterable = new StoredIterable(response.data);
			var cursor = new Cursor(mutable, iterable);
			andThen(cursor);
		});
	};
	this.nextSequenceValue = function(name) {
		var response = null;
		var request  = new XMLHttpRequest();
		request.open("GET", "/ws/store/nextSequenceValue?name=" + name, false); // must be synchronous
		request.onload = function() { 
			if (this.status == 200)
				response = JSON.parse(this.responseText); 
			else
				throw new Error(this.statusText);
		};
		request.send();
		if(response)
			return response;
		else
			return -1;
	
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
    this.value = writeJSONValue(value, true);
    return this;
}



$DataStore.instance = new RemoteStore();