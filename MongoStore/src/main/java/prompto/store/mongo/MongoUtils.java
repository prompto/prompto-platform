package prompto.store.mongo;

import java.io.IOException;

import org.bson.Document;

import prompto.utils.ResourceUtils;

public abstract class MongoUtils {

	public static Document readResource(String resource) throws IOException {
		return Document.parse(ResourceUtils.getResourceAsString(resource));
	}

}
