package prompto.debug;

import prompto.server.AppServer;

public class HttpServletDebugRequestListener implements IDebugRequestListener {

	IDebugger debugger;
	
	public HttpServletDebugRequestListener(IDebugger debugger) {
		this.debugger = debugger;
	}

	@Override
	public IDebugEvent.Connected startListening() throws Exception {
		return new IDebugEvent.Connected(); // always connected
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
