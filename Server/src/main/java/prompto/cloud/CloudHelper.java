package prompto.cloud;

import java.net.URL;

import prompto.utils.Logger;

public abstract class CloudHelper {

	static Logger logger = new Logger();
	
	public static URL getCloudAddOnURL() {
		logger.info(()->"Checking current cloud...");
		for(Cloud cloud : Cloud.values()) {
			if(cloud.checkHost()) {
				logger.info(()->"Running on " + cloud.name() + " cloud.");
				return cloud.getAddOnURL();
			}
		}
		logger.info(()->"Not running on cloud.");
		return null;
	}

	public abstract boolean checkHost();

	public abstract URL getAddOnURL();
}
