package prompto.debug;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
public class DebugEventServlet extends WebSocketServlet {

	WebSocketDebugEventAdapter adapter;
	
	public void setAdapter(WebSocketDebugEventAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.register(DebuggerWebSocket.class);
	}

	@WebSocket
	static class DebuggerWebSocket {

		@SuppressWarnings("unused")
		private Session session;

		@OnWebSocketConnect
		public void onConnect(Session session) {
			this.session = session;
		}

		@OnWebSocketMessage
		public void onMessage(String msg) {
			System.out.printf("Got msg: %s%n", msg);
		}

		@OnWebSocketClose
		public void onClose(int statusCode, String reason) {
			this.session = null;
		}

	}


}
