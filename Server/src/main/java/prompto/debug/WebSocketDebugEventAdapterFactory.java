package prompto.debug;

import prompto.config.IDebugConfiguration;

public class WebSocketDebugEventAdapterFactory implements IDebugEventAdapterFactory {

	@Override
	public IDebugEventAdapter newInstance(IDebugConfiguration config) {
		return new WebSocketDebugEventAdapter();
	}

}
