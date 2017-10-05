package prompto.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import prompto.utils.ManualTests;

@Category(ManualTests.class)
public class TestResourceInJar {
	
			
	@Test
	public void testThatResourceCanBeFoundInZipFile() throws IOException {
		try(ZipFile zip = new ZipFile("/Users/ericvergnaud/.m2/repository/org/prompto/Server/0.0.1-SNAPSHOT/Server-0.0.1-SNAPSHOT.jar")) {
			ZipEntry entry = zip.getEntry("jetty-logging.properties");
			assertNotNull(entry);
		}
	}

}
