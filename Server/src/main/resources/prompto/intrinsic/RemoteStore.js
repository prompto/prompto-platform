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
       	dbId = DataStore.instance.nextDbId();
        if(this.dbIdListener)
        	this.dbIdListener(dbId);
        this.setData("dbId", dbId, dbId);
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
        this.document.dbId = dbId? dbId : this.getOrCreateDbId();
    }
    this.document[name] = writeJSONValue(value, true);
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



DataStore.instance = new RemoteStore();