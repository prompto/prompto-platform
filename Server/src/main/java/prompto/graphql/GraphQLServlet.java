package prompto.graphql;

import prompto.code.ICodeStore;
import prompto.declaration.IDeclaration;
import prompto.server.CleverServlet;

@SuppressWarnings("serial")
public class GraphQLServlet extends CleverServlet {

	public static boolean isEnabled() {
		// IDeclaration decl = ICodeStore.getInstance().fetchLatestDeclarationsWithAnnotations("@GraphQLQuery", "@GraphQLMutation");
		return false;
	}

}
