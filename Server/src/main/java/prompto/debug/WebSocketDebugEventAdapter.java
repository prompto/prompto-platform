package prompto.debug;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;

import prompto.debug.ack.Acknowledged;
import prompto.debug.ack.IAcknowledgement;
import prompto.debug.event.ConnectedDebugEvent;
import prompto.debug.event.IDebugEvent;
import prompto.server.AppServer;
import prompto.utils.Logger;

public class WebSocketDebugEventAdapter extends DebugEventAdapterBase {

	static Logger logger = new Logger();
	
	Session session;
	
	public synchronized Session getSession() {
		return session;
	}
	
	public synchronized void setSession(Session session) {
		this.session = session;
		notify();
	}

	public synchronized void waitSession() {
		while(session==null) try {
			wait();
		} catch(InterruptedException e) {
			// nothing to do
		}
	}

	@Override
	public void handleConnectedEvent(ConnectedDebugEvent event) {
		// this event is notified during server boot, before the client actually attempts to connect
		// there is no session, and there can't be, so no point polluting the logs with an error
		logger.debug(()->"Skipping " + event.getClass().getName());
	}
	
	

	@Override
	protected IAcknowledgement send(IDebugEvent event) {
		logger.debug(()->"Sending " + event.getClass().getName());
		Session session = getSession();
		if(session!=null) try {
			String message = Serializer.writeMessage(event);
			Future<Void> action = session.getRemote().sendStringByFuture(message);
			action.get(5, TimeUnit.SECONDS);
			return new Acknowledged();
		} catch(Throwable t) {
			logger.error(()->"While sending: " + event, t);
		} else
			logger.error(()->"No session for sending: " + event);
		return null;
	}

	public void wire() {
		DebugEventServlet servlet = AppServer.getDebugEventServlet();
		if(servlet!=null) 
			servlet.setAdapter(this);
		
	}


	

}
