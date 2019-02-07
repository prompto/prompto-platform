package prompto.debug;

import prompto.config.IConfigurationReader;
import prompto.config.IDebugRequestListenerConfiguration;

public class HttpServletDebugRequestListenerFactory implements IDebugRequestListenerFactory {

	@Override
	public IDebugRequestListener newListener(IDebugRequestListenerConfiguration config, LocalDebugger debugger) {
		return new HttpServletDebugRequestListener(debugger);
	}
	
	@Override
	public IDebugRequestListenerConfiguration newConfiguration(IConfigurationReader reader) {
		return new IDebugRequestListenerConfiguration.Inline().withFactory(this.getClass().getName());
	}

}
