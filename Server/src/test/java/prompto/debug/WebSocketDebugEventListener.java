package prompto.debug;

import org.eclipse.jetty.websocket.client.WebSocketClient;

public class WebSocketDebugEventListener {

	IDebugEventListener listener;
	WebSocketClient client;
	
	public WebSocketDebugEventListener(IDebugEventListener listener) {
		this.listener = listener;
	}

	public void startListening() {
		// TODO Auto-generated method stub
	}

	public void stopListening() {
		// TODO Auto-generated method stub
		
	}

	public boolean isListening() {
		// TODO Auto-generated method stub
		return false;
	}

}
