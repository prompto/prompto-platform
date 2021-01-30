package prompto.debug;

import prompto.debug.event.ConnectedDebugEvent;
import prompto.server.AppServer;

public class HttpServletDebugRequestListener implements IDebugRequestListener {

	IDebugger debugger;
	
	public HttpServletDebugRequestListener(IDebugger debugger) {
		this.debugger = debugger;
	}

	@Override
	public ConnectedDebugEvent startListening() throws Exception {
		return new ConnectedDebugEvent(); // always connected
	}

	@Override
	public void stopListening() {
	}


	public void wire() {
		DebugRequestServlet servlet = AppServer.getDebugRequestServlet();
		if(servlet!=null) 
			servlet.setDebugger(this.debugger);
	}

}
