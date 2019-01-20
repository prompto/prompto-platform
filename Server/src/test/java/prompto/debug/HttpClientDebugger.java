package prompto.debug;

import java.util.Collection;

import prompto.parser.ISection;

public class HttpClientDebugger implements IDebugger {

	public HttpClientDebugger(WebSocketDebugEventListener eventListener) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setListener(IDebugEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void installBreakpoint(ISection section) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canTerminate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyTerminated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Status getStatus(IThread thread) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStack<?> getStack(IThread thread) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLine(IThread thread) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isStepping(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuspended(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canResume(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSuspend(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepInto(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepOver(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStepOut(IThread thread) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void suspend(IThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume(IThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepInto(IThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepOut(IThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stepOver(IThread thread) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<? extends IVariable> getVariables(IThread thread, IStackFrame frame) {
		// TODO Auto-generated method stub
		return null;
	}

}
