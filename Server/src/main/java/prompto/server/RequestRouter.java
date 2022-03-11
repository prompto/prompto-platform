package prompto.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;

import prompto.debug.ProcessDebugger;
import prompto.debug.WorkerDebugger;
import prompto.error.ExecutionError;
import prompto.error.PromptoError;
import prompto.error.TerminatedError;
import prompto.expression.IExpression;
import prompto.expression.MethodSelector;
import prompto.grammar.ArgumentList;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoDict;
import prompto.remoting.RemoteArgumentList;
import prompto.runtime.ApplicationContext;
import prompto.runtime.Context;
import prompto.runtime.Executor;
import prompto.runtime.Interpreter;
import prompto.runtime.Standalone;
import prompto.statement.MethodCall;
import prompto.store.DataStore;
import prompto.store.IStore;
import prompto.store.memory.MemStore;
import prompto.utils.Logger;
import prompto.value.BinaryValue;
import prompto.value.IValue;
import prompto.value.TextValue;

public class RequestRouter {

	static final Logger logger = new Logger();

	public void route(ExecutionMode mode, Identifier methodName, String jsonParams, Map<String, byte[]> parts, boolean main, HttpServletResponse response) throws Exception {
		boolean isTest = methodName.toString().startsWith("\"") && methodName.toString().endsWith("\"");
		switch(mode) {
		case INTERPRET:
			if(isTest)
				interpretTest(methodName, response);
			else
				interpretMethod(methodName, jsonParams, parts, main, response);
			break;
		case EXECUTE:
			if(isTest)
				executeTest(methodName, response);
			else
				executeMethod(methodName, jsonParams, parts, main, response);
			break;
		default:
			throw new InvalidParameterException(mode.name());
		}
	}
	
	private Context prepareContext(String name) {
		Context context = ApplicationContext.get().newLocalContext();
		ProcessDebugger processDebugger = ProcessDebugger.getInstance();
		if(processDebugger!=null) {
			logger.info(()->"Wiring debugger for incoming request");
			WorkerDebugger workerDebugger = Standalone.startWorkerDebugger(Thread.currentThread(), context);
			// step in start method by default
			// TODO: make this configurable
			workerDebugger.stepInto();
		}
		return context;
	}


	private void executeTest(Identifier testName, HttpServletResponse response) throws Exception {
		logger.debug(()->"Executing test: " + testName.toString());
		PrintStream oldOut = System.out;
		IStore oldStore = DataStore.getInstance();
		DataStore.setInstance(new MemStore());
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		Context context = prepareContext("Test: " + testName);
		try(var loader = Standalone.getClassLoader()) {
			Executor.executeTest(loader, testName.toString());
			bytes.flush();
			String[] lines = new String(bytes.toByteArray()).split("\n");
			writeJsonResponse(lines, response);
		} catch(TerminatedError e) {
			// not an error
		} catch(PromptoError e) {
			writeJsonErrorResponse(context, e, response);
		} finally {
			context.notifyCompleted();
			DataStore.setInstance(oldStore);
			System.setOut(oldOut);
			logger.debug(()->"Done executing test: " + testName.toString());
		}
	}

	private void interpretTest(Identifier testName, HttpServletResponse response) throws IOException {
		logger.debug(()->"Interpreting test: " + testName.toString());
		PrintStream oldOut = System.out;
		IStore oldStore = DataStore.getInstance();
		DataStore.setInstance(new MemStore());
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		Context context = prepareContext("Test: " + testName);
		try {
			Interpreter.interpretTest(context, testName, true);
			bytes.flush();
			String[] lines = new String(bytes.toByteArray()).split("\n");
			writeJsonResponse(lines, response);
		} catch(TerminatedError e) {
			// not an error
		} catch(PromptoError e) {
			writeJsonErrorResponse(context, e, response);
		} finally {
			context.notifyCompleted();
			DataStore.setInstance(oldStore);
			System.setOut(oldOut);
			logger.debug(()->"Done interpreting test: " + testName.toString());
		}
	}

