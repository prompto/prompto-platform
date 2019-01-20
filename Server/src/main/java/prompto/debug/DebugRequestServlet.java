package prompto.debug;

import prompto.server.CleverServlet;

@SuppressWarnings("serial")
public class DebugRequestServlet extends CleverServlet {

	IDebugger debugger;
	
	public void setDebugger(IDebugger debugger) {
		this.debugger = debugger;
	}

}
