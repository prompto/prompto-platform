package prompto.debug;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import prompto.utils.Logger;

@SuppressWarnings("serial")
public class DebugEventServlet extends WebSocketServlet {

	static Logger logger = new Logger();

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

		private void send(IDebugEvent event) {
			logger.debug(()->"Server sending " + event.getType().name());
			try {
				String message = Serializer.writeDebugEvent(event);
				session.getRemote().sendString(message);
			} catch(Throwable t) {
				logger.error(()->"While sending: " + event, t);
			}
		}

		@Override
		public void onWebSocketConnect(Session session) {
			logger.debug(()->"Server socket connecting");
			if(session==this.session)
				return;
			if(this.session!=null) {
				adapter.setSession(null);
				send(new IDebugEvent.Terminated());
			}
			this.session = session;
			adapter.setSession(session);
			send(new IDebugEvent.Connected());
		}


		@Override
		public void onWebSocketText(String message) {
			logger.debug(()->"Server received: " + message);
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketBinary(byte[] payload, int offset, int len) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWebSocketClose(int statusCode, String reason) {
			logger.debug(()->"Server socket closing");
			adapter.setSession(null);
			this.session = null;
		}

	
		@Override
		public void onWebSocketError(Throwable cause) {
			logger.error(()->"Server socket error", cause);
		}

	}


}
