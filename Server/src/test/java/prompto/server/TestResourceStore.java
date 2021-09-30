package prompto.server;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import prompto.code.ICodeStore;
import prompto.code.Resource;
import prompto.code.TextResource;

public class TestResourceStore extends BaseServerTest {

	@Test
	public void testStaticResource() throws Exception {
		URL url = new URL("http://localhost:" + port + "/js/lib/require.js");
		URLConnection cnx = url.openConnection();
		InputStream input = cnx.getInputStream();
		assertNotNull(input);
		input.close();
	}

	@Test
	public void testStoredResource() throws Exception {
		ICodeStore store = ICodeStore.getInstance();
		Resource resource = newResource("stuff.html");
		store.storeResource(resource, null);
		assertNotNull(store.fetchResource(resource.getName()));
		URL url = new URL("http://localhost:" + port + "/stuff.html");
		URLConnection cnx = url.openConnection();
		InputStream input = cnx.getInputStream();
		assertNotNull(input);
		input.close();
	}

	private Resource newResource(String name) {
		TextResource resource = new TextResource();
		resource.setName(name);
		resource.setMimeType("text/html");
		resource.setBody("<html><body>Hello</body></html>");
		return resource;
	}
}
