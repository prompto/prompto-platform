package prompto.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.session.AbstractSession;
import org.eclipse.jetty.server.session.HashSessionManager;

import prompto.config.IHttpConfiguration;
import prompto.utils.Logger;

public class SessionManager extends HashSessionManager {

	static final Logger logger = new Logger();

	public SessionManager(IHttpConfiguration config) {
		setMaxInactiveInterval(300); // TODO make timeout configurable
		if(config.getPort()!=443 && config.getPort()!=80) {
			_sessionCookie = _sessionCookie + "_" + config.getPort();
			logger.info(()->"Setting custom session cookie: " + _sessionCookie);
		}
	}

	@Override
	public AbstractSession getSession(String idInCluster) {
		AbstractSession session = super.getSession(idInCluster);
		if(session==null)
			logger.debug(()->"No session found for " + idInCluster);
		return session;
	}
	
	@Override
	public boolean isValid(HttpSession session) {
		boolean valid = super.isValid(session);
		logger.debug(()->"Session " + session.getId() + " valid: " + valid);
		return valid;
	}
	
	@Override
	protected void shutdownSessions() throws Exception {
		logger.debug(()->"Shutting down sessions");
		super.shutdownSessions();
	}
	
	@Override
	public void renewSessionId(String oldClusterId, String oldNodeId, String newClusterId, String newNodeId) {
		logger.debug(()->"Renewing session " + oldClusterId + " to " + newClusterId);
		super.renewSessionId(oldClusterId, oldNodeId, newClusterId, newNodeId);
	}
	
	@Override
	protected AbstractSession newSession(HttpServletRequest request) {
		logger.debug(()->"newSession");
		return super.newSession(request);
	}
	
	@Override
	protected AbstractSession newSession(long created, long accessed, String clusterId) {
		logger.debug(()->"newSession " + clusterId);
		return super.newSession(created, accessed, clusterId);
	}
}    

