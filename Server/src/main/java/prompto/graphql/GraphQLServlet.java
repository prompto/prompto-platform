package prompto.graphql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import prompto.code.ICodeStore;
import prompto.declaration.IDeclaration;
import prompto.server.CleverServlet;
import prompto.utils.Instance;
import prompto.utils.JsonUtils;
import prompto.utils.StreamUtils;

@SuppressWarnings("serial")
public class GraphQLServlet extends CleverServlet {

	static Instance<GraphQLServlet> instance = new Instance<>();
	
	public static boolean isEnabled() {
		Iterable<IDeclaration> decls = ICodeStore.getInstance().fetchDeclarationsWithAnnotations(new HashSet<>(Arrays.asList("@GraphQLQuery", "@GraphQLMutation")));
		return decls.iterator().hasNext();
	}

	public static void reset() {
		if(instance.get()!=null)
			instance.get().graphQL = null;
		GraphQLType.TYPE_BY_NAME_MAP.clear();
	}

	static class GraphQLRequest {
		String query;
		Map<String, Object> variables;
	}

	GraphQL graphQL;

	public GraphQLServlet() {
		instance.set(this);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			unsafeDoPost(request, response);
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	void unsafeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(var input = request.getInputStream()) {
			GraphQLRequest gql = readRequest(request, input);
			ExecutionResult result = executeRequest(gql);
			String json = JsonUtils.objectToJson(result.toSpecification());
			try(var writer = response.getWriter()) {
				response.setContentType("application/json");
				writer.print(json);
			}
		}
	}

	private ExecutionResult executeRequest(GraphQLRequest request) {
		ExecutionInput input = ExecutionInput.newExecutionInput()
			.query(request.query)
			.variables(request.variables)
			.build();
		GraphQL gql = getGraphQL();
		return gql.execute(input);
	}

	private GraphQL getGraphQL() {
		if(graphQL==null) synchronized(this) {
			GraphQLSchema schema = new GraphQLSchemaBuilder().build();
			graphQL = GraphQL.newGraphQL(schema).build();
		}
		return graphQL;
	}

	private GraphQLRequest readRequest(HttpServletRequest request, InputStream input) throws IOException, ServletException {
		String[] contentParts = request.getContentType().split(";");
		String contentType = contentParts[0];
		switch (contentType) {
		case "application/graphql":
			return readRequestFromGraphQL(input);
		case "application/json":
			return readRequestFromJSON(input);
		case "multipart/form-data":
			return readRequestFromMultipart(request);
		default:
			throw new UnsupportedOperationException("Unsupported content type: " + contentType);
		}
	}

	private GraphQLRequest readRequestFromMultipart(HttpServletRequest request) throws IOException, ServletException {
		Part part = request.getPart("operations");
		if (part == null)
			throw new UnsupportedOperationException("Missing 'operations' part");
		else {
			GraphQLRequest gql = readRequestFromPart(part);
			Map<String, Part> uploads = readRequestUploads(request);
			linkUploadVariables(gql.variables, uploads);
			return gql;
		}
	}

	private void linkUploadVariables(Map<String, Object> variables, Map<String, Part> uploads) {
		uploads.forEach((path, part)->linkUploadVariable(variables, path, part));
	}

	private void linkUploadVariable(Map<String, Object> variables, String path, Part part) {
		String[] parts =path.split("\\.");
		if(parts.length==1)
			variables.put(parts[0], part);
		else
			throw new UnsupportedOperationException("yet");
	}

	private Map<String, Part> readRequestUploads(HttpServletRequest request) throws IOException, ServletException {
		Part part = request.getPart("map");
		if (part == null)
			throw new UnsupportedOperationException("Missing 'map' part");
		else
			return readRequestUploads(request, part);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Part> readRequestUploads(HttpServletRequest request, Part part) throws IOException, ServletException {
		try (InputStream input = part.getInputStream()) {
			ObjectNode json = (ObjectNode) JsonUtils.parseInput(input);
			Map<String, List<String>> map = (Map<String, List<String>>) (Object) JsonUtils.toMap(json);
			Map<String, Part> uploads = new HashMap<>();
			for(Map.Entry<String, List<String>> entry : map.entrySet()) {
				Part file = request.getPart(entry.getKey());
				entry.getValue().forEach(s->uploads.put(s, file));
			}
			return uploads;
		}
	}

	private GraphQLRequest readRequestFromPart(Part part) throws IOException {
		try (InputStream input = part.getInputStream()) {
			String data = StreamUtils.readString(input);
			return readRequestFromJSON(data);
		}
	}

	private GraphQLRequest readRequestFromJSON(InputStream input) throws IOException {
		String query = StreamUtils.readString(input);
		return readRequestFromJSON(query);
	}

	private GraphQLRequest readRequestFromJSON(String query) throws IOException {
		JsonNode node = JsonUtils.parseString(query);
		return readRequestFromJSON(node);
	}

	private GraphQLRequest readRequestFromJSON(JsonNode json) {
		if (json.has("query") && json.get("query").isTextual()) {
			GraphQLRequest request = new GraphQLRequest();
			request.query = json.get("query").asText();
			if (json.has("variables") && json.get("variables").isObject()) {
				ObjectNode vars = (ObjectNode) json.get("variables");
				request.variables = JsonUtils.toMap(vars);
			} else
				request.variables = Collections.emptyMap();
			return request;
		} else
			throw new UnsupportedOperationException("Missing 'query' field: " + json.toString());
	}

	private GraphQLRequest readRequestFromGraphQL(InputStream input) throws IOException {
		GraphQLRequest request = new GraphQLRequest();
		request.query = StreamUtils.readString(input);
		request.variables = Collections.emptyMap();
		return request;
	}


}
