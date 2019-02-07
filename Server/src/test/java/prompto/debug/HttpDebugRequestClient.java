package prompto.debug;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletResponse;

public class HttpDebugRequestClient extends DebugRequestClient {

		Supplier<Boolean> remoteAlive;
	
	public HttpDebugRequestClient(String host, int port, Supplier<Boolean> remoteAlive) {
		this.remoteHost = host;
		this.remotePort = port;
		this.remoteAlive = remoteAlive;
	}

	@Override
	protected boolean isRemoteAlive() {
		return remoteAlive.get();
	}
	
	@Override
	public void setListener(IDebugEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyTerminated() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Status getProcessStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IDebugResponse sendRequest(IDebugRequest request, Consumer<Exception> errorHandler) {
		try {
			URL url = new URL("http://" + remoteHost + ":" + remotePort + "/ws/debug-request");
			HttpURLConnection cnx = (HttpURLConnection)url.openConnection();
			cnx.setRequestMethod("POST");
			cnx.setDoOutput(true);
			cnx.setRequestProperty("Content-Type", "application/json");
			cnx.setRequestProperty("charset", "utf-8");
			try(OutputStream output = cnx.getOutputStream()) {
				Serializer.writeDebugRequest(output, request);
				if(cnx.getResponseCode()!=HttpServletResponse.SC_OK)
					throw new IOException("Http error " + cnx.getResponseCode());
				try(InputStream input = cnx.getInputStream()) {
					return Serializer.readDebugResponse(input);
				}
			}
		} catch (Exception e) {
			if(errorHandler!=null)
				errorHandler.accept(e);
			else
				e.printStackTrace(System.err);
			return null;
		}
	}

}
