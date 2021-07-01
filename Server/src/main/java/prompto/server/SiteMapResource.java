package prompto.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.time.ZonedDateTime;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jetty.util.resource.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import prompto.code.ICodeStore;

public class SiteMapResource extends Resource {

	public static SiteMapResource fromWebSitePages(String urlPrefix) {
		Iterable<prompto.code.Resource> pages = ICodeStore.getInstance().fetchLatestResourcesWithMimeTypes("text/html", "text/page");
		return fromWebSitePages(urlPrefix, pages);
	}

	private static SiteMapResource fromWebSitePages(String urlPrefix, Iterable<prompto.code.Resource> pages) {
		Document doc = documentFromWebSitePages(urlPrefix, pages);
		return fromSiteMap(doc);
	}

	private static SiteMapResource fromSiteMap(Document doc) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(output);
			transformer.transform(source, result);
			return new SiteMapResource(output.toByteArray());
		} catch(Exception e) {
			return null;
		}
	}

	public static final String SITEMAPS_URI = "http://www.sitemaps.org/schemas/sitemap/0.9";
	
	private static Document documentFromWebSitePages(String urlPrefix, Iterable<prompto.code.Resource> pages) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			Document doc = factory.newDocumentBuilder().newDocument();
			Element root = doc.createElementNS(SITEMAPS_URI, "urlset");
			pages.forEach(page -> {
				Element elem = elementFromWebSitePage(doc, urlPrefix, page);
				root.appendChild(elem);
			});
			doc.appendChild(root);
			return doc;
		} catch(Exception e) {
			return null;
		}
	}

	private static Element elementFromWebSitePage(Document doc, String urlPrefix, prompto.code.Resource page) {
		Element elem = doc.createElementNS(SITEMAPS_URI, "url");
		Element child = doc.createElementNS(SITEMAPS_URI, "loc");
		child.setTextContent(urlPrefix + page.getName());
		elem.appendChild(child);
		if(page.getLastModified()!=null) {
			child = doc.createElementNS(SITEMAPS_URI, "lastmod");
			child.setTextContent(page.getLastModified().toString());
			elem.appendChild(child);
		}
		return elem;
	}

	byte[] data;
	InputStream stream;
	
	public SiteMapResource(byte[] data) {
		this.data = data;
	}

	@Override
	public boolean isContainedIn(Resource r) throws MalformedURLException {
		return false;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public long lastModified() {
		return ZonedDateTime.now().toEpochSecond();
	}

	@Override
	public URL getURL() {
		try {
			return new URL("/sitemap.xml");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public File getFile() throws IOException {
		return null;
	}

	@Override
	public String getName() {
		return "sitemap.xml";
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if(stream == null)
			return new ByteArrayInputStream(data);
		else
			throw new IllegalStateException("InputStream is already open!");
	}

	@Override
	public void close() {
		if(stream != null) try {
			stream.close();
			stream = null;
		} catch(IOException e) {
			// pass
		} else 
			throw new IllegalStateException("InputStream is not open!");
		
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return data.length;
	}


	@Override
	public ReadableByteChannel getReadableByteChannel() throws IOException {
		return null;
	}

	@Override
	public boolean delete() throws SecurityException {
		return false;
	}

	@Override
	public boolean renameTo(Resource dest) throws SecurityException {
		return false;
	}

	@Override
	public String[] list() {
		return null;
	}

	@Override
	public Resource addPath(String path) throws IOException, MalformedURLException {
		return null;
	}

}
