package prompto.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.eclipse.jetty.util.resource.Resource;

public class CodeStoreResource extends Resource implements MimeTypeProvider {

	final prompto.code.Resource wrapped;
	
	
	public CodeStoreResource(prompto.code.Resource wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public String getMimeType() {
		return wrapped.getMimeType();
	}
	
	@Override
	public boolean isContainedIn(Resource r) throws MalformedURLException {
		return false;
	}

	@Override
	public void close() {
		// nothing to do
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
		OffsetDateTime lastModified = wrapped.getLastModified();
		if(lastModified==null)
			lastModified = OffsetDateTime.now(ZoneOffset.UTC);
		return lastModified.toInstant().toEpochMilli();
	}

	@Override
	public long length() {
		return wrapped.length();
	}

	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public File getFile() throws IOException {
		return null;
	}

	@Override
	public String getName() {
		return "/" + wrapped.getName();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return wrapped.getInputStream();
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
