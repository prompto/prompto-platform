package prompto.cloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;



public class AwsCloudHelper extends CloudHelper {
	
	private Boolean isThisCloud = null;
	
	@Override
	public boolean checkHost() {
		if(isThisCloud==null)
			isThisCloud = checkThisCloud();
		return isThisCloud;
	}
	
	@Override
	public URL getAddOnURL() {
		if(!checkHost())
			return null;
		File awsDir = new File("/AwsClient/");
		for(File child : awsDir.listFiles()) {
			if(child.getName().startsWith("AwsClient-") && child.getName().endsWith(".jar")) try {
				return child.toURI().toURL();
			} catch(IOException e) {
				return null;
			}		
		}
		return null;
	}

	private Boolean checkThisCloud() {
		try {
			try(Reader reader = new FileReader("/sys/devices/virtual/dmi/id/bios_version")) {
				try(BufferedReader buffer = new BufferedReader(reader)) {
					for(;;) {
						String line = buffer.readLine();
						if(line==null)
							return false;
						if(line.toLowerCase().contains("amazon"))
							return true;
					}
				}
			}
		} catch(IOException e) {
			return false;
		}
	}

} /*
path = "file:/sys/devices/virtual/dmi/id/bios_version"
data = read all from Url with path as path
return "amazon" in data
*/