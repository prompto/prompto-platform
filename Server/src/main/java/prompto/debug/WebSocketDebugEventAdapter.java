package prompto.debug;

import prompto.debug.IDebugEvent.Connected;
import prompto.server.AppServer;

public class WebSocketDebugEventAdapter implements IDebugEventAdapter {

	@Override
	public void handleConnectedEvent(Connected event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleResumedEvent(ResumeReason reason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSuspendedEvent(SuspendReason reason) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleTerminatedEvent() {
		// TODO Auto-generated method stub
		
	}

	public void wire() {
		DebugEventServlet servlet = AppServer.getDebugEventServlet();
		if(servlet!=null) 
			servlet.setAdapter(this);
		
	}

	

}
