package prompto.debug;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
public class DebugEventServlet extends WebSocketServlet {

	DebuggerWebSocketCreator creator = new DebuggerWebSocketCreator();
	
	public void setAdapter(WebSocketDebugEventAdapter adapter) {
		creator.setAdapter(adapter);
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		factory.setCreator(creator);
	}
	
	static class DebuggerWebSocketCreator implements WebSocketCreator {

		WebSocketDebugEventAdapter adapter;
		
		public void setAdapter(WebSocketDebugEventAdapter adapter) {
			this.adapter = adapter;
		}
		
		@Override
		public DebuggerWebSocketListener createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
			return new DebuggerWebSocketListener(adapter);
		}
		
	}

	static class DebuggerWebSocketListener implements WebSocketListener {

		WebSocketDebugEventAdapter adapter;
		Session session;
		
		public DebuggerWebSocketListener(WebSocketDebugEventAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void onWebSocketConnect(Session session) {
			this.session = session;
		}

		@Override
		public void onWebSocketText(String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketBinary(byte[] payload, int offset, int len) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketClose(int statusCode, String reason) {
			// TODO Auto-generated method stub
			
		}

	
		@Override
		public void onWebSocketError(Throwable cause) {
			// TODO Auto-generated method stub
			
		}

	}


}
