var RemoteRunner = {
		
	run: function(name, params, andThen, bindTo) {
			var formData = this.makeParams(params);
		    axios.post('/ws/run/' + name, formData).then(function(response) {
		    	 response = response.data
		    	 if(response.error) 
		    		 alert(response.error); // TODO throw ?
		    	 else {	 
		     		var result = readJSONValue(response.data);
		     		andThen.bind(bindTo)(result);
		    	 }
	     	});
		},
	makeParams: function(params) {
		var formData = new FormData();
		params.forEach(function(param) {
			var value = writeJSONValue(param.value, false, formData);
			if(value && value.type && value.value) {
				param.type = value.type;
				param.value = value.value;
			} else
				param.value = value;
		});
		formData.append("params", JSON.stringify(params));
		return formData;
		},
	runSync: function(name, params) {	
			var formData = this.makeParams(params);
			var response = null;
			var request  = new XMLHttpRequest();
			request.open("POST", "/ws/run/" + name, false); // make call synchronous
			request.onload = function() { 
				if (this.status == 200)
					response = JSON.parse(this.responseText); 
				else
					throw new Error(this.statusText);
			};
			request.send(formData);
			return response && response.data ? readJSONValue(response.data) : null;
		}

};