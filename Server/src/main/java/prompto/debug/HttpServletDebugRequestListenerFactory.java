package prompto.debug;

import prompto.config.IDebugConfiguration;

public class HttpServletDebugRequestListenerFactory implements IDebugRequestListenerFactory {

	@Override
	public IDebugRequestListener newInstance(IDebugConfiguration config, LocalDebugger debugger) {
		return new HttpServletDebugRequestListener(debugger);
	}

}
