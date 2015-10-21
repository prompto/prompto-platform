package prompto.store;

import java.io.IOException;
import java.io.InputStream;

import prompto.declaration.DeclarationList;
import prompto.declaration.IDeclaration;
import prompto.parser.AbstractParser;
import prompto.parser.Dialect;
import prompto.parser.ECleverParser;
import prompto.parser.OCleverParser;
import prompto.parser.SCleverParser;
import prompto.utils.ISingleton;
import prompto.value.IValue;
import prompto.value.Text;

/* a code store is a place where a code consumer (interpreter, compiler...) can fetch code from */
public interface ICodeStore {
	
	static ISingleton<ICodeStore> instance = new ISingleton<ICodeStore>() {
		ICodeStore instance = null;
		@Override public void set(ICodeStore instance) { this.instance = instance; }
		@Override public ICodeStore get() { return instance; }
	};
	
	static void setInstance(ICodeStore store) {
		instance.set(store);
	}

	public static ICodeStore getInstance() {
		return instance.get();
	}
	
	public static DeclarationList parse(String sourceName, InputStream data) throws Exception {
		Dialect dialect = dialectFromResourceName(sourceName);
		return parse(dialect, sourceName, data);
	}
	
	public static DeclarationList parse(Dialect dialect, String sourceName, InputStream data) throws Exception {
		AbstractParser parser = parserForDialect(dialect, sourceName, data);
		return parser.parse_declaration_list();
	}

	static AbstractParser parserForDialect(Dialect dialect, String path, InputStream data) throws IOException {
		switch(dialect) {
		case E:
			return new ECleverParser(path, data);
		case O:
			return new OCleverParser(path, data);
		case S:
			return new SCleverParser(path, data);
		default:
			throw new RuntimeException("Unsupported extension: " + path);
		}
	}

	public static Dialect dialectFromResourceName(String sourceName) {
		String s = "" + sourceName.charAt(sourceName.length()-2);
		return Dialect.valueOf(s.toUpperCase());
	}

	static final String LATEST = "__latest__";
	
	static public enum ModuleType {
		APPLICATION,
		MODULE;
		
		public IValue asValue() {
			return new Text(this.name());
		}
	}
	
	IDeclaration fetchLatestVersion(String name);
	IDeclaration fetchSpecificVersion(String name, String version);
	ModuleType getModuleType();
	Dialect getModuleDialect();
	String getModuleName();
	String getModuleVersion();

}
