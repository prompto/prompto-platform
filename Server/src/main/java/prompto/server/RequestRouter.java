package prompto.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import prompto.compiler.PromptoClassLoader;
import prompto.error.PromptoError;
import prompto.expression.MethodSelector;
import prompto.grammar.ArgumentAssignmentList;
import prompto.grammar.Identifier;
import prompto.intrinsic.PromptoDict;
import prompto.memstore.MemStore;
import prompto.remoting.ParameterList;
import prompto.runtime.Context;
import prompto.runtime.Executor;
import prompto.runtime.Interpreter;
import prompto.statement.MethodCall;
import prompto.store.IDataStore;
import prompto.store.IStore;
import prompto.value.BinaryValue;
import prompto.value.IValue;
import prompto.value.Text;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class RequestRouter {

	PromptoClassLoader classLoader;
	Context context;
	
	public RequestRouter(PromptoClassLoader classLoader, Context context) {
		this.classLoader = classLoader;
		this.context = context.newLocalContext();
	}

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
	
	private void executeTest(Identifier testName, HttpServletResponse response) throws Exception {
		PrintStream oldOut = System.out;
		IStore oldStore = IDataStore.getInstance();
		IDataStore.setInstance(new MemStore());
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		try {
			Executor.executeTest(classLoader, testName.toString());
			bytes.flush();
			String[] lines = new String(bytes.toByteArray()).split("\n");
			writeJsonResponse(lines, response);
		} finally {
			IDataStore.setInstance(oldStore);
			System.setOut(oldOut);
		}
	}

	private void interpretTest(Identifier testName, HttpServletResponse response) throws IOException {
		PrintStream oldOut = System.out;
		IStore oldStore = IDataStore.getInstance();
		IDataStore.setInstance(new MemStore());
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bytes));
		try {
			Interpreter.interpretTest(context, testName, true);
			bytes.flush();
			String[] lines = new String(bytes.toByteArray()).split("\n");
			writeJsonResponse(lines, response);
		} finally {
			IDataStore.setInstance(oldStore);
			System.setOut(oldOut);
		}
	}

	public void executeMethod(Identifier methodName, String jsonParams, Map<String, byte[]> parts, boolean main, HttpServletResponse response) throws Exception {
		try {
			ParameterList params = ParameterList.read(context, jsonParams, parts);
			Class<?>[] argTypes = params.toJavaTypes(context, classLoader);
			Object[] args = params.toJavaValues(context);
			if(params.isEmpty() && main) {
				argTypes = new Class<?>[] { PromptoDict.class };
				args = new Object[] { null };
			}
			Object result = Executor.executeGlobalMethod(classLoader, methodName, argTypes, args);
			// TODO JSON output
			Text text = new Text(result==null ? "success!" : result.toString());
			writeJsonResponse(text, response);
		} finally {
			context.notifyTerminated();
		}
	}
	
	public void interpretMethod(Identifier methodName, String jsonParams, Map<String, byte[]> parts, boolean main, HttpServletResponse response) throws Exception {
		try {
			ParameterList params = ParameterList.read(context, jsonParams, parts);
			ArgumentAssignmentList assignments = params.toAssignments(context);
			IValue value = interpretMethod(context, methodName, assignments, main);
			if(value==null)
				value = new Text("Success!");
			if(value instanceof BinaryValue)
				writeBinaryResponse((BinaryValue)value, response);
			else
				writeJsonResponse(value, response);
		} finally {
			context.notifyTerminated();
		}
	}

	private IValue interpretMethod(Context context, Identifier methodName, ArgumentAssignmentList assignments, boolean main) {
		if(assignments.isEmpty() && main) {
			Interpreter.interpretMainNoArgs(context, methodName);
			return null;
		} else {
			MethodCall methodCall = new MethodCall(new MethodSelector(methodName), assignments);
			return methodCall.interpret(context);
		}
	}

	private void writeBinaryResponse(BinaryValue value, HttpServletResponse response) throws IOException {
		response.setContentType(value.getMimeType());
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().write(value.getBytes());
	}

	private void writeJsonResponse(IValue value, HttpServletResponse response) throws IOException, PromptoError {
		response.setContentType("text/json");
		response.setStatus(HttpServletResponse.SC_OK);
		JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
		generator.writeStartObject();
		generator.writeNullField("error");
		if(value==null)
			generator.writeNullField("data");
		else {
			generator.writeFieldName("data");
			value.toJson(context, generator, null, null, true, null);
		}
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}
	
	private void writeJsonResponse(String[] lines, HttpServletResponse response) throws IOException, PromptoError {
		response.setContentType("text/json");
		response.setStatus(HttpServletResponse.SC_OK);
		JsonGenerator generator = new JsonFactory().createGenerator(response.getOutputStream());
		generator.writeStartObject();
		generator.writeNullField("error");
		generator.writeArrayFieldStart("data");
		for(String line : lines)
			generator.writeString(line);
		generator.writeEndArray();
		generator.writeEndObject();
		generator.flush();
		generator.close();
	}

}
