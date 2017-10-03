package prompto.cloud;

import java.net.URL;

public enum Cloud {
	AWS(new AwsCloudHelper())
	;
	
	CloudHelper helper;
	
	Cloud(CloudHelper helper) {
		this.helper = helper;
	}

	public boolean checkHost() {
		return helper.checkHost();
	}

	public URL getAddOnURL() {
		return helper.getAddOnURL();
	}
}