	public void executeMethod(Identifier methodName, String jsonParams, Map<String, byte[]> parts, boolean main, HttpServletResponse response) throws Exception {
		logger.debug(()->"Executing method: " + methodName);
		Context context = prepareContext("Method: " + methodName);
		try(var loader = Standalone.getClassLoader()) {
			RemoteArgumentList params = RemoteArgumentList.read(context, jsonParams, parts);
			Class<?>[] argTypes = params.toJavaTypes(context, loader);
			Object[] args = params.toJavaValues(context);
			if(params.isEmpty() && main) {
				argTypes = new Class<?>[] { PromptoDict.class };
				args = new Object[] { null };
			}
			Object result = Executor.executeGlobalMethod(loader, methodName, argTypes, args);
			// TODO JSON output
			TextValue text = new TextValue(result==null ? "success!" : result.toString());
			writeJsonResponse(context, text, response);
		} catch(TerminatedError e) {
			// not an error
		} catch(PromptoError e) {
			writeJsonErrorResponse(context, e, response);
		} finally {
			context.notifyCompleted();
			logger.debug(()->"Done executing method: " + methodName);
		}
	}
	
	public void interpretMethod(Identifier methodName, String jsonParams, Map<String, byte[]> parts, boolean main, HttpServletResponse response) throws Exception {
		logger.debug(()->"Interpreting method: " + methodName);
		Context context = prepareContext("Method: " + methodName);
		try {
			RemoteArgumentList params = RemoteArgumentList.read(context, jsonParams, parts);
			ArgumentList arguments = params.toArguments(context);
			IValue value = interpretMethod(context, methodName, arguments, main);
			if(value==null)
				value = new TextValue("Success!");
			if(value instanceof BinaryValue)
				writeBinaryResponse((BinaryValue)value, response);
			else
				writeJsonResponse(context, value, response);
		} catch(TerminatedError e) {
			// not an error
		} catch(PromptoError e) {
			writeJsonErrorResponse(context, e, response);
		} finally {
			context.notifyCompleted();
			logger.debug(()->"Done interpreting method: " + methodName);
		}
	}

	private IValue interpretMethod(Context context, Identifier methodName, ArgumentList arguments, boolean eligibleForMain) {
		if(arguments.isEmpty() && eligibleForMain) {
			return Interpreter.interpretMainNoArgs(context, methodName);
		} else {
			MethodCall methodCall = new MethodCall(new MethodSelector(methodName), arguments);
			return methodCall.interpret(context);
		}
	}

	private void writeBinaryResponse(BinaryValue value, HttpServletResponse response) throws IOException {
		try(var output = response.getOutputStream()) {
			response.setContentType(value.getMimeType());
			response.setStatus(HttpServletResponse.SC_OK);
			output.write(value.getBytes());
		}
	}

	private void writeJsonResponse(Context context, IValue value, HttpServletResponse response) throws IOException, PromptoError {
		try(var output = response.getOutputStream()) {
			response.setContentType("text/json");
			response.setStatus(HttpServletResponse.SC_OK);
			try(var generator = new JsonFactory().createGenerator(output)) {
				generator.writeStartObject();
				generator.writeNullField("error");
				if(value==null)
					generator.writeNullField("data");
				else {
					generator.writeFieldName("data");
					value.toJsonStream(context, generator, true, null);
				}
				generator.writeEndObject();
				generator.flush();
			}
		}
	}
	
	private void writeJsonResponse(String[] lines, HttpServletResponse response) throws IOException, PromptoError {
		try(var output = response.getOutputStream()) {
			response.setContentType("text/json");
			response.setStatus(HttpServletResponse.SC_OK);
			try(var generator = new JsonFactory().createGenerator(output)) {
				generator.writeStartObject();
				generator.writeNullField("error");
				generator.writeArrayFieldStart("data");
				for(String line : lines)
					generator.writeString(line);
				generator.writeEndArray();
				generator.writeEndObject();
				generator.flush();
			}
		}
	}

	private void writeJsonErrorResponse(Context context, PromptoError error, HttpServletResponse response) throws IOException {
		try(var output = response.getOutputStream()) {
			response.setContentType("text/json");
			response.setStatus(HttpServletResponse.SC_OK);
			try(var generator = new JsonFactory().createGenerator(output)) {
				generator.writeStartObject();
				generator.writeStringField("error",getErrorMessage(context, error));
				generator.writeNullField("data");
				generator.writeEndObject();
				generator.flush();
			}
		}
	}

	private String getErrorMessage(Context context, PromptoError error) {
		String message = error.getMessage();
		if(message != null)
			return message;
		if(error instanceof ExecutionError) try {
			IExpression expression = ((ExecutionError)error).getExpression(context);
			IValue value = expression.interpret(context);
			if(value!=null)
				return value.toString();
		} catch(PromptoError e) {
			// bail
		}
		return "Internal error, please contact Prompto support";
	}


}
