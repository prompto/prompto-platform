package prompto.debug;

import prompto.config.IConfigurationReader;
import prompto.config.IDebugEventAdapterConfiguration;

public class WebSocketDebugEventAdapterFactory implements IDebugEventAdapterFactory {

	@Override
	public IDebugEventAdapter newAdapter(IDebugEventAdapterConfiguration config) {
		return new WebSocketDebugEventAdapter();
	}
	
	@Override
	public IDebugEventAdapterConfiguration newConfiguration(IConfigurationReader reader) {
		return new IDebugEventAdapterConfiguration.Inline().withFactory(this.getClass().getName());
	}

}
