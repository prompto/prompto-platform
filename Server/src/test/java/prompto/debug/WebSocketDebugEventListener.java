package prompto.debug;

import java.net.URI;
import java.util.UUID;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketConnectionListener;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.events.JettyListenerEventDriver;

public class WebSocketDebugEventListener {

	String host;
	int port;
	UUID uuid;
	IDebugEventListener eventListener;
	WebSocketClient client;
	WebSocket socket;
	
	public WebSocketDebugEventListener(String host, int port, IDebugEventListener eventListener) {
		this.host = host;
		this.port = port;
		this.uuid = UUID.randomUUID();
		this.eventListener = eventListener;
		this.client = new WebSocketClient();
		this.socket = new WebSocket();
	}

	public void startListening() throws Exception {
		String uri = "ws://" + host + ":" + port + "/ws/debug-event?uuid=" + uuid.toString();
		client.start();
		client.connect(socket, new URI(uri));
	}

	public void stopListening() throws Exception {
		client.stop();
	}

	public boolean isListening() {
		return client.isStarted();
	}
	
	class WebSocket extends JettyListenerEventDriver {

		public WebSocket() {
			super(WebSocketPolicy.newClientPolicy(), new WebSocketListener());
		}
		
		
	}

	class WebSocketListener implements WebSocketConnectionListener {

		@Override
		public void onWebSocketClose(int statusCode, String reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketConnect(Session session) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketError(Throwable cause) {
			// TODO Auto-generated method stub
			
		}

	}

}
