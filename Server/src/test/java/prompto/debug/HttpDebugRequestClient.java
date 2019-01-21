package prompto.debug;

import java.io.InputStream;
import java.io.OutputStream;

public class HttpDebugRequestClient extends DebugRequestClient {

	public HttpDebugRequestClient(String host, int port) {
		this.remoteHost = host;
		this.remotePort = port;
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
	protected void sendRequest(OutputStream output, IDebugRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IDebugResponse readResponse(InputStream input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
