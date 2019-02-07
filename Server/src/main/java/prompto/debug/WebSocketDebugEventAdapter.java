package prompto.debug;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;

import prompto.debug.IDebugEvent;
import prompto.server.AppServer;
import prompto.utils.Logger;

public class WebSocketDebugEventAdapter implements IDebugEventAdapter {

	static Logger logger = new Logger();
	
	Session session;
	
	public synchronized Session getSession() {
		return session;
	}
	
	public synchronized void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void handleConnectedEvent(IDebugEvent.Connected event) {
		// this event is notified during server boot, before the client actually attempts to connect
		// there is no session, and there can't be, so no point polluting the logs with an error
		logger.debug(()->"Skipping " + event.getType().name());
	}

	@Override
	public void handleResumedEvent(ResumeReason reason) {
		send(new IDebugEvent.Resumed(reason));
	}

	@Override
	public void handleSuspendedEvent(SuspendReason reason) {
		send(new IDebugEvent.Suspended(reason));
	}

	@Override
	public void handleTerminatedEvent() {
		send(new IDebugEvent.Terminated());
	}

	private void send(IDebugEvent event) {
		logger.debug(()->"Sending " + event.getType().name());
		Session session = getSession();
		if(session!=null) try {
			String message = Serializer.writeDebugEvent(event);
			Future<Void> action = session.getRemote().sendStringByFuture(message);
			action.get(5, TimeUnit.SECONDS);
		} catch(Throwable t) {
			logger.error(()->"While sending: " + event, t);
		} else
			logger.error(()->"No session for sending: " + event);
	}

	public void wire() {
		DebugEventServlet servlet = AppServer.getDebugEventServlet();
		if(servlet!=null) 
			servlet.setAdapter(this);
		
	}


	

}
