package prompto.debug;

import java.net.URI;
import java.util.UUID;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import prompto.debug.event.IDebugEvent;
import prompto.utils.Logger;


public class WebSocketDebugEventListener {

	static Logger logger = new Logger();
	
	String remoteHost;
	int port;
	UUID uuid;
	IDebugEventListener eventListener;
	WebSocketClient client;
	WebSocket socket;

	public WebSocketDebugEventListener(String host, int port, IDebugEventListener eventListener) {
		this.remoteHost = host;
		this.port = port;
		this.uuid = UUID.randomUUID();
		this.eventListener = eventListener;
		this.client = new WebSocketClient();
		this.socket = new WebSocket();
	}

	public void startListening() throws Exception {
		logger.debug(()->"Client socket connecting");
		String uri = "ws://" + remoteHost + ":" + port + "/ws/debug-event?sessionId=" + uuid.toString();
		client.start();
		client.connect(socket, new URI(uri));
	}

	public void stopListening() throws Exception {
		logger.debug(()->"Client socket disconnecting");
		client.stop();
	}

	public boolean isListening() {
		return client.isStarted();
	}

	class WebSocket implements WebSocketListener {

		@Override
		public void onWebSocketConnect(Session session) {
			logger.debug(()->"Client socket connected");
		}

		@Override
		public void onWebSocketClose(int statusCode, String reason) {
			logger.debug(()->"Client socket closed: " + reason);
		}

		@Override
		public void onWebSocketError(Throwable cause) {
			logger.error(()->"Client socket error", cause);
		}

		@Override
		public void onWebSocketBinary(byte[] payload, int offset, int len) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketText(String message) {
			logger.debug(()->"Client socket received: " + message);
			try {
				IDebugEvent event = Serializer.readDebugEvent(message);
				event.execute(eventListener);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

	}

}
