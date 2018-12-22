var RemoteRunner = {
		
	run: function(name, params, andThen, bindTo) {
		params.forEach(function(param) {
			var value = writeJSONValue(param.value, false);
			if(value.type && value.value) {
				param.type = value.type;
				param.value = value.value;
			} else
				param.value = value;
		});
		var formData = new FormData();
		formData.append("params", JSON.stringify(params));
	     axios.post('/ws/run/' + name, formData).then(function(response) {
	    	 response = response.data
	    	 if(response.error) 
	    		 alert(response.error); // TODO throw ?
	    	 else {	 
	     		var result = readJSONValue(response.data);
	     		andThen.bind(bindTo)(result);
	    	 }
     	});
	     // TODO catch
	}

};