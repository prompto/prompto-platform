var RemoteRunner = {
		
	execute: function(body, andThen, bindTo) {
		var result = body.bind(bindTo)();
		andThen.bind(bindTo)(result);
	}

};