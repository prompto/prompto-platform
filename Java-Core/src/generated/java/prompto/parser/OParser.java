// Generated from OParser.g4 by ANTLR 4.5
package prompto.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class OParser extends AbstractParser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SPACE=1, WS=2, LF=3, COMMENT=4, JAVA=5, CSHARP=6, PYTHON2=7, PYTHON3=8, 
		JAVASCRIPT=9, SWIFT=10, COLON=11, SEMI=12, COMMA=13, RANGE=14, DOT=15, 
		LPAR=16, RPAR=17, LBRAK=18, RBRAK=19, LCURL=20, RCURL=21, QMARK=22, XMARK=23, 
		AMP=24, AMP2=25, PIPE=26, PIPE2=27, PLUS=28, MINUS=29, STAR=30, SLASH=31, 
		BSLASH=32, PERCENT=33, GT=34, GTE=35, LT=36, LTE=37, LTGT=38, EQ=39, XEQ=40, 
		EQ2=41, TEQ=42, TILDE=43, LARROW=44, RARROW=45, BOOLEAN=46, CHARACTER=47, 
		TEXT=48, INTEGER=49, DECIMAL=50, DATE=51, TIME=52, DATETIME=53, PERIOD=54, 
		METHOD_T=55, CODE=56, DOCUMENT=57, BLOB=58, IMAGE=59, UUID=60, ABSTRACT=61, 
		ALL=62, ALWAYS=63, AND=64, ANY=65, AS=66, ASC=67, ATTR=68, ATTRIBUTE=69, 
		ATTRIBUTES=70, BINDINGS=71, BY=72, CASE=73, CATCH=74, CATEGORY=75, CLASS=76, 
		CLOSE=77, CONTAINS=78, DEF=79, DEFAULT=80, DEFINE=81, DESC=82, DO=83, 
		DOING=84, EACH=85, ELSE=86, ENUM=87, ENUMERATED=88, EXCEPT=89, EXECUTE=90, 
		EXPECTING=91, EXTENDS=92, FETCH=93, FINALLY=94, FOR=95, FROM=96, GETTER=97, 
		IF=98, IN=99, INVOKE=100, IS=101, MATCHING=102, METHOD=103, METHODS=104, 
		MODULO=105, MUTABLE=106, NATIVE=107, NONE=108, NOT=109, NOTHING=110, NULL=111, 
		ON=112, ONE=113, OPEN=114, OPERATOR=115, OR=116, ORDER=117, OTHERWISE=118, 
		PASS=119, RAISE=120, READ=121, RECEIVING=122, RESOURCE=123, RETURN=124, 
		RETURNING=125, ROWS=126, SELF=127, SETTER=128, SINGLETON=129, SORTED=130, 
		STORABLE=131, STORE=132, SWITCH=133, TEST=134, THIS=135, THROW=136, TO=137, 
		TRY=138, VERIFYING=139, WITH=140, WHEN=141, WHERE=142, WHILE=143, WRITE=144, 
		BOOLEAN_LITERAL=145, CHAR_LITERAL=146, MIN_INTEGER=147, MAX_INTEGER=148, 
		SYMBOL_IDENTIFIER=149, TYPE_IDENTIFIER=150, VARIABLE_IDENTIFIER=151, NATIVE_IDENTIFIER=152, 
		DOLLAR_IDENTIFIER=153, TEXT_LITERAL=154, INTEGER_LITERAL=155, HEXA_LITERAL=156, 
		DECIMAL_LITERAL=157, DATETIME_LITERAL=158, TIME_LITERAL=159, DATE_LITERAL=160, 
		PERIOD_LITERAL=161;
	public static final int
		RULE_enum_category_declaration = 0, RULE_enum_native_declaration = 1, 
		RULE_category_symbol = 2, RULE_native_symbol = 3, RULE_attribute_declaration = 4, 
		RULE_concrete_category_declaration = 5, RULE_singleton_category_declaration = 6, 
		RULE_derived_list = 7, RULE_category_method_list = 8, RULE_operator_method_declaration = 9, 
		RULE_setter_method_declaration = 10, RULE_getter_method_declaration = 11, 
		RULE_native_resource_declaration = 12, RULE_native_category_declaration = 13, 
		RULE_native_category_bindings = 14, RULE_native_category_binding_list = 15, 
		RULE_attribute_list = 16, RULE_abstract_method_declaration = 17, RULE_concrete_method_declaration = 18, 
		RULE_native_method_declaration = 19, RULE_test_method_declaration = 20, 
		RULE_assertion = 21, RULE_typed_argument = 22, RULE_statement_or_list = 23, 
		RULE_statement = 24, RULE_store_statement = 25, RULE_with_resource_statement = 26, 
		RULE_with_singleton_statement = 27, RULE_switch_statement = 28, RULE_switch_case_statement = 29, 
		RULE_for_each_statement = 30, RULE_do_while_statement = 31, RULE_while_statement = 32, 
		RULE_if_statement = 33, RULE_else_if_statement_list = 34, RULE_raise_statement = 35, 
		RULE_try_statement = 36, RULE_catch_statement = 37, RULE_return_statement = 38, 
		RULE_method_call = 39, RULE_method_selector = 40, RULE_callable_parent = 41, 
		RULE_callable_selector = 42, RULE_expression = 43, RULE_an_expression = 44, 
		RULE_closure_expression = 45, RULE_instance_expression = 46, RULE_method_expression = 47, 
		RULE_document_expression = 48, RULE_read_expression = 49, RULE_write_statement = 50, 
		RULE_fetch_expression = 51, RULE_sorted_expression = 52, RULE_selector_expression = 53, 
		RULE_constructor_expression = 54, RULE_argument_assignment_list = 55, 
		RULE_argument_assignment = 56, RULE_assign_instance_statement = 57, RULE_child_instance = 58, 
		RULE_assign_tuple_statement = 59, RULE_null_literal = 60, RULE_declaration_list = 61, 
		RULE_declarations = 62, RULE_declaration = 63, RULE_resource_declaration = 64, 
		RULE_enum_declaration = 65, RULE_native_symbol_list = 66, RULE_category_symbol_list = 67, 
		RULE_symbol_list = 68, RULE_attribute_constraint = 69, RULE_list_literal = 70, 
		RULE_set_literal = 71, RULE_expression_list = 72, RULE_range_literal = 73, 
		RULE_typedef = 74, RULE_primary_type = 75, RULE_native_type = 76, RULE_category_type = 77, 
		RULE_code_type = 78, RULE_category_declaration = 79, RULE_type_identifier_list = 80, 
		RULE_method_identifier = 81, RULE_identifier = 82, RULE_variable_identifier = 83, 
		RULE_type_identifier = 84, RULE_symbol_identifier = 85, RULE_argument_list = 86, 
		RULE_argument = 87, RULE_operator_argument = 88, RULE_named_argument = 89, 
		RULE_code_argument = 90, RULE_category_or_any_type = 91, RULE_any_type = 92, 
		RULE_member_method_declaration_list = 93, RULE_member_method_declaration = 94, 
		RULE_native_member_method_declaration_list = 95, RULE_native_member_method_declaration = 96, 
		RULE_native_category_binding = 97, RULE_python_category_binding = 98, 
		RULE_python_module = 99, RULE_module_token = 100, RULE_javascript_category_binding = 101, 
		RULE_javascript_module = 102, RULE_variable_identifier_list = 103, RULE_method_declaration = 104, 
		RULE_comment_statement = 105, RULE_native_statement_list = 106, RULE_native_statement = 107, 
		RULE_python_native_statement = 108, RULE_javascript_native_statement = 109, 
		RULE_statement_list = 110, RULE_assertion_list = 111, RULE_switch_case_statement_list = 112, 
		RULE_catch_statement_list = 113, RULE_literal_collection = 114, RULE_atomic_literal = 115, 
		RULE_literal_list_literal = 116, RULE_selectable_expression = 117, RULE_this_expression = 118, 
		RULE_parenthesis_expression = 119, RULE_literal_expression = 120, RULE_collection_literal = 121, 
		RULE_tuple_literal = 122, RULE_dict_literal = 123, RULE_expression_tuple = 124, 
		RULE_dict_entry_list = 125, RULE_dict_entry = 126, RULE_slice_arguments = 127, 
		RULE_assign_variable_statement = 128, RULE_assignable_instance = 129, 
		RULE_is_expression = 130, RULE_order_by_list = 131, RULE_order_by = 132, 
		RULE_operator = 133, RULE_new_token = 134, RULE_key_token = 135, RULE_value_token = 136, 
		RULE_symbols_token = 137, RULE_assign = 138, RULE_multiply = 139, RULE_divide = 140, 
		RULE_idivide = 141, RULE_modulo = 142, RULE_lfs = 143, RULE_lfp = 144, 
		RULE_javascript_statement = 145, RULE_javascript_expression = 146, RULE_javascript_primary_expression = 147, 
		RULE_javascript_this_expression = 148, RULE_javascript_new_expression = 149, 
		RULE_javascript_selector_expression = 150, RULE_javascript_method_expression = 151, 
		RULE_javascript_arguments = 152, RULE_javascript_item_expression = 153, 
		RULE_javascript_parenthesis_expression = 154, RULE_javascript_identifier_expression = 155, 
		RULE_javascript_literal_expression = 156, RULE_javascript_identifier = 157, 
		RULE_python_statement = 158, RULE_python_expression = 159, RULE_python_primary_expression = 160, 
		RULE_python_selector_expression = 161, RULE_python_method_expression = 162, 
		RULE_python_argument_list = 163, RULE_python_ordinal_argument_list = 164, 
		RULE_python_named_argument_list = 165, RULE_python_parenthesis_expression = 166, 
		RULE_python_identifier_expression = 167, RULE_python_literal_expression = 168, 
		RULE_python_identifier = 169, RULE_java_statement = 170, RULE_java_expression = 171, 
		RULE_java_primary_expression = 172, RULE_java_this_expression = 173, RULE_java_new_expression = 174, 
		RULE_java_selector_expression = 175, RULE_java_method_expression = 176, 
		RULE_java_arguments = 177, RULE_java_item_expression = 178, RULE_java_parenthesis_expression = 179, 
		RULE_java_identifier_expression = 180, RULE_java_class_identifier_expression = 181, 
		RULE_java_literal_expression = 182, RULE_java_identifier = 183, RULE_csharp_statement = 184, 
		RULE_csharp_expression = 185, RULE_csharp_primary_expression = 186, RULE_csharp_this_expression = 187, 
		RULE_csharp_new_expression = 188, RULE_csharp_selector_expression = 189, 
		RULE_csharp_method_expression = 190, RULE_csharp_arguments = 191, RULE_csharp_item_expression = 192, 
		RULE_csharp_parenthesis_expression = 193, RULE_csharp_identifier_expression = 194, 
		RULE_csharp_literal_expression = 195, RULE_csharp_identifier = 196;
	public static final String[] ruleNames = {
		"enum_category_declaration", "enum_native_declaration", "category_symbol", 
		"native_symbol", "attribute_declaration", "concrete_category_declaration", 
		"singleton_category_declaration", "derived_list", "category_method_list", 
		"operator_method_declaration", "setter_method_declaration", "getter_method_declaration", 
		"native_resource_declaration", "native_category_declaration", "native_category_bindings", 
		"native_category_binding_list", "attribute_list", "abstract_method_declaration", 
		"concrete_method_declaration", "native_method_declaration", "test_method_declaration", 
		"assertion", "typed_argument", "statement_or_list", "statement", "store_statement", 
		"with_resource_statement", "with_singleton_statement", "switch_statement", 
		"switch_case_statement", "for_each_statement", "do_while_statement", "while_statement", 
		"if_statement", "else_if_statement_list", "raise_statement", "try_statement", 
		"catch_statement", "return_statement", "method_call", "method_selector", 
		"callable_parent", "callable_selector", "expression", "an_expression", 
		"closure_expression", "instance_expression", "method_expression", "document_expression", 
		"read_expression", "write_statement", "fetch_expression", "sorted_expression", 
		"selector_expression", "constructor_expression", "argument_assignment_list", 
		"argument_assignment", "assign_instance_statement", "child_instance", 
		"assign_tuple_statement", "null_literal", "declaration_list", "declarations", 
		"declaration", "resource_declaration", "enum_declaration", "native_symbol_list", 
		"category_symbol_list", "symbol_list", "attribute_constraint", "list_literal", 
		"set_literal", "expression_list", "range_literal", "typedef", "primary_type", 
		"native_type", "category_type", "code_type", "category_declaration", "type_identifier_list", 
		"method_identifier", "identifier", "variable_identifier", "type_identifier", 
		"symbol_identifier", "argument_list", "argument", "operator_argument", 
		"named_argument", "code_argument", "category_or_any_type", "any_type", 
		"member_method_declaration_list", "member_method_declaration", "native_member_method_declaration_list", 
		"native_member_method_declaration", "native_category_binding", "python_category_binding", 
		"python_module", "module_token", "javascript_category_binding", "javascript_module", 
		"variable_identifier_list", "method_declaration", "comment_statement", 
		"native_statement_list", "native_statement", "python_native_statement", 
		"javascript_native_statement", "statement_list", "assertion_list", "switch_case_statement_list", 
		"catch_statement_list", "literal_collection", "atomic_literal", "literal_list_literal", 
		"selectable_expression", "this_expression", "parenthesis_expression", 
		"literal_expression", "collection_literal", "tuple_literal", "dict_literal", 
		"expression_tuple", "dict_entry_list", "dict_entry", "slice_arguments", 
		"assign_variable_statement", "assignable_instance", "is_expression", "order_by_list", 
		"order_by", "operator", "new_token", "key_token", "value_token", "symbols_token", 
		"assign", "multiply", "divide", "idivide", "modulo", "lfs", "lfp", "javascript_statement", 
		"javascript_expression", "javascript_primary_expression", "javascript_this_expression", 
		"javascript_new_expression", "javascript_selector_expression", "javascript_method_expression", 
		"javascript_arguments", "javascript_item_expression", "javascript_parenthesis_expression", 
		"javascript_identifier_expression", "javascript_literal_expression", "javascript_identifier", 
		"python_statement", "python_expression", "python_primary_expression", 
		"python_selector_expression", "python_method_expression", "python_argument_list", 
		"python_ordinal_argument_list", "python_named_argument_list", "python_parenthesis_expression", 
		"python_identifier_expression", "python_literal_expression", "python_identifier", 
		"java_statement", "java_expression", "java_primary_expression", "java_this_expression", 
		"java_new_expression", "java_selector_expression", "java_method_expression", 
		"java_arguments", "java_item_expression", "java_parenthesis_expression", 
		"java_identifier_expression", "java_class_identifier_expression", "java_literal_expression", 
		"java_identifier", "csharp_statement", "csharp_expression", "csharp_primary_expression", 
		"csharp_this_expression", "csharp_new_expression", "csharp_selector_expression", 
		"csharp_method_expression", "csharp_arguments", "csharp_item_expression", 
		"csharp_parenthesis_expression", "csharp_identifier_expression", "csharp_literal_expression", 
		"csharp_identifier"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "' '", null, null, null, "'Java:'", "'C#:'", "'Python2:'", "'Python3:'", 
		"'JavaScript:'", "'Swift:'", "':'", "';'", "','", "'..'", "'.'", "'('", 
		"')'", "'['", "']'", "'{'", "'}'", "'?'", "'!'", "'&'", "'&&'", "'|'", 
		"'||'", "'+'", "'-'", "'*'", "'/'", "'\\'", "'%'", "'>'", "'>='", "'<'", 
		"'<='", "'<>'", "'='", "'!='", "'=='", "'~='", "'~'", "'<-'", "'->'", 
		"'Boolean'", "'Character'", "'Text'", "'Integer'", "'Decimal'", "'Date'", 
		"'Time'", "'DateTime'", "'Period'", "'Method'", "'Code'", "'Document'", 
		"'Blob'", "'Image'", "'UUID'", "'abstract'", "'all'", "'always'", "'and'", 
		"'any'", "'as'", null, "'attr'", "'attribute'", "'attributes'", "'bindings'", 
		"'by'", "'case'", "'catch'", "'category'", "'class'", "'close'", "'contains'", 
		"'def'", "'default'", "'define'", null, "'do'", "'doing'", "'each'", "'else'", 
		"'enum'", "'enumerated'", "'except'", "'execute'", "'expecting'", "'extends'", 
		"'fetch'", "'finally'", "'for'", "'from'", "'getter'", "'if'", "'in'", 
		"'invoke'", "'is'", "'matching'", "'method'", "'methods'", "'modulo'", 
		"'mutable'", "'native'", "'None'", "'not'", null, "'null'", "'on'", "'one'", 
		"'open'", "'operator'", "'or'", "'order'", "'otherwise'", "'pass'", "'raise'", 
		"'read'", "'receiving'", "'resource'", "'return'", "'returning'", "'rows'", 
		"'self'", "'setter'", "'singleton'", "'sorted'", "'storable'", "'store'", 
		"'switch'", "'test'", "'this'", "'throw'", "'to'", "'try'", "'verifying'", 
		"'with'", "'when'", "'where'", "'while'", "'write'", null, null, "'MIN_INTEGER'", 
		"'MAX_INTEGER'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SPACE", "WS", "LF", "COMMENT", "JAVA", "CSHARP", "PYTHON2", "PYTHON3", 
		"JAVASCRIPT", "SWIFT", "COLON", "SEMI", "COMMA", "RANGE", "DOT", "LPAR", 
		"RPAR", "LBRAK", "RBRAK", "LCURL", "RCURL", "QMARK", "XMARK", "AMP", "AMP2", 
		"PIPE", "PIPE2", "PLUS", "MINUS", "STAR", "SLASH", "BSLASH", "PERCENT", 
		"GT", "GTE", "LT", "LTE", "LTGT", "EQ", "XEQ", "EQ2", "TEQ", "TILDE", 
		"LARROW", "RARROW", "BOOLEAN", "CHARACTER", "TEXT", "INTEGER", "DECIMAL", 
		"DATE", "TIME", "DATETIME", "PERIOD", "METHOD_T", "CODE", "DOCUMENT", 
		"BLOB", "IMAGE", "UUID", "ABSTRACT", "ALL", "ALWAYS", "AND", "ANY", "AS", 
		"ASC", "ATTR", "ATTRIBUTE", "ATTRIBUTES", "BINDINGS", "BY", "CASE", "CATCH", 
		"CATEGORY", "CLASS", "CLOSE", "CONTAINS", "DEF", "DEFAULT", "DEFINE", 
		"DESC", "DO", "DOING", "EACH", "ELSE", "ENUM", "ENUMERATED", "EXCEPT", 
		"EXECUTE", "EXPECTING", "EXTENDS", "FETCH", "FINALLY", "FOR", "FROM", 
		"GETTER", "IF", "IN", "INVOKE", "IS", "MATCHING", "METHOD", "METHODS", 
		"MODULO", "MUTABLE", "NATIVE", "NONE", "NOT", "NOTHING", "NULL", "ON", 
		"ONE", "OPEN", "OPERATOR", "OR", "ORDER", "OTHERWISE", "PASS", "RAISE", 
		"READ", "RECEIVING", "RESOURCE", "RETURN", "RETURNING", "ROWS", "SELF", 
		"SETTER", "SINGLETON", "SORTED", "STORABLE", "STORE", "SWITCH", "TEST", 
		"THIS", "THROW", "TO", "TRY", "VERIFYING", "WITH", "WHEN", "WHERE", "WHILE", 
		"WRITE", "BOOLEAN_LITERAL", "CHAR_LITERAL", "MIN_INTEGER", "MAX_INTEGER", 
		"SYMBOL_IDENTIFIER", "TYPE_IDENTIFIER", "VARIABLE_IDENTIFIER", "NATIVE_IDENTIFIER", 
		"DOLLAR_IDENTIFIER", "TEXT_LITERAL", "INTEGER_LITERAL", "HEXA_LITERAL", 
		"DECIMAL_LITERAL", "DATETIME_LITERAL", "TIME_LITERAL", "DATE_LITERAL", 
		"PERIOD_LITERAL"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	@NotNull
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "OParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public OParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Enum_category_declarationContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Attribute_listContext attrs;
		public Type_identifierContext derived;
		public Category_symbol_listContext symbols;
		public TerminalNode ENUMERATED() { return getToken(OParser.ENUMERATED, 0); }
		public TerminalNode CATEGORY() { return getToken(OParser.CATEGORY, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public List<Type_identifierContext> type_identifier() {
			return getRuleContexts(Type_identifierContext.class);
		}
		public Type_identifierContext type_identifier(int i) {
			return getRuleContext(Type_identifierContext.class,i);
		}
		public Category_symbol_listContext category_symbol_list() {
			return getRuleContext(Category_symbol_listContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode EXTENDS() { return getToken(OParser.EXTENDS, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Enum_category_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_category_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterEnum_category_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitEnum_category_declaration(this);
		}
	}

	public final Enum_category_declarationContext enum_category_declaration() throws RecognitionException {
		Enum_category_declarationContext _localctx = new Enum_category_declarationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_enum_category_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394); 
			match(ENUMERATED);
			setState(395); 
			match(CATEGORY);
			setState(396); 
			((Enum_category_declarationContext)_localctx).name = type_identifier();
			setState(401);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(397); 
				match(LPAR);
				setState(398); 
				((Enum_category_declarationContext)_localctx).attrs = attribute_list(0);
				setState(399); 
				match(RPAR);
				}
			}

			setState(405);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(403); 
				match(EXTENDS);
				setState(404); 
				((Enum_category_declarationContext)_localctx).derived = type_identifier();
				}
			}

			setState(407); 
			match(LCURL);
			setState(408); 
			((Enum_category_declarationContext)_localctx).symbols = category_symbol_list(0);
			setState(409); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enum_native_declarationContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Native_typeContext typ;
		public Native_symbol_listContext symbols;
		public TerminalNode ENUMERATED() { return getToken(OParser.ENUMERATED, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Native_typeContext native_type() {
			return getRuleContext(Native_typeContext.class,0);
		}
		public Native_symbol_listContext native_symbol_list() {
			return getRuleContext(Native_symbol_listContext.class,0);
		}
		public Enum_native_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_native_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterEnum_native_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitEnum_native_declaration(this);
		}
	}

	public final Enum_native_declarationContext enum_native_declaration() throws RecognitionException {
		Enum_native_declarationContext _localctx = new Enum_native_declarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_enum_native_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(411); 
			match(ENUMERATED);
			setState(412); 
			((Enum_native_declarationContext)_localctx).name = type_identifier();
			setState(413); 
			match(LPAR);
			setState(414); 
			((Enum_native_declarationContext)_localctx).typ = native_type();
			setState(415); 
			match(RPAR);
			setState(416); 
			match(LCURL);
			setState(417); 
			((Enum_native_declarationContext)_localctx).symbols = native_symbol_list(0);
			setState(418); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Category_symbolContext extends ParserRuleContext {
		public Symbol_identifierContext name;
		public Argument_assignment_listContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public Argument_assignment_listContext argument_assignment_list() {
			return getRuleContext(Argument_assignment_listContext.class,0);
		}
		public Category_symbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategory_symbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategory_symbol(this);
		}
	}

	public final Category_symbolContext category_symbol() throws RecognitionException {
		Category_symbolContext _localctx = new Category_symbolContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_category_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(420); 
			((Category_symbolContext)_localctx).name = symbol_identifier();
			setState(421); 
			match(LPAR);
			setState(422); 
			((Category_symbolContext)_localctx).args = argument_assignment_list(0);
			setState(423); 
			match(RPAR);
			setState(424); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_symbolContext extends ParserRuleContext {
		public Symbol_identifierContext name;
		public ExpressionContext exp;
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Native_symbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNative_symbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNative_symbol(this);
		}
	}

	public final Native_symbolContext native_symbol() throws RecognitionException {
		Native_symbolContext _localctx = new Native_symbolContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_native_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426); 
			((Native_symbolContext)_localctx).name = symbol_identifier();
			setState(427); 
			match(EQ);
			setState(428); 
			((Native_symbolContext)_localctx).exp = expression(0);
			setState(429); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Attribute_declarationContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public TypedefContext typ;
		public Attribute_constraintContext match;
		public TerminalNode ATTRIBUTE() { return getToken(OParser.ATTRIBUTE, 0); }
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public TerminalNode STORABLE() { return getToken(OParser.STORABLE, 0); }
		public Attribute_constraintContext attribute_constraint() {
			return getRuleContext(Attribute_constraintContext.class,0);
		}
		public Attribute_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAttribute_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAttribute_declaration(this);
		}
	}

	public final Attribute_declarationContext attribute_declaration() throws RecognitionException {
		Attribute_declarationContext _localctx = new Attribute_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_attribute_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(432);
			_la = _input.LA(1);
			if (_la==STORABLE) {
				{
				setState(431); 
				match(STORABLE);
				}
			}

			setState(434); 
			match(ATTRIBUTE);
			setState(435); 
			((Attribute_declarationContext)_localctx).name = variable_identifier();
			setState(436); 
			match(COLON);
			setState(437); 
			((Attribute_declarationContext)_localctx).typ = typedef(0);
			setState(439);
			_la = _input.LA(1);
			if (_la==IN || _la==MATCHING) {
				{
				setState(438); 
				((Attribute_declarationContext)_localctx).match = attribute_constraint();
				}
			}

			setState(441); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Concrete_category_declarationContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Attribute_listContext attrs;
		public Derived_listContext derived;
		public Category_method_listContext methods;
		public TerminalNode CATEGORY() { return getToken(OParser.CATEGORY, 0); }
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Category_method_listContext category_method_list() {
			return getRuleContext(Category_method_listContext.class,0);
		}
		public TerminalNode STORABLE() { return getToken(OParser.STORABLE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode EXTENDS() { return getToken(OParser.EXTENDS, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Derived_listContext derived_list() {
			return getRuleContext(Derived_listContext.class,0);
		}
		public Concrete_category_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_concrete_category_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterConcrete_category_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitConcrete_category_declaration(this);
		}
	}

	public final Concrete_category_declarationContext concrete_category_declaration() throws RecognitionException {
		Concrete_category_declarationContext _localctx = new Concrete_category_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_concrete_category_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(444);
			_la = _input.LA(1);
			if (_la==STORABLE) {
				{
				setState(443); 
				match(STORABLE);
				}
			}

			setState(446); 
			match(CATEGORY);
			setState(447); 
			((Concrete_category_declarationContext)_localctx).name = type_identifier();
			setState(452);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(448); 
				match(LPAR);
				setState(449); 
				((Concrete_category_declarationContext)_localctx).attrs = attribute_list(0);
				setState(450); 
				match(RPAR);
				}
			}

			setState(456);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(454); 
				match(EXTENDS);
				setState(455); 
				((Concrete_category_declarationContext)_localctx).derived = derived_list(0);
				}
			}

			setState(458); 
			((Concrete_category_declarationContext)_localctx).methods = category_method_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Singleton_category_declarationContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Attribute_listContext attrs;
		public Category_method_listContext methods;
		public TerminalNode SINGLETON() { return getToken(OParser.SINGLETON, 0); }
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Category_method_listContext category_method_list() {
			return getRuleContext(Category_method_listContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Singleton_category_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singleton_category_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSingleton_category_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSingleton_category_declaration(this);
		}
	}

	public final Singleton_category_declarationContext singleton_category_declaration() throws RecognitionException {
		Singleton_category_declarationContext _localctx = new Singleton_category_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_singleton_category_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(460); 
			match(SINGLETON);
			setState(461); 
			((Singleton_category_declarationContext)_localctx).name = type_identifier();
			setState(466);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(462); 
				match(LPAR);
				setState(463); 
				((Singleton_category_declarationContext)_localctx).attrs = attribute_list(0);
				setState(464); 
				match(RPAR);
				}
			}

			setState(468); 
			((Singleton_category_declarationContext)_localctx).methods = category_method_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Derived_listContext extends ParserRuleContext {
		public Derived_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_derived_list; }
	 
		public Derived_listContext() { }
		public void copyFrom(Derived_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DerivedListItemContext extends Derived_listContext {
		public Derived_listContext items;
		public Type_identifierContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Derived_listContext derived_list() {
			return getRuleContext(Derived_listContext.class,0);
		}
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public DerivedListItemContext(Derived_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDerivedListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDerivedListItem(this);
		}
	}
	public static class DerivedListContext extends Derived_listContext {
		public Type_identifierContext item;
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public DerivedListContext(Derived_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDerivedList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDerivedList(this);
		}
	}

	public final Derived_listContext derived_list() throws RecognitionException {
		return derived_list(0);
	}

	private Derived_listContext derived_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Derived_listContext _localctx = new Derived_listContext(_ctx, _parentState);
		Derived_listContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_derived_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new DerivedListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(471); 
			((DerivedListContext)_localctx).item = type_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(478);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DerivedListItemContext(new Derived_listContext(_parentctx, _parentState));
					((DerivedListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_derived_list);
					setState(473);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(474); 
					match(COMMA);
					setState(475); 
					((DerivedListItemContext)_localctx).item = type_identifier();
					}
					} 
				}
				setState(480);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Category_method_listContext extends ParserRuleContext {
		public Category_method_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category_method_list; }
	 
		public Category_method_listContext() { }
		public void copyFrom(Category_method_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EmptyCategoryMethodListContext extends Category_method_listContext {
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public EmptyCategoryMethodListContext(Category_method_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterEmptyCategoryMethodList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitEmptyCategoryMethodList(this);
		}
	}
	public static class CurlyCategoryMethodListContext extends Category_method_listContext {
		public Member_method_declaration_listContext items;
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Member_method_declaration_listContext member_method_declaration_list() {
			return getRuleContext(Member_method_declaration_listContext.class,0);
		}
		public CurlyCategoryMethodListContext(Category_method_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCurlyCategoryMethodList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCurlyCategoryMethodList(this);
		}
	}

	public final Category_method_listContext category_method_list() throws RecognitionException {
		Category_method_listContext _localctx = new Category_method_listContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_category_method_list);
		int _la;
		try {
			setState(487);
			switch (_input.LA(1)) {
			case SEMI:
				_localctx = new EmptyCategoryMethodListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(481); 
				match(SEMI);
				}
				break;
			case LCURL:
				_localctx = new CurlyCategoryMethodListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(482); 
				match(LCURL);
				setState(484);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID) | (1L << ABSTRACT))) != 0) || ((((_la - 97)) & ~0x3f) == 0 && ((1L << (_la - 97)) & ((1L << (GETTER - 97)) | (1L << (METHOD - 97)) | (1L << (OPERATOR - 97)) | (1L << (SETTER - 97)) | (1L << (TYPE_IDENTIFIER - 97)))) != 0)) {
					{
					setState(483); 
					((CurlyCategoryMethodListContext)_localctx).items = member_method_declaration_list(0);
					}
				}

				setState(486); 
				match(RCURL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Operator_method_declarationContext extends ParserRuleContext {
		public TypedefContext typ;
		public OperatorContext op;
		public Operator_argumentContext arg;
		public Statement_listContext stmts;
		public TerminalNode OPERATOR() { return getToken(OParser.OPERATOR, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public OperatorContext operator() {
			return getRuleContext(OperatorContext.class,0);
		}
		public Operator_argumentContext operator_argument() {
			return getRuleContext(Operator_argumentContext.class,0);
		}
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Operator_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperator_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperator_method_declaration(this);
		}
	}

	public final Operator_method_declarationContext operator_method_declaration() throws RecognitionException {
		Operator_method_declarationContext _localctx = new Operator_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_operator_method_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || _la==TYPE_IDENTIFIER) {
				{
				setState(489); 
				((Operator_method_declarationContext)_localctx).typ = typedef(0);
				}
			}

			setState(492); 
			match(OPERATOR);
			setState(493); 
			((Operator_method_declarationContext)_localctx).op = operator();
			setState(494); 
			match(LPAR);
			setState(495); 
			((Operator_method_declarationContext)_localctx).arg = operator_argument();
			setState(496); 
			match(RPAR);
			setState(497); 
			match(LCURL);
			setState(499);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
				{
				setState(498); 
				((Operator_method_declarationContext)_localctx).stmts = statement_list(0);
				}
			}

			setState(501); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Setter_method_declarationContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public Statement_listContext stmts;
		public TerminalNode SETTER() { return getToken(OParser.SETTER, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Setter_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setter_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSetter_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSetter_method_declaration(this);
		}
	}

	public final Setter_method_declarationContext setter_method_declaration() throws RecognitionException {
		Setter_method_declarationContext _localctx = new Setter_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_setter_method_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(503); 
			match(SETTER);
			setState(504); 
			((Setter_method_declarationContext)_localctx).name = variable_identifier();
			setState(505); 
			match(LCURL);
			setState(507);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
				{
				setState(506); 
				((Setter_method_declarationContext)_localctx).stmts = statement_list(0);
				}
			}

			setState(509); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Getter_method_declarationContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public Statement_listContext stmts;
		public TerminalNode GETTER() { return getToken(OParser.GETTER, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Getter_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getter_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterGetter_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitGetter_method_declaration(this);
		}
	}

	public final Getter_method_declarationContext getter_method_declaration() throws RecognitionException {
		Getter_method_declarationContext _localctx = new Getter_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_getter_method_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(511); 
			match(GETTER);
			setState(512); 
			((Getter_method_declarationContext)_localctx).name = variable_identifier();
			setState(513); 
			match(LCURL);
			setState(515);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
				{
				setState(514); 
				((Getter_method_declarationContext)_localctx).stmts = statement_list(0);
				}
			}

			setState(517); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_resource_declarationContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Attribute_listContext attrs;
		public Native_category_bindingsContext bindings;
		public Native_member_method_declaration_listContext methods;
		public TerminalNode NATIVE() { return getToken(OParser.NATIVE, 0); }
		public TerminalNode RESOURCE() { return getToken(OParser.RESOURCE, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Native_category_bindingsContext native_category_bindings() {
			return getRuleContext(Native_category_bindingsContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Native_member_method_declaration_listContext native_member_method_declaration_list() {
			return getRuleContext(Native_member_method_declaration_listContext.class,0);
		}
		public Native_resource_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_resource_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNative_resource_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNative_resource_declaration(this);
		}
	}

	public final Native_resource_declarationContext native_resource_declaration() throws RecognitionException {
		Native_resource_declarationContext _localctx = new Native_resource_declarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_native_resource_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(519); 
			match(NATIVE);
			setState(520); 
			match(RESOURCE);
			setState(521); 
			((Native_resource_declarationContext)_localctx).name = type_identifier();
			setState(526);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(522); 
				match(LPAR);
				setState(523); 
				((Native_resource_declarationContext)_localctx).attrs = attribute_list(0);
				setState(524); 
				match(RPAR);
				}
			}

			setState(528); 
			match(LCURL);
			setState(529); 
			((Native_resource_declarationContext)_localctx).bindings = native_category_bindings();
			setState(531);
			_la = _input.LA(1);
			if (((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (BOOLEAN - 46)) | (1L << (CHARACTER - 46)) | (1L << (TEXT - 46)) | (1L << (INTEGER - 46)) | (1L << (DECIMAL - 46)) | (1L << (DATE - 46)) | (1L << (TIME - 46)) | (1L << (DATETIME - 46)) | (1L << (PERIOD - 46)) | (1L << (CODE - 46)) | (1L << (DOCUMENT - 46)) | (1L << (BLOB - 46)) | (1L << (IMAGE - 46)) | (1L << (UUID - 46)) | (1L << (ABSTRACT - 46)) | (1L << (ANY - 46)) | (1L << (GETTER - 46)) | (1L << (METHOD - 46)) | (1L << (NATIVE - 46)))) != 0) || ((((_la - 115)) & ~0x3f) == 0 && ((1L << (_la - 115)) & ((1L << (OPERATOR - 115)) | (1L << (SETTER - 115)) | (1L << (TYPE_IDENTIFIER - 115)))) != 0)) {
				{
				setState(530); 
				((Native_resource_declarationContext)_localctx).methods = native_member_method_declaration_list(0);
				}
			}

			setState(533); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_category_declarationContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Attribute_listContext attrs;
		public Native_category_bindingsContext bindings;
		public Native_member_method_declaration_listContext methods;
		public TerminalNode NATIVE() { return getToken(OParser.NATIVE, 0); }
		public TerminalNode CATEGORY() { return getToken(OParser.CATEGORY, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Native_category_bindingsContext native_category_bindings() {
			return getRuleContext(Native_category_bindingsContext.class,0);
		}
		public TerminalNode STORABLE() { return getToken(OParser.STORABLE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Native_member_method_declaration_listContext native_member_method_declaration_list() {
			return getRuleContext(Native_member_method_declaration_listContext.class,0);
		}
		public Native_category_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_category_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNative_category_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNative_category_declaration(this);
		}
	}

	public final Native_category_declarationContext native_category_declaration() throws RecognitionException {
		Native_category_declarationContext _localctx = new Native_category_declarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_native_category_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(536);
			_la = _input.LA(1);
			if (_la==STORABLE) {
				{
				setState(535); 
				match(STORABLE);
				}
			}

			setState(538); 
			match(NATIVE);
			setState(539); 
			match(CATEGORY);
			setState(540); 
			((Native_category_declarationContext)_localctx).name = type_identifier();
			setState(545);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(541); 
				match(LPAR);
				setState(542); 
				((Native_category_declarationContext)_localctx).attrs = attribute_list(0);
				setState(543); 
				match(RPAR);
				}
			}

			setState(547); 
			match(LCURL);
			setState(548); 
			((Native_category_declarationContext)_localctx).bindings = native_category_bindings();
			setState(550);
			_la = _input.LA(1);
			if (((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (BOOLEAN - 46)) | (1L << (CHARACTER - 46)) | (1L << (TEXT - 46)) | (1L << (INTEGER - 46)) | (1L << (DECIMAL - 46)) | (1L << (DATE - 46)) | (1L << (TIME - 46)) | (1L << (DATETIME - 46)) | (1L << (PERIOD - 46)) | (1L << (CODE - 46)) | (1L << (DOCUMENT - 46)) | (1L << (BLOB - 46)) | (1L << (IMAGE - 46)) | (1L << (UUID - 46)) | (1L << (ABSTRACT - 46)) | (1L << (ANY - 46)) | (1L << (GETTER - 46)) | (1L << (METHOD - 46)) | (1L << (NATIVE - 46)))) != 0) || ((((_la - 115)) & ~0x3f) == 0 && ((1L << (_la - 115)) & ((1L << (OPERATOR - 115)) | (1L << (SETTER - 115)) | (1L << (TYPE_IDENTIFIER - 115)))) != 0)) {
				{
				setState(549); 
				((Native_category_declarationContext)_localctx).methods = native_member_method_declaration_list(0);
				}
			}

			setState(552); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_category_bindingsContext extends ParserRuleContext {
		public Native_category_binding_listContext items;
		public TerminalNode CATEGORY() { return getToken(OParser.CATEGORY, 0); }
		public TerminalNode BINDINGS() { return getToken(OParser.BINDINGS, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Native_category_binding_listContext native_category_binding_list() {
			return getRuleContext(Native_category_binding_listContext.class,0);
		}
		public Native_category_bindingsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_category_bindings; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNative_category_bindings(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNative_category_bindings(this);
		}
	}

	public final Native_category_bindingsContext native_category_bindings() throws RecognitionException {
		Native_category_bindingsContext _localctx = new Native_category_bindingsContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_native_category_bindings);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(554); 
			match(CATEGORY);
			setState(555); 
			match(BINDINGS);
			setState(556); 
			match(LCURL);
			setState(557); 
			((Native_category_bindingsContext)_localctx).items = native_category_binding_list(0);
			setState(558); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_category_binding_listContext extends ParserRuleContext {
		public Native_category_binding_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_category_binding_list; }
	 
		public Native_category_binding_listContext() { }
		public void copyFrom(Native_category_binding_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeCategoryBindingListItemContext extends Native_category_binding_listContext {
		public Native_category_binding_listContext items;
		public Native_category_bindingContext item;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Native_category_binding_listContext native_category_binding_list() {
			return getRuleContext(Native_category_binding_listContext.class,0);
		}
		public Native_category_bindingContext native_category_binding() {
			return getRuleContext(Native_category_bindingContext.class,0);
		}
		public NativeCategoryBindingListItemContext(Native_category_binding_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeCategoryBindingListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeCategoryBindingListItem(this);
		}
	}
	public static class NativeCategoryBindingListContext extends Native_category_binding_listContext {
		public Native_category_bindingContext item;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Native_category_bindingContext native_category_binding() {
			return getRuleContext(Native_category_bindingContext.class,0);
		}
		public NativeCategoryBindingListContext(Native_category_binding_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeCategoryBindingList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeCategoryBindingList(this);
		}
	}

	public final Native_category_binding_listContext native_category_binding_list() throws RecognitionException {
		return native_category_binding_list(0);
	}

	private Native_category_binding_listContext native_category_binding_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Native_category_binding_listContext _localctx = new Native_category_binding_listContext(_ctx, _parentState);
		Native_category_binding_listContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_native_category_binding_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new NativeCategoryBindingListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(561); 
			((NativeCategoryBindingListContext)_localctx).item = native_category_binding();
			setState(562); 
			match(SEMI);
			}
			_ctx.stop = _input.LT(-1);
			setState(570);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new NativeCategoryBindingListItemContext(new Native_category_binding_listContext(_parentctx, _parentState));
					((NativeCategoryBindingListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_native_category_binding_list);
					setState(564);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(565); 
					((NativeCategoryBindingListItemContext)_localctx).item = native_category_binding();
					setState(566); 
					match(SEMI);
					}
					} 
				}
				setState(572);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Attribute_listContext extends ParserRuleContext {
		public Attribute_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_list; }
	 
		public Attribute_listContext() { }
		public void copyFrom(Attribute_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AttributeListContext extends Attribute_listContext {
		public Variable_identifierContext item;
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public AttributeListContext(Attribute_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAttributeList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAttributeList(this);
		}
	}
	public static class AttributeListItemContext extends Attribute_listContext {
		public Attribute_listContext items;
		public Variable_identifierContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public AttributeListItemContext(Attribute_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAttributeListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAttributeListItem(this);
		}
	}

	public final Attribute_listContext attribute_list() throws RecognitionException {
		return attribute_list(0);
	}

	private Attribute_listContext attribute_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Attribute_listContext _localctx = new Attribute_listContext(_ctx, _parentState);
		Attribute_listContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_attribute_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AttributeListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(574); 
			((AttributeListContext)_localctx).item = variable_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(581);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AttributeListItemContext(new Attribute_listContext(_parentctx, _parentState));
					((AttributeListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_attribute_list);
					setState(576);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(577); 
					match(COMMA);
					setState(578); 
					((AttributeListItemContext)_localctx).item = variable_identifier();
					}
					} 
				}
				setState(583);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Abstract_method_declarationContext extends ParserRuleContext {
		public TypedefContext typ;
		public Method_identifierContext name;
		public Argument_listContext args;
		public TerminalNode ABSTRACT() { return getToken(OParser.ABSTRACT, 0); }
		public TerminalNode METHOD() { return getToken(OParser.METHOD, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Method_identifierContext method_identifier() {
			return getRuleContext(Method_identifierContext.class,0);
		}
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public Abstract_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abstract_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAbstract_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAbstract_method_declaration(this);
		}
	}

	public final Abstract_method_declarationContext abstract_method_declaration() throws RecognitionException {
		Abstract_method_declarationContext _localctx = new Abstract_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_abstract_method_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(584); 
			match(ABSTRACT);
			setState(586);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || _la==TYPE_IDENTIFIER) {
				{
				setState(585); 
				((Abstract_method_declarationContext)_localctx).typ = typedef(0);
				}
			}

			setState(588); 
			match(METHOD);
			setState(589); 
			((Abstract_method_declarationContext)_localctx).name = method_identifier();
			setState(590); 
			match(LPAR);
			setState(592);
			_la = _input.LA(1);
			if (((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (BOOLEAN - 46)) | (1L << (CHARACTER - 46)) | (1L << (TEXT - 46)) | (1L << (INTEGER - 46)) | (1L << (DECIMAL - 46)) | (1L << (DATE - 46)) | (1L << (TIME - 46)) | (1L << (DATETIME - 46)) | (1L << (PERIOD - 46)) | (1L << (CODE - 46)) | (1L << (DOCUMENT - 46)) | (1L << (BLOB - 46)) | (1L << (IMAGE - 46)) | (1L << (UUID - 46)) | (1L << (ANY - 46)) | (1L << (MUTABLE - 46)))) != 0) || _la==TYPE_IDENTIFIER || _la==VARIABLE_IDENTIFIER) {
				{
				setState(591); 
				((Abstract_method_declarationContext)_localctx).args = argument_list(0);
				}
			}

			setState(594); 
			match(RPAR);
			setState(595); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Concrete_method_declarationContext extends ParserRuleContext {
		public TypedefContext typ;
		public Method_identifierContext name;
		public Argument_listContext args;
		public Statement_listContext stmts;
		public TerminalNode METHOD() { return getToken(OParser.METHOD, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Method_identifierContext method_identifier() {
			return getRuleContext(Method_identifierContext.class,0);
		}
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Concrete_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_concrete_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterConcrete_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitConcrete_method_declaration(this);
		}
	}

	public final Concrete_method_declarationContext concrete_method_declaration() throws RecognitionException {
		Concrete_method_declarationContext _localctx = new Concrete_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_concrete_method_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(598);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || _la==TYPE_IDENTIFIER) {
				{
				setState(597); 
				((Concrete_method_declarationContext)_localctx).typ = typedef(0);
				}
			}

			setState(600); 
			match(METHOD);
			setState(601); 
			((Concrete_method_declarationContext)_localctx).name = method_identifier();
			setState(602); 
			match(LPAR);
			setState(604);
			_la = _input.LA(1);
			if (((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (BOOLEAN - 46)) | (1L << (CHARACTER - 46)) | (1L << (TEXT - 46)) | (1L << (INTEGER - 46)) | (1L << (DECIMAL - 46)) | (1L << (DATE - 46)) | (1L << (TIME - 46)) | (1L << (DATETIME - 46)) | (1L << (PERIOD - 46)) | (1L << (CODE - 46)) | (1L << (DOCUMENT - 46)) | (1L << (BLOB - 46)) | (1L << (IMAGE - 46)) | (1L << (UUID - 46)) | (1L << (ANY - 46)) | (1L << (MUTABLE - 46)))) != 0) || _la==TYPE_IDENTIFIER || _la==VARIABLE_IDENTIFIER) {
				{
				setState(603); 
				((Concrete_method_declarationContext)_localctx).args = argument_list(0);
				}
			}

			setState(606); 
			match(RPAR);
			setState(607); 
			match(LCURL);
			setState(609);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
				{
				setState(608); 
				((Concrete_method_declarationContext)_localctx).stmts = statement_list(0);
				}
			}

			setState(611); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_method_declarationContext extends ParserRuleContext {
		public Category_or_any_typeContext typ;
		public Method_identifierContext name;
		public Argument_listContext args;
		public Native_statement_listContext stmts;
		public TerminalNode NATIVE() { return getToken(OParser.NATIVE, 0); }
		public TerminalNode METHOD() { return getToken(OParser.METHOD, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Method_identifierContext method_identifier() {
			return getRuleContext(Method_identifierContext.class,0);
		}
		public Native_statement_listContext native_statement_list() {
			return getRuleContext(Native_statement_listContext.class,0);
		}
		public Category_or_any_typeContext category_or_any_type() {
			return getRuleContext(Category_or_any_typeContext.class,0);
		}
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public Native_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNative_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNative_method_declaration(this);
		}
	}

	public final Native_method_declarationContext native_method_declaration() throws RecognitionException {
		Native_method_declarationContext _localctx = new Native_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_native_method_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(614);
			_la = _input.LA(1);
			if (((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (BOOLEAN - 46)) | (1L << (CHARACTER - 46)) | (1L << (TEXT - 46)) | (1L << (INTEGER - 46)) | (1L << (DECIMAL - 46)) | (1L << (DATE - 46)) | (1L << (TIME - 46)) | (1L << (DATETIME - 46)) | (1L << (PERIOD - 46)) | (1L << (CODE - 46)) | (1L << (DOCUMENT - 46)) | (1L << (BLOB - 46)) | (1L << (IMAGE - 46)) | (1L << (UUID - 46)) | (1L << (ANY - 46)))) != 0) || _la==TYPE_IDENTIFIER) {
				{
				setState(613); 
				((Native_method_declarationContext)_localctx).typ = category_or_any_type();
				}
			}

			setState(616); 
			match(NATIVE);
			setState(617); 
			match(METHOD);
			setState(618); 
			((Native_method_declarationContext)_localctx).name = method_identifier();
			setState(619); 
			match(LPAR);
			setState(621);
			_la = _input.LA(1);
			if (((((_la - 46)) & ~0x3f) == 0 && ((1L << (_la - 46)) & ((1L << (BOOLEAN - 46)) | (1L << (CHARACTER - 46)) | (1L << (TEXT - 46)) | (1L << (INTEGER - 46)) | (1L << (DECIMAL - 46)) | (1L << (DATE - 46)) | (1L << (TIME - 46)) | (1L << (DATETIME - 46)) | (1L << (PERIOD - 46)) | (1L << (CODE - 46)) | (1L << (DOCUMENT - 46)) | (1L << (BLOB - 46)) | (1L << (IMAGE - 46)) | (1L << (UUID - 46)) | (1L << (ANY - 46)) | (1L << (MUTABLE - 46)))) != 0) || _la==TYPE_IDENTIFIER || _la==VARIABLE_IDENTIFIER) {
				{
				setState(620); 
				((Native_method_declarationContext)_localctx).args = argument_list(0);
				}
			}

			setState(623); 
			match(RPAR);
			setState(624); 
			match(LCURL);
			setState(625); 
			((Native_method_declarationContext)_localctx).stmts = native_statement_list(0);
			setState(626); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Test_method_declarationContext extends ParserRuleContext {
		public Token name;
		public Statement_listContext stmts;
		public Assertion_listContext exps;
		public Symbol_identifierContext error;
		public TerminalNode TEST() { return getToken(OParser.TEST, 0); }
		public TerminalNode METHOD() { return getToken(OParser.METHOD, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public List<TerminalNode> LCURL() { return getTokens(OParser.LCURL); }
		public TerminalNode LCURL(int i) {
			return getToken(OParser.LCURL, i);
		}
		public List<TerminalNode> RCURL() { return getTokens(OParser.RCURL); }
		public TerminalNode RCURL(int i) {
			return getToken(OParser.RCURL, i);
		}
		public TerminalNode VERIFYING() { return getToken(OParser.VERIFYING, 0); }
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Assertion_listContext assertion_list() {
			return getRuleContext(Assertion_listContext.class,0);
		}
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public Test_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTest_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTest_method_declaration(this);
		}
	}

	public final Test_method_declarationContext test_method_declaration() throws RecognitionException {
		Test_method_declarationContext _localctx = new Test_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_test_method_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(628); 
			match(TEST);
			setState(629); 
			match(METHOD);
			setState(630); 
			((Test_method_declarationContext)_localctx).name = match(TEXT_LITERAL);
			setState(631); 
			match(LPAR);
			setState(632); 
			match(RPAR);
			setState(633); 
			match(LCURL);
			setState(634); 
			((Test_method_declarationContext)_localctx).stmts = statement_list(0);
			setState(635); 
			match(RCURL);
			setState(636); 
			match(VERIFYING);
			setState(644);
			switch (_input.LA(1)) {
			case LCURL:
				{
				{
				setState(637); 
				match(LCURL);
				setState(638); 
				((Test_method_declarationContext)_localctx).exps = assertion_list(0);
				setState(639); 
				match(RCURL);
				}
				}
				break;
			case SYMBOL_IDENTIFIER:
				{
				{
				setState(641); 
				((Test_method_declarationContext)_localctx).error = symbol_identifier();
				setState(642); 
				match(SEMI);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssertionContext extends ParserRuleContext {
		public ExpressionContext exp;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssertionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssertion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssertion(this);
		}
	}

	public final AssertionContext assertion() throws RecognitionException {
		AssertionContext _localctx = new AssertionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_assertion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(646); 
			((AssertionContext)_localctx).exp = expression(0);
			setState(647); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Typed_argumentContext extends ParserRuleContext {
		public Category_or_any_typeContext typ;
		public Attribute_listContext attrs;
		public Variable_identifierContext name;
		public Literal_expressionContext value;
		public Category_or_any_typeContext category_or_any_type() {
			return getRuleContext(Category_or_any_typeContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public Attribute_listContext attribute_list() {
			return getRuleContext(Attribute_listContext.class,0);
		}
		public Literal_expressionContext literal_expression() {
			return getRuleContext(Literal_expressionContext.class,0);
		}
		public Typed_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typed_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTyped_argument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTyped_argument(this);
		}
	}

	public final Typed_argumentContext typed_argument() throws RecognitionException {
		Typed_argumentContext _localctx = new Typed_argumentContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_typed_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(649); 
			((Typed_argumentContext)_localctx).typ = category_or_any_type();
			setState(654);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(650); 
				match(LPAR);
				setState(651); 
				((Typed_argumentContext)_localctx).attrs = attribute_list(0);
				setState(652); 
				match(RPAR);
				}
			}

			setState(656); 
			((Typed_argumentContext)_localctx).name = variable_identifier();
			setState(659);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(657); 
				match(EQ);
				setState(658); 
				((Typed_argumentContext)_localctx).value = literal_expression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Statement_or_listContext extends ParserRuleContext {
		public Statement_or_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_or_list; }
	 
		public Statement_or_listContext() { }
		public void copyFrom(Statement_or_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CurlyStatementListContext extends Statement_or_listContext {
		public Statement_listContext items;
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public CurlyStatementListContext(Statement_or_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCurlyStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCurlyStatementList(this);
		}
	}
	public static class SingleStatementContext extends Statement_or_listContext {
		public StatementContext stmt;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public SingleStatementContext(Statement_or_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSingleStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSingleStatement(this);
		}
	}

	public final Statement_or_listContext statement_or_list() throws RecognitionException {
		Statement_or_listContext _localctx = new Statement_or_listContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_statement_or_list);
		try {
			setState(668);
			switch (_input.LA(1)) {
			case COMMENT:
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case CODE:
			case DOCUMENT:
			case BLOB:
			case IMAGE:
			case UUID:
			case DO:
			case FOR:
			case IF:
			case METHOD:
			case RETURN:
			case STORE:
			case SWITCH:
			case THROW:
			case TRY:
			case WITH:
			case WHILE:
			case WRITE:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
				_localctx = new SingleStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(661); 
				((SingleStatementContext)_localctx).stmt = statement();
				}
				break;
			case LCURL:
				_localctx = new CurlyStatementListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(662); 
				match(LCURL);
				setState(666);
				switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
				case 1:
					{
					setState(663); 
					((CurlyStatementListContext)_localctx).items = statement_list(0);
					setState(664); 
					match(RCURL);
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CommentStatementContext extends StatementContext {
		public Comment_statementContext decl;
		public Comment_statementContext comment_statement() {
			return getRuleContext(Comment_statementContext.class,0);
		}
		public CommentStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCommentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCommentStatement(this);
		}
	}
	public static class StoreStatementContext extends StatementContext {
		public Store_statementContext stmt;
		public Store_statementContext store_statement() {
			return getRuleContext(Store_statementContext.class,0);
		}
		public StoreStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterStoreStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitStoreStatement(this);
		}
	}
	public static class WithSingletonStatementContext extends StatementContext {
		public With_singleton_statementContext stmt;
		public With_singleton_statementContext with_singleton_statement() {
			return getRuleContext(With_singleton_statementContext.class,0);
		}
		public WithSingletonStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWithSingletonStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWithSingletonStatement(this);
		}
	}
	public static class WriteStatementContext extends StatementContext {
		public Write_statementContext stmt;
		public Write_statementContext write_statement() {
			return getRuleContext(Write_statementContext.class,0);
		}
		public WriteStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWriteStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWriteStatement(this);
		}
	}
	public static class WhileStatementContext extends StatementContext {
		public While_statementContext stmt;
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public WhileStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWhileStatement(this);
		}
	}
	public static class WithResourceStatementContext extends StatementContext {
		public With_resource_statementContext stmt;
		public With_resource_statementContext with_resource_statement() {
			return getRuleContext(With_resource_statementContext.class,0);
		}
		public WithResourceStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWithResourceStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWithResourceStatement(this);
		}
	}
	public static class RaiseStatementContext extends StatementContext {
		public Raise_statementContext stmt;
		public Raise_statementContext raise_statement() {
			return getRuleContext(Raise_statementContext.class,0);
		}
		public RaiseStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRaiseStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRaiseStatement(this);
		}
	}
	public static class AssignInstanceStatementContext extends StatementContext {
		public Assign_instance_statementContext stmt;
		public Assign_instance_statementContext assign_instance_statement() {
			return getRuleContext(Assign_instance_statementContext.class,0);
		}
		public AssignInstanceStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssignInstanceStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssignInstanceStatement(this);
		}
	}
	public static class IfStatementContext extends StatementContext {
		public If_statementContext stmt;
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public IfStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIfStatement(this);
		}
	}
	public static class SwitchStatementContext extends StatementContext {
		public Switch_statementContext stmt;
		public Switch_statementContext switch_statement() {
			return getRuleContext(Switch_statementContext.class,0);
		}
		public SwitchStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSwitchStatement(this);
		}
	}
	public static class TryStatementContext extends StatementContext {
		public Try_statementContext stmt;
		public Try_statementContext try_statement() {
			return getRuleContext(Try_statementContext.class,0);
		}
		public TryStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTryStatement(this);
		}
	}
	public static class MethodCallStatementContext extends StatementContext {
		public Method_callContext stmt;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Method_callContext method_call() {
			return getRuleContext(Method_callContext.class,0);
		}
		public MethodCallStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodCallStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodCallStatement(this);
		}
	}
	public static class ReturnStatementContext extends StatementContext {
		public Return_statementContext stmt;
		public Return_statementContext return_statement() {
			return getRuleContext(Return_statementContext.class,0);
		}
		public ReturnStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitReturnStatement(this);
		}
	}
	public static class AssignTupleStatementContext extends StatementContext {
		public Assign_tuple_statementContext stmt;
		public Assign_tuple_statementContext assign_tuple_statement() {
			return getRuleContext(Assign_tuple_statementContext.class,0);
		}
		public AssignTupleStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssignTupleStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssignTupleStatement(this);
		}
	}
	public static class ClosureStatementContext extends StatementContext {
		public Concrete_method_declarationContext decl;
		public Concrete_method_declarationContext concrete_method_declaration() {
			return getRuleContext(Concrete_method_declarationContext.class,0);
		}
		public ClosureStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterClosureStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitClosureStatement(this);
		}
	}
	public static class DoWhileStatementContext extends StatementContext {
		public Do_while_statementContext stmt;
		public Do_while_statementContext do_while_statement() {
			return getRuleContext(Do_while_statementContext.class,0);
		}
		public DoWhileStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDoWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDoWhileStatement(this);
		}
	}
	public static class ForEachStatementContext extends StatementContext {
		public For_each_statementContext stmt;
		public For_each_statementContext for_each_statement() {
			return getRuleContext(For_each_statementContext.class,0);
		}
		public ForEachStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterForEachStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitForEachStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_statement);
		try {
			setState(689);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				_localctx = new MethodCallStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(670); 
				((MethodCallStatementContext)_localctx).stmt = method_call();
				setState(671); 
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new AssignInstanceStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(673); 
				((AssignInstanceStatementContext)_localctx).stmt = assign_instance_statement();
				}
				break;
			case 3:
				_localctx = new AssignTupleStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(674); 
				((AssignTupleStatementContext)_localctx).stmt = assign_tuple_statement();
				}
				break;
			case 4:
				_localctx = new StoreStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(675); 
				((StoreStatementContext)_localctx).stmt = store_statement();
				}
				break;
			case 5:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(676); 
				((ReturnStatementContext)_localctx).stmt = return_statement();
				}
				break;
			case 6:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(677); 
				((IfStatementContext)_localctx).stmt = if_statement();
				}
				break;
			case 7:
				_localctx = new SwitchStatementContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(678); 
				((SwitchStatementContext)_localctx).stmt = switch_statement();
				}
				break;
			case 8:
				_localctx = new ForEachStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(679); 
				((ForEachStatementContext)_localctx).stmt = for_each_statement();
				}
				break;
			case 9:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(680); 
				((WhileStatementContext)_localctx).stmt = while_statement();
				}
				break;
			case 10:
				_localctx = new DoWhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(681); 
				((DoWhileStatementContext)_localctx).stmt = do_while_statement();
				}
				break;
			case 11:
				_localctx = new TryStatementContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(682); 
				((TryStatementContext)_localctx).stmt = try_statement();
				}
				break;
			case 12:
				_localctx = new RaiseStatementContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(683); 
				((RaiseStatementContext)_localctx).stmt = raise_statement();
				}
				break;
			case 13:
				_localctx = new WriteStatementContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(684); 
				((WriteStatementContext)_localctx).stmt = write_statement();
				}
				break;
			case 14:
				_localctx = new WithResourceStatementContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(685); 
				((WithResourceStatementContext)_localctx).stmt = with_resource_statement();
				}
				break;
			case 15:
				_localctx = new WithSingletonStatementContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(686); 
				((WithSingletonStatementContext)_localctx).stmt = with_singleton_statement();
				}
				break;
			case 16:
				_localctx = new ClosureStatementContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(687); 
				((ClosureStatementContext)_localctx).decl = concrete_method_declaration();
				}
				break;
			case 17:
				_localctx = new CommentStatementContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(688); 
				((CommentStatementContext)_localctx).decl = comment_statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Store_statementContext extends ParserRuleContext {
		public Expression_listContext exps;
		public TerminalNode STORE() { return getToken(OParser.STORE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public Store_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_store_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterStore_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitStore_statement(this);
		}
	}

	public final Store_statementContext store_statement() throws RecognitionException {
		Store_statementContext _localctx = new Store_statementContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_store_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(691); 
			match(STORE);
			setState(692); 
			match(LPAR);
			setState(693); 
			((Store_statementContext)_localctx).exps = expression_list(0);
			setState(694); 
			match(RPAR);
			setState(695); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class With_resource_statementContext extends ParserRuleContext {
		public Assign_variable_statementContext stmt;
		public Statement_or_listContext stmts;
		public TerminalNode WITH() { return getToken(OParser.WITH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Assign_variable_statementContext assign_variable_statement() {
			return getRuleContext(Assign_variable_statementContext.class,0);
		}
		public Statement_or_listContext statement_or_list() {
			return getRuleContext(Statement_or_listContext.class,0);
		}
		public With_resource_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_with_resource_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWith_resource_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWith_resource_statement(this);
		}
	}

	public final With_resource_statementContext with_resource_statement() throws RecognitionException {
		With_resource_statementContext _localctx = new With_resource_statementContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_with_resource_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(697); 
			match(WITH);
			setState(698); 
			match(LPAR);
			setState(699); 
			((With_resource_statementContext)_localctx).stmt = assign_variable_statement();
			setState(700); 
			match(RPAR);
			setState(701); 
			((With_resource_statementContext)_localctx).stmts = statement_or_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class With_singleton_statementContext extends ParserRuleContext {
		public Type_identifierContext typ;
		public Statement_or_listContext stmts;
		public TerminalNode WITH() { return getToken(OParser.WITH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Statement_or_listContext statement_or_list() {
			return getRuleContext(Statement_or_listContext.class,0);
		}
		public With_singleton_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_with_singleton_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWith_singleton_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWith_singleton_statement(this);
		}
	}

	public final With_singleton_statementContext with_singleton_statement() throws RecognitionException {
		With_singleton_statementContext _localctx = new With_singleton_statementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_with_singleton_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(703); 
			match(WITH);
			setState(704); 
			match(LPAR);
			setState(705); 
			((With_singleton_statementContext)_localctx).typ = type_identifier();
			setState(706); 
			match(RPAR);
			setState(707); 
			((With_singleton_statementContext)_localctx).stmts = statement_or_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Switch_statementContext extends ParserRuleContext {
		public ExpressionContext exp;
		public Switch_case_statement_listContext cases;
		public Statement_listContext stmts;
		public TerminalNode SWITCH() { return getToken(OParser.SWITCH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Switch_case_statement_listContext switch_case_statement_list() {
			return getRuleContext(Switch_case_statement_listContext.class,0);
		}
		public TerminalNode DEFAULT() { return getToken(OParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Switch_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSwitch_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSwitch_statement(this);
		}
	}

	public final Switch_statementContext switch_statement() throws RecognitionException {
		Switch_statementContext _localctx = new Switch_statementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_switch_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(709); 
			match(SWITCH);
			setState(710); 
			match(LPAR);
			setState(711); 
			((Switch_statementContext)_localctx).exp = expression(0);
			setState(712); 
			match(RPAR);
			setState(713); 
			match(LCURL);
			setState(714); 
			((Switch_statementContext)_localctx).cases = switch_case_statement_list(0);
			setState(720);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(715); 
				match(DEFAULT);
				setState(716); 
				match(COLON);
				setState(718);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
					{
					setState(717); 
					((Switch_statementContext)_localctx).stmts = statement_list(0);
					}
				}

				}
			}

			setState(722); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Switch_case_statementContext extends ParserRuleContext {
		public Switch_case_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_case_statement; }
	 
		public Switch_case_statementContext() { }
		public void copyFrom(Switch_case_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AtomicSwitchCaseContext extends Switch_case_statementContext {
		public Atomic_literalContext exp;
		public Statement_listContext stmts;
		public TerminalNode CASE() { return getToken(OParser.CASE, 0); }
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public Atomic_literalContext atomic_literal() {
			return getRuleContext(Atomic_literalContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public AtomicSwitchCaseContext(Switch_case_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAtomicSwitchCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAtomicSwitchCase(this);
		}
	}
	public static class CollectionSwitchCaseContext extends Switch_case_statementContext {
		public Literal_collectionContext exp;
		public Statement_listContext stmts;
		public TerminalNode CASE() { return getToken(OParser.CASE, 0); }
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public Literal_collectionContext literal_collection() {
			return getRuleContext(Literal_collectionContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public CollectionSwitchCaseContext(Switch_case_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCollectionSwitchCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCollectionSwitchCase(this);
		}
	}

	public final Switch_case_statementContext switch_case_statement() throws RecognitionException {
		Switch_case_statementContext _localctx = new Switch_case_statementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_switch_case_statement);
		try {
			setState(737);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				_localctx = new AtomicSwitchCaseContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(724); 
				match(CASE);
				setState(725); 
				((AtomicSwitchCaseContext)_localctx).exp = atomic_literal();
				setState(726); 
				match(COLON);
				setState(728);
				switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
				case 1:
					{
					setState(727); 
					((AtomicSwitchCaseContext)_localctx).stmts = statement_list(0);
					}
					break;
				}
				}
				break;
			case 2:
				_localctx = new CollectionSwitchCaseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(730); 
				match(CASE);
				setState(731); 
				match(IN);
				setState(732); 
				((CollectionSwitchCaseContext)_localctx).exp = literal_collection();
				setState(733); 
				match(COLON);
				setState(735);
				switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
				case 1:
					{
					setState(734); 
					((CollectionSwitchCaseContext)_localctx).stmts = statement_list(0);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class For_each_statementContext extends ParserRuleContext {
		public Variable_identifierContext name1;
		public Variable_identifierContext name2;
		public ExpressionContext source;
		public Statement_or_listContext stmts;
		public TerminalNode FOR() { return getToken(OParser.FOR, 0); }
		public TerminalNode EACH() { return getToken(OParser.EACH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public List<Variable_identifierContext> variable_identifier() {
			return getRuleContexts(Variable_identifierContext.class);
		}
		public Variable_identifierContext variable_identifier(int i) {
			return getRuleContext(Variable_identifierContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Statement_or_listContext statement_or_list() {
			return getRuleContext(Statement_or_listContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public For_each_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_for_each_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterFor_each_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitFor_each_statement(this);
		}
	}

	public final For_each_statementContext for_each_statement() throws RecognitionException {
		For_each_statementContext _localctx = new For_each_statementContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_for_each_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(739); 
			match(FOR);
			setState(740); 
			match(EACH);
			setState(741); 
			match(LPAR);
			setState(742); 
			((For_each_statementContext)_localctx).name1 = variable_identifier();
			setState(745);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(743); 
				match(COMMA);
				setState(744); 
				((For_each_statementContext)_localctx).name2 = variable_identifier();
				}
			}

			setState(747); 
			match(IN);
			setState(748); 
			((For_each_statementContext)_localctx).source = expression(0);
			setState(749); 
			match(RPAR);
			setState(750); 
			((For_each_statementContext)_localctx).stmts = statement_or_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Do_while_statementContext extends ParserRuleContext {
		public Statement_listContext stmts;
		public ExpressionContext exp;
		public TerminalNode DO() { return getToken(OParser.DO, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public TerminalNode WHILE() { return getToken(OParser.WHILE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public Do_while_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_do_while_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDo_while_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDo_while_statement(this);
		}
	}

	public final Do_while_statementContext do_while_statement() throws RecognitionException {
		Do_while_statementContext _localctx = new Do_while_statementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_do_while_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(752); 
			match(DO);
			setState(753); 
			match(LCURL);
			setState(755);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
				{
				setState(754); 
				((Do_while_statementContext)_localctx).stmts = statement_list(0);
				}
			}

			setState(757); 
			match(RCURL);
			setState(758); 
			match(WHILE);
			setState(759); 
			match(LPAR);
			setState(760); 
			((Do_while_statementContext)_localctx).exp = expression(0);
			setState(761); 
			match(RPAR);
			setState(762); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class While_statementContext extends ParserRuleContext {
		public ExpressionContext exp;
		public Statement_or_listContext stmts;
		public TerminalNode WHILE() { return getToken(OParser.WHILE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Statement_or_listContext statement_or_list() {
			return getRuleContext(Statement_or_listContext.class,0);
		}
		public While_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWhile_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWhile_statement(this);
		}
	}

	public final While_statementContext while_statement() throws RecognitionException {
		While_statementContext _localctx = new While_statementContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(764); 
			match(WHILE);
			setState(765); 
			match(LPAR);
			setState(766); 
			((While_statementContext)_localctx).exp = expression(0);
			setState(767); 
			match(RPAR);
			setState(768); 
			((While_statementContext)_localctx).stmts = statement_or_list();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_statementContext extends ParserRuleContext {
		public ExpressionContext exp;
		public Statement_or_listContext stmts;
		public Else_if_statement_listContext elseIfs;
		public Statement_or_listContext elseStmts;
		public TerminalNode IF() { return getToken(OParser.IF, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<Statement_or_listContext> statement_or_list() {
			return getRuleContexts(Statement_or_listContext.class);
		}
		public Statement_or_listContext statement_or_list(int i) {
			return getRuleContext(Statement_or_listContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(OParser.ELSE, 0); }
		public Else_if_statement_listContext else_if_statement_list() {
			return getRuleContext(Else_if_statement_listContext.class,0);
		}
		public If_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIf_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIf_statement(this);
		}
	}

	public final If_statementContext if_statement() throws RecognitionException {
		If_statementContext _localctx = new If_statementContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_if_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(770); 
			match(IF);
			setState(771); 
			match(LPAR);
			setState(772); 
			((If_statementContext)_localctx).exp = expression(0);
			setState(773); 
			match(RPAR);
			setState(774); 
			((If_statementContext)_localctx).stmts = statement_or_list();
			setState(776);
			switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
			case 1:
				{
				setState(775); 
				((If_statementContext)_localctx).elseIfs = else_if_statement_list(0);
				}
				break;
			}
			setState(780);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				{
				setState(778); 
				match(ELSE);
				setState(779); 
				((If_statementContext)_localctx).elseStmts = statement_or_list();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Else_if_statement_listContext extends ParserRuleContext {
		public Else_if_statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_if_statement_list; }
	 
		public Else_if_statement_listContext() { }
		public void copyFrom(Else_if_statement_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ElseIfStatementListContext extends Else_if_statement_listContext {
		public ExpressionContext exp;
		public Statement_or_listContext stmts;
		public TerminalNode ELSE() { return getToken(OParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(OParser.IF, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Statement_or_listContext statement_or_list() {
			return getRuleContext(Statement_or_listContext.class,0);
		}
		public ElseIfStatementListContext(Else_if_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterElseIfStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitElseIfStatementList(this);
		}
	}
	public static class ElseIfStatementListItemContext extends Else_if_statement_listContext {
		public Else_if_statement_listContext items;
		public ExpressionContext exp;
		public Statement_or_listContext stmts;
		public TerminalNode ELSE() { return getToken(OParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(OParser.IF, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Else_if_statement_listContext else_if_statement_list() {
			return getRuleContext(Else_if_statement_listContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Statement_or_listContext statement_or_list() {
			return getRuleContext(Statement_or_listContext.class,0);
		}
		public ElseIfStatementListItemContext(Else_if_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterElseIfStatementListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitElseIfStatementListItem(this);
		}
	}

	public final Else_if_statement_listContext else_if_statement_list() throws RecognitionException {
		return else_if_statement_list(0);
	}

	private Else_if_statement_listContext else_if_statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Else_if_statement_listContext _localctx = new Else_if_statement_listContext(_ctx, _parentState);
		Else_if_statement_listContext _prevctx = _localctx;
		int _startState = 68;
		enterRecursionRule(_localctx, 68, RULE_else_if_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ElseIfStatementListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(783); 
			match(ELSE);
			setState(784); 
			match(IF);
			setState(785); 
			match(LPAR);
			setState(786); 
			((ElseIfStatementListContext)_localctx).exp = expression(0);
			setState(787); 
			match(RPAR);
			setState(788); 
			((ElseIfStatementListContext)_localctx).stmts = statement_or_list();
			}
			_ctx.stop = _input.LT(-1);
			setState(800);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ElseIfStatementListItemContext(new Else_if_statement_listContext(_parentctx, _parentState));
					((ElseIfStatementListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_else_if_statement_list);
					setState(790);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(791); 
					match(ELSE);
					setState(792); 
					match(IF);
					setState(793); 
					match(LPAR);
					setState(794); 
					((ElseIfStatementListItemContext)_localctx).exp = expression(0);
					setState(795); 
					match(RPAR);
					setState(796); 
					((ElseIfStatementListItemContext)_localctx).stmts = statement_or_list();
					}
					} 
				}
				setState(802);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Raise_statementContext extends ParserRuleContext {
		public ExpressionContext exp;
		public TerminalNode THROW() { return getToken(OParser.THROW, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Raise_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_raise_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRaise_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRaise_statement(this);
		}
	}

	public final Raise_statementContext raise_statement() throws RecognitionException {
		Raise_statementContext _localctx = new Raise_statementContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_raise_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(803); 
			match(THROW);
			setState(804); 
			((Raise_statementContext)_localctx).exp = expression(0);
			setState(805); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Try_statementContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public Statement_listContext stmts;
		public Catch_statement_listContext handlers;
		public Statement_listContext anyStmts;
		public Statement_listContext finalStmts;
		public TerminalNode TRY() { return getToken(OParser.TRY, 0); }
		public List<TerminalNode> LPAR() { return getTokens(OParser.LPAR); }
		public TerminalNode LPAR(int i) {
			return getToken(OParser.LPAR, i);
		}
		public List<TerminalNode> RPAR() { return getTokens(OParser.RPAR); }
		public TerminalNode RPAR(int i) {
			return getToken(OParser.RPAR, i);
		}
		public List<TerminalNode> LCURL() { return getTokens(OParser.LCURL); }
		public TerminalNode LCURL(int i) {
			return getToken(OParser.LCURL, i);
		}
		public List<TerminalNode> RCURL() { return getTokens(OParser.RCURL); }
		public TerminalNode RCURL(int i) {
			return getToken(OParser.RCURL, i);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode CATCH() { return getToken(OParser.CATCH, 0); }
		public TerminalNode ANY() { return getToken(OParser.ANY, 0); }
		public TerminalNode FINALLY() { return getToken(OParser.FINALLY, 0); }
		public List<Statement_listContext> statement_list() {
			return getRuleContexts(Statement_listContext.class);
		}
		public Statement_listContext statement_list(int i) {
			return getRuleContext(Statement_listContext.class,i);
		}
		public Catch_statement_listContext catch_statement_list() {
			return getRuleContext(Catch_statement_listContext.class,0);
		}
		public Try_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_try_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTry_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTry_statement(this);
		}
	}

	public final Try_statementContext try_statement() throws RecognitionException {
		Try_statementContext _localctx = new Try_statementContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_try_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(807); 
			match(TRY);
			setState(808); 
			match(LPAR);
			setState(809); 
			((Try_statementContext)_localctx).name = variable_identifier();
			setState(810); 
			match(RPAR);
			setState(811); 
			match(LCURL);
			setState(813);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
				{
				setState(812); 
				((Try_statementContext)_localctx).stmts = statement_list(0);
				}
			}

			setState(815); 
			match(RCURL);
			setState(817);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				setState(816); 
				((Try_statementContext)_localctx).handlers = catch_statement_list(0);
				}
				break;
			}
			setState(828);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(819); 
				match(CATCH);
				setState(820); 
				match(LPAR);
				setState(821); 
				match(ANY);
				setState(822); 
				match(RPAR);
				setState(823); 
				match(LCURL);
				setState(825);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
					{
					setState(824); 
					((Try_statementContext)_localctx).anyStmts = statement_list(0);
					}
				}

				setState(827); 
				match(RCURL);
				}
				break;
			}
			setState(836);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				setState(830); 
				match(FINALLY);
				setState(831); 
				match(LCURL);
				setState(833);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
					{
					setState(832); 
					((Try_statementContext)_localctx).finalStmts = statement_list(0);
					}
				}

				setState(835); 
				match(RCURL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Catch_statementContext extends ParserRuleContext {
		public Catch_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catch_statement; }
	 
		public Catch_statementContext() { }
		public void copyFrom(Catch_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CatchAtomicStatementContext extends Catch_statementContext {
		public Symbol_identifierContext name;
		public Statement_listContext stmts;
		public TerminalNode CATCH() { return getToken(OParser.CATCH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public CatchAtomicStatementContext(Catch_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCatchAtomicStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCatchAtomicStatement(this);
		}
	}
	public static class CatchCollectionStatementContext extends Catch_statementContext {
		public Symbol_listContext exp;
		public Statement_listContext stmts;
		public TerminalNode CATCH() { return getToken(OParser.CATCH, 0); }
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Symbol_listContext symbol_list() {
			return getRuleContext(Symbol_listContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public CatchCollectionStatementContext(Catch_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCatchCollectionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCatchCollectionStatement(this);
		}
	}

	public final Catch_statementContext catch_statement() throws RecognitionException {
		Catch_statementContext _localctx = new Catch_statementContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_catch_statement);
		int _la;
		try {
			setState(859);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				_localctx = new CatchAtomicStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(838); 
				match(CATCH);
				setState(839); 
				match(LPAR);
				setState(840); 
				((CatchAtomicStatementContext)_localctx).name = symbol_identifier();
				setState(841); 
				match(RPAR);
				setState(842); 
				match(LCURL);
				setState(844);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
					{
					setState(843); 
					((CatchAtomicStatementContext)_localctx).stmts = statement_list(0);
					}
				}

				setState(846); 
				match(RCURL);
				}
				break;
			case 2:
				_localctx = new CatchCollectionStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(848); 
				match(CATCH);
				setState(849); 
				match(IN);
				setState(850); 
				match(LPAR);
				setState(851); 
				((CatchCollectionStatementContext)_localctx).exp = symbol_list(0);
				setState(852); 
				match(RPAR);
				setState(853); 
				match(LCURL);
				setState(855);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID))) != 0) || ((((_la - 83)) & ~0x3f) == 0 && ((1L << (_la - 83)) & ((1L << (DO - 83)) | (1L << (FOR - 83)) | (1L << (IF - 83)) | (1L << (METHOD - 83)) | (1L << (RETURN - 83)) | (1L << (STORE - 83)) | (1L << (SWITCH - 83)) | (1L << (THROW - 83)) | (1L << (TRY - 83)) | (1L << (WITH - 83)) | (1L << (WHILE - 83)) | (1L << (WRITE - 83)))) != 0) || ((((_la - 149)) & ~0x3f) == 0 && ((1L << (_la - 149)) & ((1L << (SYMBOL_IDENTIFIER - 149)) | (1L << (TYPE_IDENTIFIER - 149)) | (1L << (VARIABLE_IDENTIFIER - 149)))) != 0)) {
					{
					setState(854); 
					((CatchCollectionStatementContext)_localctx).stmts = statement_list(0);
					}
				}

				setState(857); 
				match(RCURL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Return_statementContext extends ParserRuleContext {
		public ExpressionContext exp;
		public TerminalNode RETURN() { return getToken(OParser.RETURN, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Return_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterReturn_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitReturn_statement(this);
		}
	}

	public final Return_statementContext return_statement() throws RecognitionException {
		Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_return_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(861); 
			match(RETURN);
			setState(863);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(862); 
				((Return_statementContext)_localctx).exp = expression(0);
				}
			}

			setState(865); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_callContext extends ParserRuleContext {
		public Method_selectorContext method;
		public Argument_assignment_listContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Method_selectorContext method_selector() {
			return getRuleContext(Method_selectorContext.class,0);
		}
		public Argument_assignment_listContext argument_assignment_list() {
			return getRuleContext(Argument_assignment_listContext.class,0);
		}
		public Method_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethod_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethod_call(this);
		}
	}

	public final Method_callContext method_call() throws RecognitionException {
		Method_callContext _localctx = new Method_callContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_method_call);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(867); 
			((Method_callContext)_localctx).method = method_selector();
			setState(868); 
			match(LPAR);
			setState(870);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(869); 
				((Method_callContext)_localctx).args = argument_assignment_list(0);
				}
			}

			setState(872); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_selectorContext extends ParserRuleContext {
		public Method_selectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_selector; }
	 
		public Method_selectorContext() { }
		public void copyFrom(Method_selectorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MethodParentContext extends Method_selectorContext {
		public Callable_parentContext parent;
		public Method_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Callable_parentContext callable_parent() {
			return getRuleContext(Callable_parentContext.class,0);
		}
		public Method_identifierContext method_identifier() {
			return getRuleContext(Method_identifierContext.class,0);
		}
		public MethodParentContext(Method_selectorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodParent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodParent(this);
		}
	}
	public static class MethodNameContext extends Method_selectorContext {
		public Method_identifierContext name;
		public Method_identifierContext method_identifier() {
			return getRuleContext(Method_identifierContext.class,0);
		}
		public MethodNameContext(Method_selectorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodName(this);
		}
	}

	public final Method_selectorContext method_selector() throws RecognitionException {
		Method_selectorContext _localctx = new Method_selectorContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_method_selector);
		try {
			setState(879);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				_localctx = new MethodNameContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(874); 
				((MethodNameContext)_localctx).name = method_identifier();
				}
				break;
			case 2:
				_localctx = new MethodParentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(875); 
				((MethodParentContext)_localctx).parent = callable_parent(0);
				setState(876); 
				match(DOT);
				setState(877); 
				((MethodParentContext)_localctx).name = method_identifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Callable_parentContext extends ParserRuleContext {
		public Callable_parentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callable_parent; }
	 
		public Callable_parentContext() { }
		public void copyFrom(Callable_parentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallableSelectorContext extends Callable_parentContext {
		public Callable_parentContext parent;
		public Callable_selectorContext select;
		public Callable_parentContext callable_parent() {
			return getRuleContext(Callable_parentContext.class,0);
		}
		public Callable_selectorContext callable_selector() {
			return getRuleContext(Callable_selectorContext.class,0);
		}
		public CallableSelectorContext(Callable_parentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCallableSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCallableSelector(this);
		}
	}
	public static class CallableRootContext extends Callable_parentContext {
		public IdentifierContext name;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public CallableRootContext(Callable_parentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCallableRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCallableRoot(this);
		}
	}

	public final Callable_parentContext callable_parent() throws RecognitionException {
		return callable_parent(0);
	}

	private Callable_parentContext callable_parent(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Callable_parentContext _localctx = new Callable_parentContext(_ctx, _parentState);
		Callable_parentContext _prevctx = _localctx;
		int _startState = 82;
		enterRecursionRule(_localctx, 82, RULE_callable_parent, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new CallableRootContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(882); 
			((CallableRootContext)_localctx).name = identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(888);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CallableSelectorContext(new Callable_parentContext(_parentctx, _parentState));
					((CallableSelectorContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_callable_parent);
					setState(884);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(885); 
					((CallableSelectorContext)_localctx).select = callable_selector();
					}
					} 
				}
				setState(890);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Callable_selectorContext extends ParserRuleContext {
		public Callable_selectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callable_selector; }
	 
		public Callable_selectorContext() { }
		public void copyFrom(Callable_selectorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallableItemSelectorContext extends Callable_selectorContext {
		public ExpressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CallableItemSelectorContext(Callable_selectorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCallableItemSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCallableItemSelector(this);
		}
	}
	public static class CallableMemberSelectorContext extends Callable_selectorContext {
		public Variable_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public CallableMemberSelectorContext(Callable_selectorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCallableMemberSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCallableMemberSelector(this);
		}
	}

	public final Callable_selectorContext callable_selector() throws RecognitionException {
		Callable_selectorContext _localctx = new Callable_selectorContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_callable_selector);
		try {
			setState(897);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new CallableMemberSelectorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(891); 
				match(DOT);
				setState(892); 
				((CallableMemberSelectorContext)_localctx).name = variable_identifier();
				}
				break;
			case LBRAK:
				_localctx = new CallableItemSelectorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(893); 
				match(LBRAK);
				setState(894); 
				((CallableItemSelectorContext)_localctx).exp = expression(0);
				setState(895); 
				match(RBRAK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntDivideExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public IdivideContext idivide() {
			return getRuleContext(IdivideContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IntDivideExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIntDivideExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIntDivideExpression(this);
		}
	}
	public static class TernaryExpressionContext extends ExpressionContext {
		public ExpressionContext test;
		public ExpressionContext ifTrue;
		public ExpressionContext ifFalse;
		public TerminalNode QMARK() { return getToken(OParser.QMARK, 0); }
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TernaryExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTernaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTernaryExpression(this);
		}
	}
	public static class ContainsAllExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode CONTAINS() { return getToken(OParser.CONTAINS, 0); }
		public TerminalNode ALL() { return getToken(OParser.ALL, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ContainsAllExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterContainsAllExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitContainsAllExpression(this);
		}
	}
	public static class NotEqualsExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode XEQ() { return getToken(OParser.XEQ, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NotEqualsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNotEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNotEqualsExpression(this);
		}
	}
	public static class InExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterInExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitInExpression(this);
		}
	}
	public static class IsAnExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public An_expressionContext right;
		public TerminalNode IS() { return getToken(OParser.IS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public An_expressionContext an_expression() {
			return getRuleContext(An_expressionContext.class,0);
		}
		public IsAnExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIsAnExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIsAnExpression(this);
		}
	}
	public static class NotExpressionContext extends ExpressionContext {
		public ExpressionContext exp;
		public TerminalNode XMARK() { return getToken(OParser.XMARK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNotExpression(this);
		}
	}
	public static class GreaterThanExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode GT() { return getToken(OParser.GT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public GreaterThanExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterGreaterThanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitGreaterThanExpression(this);
		}
	}
	public static class OrExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode PIPE2() { return getToken(OParser.PIPE2, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOrExpression(this);
		}
	}
	public static class CodeExpressionContext extends ExpressionContext {
		public ExpressionContext exp;
		public TerminalNode CODE() { return getToken(OParser.CODE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CodeExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCodeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCodeExpression(this);
		}
	}
	public static class LessThanOrEqualExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode LTE() { return getToken(OParser.LTE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LessThanOrEqualExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLessThanOrEqualExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLessThanOrEqualExpression(this);
		}
	}
	public static class AndExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode AMP2() { return getToken(OParser.AMP2, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAndExpression(this);
		}
	}
	public static class ClosureExpressionContext extends ExpressionContext {
		public Closure_expressionContext exp;
		public Closure_expressionContext closure_expression() {
			return getRuleContext(Closure_expressionContext.class,0);
		}
		public ClosureExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterClosureExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitClosureExpression(this);
		}
	}
	public static class NotContainsAnyExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode NOT() { return getToken(OParser.NOT, 0); }
		public TerminalNode CONTAINS() { return getToken(OParser.CONTAINS, 0); }
		public TerminalNode ANY() { return getToken(OParser.ANY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NotContainsAnyExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNotContainsAnyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNotContainsAnyExpression(this);
		}
	}
	public static class ContainsExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode CONTAINS() { return getToken(OParser.CONTAINS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ContainsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterContainsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitContainsExpression(this);
		}
	}
	public static class NotContainsExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode NOT() { return getToken(OParser.NOT, 0); }
		public TerminalNode CONTAINS() { return getToken(OParser.CONTAINS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NotContainsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNotContainsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNotContainsExpression(this);
		}
	}
	public static class MultiplyExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public MultiplyContext multiply() {
			return getRuleContext(MultiplyContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultiplyExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMultiplyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMultiplyExpression(this);
		}
	}
	public static class RoughlyEqualsExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode TEQ() { return getToken(OParser.TEQ, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RoughlyEqualsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRoughlyEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRoughlyEqualsExpression(this);
		}
	}
	public static class IsNotAnExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public An_expressionContext right;
		public TerminalNode IS() { return getToken(OParser.IS, 0); }
		public TerminalNode NOT() { return getToken(OParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public An_expressionContext an_expression() {
			return getRuleContext(An_expressionContext.class,0);
		}
		public IsNotAnExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIsNotAnExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIsNotAnExpression(this);
		}
	}
	public static class ExecuteExpressionContext extends ExpressionContext {
		public Variable_identifierContext name;
		public TerminalNode EXECUTE() { return getToken(OParser.EXECUTE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public ExecuteExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterExecuteExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitExecuteExpression(this);
		}
	}
	public static class MethodExpressionContext extends ExpressionContext {
		public Method_expressionContext exp;
		public Method_expressionContext method_expression() {
			return getRuleContext(Method_expressionContext.class,0);
		}
		public MethodExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodExpression(this);
		}
	}
	public static class GreaterThanOrEqualExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode GTE() { return getToken(OParser.GTE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public GreaterThanOrEqualExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterGreaterThanOrEqualExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitGreaterThanOrEqualExpression(this);
		}
	}
	public static class NotInExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode NOT() { return getToken(OParser.NOT, 0); }
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NotInExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNotInExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNotInExpression(this);
		}
	}
	public static class IteratorExpressionContext extends ExpressionContext {
		public ExpressionContext exp;
		public Variable_identifierContext name;
		public ExpressionContext source;
		public TerminalNode FOR() { return getToken(OParser.FOR, 0); }
		public TerminalNode EACH() { return getToken(OParser.EACH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public IteratorExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIteratorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIteratorExpression(this);
		}
	}
	public static class IsNotExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode IS() { return getToken(OParser.IS, 0); }
		public TerminalNode NOT() { return getToken(OParser.NOT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IsNotExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIsNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIsNotExpression(this);
		}
	}
	public static class DivideExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public DivideContext divide() {
			return getRuleContext(DivideContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public DivideExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDivideExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDivideExpression(this);
		}
	}
	public static class IsExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode IS() { return getToken(OParser.IS, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIsExpression(this);
		}
	}
	public static class MinusExpressionContext extends ExpressionContext {
		public ExpressionContext exp;
		public TerminalNode MINUS() { return getToken(OParser.MINUS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MinusExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMinusExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMinusExpression(this);
		}
	}
	public static class AddExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(OParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(OParser.MINUS, 0); }
		public AddExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAddExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAddExpression(this);
		}
	}
	public static class NotContainsAllExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode NOT() { return getToken(OParser.NOT, 0); }
		public TerminalNode CONTAINS() { return getToken(OParser.CONTAINS, 0); }
		public TerminalNode ALL() { return getToken(OParser.ALL, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NotContainsAllExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNotContainsAllExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNotContainsAllExpression(this);
		}
	}
	public static class InstanceExpressionContext extends ExpressionContext {
		public Instance_expressionContext exp;
		public Instance_expressionContext instance_expression() {
			return getRuleContext(Instance_expressionContext.class,0);
		}
		public InstanceExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterInstanceExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitInstanceExpression(this);
		}
	}
	public static class CastExpressionContext extends ExpressionContext {
		public Category_or_any_typeContext right;
		public ExpressionContext left;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Category_or_any_typeContext category_or_any_type() {
			return getRuleContext(Category_or_any_typeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CastExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCastExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCastExpression(this);
		}
	}
	public static class ContainsAnyExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode CONTAINS() { return getToken(OParser.CONTAINS, 0); }
		public TerminalNode ANY() { return getToken(OParser.ANY, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ContainsAnyExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterContainsAnyExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitContainsAnyExpression(this);
		}
	}
	public static class ModuloExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public ModuloContext modulo() {
			return getRuleContext(ModuloContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ModuloExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterModuloExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitModuloExpression(this);
		}
	}
	public static class LessThanExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode LT() { return getToken(OParser.LT, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LessThanExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLessThanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLessThanExpression(this);
		}
	}
	public static class EqualsExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public ExpressionContext right;
		public TerminalNode EQ2() { return getToken(OParser.EQ2, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public EqualsExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitEqualsExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 86;
		enterRecursionRule(_localctx, 86, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(922);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				{
				_localctx = new MinusExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(900); 
				match(MINUS);
				setState(901); 
				((MinusExpressionContext)_localctx).exp = expression(34);
				}
				break;
			case 2:
				{
				_localctx = new NotExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(902); 
				match(XMARK);
				setState(903); 
				((NotExpressionContext)_localctx).exp = expression(33);
				}
				break;
			case 3:
				{
				_localctx = new CastExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(904); 
				match(LPAR);
				setState(905); 
				((CastExpressionContext)_localctx).right = category_or_any_type();
				setState(906); 
				match(RPAR);
				setState(907); 
				((CastExpressionContext)_localctx).left = expression(13);
				}
				break;
			case 4:
				{
				_localctx = new InstanceExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(909); 
				((InstanceExpressionContext)_localctx).exp = instance_expression(0);
				}
				break;
			case 5:
				{
				_localctx = new MethodExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(910); 
				((MethodExpressionContext)_localctx).exp = method_expression();
				}
				break;
			case 6:
				{
				_localctx = new CodeExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(911); 
				match(CODE);
				setState(912); 
				match(LPAR);
				setState(913); 
				((CodeExpressionContext)_localctx).exp = expression(0);
				setState(914); 
				match(RPAR);
				}
				break;
			case 7:
				{
				_localctx = new ExecuteExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(916); 
				match(EXECUTE);
				setState(917); 
				match(LPAR);
				setState(918); 
				((ExecuteExpressionContext)_localctx).name = variable_identifier();
				setState(919); 
				match(RPAR);
				}
				break;
			case 8:
				{
				_localctx = new ClosureExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(921); 
				((ClosureExpressionContext)_localctx).exp = closure_expression();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1033);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1031);
					switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplyExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((MultiplyExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(924);
						if (!(precpred(_ctx, 32))) throw new FailedPredicateException(this, "precpred(_ctx, 32)");
						setState(925); 
						multiply();
						setState(926); 
						((MultiplyExpressionContext)_localctx).right = expression(33);
						}
						break;
					case 2:
						{
						_localctx = new DivideExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((DivideExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(928);
						if (!(precpred(_ctx, 31))) throw new FailedPredicateException(this, "precpred(_ctx, 31)");
						setState(929); 
						divide();
						setState(930); 
						((DivideExpressionContext)_localctx).right = expression(32);
						}
						break;
					case 3:
						{
						_localctx = new ModuloExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ModuloExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(932);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(933); 
						modulo();
						setState(934); 
						((ModuloExpressionContext)_localctx).right = expression(31);
						}
						break;
					case 4:
						{
						_localctx = new IntDivideExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((IntDivideExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(936);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(937); 
						idivide();
						setState(938); 
						((IntDivideExpressionContext)_localctx).right = expression(30);
						}
						break;
					case 5:
						{
						_localctx = new AddExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AddExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(940);
						if (!(precpred(_ctx, 28))) throw new FailedPredicateException(this, "precpred(_ctx, 28)");
						setState(941);
						((AddExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((AddExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(942); 
						((AddExpressionContext)_localctx).right = expression(29);
						}
						break;
					case 6:
						{
						_localctx = new LessThanExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((LessThanExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(943);
						if (!(precpred(_ctx, 27))) throw new FailedPredicateException(this, "precpred(_ctx, 27)");
						setState(944); 
						match(LT);
						setState(945); 
						((LessThanExpressionContext)_localctx).right = expression(28);
						}
						break;
					case 7:
						{
						_localctx = new LessThanOrEqualExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((LessThanOrEqualExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(946);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(947); 
						match(LTE);
						setState(948); 
						((LessThanOrEqualExpressionContext)_localctx).right = expression(27);
						}
						break;
					case 8:
						{
						_localctx = new GreaterThanExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((GreaterThanExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(949);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(950); 
						match(GT);
						setState(951); 
						((GreaterThanExpressionContext)_localctx).right = expression(26);
						}
						break;
					case 9:
						{
						_localctx = new GreaterThanOrEqualExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((GreaterThanOrEqualExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(952);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(953); 
						match(GTE);
						setState(954); 
						((GreaterThanOrEqualExpressionContext)_localctx).right = expression(25);
						}
						break;
					case 10:
						{
						_localctx = new IsNotExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((IsNotExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(955);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(956); 
						match(IS);
						setState(957); 
						match(NOT);
						setState(958); 
						((IsNotExpressionContext)_localctx).right = expression(22);
						}
						break;
					case 11:
						{
						_localctx = new IsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((IsExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(959);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(960); 
						match(IS);
						setState(961); 
						((IsExpressionContext)_localctx).right = expression(21);
						}
						break;
					case 12:
						{
						_localctx = new EqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((EqualsExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(962);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(963); 
						match(EQ2);
						setState(964); 
						((EqualsExpressionContext)_localctx).right = expression(20);
						}
						break;
					case 13:
						{
						_localctx = new NotEqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((NotEqualsExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(965);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(966); 
						match(XEQ);
						setState(967); 
						((NotEqualsExpressionContext)_localctx).right = expression(19);
						}
						break;
					case 14:
						{
						_localctx = new RoughlyEqualsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((RoughlyEqualsExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(968);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(969); 
						match(TEQ);
						setState(970); 
						((RoughlyEqualsExpressionContext)_localctx).right = expression(18);
						}
						break;
					case 15:
						{
						_localctx = new OrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((OrExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(971);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(972); 
						match(PIPE2);
						setState(973); 
						((OrExpressionContext)_localctx).right = expression(17);
						}
						break;
					case 16:
						{
						_localctx = new AndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AndExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(974);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(975); 
						match(AMP2);
						setState(976); 
						((AndExpressionContext)_localctx).right = expression(16);
						}
						break;
					case 17:
						{
						_localctx = new TernaryExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((TernaryExpressionContext)_localctx).test = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(977);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(978); 
						match(QMARK);
						setState(979); 
						((TernaryExpressionContext)_localctx).ifTrue = expression(0);
						setState(980); 
						match(COLON);
						setState(981); 
						((TernaryExpressionContext)_localctx).ifFalse = expression(15);
						}
						break;
					case 18:
						{
						_localctx = new InExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((InExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(983);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(984); 
						match(IN);
						setState(985); 
						((InExpressionContext)_localctx).right = expression(13);
						}
						break;
					case 19:
						{
						_localctx = new ContainsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ContainsExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(986);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(987); 
						match(CONTAINS);
						setState(988); 
						((ContainsExpressionContext)_localctx).right = expression(12);
						}
						break;
					case 20:
						{
						_localctx = new ContainsAllExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ContainsAllExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(989);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(990); 
						match(CONTAINS);
						setState(991); 
						match(ALL);
						setState(992); 
						((ContainsAllExpressionContext)_localctx).right = expression(11);
						}
						break;
					case 21:
						{
						_localctx = new ContainsAnyExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ContainsAnyExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(993);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(994); 
						match(CONTAINS);
						setState(995); 
						match(ANY);
						setState(996); 
						((ContainsAnyExpressionContext)_localctx).right = expression(10);
						}
						break;
					case 22:
						{
						_localctx = new NotInExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((NotInExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(997);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(998); 
						match(NOT);
						setState(999); 
						match(IN);
						setState(1000); 
						((NotInExpressionContext)_localctx).right = expression(9);
						}
						break;
					case 23:
						{
						_localctx = new NotContainsExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((NotContainsExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(1001);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(1002); 
						match(NOT);
						setState(1003); 
						match(CONTAINS);
						setState(1004); 
						((NotContainsExpressionContext)_localctx).right = expression(8);
						}
						break;
					case 24:
						{
						_localctx = new NotContainsAllExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((NotContainsAllExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(1005);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(1006); 
						match(NOT);
						setState(1007); 
						match(CONTAINS);
						setState(1008); 
						match(ALL);
						setState(1009); 
						((NotContainsAllExpressionContext)_localctx).right = expression(7);
						}
						break;
					case 25:
						{
						_localctx = new NotContainsAnyExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((NotContainsAnyExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(1010);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(1011); 
						match(NOT);
						setState(1012); 
						match(CONTAINS);
						setState(1013); 
						match(ANY);
						setState(1014); 
						((NotContainsAnyExpressionContext)_localctx).right = expression(6);
						}
						break;
					case 26:
						{
						_localctx = new IsNotAnExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((IsNotAnExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(1015);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(1016); 
						match(IS);
						setState(1017); 
						match(NOT);
						setState(1018); 
						((IsNotAnExpressionContext)_localctx).right = an_expression();
						}
						break;
					case 27:
						{
						_localctx = new IsAnExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((IsAnExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(1019);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(1020); 
						match(IS);
						setState(1021); 
						((IsAnExpressionContext)_localctx).right = an_expression();
						}
						break;
					case 28:
						{
						_localctx = new IteratorExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((IteratorExpressionContext)_localctx).exp = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(1022);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1023); 
						match(FOR);
						setState(1024); 
						match(EACH);
						setState(1025); 
						match(LPAR);
						setState(1026); 
						((IteratorExpressionContext)_localctx).name = variable_identifier();
						setState(1027); 
						match(IN);
						setState(1028); 
						((IteratorExpressionContext)_localctx).source = expression(0);
						setState(1029); 
						match(RPAR);
						}
						break;
					}
					} 
				}
				setState(1035);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,61,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class An_expressionContext extends ParserRuleContext {
		public Category_or_any_typeContext typ;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Category_or_any_typeContext category_or_any_type() {
			return getRuleContext(Category_or_any_typeContext.class,0);
		}
		public An_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_an_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAn_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAn_expression(this);
		}
	}

	public final An_expressionContext an_expression() throws RecognitionException {
		An_expressionContext _localctx = new An_expressionContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_an_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1036);
			if (!(this.willBeAOrAn())) throw new FailedPredicateException(this, "$parser.willBeAOrAn()");
			setState(1037); 
			match(VARIABLE_IDENTIFIER);
			setState(1038); 
			((An_expressionContext)_localctx).typ = category_or_any_type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Closure_expressionContext extends ParserRuleContext {
		public Type_identifierContext name;
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public Closure_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closure_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterClosure_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitClosure_expression(this);
		}
	}

	public final Closure_expressionContext closure_expression() throws RecognitionException {
		Closure_expressionContext _localctx = new Closure_expressionContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_closure_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1040); 
			((Closure_expressionContext)_localctx).name = type_identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Instance_expressionContext extends ParserRuleContext {
		public Instance_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instance_expression; }
	 
		public Instance_expressionContext() { }
		public void copyFrom(Instance_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SelectorExpressionContext extends Instance_expressionContext {
		public Instance_expressionContext parent;
		public Selector_expressionContext selector;
		public Instance_expressionContext instance_expression() {
			return getRuleContext(Instance_expressionContext.class,0);
		}
		public Selector_expressionContext selector_expression() {
			return getRuleContext(Selector_expressionContext.class,0);
		}
		public SelectorExpressionContext(Instance_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSelectorExpression(this);
		}
	}
	public static class SelectableExpressionContext extends Instance_expressionContext {
		public Selectable_expressionContext parent;
		public Selectable_expressionContext selectable_expression() {
			return getRuleContext(Selectable_expressionContext.class,0);
		}
		public SelectableExpressionContext(Instance_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSelectableExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSelectableExpression(this);
		}
	}

	public final Instance_expressionContext instance_expression() throws RecognitionException {
		return instance_expression(0);
	}

	private Instance_expressionContext instance_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Instance_expressionContext _localctx = new Instance_expressionContext(_ctx, _parentState);
		Instance_expressionContext _prevctx = _localctx;
		int _startState = 92;
		enterRecursionRule(_localctx, 92, RULE_instance_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new SelectableExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1043); 
			((SelectableExpressionContext)_localctx).parent = selectable_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(1049);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SelectorExpressionContext(new Instance_expressionContext(_parentctx, _parentState));
					((SelectorExpressionContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_instance_expression);
					setState(1045);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1046); 
					((SelectorExpressionContext)_localctx).selector = selector_expression();
					}
					} 
				}
				setState(1051);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,62,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Method_expressionContext extends ParserRuleContext {
		public Method_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_expression; }
	 
		public Method_expressionContext() { }
		public void copyFrom(Method_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReadExpressionContext extends Method_expressionContext {
		public Read_expressionContext exp;
		public Read_expressionContext read_expression() {
			return getRuleContext(Read_expressionContext.class,0);
		}
		public ReadExpressionContext(Method_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterReadExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitReadExpression(this);
		}
	}
	public static class MethodCallExpressionContext extends Method_expressionContext {
		public Method_callContext exp;
		public Method_callContext method_call() {
			return getRuleContext(Method_callContext.class,0);
		}
		public MethodCallExpressionContext(Method_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodCallExpression(this);
		}
	}
	public static class FetchExpressionContext extends Method_expressionContext {
		public Fetch_expressionContext exp;
		public Fetch_expressionContext fetch_expression() {
			return getRuleContext(Fetch_expressionContext.class,0);
		}
		public FetchExpressionContext(Method_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterFetchExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitFetchExpression(this);
		}
	}
	public static class ConstructorExpressionContext extends Method_expressionContext {
		public Constructor_expressionContext exp;
		public Constructor_expressionContext constructor_expression() {
			return getRuleContext(Constructor_expressionContext.class,0);
		}
		public ConstructorExpressionContext(Method_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterConstructorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitConstructorExpression(this);
		}
	}
	public static class DocumentExpressionContext extends Method_expressionContext {
		public Document_expressionContext exp;
		public Document_expressionContext document_expression() {
			return getRuleContext(Document_expressionContext.class,0);
		}
		public DocumentExpressionContext(Method_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDocumentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDocumentExpression(this);
		}
	}
	public static class SortedExpressionContext extends Method_expressionContext {
		public Sorted_expressionContext exp;
		public Sorted_expressionContext sorted_expression() {
			return getRuleContext(Sorted_expressionContext.class,0);
		}
		public SortedExpressionContext(Method_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSortedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSortedExpression(this);
		}
	}

	public final Method_expressionContext method_expression() throws RecognitionException {
		Method_expressionContext _localctx = new Method_expressionContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_method_expression);
		try {
			setState(1058);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				_localctx = new DocumentExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1052); 
				((DocumentExpressionContext)_localctx).exp = document_expression();
				}
				break;
			case 2:
				_localctx = new FetchExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1053); 
				((FetchExpressionContext)_localctx).exp = fetch_expression();
				}
				break;
			case 3:
				_localctx = new ReadExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1054); 
				((ReadExpressionContext)_localctx).exp = read_expression();
				}
				break;
			case 4:
				_localctx = new SortedExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1055); 
				((SortedExpressionContext)_localctx).exp = sorted_expression();
				}
				break;
			case 5:
				_localctx = new MethodCallExpressionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1056); 
				((MethodCallExpressionContext)_localctx).exp = method_call();
				}
				break;
			case 6:
				_localctx = new ConstructorExpressionContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1057); 
				((ConstructorExpressionContext)_localctx).exp = constructor_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Document_expressionContext extends ParserRuleContext {
		public TerminalNode DOCUMENT() { return getToken(OParser.DOCUMENT, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Document_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDocument_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDocument_expression(this);
		}
	}

	public final Document_expressionContext document_expression() throws RecognitionException {
		Document_expressionContext _localctx = new Document_expressionContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_document_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1060); 
			match(DOCUMENT);
			setState(1061); 
			match(LPAR);
			setState(1062); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Read_expressionContext extends ParserRuleContext {
		public ExpressionContext source;
		public TerminalNode READ() { return getToken(OParser.READ, 0); }
		public TerminalNode FROM() { return getToken(OParser.FROM, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Read_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRead_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRead_expression(this);
		}
	}

	public final Read_expressionContext read_expression() throws RecognitionException {
		Read_expressionContext _localctx = new Read_expressionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_read_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1064); 
			match(READ);
			setState(1065); 
			match(FROM);
			setState(1066); 
			((Read_expressionContext)_localctx).source = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Write_statementContext extends ParserRuleContext {
		public ExpressionContext what;
		public ExpressionContext target;
		public TerminalNode WRITE() { return getToken(OParser.WRITE, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode TO() { return getToken(OParser.TO, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Write_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_write_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterWrite_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitWrite_statement(this);
		}
	}

	public final Write_statementContext write_statement() throws RecognitionException {
		Write_statementContext _localctx = new Write_statementContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_write_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1068); 
			match(WRITE);
			setState(1069); 
			match(LPAR);
			setState(1070); 
			((Write_statementContext)_localctx).what = expression(0);
			setState(1071); 
			match(RPAR);
			setState(1072); 
			match(TO);
			setState(1073); 
			((Write_statementContext)_localctx).target = expression(0);
			setState(1074); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Fetch_expressionContext extends ParserRuleContext {
		public Fetch_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fetch_expression; }
	 
		public Fetch_expressionContext() { }
		public void copyFrom(Fetch_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FetchOneContext extends Fetch_expressionContext {
		public Category_typeContext typ;
		public ExpressionContext xfilter;
		public TerminalNode FETCH() { return getToken(OParser.FETCH, 0); }
		public TerminalNode ONE() { return getToken(OParser.ONE, 0); }
		public List<TerminalNode> LPAR() { return getTokens(OParser.LPAR); }
		public TerminalNode LPAR(int i) {
			return getToken(OParser.LPAR, i);
		}
		public List<TerminalNode> RPAR() { return getTokens(OParser.RPAR); }
		public TerminalNode RPAR(int i) {
			return getToken(OParser.RPAR, i);
		}
		public TerminalNode WHERE() { return getToken(OParser.WHERE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Category_typeContext category_type() {
			return getRuleContext(Category_typeContext.class,0);
		}
		public FetchOneContext(Fetch_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterFetchOne(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitFetchOne(this);
		}
	}
	public static class FetchListContext extends Fetch_expressionContext {
		public Variable_identifierContext name;
		public ExpressionContext source;
		public ExpressionContext xfilter;
		public TerminalNode FETCH() { return getToken(OParser.FETCH, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public TerminalNode FROM() { return getToken(OParser.FROM, 0); }
		public TerminalNode WHERE() { return getToken(OParser.WHERE, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FetchListContext(Fetch_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterFetchList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitFetchList(this);
		}
	}
	public static class FetchAllContext extends Fetch_expressionContext {
		public Category_typeContext typ;
		public ExpressionContext xstart;
		public ExpressionContext xstop;
		public ExpressionContext xfilter;
		public Order_by_listContext xorder;
		public TerminalNode FETCH() { return getToken(OParser.FETCH, 0); }
		public TerminalNode WHERE() { return getToken(OParser.WHERE, 0); }
		public List<TerminalNode> LPAR() { return getTokens(OParser.LPAR); }
		public TerminalNode LPAR(int i) {
			return getToken(OParser.LPAR, i);
		}
		public List<TerminalNode> RPAR() { return getTokens(OParser.RPAR); }
		public TerminalNode RPAR(int i) {
			return getToken(OParser.RPAR, i);
		}
		public TerminalNode ORDER() { return getToken(OParser.ORDER, 0); }
		public TerminalNode BY() { return getToken(OParser.BY, 0); }
		public TerminalNode ALL() { return getToken(OParser.ALL, 0); }
		public TerminalNode ROWS() { return getToken(OParser.ROWS, 0); }
		public TerminalNode TO() { return getToken(OParser.TO, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Order_by_listContext order_by_list() {
			return getRuleContext(Order_by_listContext.class,0);
		}
		public Category_typeContext category_type() {
			return getRuleContext(Category_typeContext.class,0);
		}
		public FetchAllContext(Fetch_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterFetchAll(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitFetchAll(this);
		}
	}

	public final Fetch_expressionContext fetch_expression() throws RecognitionException {
		Fetch_expressionContext _localctx = new Fetch_expressionContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_fetch_expression);
		int _la;
		try {
			setState(1133);
			switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
			case 1:
				_localctx = new FetchListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1076); 
				match(FETCH);
				setState(1077); 
				match(LPAR);
				setState(1078); 
				((FetchListContext)_localctx).name = variable_identifier();
				setState(1079); 
				match(RPAR);
				setState(1080); 
				match(FROM);
				setState(1081); 
				((FetchListContext)_localctx).source = expression(0);
				setState(1082); 
				match(WHERE);
				setState(1083); 
				((FetchListContext)_localctx).xfilter = expression(0);
				}
				break;
			case 2:
				_localctx = new FetchOneContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1085); 
				match(FETCH);
				setState(1086); 
				match(ONE);
				setState(1087); 
				match(LPAR);
				setState(1089);
				_la = _input.LA(1);
				if (_la==TYPE_IDENTIFIER) {
					{
					setState(1088); 
					((FetchOneContext)_localctx).typ = category_type();
					}
				}

				setState(1091); 
				match(RPAR);
				setState(1092); 
				match(WHERE);
				setState(1093); 
				match(LPAR);
				setState(1094); 
				((FetchOneContext)_localctx).xfilter = expression(0);
				setState(1095); 
				match(RPAR);
				}
				break;
			case 3:
				_localctx = new FetchAllContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1097); 
				match(FETCH);
				setState(1116);
				switch (_input.LA(1)) {
				case ALL:
					{
					{
					setState(1098); 
					match(ALL);
					setState(1099); 
					match(LPAR);
					setState(1101);
					_la = _input.LA(1);
					if (_la==TYPE_IDENTIFIER) {
						{
						setState(1100); 
						((FetchAllContext)_localctx).typ = category_type();
						}
					}

					setState(1103); 
					match(RPAR);
					}
					}
					break;
				case LPAR:
					{
					{
					setState(1104); 
					match(LPAR);
					setState(1106);
					_la = _input.LA(1);
					if (_la==TYPE_IDENTIFIER) {
						{
						setState(1105); 
						((FetchAllContext)_localctx).typ = category_type();
						}
					}

					setState(1108); 
					match(RPAR);
					setState(1109); 
					match(ROWS);
					setState(1110); 
					match(LPAR);
					setState(1111); 
					((FetchAllContext)_localctx).xstart = expression(0);
					setState(1112); 
					match(TO);
					setState(1113); 
					((FetchAllContext)_localctx).xstop = expression(0);
					setState(1114); 
					match(RPAR);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1123);
				switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
				case 1:
					{
					setState(1118); 
					match(WHERE);
					setState(1119); 
					match(LPAR);
					setState(1120); 
					((FetchAllContext)_localctx).xfilter = expression(0);
					setState(1121); 
					match(RPAR);
					}
					break;
				}
				setState(1131);
				switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
				case 1:
					{
					setState(1125); 
					match(ORDER);
					setState(1126); 
					match(BY);
					setState(1127); 
					match(LPAR);
					setState(1128); 
					((FetchAllContext)_localctx).xorder = order_by_list();
					setState(1129); 
					match(RPAR);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sorted_expressionContext extends ParserRuleContext {
		public Instance_expressionContext source;
		public Instance_expressionContext key;
		public TerminalNode SORTED() { return getToken(OParser.SORTED, 0); }
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public List<Instance_expressionContext> instance_expression() {
			return getRuleContexts(Instance_expressionContext.class);
		}
		public Instance_expressionContext instance_expression(int i) {
			return getRuleContext(Instance_expressionContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Key_tokenContext key_token() {
			return getRuleContext(Key_tokenContext.class,0);
		}
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public Sorted_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sorted_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSorted_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSorted_expression(this);
		}
	}

	public final Sorted_expressionContext sorted_expression() throws RecognitionException {
		Sorted_expressionContext _localctx = new Sorted_expressionContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_sorted_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1135); 
			match(SORTED);
			setState(1136); 
			match(LPAR);
			setState(1137); 
			((Sorted_expressionContext)_localctx).source = instance_expression(0);
			setState(1143);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(1138); 
				match(COMMA);
				setState(1139); 
				key_token();
				setState(1140); 
				match(EQ);
				setState(1141); 
				((Sorted_expressionContext)_localctx).key = instance_expression(0);
				}
			}

			setState(1145); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Selector_expressionContext extends ParserRuleContext {
		public Selector_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector_expression; }
	 
		public Selector_expressionContext() { }
		public void copyFrom(Selector_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SliceSelectorContext extends Selector_expressionContext {
		public Slice_argumentsContext xslice;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Slice_argumentsContext slice_arguments() {
			return getRuleContext(Slice_argumentsContext.class,0);
		}
		public SliceSelectorContext(Selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSliceSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSliceSelector(this);
		}
	}
	public static class MemberSelectorContext extends Selector_expressionContext {
		public Variable_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public MemberSelectorContext(Selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMemberSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMemberSelector(this);
		}
	}
	public static class ItemSelectorContext extends Selector_expressionContext {
		public ExpressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ItemSelectorContext(Selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterItemSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitItemSelector(this);
		}
	}

	public final Selector_expressionContext selector_expression() throws RecognitionException {
		Selector_expressionContext _localctx = new Selector_expressionContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_selector_expression);
		try {
			setState(1157);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				_localctx = new MemberSelectorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1147); 
				match(DOT);
				setState(1148); 
				((MemberSelectorContext)_localctx).name = variable_identifier();
				}
				break;
			case 2:
				_localctx = new ItemSelectorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1149); 
				match(LBRAK);
				setState(1150); 
				((ItemSelectorContext)_localctx).exp = expression(0);
				setState(1151); 
				match(RBRAK);
				}
				break;
			case 3:
				_localctx = new SliceSelectorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1153); 
				match(LBRAK);
				setState(1154); 
				((SliceSelectorContext)_localctx).xslice = slice_arguments();
				setState(1155); 
				match(RBRAK);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Constructor_expressionContext extends ParserRuleContext {
		public Category_typeContext typ;
		public Argument_assignment_listContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Category_typeContext category_type() {
			return getRuleContext(Category_typeContext.class,0);
		}
		public TerminalNode MUTABLE() { return getToken(OParser.MUTABLE, 0); }
		public Argument_assignment_listContext argument_assignment_list() {
			return getRuleContext(Argument_assignment_listContext.class,0);
		}
		public Constructor_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructor_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterConstructor_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitConstructor_expression(this);
		}
	}

	public final Constructor_expressionContext constructor_expression() throws RecognitionException {
		Constructor_expressionContext _localctx = new Constructor_expressionContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_constructor_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1160);
			_la = _input.LA(1);
			if (_la==MUTABLE) {
				{
				setState(1159); 
				match(MUTABLE);
				}
			}

			setState(1162); 
			((Constructor_expressionContext)_localctx).typ = category_type();
			setState(1163); 
			match(LPAR);
			setState(1165);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(1164); 
				((Constructor_expressionContext)_localctx).args = argument_assignment_list(0);
				}
			}

			setState(1167); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Argument_assignment_listContext extends ParserRuleContext {
		public Argument_assignment_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_assignment_list; }
	 
		public Argument_assignment_listContext() { }
		public void copyFrom(Argument_assignment_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExpressionAssignmentListContext extends Argument_assignment_listContext {
		public ExpressionContext exp;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionAssignmentListContext(Argument_assignment_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterExpressionAssignmentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitExpressionAssignmentList(this);
		}
	}
	public static class ArgumentAssignmentListContext extends Argument_assignment_listContext {
		public Argument_assignmentContext item;
		public Argument_assignmentContext argument_assignment() {
			return getRuleContext(Argument_assignmentContext.class,0);
		}
		public ArgumentAssignmentListContext(Argument_assignment_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterArgumentAssignmentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitArgumentAssignmentList(this);
		}
	}
	public static class ArgumentAssignmentListItemContext extends Argument_assignment_listContext {
		public Argument_assignment_listContext items;
		public Argument_assignmentContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Argument_assignment_listContext argument_assignment_list() {
			return getRuleContext(Argument_assignment_listContext.class,0);
		}
		public Argument_assignmentContext argument_assignment() {
			return getRuleContext(Argument_assignmentContext.class,0);
		}
		public ArgumentAssignmentListItemContext(Argument_assignment_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterArgumentAssignmentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitArgumentAssignmentListItem(this);
		}
	}

	public final Argument_assignment_listContext argument_assignment_list() throws RecognitionException {
		return argument_assignment_list(0);
	}

	private Argument_assignment_listContext argument_assignment_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Argument_assignment_listContext _localctx = new Argument_assignment_listContext(_ctx, _parentState);
		Argument_assignment_listContext _prevctx = _localctx;
		int _startState = 110;
		enterRecursionRule(_localctx, 110, RULE_argument_assignment_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1174);
			switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
			case 1:
				{
				_localctx = new ExpressionAssignmentListContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1170); 
				((ExpressionAssignmentListContext)_localctx).exp = expression(0);
				setState(1171);
				if (!(this.willNotBe(this.equalToken()))) throw new FailedPredicateException(this, "$parser.willNotBe($parser.equalToken())");
				}
				break;
			case 2:
				{
				_localctx = new ArgumentAssignmentListContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1173); 
				((ArgumentAssignmentListContext)_localctx).item = argument_assignment();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(1181);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArgumentAssignmentListItemContext(new Argument_assignment_listContext(_parentctx, _parentState));
					((ArgumentAssignmentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_argument_assignment_list);
					setState(1176);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1177); 
					match(COMMA);
					setState(1178); 
					((ArgumentAssignmentListItemContext)_localctx).item = argument_assignment();
					}
					} 
				}
				setState(1183);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,76,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Argument_assignmentContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public ExpressionContext exp;
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Argument_assignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterArgument_assignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitArgument_assignment(this);
		}
	}

	public final Argument_assignmentContext argument_assignment() throws RecognitionException {
		Argument_assignmentContext _localctx = new Argument_assignmentContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_argument_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1184); 
			((Argument_assignmentContext)_localctx).name = variable_identifier();
			setState(1185); 
			assign();
			setState(1186); 
			((Argument_assignmentContext)_localctx).exp = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_instance_statementContext extends ParserRuleContext {
		public Assignable_instanceContext inst;
		public ExpressionContext exp;
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Assignable_instanceContext assignable_instance() {
			return getRuleContext(Assignable_instanceContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Assign_instance_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_instance_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssign_instance_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssign_instance_statement(this);
		}
	}

	public final Assign_instance_statementContext assign_instance_statement() throws RecognitionException {
		Assign_instance_statementContext _localctx = new Assign_instance_statementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_assign_instance_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1188); 
			((Assign_instance_statementContext)_localctx).inst = assignable_instance(0);
			setState(1189); 
			assign();
			setState(1190); 
			((Assign_instance_statementContext)_localctx).exp = expression(0);
			setState(1191); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Child_instanceContext extends ParserRuleContext {
		public Child_instanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_child_instance; }
	 
		public Child_instanceContext() { }
		public void copyFrom(Child_instanceContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MemberInstanceContext extends Child_instanceContext {
		public Variable_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public MemberInstanceContext(Child_instanceContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMemberInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMemberInstance(this);
		}
	}
	public static class ItemInstanceContext extends Child_instanceContext {
		public ExpressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ItemInstanceContext(Child_instanceContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterItemInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitItemInstance(this);
		}
	}

	public final Child_instanceContext child_instance() throws RecognitionException {
		Child_instanceContext _localctx = new Child_instanceContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_child_instance);
		try {
			setState(1199);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new MemberInstanceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1193); 
				match(DOT);
				setState(1194); 
				((MemberInstanceContext)_localctx).name = variable_identifier();
				}
				break;
			case LBRAK:
				_localctx = new ItemInstanceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1195); 
				match(LBRAK);
				setState(1196); 
				((ItemInstanceContext)_localctx).exp = expression(0);
				setState(1197); 
				match(RBRAK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_tuple_statementContext extends ParserRuleContext {
		public Variable_identifier_listContext items;
		public ExpressionContext exp;
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Variable_identifier_listContext variable_identifier_list() {
			return getRuleContext(Variable_identifier_listContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Assign_tuple_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_tuple_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssign_tuple_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssign_tuple_statement(this);
		}
	}

	public final Assign_tuple_statementContext assign_tuple_statement() throws RecognitionException {
		Assign_tuple_statementContext _localctx = new Assign_tuple_statementContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_assign_tuple_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1201); 
			((Assign_tuple_statementContext)_localctx).items = variable_identifier_list(0);
			setState(1202); 
			assign();
			setState(1203); 
			((Assign_tuple_statementContext)_localctx).exp = expression(0);
			setState(1204); 
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Null_literalContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(OParser.NULL, 0); }
		public Null_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_null_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNull_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNull_literal(this);
		}
	}

	public final Null_literalContext null_literal() throws RecognitionException {
		Null_literalContext _localctx = new Null_literalContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_null_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1206); 
			match(NULL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Declaration_listContext extends ParserRuleContext {
		public Declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_list; }
	 
		public Declaration_listContext() { }
		public void copyFrom(Declaration_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FullDeclarationListContext extends Declaration_listContext {
		public DeclarationsContext items;
		public LfsContext lfs() {
			return getRuleContext(LfsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(OParser.EOF, 0); }
		public DeclarationsContext declarations() {
			return getRuleContext(DeclarationsContext.class,0);
		}
		public FullDeclarationListContext(Declaration_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterFullDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitFullDeclarationList(this);
		}
	}

	public final Declaration_listContext declaration_list() throws RecognitionException {
		Declaration_listContext _localctx = new Declaration_listContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_declaration_list);
		int _la;
		try {
			_localctx = new FullDeclarationListContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(1209);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COMMENT) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD) | (1L << CODE) | (1L << DOCUMENT) | (1L << BLOB) | (1L << IMAGE) | (1L << UUID) | (1L << ABSTRACT))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (ANY - 65)) | (1L << (ATTRIBUTE - 65)) | (1L << (CATEGORY - 65)) | (1L << (ENUMERATED - 65)) | (1L << (METHOD - 65)) | (1L << (NATIVE - 65)))) != 0) || ((((_la - 129)) & ~0x3f) == 0 && ((1L << (_la - 129)) & ((1L << (SINGLETON - 129)) | (1L << (STORABLE - 129)) | (1L << (TEST - 129)) | (1L << (TYPE_IDENTIFIER - 129)))) != 0)) {
				{
				setState(1208); 
				((FullDeclarationListContext)_localctx).items = declarations(0);
				}
			}

			setState(1211); 
			lfs();
			setState(1212); 
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationsContext extends ParserRuleContext {
		public DeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarations; }
	 
		public DeclarationsContext() { }
		public void copyFrom(DeclarationsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DeclarationListItemContext extends DeclarationsContext {
		public DeclarationsContext items;
		public DeclarationContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public DeclarationsContext declarations() {
			return getRuleContext(DeclarationsContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public DeclarationListItemContext(DeclarationsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDeclarationListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDeclarationListItem(this);
		}
	}
	public static class DeclarationListContext extends DeclarationsContext {
		public DeclarationContext item;
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public DeclarationListContext(DeclarationsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDeclarationList(this);
		}
	}

	public final DeclarationsContext declarations() throws RecognitionException {
		return declarations(0);
	}

	private DeclarationsContext declarations(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		DeclarationsContext _localctx = new DeclarationsContext(_ctx, _parentState);
		DeclarationsContext _prevctx = _localctx;
		int _startState = 124;
		enterRecursionRule(_localctx, 124, RULE_declarations, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new DeclarationListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1215); 
			((DeclarationListContext)_localctx).item = declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(1223);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DeclarationListItemContext(new DeclarationsContext(_parentctx, _parentState));
					((DeclarationListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_declarations);
					setState(1217);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1218); 
					lfp();
					setState(1219); 
					((DeclarationListItemContext)_localctx).item = declaration();
					}
					} 
				}
				setState(1225);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,79,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public Attribute_declarationContext attribute_declaration() {
			return getRuleContext(Attribute_declarationContext.class,0);
		}
		public Category_declarationContext category_declaration() {
			return getRuleContext(Category_declarationContext.class,0);
		}
		public Resource_declarationContext resource_declaration() {
			return getRuleContext(Resource_declarationContext.class,0);
		}
		public Enum_declarationContext enum_declaration() {
			return getRuleContext(Enum_declarationContext.class,0);
		}
		public Method_declarationContext method_declaration() {
			return getRuleContext(Method_declarationContext.class,0);
		}
		public List<Comment_statementContext> comment_statement() {
			return getRuleContexts(Comment_statementContext.class);
		}
		public Comment_statementContext comment_statement(int i) {
			return getRuleContext(Comment_statementContext.class,i);
		}
		public List<LfpContext> lfp() {
			return getRuleContexts(LfpContext.class);
		}
		public LfpContext lfp(int i) {
			return getRuleContext(LfpContext.class,i);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1231);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(1226); 
				comment_statement();
				setState(1227); 
				lfp();
				}
				}
				setState(1233);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1239);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				{
				setState(1234); 
				attribute_declaration();
				}
				break;
			case 2:
				{
				setState(1235); 
				category_declaration();
				}
				break;
			case 3:
				{
				setState(1236); 
				resource_declaration();
				}
				break;
			case 4:
				{
				setState(1237); 
				enum_declaration();
				}
				break;
			case 5:
				{
				setState(1238); 
				method_declaration();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Resource_declarationContext extends ParserRuleContext {
		public Native_resource_declarationContext decl;
		public Native_resource_declarationContext native_resource_declaration() {
			return getRuleContext(Native_resource_declarationContext.class,0);
		}
		public Resource_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_resource_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterResource_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitResource_declaration(this);
		}
	}

	public final Resource_declarationContext resource_declaration() throws RecognitionException {
		Resource_declarationContext _localctx = new Resource_declarationContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_resource_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1241); 
			((Resource_declarationContext)_localctx).decl = native_resource_declaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enum_declarationContext extends ParserRuleContext {
		public Enum_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_declaration; }
	 
		public Enum_declarationContext() { }
		public void copyFrom(Enum_declarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EnumCategoryDeclarationContext extends Enum_declarationContext {
		public Enum_category_declarationContext decl;
		public Enum_category_declarationContext enum_category_declaration() {
			return getRuleContext(Enum_category_declarationContext.class,0);
		}
		public EnumCategoryDeclarationContext(Enum_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterEnumCategoryDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitEnumCategoryDeclaration(this);
		}
	}
	public static class EnumNativeDeclarationContext extends Enum_declarationContext {
		public Enum_native_declarationContext decl;
		public Enum_native_declarationContext enum_native_declaration() {
			return getRuleContext(Enum_native_declarationContext.class,0);
		}
		public EnumNativeDeclarationContext(Enum_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterEnumNativeDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitEnumNativeDeclaration(this);
		}
	}

	public final Enum_declarationContext enum_declaration() throws RecognitionException {
		Enum_declarationContext _localctx = new Enum_declarationContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_enum_declaration);
		try {
			setState(1245);
			switch ( getInterpreter().adaptivePredict(_input,82,_ctx) ) {
			case 1:
				_localctx = new EnumCategoryDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1243); 
				((EnumCategoryDeclarationContext)_localctx).decl = enum_category_declaration();
				}
				break;
			case 2:
				_localctx = new EnumNativeDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1244); 
				((EnumNativeDeclarationContext)_localctx).decl = enum_native_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_symbol_listContext extends ParserRuleContext {
		public Native_symbol_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_symbol_list; }
	 
		public Native_symbol_listContext() { }
		public void copyFrom(Native_symbol_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeSymbolListContext extends Native_symbol_listContext {
		public Native_symbolContext item;
		public Native_symbolContext native_symbol() {
			return getRuleContext(Native_symbolContext.class,0);
		}
		public NativeSymbolListContext(Native_symbol_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeSymbolList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeSymbolList(this);
		}
	}
	public static class NativeSymbolListItemContext extends Native_symbol_listContext {
		public Native_symbol_listContext items;
		public Native_symbolContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Native_symbol_listContext native_symbol_list() {
			return getRuleContext(Native_symbol_listContext.class,0);
		}
		public Native_symbolContext native_symbol() {
			return getRuleContext(Native_symbolContext.class,0);
		}
		public NativeSymbolListItemContext(Native_symbol_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeSymbolListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeSymbolListItem(this);
		}
	}

	public final Native_symbol_listContext native_symbol_list() throws RecognitionException {
		return native_symbol_list(0);
	}

	private Native_symbol_listContext native_symbol_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Native_symbol_listContext _localctx = new Native_symbol_listContext(_ctx, _parentState);
		Native_symbol_listContext _prevctx = _localctx;
		int _startState = 132;
		enterRecursionRule(_localctx, 132, RULE_native_symbol_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new NativeSymbolListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1248); 
			((NativeSymbolListContext)_localctx).item = native_symbol();
			}
			_ctx.stop = _input.LT(-1);
			setState(1256);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,83,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new NativeSymbolListItemContext(new Native_symbol_listContext(_parentctx, _parentState));
					((NativeSymbolListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_native_symbol_list);
					setState(1250);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1251); 
					lfp();
					setState(1252); 
					((NativeSymbolListItemContext)_localctx).item = native_symbol();
					}
					} 
				}
				setState(1258);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,83,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Category_symbol_listContext extends ParserRuleContext {
		public Category_symbol_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category_symbol_list; }
	 
		public Category_symbol_listContext() { }
		public void copyFrom(Category_symbol_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CategorySymbolListItemContext extends Category_symbol_listContext {
		public Category_symbol_listContext items;
		public Category_symbolContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Category_symbol_listContext category_symbol_list() {
			return getRuleContext(Category_symbol_listContext.class,0);
		}
		public Category_symbolContext category_symbol() {
			return getRuleContext(Category_symbolContext.class,0);
		}
		public CategorySymbolListItemContext(Category_symbol_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategorySymbolListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategorySymbolListItem(this);
		}
	}
	public static class CategorySymbolListContext extends Category_symbol_listContext {
		public Category_symbolContext item;
		public Category_symbolContext category_symbol() {
			return getRuleContext(Category_symbolContext.class,0);
		}
		public CategorySymbolListContext(Category_symbol_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategorySymbolList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategorySymbolList(this);
		}
	}

	public final Category_symbol_listContext category_symbol_list() throws RecognitionException {
		return category_symbol_list(0);
	}

	private Category_symbol_listContext category_symbol_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Category_symbol_listContext _localctx = new Category_symbol_listContext(_ctx, _parentState);
		Category_symbol_listContext _prevctx = _localctx;
		int _startState = 134;
		enterRecursionRule(_localctx, 134, RULE_category_symbol_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new CategorySymbolListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1260); 
			((CategorySymbolListContext)_localctx).item = category_symbol();
			}
			_ctx.stop = _input.LT(-1);
			setState(1268);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,84,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CategorySymbolListItemContext(new Category_symbol_listContext(_parentctx, _parentState));
					((CategorySymbolListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_category_symbol_list);
					setState(1262);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1263); 
					lfp();
					setState(1264); 
					((CategorySymbolListItemContext)_localctx).item = category_symbol();
					}
					} 
				}
				setState(1270);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,84,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Symbol_listContext extends ParserRuleContext {
		public Symbol_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol_list; }
	 
		public Symbol_listContext() { }
		public void copyFrom(Symbol_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SymbolListContext extends Symbol_listContext {
		public Symbol_identifierContext item;
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public SymbolListContext(Symbol_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSymbolList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSymbolList(this);
		}
	}
	public static class SymbolListItemContext extends Symbol_listContext {
		public Symbol_listContext items;
		public Symbol_identifierContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Symbol_listContext symbol_list() {
			return getRuleContext(Symbol_listContext.class,0);
		}
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public SymbolListItemContext(Symbol_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSymbolListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSymbolListItem(this);
		}
	}

	public final Symbol_listContext symbol_list() throws RecognitionException {
		return symbol_list(0);
	}

	private Symbol_listContext symbol_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Symbol_listContext _localctx = new Symbol_listContext(_ctx, _parentState);
		Symbol_listContext _prevctx = _localctx;
		int _startState = 136;
		enterRecursionRule(_localctx, 136, RULE_symbol_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new SymbolListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1272); 
			((SymbolListContext)_localctx).item = symbol_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(1279);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,85,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SymbolListItemContext(new Symbol_listContext(_parentctx, _parentState));
					((SymbolListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_symbol_list);
					setState(1274);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1275); 
					match(COMMA);
					setState(1276); 
					((SymbolListItemContext)_localctx).item = symbol_identifier();
					}
					} 
				}
				setState(1281);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,85,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Attribute_constraintContext extends ParserRuleContext {
		public Attribute_constraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute_constraint; }
	 
		public Attribute_constraintContext() { }
		public void copyFrom(Attribute_constraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MatchingSetContext extends Attribute_constraintContext {
		public Set_literalContext source;
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public Set_literalContext set_literal() {
			return getRuleContext(Set_literalContext.class,0);
		}
		public MatchingSetContext(Attribute_constraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMatchingSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMatchingSet(this);
		}
	}
	public static class MatchingPatternContext extends Attribute_constraintContext {
		public Token text;
		public TerminalNode MATCHING() { return getToken(OParser.MATCHING, 0); }
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public MatchingPatternContext(Attribute_constraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMatchingPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMatchingPattern(this);
		}
	}
	public static class MatchingListContext extends Attribute_constraintContext {
		public List_literalContext source;
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public List_literalContext list_literal() {
			return getRuleContext(List_literalContext.class,0);
		}
		public MatchingListContext(Attribute_constraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMatchingList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMatchingList(this);
		}
	}
	public static class MatchingRangeContext extends Attribute_constraintContext {
		public Range_literalContext source;
		public TerminalNode IN() { return getToken(OParser.IN, 0); }
		public Range_literalContext range_literal() {
			return getRuleContext(Range_literalContext.class,0);
		}
		public MatchingRangeContext(Attribute_constraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMatchingRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMatchingRange(this);
		}
	}
	public static class MatchingExpressionContext extends Attribute_constraintContext {
		public ExpressionContext exp;
		public TerminalNode MATCHING() { return getToken(OParser.MATCHING, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MatchingExpressionContext(Attribute_constraintContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMatchingExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMatchingExpression(this);
		}
	}

	public final Attribute_constraintContext attribute_constraint() throws RecognitionException {
		Attribute_constraintContext _localctx = new Attribute_constraintContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_attribute_constraint);
		try {
			setState(1292);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				_localctx = new MatchingListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1282); 
				match(IN);
				setState(1283); 
				((MatchingListContext)_localctx).source = list_literal();
				}
				break;
			case 2:
				_localctx = new MatchingSetContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1284); 
				match(IN);
				setState(1285); 
				((MatchingSetContext)_localctx).source = set_literal();
				}
				break;
			case 3:
				_localctx = new MatchingRangeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1286); 
				match(IN);
				setState(1287); 
				((MatchingRangeContext)_localctx).source = range_literal();
				}
				break;
			case 4:
				_localctx = new MatchingPatternContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1288); 
				match(MATCHING);
				setState(1289); 
				((MatchingPatternContext)_localctx).text = match(TEXT_LITERAL);
				}
				break;
			case 5:
				_localctx = new MatchingExpressionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1290); 
				match(MATCHING);
				setState(1291); 
				((MatchingExpressionContext)_localctx).exp = expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class List_literalContext extends ParserRuleContext {
		public Expression_listContext items;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public List_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterList_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitList_literal(this);
		}
	}

	public final List_literalContext list_literal() throws RecognitionException {
		List_literalContext _localctx = new List_literalContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_list_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1294); 
			match(LBRAK);
			setState(1296);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(1295); 
				((List_literalContext)_localctx).items = expression_list(0);
				}
			}

			setState(1298); 
			match(RBRAK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Set_literalContext extends ParserRuleContext {
		public Expression_listContext items;
		public TerminalNode LT() { return getToken(OParser.LT, 0); }
		public TerminalNode GT() { return getToken(OParser.GT, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public Set_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSet_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSet_literal(this);
		}
	}

	public final Set_literalContext set_literal() throws RecognitionException {
		Set_literalContext _localctx = new Set_literalContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_set_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1300); 
			match(LT);
			setState(1302);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(1301); 
				((Set_literalContext)_localctx).items = expression_list(0);
				}
			}

			setState(1304); 
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expression_listContext extends ParserRuleContext {
		public Expression_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_list; }
	 
		public Expression_listContext() { }
		public void copyFrom(Expression_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ValueListContext extends Expression_listContext {
		public ExpressionContext item;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ValueListContext(Expression_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitValueList(this);
		}
	}
	public static class ValueListItemContext extends Expression_listContext {
		public Expression_listContext items;
		public ExpressionContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Expression_listContext expression_list() {
			return getRuleContext(Expression_listContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ValueListItemContext(Expression_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterValueListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitValueListItem(this);
		}
	}

	public final Expression_listContext expression_list() throws RecognitionException {
		return expression_list(0);
	}

	private Expression_listContext expression_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expression_listContext _localctx = new Expression_listContext(_ctx, _parentState);
		Expression_listContext _prevctx = _localctx;
		int _startState = 144;
		enterRecursionRule(_localctx, 144, RULE_expression_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ValueListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1307); 
			((ValueListContext)_localctx).item = expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(1314);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,89,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ValueListItemContext(new Expression_listContext(_parentctx, _parentState));
					((ValueListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression_list);
					setState(1309);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1310); 
					match(COMMA);
					setState(1311); 
					((ValueListItemContext)_localctx).item = expression(0);
					}
					} 
				}
				setState(1316);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,89,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Range_literalContext extends ParserRuleContext {
		public ExpressionContext low;
		public ExpressionContext high;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RANGE() { return getToken(OParser.RANGE, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Range_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_range_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRange_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRange_literal(this);
		}
	}

	public final Range_literalContext range_literal() throws RecognitionException {
		Range_literalContext _localctx = new Range_literalContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_range_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1317); 
			match(LBRAK);
			setState(1318); 
			((Range_literalContext)_localctx).low = expression(0);
			setState(1319); 
			match(RANGE);
			setState(1320); 
			((Range_literalContext)_localctx).high = expression(0);
			setState(1321); 
			match(RBRAK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedefContext extends ParserRuleContext {
		public TypedefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedef; }
	 
		public TypedefContext() { }
		public void copyFrom(TypedefContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SetTypeContext extends TypedefContext {
		public TypedefContext s;
		public TerminalNode LTGT() { return getToken(OParser.LTGT, 0); }
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public SetTypeContext(TypedefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSetType(this);
		}
	}
	public static class ListTypeContext extends TypedefContext {
		public TypedefContext l;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public ListTypeContext(TypedefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterListType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitListType(this);
		}
	}
	public static class DictTypeContext extends TypedefContext {
		public TypedefContext d;
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public DictTypeContext(TypedefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDictType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDictType(this);
		}
	}
	public static class PrimaryTypeContext extends TypedefContext {
		public Primary_typeContext p;
		public Primary_typeContext primary_type() {
			return getRuleContext(Primary_typeContext.class,0);
		}
		public PrimaryTypeContext(TypedefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPrimaryType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPrimaryType(this);
		}
	}

	public final TypedefContext typedef() throws RecognitionException {
		return typedef(0);
	}

	private TypedefContext typedef(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypedefContext _localctx = new TypedefContext(_ctx, _parentState);
		TypedefContext _prevctx = _localctx;
		int _startState = 148;
		enterRecursionRule(_localctx, 148, RULE_typedef, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new PrimaryTypeContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1324); 
			((PrimaryTypeContext)_localctx).p = primary_type();
			}
			_ctx.stop = _input.LT(-1);
			setState(1336);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1334);
					switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
					case 1:
						{
						_localctx = new SetTypeContext(new TypedefContext(_parentctx, _parentState));
						((SetTypeContext)_localctx).s = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_typedef);
						setState(1326);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(1327); 
						match(LTGT);
						}
						break;
					case 2:
						{
						_localctx = new ListTypeContext(new TypedefContext(_parentctx, _parentState));
						((ListTypeContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_typedef);
						setState(1328);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1329); 
						match(LBRAK);
						setState(1330); 
						match(RBRAK);
						}
						break;
					case 3:
						{
						_localctx = new DictTypeContext(new TypedefContext(_parentctx, _parentState));
						((DictTypeContext)_localctx).d = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_typedef);
						setState(1331);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1332); 
						match(LCURL);
						setState(1333); 
						match(RCURL);
						}
						break;
					}
					} 
				}
				setState(1338);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,91,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Primary_typeContext extends ParserRuleContext {
		public Primary_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary_type; }
	 
		public Primary_typeContext() { }
		public void copyFrom(Primary_typeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeTypeContext extends Primary_typeContext {
		public Native_typeContext n;
		public Native_typeContext native_type() {
			return getRuleContext(Native_typeContext.class,0);
		}
		public NativeTypeContext(Primary_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeType(this);
		}
	}
	public static class CategoryTypeContext extends Primary_typeContext {
		public Category_typeContext c;
		public Category_typeContext category_type() {
			return getRuleContext(Category_typeContext.class,0);
		}
		public CategoryTypeContext(Primary_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategoryType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategoryType(this);
		}
	}

	public final Primary_typeContext primary_type() throws RecognitionException {
		Primary_typeContext _localctx = new Primary_typeContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_primary_type);
		try {
			setState(1341);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case CODE:
			case DOCUMENT:
			case BLOB:
			case IMAGE:
			case UUID:
				_localctx = new NativeTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1339); 
				((NativeTypeContext)_localctx).n = native_type();
				}
				break;
			case TYPE_IDENTIFIER:
				_localctx = new CategoryTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1340); 
				((CategoryTypeContext)_localctx).c = category_type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_typeContext extends ParserRuleContext {
		public Native_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_type; }
	 
		public Native_typeContext() { }
		public void copyFrom(Native_typeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PeriodTypeContext extends Native_typeContext {
		public TerminalNode PERIOD() { return getToken(OParser.PERIOD, 0); }
		public PeriodTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPeriodType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPeriodType(this);
		}
	}
	public static class BooleanTypeContext extends Native_typeContext {
		public TerminalNode BOOLEAN() { return getToken(OParser.BOOLEAN, 0); }
		public BooleanTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterBooleanType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitBooleanType(this);
		}
	}
	public static class DocumentTypeContext extends Native_typeContext {
		public TerminalNode DOCUMENT() { return getToken(OParser.DOCUMENT, 0); }
		public DocumentTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDocumentType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDocumentType(this);
		}
	}
	public static class CharacterTypeContext extends Native_typeContext {
		public TerminalNode CHARACTER() { return getToken(OParser.CHARACTER, 0); }
		public CharacterTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCharacterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCharacterType(this);
		}
	}
	public static class TextTypeContext extends Native_typeContext {
		public TerminalNode TEXT() { return getToken(OParser.TEXT, 0); }
		public TextTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTextType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTextType(this);
		}
	}
	public static class ImageTypeContext extends Native_typeContext {
		public TerminalNode IMAGE() { return getToken(OParser.IMAGE, 0); }
		public ImageTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterImageType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitImageType(this);
		}
	}
	public static class TimeTypeContext extends Native_typeContext {
		public TerminalNode TIME() { return getToken(OParser.TIME, 0); }
		public TimeTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTimeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTimeType(this);
		}
	}
	public static class IntegerTypeContext extends Native_typeContext {
		public TerminalNode INTEGER() { return getToken(OParser.INTEGER, 0); }
		public IntegerTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIntegerType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIntegerType(this);
		}
	}
	public static class DateTimeTypeContext extends Native_typeContext {
		public TerminalNode DATETIME() { return getToken(OParser.DATETIME, 0); }
		public DateTimeTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDateTimeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDateTimeType(this);
		}
	}
	public static class BlobTypeContext extends Native_typeContext {
		public TerminalNode BLOB() { return getToken(OParser.BLOB, 0); }
		public BlobTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterBlobType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitBlobType(this);
		}
	}
	public static class UUIDTypeContext extends Native_typeContext {
		public TerminalNode UUID() { return getToken(OParser.UUID, 0); }
		public UUIDTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterUUIDType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitUUIDType(this);
		}
	}
	public static class DecimalTypeContext extends Native_typeContext {
		public TerminalNode DECIMAL() { return getToken(OParser.DECIMAL, 0); }
		public DecimalTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDecimalType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDecimalType(this);
		}
	}
	public static class CodeTypeContext extends Native_typeContext {
		public TerminalNode CODE() { return getToken(OParser.CODE, 0); }
		public CodeTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCodeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCodeType(this);
		}
	}
	public static class DateTypeContext extends Native_typeContext {
		public TerminalNode DATE() { return getToken(OParser.DATE, 0); }
		public DateTypeContext(Native_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDateType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDateType(this);
		}
	}

	public final Native_typeContext native_type() throws RecognitionException {
		Native_typeContext _localctx = new Native_typeContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_native_type);
		try {
			setState(1357);
			switch (_input.LA(1)) {
			case BOOLEAN:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1343); 
				match(BOOLEAN);
				}
				break;
			case CHARACTER:
				_localctx = new CharacterTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1344); 
				match(CHARACTER);
				}
				break;
			case TEXT:
				_localctx = new TextTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1345); 
				match(TEXT);
				}
				break;
			case IMAGE:
				_localctx = new ImageTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1346); 
				match(IMAGE);
				}
				break;
			case INTEGER:
				_localctx = new IntegerTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1347); 
				match(INTEGER);
				}
				break;
			case DECIMAL:
				_localctx = new DecimalTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1348); 
				match(DECIMAL);
				}
				break;
			case DOCUMENT:
				_localctx = new DocumentTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1349); 
				match(DOCUMENT);
				}
				break;
			case DATE:
				_localctx = new DateTypeContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(1350); 
				match(DATE);
				}
				break;
			case DATETIME:
				_localctx = new DateTimeTypeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(1351); 
				match(DATETIME);
				}
				break;
			case TIME:
				_localctx = new TimeTypeContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(1352); 
				match(TIME);
				}
				break;
			case PERIOD:
				_localctx = new PeriodTypeContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(1353); 
				match(PERIOD);
				}
				break;
			case CODE:
				_localctx = new CodeTypeContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(1354); 
				match(CODE);
				}
				break;
			case BLOB:
				_localctx = new BlobTypeContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(1355); 
				match(BLOB);
				}
				break;
			case UUID:
				_localctx = new UUIDTypeContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(1356); 
				match(UUID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Category_typeContext extends ParserRuleContext {
		public Token t1;
		public TerminalNode TYPE_IDENTIFIER() { return getToken(OParser.TYPE_IDENTIFIER, 0); }
		public Category_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategory_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategory_type(this);
		}
	}

	public final Category_typeContext category_type() throws RecognitionException {
		Category_typeContext _localctx = new Category_typeContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_category_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1359); 
			((Category_typeContext)_localctx).t1 = match(TYPE_IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Code_typeContext extends ParserRuleContext {
		public Token t1;
		public TerminalNode CODE() { return getToken(OParser.CODE, 0); }
		public Code_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCode_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCode_type(this);
		}
	}

	public final Code_typeContext code_type() throws RecognitionException {
		Code_typeContext _localctx = new Code_typeContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_code_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1361); 
			((Code_typeContext)_localctx).t1 = match(CODE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Category_declarationContext extends ParserRuleContext {
		public Category_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category_declaration; }
	 
		public Category_declarationContext() { }
		public void copyFrom(Category_declarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConcreteCategoryDeclarationContext extends Category_declarationContext {
		public Concrete_category_declarationContext decl;
		public Concrete_category_declarationContext concrete_category_declaration() {
			return getRuleContext(Concrete_category_declarationContext.class,0);
		}
		public ConcreteCategoryDeclarationContext(Category_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterConcreteCategoryDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitConcreteCategoryDeclaration(this);
		}
	}
	public static class NativeCategoryDeclarationContext extends Category_declarationContext {
		public Native_category_declarationContext decl;
		public Native_category_declarationContext native_category_declaration() {
			return getRuleContext(Native_category_declarationContext.class,0);
		}
		public NativeCategoryDeclarationContext(Category_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeCategoryDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeCategoryDeclaration(this);
		}
	}
	public static class SingletonCategoryDeclarationContext extends Category_declarationContext {
		public Singleton_category_declarationContext decl;
		public Singleton_category_declarationContext singleton_category_declaration() {
			return getRuleContext(Singleton_category_declarationContext.class,0);
		}
		public SingletonCategoryDeclarationContext(Category_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSingletonCategoryDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSingletonCategoryDeclaration(this);
		}
	}

	public final Category_declarationContext category_declaration() throws RecognitionException {
		Category_declarationContext _localctx = new Category_declarationContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_category_declaration);
		try {
			setState(1366);
			switch ( getInterpreter().adaptivePredict(_input,94,_ctx) ) {
			case 1:
				_localctx = new ConcreteCategoryDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1363); 
				((ConcreteCategoryDeclarationContext)_localctx).decl = concrete_category_declaration();
				}
				break;
			case 2:
				_localctx = new NativeCategoryDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1364); 
				((NativeCategoryDeclarationContext)_localctx).decl = native_category_declaration();
				}
				break;
			case 3:
				_localctx = new SingletonCategoryDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1365); 
				((SingletonCategoryDeclarationContext)_localctx).decl = singleton_category_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_identifier_listContext extends ParserRuleContext {
		public Type_identifier_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_identifier_list; }
	 
		public Type_identifier_listContext() { }
		public void copyFrom(Type_identifier_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TypeIdentifierListContext extends Type_identifier_listContext {
		public Type_identifierContext item;
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public TypeIdentifierListContext(Type_identifier_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTypeIdentifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTypeIdentifierList(this);
		}
	}
	public static class TypeIdentifierListItemContext extends Type_identifier_listContext {
		public Type_identifier_listContext items;
		public Type_identifierContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Type_identifier_listContext type_identifier_list() {
			return getRuleContext(Type_identifier_listContext.class,0);
		}
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public TypeIdentifierListItemContext(Type_identifier_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTypeIdentifierListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTypeIdentifierListItem(this);
		}
	}

	public final Type_identifier_listContext type_identifier_list() throws RecognitionException {
		return type_identifier_list(0);
	}

	private Type_identifier_listContext type_identifier_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Type_identifier_listContext _localctx = new Type_identifier_listContext(_ctx, _parentState);
		Type_identifier_listContext _prevctx = _localctx;
		int _startState = 160;
		enterRecursionRule(_localctx, 160, RULE_type_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new TypeIdentifierListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1369); 
			((TypeIdentifierListContext)_localctx).item = type_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(1376);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,95,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeIdentifierListItemContext(new Type_identifier_listContext(_parentctx, _parentState));
					((TypeIdentifierListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_type_identifier_list);
					setState(1371);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1372); 
					match(COMMA);
					setState(1373); 
					((TypeIdentifierListItemContext)_localctx).item = type_identifier();
					}
					} 
				}
				setState(1378);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,95,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Method_identifierContext extends ParserRuleContext {
		public Method_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_identifier; }
	 
		public Method_identifierContext() { }
		public void copyFrom(Method_identifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MethodVariableIdentifierContext extends Method_identifierContext {
		public Variable_identifierContext name;
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public MethodVariableIdentifierContext(Method_identifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodVariableIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodVariableIdentifier(this);
		}
	}
	public static class MethodTypeIdentifierContext extends Method_identifierContext {
		public Type_identifierContext name;
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public MethodTypeIdentifierContext(Method_identifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMethodTypeIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMethodTypeIdentifier(this);
		}
	}

	public final Method_identifierContext method_identifier() throws RecognitionException {
		Method_identifierContext _localctx = new Method_identifierContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_method_identifier);
		try {
			setState(1381);
			switch (_input.LA(1)) {
			case VARIABLE_IDENTIFIER:
				_localctx = new MethodVariableIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1379); 
				((MethodVariableIdentifierContext)_localctx).name = variable_identifier();
				}
				break;
			case TYPE_IDENTIFIER:
				_localctx = new MethodTypeIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1380); 
				((MethodTypeIdentifierContext)_localctx).name = type_identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
	 
		public IdentifierContext() { }
		public void copyFrom(IdentifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TypeIdentifierContext extends IdentifierContext {
		public Type_identifierContext name;
		public Type_identifierContext type_identifier() {
			return getRuleContext(Type_identifierContext.class,0);
		}
		public TypeIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTypeIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTypeIdentifier(this);
		}
	}
	public static class SymbolIdentifierContext extends IdentifierContext {
		public Symbol_identifierContext name;
		public Symbol_identifierContext symbol_identifier() {
			return getRuleContext(Symbol_identifierContext.class,0);
		}
		public SymbolIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSymbolIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSymbolIdentifier(this);
		}
	}
	public static class VariableIdentifierContext extends IdentifierContext {
		public Variable_identifierContext name;
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public VariableIdentifierContext(IdentifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterVariableIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitVariableIdentifier(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_identifier);
		try {
			setState(1386);
			switch (_input.LA(1)) {
			case VARIABLE_IDENTIFIER:
				_localctx = new VariableIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1383); 
				((VariableIdentifierContext)_localctx).name = variable_identifier();
				}
				break;
			case TYPE_IDENTIFIER:
				_localctx = new TypeIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1384); 
				((TypeIdentifierContext)_localctx).name = type_identifier();
				}
				break;
			case SYMBOL_IDENTIFIER:
				_localctx = new SymbolIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1385); 
				((SymbolIdentifierContext)_localctx).name = symbol_identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Variable_identifierContext extends ParserRuleContext {
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Variable_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterVariable_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitVariable_identifier(this);
		}
	}

	public final Variable_identifierContext variable_identifier() throws RecognitionException {
		Variable_identifierContext _localctx = new Variable_identifierContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_variable_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1388); 
			match(VARIABLE_IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Type_identifierContext extends ParserRuleContext {
		public TerminalNode TYPE_IDENTIFIER() { return getToken(OParser.TYPE_IDENTIFIER, 0); }
		public Type_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterType_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitType_identifier(this);
		}
	}

	public final Type_identifierContext type_identifier() throws RecognitionException {
		Type_identifierContext _localctx = new Type_identifierContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_type_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1390); 
			match(TYPE_IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Symbol_identifierContext extends ParserRuleContext {
		public TerminalNode SYMBOL_IDENTIFIER() { return getToken(OParser.SYMBOL_IDENTIFIER, 0); }
		public Symbol_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbol_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSymbol_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSymbol_identifier(this);
		}
	}

	public final Symbol_identifierContext symbol_identifier() throws RecognitionException {
		Symbol_identifierContext _localctx = new Symbol_identifierContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_symbol_identifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1392); 
			match(SYMBOL_IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Argument_listContext extends ParserRuleContext {
		public Argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_list; }
	 
		public Argument_listContext() { }
		public void copyFrom(Argument_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArgumentListItemContext extends Argument_listContext {
		public Argument_listContext items;
		public ArgumentContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public ArgumentListItemContext(Argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterArgumentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitArgumentListItem(this);
		}
	}
	public static class ArgumentListContext extends Argument_listContext {
		public ArgumentContext item;
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public ArgumentListContext(Argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitArgumentList(this);
		}
	}

	public final Argument_listContext argument_list() throws RecognitionException {
		return argument_list(0);
	}

	private Argument_listContext argument_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Argument_listContext _localctx = new Argument_listContext(_ctx, _parentState);
		Argument_listContext _prevctx = _localctx;
		int _startState = 172;
		enterRecursionRule(_localctx, 172, RULE_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ArgumentListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1395); 
			((ArgumentListContext)_localctx).item = argument();
			}
			_ctx.stop = _input.LT(-1);
			setState(1402);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,98,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArgumentListItemContext(new Argument_listContext(_parentctx, _parentState));
					((ArgumentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_argument_list);
					setState(1397);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1398); 
					match(COMMA);
					setState(1399); 
					((ArgumentListItemContext)_localctx).item = argument();
					}
					} 
				}
				setState(1404);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,98,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
	 
		public ArgumentContext() { }
		public void copyFrom(ArgumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OperatorArgumentContext extends ArgumentContext {
		public Operator_argumentContext arg;
		public Operator_argumentContext operator_argument() {
			return getRuleContext(Operator_argumentContext.class,0);
		}
		public TerminalNode MUTABLE() { return getToken(OParser.MUTABLE, 0); }
		public OperatorArgumentContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorArgument(this);
		}
	}
	public static class CodeArgumentContext extends ArgumentContext {
		public Code_argumentContext arg;
		public Code_argumentContext code_argument() {
			return getRuleContext(Code_argumentContext.class,0);
		}
		public CodeArgumentContext(ArgumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCodeArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCodeArgument(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_argument);
		int _la;
		try {
			setState(1410);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				_localctx = new CodeArgumentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1405); 
				((CodeArgumentContext)_localctx).arg = code_argument();
				}
				break;
			case 2:
				_localctx = new OperatorArgumentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1407);
				_la = _input.LA(1);
				if (_la==MUTABLE) {
					{
					setState(1406); 
					match(MUTABLE);
					}
				}

				setState(1409); 
				((OperatorArgumentContext)_localctx).arg = operator_argument();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Operator_argumentContext extends ParserRuleContext {
		public Operator_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator_argument; }
	 
		public Operator_argumentContext() { }
		public void copyFrom(Operator_argumentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TypedArgumentContext extends Operator_argumentContext {
		public Typed_argumentContext arg;
		public Typed_argumentContext typed_argument() {
			return getRuleContext(Typed_argumentContext.class,0);
		}
		public TypedArgumentContext(Operator_argumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTypedArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTypedArgument(this);
		}
	}
	public static class NamedArgumentContext extends Operator_argumentContext {
		public Named_argumentContext arg;
		public Named_argumentContext named_argument() {
			return getRuleContext(Named_argumentContext.class,0);
		}
		public NamedArgumentContext(Operator_argumentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNamedArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNamedArgument(this);
		}
	}

	public final Operator_argumentContext operator_argument() throws RecognitionException {
		Operator_argumentContext _localctx = new Operator_argumentContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_operator_argument);
		try {
			setState(1414);
			switch (_input.LA(1)) {
			case VARIABLE_IDENTIFIER:
				_localctx = new NamedArgumentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1412); 
				((NamedArgumentContext)_localctx).arg = named_argument();
				}
				break;
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case CODE:
			case DOCUMENT:
			case BLOB:
			case IMAGE:
			case UUID:
			case ANY:
			case TYPE_IDENTIFIER:
				_localctx = new TypedArgumentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1413); 
				((TypedArgumentContext)_localctx).arg = typed_argument();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Named_argumentContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public Literal_expressionContext value;
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public Literal_expressionContext literal_expression() {
			return getRuleContext(Literal_expressionContext.class,0);
		}
		public Named_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_named_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNamed_argument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNamed_argument(this);
		}
	}

	public final Named_argumentContext named_argument() throws RecognitionException {
		Named_argumentContext _localctx = new Named_argumentContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_named_argument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1416); 
			((Named_argumentContext)_localctx).name = variable_identifier();
			setState(1419);
			switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
			case 1:
				{
				setState(1417); 
				match(EQ);
				setState(1418); 
				((Named_argumentContext)_localctx).value = literal_expression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Code_argumentContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public Code_typeContext code_type() {
			return getRuleContext(Code_typeContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public Code_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCode_argument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCode_argument(this);
		}
	}

	public final Code_argumentContext code_argument() throws RecognitionException {
		Code_argumentContext _localctx = new Code_argumentContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_code_argument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1421); 
			code_type();
			setState(1422); 
			((Code_argumentContext)_localctx).name = variable_identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Category_or_any_typeContext extends ParserRuleContext {
		public Category_or_any_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_category_or_any_type; }
	 
		public Category_or_any_typeContext() { }
		public void copyFrom(Category_or_any_typeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AnyArgumentTypeContext extends Category_or_any_typeContext {
		public Any_typeContext typ;
		public Any_typeContext any_type() {
			return getRuleContext(Any_typeContext.class,0);
		}
		public AnyArgumentTypeContext(Category_or_any_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAnyArgumentType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAnyArgumentType(this);
		}
	}
	public static class CategoryArgumentTypeContext extends Category_or_any_typeContext {
		public TypedefContext typ;
		public TypedefContext typedef() {
			return getRuleContext(TypedefContext.class,0);
		}
		public CategoryArgumentTypeContext(Category_or_any_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategoryArgumentType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategoryArgumentType(this);
		}
	}

	public final Category_or_any_typeContext category_or_any_type() throws RecognitionException {
		Category_or_any_typeContext _localctx = new Category_or_any_typeContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_category_or_any_type);
		try {
			setState(1426);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case CODE:
			case DOCUMENT:
			case BLOB:
			case IMAGE:
			case UUID:
			case TYPE_IDENTIFIER:
				_localctx = new CategoryArgumentTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1424); 
				((CategoryArgumentTypeContext)_localctx).typ = typedef(0);
				}
				break;
			case ANY:
				_localctx = new AnyArgumentTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1425); 
				((AnyArgumentTypeContext)_localctx).typ = any_type(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Any_typeContext extends ParserRuleContext {
		public Any_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_any_type; }
	 
		public Any_typeContext() { }
		public void copyFrom(Any_typeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AnyListTypeContext extends Any_typeContext {
		public Any_typeContext typ;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Any_typeContext any_type() {
			return getRuleContext(Any_typeContext.class,0);
		}
		public AnyListTypeContext(Any_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAnyListType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAnyListType(this);
		}
	}
	public static class AnyTypeContext extends Any_typeContext {
		public TerminalNode ANY() { return getToken(OParser.ANY, 0); }
		public AnyTypeContext(Any_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAnyType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAnyType(this);
		}
	}
	public static class AnyDictTypeContext extends Any_typeContext {
		public Any_typeContext typ;
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Any_typeContext any_type() {
			return getRuleContext(Any_typeContext.class,0);
		}
		public AnyDictTypeContext(Any_typeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAnyDictType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAnyDictType(this);
		}
	}

	public final Any_typeContext any_type() throws RecognitionException {
		return any_type(0);
	}

	private Any_typeContext any_type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Any_typeContext _localctx = new Any_typeContext(_ctx, _parentState);
		Any_typeContext _prevctx = _localctx;
		int _startState = 184;
		enterRecursionRule(_localctx, 184, RULE_any_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AnyTypeContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1429); 
			match(ANY);
			}
			_ctx.stop = _input.LT(-1);
			setState(1439);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(1437);
					switch ( getInterpreter().adaptivePredict(_input,104,_ctx) ) {
					case 1:
						{
						_localctx = new AnyListTypeContext(new Any_typeContext(_parentctx, _parentState));
						((AnyListTypeContext)_localctx).typ = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_any_type);
						setState(1431);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(1432); 
						match(LBRAK);
						setState(1433); 
						match(RBRAK);
						}
						break;
					case 2:
						{
						_localctx = new AnyDictTypeContext(new Any_typeContext(_parentctx, _parentState));
						((AnyDictTypeContext)_localctx).typ = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_any_type);
						setState(1434);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(1435); 
						match(LCURL);
						setState(1436); 
						match(RCURL);
						}
						break;
					}
					} 
				}
				setState(1441);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Member_method_declaration_listContext extends ParserRuleContext {
		public Member_method_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member_method_declaration_list; }
	 
		public Member_method_declaration_listContext() { }
		public void copyFrom(Member_method_declaration_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CategoryMethodListItemContext extends Member_method_declaration_listContext {
		public Member_method_declaration_listContext items;
		public Member_method_declarationContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Member_method_declaration_listContext member_method_declaration_list() {
			return getRuleContext(Member_method_declaration_listContext.class,0);
		}
		public Member_method_declarationContext member_method_declaration() {
			return getRuleContext(Member_method_declarationContext.class,0);
		}
		public CategoryMethodListItemContext(Member_method_declaration_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategoryMethodListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategoryMethodListItem(this);
		}
	}
	public static class CategoryMethodListContext extends Member_method_declaration_listContext {
		public Member_method_declarationContext item;
		public Member_method_declarationContext member_method_declaration() {
			return getRuleContext(Member_method_declarationContext.class,0);
		}
		public CategoryMethodListContext(Member_method_declaration_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCategoryMethodList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCategoryMethodList(this);
		}
	}

	public final Member_method_declaration_listContext member_method_declaration_list() throws RecognitionException {
		return member_method_declaration_list(0);
	}

	private Member_method_declaration_listContext member_method_declaration_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Member_method_declaration_listContext _localctx = new Member_method_declaration_listContext(_ctx, _parentState);
		Member_method_declaration_listContext _prevctx = _localctx;
		int _startState = 186;
		enterRecursionRule(_localctx, 186, RULE_member_method_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new CategoryMethodListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1443); 
			((CategoryMethodListContext)_localctx).item = member_method_declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(1451);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CategoryMethodListItemContext(new Member_method_declaration_listContext(_parentctx, _parentState));
					((CategoryMethodListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_member_method_declaration_list);
					setState(1445);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1446); 
					lfp();
					setState(1447); 
					((CategoryMethodListItemContext)_localctx).item = member_method_declaration();
					}
					} 
				}
				setState(1453);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,106,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Member_method_declarationContext extends ParserRuleContext {
		public Setter_method_declarationContext setter_method_declaration() {
			return getRuleContext(Setter_method_declarationContext.class,0);
		}
		public Getter_method_declarationContext getter_method_declaration() {
			return getRuleContext(Getter_method_declarationContext.class,0);
		}
		public Concrete_method_declarationContext concrete_method_declaration() {
			return getRuleContext(Concrete_method_declarationContext.class,0);
		}
		public Abstract_method_declarationContext abstract_method_declaration() {
			return getRuleContext(Abstract_method_declarationContext.class,0);
		}
		public Operator_method_declarationContext operator_method_declaration() {
			return getRuleContext(Operator_method_declarationContext.class,0);
		}
		public Member_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMember_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMember_method_declaration(this);
		}
	}

	public final Member_method_declarationContext member_method_declaration() throws RecognitionException {
		Member_method_declarationContext _localctx = new Member_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_member_method_declaration);
		try {
			setState(1459);
			switch ( getInterpreter().adaptivePredict(_input,107,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1454); 
				setter_method_declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1455); 
				getter_method_declaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1456); 
				concrete_method_declaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1457); 
				abstract_method_declaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1458); 
				operator_method_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_member_method_declaration_listContext extends ParserRuleContext {
		public Native_member_method_declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_member_method_declaration_list; }
	 
		public Native_member_method_declaration_listContext() { }
		public void copyFrom(Native_member_method_declaration_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeCategoryMethodListContext extends Native_member_method_declaration_listContext {
		public Native_member_method_declarationContext item;
		public Native_member_method_declarationContext native_member_method_declaration() {
			return getRuleContext(Native_member_method_declarationContext.class,0);
		}
		public NativeCategoryMethodListContext(Native_member_method_declaration_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeCategoryMethodList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeCategoryMethodList(this);
		}
	}
	public static class NativeCategoryMethodListItemContext extends Native_member_method_declaration_listContext {
		public Native_member_method_declaration_listContext items;
		public Native_member_method_declarationContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Native_member_method_declaration_listContext native_member_method_declaration_list() {
			return getRuleContext(Native_member_method_declaration_listContext.class,0);
		}
		public Native_member_method_declarationContext native_member_method_declaration() {
			return getRuleContext(Native_member_method_declarationContext.class,0);
		}
		public NativeCategoryMethodListItemContext(Native_member_method_declaration_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeCategoryMethodListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeCategoryMethodListItem(this);
		}
	}

	public final Native_member_method_declaration_listContext native_member_method_declaration_list() throws RecognitionException {
		return native_member_method_declaration_list(0);
	}

	private Native_member_method_declaration_listContext native_member_method_declaration_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Native_member_method_declaration_listContext _localctx = new Native_member_method_declaration_listContext(_ctx, _parentState);
		Native_member_method_declaration_listContext _prevctx = _localctx;
		int _startState = 190;
		enterRecursionRule(_localctx, 190, RULE_native_member_method_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new NativeCategoryMethodListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1462); 
			((NativeCategoryMethodListContext)_localctx).item = native_member_method_declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(1470);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,108,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new NativeCategoryMethodListItemContext(new Native_member_method_declaration_listContext(_parentctx, _parentState));
					((NativeCategoryMethodListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_native_member_method_declaration_list);
					setState(1464);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1465); 
					lfp();
					setState(1466); 
					((NativeCategoryMethodListItemContext)_localctx).item = native_member_method_declaration();
					}
					} 
				}
				setState(1472);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,108,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Native_member_method_declarationContext extends ParserRuleContext {
		public Member_method_declarationContext member_method_declaration() {
			return getRuleContext(Member_method_declarationContext.class,0);
		}
		public Native_method_declarationContext native_method_declaration() {
			return getRuleContext(Native_method_declarationContext.class,0);
		}
		public Native_member_method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_member_method_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNative_member_method_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNative_member_method_declaration(this);
		}
	}

	public final Native_member_method_declarationContext native_member_method_declaration() throws RecognitionException {
		Native_member_method_declarationContext _localctx = new Native_member_method_declarationContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_native_member_method_declaration);
		try {
			setState(1475);
			switch ( getInterpreter().adaptivePredict(_input,109,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1473); 
				member_method_declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1474); 
				native_method_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_category_bindingContext extends ParserRuleContext {
		public Native_category_bindingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_category_binding; }
	 
		public Native_category_bindingContext() { }
		public void copyFrom(Native_category_bindingContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Python2CategoryBindingContext extends Native_category_bindingContext {
		public Python_category_bindingContext binding;
		public TerminalNode PYTHON2() { return getToken(OParser.PYTHON2, 0); }
		public Python_category_bindingContext python_category_binding() {
			return getRuleContext(Python_category_bindingContext.class,0);
		}
		public Python2CategoryBindingContext(Native_category_bindingContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython2CategoryBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython2CategoryBinding(this);
		}
	}
	public static class Python3CategoryBindingContext extends Native_category_bindingContext {
		public Python_category_bindingContext binding;
		public TerminalNode PYTHON3() { return getToken(OParser.PYTHON3, 0); }
		public Python_category_bindingContext python_category_binding() {
			return getRuleContext(Python_category_bindingContext.class,0);
		}
		public Python3CategoryBindingContext(Native_category_bindingContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython3CategoryBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython3CategoryBinding(this);
		}
	}
	public static class JavaCategoryBindingContext extends Native_category_bindingContext {
		public Java_class_identifier_expressionContext binding;
		public TerminalNode JAVA() { return getToken(OParser.JAVA, 0); }
		public Java_class_identifier_expressionContext java_class_identifier_expression() {
			return getRuleContext(Java_class_identifier_expressionContext.class,0);
		}
		public JavaCategoryBindingContext(Native_category_bindingContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaCategoryBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaCategoryBinding(this);
		}
	}
	public static class CSharpCategoryBindingContext extends Native_category_bindingContext {
		public Csharp_identifier_expressionContext binding;
		public TerminalNode CSHARP() { return getToken(OParser.CSHARP, 0); }
		public Csharp_identifier_expressionContext csharp_identifier_expression() {
			return getRuleContext(Csharp_identifier_expressionContext.class,0);
		}
		public CSharpCategoryBindingContext(Native_category_bindingContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpCategoryBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpCategoryBinding(this);
		}
	}
	public static class JavaScriptCategoryBindingContext extends Native_category_bindingContext {
		public Javascript_category_bindingContext binding;
		public TerminalNode JAVASCRIPT() { return getToken(OParser.JAVASCRIPT, 0); }
		public Javascript_category_bindingContext javascript_category_binding() {
			return getRuleContext(Javascript_category_bindingContext.class,0);
		}
		public JavaScriptCategoryBindingContext(Native_category_bindingContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaScriptCategoryBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaScriptCategoryBinding(this);
		}
	}

	public final Native_category_bindingContext native_category_binding() throws RecognitionException {
		Native_category_bindingContext _localctx = new Native_category_bindingContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_native_category_binding);
		try {
			setState(1487);
			switch (_input.LA(1)) {
			case JAVA:
				_localctx = new JavaCategoryBindingContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1477); 
				match(JAVA);
				setState(1478); 
				((JavaCategoryBindingContext)_localctx).binding = java_class_identifier_expression(0);
				}
				break;
			case CSHARP:
				_localctx = new CSharpCategoryBindingContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1479); 
				match(CSHARP);
				setState(1480); 
				((CSharpCategoryBindingContext)_localctx).binding = csharp_identifier_expression(0);
				}
				break;
			case PYTHON2:
				_localctx = new Python2CategoryBindingContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1481); 
				match(PYTHON2);
				setState(1482); 
				((Python2CategoryBindingContext)_localctx).binding = python_category_binding();
				}
				break;
			case PYTHON3:
				_localctx = new Python3CategoryBindingContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1483); 
				match(PYTHON3);
				setState(1484); 
				((Python3CategoryBindingContext)_localctx).binding = python_category_binding();
				}
				break;
			case JAVASCRIPT:
				_localctx = new JavaScriptCategoryBindingContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1485); 
				match(JAVASCRIPT);
				setState(1486); 
				((JavaScriptCategoryBindingContext)_localctx).binding = javascript_category_binding();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_category_bindingContext extends ParserRuleContext {
		public IdentifierContext id_;
		public Python_moduleContext module;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public Python_moduleContext python_module() {
			return getRuleContext(Python_moduleContext.class,0);
		}
		public Python_category_bindingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_category_binding; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython_category_binding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython_category_binding(this);
		}
	}

	public final Python_category_bindingContext python_category_binding() throws RecognitionException {
		Python_category_bindingContext _localctx = new Python_category_bindingContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_python_category_binding);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1489); 
			((Python_category_bindingContext)_localctx).id_ = identifier();
			setState(1491);
			_la = _input.LA(1);
			if (_la==FROM) {
				{
				setState(1490); 
				((Python_category_bindingContext)_localctx).module = python_module();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_moduleContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(OParser.FROM, 0); }
		public Module_tokenContext module_token() {
			return getRuleContext(Module_tokenContext.class,0);
		}
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(OParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(OParser.DOT, i);
		}
		public Python_moduleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_module; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython_module(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython_module(this);
		}
	}

	public final Python_moduleContext python_module() throws RecognitionException {
		Python_moduleContext _localctx = new Python_moduleContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_python_module);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1493); 
			match(FROM);
			setState(1494); 
			module_token();
			setState(1495); 
			match(COLON);
			setState(1496); 
			identifier();
			setState(1501);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,112,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1497); 
					match(DOT);
					setState(1498); 
					identifier();
					}
					} 
				}
				setState(1503);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,112,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Module_tokenContext extends ParserRuleContext {
		public Token i1;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Module_tokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterModule_token(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitModule_token(this);
		}
	}

	public final Module_tokenContext module_token() throws RecognitionException {
		Module_tokenContext _localctx = new Module_tokenContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_module_token);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1504); 
			((Module_tokenContext)_localctx).i1 = match(VARIABLE_IDENTIFIER);
			setState(1505);
			if (!(this.isText(((Module_tokenContext)_localctx).i1,"module"))) throw new FailedPredicateException(this, "$parser.isText($i1,\"module\")");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_category_bindingContext extends ParserRuleContext {
		public IdentifierContext id_;
		public Javascript_moduleContext module;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public Javascript_moduleContext javascript_module() {
			return getRuleContext(Javascript_moduleContext.class,0);
		}
		public Javascript_category_bindingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_category_binding; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_category_binding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_category_binding(this);
		}
	}

	public final Javascript_category_bindingContext javascript_category_binding() throws RecognitionException {
		Javascript_category_bindingContext _localctx = new Javascript_category_bindingContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_javascript_category_binding);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1507); 
			((Javascript_category_bindingContext)_localctx).id_ = identifier();
			setState(1509);
			_la = _input.LA(1);
			if (_la==FROM) {
				{
				setState(1508); 
				((Javascript_category_bindingContext)_localctx).module = javascript_module();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_moduleContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(OParser.FROM, 0); }
		public Module_tokenContext module_token() {
			return getRuleContext(Module_tokenContext.class,0);
		}
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public List<Javascript_identifierContext> javascript_identifier() {
			return getRuleContexts(Javascript_identifierContext.class);
		}
		public Javascript_identifierContext javascript_identifier(int i) {
			return getRuleContext(Javascript_identifierContext.class,i);
		}
		public List<TerminalNode> SLASH() { return getTokens(OParser.SLASH); }
		public TerminalNode SLASH(int i) {
			return getToken(OParser.SLASH, i);
		}
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Javascript_moduleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_module; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_module(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_module(this);
		}
	}

	public final Javascript_moduleContext javascript_module() throws RecognitionException {
		Javascript_moduleContext _localctx = new Javascript_moduleContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_javascript_module);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1511); 
			match(FROM);
			setState(1512); 
			module_token();
			setState(1513); 
			match(COLON);
			setState(1515);
			_la = _input.LA(1);
			if (_la==SLASH) {
				{
				setState(1514); 
				match(SLASH);
				}
			}

			setState(1517); 
			javascript_identifier();
			setState(1522);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1518); 
					match(SLASH);
					setState(1519); 
					javascript_identifier();
					}
					} 
				}
				setState(1524);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,115,_ctx);
			}
			setState(1527);
			switch ( getInterpreter().adaptivePredict(_input,116,_ctx) ) {
			case 1:
				{
				setState(1525); 
				match(DOT);
				setState(1526); 
				javascript_identifier();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Variable_identifier_listContext extends ParserRuleContext {
		public Variable_identifier_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_identifier_list; }
	 
		public Variable_identifier_listContext() { }
		public void copyFrom(Variable_identifier_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VariableListContext extends Variable_identifier_listContext {
		public Variable_identifierContext item;
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public VariableListContext(Variable_identifier_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterVariableList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitVariableList(this);
		}
	}
	public static class VariableListItemContext extends Variable_identifier_listContext {
		public Variable_identifier_listContext items;
		public Variable_identifierContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Variable_identifier_listContext variable_identifier_list() {
			return getRuleContext(Variable_identifier_listContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public VariableListItemContext(Variable_identifier_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterVariableListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitVariableListItem(this);
		}
	}

	public final Variable_identifier_listContext variable_identifier_list() throws RecognitionException {
		return variable_identifier_list(0);
	}

	private Variable_identifier_listContext variable_identifier_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Variable_identifier_listContext _localctx = new Variable_identifier_listContext(_ctx, _parentState);
		Variable_identifier_listContext _prevctx = _localctx;
		int _startState = 206;
		enterRecursionRule(_localctx, 206, RULE_variable_identifier_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new VariableListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1530); 
			((VariableListContext)_localctx).item = variable_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(1537);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,117,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new VariableListItemContext(new Variable_identifier_listContext(_parentctx, _parentState));
					((VariableListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_variable_identifier_list);
					setState(1532);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1533); 
					match(COMMA);
					setState(1534); 
					((VariableListItemContext)_localctx).item = variable_identifier();
					}
					} 
				}
				setState(1539);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,117,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Method_declarationContext extends ParserRuleContext {
		public Method_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_declaration; }
	 
		public Method_declarationContext() { }
		public void copyFrom(Method_declarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeMethodContext extends Method_declarationContext {
		public Native_method_declarationContext decl;
		public Native_method_declarationContext native_method_declaration() {
			return getRuleContext(Native_method_declarationContext.class,0);
		}
		public NativeMethodContext(Method_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeMethod(this);
		}
	}
	public static class AbstractMethodContext extends Method_declarationContext {
		public Abstract_method_declarationContext decl;
		public Abstract_method_declarationContext abstract_method_declaration() {
			return getRuleContext(Abstract_method_declarationContext.class,0);
		}
		public AbstractMethodContext(Method_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAbstractMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAbstractMethod(this);
		}
	}
	public static class ConcreteMethodContext extends Method_declarationContext {
		public Concrete_method_declarationContext decl;
		public Concrete_method_declarationContext concrete_method_declaration() {
			return getRuleContext(Concrete_method_declarationContext.class,0);
		}
		public ConcreteMethodContext(Method_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterConcreteMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitConcreteMethod(this);
		}
	}
	public static class TestMethodContext extends Method_declarationContext {
		public Test_method_declarationContext decl;
		public Test_method_declarationContext test_method_declaration() {
			return getRuleContext(Test_method_declarationContext.class,0);
		}
		public TestMethodContext(Method_declarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTestMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTestMethod(this);
		}
	}

	public final Method_declarationContext method_declaration() throws RecognitionException {
		Method_declarationContext _localctx = new Method_declarationContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_method_declaration);
		try {
			setState(1544);
			switch ( getInterpreter().adaptivePredict(_input,118,_ctx) ) {
			case 1:
				_localctx = new AbstractMethodContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1540); 
				((AbstractMethodContext)_localctx).decl = abstract_method_declaration();
				}
				break;
			case 2:
				_localctx = new ConcreteMethodContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1541); 
				((ConcreteMethodContext)_localctx).decl = concrete_method_declaration();
				}
				break;
			case 3:
				_localctx = new NativeMethodContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1542); 
				((NativeMethodContext)_localctx).decl = native_method_declaration();
				}
				break;
			case 4:
				_localctx = new TestMethodContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1543); 
				((TestMethodContext)_localctx).decl = test_method_declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Comment_statementContext extends ParserRuleContext {
		public TerminalNode COMMENT() { return getToken(OParser.COMMENT, 0); }
		public Comment_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterComment_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitComment_statement(this);
		}
	}

	public final Comment_statementContext comment_statement() throws RecognitionException {
		Comment_statementContext _localctx = new Comment_statementContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_comment_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1546); 
			match(COMMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Native_statement_listContext extends ParserRuleContext {
		public Native_statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_statement_list; }
	 
		public Native_statement_listContext() { }
		public void copyFrom(Native_statement_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeStatementListItemContext extends Native_statement_listContext {
		public Native_statement_listContext items;
		public Native_statementContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Native_statement_listContext native_statement_list() {
			return getRuleContext(Native_statement_listContext.class,0);
		}
		public Native_statementContext native_statement() {
			return getRuleContext(Native_statementContext.class,0);
		}
		public NativeStatementListItemContext(Native_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeStatementListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeStatementListItem(this);
		}
	}
	public static class NativeStatementListContext extends Native_statement_listContext {
		public Native_statementContext item;
		public Native_statementContext native_statement() {
			return getRuleContext(Native_statementContext.class,0);
		}
		public NativeStatementListContext(Native_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNativeStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNativeStatementList(this);
		}
	}

	public final Native_statement_listContext native_statement_list() throws RecognitionException {
		return native_statement_list(0);
	}

	private Native_statement_listContext native_statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Native_statement_listContext _localctx = new Native_statement_listContext(_ctx, _parentState);
		Native_statement_listContext _prevctx = _localctx;
		int _startState = 212;
		enterRecursionRule(_localctx, 212, RULE_native_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new NativeStatementListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1549); 
			((NativeStatementListContext)_localctx).item = native_statement();
			}
			_ctx.stop = _input.LT(-1);
			setState(1557);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,119,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new NativeStatementListItemContext(new Native_statement_listContext(_parentctx, _parentState));
					((NativeStatementListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_native_statement_list);
					setState(1551);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1552); 
					lfp();
					setState(1553); 
					((NativeStatementListItemContext)_localctx).item = native_statement();
					}
					} 
				}
				setState(1559);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,119,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Native_statementContext extends ParserRuleContext {
		public Native_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_native_statement; }
	 
		public Native_statementContext() { }
		public void copyFrom(Native_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpNativeStatementContext extends Native_statementContext {
		public Csharp_statementContext stmt;
		public TerminalNode CSHARP() { return getToken(OParser.CSHARP, 0); }
		public Csharp_statementContext csharp_statement() {
			return getRuleContext(Csharp_statementContext.class,0);
		}
		public CSharpNativeStatementContext(Native_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpNativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpNativeStatement(this);
		}
	}
	public static class JavaNativeStatementContext extends Native_statementContext {
		public Java_statementContext stmt;
		public TerminalNode JAVA() { return getToken(OParser.JAVA, 0); }
		public Java_statementContext java_statement() {
			return getRuleContext(Java_statementContext.class,0);
		}
		public JavaNativeStatementContext(Native_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaNativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaNativeStatement(this);
		}
	}
	public static class JavaScriptNativeStatementContext extends Native_statementContext {
		public Javascript_native_statementContext stmt;
		public TerminalNode JAVASCRIPT() { return getToken(OParser.JAVASCRIPT, 0); }
		public Javascript_native_statementContext javascript_native_statement() {
			return getRuleContext(Javascript_native_statementContext.class,0);
		}
		public JavaScriptNativeStatementContext(Native_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaScriptNativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaScriptNativeStatement(this);
		}
	}
	public static class Python2NativeStatementContext extends Native_statementContext {
		public Python_native_statementContext stmt;
		public TerminalNode PYTHON2() { return getToken(OParser.PYTHON2, 0); }
		public Python_native_statementContext python_native_statement() {
			return getRuleContext(Python_native_statementContext.class,0);
		}
		public Python2NativeStatementContext(Native_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython2NativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython2NativeStatement(this);
		}
	}
	public static class Python3NativeStatementContext extends Native_statementContext {
		public Python_native_statementContext stmt;
		public TerminalNode PYTHON3() { return getToken(OParser.PYTHON3, 0); }
		public Python_native_statementContext python_native_statement() {
			return getRuleContext(Python_native_statementContext.class,0);
		}
		public Python3NativeStatementContext(Native_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython3NativeStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython3NativeStatement(this);
		}
	}

	public final Native_statementContext native_statement() throws RecognitionException {
		Native_statementContext _localctx = new Native_statementContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_native_statement);
		try {
			setState(1570);
			switch (_input.LA(1)) {
			case JAVA:
				_localctx = new JavaNativeStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1560); 
				match(JAVA);
				setState(1561); 
				((JavaNativeStatementContext)_localctx).stmt = java_statement();
				}
				break;
			case CSHARP:
				_localctx = new CSharpNativeStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1562); 
				match(CSHARP);
				setState(1563); 
				((CSharpNativeStatementContext)_localctx).stmt = csharp_statement();
				}
				break;
			case PYTHON2:
				_localctx = new Python2NativeStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1564); 
				match(PYTHON2);
				setState(1565); 
				((Python2NativeStatementContext)_localctx).stmt = python_native_statement();
				}
				break;
			case PYTHON3:
				_localctx = new Python3NativeStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1566); 
				match(PYTHON3);
				setState(1567); 
				((Python3NativeStatementContext)_localctx).stmt = python_native_statement();
				}
				break;
			case JAVASCRIPT:
				_localctx = new JavaScriptNativeStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1568); 
				match(JAVASCRIPT);
				setState(1569); 
				((JavaScriptNativeStatementContext)_localctx).stmt = javascript_native_statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_native_statementContext extends ParserRuleContext {
		public Python_statementContext stmt;
		public Python_moduleContext module;
		public Python_statementContext python_statement() {
			return getRuleContext(Python_statementContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Python_moduleContext python_module() {
			return getRuleContext(Python_moduleContext.class,0);
		}
		public Python_native_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_native_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython_native_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython_native_statement(this);
		}
	}

	public final Python_native_statementContext python_native_statement() throws RecognitionException {
		Python_native_statementContext _localctx = new Python_native_statementContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_python_native_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1572); 
			((Python_native_statementContext)_localctx).stmt = python_statement();
			setState(1574);
			switch ( getInterpreter().adaptivePredict(_input,121,_ctx) ) {
			case 1:
				{
				setState(1573); 
				match(SEMI);
				}
				break;
			}
			setState(1577);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				{
				setState(1576); 
				((Python_native_statementContext)_localctx).module = python_module();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_native_statementContext extends ParserRuleContext {
		public Javascript_statementContext stmt;
		public Javascript_moduleContext module;
		public Javascript_statementContext javascript_statement() {
			return getRuleContext(Javascript_statementContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Javascript_moduleContext javascript_module() {
			return getRuleContext(Javascript_moduleContext.class,0);
		}
		public Javascript_native_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_native_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_native_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_native_statement(this);
		}
	}

	public final Javascript_native_statementContext javascript_native_statement() throws RecognitionException {
		Javascript_native_statementContext _localctx = new Javascript_native_statementContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_javascript_native_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1579); 
			((Javascript_native_statementContext)_localctx).stmt = javascript_statement();
			setState(1581);
			switch ( getInterpreter().adaptivePredict(_input,123,_ctx) ) {
			case 1:
				{
				setState(1580); 
				match(SEMI);
				}
				break;
			}
			setState(1584);
			switch ( getInterpreter().adaptivePredict(_input,124,_ctx) ) {
			case 1:
				{
				setState(1583); 
				((Javascript_native_statementContext)_localctx).module = javascript_module();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Statement_listContext extends ParserRuleContext {
		public Statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_list; }
	 
		public Statement_listContext() { }
		public void copyFrom(Statement_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StatementListContext extends Statement_listContext {
		public StatementContext item;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementListContext(Statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitStatementList(this);
		}
	}
	public static class StatementListItemContext extends Statement_listContext {
		public Statement_listContext items;
		public StatementContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Statement_listContext statement_list() {
			return getRuleContext(Statement_listContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementListItemContext(Statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterStatementListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitStatementListItem(this);
		}
	}

	public final Statement_listContext statement_list() throws RecognitionException {
		return statement_list(0);
	}

	private Statement_listContext statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Statement_listContext _localctx = new Statement_listContext(_ctx, _parentState);
		Statement_listContext _prevctx = _localctx;
		int _startState = 220;
		enterRecursionRule(_localctx, 220, RULE_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new StatementListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1587); 
			((StatementListContext)_localctx).item = statement();
			}
			_ctx.stop = _input.LT(-1);
			setState(1595);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatementListItemContext(new Statement_listContext(_parentctx, _parentState));
					((StatementListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_statement_list);
					setState(1589);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1590); 
					lfp();
					setState(1591); 
					((StatementListItemContext)_localctx).item = statement();
					}
					} 
				}
				setState(1597);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Assertion_listContext extends ParserRuleContext {
		public Assertion_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertion_list; }
	 
		public Assertion_listContext() { }
		public void copyFrom(Assertion_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssertionListContext extends Assertion_listContext {
		public AssertionContext item;
		public AssertionContext assertion() {
			return getRuleContext(AssertionContext.class,0);
		}
		public AssertionListContext(Assertion_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssertionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssertionList(this);
		}
	}
	public static class AssertionListItemContext extends Assertion_listContext {
		public Assertion_listContext items;
		public AssertionContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Assertion_listContext assertion_list() {
			return getRuleContext(Assertion_listContext.class,0);
		}
		public AssertionContext assertion() {
			return getRuleContext(AssertionContext.class,0);
		}
		public AssertionListItemContext(Assertion_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssertionListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssertionListItem(this);
		}
	}

	public final Assertion_listContext assertion_list() throws RecognitionException {
		return assertion_list(0);
	}

	private Assertion_listContext assertion_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Assertion_listContext _localctx = new Assertion_listContext(_ctx, _parentState);
		Assertion_listContext _prevctx = _localctx;
		int _startState = 222;
		enterRecursionRule(_localctx, 222, RULE_assertion_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AssertionListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1599); 
			((AssertionListContext)_localctx).item = assertion();
			}
			_ctx.stop = _input.LT(-1);
			setState(1607);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,126,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AssertionListItemContext(new Assertion_listContext(_parentctx, _parentState));
					((AssertionListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_assertion_list);
					setState(1601);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1602); 
					lfp();
					setState(1603); 
					((AssertionListItemContext)_localctx).item = assertion();
					}
					} 
				}
				setState(1609);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,126,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Switch_case_statement_listContext extends ParserRuleContext {
		public Switch_case_statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switch_case_statement_list; }
	 
		public Switch_case_statement_listContext() { }
		public void copyFrom(Switch_case_statement_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SwitchCaseStatementListContext extends Switch_case_statement_listContext {
		public Switch_case_statementContext item;
		public Switch_case_statementContext switch_case_statement() {
			return getRuleContext(Switch_case_statementContext.class,0);
		}
		public SwitchCaseStatementListContext(Switch_case_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSwitchCaseStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSwitchCaseStatementList(this);
		}
	}
	public static class SwitchCaseStatementListItemContext extends Switch_case_statement_listContext {
		public Switch_case_statement_listContext items;
		public Switch_case_statementContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Switch_case_statement_listContext switch_case_statement_list() {
			return getRuleContext(Switch_case_statement_listContext.class,0);
		}
		public Switch_case_statementContext switch_case_statement() {
			return getRuleContext(Switch_case_statementContext.class,0);
		}
		public SwitchCaseStatementListItemContext(Switch_case_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSwitchCaseStatementListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSwitchCaseStatementListItem(this);
		}
	}

	public final Switch_case_statement_listContext switch_case_statement_list() throws RecognitionException {
		return switch_case_statement_list(0);
	}

	private Switch_case_statement_listContext switch_case_statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Switch_case_statement_listContext _localctx = new Switch_case_statement_listContext(_ctx, _parentState);
		Switch_case_statement_listContext _prevctx = _localctx;
		int _startState = 224;
		enterRecursionRule(_localctx, 224, RULE_switch_case_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new SwitchCaseStatementListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1611); 
			((SwitchCaseStatementListContext)_localctx).item = switch_case_statement();
			}
			_ctx.stop = _input.LT(-1);
			setState(1619);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,127,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new SwitchCaseStatementListItemContext(new Switch_case_statement_listContext(_parentctx, _parentState));
					((SwitchCaseStatementListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_switch_case_statement_list);
					setState(1613);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1614); 
					lfp();
					setState(1615); 
					((SwitchCaseStatementListItemContext)_localctx).item = switch_case_statement();
					}
					} 
				}
				setState(1621);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,127,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Catch_statement_listContext extends ParserRuleContext {
		public Catch_statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catch_statement_list; }
	 
		public Catch_statement_listContext() { }
		public void copyFrom(Catch_statement_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CatchStatementListContext extends Catch_statement_listContext {
		public Catch_statementContext item;
		public Catch_statementContext catch_statement() {
			return getRuleContext(Catch_statementContext.class,0);
		}
		public CatchStatementListContext(Catch_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCatchStatementList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCatchStatementList(this);
		}
	}
	public static class CatchStatementListItemContext extends Catch_statement_listContext {
		public Catch_statement_listContext items;
		public Catch_statementContext item;
		public LfpContext lfp() {
			return getRuleContext(LfpContext.class,0);
		}
		public Catch_statement_listContext catch_statement_list() {
			return getRuleContext(Catch_statement_listContext.class,0);
		}
		public Catch_statementContext catch_statement() {
			return getRuleContext(Catch_statementContext.class,0);
		}
		public CatchStatementListItemContext(Catch_statement_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCatchStatementListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCatchStatementListItem(this);
		}
	}

	public final Catch_statement_listContext catch_statement_list() throws RecognitionException {
		return catch_statement_list(0);
	}

	private Catch_statement_listContext catch_statement_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Catch_statement_listContext _localctx = new Catch_statement_listContext(_ctx, _parentState);
		Catch_statement_listContext _prevctx = _localctx;
		int _startState = 226;
		enterRecursionRule(_localctx, 226, RULE_catch_statement_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new CatchStatementListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1623); 
			((CatchStatementListContext)_localctx).item = catch_statement();
			}
			_ctx.stop = _input.LT(-1);
			setState(1631);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,128,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CatchStatementListItemContext(new Catch_statement_listContext(_parentctx, _parentState));
					((CatchStatementListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_catch_statement_list);
					setState(1625);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1626); 
					lfp();
					setState(1627); 
					((CatchStatementListItemContext)_localctx).item = catch_statement();
					}
					} 
				}
				setState(1633);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,128,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Literal_collectionContext extends ParserRuleContext {
		public Literal_collectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_collection; }
	 
		public Literal_collectionContext() { }
		public void copyFrom(Literal_collectionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LiteralListLiteralContext extends Literal_collectionContext {
		public Literal_list_literalContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Literal_list_literalContext literal_list_literal() {
			return getRuleContext(Literal_list_literalContext.class,0);
		}
		public LiteralListLiteralContext(Literal_collectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLiteralListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLiteralListLiteral(this);
		}
	}
	public static class LiteralRangeLiteralContext extends Literal_collectionContext {
		public Atomic_literalContext low;
		public Atomic_literalContext high;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RANGE() { return getToken(OParser.RANGE, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public List<Atomic_literalContext> atomic_literal() {
			return getRuleContexts(Atomic_literalContext.class);
		}
		public Atomic_literalContext atomic_literal(int i) {
			return getRuleContext(Atomic_literalContext.class,i);
		}
		public LiteralRangeLiteralContext(Literal_collectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLiteralRangeLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLiteralRangeLiteral(this);
		}
	}
	public static class LiteralSetLiteralContext extends Literal_collectionContext {
		public Literal_list_literalContext exp;
		public TerminalNode LT() { return getToken(OParser.LT, 0); }
		public TerminalNode GT() { return getToken(OParser.GT, 0); }
		public Literal_list_literalContext literal_list_literal() {
			return getRuleContext(Literal_list_literalContext.class,0);
		}
		public LiteralSetLiteralContext(Literal_collectionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLiteralSetLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLiteralSetLiteral(this);
		}
	}

	public final Literal_collectionContext literal_collection() throws RecognitionException {
		Literal_collectionContext _localctx = new Literal_collectionContext(_ctx, getState());
		enterRule(_localctx, 228, RULE_literal_collection);
		try {
			setState(1648);
			switch ( getInterpreter().adaptivePredict(_input,129,_ctx) ) {
			case 1:
				_localctx = new LiteralRangeLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1634); 
				match(LBRAK);
				setState(1635); 
				((LiteralRangeLiteralContext)_localctx).low = atomic_literal();
				setState(1636); 
				match(RANGE);
				setState(1637); 
				((LiteralRangeLiteralContext)_localctx).high = atomic_literal();
				setState(1638); 
				match(RBRAK);
				}
				break;
			case 2:
				_localctx = new LiteralListLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1640); 
				match(LBRAK);
				setState(1641); 
				((LiteralListLiteralContext)_localctx).exp = literal_list_literal(0);
				setState(1642); 
				match(RBRAK);
				}
				break;
			case 3:
				_localctx = new LiteralSetLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1644); 
				match(LT);
				setState(1645); 
				((LiteralSetLiteralContext)_localctx).exp = literal_list_literal(0);
				setState(1646); 
				match(GT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Atomic_literalContext extends ParserRuleContext {
		public Atomic_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomic_literal; }
	 
		public Atomic_literalContext() { }
		public void copyFrom(Atomic_literalContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MinIntegerLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode MIN_INTEGER() { return getToken(OParser.MIN_INTEGER, 0); }
		public MinIntegerLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMinIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMinIntegerLiteral(this);
		}
	}
	public static class DateLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode DATE_LITERAL() { return getToken(OParser.DATE_LITERAL, 0); }
		public DateLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDateLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDateLiteral(this);
		}
	}
	public static class BooleanLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode BOOLEAN_LITERAL() { return getToken(OParser.BOOLEAN_LITERAL, 0); }
		public BooleanLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitBooleanLiteral(this);
		}
	}
	public static class HexadecimalLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode HEXA_LITERAL() { return getToken(OParser.HEXA_LITERAL, 0); }
		public HexadecimalLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterHexadecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitHexadecimalLiteral(this);
		}
	}
	public static class MaxIntegerLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode MAX_INTEGER() { return getToken(OParser.MAX_INTEGER, 0); }
		public MaxIntegerLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMaxIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMaxIntegerLiteral(this);
		}
	}
	public static class DateTimeLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode DATETIME_LITERAL() { return getToken(OParser.DATETIME_LITERAL, 0); }
		public DateTimeLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDateTimeLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDateTimeLiteral(this);
		}
	}
	public static class PeriodLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode PERIOD_LITERAL() { return getToken(OParser.PERIOD_LITERAL, 0); }
		public PeriodLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPeriodLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPeriodLiteral(this);
		}
	}
	public static class DecimalLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode DECIMAL_LITERAL() { return getToken(OParser.DECIMAL_LITERAL, 0); }
		public DecimalLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDecimalLiteral(this);
		}
	}
	public static class TextLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public TextLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTextLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTextLiteral(this);
		}
	}
	public static class NullLiteralContext extends Atomic_literalContext {
		public Null_literalContext n;
		public Null_literalContext null_literal() {
			return getRuleContext(Null_literalContext.class,0);
		}
		public NullLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNullLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNullLiteral(this);
		}
	}
	public static class IntegerLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode INTEGER_LITERAL() { return getToken(OParser.INTEGER_LITERAL, 0); }
		public IntegerLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIntegerLiteral(this);
		}
	}
	public static class TimeLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode TIME_LITERAL() { return getToken(OParser.TIME_LITERAL, 0); }
		public TimeLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTimeLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTimeLiteral(this);
		}
	}
	public static class CharacterLiteralContext extends Atomic_literalContext {
		public Token t;
		public TerminalNode CHAR_LITERAL() { return getToken(OParser.CHAR_LITERAL, 0); }
		public CharacterLiteralContext(Atomic_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCharacterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCharacterLiteral(this);
		}
	}

	public final Atomic_literalContext atomic_literal() throws RecognitionException {
		Atomic_literalContext _localctx = new Atomic_literalContext(_ctx, getState());
		enterRule(_localctx, 230, RULE_atomic_literal);
		try {
			setState(1663);
			switch (_input.LA(1)) {
			case MIN_INTEGER:
				_localctx = new MinIntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1650); 
				((MinIntegerLiteralContext)_localctx).t = match(MIN_INTEGER);
				}
				break;
			case MAX_INTEGER:
				_localctx = new MaxIntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1651); 
				((MaxIntegerLiteralContext)_localctx).t = match(MAX_INTEGER);
				}
				break;
			case INTEGER_LITERAL:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1652); 
				((IntegerLiteralContext)_localctx).t = match(INTEGER_LITERAL);
				}
				break;
			case HEXA_LITERAL:
				_localctx = new HexadecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1653); 
				((HexadecimalLiteralContext)_localctx).t = match(HEXA_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				_localctx = new CharacterLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1654); 
				((CharacterLiteralContext)_localctx).t = match(CHAR_LITERAL);
				}
				break;
			case DATE_LITERAL:
				_localctx = new DateLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1655); 
				((DateLiteralContext)_localctx).t = match(DATE_LITERAL);
				}
				break;
			case TIME_LITERAL:
				_localctx = new TimeLiteralContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(1656); 
				((TimeLiteralContext)_localctx).t = match(TIME_LITERAL);
				}
				break;
			case TEXT_LITERAL:
				_localctx = new TextLiteralContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(1657); 
				((TextLiteralContext)_localctx).t = match(TEXT_LITERAL);
				}
				break;
			case DECIMAL_LITERAL:
				_localctx = new DecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(1658); 
				((DecimalLiteralContext)_localctx).t = match(DECIMAL_LITERAL);
				}
				break;
			case DATETIME_LITERAL:
				_localctx = new DateTimeLiteralContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(1659); 
				((DateTimeLiteralContext)_localctx).t = match(DATETIME_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new BooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(1660); 
				((BooleanLiteralContext)_localctx).t = match(BOOLEAN_LITERAL);
				}
				break;
			case PERIOD_LITERAL:
				_localctx = new PeriodLiteralContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(1661); 
				((PeriodLiteralContext)_localctx).t = match(PERIOD_LITERAL);
				}
				break;
			case NULL:
				_localctx = new NullLiteralContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(1662); 
				((NullLiteralContext)_localctx).n = null_literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_list_literalContext extends ParserRuleContext {
		public Literal_list_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_list_literal; }
	 
		public Literal_list_literalContext() { }
		public void copyFrom(Literal_list_literalContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LiteralListContext extends Literal_list_literalContext {
		public Atomic_literalContext item;
		public Atomic_literalContext atomic_literal() {
			return getRuleContext(Atomic_literalContext.class,0);
		}
		public LiteralListContext(Literal_list_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLiteralList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLiteralList(this);
		}
	}
	public static class LiteralListItemContext extends Literal_list_literalContext {
		public Literal_list_literalContext items;
		public Atomic_literalContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Literal_list_literalContext literal_list_literal() {
			return getRuleContext(Literal_list_literalContext.class,0);
		}
		public Atomic_literalContext atomic_literal() {
			return getRuleContext(Atomic_literalContext.class,0);
		}
		public LiteralListItemContext(Literal_list_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLiteralListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLiteralListItem(this);
		}
	}

	public final Literal_list_literalContext literal_list_literal() throws RecognitionException {
		return literal_list_literal(0);
	}

	private Literal_list_literalContext literal_list_literal(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Literal_list_literalContext _localctx = new Literal_list_literalContext(_ctx, _parentState);
		Literal_list_literalContext _prevctx = _localctx;
		int _startState = 232;
		enterRecursionRule(_localctx, 232, RULE_literal_list_literal, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new LiteralListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1666); 
			((LiteralListContext)_localctx).item = atomic_literal();
			}
			_ctx.stop = _input.LT(-1);
			setState(1673);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,131,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LiteralListItemContext(new Literal_list_literalContext(_parentctx, _parentState));
					((LiteralListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_literal_list_literal);
					setState(1668);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1669); 
					match(COMMA);
					setState(1670); 
					((LiteralListItemContext)_localctx).item = atomic_literal();
					}
					} 
				}
				setState(1675);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,131,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Selectable_expressionContext extends ParserRuleContext {
		public Selectable_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectable_expression; }
	 
		public Selectable_expressionContext() { }
		public void copyFrom(Selectable_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ThisExpressionContext extends Selectable_expressionContext {
		public This_expressionContext exp;
		public This_expressionContext this_expression() {
			return getRuleContext(This_expressionContext.class,0);
		}
		public ThisExpressionContext(Selectable_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterThisExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitThisExpression(this);
		}
	}
	public static class ParenthesisExpressionContext extends Selectable_expressionContext {
		public Parenthesis_expressionContext exp;
		public Parenthesis_expressionContext parenthesis_expression() {
			return getRuleContext(Parenthesis_expressionContext.class,0);
		}
		public ParenthesisExpressionContext(Selectable_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterParenthesisExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitParenthesisExpression(this);
		}
	}
	public static class LiteralExpressionContext extends Selectable_expressionContext {
		public Literal_expressionContext exp;
		public Literal_expressionContext literal_expression() {
			return getRuleContext(Literal_expressionContext.class,0);
		}
		public LiteralExpressionContext(Selectable_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLiteralExpression(this);
		}
	}
	public static class IdentifierExpressionContext extends Selectable_expressionContext {
		public IdentifierContext exp;
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public IdentifierExpressionContext(Selectable_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIdentifierExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIdentifierExpression(this);
		}
	}

	public final Selectable_expressionContext selectable_expression() throws RecognitionException {
		Selectable_expressionContext _localctx = new Selectable_expressionContext(_ctx, getState());
		enterRule(_localctx, 234, RULE_selectable_expression);
		try {
			setState(1680);
			switch ( getInterpreter().adaptivePredict(_input,132,_ctx) ) {
			case 1:
				_localctx = new ParenthesisExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1676); 
				((ParenthesisExpressionContext)_localctx).exp = parenthesis_expression();
				}
				break;
			case 2:
				_localctx = new LiteralExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1677); 
				((LiteralExpressionContext)_localctx).exp = literal_expression();
				}
				break;
			case 3:
				_localctx = new IdentifierExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1678); 
				((IdentifierExpressionContext)_localctx).exp = identifier();
				}
				break;
			case 4:
				_localctx = new ThisExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1679); 
				((ThisExpressionContext)_localctx).exp = this_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class This_expressionContext extends ParserRuleContext {
		public TerminalNode SELF() { return getToken(OParser.SELF, 0); }
		public TerminalNode THIS() { return getToken(OParser.THIS, 0); }
		public This_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_this_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterThis_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitThis_expression(this);
		}
	}

	public final This_expressionContext this_expression() throws RecognitionException {
		This_expressionContext _localctx = new This_expressionContext(_ctx, getState());
		enterRule(_localctx, 236, RULE_this_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1682);
			_la = _input.LA(1);
			if ( !(_la==SELF || _la==THIS) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Parenthesis_expressionContext extends ParserRuleContext {
		public ExpressionContext exp;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Parenthesis_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesis_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterParenthesis_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitParenthesis_expression(this);
		}
	}

	public final Parenthesis_expressionContext parenthesis_expression() throws RecognitionException {
		Parenthesis_expressionContext _localctx = new Parenthesis_expressionContext(_ctx, getState());
		enterRule(_localctx, 238, RULE_parenthesis_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1684); 
			match(LPAR);
			setState(1685); 
			((Parenthesis_expressionContext)_localctx).exp = expression(0);
			setState(1686); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_expressionContext extends ParserRuleContext {
		public Literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_expression; }
	 
		public Literal_expressionContext() { }
		public void copyFrom(Literal_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CollectionLiteralContext extends Literal_expressionContext {
		public Collection_literalContext exp;
		public Collection_literalContext collection_literal() {
			return getRuleContext(Collection_literalContext.class,0);
		}
		public CollectionLiteralContext(Literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCollectionLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCollectionLiteral(this);
		}
	}
	public static class AtomicLiteralContext extends Literal_expressionContext {
		public Atomic_literalContext exp;
		public Atomic_literalContext atomic_literal() {
			return getRuleContext(Atomic_literalContext.class,0);
		}
		public AtomicLiteralContext(Literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAtomicLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAtomicLiteral(this);
		}
	}

	public final Literal_expressionContext literal_expression() throws RecognitionException {
		Literal_expressionContext _localctx = new Literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 240, RULE_literal_expression);
		try {
			setState(1690);
			switch (_input.LA(1)) {
			case NULL:
			case BOOLEAN_LITERAL:
			case CHAR_LITERAL:
			case MIN_INTEGER:
			case MAX_INTEGER:
			case TEXT_LITERAL:
			case INTEGER_LITERAL:
			case HEXA_LITERAL:
			case DECIMAL_LITERAL:
			case DATETIME_LITERAL:
			case TIME_LITERAL:
			case DATE_LITERAL:
			case PERIOD_LITERAL:
				_localctx = new AtomicLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1688); 
				((AtomicLiteralContext)_localctx).exp = atomic_literal();
				}
				break;
			case LPAR:
			case LBRAK:
			case LCURL:
			case LT:
				_localctx = new CollectionLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1689); 
				((CollectionLiteralContext)_localctx).exp = collection_literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Collection_literalContext extends ParserRuleContext {
		public Collection_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collection_literal; }
	 
		public Collection_literalContext() { }
		public void copyFrom(Collection_literalContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ListLiteralContext extends Collection_literalContext {
		public List_literalContext exp;
		public List_literalContext list_literal() {
			return getRuleContext(List_literalContext.class,0);
		}
		public ListLiteralContext(Collection_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitListLiteral(this);
		}
	}
	public static class RangeLiteralContext extends Collection_literalContext {
		public Range_literalContext exp;
		public Range_literalContext range_literal() {
			return getRuleContext(Range_literalContext.class,0);
		}
		public RangeLiteralContext(Collection_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRangeLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRangeLiteral(this);
		}
	}
	public static class TupleLiteralContext extends Collection_literalContext {
		public Tuple_literalContext exp;
		public Tuple_literalContext tuple_literal() {
			return getRuleContext(Tuple_literalContext.class,0);
		}
		public TupleLiteralContext(Collection_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTupleLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTupleLiteral(this);
		}
	}
	public static class SetLiteralContext extends Collection_literalContext {
		public Set_literalContext exp;
		public Set_literalContext set_literal() {
			return getRuleContext(Set_literalContext.class,0);
		}
		public SetLiteralContext(Collection_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSetLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSetLiteral(this);
		}
	}
	public static class DictLiteralContext extends Collection_literalContext {
		public Dict_literalContext exp;
		public Dict_literalContext dict_literal() {
			return getRuleContext(Dict_literalContext.class,0);
		}
		public DictLiteralContext(Collection_literalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDictLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDictLiteral(this);
		}
	}

	public final Collection_literalContext collection_literal() throws RecognitionException {
		Collection_literalContext _localctx = new Collection_literalContext(_ctx, getState());
		enterRule(_localctx, 242, RULE_collection_literal);
		try {
			setState(1697);
			switch ( getInterpreter().adaptivePredict(_input,134,_ctx) ) {
			case 1:
				_localctx = new RangeLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1692); 
				((RangeLiteralContext)_localctx).exp = range_literal();
				}
				break;
			case 2:
				_localctx = new ListLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1693); 
				((ListLiteralContext)_localctx).exp = list_literal();
				}
				break;
			case 3:
				_localctx = new SetLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1694); 
				((SetLiteralContext)_localctx).exp = set_literal();
				}
				break;
			case 4:
				_localctx = new DictLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1695); 
				((DictLiteralContext)_localctx).exp = dict_literal();
				}
				break;
			case 5:
				_localctx = new TupleLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1696); 
				((TupleLiteralContext)_localctx).exp = tuple_literal();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Tuple_literalContext extends ParserRuleContext {
		public Expression_tupleContext items;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Expression_tupleContext expression_tuple() {
			return getRuleContext(Expression_tupleContext.class,0);
		}
		public Tuple_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tuple_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterTuple_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitTuple_literal(this);
		}
	}

	public final Tuple_literalContext tuple_literal() throws RecognitionException {
		Tuple_literalContext _localctx = new Tuple_literalContext(_ctx, getState());
		enterRule(_localctx, 244, RULE_tuple_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1699); 
			match(LPAR);
			setState(1701);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(1700); 
				((Tuple_literalContext)_localctx).items = expression_tuple(0);
				}
			}

			setState(1703); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Dict_literalContext extends ParserRuleContext {
		public Dict_entry_listContext items;
		public TerminalNode LCURL() { return getToken(OParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(OParser.RCURL, 0); }
		public Dict_entry_listContext dict_entry_list() {
			return getRuleContext(Dict_entry_listContext.class,0);
		}
		public Dict_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDict_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDict_literal(this);
		}
	}

	public final Dict_literalContext dict_literal() throws RecognitionException {
		Dict_literalContext _localctx = new Dict_literalContext(_ctx, getState());
		enterRule(_localctx, 246, RULE_dict_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1705); 
			match(LCURL);
			setState(1707);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << LCURL) | (1L << XMARK) | (1L << MINUS) | (1L << LT) | (1L << CODE) | (1L << DOCUMENT))) != 0) || ((((_la - 90)) & ~0x3f) == 0 && ((1L << (_la - 90)) & ((1L << (EXECUTE - 90)) | (1L << (FETCH - 90)) | (1L << (MUTABLE - 90)) | (1L << (NULL - 90)) | (1L << (READ - 90)) | (1L << (SELF - 90)) | (1L << (SORTED - 90)) | (1L << (THIS - 90)) | (1L << (BOOLEAN_LITERAL - 90)) | (1L << (CHAR_LITERAL - 90)) | (1L << (MIN_INTEGER - 90)) | (1L << (MAX_INTEGER - 90)) | (1L << (SYMBOL_IDENTIFIER - 90)) | (1L << (TYPE_IDENTIFIER - 90)) | (1L << (VARIABLE_IDENTIFIER - 90)))) != 0) || ((((_la - 154)) & ~0x3f) == 0 && ((1L << (_la - 154)) & ((1L << (TEXT_LITERAL - 154)) | (1L << (INTEGER_LITERAL - 154)) | (1L << (HEXA_LITERAL - 154)) | (1L << (DECIMAL_LITERAL - 154)) | (1L << (DATETIME_LITERAL - 154)) | (1L << (TIME_LITERAL - 154)) | (1L << (DATE_LITERAL - 154)) | (1L << (PERIOD_LITERAL - 154)))) != 0)) {
				{
				setState(1706); 
				((Dict_literalContext)_localctx).items = dict_entry_list(0);
				}
			}

			setState(1709); 
			match(RCURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expression_tupleContext extends ParserRuleContext {
		public Expression_tupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_tuple; }
	 
		public Expression_tupleContext() { }
		public void copyFrom(Expression_tupleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ValueTupleContext extends Expression_tupleContext {
		public ExpressionContext item;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ValueTupleContext(Expression_tupleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterValueTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitValueTuple(this);
		}
	}
	public static class ValueTupleItemContext extends Expression_tupleContext {
		public Expression_tupleContext items;
		public ExpressionContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Expression_tupleContext expression_tuple() {
			return getRuleContext(Expression_tupleContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ValueTupleItemContext(Expression_tupleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterValueTupleItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitValueTupleItem(this);
		}
	}

	public final Expression_tupleContext expression_tuple() throws RecognitionException {
		return expression_tuple(0);
	}

	private Expression_tupleContext expression_tuple(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expression_tupleContext _localctx = new Expression_tupleContext(_ctx, _parentState);
		Expression_tupleContext _prevctx = _localctx;
		int _startState = 248;
		enterRecursionRule(_localctx, 248, RULE_expression_tuple, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ValueTupleContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1712); 
			((ValueTupleContext)_localctx).item = expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(1719);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,137,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ValueTupleItemContext(new Expression_tupleContext(_parentctx, _parentState));
					((ValueTupleItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expression_tuple);
					setState(1714);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1715); 
					match(COMMA);
					setState(1716); 
					((ValueTupleItemContext)_localctx).item = expression(0);
					}
					} 
				}
				setState(1721);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,137,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Dict_entry_listContext extends ParserRuleContext {
		public Dict_entry_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict_entry_list; }
	 
		public Dict_entry_listContext() { }
		public void copyFrom(Dict_entry_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DictEntryListContext extends Dict_entry_listContext {
		public Dict_entryContext item;
		public Dict_entryContext dict_entry() {
			return getRuleContext(Dict_entryContext.class,0);
		}
		public DictEntryListContext(Dict_entry_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDictEntryList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDictEntryList(this);
		}
	}
	public static class DictEntryListItemContext extends Dict_entry_listContext {
		public Dict_entry_listContext items;
		public Dict_entryContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Dict_entry_listContext dict_entry_list() {
			return getRuleContext(Dict_entry_listContext.class,0);
		}
		public Dict_entryContext dict_entry() {
			return getRuleContext(Dict_entryContext.class,0);
		}
		public DictEntryListItemContext(Dict_entry_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDictEntryListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDictEntryListItem(this);
		}
	}

	public final Dict_entry_listContext dict_entry_list() throws RecognitionException {
		return dict_entry_list(0);
	}

	private Dict_entry_listContext dict_entry_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Dict_entry_listContext _localctx = new Dict_entry_listContext(_ctx, _parentState);
		Dict_entry_listContext _prevctx = _localctx;
		int _startState = 250;
		enterRecursionRule(_localctx, 250, RULE_dict_entry_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new DictEntryListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1723); 
			((DictEntryListContext)_localctx).item = dict_entry();
			}
			_ctx.stop = _input.LT(-1);
			setState(1730);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,138,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DictEntryListItemContext(new Dict_entry_listContext(_parentctx, _parentState));
					((DictEntryListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_dict_entry_list);
					setState(1725);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1726); 
					match(COMMA);
					setState(1727); 
					((DictEntryListItemContext)_localctx).item = dict_entry();
					}
					} 
				}
				setState(1732);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,138,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Dict_entryContext extends ParserRuleContext {
		public ExpressionContext key;
		public ExpressionContext value;
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Dict_entryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDict_entry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDict_entry(this);
		}
	}

	public final Dict_entryContext dict_entry() throws RecognitionException {
		Dict_entryContext _localctx = new Dict_entryContext(_ctx, getState());
		enterRule(_localctx, 252, RULE_dict_entry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1733); 
			((Dict_entryContext)_localctx).key = expression(0);
			setState(1734); 
			match(COLON);
			setState(1735); 
			((Dict_entryContext)_localctx).value = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Slice_argumentsContext extends ParserRuleContext {
		public Slice_argumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_slice_arguments; }
	 
		public Slice_argumentsContext() { }
		public void copyFrom(Slice_argumentsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SliceFirstAndLastContext extends Slice_argumentsContext {
		public ExpressionContext first;
		public ExpressionContext last;
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SliceFirstAndLastContext(Slice_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSliceFirstAndLast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSliceFirstAndLast(this);
		}
	}
	public static class SliceLastOnlyContext extends Slice_argumentsContext {
		public ExpressionContext last;
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SliceLastOnlyContext(Slice_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSliceLastOnly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSliceLastOnly(this);
		}
	}
	public static class SliceFirstOnlyContext extends Slice_argumentsContext {
		public ExpressionContext first;
		public TerminalNode COLON() { return getToken(OParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SliceFirstOnlyContext(Slice_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSliceFirstOnly(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSliceFirstOnly(this);
		}
	}

	public final Slice_argumentsContext slice_arguments() throws RecognitionException {
		Slice_argumentsContext _localctx = new Slice_argumentsContext(_ctx, getState());
		enterRule(_localctx, 254, RULE_slice_arguments);
		try {
			setState(1746);
			switch ( getInterpreter().adaptivePredict(_input,139,_ctx) ) {
			case 1:
				_localctx = new SliceFirstAndLastContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1737); 
				((SliceFirstAndLastContext)_localctx).first = expression(0);
				setState(1738); 
				match(COLON);
				setState(1739); 
				((SliceFirstAndLastContext)_localctx).last = expression(0);
				}
				break;
			case 2:
				_localctx = new SliceFirstOnlyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1741); 
				((SliceFirstOnlyContext)_localctx).first = expression(0);
				setState(1742); 
				match(COLON);
				}
				break;
			case 3:
				_localctx = new SliceLastOnlyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1744); 
				match(COLON);
				setState(1745); 
				((SliceLastOnlyContext)_localctx).last = expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_variable_statementContext extends ParserRuleContext {
		public Variable_identifierContext name;
		public ExpressionContext exp;
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Assign_variable_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_variable_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssign_variable_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssign_variable_statement(this);
		}
	}

	public final Assign_variable_statementContext assign_variable_statement() throws RecognitionException {
		Assign_variable_statementContext _localctx = new Assign_variable_statementContext(_ctx, getState());
		enterRule(_localctx, 256, RULE_assign_variable_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1748); 
			((Assign_variable_statementContext)_localctx).name = variable_identifier();
			setState(1749); 
			assign();
			setState(1750); 
			((Assign_variable_statementContext)_localctx).exp = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assignable_instanceContext extends ParserRuleContext {
		public Assignable_instanceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignable_instance; }
	 
		public Assignable_instanceContext() { }
		public void copyFrom(Assignable_instanceContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ChildInstanceContext extends Assignable_instanceContext {
		public Assignable_instanceContext parent;
		public Child_instanceContext child;
		public Assignable_instanceContext assignable_instance() {
			return getRuleContext(Assignable_instanceContext.class,0);
		}
		public Child_instanceContext child_instance() {
			return getRuleContext(Child_instanceContext.class,0);
		}
		public ChildInstanceContext(Assignable_instanceContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterChildInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitChildInstance(this);
		}
	}
	public static class RootInstanceContext extends Assignable_instanceContext {
		public Variable_identifierContext name;
		public Variable_identifierContext variable_identifier() {
			return getRuleContext(Variable_identifierContext.class,0);
		}
		public RootInstanceContext(Assignable_instanceContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterRootInstance(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitRootInstance(this);
		}
	}

	public final Assignable_instanceContext assignable_instance() throws RecognitionException {
		return assignable_instance(0);
	}

	private Assignable_instanceContext assignable_instance(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Assignable_instanceContext _localctx = new Assignable_instanceContext(_ctx, _parentState);
		Assignable_instanceContext _prevctx = _localctx;
		int _startState = 258;
		enterRecursionRule(_localctx, 258, RULE_assignable_instance, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new RootInstanceContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1753); 
			((RootInstanceContext)_localctx).name = variable_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(1759);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,140,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ChildInstanceContext(new Assignable_instanceContext(_parentctx, _parentState));
					((ChildInstanceContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_assignable_instance);
					setState(1755);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1756); 
					((ChildInstanceContext)_localctx).child = child_instance();
					}
					} 
				}
				setState(1761);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,140,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Is_expressionContext extends ParserRuleContext {
		public Is_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_is_expression; }
	 
		public Is_expressionContext() { }
		public void copyFrom(Is_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IsATypeExpressionContext extends Is_expressionContext {
		public Category_or_any_typeContext typ;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Category_or_any_typeContext category_or_any_type() {
			return getRuleContext(Category_or_any_typeContext.class,0);
		}
		public IsATypeExpressionContext(Is_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIsATypeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIsATypeExpression(this);
		}
	}
	public static class IsOtherExpressionContext extends Is_expressionContext {
		public ExpressionContext exp;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IsOtherExpressionContext(Is_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIsOtherExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIsOtherExpression(this);
		}
	}

	public final Is_expressionContext is_expression() throws RecognitionException {
		Is_expressionContext _localctx = new Is_expressionContext(_ctx, getState());
		enterRule(_localctx, 260, RULE_is_expression);
		try {
			setState(1766);
			switch ( getInterpreter().adaptivePredict(_input,141,_ctx) ) {
			case 1:
				_localctx = new IsATypeExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1762);
				if (!(this.willBeAOrAn())) throw new FailedPredicateException(this, "$parser.willBeAOrAn()");
				setState(1763); 
				match(VARIABLE_IDENTIFIER);
				setState(1764); 
				((IsATypeExpressionContext)_localctx).typ = category_or_any_type();
				}
				break;
			case 2:
				_localctx = new IsOtherExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1765); 
				((IsOtherExpressionContext)_localctx).exp = expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Order_by_listContext extends ParserRuleContext {
		public List<Order_byContext> order_by() {
			return getRuleContexts(Order_byContext.class);
		}
		public Order_byContext order_by(int i) {
			return getRuleContext(Order_byContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(OParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(OParser.COMMA, i);
		}
		public Order_by_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_order_by_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOrder_by_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOrder_by_list(this);
		}
	}

	public final Order_by_listContext order_by_list() throws RecognitionException {
		Order_by_listContext _localctx = new Order_by_listContext(_ctx, getState());
		enterRule(_localctx, 262, RULE_order_by_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1768); 
			order_by();
			setState(1773);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(1769); 
				match(COMMA);
				setState(1770); 
				order_by();
				}
				}
				setState(1775);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Order_byContext extends ParserRuleContext {
		public List<Variable_identifierContext> variable_identifier() {
			return getRuleContexts(Variable_identifierContext.class);
		}
		public Variable_identifierContext variable_identifier(int i) {
			return getRuleContext(Variable_identifierContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(OParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(OParser.DOT, i);
		}
		public TerminalNode ASC() { return getToken(OParser.ASC, 0); }
		public TerminalNode DESC() { return getToken(OParser.DESC, 0); }
		public Order_byContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_order_by; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOrder_by(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOrder_by(this);
		}
	}

	public final Order_byContext order_by() throws RecognitionException {
		Order_byContext _localctx = new Order_byContext(_ctx, getState());
		enterRule(_localctx, 264, RULE_order_by);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1776); 
			variable_identifier();
			setState(1781);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(1777); 
				match(DOT);
				setState(1778); 
				variable_identifier();
				}
				}
				setState(1783);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1785);
			_la = _input.LA(1);
			if (_la==ASC || _la==DESC) {
				{
				setState(1784);
				_la = _input.LA(1);
				if ( !(_la==ASC || _la==DESC) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorContext extends ParserRuleContext {
		public OperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operator; }
	 
		public OperatorContext() { }
		public void copyFrom(OperatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OperatorPlusContext extends OperatorContext {
		public TerminalNode PLUS() { return getToken(OParser.PLUS, 0); }
		public OperatorPlusContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorPlus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorPlus(this);
		}
	}
	public static class OperatorDivideContext extends OperatorContext {
		public DivideContext divide() {
			return getRuleContext(DivideContext.class,0);
		}
		public OperatorDivideContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorDivide(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorDivide(this);
		}
	}
	public static class OperatorIDivideContext extends OperatorContext {
		public IdivideContext idivide() {
			return getRuleContext(IdivideContext.class,0);
		}
		public OperatorIDivideContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorIDivide(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorIDivide(this);
		}
	}
	public static class OperatorMultiplyContext extends OperatorContext {
		public MultiplyContext multiply() {
			return getRuleContext(MultiplyContext.class,0);
		}
		public OperatorMultiplyContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorMultiply(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorMultiply(this);
		}
	}
	public static class OperatorMinusContext extends OperatorContext {
		public TerminalNode MINUS() { return getToken(OParser.MINUS, 0); }
		public OperatorMinusContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorMinus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorMinus(this);
		}
	}
	public static class OperatorModuloContext extends OperatorContext {
		public ModuloContext modulo() {
			return getRuleContext(ModuloContext.class,0);
		}
		public OperatorModuloContext(OperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterOperatorModulo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitOperatorModulo(this);
		}
	}

	public final OperatorContext operator() throws RecognitionException {
		OperatorContext _localctx = new OperatorContext(_ctx, getState());
		enterRule(_localctx, 266, RULE_operator);
		try {
			setState(1793);
			switch (_input.LA(1)) {
			case PLUS:
				_localctx = new OperatorPlusContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1787); 
				match(PLUS);
				}
				break;
			case MINUS:
				_localctx = new OperatorMinusContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1788); 
				match(MINUS);
				}
				break;
			case STAR:
				_localctx = new OperatorMultiplyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1789); 
				multiply();
				}
				break;
			case SLASH:
				_localctx = new OperatorDivideContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1790); 
				divide();
				}
				break;
			case BSLASH:
				_localctx = new OperatorIDivideContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1791); 
				idivide();
				}
				break;
			case PERCENT:
			case MODULO:
				_localctx = new OperatorModuloContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(1792); 
				modulo();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class New_tokenContext extends ParserRuleContext {
		public Token i1;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public New_tokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_new_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterNew_token(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitNew_token(this);
		}
	}

	public final New_tokenContext new_token() throws RecognitionException {
		New_tokenContext _localctx = new New_tokenContext(_ctx, getState());
		enterRule(_localctx, 268, RULE_new_token);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1795); 
			((New_tokenContext)_localctx).i1 = match(VARIABLE_IDENTIFIER);
			setState(1796);
			if (!(this.isText(((New_tokenContext)_localctx).i1,"new"))) throw new FailedPredicateException(this, "$parser.isText($i1,\"new\")");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Key_tokenContext extends ParserRuleContext {
		public Token i1;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Key_tokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterKey_token(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitKey_token(this);
		}
	}

	public final Key_tokenContext key_token() throws RecognitionException {
		Key_tokenContext _localctx = new Key_tokenContext(_ctx, getState());
		enterRule(_localctx, 270, RULE_key_token);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1798); 
			((Key_tokenContext)_localctx).i1 = match(VARIABLE_IDENTIFIER);
			setState(1799);
			if (!(this.isText(((Key_tokenContext)_localctx).i1,"key"))) throw new FailedPredicateException(this, "$parser.isText($i1,\"key\")");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Value_tokenContext extends ParserRuleContext {
		public Token i1;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Value_tokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterValue_token(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitValue_token(this);
		}
	}

	public final Value_tokenContext value_token() throws RecognitionException {
		Value_tokenContext _localctx = new Value_tokenContext(_ctx, getState());
		enterRule(_localctx, 272, RULE_value_token);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1801); 
			((Value_tokenContext)_localctx).i1 = match(VARIABLE_IDENTIFIER);
			setState(1802);
			if (!(this.isText(((Value_tokenContext)_localctx).i1,"value"))) throw new FailedPredicateException(this, "$parser.isText($i1,\"value\")");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Symbols_tokenContext extends ParserRuleContext {
		public Token i1;
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public Symbols_tokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_symbols_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterSymbols_token(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitSymbols_token(this);
		}
	}

	public final Symbols_tokenContext symbols_token() throws RecognitionException {
		Symbols_tokenContext _localctx = new Symbols_tokenContext(_ctx, getState());
		enterRule(_localctx, 274, RULE_symbols_token);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1804); 
			((Symbols_tokenContext)_localctx).i1 = match(VARIABLE_IDENTIFIER);
			setState(1805);
			if (!(this.isText(((Symbols_tokenContext)_localctx).i1,"symbols"))) throw new FailedPredicateException(this, "$parser.isText($i1,\"symbols\")");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitAssign(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 276, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1807); 
			match(EQ);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplyContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(OParser.STAR, 0); }
		public MultiplyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiply; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterMultiply(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitMultiply(this);
		}
	}

	public final MultiplyContext multiply() throws RecognitionException {
		MultiplyContext _localctx = new MultiplyContext(_ctx, getState());
		enterRule(_localctx, 278, RULE_multiply);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1809); 
			match(STAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DivideContext extends ParserRuleContext {
		public TerminalNode SLASH() { return getToken(OParser.SLASH, 0); }
		public DivideContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_divide; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterDivide(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitDivide(this);
		}
	}

	public final DivideContext divide() throws RecognitionException {
		DivideContext _localctx = new DivideContext(_ctx, getState());
		enterRule(_localctx, 280, RULE_divide);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1811); 
			match(SLASH);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdivideContext extends ParserRuleContext {
		public TerminalNode BSLASH() { return getToken(OParser.BSLASH, 0); }
		public IdivideContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idivide; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterIdivide(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitIdivide(this);
		}
	}

	public final IdivideContext idivide() throws RecognitionException {
		IdivideContext _localctx = new IdivideContext(_ctx, getState());
		enterRule(_localctx, 282, RULE_idivide);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1813); 
			match(BSLASH);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModuloContext extends ParserRuleContext {
		public TerminalNode PERCENT() { return getToken(OParser.PERCENT, 0); }
		public TerminalNode MODULO() { return getToken(OParser.MODULO, 0); }
		public ModuloContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modulo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterModulo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitModulo(this);
		}
	}

	public final ModuloContext modulo() throws RecognitionException {
		ModuloContext _localctx = new ModuloContext(_ctx, getState());
		enterRule(_localctx, 284, RULE_modulo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1815);
			_la = _input.LA(1);
			if ( !(_la==PERCENT || _la==MODULO) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LfsContext extends ParserRuleContext {
		public LfsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lfs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLfs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLfs(this);
		}
	}

	public final LfsContext lfs() throws RecognitionException {
		LfsContext _localctx = new LfsContext(_ctx, getState());
		enterRule(_localctx, 286, RULE_lfs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LfpContext extends ParserRuleContext {
		public LfpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lfp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterLfp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitLfp(this);
		}
	}

	public final LfpContext lfp() throws RecognitionException {
		LfpContext _localctx = new LfpContext(_ctx, getState());
		enterRule(_localctx, 288, RULE_lfp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_statementContext extends ParserRuleContext {
		public Javascript_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_statement; }
	 
		public Javascript_statementContext() { }
		public void copyFrom(Javascript_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavascriptStatementContext extends Javascript_statementContext {
		public Javascript_expressionContext exp;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public JavascriptStatementContext(Javascript_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptStatement(this);
		}
	}
	public static class JavascriptReturnStatementContext extends Javascript_statementContext {
		public Javascript_expressionContext exp;
		public TerminalNode RETURN() { return getToken(OParser.RETURN, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public JavascriptReturnStatementContext(Javascript_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptReturnStatement(this);
		}
	}

	public final Javascript_statementContext javascript_statement() throws RecognitionException {
		Javascript_statementContext _localctx = new Javascript_statementContext(_ctx, getState());
		enterRule(_localctx, 290, RULE_javascript_statement);
		try {
			setState(1828);
			switch (_input.LA(1)) {
			case RETURN:
				_localctx = new JavascriptReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1821); 
				match(RETURN);
				setState(1822); 
				((JavascriptReturnStatementContext)_localctx).exp = javascript_expression(0);
				setState(1823); 
				match(SEMI);
				}
				break;
			case LPAR:
			case LBRAK:
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case READ:
			case SELF:
			case TEST:
			case THIS:
			case WRITE:
			case BOOLEAN_LITERAL:
			case CHAR_LITERAL:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case TEXT_LITERAL:
			case INTEGER_LITERAL:
			case DECIMAL_LITERAL:
				_localctx = new JavascriptStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1825); 
				((JavascriptStatementContext)_localctx).exp = javascript_expression(0);
				setState(1826); 
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_expressionContext extends ParserRuleContext {
		public Javascript_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_expression; }
	 
		public Javascript_expressionContext() { }
		public void copyFrom(Javascript_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavascriptSelectorExpressionContext extends Javascript_expressionContext {
		public Javascript_expressionContext parent;
		public Javascript_selector_expressionContext child;
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public Javascript_selector_expressionContext javascript_selector_expression() {
			return getRuleContext(Javascript_selector_expressionContext.class,0);
		}
		public JavascriptSelectorExpressionContext(Javascript_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptSelectorExpression(this);
		}
	}
	public static class JavascriptPrimaryExpressionContext extends Javascript_expressionContext {
		public Javascript_primary_expressionContext exp;
		public Javascript_primary_expressionContext javascript_primary_expression() {
			return getRuleContext(Javascript_primary_expressionContext.class,0);
		}
		public JavascriptPrimaryExpressionContext(Javascript_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptPrimaryExpression(this);
		}
	}

	public final Javascript_expressionContext javascript_expression() throws RecognitionException {
		return javascript_expression(0);
	}

	private Javascript_expressionContext javascript_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Javascript_expressionContext _localctx = new Javascript_expressionContext(_ctx, _parentState);
		Javascript_expressionContext _prevctx = _localctx;
		int _startState = 292;
		enterRecursionRule(_localctx, 292, RULE_javascript_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new JavascriptPrimaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1831); 
			((JavascriptPrimaryExpressionContext)_localctx).exp = javascript_primary_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(1837);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,147,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JavascriptSelectorExpressionContext(new Javascript_expressionContext(_parentctx, _parentState));
					((JavascriptSelectorExpressionContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_javascript_expression);
					setState(1833);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1834); 
					((JavascriptSelectorExpressionContext)_localctx).child = javascript_selector_expression();
					}
					} 
				}
				setState(1839);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,147,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Javascript_primary_expressionContext extends ParserRuleContext {
		public Javascript_this_expressionContext javascript_this_expression() {
			return getRuleContext(Javascript_this_expressionContext.class,0);
		}
		public Javascript_new_expressionContext javascript_new_expression() {
			return getRuleContext(Javascript_new_expressionContext.class,0);
		}
		public Javascript_parenthesis_expressionContext javascript_parenthesis_expression() {
			return getRuleContext(Javascript_parenthesis_expressionContext.class,0);
		}
		public Javascript_identifier_expressionContext javascript_identifier_expression() {
			return getRuleContext(Javascript_identifier_expressionContext.class,0);
		}
		public Javascript_literal_expressionContext javascript_literal_expression() {
			return getRuleContext(Javascript_literal_expressionContext.class,0);
		}
		public Javascript_method_expressionContext javascript_method_expression() {
			return getRuleContext(Javascript_method_expressionContext.class,0);
		}
		public Javascript_item_expressionContext javascript_item_expression() {
			return getRuleContext(Javascript_item_expressionContext.class,0);
		}
		public Javascript_primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_primary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_primary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_primary_expression(this);
		}
	}

	public final Javascript_primary_expressionContext javascript_primary_expression() throws RecognitionException {
		Javascript_primary_expressionContext _localctx = new Javascript_primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 294, RULE_javascript_primary_expression);
		try {
			setState(1847);
			switch ( getInterpreter().adaptivePredict(_input,148,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1840); 
				javascript_this_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1841); 
				javascript_new_expression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1842); 
				javascript_parenthesis_expression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1843); 
				javascript_identifier_expression();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1844); 
				javascript_literal_expression();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1845); 
				javascript_method_expression();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1846); 
				javascript_item_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_this_expressionContext extends ParserRuleContext {
		public This_expressionContext this_expression() {
			return getRuleContext(This_expressionContext.class,0);
		}
		public Javascript_this_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_this_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_this_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_this_expression(this);
		}
	}

	public final Javascript_this_expressionContext javascript_this_expression() throws RecognitionException {
		Javascript_this_expressionContext _localctx = new Javascript_this_expressionContext(_ctx, getState());
		enterRule(_localctx, 296, RULE_javascript_this_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1849); 
			this_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_new_expressionContext extends ParserRuleContext {
		public New_tokenContext new_token() {
			return getRuleContext(New_tokenContext.class,0);
		}
		public Javascript_method_expressionContext javascript_method_expression() {
			return getRuleContext(Javascript_method_expressionContext.class,0);
		}
		public Javascript_new_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_new_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_new_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_new_expression(this);
		}
	}

	public final Javascript_new_expressionContext javascript_new_expression() throws RecognitionException {
		Javascript_new_expressionContext _localctx = new Javascript_new_expressionContext(_ctx, getState());
		enterRule(_localctx, 298, RULE_javascript_new_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1851); 
			new_token();
			setState(1852); 
			javascript_method_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_selector_expressionContext extends ParserRuleContext {
		public Javascript_selector_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_selector_expression; }
	 
		public Javascript_selector_expressionContext() { }
		public void copyFrom(Javascript_selector_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaScriptMemberExpressionContext extends Javascript_selector_expressionContext {
		public Javascript_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Javascript_identifierContext javascript_identifier() {
			return getRuleContext(Javascript_identifierContext.class,0);
		}
		public JavaScriptMemberExpressionContext(Javascript_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaScriptMemberExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaScriptMemberExpression(this);
		}
	}
	public static class JavaScriptItemExpressionContext extends Javascript_selector_expressionContext {
		public Javascript_item_expressionContext exp;
		public Javascript_item_expressionContext javascript_item_expression() {
			return getRuleContext(Javascript_item_expressionContext.class,0);
		}
		public JavaScriptItemExpressionContext(Javascript_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaScriptItemExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaScriptItemExpression(this);
		}
	}
	public static class JavaScriptMethodExpressionContext extends Javascript_selector_expressionContext {
		public Javascript_method_expressionContext method;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Javascript_method_expressionContext javascript_method_expression() {
			return getRuleContext(Javascript_method_expressionContext.class,0);
		}
		public JavaScriptMethodExpressionContext(Javascript_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaScriptMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaScriptMethodExpression(this);
		}
	}

	public final Javascript_selector_expressionContext javascript_selector_expression() throws RecognitionException {
		Javascript_selector_expressionContext _localctx = new Javascript_selector_expressionContext(_ctx, getState());
		enterRule(_localctx, 300, RULE_javascript_selector_expression);
		try {
			setState(1859);
			switch ( getInterpreter().adaptivePredict(_input,149,_ctx) ) {
			case 1:
				_localctx = new JavaScriptMethodExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1854); 
				match(DOT);
				setState(1855); 
				((JavaScriptMethodExpressionContext)_localctx).method = javascript_method_expression();
				}
				break;
			case 2:
				_localctx = new JavaScriptMemberExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1856); 
				match(DOT);
				setState(1857); 
				((JavaScriptMemberExpressionContext)_localctx).name = javascript_identifier();
				}
				break;
			case 3:
				_localctx = new JavaScriptItemExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1858); 
				((JavaScriptItemExpressionContext)_localctx).exp = javascript_item_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_method_expressionContext extends ParserRuleContext {
		public Javascript_identifierContext name;
		public Javascript_argumentsContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Javascript_identifierContext javascript_identifier() {
			return getRuleContext(Javascript_identifierContext.class,0);
		}
		public Javascript_argumentsContext javascript_arguments() {
			return getRuleContext(Javascript_argumentsContext.class,0);
		}
		public Javascript_method_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_method_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_method_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_method_expression(this);
		}
	}

	public final Javascript_method_expressionContext javascript_method_expression() throws RecognitionException {
		Javascript_method_expressionContext _localctx = new Javascript_method_expressionContext(_ctx, getState());
		enterRule(_localctx, 302, RULE_javascript_method_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1861); 
			((Javascript_method_expressionContext)_localctx).name = javascript_identifier();
			setState(1862); 
			match(LPAR);
			setState(1864);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << LBRAK) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (SELF - 121)) | (1L << (TEST - 121)) | (1L << (THIS - 121)) | (1L << (WRITE - 121)) | (1L << (BOOLEAN_LITERAL - 121)) | (1L << (CHAR_LITERAL - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)) | (1L << (DOLLAR_IDENTIFIER - 121)) | (1L << (TEXT_LITERAL - 121)) | (1L << (INTEGER_LITERAL - 121)) | (1L << (DECIMAL_LITERAL - 121)))) != 0)) {
				{
				setState(1863); 
				((Javascript_method_expressionContext)_localctx).args = javascript_arguments(0);
				}
			}

			setState(1866); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_argumentsContext extends ParserRuleContext {
		public Javascript_argumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_arguments; }
	 
		public Javascript_argumentsContext() { }
		public void copyFrom(Javascript_argumentsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavascriptArgumentListContext extends Javascript_argumentsContext {
		public Javascript_expressionContext item;
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public JavascriptArgumentListContext(Javascript_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptArgumentList(this);
		}
	}
	public static class JavascriptArgumentListItemContext extends Javascript_argumentsContext {
		public Javascript_argumentsContext items;
		public Javascript_expressionContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Javascript_argumentsContext javascript_arguments() {
			return getRuleContext(Javascript_argumentsContext.class,0);
		}
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public JavascriptArgumentListItemContext(Javascript_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptArgumentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptArgumentListItem(this);
		}
	}

	public final Javascript_argumentsContext javascript_arguments() throws RecognitionException {
		return javascript_arguments(0);
	}

	private Javascript_argumentsContext javascript_arguments(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Javascript_argumentsContext _localctx = new Javascript_argumentsContext(_ctx, _parentState);
		Javascript_argumentsContext _prevctx = _localctx;
		int _startState = 304;
		enterRecursionRule(_localctx, 304, RULE_javascript_arguments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new JavascriptArgumentListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1869); 
			((JavascriptArgumentListContext)_localctx).item = javascript_expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(1876);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,151,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JavascriptArgumentListItemContext(new Javascript_argumentsContext(_parentctx, _parentState));
					((JavascriptArgumentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_javascript_arguments);
					setState(1871);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1872); 
					match(COMMA);
					setState(1873); 
					((JavascriptArgumentListItemContext)_localctx).item = javascript_expression(0);
					}
					} 
				}
				setState(1878);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,151,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Javascript_item_expressionContext extends ParserRuleContext {
		public Javascript_expressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public Javascript_item_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_item_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_item_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_item_expression(this);
		}
	}

	public final Javascript_item_expressionContext javascript_item_expression() throws RecognitionException {
		Javascript_item_expressionContext _localctx = new Javascript_item_expressionContext(_ctx, getState());
		enterRule(_localctx, 306, RULE_javascript_item_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1879); 
			match(LBRAK);
			setState(1880); 
			((Javascript_item_expressionContext)_localctx).exp = javascript_expression(0);
			setState(1881); 
			match(RBRAK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_parenthesis_expressionContext extends ParserRuleContext {
		public Javascript_expressionContext exp;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Javascript_expressionContext javascript_expression() {
			return getRuleContext(Javascript_expressionContext.class,0);
		}
		public Javascript_parenthesis_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_parenthesis_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_parenthesis_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_parenthesis_expression(this);
		}
	}

	public final Javascript_parenthesis_expressionContext javascript_parenthesis_expression() throws RecognitionException {
		Javascript_parenthesis_expressionContext _localctx = new Javascript_parenthesis_expressionContext(_ctx, getState());
		enterRule(_localctx, 308, RULE_javascript_parenthesis_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1883); 
			match(LPAR);
			setState(1884); 
			((Javascript_parenthesis_expressionContext)_localctx).exp = javascript_expression(0);
			setState(1885); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_identifier_expressionContext extends ParserRuleContext {
		public Javascript_identifierContext name;
		public Javascript_identifierContext javascript_identifier() {
			return getRuleContext(Javascript_identifierContext.class,0);
		}
		public Javascript_identifier_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_identifier_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_identifier_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_identifier_expression(this);
		}
	}

	public final Javascript_identifier_expressionContext javascript_identifier_expression() throws RecognitionException {
		Javascript_identifier_expressionContext _localctx = new Javascript_identifier_expressionContext(_ctx, getState());
		enterRule(_localctx, 310, RULE_javascript_identifier_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1887); 
			((Javascript_identifier_expressionContext)_localctx).name = javascript_identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_literal_expressionContext extends ParserRuleContext {
		public Javascript_literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_literal_expression; }
	 
		public Javascript_literal_expressionContext() { }
		public void copyFrom(Javascript_literal_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavascriptIntegerLiteralContext extends Javascript_literal_expressionContext {
		public Token t;
		public TerminalNode INTEGER_LITERAL() { return getToken(OParser.INTEGER_LITERAL, 0); }
		public JavascriptIntegerLiteralContext(Javascript_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptIntegerLiteral(this);
		}
	}
	public static class JavascriptBooleanLiteralContext extends Javascript_literal_expressionContext {
		public Token t;
		public TerminalNode BOOLEAN_LITERAL() { return getToken(OParser.BOOLEAN_LITERAL, 0); }
		public JavascriptBooleanLiteralContext(Javascript_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptBooleanLiteral(this);
		}
	}
	public static class JavascriptCharacterLiteralContext extends Javascript_literal_expressionContext {
		public Token t;
		public TerminalNode CHAR_LITERAL() { return getToken(OParser.CHAR_LITERAL, 0); }
		public JavascriptCharacterLiteralContext(Javascript_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptCharacterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptCharacterLiteral(this);
		}
	}
	public static class JavascriptTextLiteralContext extends Javascript_literal_expressionContext {
		public Token t;
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public JavascriptTextLiteralContext(Javascript_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptTextLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptTextLiteral(this);
		}
	}
	public static class JavascriptDecimalLiteralContext extends Javascript_literal_expressionContext {
		public Token t;
		public TerminalNode DECIMAL_LITERAL() { return getToken(OParser.DECIMAL_LITERAL, 0); }
		public JavascriptDecimalLiteralContext(Javascript_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascriptDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascriptDecimalLiteral(this);
		}
	}

	public final Javascript_literal_expressionContext javascript_literal_expression() throws RecognitionException {
		Javascript_literal_expressionContext _localctx = new Javascript_literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 312, RULE_javascript_literal_expression);
		try {
			setState(1894);
			switch (_input.LA(1)) {
			case INTEGER_LITERAL:
				_localctx = new JavascriptIntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1889); 
				((JavascriptIntegerLiteralContext)_localctx).t = match(INTEGER_LITERAL);
				}
				break;
			case DECIMAL_LITERAL:
				_localctx = new JavascriptDecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1890); 
				((JavascriptDecimalLiteralContext)_localctx).t = match(DECIMAL_LITERAL);
				}
				break;
			case TEXT_LITERAL:
				_localctx = new JavascriptTextLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1891); 
				((JavascriptTextLiteralContext)_localctx).t = match(TEXT_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new JavascriptBooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1892); 
				((JavascriptBooleanLiteralContext)_localctx).t = match(BOOLEAN_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				_localctx = new JavascriptCharacterLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1893); 
				((JavascriptCharacterLiteralContext)_localctx).t = match(CHAR_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Javascript_identifierContext extends ParserRuleContext {
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public TerminalNode SYMBOL_IDENTIFIER() { return getToken(OParser.SYMBOL_IDENTIFIER, 0); }
		public TerminalNode DOLLAR_IDENTIFIER() { return getToken(OParser.DOLLAR_IDENTIFIER, 0); }
		public TerminalNode TYPE_IDENTIFIER() { return getToken(OParser.TYPE_IDENTIFIER, 0); }
		public TerminalNode BOOLEAN() { return getToken(OParser.BOOLEAN, 0); }
		public TerminalNode CHARACTER() { return getToken(OParser.CHARACTER, 0); }
		public TerminalNode TEXT() { return getToken(OParser.TEXT, 0); }
		public TerminalNode INTEGER() { return getToken(OParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(OParser.DECIMAL, 0); }
		public TerminalNode DATE() { return getToken(OParser.DATE, 0); }
		public TerminalNode TIME() { return getToken(OParser.TIME, 0); }
		public TerminalNode DATETIME() { return getToken(OParser.DATETIME, 0); }
		public TerminalNode PERIOD() { return getToken(OParser.PERIOD, 0); }
		public TerminalNode READ() { return getToken(OParser.READ, 0); }
		public TerminalNode WRITE() { return getToken(OParser.WRITE, 0); }
		public TerminalNode TEST() { return getToken(OParser.TEST, 0); }
		public Javascript_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javascript_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavascript_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavascript_identifier(this);
		}
	}

	public final Javascript_identifierContext javascript_identifier() throws RecognitionException {
		Javascript_identifierContext _localctx = new Javascript_identifierContext(_ctx, getState());
		enterRule(_localctx, 314, RULE_javascript_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1896);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (TEST - 121)) | (1L << (WRITE - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)) | (1L << (DOLLAR_IDENTIFIER - 121)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_statementContext extends ParserRuleContext {
		public Python_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_statement; }
	 
		public Python_statementContext() { }
		public void copyFrom(Python_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonStatementContext extends Python_statementContext {
		public Python_expressionContext exp;
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonStatementContext(Python_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonStatement(this);
		}
	}
	public static class PythonReturnStatementContext extends Python_statementContext {
		public Python_expressionContext exp;
		public TerminalNode RETURN() { return getToken(OParser.RETURN, 0); }
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonReturnStatementContext(Python_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonReturnStatement(this);
		}
	}

	public final Python_statementContext python_statement() throws RecognitionException {
		Python_statementContext _localctx = new Python_statementContext(_ctx, getState());
		enterRule(_localctx, 316, RULE_python_statement);
		try {
			setState(1901);
			switch (_input.LA(1)) {
			case RETURN:
				_localctx = new PythonReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1898); 
				match(RETURN);
				setState(1899); 
				((PythonReturnStatementContext)_localctx).exp = python_expression(0);
				}
				break;
			case LPAR:
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case READ:
			case SELF:
			case TEST:
			case THIS:
			case WRITE:
			case BOOLEAN_LITERAL:
			case CHAR_LITERAL:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case TEXT_LITERAL:
			case INTEGER_LITERAL:
			case DECIMAL_LITERAL:
				_localctx = new PythonStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1900); 
				((PythonStatementContext)_localctx).exp = python_expression(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_expressionContext extends ParserRuleContext {
		public Python_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_expression; }
	 
		public Python_expressionContext() { }
		public void copyFrom(Python_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonSelectorExpressionContext extends Python_expressionContext {
		public Python_expressionContext parent;
		public Python_selector_expressionContext child;
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public Python_selector_expressionContext python_selector_expression() {
			return getRuleContext(Python_selector_expressionContext.class,0);
		}
		public PythonSelectorExpressionContext(Python_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonSelectorExpression(this);
		}
	}
	public static class PythonPrimaryExpressionContext extends Python_expressionContext {
		public Python_primary_expressionContext exp;
		public Python_primary_expressionContext python_primary_expression() {
			return getRuleContext(Python_primary_expressionContext.class,0);
		}
		public PythonPrimaryExpressionContext(Python_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonPrimaryExpression(this);
		}
	}

	public final Python_expressionContext python_expression() throws RecognitionException {
		return python_expression(0);
	}

	private Python_expressionContext python_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Python_expressionContext _localctx = new Python_expressionContext(_ctx, _parentState);
		Python_expressionContext _prevctx = _localctx;
		int _startState = 318;
		enterRecursionRule(_localctx, 318, RULE_python_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new PythonPrimaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1904); 
			((PythonPrimaryExpressionContext)_localctx).exp = python_primary_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(1910);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,154,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PythonSelectorExpressionContext(new Python_expressionContext(_parentctx, _parentState));
					((PythonSelectorExpressionContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_python_expression);
					setState(1906);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1907); 
					((PythonSelectorExpressionContext)_localctx).child = python_selector_expression();
					}
					} 
				}
				setState(1912);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,154,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Python_primary_expressionContext extends ParserRuleContext {
		public Python_primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_primary_expression; }
	 
		public Python_primary_expressionContext() { }
		public void copyFrom(Python_primary_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonParenthesisExpressionContext extends Python_primary_expressionContext {
		public Python_parenthesis_expressionContext exp;
		public Python_parenthesis_expressionContext python_parenthesis_expression() {
			return getRuleContext(Python_parenthesis_expressionContext.class,0);
		}
		public PythonParenthesisExpressionContext(Python_primary_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonParenthesisExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonParenthesisExpression(this);
		}
	}
	public static class PythonIdentifierExpressionContext extends Python_primary_expressionContext {
		public Python_identifier_expressionContext exp;
		public Python_identifier_expressionContext python_identifier_expression() {
			return getRuleContext(Python_identifier_expressionContext.class,0);
		}
		public PythonIdentifierExpressionContext(Python_primary_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonIdentifierExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonIdentifierExpression(this);
		}
	}
	public static class PythonLiteralExpressionContext extends Python_primary_expressionContext {
		public Python_literal_expressionContext exp;
		public Python_literal_expressionContext python_literal_expression() {
			return getRuleContext(Python_literal_expressionContext.class,0);
		}
		public PythonLiteralExpressionContext(Python_primary_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonLiteralExpression(this);
		}
	}
	public static class PythonGlobalMethodExpressionContext extends Python_primary_expressionContext {
		public Python_method_expressionContext exp;
		public Python_method_expressionContext python_method_expression() {
			return getRuleContext(Python_method_expressionContext.class,0);
		}
		public PythonGlobalMethodExpressionContext(Python_primary_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonGlobalMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonGlobalMethodExpression(this);
		}
	}

	public final Python_primary_expressionContext python_primary_expression() throws RecognitionException {
		Python_primary_expressionContext _localctx = new Python_primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 320, RULE_python_primary_expression);
		try {
			setState(1917);
			switch ( getInterpreter().adaptivePredict(_input,155,_ctx) ) {
			case 1:
				_localctx = new PythonParenthesisExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1913); 
				((PythonParenthesisExpressionContext)_localctx).exp = python_parenthesis_expression();
				}
				break;
			case 2:
				_localctx = new PythonIdentifierExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1914); 
				((PythonIdentifierExpressionContext)_localctx).exp = python_identifier_expression(0);
				}
				break;
			case 3:
				_localctx = new PythonLiteralExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1915); 
				((PythonLiteralExpressionContext)_localctx).exp = python_literal_expression();
				}
				break;
			case 4:
				_localctx = new PythonGlobalMethodExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1916); 
				((PythonGlobalMethodExpressionContext)_localctx).exp = python_method_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_selector_expressionContext extends ParserRuleContext {
		public Python_selector_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_selector_expression; }
	 
		public Python_selector_expressionContext() { }
		public void copyFrom(Python_selector_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonMethodExpressionContext extends Python_selector_expressionContext {
		public Python_method_expressionContext exp;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Python_method_expressionContext python_method_expression() {
			return getRuleContext(Python_method_expressionContext.class,0);
		}
		public PythonMethodExpressionContext(Python_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonMethodExpression(this);
		}
	}
	public static class PythonItemExpressionContext extends Python_selector_expressionContext {
		public Python_expressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonItemExpressionContext(Python_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonItemExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonItemExpression(this);
		}
	}

	public final Python_selector_expressionContext python_selector_expression() throws RecognitionException {
		Python_selector_expressionContext _localctx = new Python_selector_expressionContext(_ctx, getState());
		enterRule(_localctx, 322, RULE_python_selector_expression);
		try {
			setState(1925);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new PythonMethodExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1919); 
				match(DOT);
				setState(1920); 
				((PythonMethodExpressionContext)_localctx).exp = python_method_expression();
				}
				break;
			case LBRAK:
				_localctx = new PythonItemExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1921); 
				match(LBRAK);
				setState(1922); 
				((PythonItemExpressionContext)_localctx).exp = python_expression(0);
				setState(1923); 
				match(RBRAK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_method_expressionContext extends ParserRuleContext {
		public Python_identifierContext name;
		public Python_argument_listContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Python_identifierContext python_identifier() {
			return getRuleContext(Python_identifierContext.class,0);
		}
		public Python_argument_listContext python_argument_list() {
			return getRuleContext(Python_argument_listContext.class,0);
		}
		public Python_method_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_method_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython_method_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython_method_expression(this);
		}
	}

	public final Python_method_expressionContext python_method_expression() throws RecognitionException {
		Python_method_expressionContext _localctx = new Python_method_expressionContext(_ctx, getState());
		enterRule(_localctx, 324, RULE_python_method_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1927); 
			((Python_method_expressionContext)_localctx).name = python_identifier();
			setState(1928); 
			match(LPAR);
			setState(1930);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (SELF - 121)) | (1L << (TEST - 121)) | (1L << (THIS - 121)) | (1L << (WRITE - 121)) | (1L << (BOOLEAN_LITERAL - 121)) | (1L << (CHAR_LITERAL - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)) | (1L << (DOLLAR_IDENTIFIER - 121)) | (1L << (TEXT_LITERAL - 121)) | (1L << (INTEGER_LITERAL - 121)) | (1L << (DECIMAL_LITERAL - 121)))) != 0)) {
				{
				setState(1929); 
				((Python_method_expressionContext)_localctx).args = python_argument_list();
				}
			}

			setState(1932); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_argument_listContext extends ParserRuleContext {
		public Python_argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_argument_list; }
	 
		public Python_argument_listContext() { }
		public void copyFrom(Python_argument_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonOrdinalOnlyArgumentListContext extends Python_argument_listContext {
		public Python_ordinal_argument_listContext ordinal;
		public Python_ordinal_argument_listContext python_ordinal_argument_list() {
			return getRuleContext(Python_ordinal_argument_listContext.class,0);
		}
		public PythonOrdinalOnlyArgumentListContext(Python_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonOrdinalOnlyArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonOrdinalOnlyArgumentList(this);
		}
	}
	public static class PythonNamedOnlyArgumentListContext extends Python_argument_listContext {
		public Python_named_argument_listContext named;
		public Python_named_argument_listContext python_named_argument_list() {
			return getRuleContext(Python_named_argument_listContext.class,0);
		}
		public PythonNamedOnlyArgumentListContext(Python_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonNamedOnlyArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonNamedOnlyArgumentList(this);
		}
	}
	public static class PythonArgumentListContext extends Python_argument_listContext {
		public Python_ordinal_argument_listContext ordinal;
		public Python_named_argument_listContext named;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Python_ordinal_argument_listContext python_ordinal_argument_list() {
			return getRuleContext(Python_ordinal_argument_listContext.class,0);
		}
		public Python_named_argument_listContext python_named_argument_list() {
			return getRuleContext(Python_named_argument_listContext.class,0);
		}
		public PythonArgumentListContext(Python_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonArgumentList(this);
		}
	}

	public final Python_argument_listContext python_argument_list() throws RecognitionException {
		Python_argument_listContext _localctx = new Python_argument_listContext(_ctx, getState());
		enterRule(_localctx, 326, RULE_python_argument_list);
		try {
			setState(1940);
			switch ( getInterpreter().adaptivePredict(_input,158,_ctx) ) {
			case 1:
				_localctx = new PythonOrdinalOnlyArgumentListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1934); 
				((PythonOrdinalOnlyArgumentListContext)_localctx).ordinal = python_ordinal_argument_list(0);
				}
				break;
			case 2:
				_localctx = new PythonNamedOnlyArgumentListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1935); 
				((PythonNamedOnlyArgumentListContext)_localctx).named = python_named_argument_list(0);
				}
				break;
			case 3:
				_localctx = new PythonArgumentListContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1936); 
				((PythonArgumentListContext)_localctx).ordinal = python_ordinal_argument_list(0);
				setState(1937); 
				match(COMMA);
				setState(1938); 
				((PythonArgumentListContext)_localctx).named = python_named_argument_list(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_ordinal_argument_listContext extends ParserRuleContext {
		public Python_ordinal_argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_ordinal_argument_list; }
	 
		public Python_ordinal_argument_listContext() { }
		public void copyFrom(Python_ordinal_argument_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonOrdinalArgumentListContext extends Python_ordinal_argument_listContext {
		public Python_expressionContext item;
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonOrdinalArgumentListContext(Python_ordinal_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonOrdinalArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonOrdinalArgumentList(this);
		}
	}
	public static class PythonOrdinalArgumentListItemContext extends Python_ordinal_argument_listContext {
		public Python_ordinal_argument_listContext items;
		public Python_expressionContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Python_ordinal_argument_listContext python_ordinal_argument_list() {
			return getRuleContext(Python_ordinal_argument_listContext.class,0);
		}
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonOrdinalArgumentListItemContext(Python_ordinal_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonOrdinalArgumentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonOrdinalArgumentListItem(this);
		}
	}

	public final Python_ordinal_argument_listContext python_ordinal_argument_list() throws RecognitionException {
		return python_ordinal_argument_list(0);
	}

	private Python_ordinal_argument_listContext python_ordinal_argument_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Python_ordinal_argument_listContext _localctx = new Python_ordinal_argument_listContext(_ctx, _parentState);
		Python_ordinal_argument_listContext _prevctx = _localctx;
		int _startState = 328;
		enterRecursionRule(_localctx, 328, RULE_python_ordinal_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new PythonOrdinalArgumentListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1943); 
			((PythonOrdinalArgumentListContext)_localctx).item = python_expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(1950);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,159,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PythonOrdinalArgumentListItemContext(new Python_ordinal_argument_listContext(_parentctx, _parentState));
					((PythonOrdinalArgumentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_python_ordinal_argument_list);
					setState(1945);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1946); 
					match(COMMA);
					setState(1947); 
					((PythonOrdinalArgumentListItemContext)_localctx).item = python_expression(0);
					}
					} 
				}
				setState(1952);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,159,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Python_named_argument_listContext extends ParserRuleContext {
		public Python_named_argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_named_argument_list; }
	 
		public Python_named_argument_listContext() { }
		public void copyFrom(Python_named_argument_listContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonNamedArgumentListContext extends Python_named_argument_listContext {
		public Python_identifierContext name;
		public Python_expressionContext exp;
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public Python_identifierContext python_identifier() {
			return getRuleContext(Python_identifierContext.class,0);
		}
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonNamedArgumentListContext(Python_named_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonNamedArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonNamedArgumentList(this);
		}
	}
	public static class PythonNamedArgumentListItemContext extends Python_named_argument_listContext {
		public Python_named_argument_listContext items;
		public Python_identifierContext name;
		public Python_expressionContext exp;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public TerminalNode EQ() { return getToken(OParser.EQ, 0); }
		public Python_named_argument_listContext python_named_argument_list() {
			return getRuleContext(Python_named_argument_listContext.class,0);
		}
		public Python_identifierContext python_identifier() {
			return getRuleContext(Python_identifierContext.class,0);
		}
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public PythonNamedArgumentListItemContext(Python_named_argument_listContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonNamedArgumentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonNamedArgumentListItem(this);
		}
	}

	public final Python_named_argument_listContext python_named_argument_list() throws RecognitionException {
		return python_named_argument_list(0);
	}

	private Python_named_argument_listContext python_named_argument_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Python_named_argument_listContext _localctx = new Python_named_argument_listContext(_ctx, _parentState);
		Python_named_argument_listContext _prevctx = _localctx;
		int _startState = 330;
		enterRecursionRule(_localctx, 330, RULE_python_named_argument_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new PythonNamedArgumentListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(1954); 
			((PythonNamedArgumentListContext)_localctx).name = python_identifier();
			setState(1955); 
			match(EQ);
			setState(1956); 
			((PythonNamedArgumentListContext)_localctx).exp = python_expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(1966);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,160,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PythonNamedArgumentListItemContext(new Python_named_argument_listContext(_parentctx, _parentState));
					((PythonNamedArgumentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_python_named_argument_list);
					setState(1958);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1959); 
					match(COMMA);
					setState(1960); 
					((PythonNamedArgumentListItemContext)_localctx).name = python_identifier();
					setState(1961); 
					match(EQ);
					setState(1962); 
					((PythonNamedArgumentListItemContext)_localctx).exp = python_expression(0);
					}
					} 
				}
				setState(1968);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,160,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Python_parenthesis_expressionContext extends ParserRuleContext {
		public Python_expressionContext exp;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Python_expressionContext python_expression() {
			return getRuleContext(Python_expressionContext.class,0);
		}
		public Python_parenthesis_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_parenthesis_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython_parenthesis_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython_parenthesis_expression(this);
		}
	}

	public final Python_parenthesis_expressionContext python_parenthesis_expression() throws RecognitionException {
		Python_parenthesis_expressionContext _localctx = new Python_parenthesis_expressionContext(_ctx, getState());
		enterRule(_localctx, 332, RULE_python_parenthesis_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1969); 
			match(LPAR);
			setState(1970); 
			((Python_parenthesis_expressionContext)_localctx).exp = python_expression(0);
			setState(1971); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_identifier_expressionContext extends ParserRuleContext {
		public Python_identifier_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_identifier_expression; }
	 
		public Python_identifier_expressionContext() { }
		public void copyFrom(Python_identifier_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonChildIdentifierContext extends Python_identifier_expressionContext {
		public Python_identifier_expressionContext parent;
		public Python_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Python_identifier_expressionContext python_identifier_expression() {
			return getRuleContext(Python_identifier_expressionContext.class,0);
		}
		public Python_identifierContext python_identifier() {
			return getRuleContext(Python_identifierContext.class,0);
		}
		public PythonChildIdentifierContext(Python_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonChildIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonChildIdentifier(this);
		}
	}
	public static class PythonPromptoIdentifierContext extends Python_identifier_expressionContext {
		public TerminalNode DOLLAR_IDENTIFIER() { return getToken(OParser.DOLLAR_IDENTIFIER, 0); }
		public PythonPromptoIdentifierContext(Python_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonPromptoIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonPromptoIdentifier(this);
		}
	}
	public static class PythonIdentifierContext extends Python_identifier_expressionContext {
		public Python_identifierContext name;
		public Python_identifierContext python_identifier() {
			return getRuleContext(Python_identifierContext.class,0);
		}
		public PythonIdentifierContext(Python_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonIdentifier(this);
		}
	}

	public final Python_identifier_expressionContext python_identifier_expression() throws RecognitionException {
		return python_identifier_expression(0);
	}

	private Python_identifier_expressionContext python_identifier_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Python_identifier_expressionContext _localctx = new Python_identifier_expressionContext(_ctx, _parentState);
		Python_identifier_expressionContext _prevctx = _localctx;
		int _startState = 334;
		enterRecursionRule(_localctx, 334, RULE_python_identifier_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1976);
			switch (_input.LA(1)) {
			case DOLLAR_IDENTIFIER:
				{
				_localctx = new PythonPromptoIdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(1974); 
				match(DOLLAR_IDENTIFIER);
				}
				break;
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case READ:
			case SELF:
			case TEST:
			case THIS:
			case WRITE:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
				{
				_localctx = new PythonIdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(1975); 
				((PythonIdentifierContext)_localctx).name = python_identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(1983);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,162,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PythonChildIdentifierContext(new Python_identifier_expressionContext(_parentctx, _parentState));
					((PythonChildIdentifierContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_python_identifier_expression);
					setState(1978);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1979); 
					match(DOT);
					setState(1980); 
					((PythonChildIdentifierContext)_localctx).name = python_identifier();
					}
					} 
				}
				setState(1985);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,162,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Python_literal_expressionContext extends ParserRuleContext {
		public Python_literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_literal_expression; }
	 
		public Python_literal_expressionContext() { }
		public void copyFrom(Python_literal_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PythonIntegerLiteralContext extends Python_literal_expressionContext {
		public Token t;
		public TerminalNode INTEGER_LITERAL() { return getToken(OParser.INTEGER_LITERAL, 0); }
		public PythonIntegerLiteralContext(Python_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonIntegerLiteral(this);
		}
	}
	public static class PythonBooleanLiteralContext extends Python_literal_expressionContext {
		public Token t;
		public TerminalNode BOOLEAN_LITERAL() { return getToken(OParser.BOOLEAN_LITERAL, 0); }
		public PythonBooleanLiteralContext(Python_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonBooleanLiteral(this);
		}
	}
	public static class PythonCharacterLiteralContext extends Python_literal_expressionContext {
		public Token t;
		public TerminalNode CHAR_LITERAL() { return getToken(OParser.CHAR_LITERAL, 0); }
		public PythonCharacterLiteralContext(Python_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonCharacterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonCharacterLiteral(this);
		}
	}
	public static class PythonTextLiteralContext extends Python_literal_expressionContext {
		public Token t;
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public PythonTextLiteralContext(Python_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonTextLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonTextLiteral(this);
		}
	}
	public static class PythonDecimalLiteralContext extends Python_literal_expressionContext {
		public Token t;
		public TerminalNode DECIMAL_LITERAL() { return getToken(OParser.DECIMAL_LITERAL, 0); }
		public PythonDecimalLiteralContext(Python_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPythonDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPythonDecimalLiteral(this);
		}
	}

	public final Python_literal_expressionContext python_literal_expression() throws RecognitionException {
		Python_literal_expressionContext _localctx = new Python_literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 336, RULE_python_literal_expression);
		try {
			setState(1991);
			switch (_input.LA(1)) {
			case INTEGER_LITERAL:
				_localctx = new PythonIntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1986); 
				((PythonIntegerLiteralContext)_localctx).t = match(INTEGER_LITERAL);
				}
				break;
			case DECIMAL_LITERAL:
				_localctx = new PythonDecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1987); 
				((PythonDecimalLiteralContext)_localctx).t = match(DECIMAL_LITERAL);
				}
				break;
			case TEXT_LITERAL:
				_localctx = new PythonTextLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(1988); 
				((PythonTextLiteralContext)_localctx).t = match(TEXT_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new PythonBooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(1989); 
				((PythonBooleanLiteralContext)_localctx).t = match(BOOLEAN_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				_localctx = new PythonCharacterLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(1990); 
				((PythonCharacterLiteralContext)_localctx).t = match(CHAR_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Python_identifierContext extends ParserRuleContext {
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public TerminalNode SYMBOL_IDENTIFIER() { return getToken(OParser.SYMBOL_IDENTIFIER, 0); }
		public TerminalNode TYPE_IDENTIFIER() { return getToken(OParser.TYPE_IDENTIFIER, 0); }
		public TerminalNode BOOLEAN() { return getToken(OParser.BOOLEAN, 0); }
		public TerminalNode CHARACTER() { return getToken(OParser.CHARACTER, 0); }
		public TerminalNode TEXT() { return getToken(OParser.TEXT, 0); }
		public TerminalNode INTEGER() { return getToken(OParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(OParser.DECIMAL, 0); }
		public TerminalNode DATE() { return getToken(OParser.DATE, 0); }
		public TerminalNode TIME() { return getToken(OParser.TIME, 0); }
		public TerminalNode DATETIME() { return getToken(OParser.DATETIME, 0); }
		public TerminalNode PERIOD() { return getToken(OParser.PERIOD, 0); }
		public TerminalNode READ() { return getToken(OParser.READ, 0); }
		public TerminalNode WRITE() { return getToken(OParser.WRITE, 0); }
		public TerminalNode TEST() { return getToken(OParser.TEST, 0); }
		public TerminalNode SELF() { return getToken(OParser.SELF, 0); }
		public TerminalNode THIS() { return getToken(OParser.THIS, 0); }
		public Python_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_python_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterPython_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitPython_identifier(this);
		}
	}

	public final Python_identifierContext python_identifier() throws RecognitionException {
		Python_identifierContext _localctx = new Python_identifierContext(_ctx, getState());
		enterRule(_localctx, 338, RULE_python_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1993);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (SELF - 121)) | (1L << (TEST - 121)) | (1L << (THIS - 121)) | (1L << (WRITE - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_statementContext extends ParserRuleContext {
		public Java_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_statement; }
	 
		public Java_statementContext() { }
		public void copyFrom(Java_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaReturnStatementContext extends Java_statementContext {
		public Java_expressionContext exp;
		public TerminalNode RETURN() { return getToken(OParser.RETURN, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public JavaReturnStatementContext(Java_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaReturnStatement(this);
		}
	}
	public static class JavaStatementContext extends Java_statementContext {
		public Java_expressionContext exp;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public JavaStatementContext(Java_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaStatement(this);
		}
	}

	public final Java_statementContext java_statement() throws RecognitionException {
		Java_statementContext _localctx = new Java_statementContext(_ctx, getState());
		enterRule(_localctx, 340, RULE_java_statement);
		try {
			setState(2002);
			switch (_input.LA(1)) {
			case RETURN:
				_localctx = new JavaReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(1995); 
				match(RETURN);
				setState(1996); 
				((JavaReturnStatementContext)_localctx).exp = java_expression(0);
				setState(1997); 
				match(SEMI);
				}
				break;
			case LPAR:
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case READ:
			case SELF:
			case TEST:
			case THIS:
			case WRITE:
			case BOOLEAN_LITERAL:
			case CHAR_LITERAL:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
			case NATIVE_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case TEXT_LITERAL:
			case INTEGER_LITERAL:
			case DECIMAL_LITERAL:
				_localctx = new JavaStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(1999); 
				((JavaStatementContext)_localctx).exp = java_expression(0);
				setState(2000); 
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_expressionContext extends ParserRuleContext {
		public Java_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_expression; }
	 
		public Java_expressionContext() { }
		public void copyFrom(Java_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaSelectorExpressionContext extends Java_expressionContext {
		public Java_expressionContext parent;
		public Java_selector_expressionContext child;
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public Java_selector_expressionContext java_selector_expression() {
			return getRuleContext(Java_selector_expressionContext.class,0);
		}
		public JavaSelectorExpressionContext(Java_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaSelectorExpression(this);
		}
	}
	public static class JavaPrimaryExpressionContext extends Java_expressionContext {
		public Java_primary_expressionContext exp;
		public Java_primary_expressionContext java_primary_expression() {
			return getRuleContext(Java_primary_expressionContext.class,0);
		}
		public JavaPrimaryExpressionContext(Java_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaPrimaryExpression(this);
		}
	}

	public final Java_expressionContext java_expression() throws RecognitionException {
		return java_expression(0);
	}

	private Java_expressionContext java_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Java_expressionContext _localctx = new Java_expressionContext(_ctx, _parentState);
		Java_expressionContext _prevctx = _localctx;
		int _startState = 342;
		enterRecursionRule(_localctx, 342, RULE_java_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new JavaPrimaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2005); 
			((JavaPrimaryExpressionContext)_localctx).exp = java_primary_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(2011);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,165,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JavaSelectorExpressionContext(new Java_expressionContext(_parentctx, _parentState));
					((JavaSelectorExpressionContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_java_expression);
					setState(2007);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2008); 
					((JavaSelectorExpressionContext)_localctx).child = java_selector_expression();
					}
					} 
				}
				setState(2013);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,165,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Java_primary_expressionContext extends ParserRuleContext {
		public Java_this_expressionContext java_this_expression() {
			return getRuleContext(Java_this_expressionContext.class,0);
		}
		public Java_new_expressionContext java_new_expression() {
			return getRuleContext(Java_new_expressionContext.class,0);
		}
		public Java_parenthesis_expressionContext java_parenthesis_expression() {
			return getRuleContext(Java_parenthesis_expressionContext.class,0);
		}
		public Java_identifier_expressionContext java_identifier_expression() {
			return getRuleContext(Java_identifier_expressionContext.class,0);
		}
		public Java_literal_expressionContext java_literal_expression() {
			return getRuleContext(Java_literal_expressionContext.class,0);
		}
		public Java_primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_primary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_primary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_primary_expression(this);
		}
	}

	public final Java_primary_expressionContext java_primary_expression() throws RecognitionException {
		Java_primary_expressionContext _localctx = new Java_primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 344, RULE_java_primary_expression);
		try {
			setState(2019);
			switch ( getInterpreter().adaptivePredict(_input,166,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2014); 
				java_this_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2015); 
				java_new_expression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2016); 
				java_parenthesis_expression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2017); 
				java_identifier_expression(0);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2018); 
				java_literal_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_this_expressionContext extends ParserRuleContext {
		public This_expressionContext this_expression() {
			return getRuleContext(This_expressionContext.class,0);
		}
		public Java_this_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_this_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_this_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_this_expression(this);
		}
	}

	public final Java_this_expressionContext java_this_expression() throws RecognitionException {
		Java_this_expressionContext _localctx = new Java_this_expressionContext(_ctx, getState());
		enterRule(_localctx, 346, RULE_java_this_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2021); 
			this_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_new_expressionContext extends ParserRuleContext {
		public New_tokenContext new_token() {
			return getRuleContext(New_tokenContext.class,0);
		}
		public Java_method_expressionContext java_method_expression() {
			return getRuleContext(Java_method_expressionContext.class,0);
		}
		public Java_new_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_new_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_new_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_new_expression(this);
		}
	}

	public final Java_new_expressionContext java_new_expression() throws RecognitionException {
		Java_new_expressionContext _localctx = new Java_new_expressionContext(_ctx, getState());
		enterRule(_localctx, 348, RULE_java_new_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2023); 
			new_token();
			setState(2024); 
			java_method_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_selector_expressionContext extends ParserRuleContext {
		public Java_selector_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_selector_expression; }
	 
		public Java_selector_expressionContext() { }
		public void copyFrom(Java_selector_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaItemExpressionContext extends Java_selector_expressionContext {
		public Java_item_expressionContext exp;
		public Java_item_expressionContext java_item_expression() {
			return getRuleContext(Java_item_expressionContext.class,0);
		}
		public JavaItemExpressionContext(Java_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaItemExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaItemExpression(this);
		}
	}
	public static class JavaMethodExpressionContext extends Java_selector_expressionContext {
		public Java_method_expressionContext exp;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Java_method_expressionContext java_method_expression() {
			return getRuleContext(Java_method_expressionContext.class,0);
		}
		public JavaMethodExpressionContext(Java_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaMethodExpression(this);
		}
	}

	public final Java_selector_expressionContext java_selector_expression() throws RecognitionException {
		Java_selector_expressionContext _localctx = new Java_selector_expressionContext(_ctx, getState());
		enterRule(_localctx, 350, RULE_java_selector_expression);
		try {
			setState(2029);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new JavaMethodExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2026); 
				match(DOT);
				setState(2027); 
				((JavaMethodExpressionContext)_localctx).exp = java_method_expression();
				}
				break;
			case LBRAK:
				_localctx = new JavaItemExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2028); 
				((JavaItemExpressionContext)_localctx).exp = java_item_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_method_expressionContext extends ParserRuleContext {
		public Java_identifierContext name;
		public Java_argumentsContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Java_identifierContext java_identifier() {
			return getRuleContext(Java_identifierContext.class,0);
		}
		public Java_argumentsContext java_arguments() {
			return getRuleContext(Java_argumentsContext.class,0);
		}
		public Java_method_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_method_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_method_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_method_expression(this);
		}
	}

	public final Java_method_expressionContext java_method_expression() throws RecognitionException {
		Java_method_expressionContext _localctx = new Java_method_expressionContext(_ctx, getState());
		enterRule(_localctx, 352, RULE_java_method_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2031); 
			((Java_method_expressionContext)_localctx).name = java_identifier();
			setState(2032); 
			match(LPAR);
			setState(2034);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (SELF - 121)) | (1L << (TEST - 121)) | (1L << (THIS - 121)) | (1L << (WRITE - 121)) | (1L << (BOOLEAN_LITERAL - 121)) | (1L << (CHAR_LITERAL - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)) | (1L << (NATIVE_IDENTIFIER - 121)) | (1L << (DOLLAR_IDENTIFIER - 121)) | (1L << (TEXT_LITERAL - 121)) | (1L << (INTEGER_LITERAL - 121)) | (1L << (DECIMAL_LITERAL - 121)))) != 0)) {
				{
				setState(2033); 
				((Java_method_expressionContext)_localctx).args = java_arguments(0);
				}
			}

			setState(2036); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_argumentsContext extends ParserRuleContext {
		public Java_argumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_arguments; }
	 
		public Java_argumentsContext() { }
		public void copyFrom(Java_argumentsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaArgumentListItemContext extends Java_argumentsContext {
		public Java_argumentsContext items;
		public Java_expressionContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Java_argumentsContext java_arguments() {
			return getRuleContext(Java_argumentsContext.class,0);
		}
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public JavaArgumentListItemContext(Java_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaArgumentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaArgumentListItem(this);
		}
	}
	public static class JavaArgumentListContext extends Java_argumentsContext {
		public Java_expressionContext item;
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public JavaArgumentListContext(Java_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaArgumentList(this);
		}
	}

	public final Java_argumentsContext java_arguments() throws RecognitionException {
		return java_arguments(0);
	}

	private Java_argumentsContext java_arguments(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Java_argumentsContext _localctx = new Java_argumentsContext(_ctx, _parentState);
		Java_argumentsContext _prevctx = _localctx;
		int _startState = 354;
		enterRecursionRule(_localctx, 354, RULE_java_arguments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new JavaArgumentListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2039); 
			((JavaArgumentListContext)_localctx).item = java_expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(2046);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JavaArgumentListItemContext(new Java_argumentsContext(_parentctx, _parentState));
					((JavaArgumentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_java_arguments);
					setState(2041);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2042); 
					match(COMMA);
					setState(2043); 
					((JavaArgumentListItemContext)_localctx).item = java_expression(0);
					}
					} 
				}
				setState(2048);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,169,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Java_item_expressionContext extends ParserRuleContext {
		public Java_expressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public Java_item_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_item_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_item_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_item_expression(this);
		}
	}

	public final Java_item_expressionContext java_item_expression() throws RecognitionException {
		Java_item_expressionContext _localctx = new Java_item_expressionContext(_ctx, getState());
		enterRule(_localctx, 356, RULE_java_item_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2049); 
			match(LBRAK);
			setState(2050); 
			((Java_item_expressionContext)_localctx).exp = java_expression(0);
			setState(2051); 
			match(RBRAK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_parenthesis_expressionContext extends ParserRuleContext {
		public Java_expressionContext exp;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Java_expressionContext java_expression() {
			return getRuleContext(Java_expressionContext.class,0);
		}
		public Java_parenthesis_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_parenthesis_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_parenthesis_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_parenthesis_expression(this);
		}
	}

	public final Java_parenthesis_expressionContext java_parenthesis_expression() throws RecognitionException {
		Java_parenthesis_expressionContext _localctx = new Java_parenthesis_expressionContext(_ctx, getState());
		enterRule(_localctx, 358, RULE_java_parenthesis_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2053); 
			match(LPAR);
			setState(2054); 
			((Java_parenthesis_expressionContext)_localctx).exp = java_expression(0);
			setState(2055); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_identifier_expressionContext extends ParserRuleContext {
		public Java_identifier_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_identifier_expression; }
	 
		public Java_identifier_expressionContext() { }
		public void copyFrom(Java_identifier_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaIdentifierContext extends Java_identifier_expressionContext {
		public Java_identifierContext name;
		public Java_identifierContext java_identifier() {
			return getRuleContext(Java_identifierContext.class,0);
		}
		public JavaIdentifierContext(Java_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaIdentifier(this);
		}
	}
	public static class JavaChildIdentifierContext extends Java_identifier_expressionContext {
		public Java_identifier_expressionContext parent;
		public Java_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Java_identifier_expressionContext java_identifier_expression() {
			return getRuleContext(Java_identifier_expressionContext.class,0);
		}
		public Java_identifierContext java_identifier() {
			return getRuleContext(Java_identifierContext.class,0);
		}
		public JavaChildIdentifierContext(Java_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaChildIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaChildIdentifier(this);
		}
	}

	public final Java_identifier_expressionContext java_identifier_expression() throws RecognitionException {
		return java_identifier_expression(0);
	}

	private Java_identifier_expressionContext java_identifier_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Java_identifier_expressionContext _localctx = new Java_identifier_expressionContext(_ctx, _parentState);
		Java_identifier_expressionContext _prevctx = _localctx;
		int _startState = 360;
		enterRecursionRule(_localctx, 360, RULE_java_identifier_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new JavaIdentifierContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2058); 
			((JavaIdentifierContext)_localctx).name = java_identifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(2065);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,170,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JavaChildIdentifierContext(new Java_identifier_expressionContext(_parentctx, _parentState));
					((JavaChildIdentifierContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_java_identifier_expression);
					setState(2060);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2061); 
					match(DOT);
					setState(2062); 
					((JavaChildIdentifierContext)_localctx).name = java_identifier();
					}
					} 
				}
				setState(2067);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,170,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Java_class_identifier_expressionContext extends ParserRuleContext {
		public Java_class_identifier_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_class_identifier_expression; }
	 
		public Java_class_identifier_expressionContext() { }
		public void copyFrom(Java_class_identifier_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaClassIdentifierContext extends Java_class_identifier_expressionContext {
		public Java_identifier_expressionContext klass;
		public Java_identifier_expressionContext java_identifier_expression() {
			return getRuleContext(Java_identifier_expressionContext.class,0);
		}
		public JavaClassIdentifierContext(Java_class_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaClassIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaClassIdentifier(this);
		}
	}
	public static class JavaChildClassIdentifierContext extends Java_class_identifier_expressionContext {
		public Java_class_identifier_expressionContext parent;
		public Token name;
		public Java_class_identifier_expressionContext java_class_identifier_expression() {
			return getRuleContext(Java_class_identifier_expressionContext.class,0);
		}
		public TerminalNode DOLLAR_IDENTIFIER() { return getToken(OParser.DOLLAR_IDENTIFIER, 0); }
		public JavaChildClassIdentifierContext(Java_class_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaChildClassIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaChildClassIdentifier(this);
		}
	}

	public final Java_class_identifier_expressionContext java_class_identifier_expression() throws RecognitionException {
		return java_class_identifier_expression(0);
	}

	private Java_class_identifier_expressionContext java_class_identifier_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Java_class_identifier_expressionContext _localctx = new Java_class_identifier_expressionContext(_ctx, _parentState);
		Java_class_identifier_expressionContext _prevctx = _localctx;
		int _startState = 362;
		enterRecursionRule(_localctx, 362, RULE_java_class_identifier_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new JavaClassIdentifierContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2069); 
			((JavaClassIdentifierContext)_localctx).klass = java_identifier_expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(2075);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,171,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new JavaChildClassIdentifierContext(new Java_class_identifier_expressionContext(_parentctx, _parentState));
					((JavaChildClassIdentifierContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_java_class_identifier_expression);
					setState(2071);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2072); 
					((JavaChildClassIdentifierContext)_localctx).name = match(DOLLAR_IDENTIFIER);
					}
					} 
				}
				setState(2077);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,171,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Java_literal_expressionContext extends ParserRuleContext {
		public Java_literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_literal_expression; }
	 
		public Java_literal_expressionContext() { }
		public void copyFrom(Java_literal_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JavaBooleanLiteralContext extends Java_literal_expressionContext {
		public Token t;
		public TerminalNode BOOLEAN_LITERAL() { return getToken(OParser.BOOLEAN_LITERAL, 0); }
		public JavaBooleanLiteralContext(Java_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaBooleanLiteral(this);
		}
	}
	public static class JavaCharacterLiteralContext extends Java_literal_expressionContext {
		public Token t;
		public TerminalNode CHAR_LITERAL() { return getToken(OParser.CHAR_LITERAL, 0); }
		public JavaCharacterLiteralContext(Java_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaCharacterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaCharacterLiteral(this);
		}
	}
	public static class JavaIntegerLiteralContext extends Java_literal_expressionContext {
		public Token t;
		public TerminalNode INTEGER_LITERAL() { return getToken(OParser.INTEGER_LITERAL, 0); }
		public JavaIntegerLiteralContext(Java_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaIntegerLiteral(this);
		}
	}
	public static class JavaTextLiteralContext extends Java_literal_expressionContext {
		public Token t;
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public JavaTextLiteralContext(Java_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaTextLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaTextLiteral(this);
		}
	}
	public static class JavaDecimalLiteralContext extends Java_literal_expressionContext {
		public Token t;
		public TerminalNode DECIMAL_LITERAL() { return getToken(OParser.DECIMAL_LITERAL, 0); }
		public JavaDecimalLiteralContext(Java_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJavaDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJavaDecimalLiteral(this);
		}
	}

	public final Java_literal_expressionContext java_literal_expression() throws RecognitionException {
		Java_literal_expressionContext _localctx = new Java_literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 364, RULE_java_literal_expression);
		try {
			setState(2083);
			switch (_input.LA(1)) {
			case INTEGER_LITERAL:
				_localctx = new JavaIntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2078); 
				((JavaIntegerLiteralContext)_localctx).t = match(INTEGER_LITERAL);
				}
				break;
			case DECIMAL_LITERAL:
				_localctx = new JavaDecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2079); 
				((JavaDecimalLiteralContext)_localctx).t = match(DECIMAL_LITERAL);
				}
				break;
			case TEXT_LITERAL:
				_localctx = new JavaTextLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2080); 
				((JavaTextLiteralContext)_localctx).t = match(TEXT_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new JavaBooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2081); 
				((JavaBooleanLiteralContext)_localctx).t = match(BOOLEAN_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				_localctx = new JavaCharacterLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2082); 
				((JavaCharacterLiteralContext)_localctx).t = match(CHAR_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Java_identifierContext extends ParserRuleContext {
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public TerminalNode SYMBOL_IDENTIFIER() { return getToken(OParser.SYMBOL_IDENTIFIER, 0); }
		public TerminalNode NATIVE_IDENTIFIER() { return getToken(OParser.NATIVE_IDENTIFIER, 0); }
		public TerminalNode DOLLAR_IDENTIFIER() { return getToken(OParser.DOLLAR_IDENTIFIER, 0); }
		public TerminalNode TYPE_IDENTIFIER() { return getToken(OParser.TYPE_IDENTIFIER, 0); }
		public TerminalNode BOOLEAN() { return getToken(OParser.BOOLEAN, 0); }
		public TerminalNode CHARACTER() { return getToken(OParser.CHARACTER, 0); }
		public TerminalNode TEXT() { return getToken(OParser.TEXT, 0); }
		public TerminalNode INTEGER() { return getToken(OParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(OParser.DECIMAL, 0); }
		public TerminalNode DATE() { return getToken(OParser.DATE, 0); }
		public TerminalNode TIME() { return getToken(OParser.TIME, 0); }
		public TerminalNode DATETIME() { return getToken(OParser.DATETIME, 0); }
		public TerminalNode PERIOD() { return getToken(OParser.PERIOD, 0); }
		public TerminalNode READ() { return getToken(OParser.READ, 0); }
		public TerminalNode WRITE() { return getToken(OParser.WRITE, 0); }
		public TerminalNode TEST() { return getToken(OParser.TEST, 0); }
		public Java_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_java_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterJava_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitJava_identifier(this);
		}
	}

	public final Java_identifierContext java_identifier() throws RecognitionException {
		Java_identifierContext _localctx = new Java_identifierContext(_ctx, getState());
		enterRule(_localctx, 366, RULE_java_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2085);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (TEST - 121)) | (1L << (WRITE - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)) | (1L << (NATIVE_IDENTIFIER - 121)) | (1L << (DOLLAR_IDENTIFIER - 121)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_statementContext extends ParserRuleContext {
		public Csharp_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_statement; }
	 
		public Csharp_statementContext() { }
		public void copyFrom(Csharp_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpReturnStatementContext extends Csharp_statementContext {
		public Csharp_expressionContext exp;
		public TerminalNode RETURN() { return getToken(OParser.RETURN, 0); }
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public CSharpReturnStatementContext(Csharp_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpReturnStatement(this);
		}
	}
	public static class CSharpStatementContext extends Csharp_statementContext {
		public Csharp_expressionContext exp;
		public TerminalNode SEMI() { return getToken(OParser.SEMI, 0); }
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public CSharpStatementContext(Csharp_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpStatement(this);
		}
	}

	public final Csharp_statementContext csharp_statement() throws RecognitionException {
		Csharp_statementContext _localctx = new Csharp_statementContext(_ctx, getState());
		enterRule(_localctx, 368, RULE_csharp_statement);
		try {
			setState(2094);
			switch (_input.LA(1)) {
			case RETURN:
				_localctx = new CSharpReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2087); 
				match(RETURN);
				setState(2088); 
				((CSharpReturnStatementContext)_localctx).exp = csharp_expression(0);
				setState(2089); 
				match(SEMI);
				}
				break;
			case LPAR:
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case READ:
			case SELF:
			case TEST:
			case THIS:
			case WRITE:
			case BOOLEAN_LITERAL:
			case CHAR_LITERAL:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
			case DOLLAR_IDENTIFIER:
			case TEXT_LITERAL:
			case INTEGER_LITERAL:
			case DECIMAL_LITERAL:
				_localctx = new CSharpStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2091); 
				((CSharpStatementContext)_localctx).exp = csharp_expression(0);
				setState(2092); 
				match(SEMI);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_expressionContext extends ParserRuleContext {
		public Csharp_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_expression; }
	 
		public Csharp_expressionContext() { }
		public void copyFrom(Csharp_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpSelectorExpressionContext extends Csharp_expressionContext {
		public Csharp_expressionContext parent;
		public Csharp_selector_expressionContext child;
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public Csharp_selector_expressionContext csharp_selector_expression() {
			return getRuleContext(Csharp_selector_expressionContext.class,0);
		}
		public CSharpSelectorExpressionContext(Csharp_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpSelectorExpression(this);
		}
	}
	public static class CSharpPrimaryExpressionContext extends Csharp_expressionContext {
		public Csharp_primary_expressionContext exp;
		public Csharp_primary_expressionContext csharp_primary_expression() {
			return getRuleContext(Csharp_primary_expressionContext.class,0);
		}
		public CSharpPrimaryExpressionContext(Csharp_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpPrimaryExpression(this);
		}
	}

	public final Csharp_expressionContext csharp_expression() throws RecognitionException {
		return csharp_expression(0);
	}

	private Csharp_expressionContext csharp_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Csharp_expressionContext _localctx = new Csharp_expressionContext(_ctx, _parentState);
		Csharp_expressionContext _prevctx = _localctx;
		int _startState = 370;
		enterRecursionRule(_localctx, 370, RULE_csharp_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new CSharpPrimaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2097); 
			((CSharpPrimaryExpressionContext)_localctx).exp = csharp_primary_expression();
			}
			_ctx.stop = _input.LT(-1);
			setState(2103);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,174,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CSharpSelectorExpressionContext(new Csharp_expressionContext(_parentctx, _parentState));
					((CSharpSelectorExpressionContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_csharp_expression);
					setState(2099);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2100); 
					((CSharpSelectorExpressionContext)_localctx).child = csharp_selector_expression();
					}
					} 
				}
				setState(2105);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,174,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Csharp_primary_expressionContext extends ParserRuleContext {
		public Csharp_this_expressionContext csharp_this_expression() {
			return getRuleContext(Csharp_this_expressionContext.class,0);
		}
		public Csharp_new_expressionContext csharp_new_expression() {
			return getRuleContext(Csharp_new_expressionContext.class,0);
		}
		public Csharp_parenthesis_expressionContext csharp_parenthesis_expression() {
			return getRuleContext(Csharp_parenthesis_expressionContext.class,0);
		}
		public Csharp_identifier_expressionContext csharp_identifier_expression() {
			return getRuleContext(Csharp_identifier_expressionContext.class,0);
		}
		public Csharp_literal_expressionContext csharp_literal_expression() {
			return getRuleContext(Csharp_literal_expressionContext.class,0);
		}
		public Csharp_primary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_primary_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_primary_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_primary_expression(this);
		}
	}

	public final Csharp_primary_expressionContext csharp_primary_expression() throws RecognitionException {
		Csharp_primary_expressionContext _localctx = new Csharp_primary_expressionContext(_ctx, getState());
		enterRule(_localctx, 372, RULE_csharp_primary_expression);
		try {
			setState(2111);
			switch ( getInterpreter().adaptivePredict(_input,175,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(2106); 
				csharp_this_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(2107); 
				csharp_new_expression();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(2108); 
				csharp_parenthesis_expression();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(2109); 
				csharp_identifier_expression(0);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(2110); 
				csharp_literal_expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_this_expressionContext extends ParserRuleContext {
		public This_expressionContext this_expression() {
			return getRuleContext(This_expressionContext.class,0);
		}
		public Csharp_this_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_this_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_this_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_this_expression(this);
		}
	}

	public final Csharp_this_expressionContext csharp_this_expression() throws RecognitionException {
		Csharp_this_expressionContext _localctx = new Csharp_this_expressionContext(_ctx, getState());
		enterRule(_localctx, 374, RULE_csharp_this_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2113); 
			this_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_new_expressionContext extends ParserRuleContext {
		public New_tokenContext new_token() {
			return getRuleContext(New_tokenContext.class,0);
		}
		public Csharp_method_expressionContext csharp_method_expression() {
			return getRuleContext(Csharp_method_expressionContext.class,0);
		}
		public Csharp_new_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_new_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_new_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_new_expression(this);
		}
	}

	public final Csharp_new_expressionContext csharp_new_expression() throws RecognitionException {
		Csharp_new_expressionContext _localctx = new Csharp_new_expressionContext(_ctx, getState());
		enterRule(_localctx, 376, RULE_csharp_new_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2115); 
			new_token();
			setState(2116); 
			csharp_method_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_selector_expressionContext extends ParserRuleContext {
		public Csharp_selector_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_selector_expression; }
	 
		public Csharp_selector_expressionContext() { }
		public void copyFrom(Csharp_selector_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpMethodExpressionContext extends Csharp_selector_expressionContext {
		public Csharp_method_expressionContext exp;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Csharp_method_expressionContext csharp_method_expression() {
			return getRuleContext(Csharp_method_expressionContext.class,0);
		}
		public CSharpMethodExpressionContext(Csharp_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpMethodExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpMethodExpression(this);
		}
	}
	public static class CSharpItemExpressionContext extends Csharp_selector_expressionContext {
		public Csharp_item_expressionContext exp;
		public Csharp_item_expressionContext csharp_item_expression() {
			return getRuleContext(Csharp_item_expressionContext.class,0);
		}
		public CSharpItemExpressionContext(Csharp_selector_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpItemExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpItemExpression(this);
		}
	}

	public final Csharp_selector_expressionContext csharp_selector_expression() throws RecognitionException {
		Csharp_selector_expressionContext _localctx = new Csharp_selector_expressionContext(_ctx, getState());
		enterRule(_localctx, 378, RULE_csharp_selector_expression);
		try {
			setState(2121);
			switch (_input.LA(1)) {
			case DOT:
				_localctx = new CSharpMethodExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2118); 
				match(DOT);
				setState(2119); 
				((CSharpMethodExpressionContext)_localctx).exp = csharp_method_expression();
				}
				break;
			case LBRAK:
				_localctx = new CSharpItemExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2120); 
				((CSharpItemExpressionContext)_localctx).exp = csharp_item_expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_method_expressionContext extends ParserRuleContext {
		public Csharp_identifierContext name;
		public Csharp_argumentsContext args;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Csharp_identifierContext csharp_identifier() {
			return getRuleContext(Csharp_identifierContext.class,0);
		}
		public Csharp_argumentsContext csharp_arguments() {
			return getRuleContext(Csharp_argumentsContext.class,0);
		}
		public Csharp_method_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_method_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_method_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_method_expression(this);
		}
	}

	public final Csharp_method_expressionContext csharp_method_expression() throws RecognitionException {
		Csharp_method_expressionContext _localctx = new Csharp_method_expressionContext(_ctx, getState());
		enterRule(_localctx, 380, RULE_csharp_method_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2123); 
			((Csharp_method_expressionContext)_localctx).name = csharp_identifier();
			setState(2124); 
			match(LPAR);
			setState(2126);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAR) | (1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (SELF - 121)) | (1L << (TEST - 121)) | (1L << (THIS - 121)) | (1L << (WRITE - 121)) | (1L << (BOOLEAN_LITERAL - 121)) | (1L << (CHAR_LITERAL - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)) | (1L << (DOLLAR_IDENTIFIER - 121)) | (1L << (TEXT_LITERAL - 121)) | (1L << (INTEGER_LITERAL - 121)) | (1L << (DECIMAL_LITERAL - 121)))) != 0)) {
				{
				setState(2125); 
				((Csharp_method_expressionContext)_localctx).args = csharp_arguments(0);
				}
			}

			setState(2128); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_argumentsContext extends ParserRuleContext {
		public Csharp_argumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_arguments; }
	 
		public Csharp_argumentsContext() { }
		public void copyFrom(Csharp_argumentsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpArgumentListContext extends Csharp_argumentsContext {
		public Csharp_expressionContext item;
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public CSharpArgumentListContext(Csharp_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpArgumentList(this);
		}
	}
	public static class CSharpArgumentListItemContext extends Csharp_argumentsContext {
		public Csharp_argumentsContext items;
		public Csharp_expressionContext item;
		public TerminalNode COMMA() { return getToken(OParser.COMMA, 0); }
		public Csharp_argumentsContext csharp_arguments() {
			return getRuleContext(Csharp_argumentsContext.class,0);
		}
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public CSharpArgumentListItemContext(Csharp_argumentsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpArgumentListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpArgumentListItem(this);
		}
	}

	public final Csharp_argumentsContext csharp_arguments() throws RecognitionException {
		return csharp_arguments(0);
	}

	private Csharp_argumentsContext csharp_arguments(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Csharp_argumentsContext _localctx = new Csharp_argumentsContext(_ctx, _parentState);
		Csharp_argumentsContext _prevctx = _localctx;
		int _startState = 382;
		enterRecursionRule(_localctx, 382, RULE_csharp_arguments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new CSharpArgumentListContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(2131); 
			((CSharpArgumentListContext)_localctx).item = csharp_expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(2138);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,178,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CSharpArgumentListItemContext(new Csharp_argumentsContext(_parentctx, _parentState));
					((CSharpArgumentListItemContext)_localctx).items = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_csharp_arguments);
					setState(2133);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2134); 
					match(COMMA);
					setState(2135); 
					((CSharpArgumentListItemContext)_localctx).item = csharp_expression(0);
					}
					} 
				}
				setState(2140);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,178,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Csharp_item_expressionContext extends ParserRuleContext {
		public Csharp_expressionContext exp;
		public TerminalNode LBRAK() { return getToken(OParser.LBRAK, 0); }
		public TerminalNode RBRAK() { return getToken(OParser.RBRAK, 0); }
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public Csharp_item_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_item_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_item_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_item_expression(this);
		}
	}

	public final Csharp_item_expressionContext csharp_item_expression() throws RecognitionException {
		Csharp_item_expressionContext _localctx = new Csharp_item_expressionContext(_ctx, getState());
		enterRule(_localctx, 384, RULE_csharp_item_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2141); 
			match(LBRAK);
			setState(2142); 
			((Csharp_item_expressionContext)_localctx).exp = csharp_expression(0);
			setState(2143); 
			match(RBRAK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_parenthesis_expressionContext extends ParserRuleContext {
		public Csharp_expressionContext exp;
		public TerminalNode LPAR() { return getToken(OParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(OParser.RPAR, 0); }
		public Csharp_expressionContext csharp_expression() {
			return getRuleContext(Csharp_expressionContext.class,0);
		}
		public Csharp_parenthesis_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_parenthesis_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_parenthesis_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_parenthesis_expression(this);
		}
	}

	public final Csharp_parenthesis_expressionContext csharp_parenthesis_expression() throws RecognitionException {
		Csharp_parenthesis_expressionContext _localctx = new Csharp_parenthesis_expressionContext(_ctx, getState());
		enterRule(_localctx, 386, RULE_csharp_parenthesis_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2145); 
			match(LPAR);
			setState(2146); 
			((Csharp_parenthesis_expressionContext)_localctx).exp = csharp_expression(0);
			setState(2147); 
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_identifier_expressionContext extends ParserRuleContext {
		public Csharp_identifier_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_identifier_expression; }
	 
		public Csharp_identifier_expressionContext() { }
		public void copyFrom(Csharp_identifier_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpIdentifierContext extends Csharp_identifier_expressionContext {
		public Csharp_identifierContext name;
		public Csharp_identifierContext csharp_identifier() {
			return getRuleContext(Csharp_identifierContext.class,0);
		}
		public CSharpIdentifierContext(Csharp_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpIdentifier(this);
		}
	}
	public static class CSharpChildIdentifierContext extends Csharp_identifier_expressionContext {
		public Csharp_identifier_expressionContext parent;
		public Csharp_identifierContext name;
		public TerminalNode DOT() { return getToken(OParser.DOT, 0); }
		public Csharp_identifier_expressionContext csharp_identifier_expression() {
			return getRuleContext(Csharp_identifier_expressionContext.class,0);
		}
		public Csharp_identifierContext csharp_identifier() {
			return getRuleContext(Csharp_identifierContext.class,0);
		}
		public CSharpChildIdentifierContext(Csharp_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpChildIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpChildIdentifier(this);
		}
	}
	public static class CSharpPromptoIdentifierContext extends Csharp_identifier_expressionContext {
		public TerminalNode DOLLAR_IDENTIFIER() { return getToken(OParser.DOLLAR_IDENTIFIER, 0); }
		public CSharpPromptoIdentifierContext(Csharp_identifier_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpPromptoIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpPromptoIdentifier(this);
		}
	}

	public final Csharp_identifier_expressionContext csharp_identifier_expression() throws RecognitionException {
		return csharp_identifier_expression(0);
	}

	private Csharp_identifier_expressionContext csharp_identifier_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Csharp_identifier_expressionContext _localctx = new Csharp_identifier_expressionContext(_ctx, _parentState);
		Csharp_identifier_expressionContext _prevctx = _localctx;
		int _startState = 388;
		enterRecursionRule(_localctx, 388, RULE_csharp_identifier_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(2152);
			switch (_input.LA(1)) {
			case DOLLAR_IDENTIFIER:
				{
				_localctx = new CSharpPromptoIdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(2150); 
				match(DOLLAR_IDENTIFIER);
				}
				break;
			case BOOLEAN:
			case CHARACTER:
			case TEXT:
			case INTEGER:
			case DECIMAL:
			case DATE:
			case TIME:
			case DATETIME:
			case PERIOD:
			case READ:
			case TEST:
			case WRITE:
			case SYMBOL_IDENTIFIER:
			case TYPE_IDENTIFIER:
			case VARIABLE_IDENTIFIER:
				{
				_localctx = new CSharpIdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(2151); 
				((CSharpIdentifierContext)_localctx).name = csharp_identifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(2159);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,180,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CSharpChildIdentifierContext(new Csharp_identifier_expressionContext(_parentctx, _parentState));
					((CSharpChildIdentifierContext)_localctx).parent = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_csharp_identifier_expression);
					setState(2154);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(2155); 
					match(DOT);
					setState(2156); 
					((CSharpChildIdentifierContext)_localctx).name = csharp_identifier();
					}
					} 
				}
				setState(2161);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,180,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Csharp_literal_expressionContext extends ParserRuleContext {
		public Csharp_literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_literal_expression; }
	 
		public Csharp_literal_expressionContext() { }
		public void copyFrom(Csharp_literal_expressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CSharpBooleanLiteralContext extends Csharp_literal_expressionContext {
		public TerminalNode BOOLEAN_LITERAL() { return getToken(OParser.BOOLEAN_LITERAL, 0); }
		public CSharpBooleanLiteralContext(Csharp_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpBooleanLiteral(this);
		}
	}
	public static class CSharpIntegerLiteralContext extends Csharp_literal_expressionContext {
		public TerminalNode INTEGER_LITERAL() { return getToken(OParser.INTEGER_LITERAL, 0); }
		public CSharpIntegerLiteralContext(Csharp_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpIntegerLiteral(this);
		}
	}
	public static class CSharpDecimalLiteralContext extends Csharp_literal_expressionContext {
		public TerminalNode DECIMAL_LITERAL() { return getToken(OParser.DECIMAL_LITERAL, 0); }
		public CSharpDecimalLiteralContext(Csharp_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpDecimalLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpDecimalLiteral(this);
		}
	}
	public static class CSharpCharacterLiteralContext extends Csharp_literal_expressionContext {
		public TerminalNode CHAR_LITERAL() { return getToken(OParser.CHAR_LITERAL, 0); }
		public CSharpCharacterLiteralContext(Csharp_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpCharacterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpCharacterLiteral(this);
		}
	}
	public static class CSharpTextLiteralContext extends Csharp_literal_expressionContext {
		public TerminalNode TEXT_LITERAL() { return getToken(OParser.TEXT_LITERAL, 0); }
		public CSharpTextLiteralContext(Csharp_literal_expressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCSharpTextLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCSharpTextLiteral(this);
		}
	}

	public final Csharp_literal_expressionContext csharp_literal_expression() throws RecognitionException {
		Csharp_literal_expressionContext _localctx = new Csharp_literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 390, RULE_csharp_literal_expression);
		try {
			setState(2167);
			switch (_input.LA(1)) {
			case INTEGER_LITERAL:
				_localctx = new CSharpIntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2162); 
				match(INTEGER_LITERAL);
				}
				break;
			case DECIMAL_LITERAL:
				_localctx = new CSharpDecimalLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(2163); 
				match(DECIMAL_LITERAL);
				}
				break;
			case TEXT_LITERAL:
				_localctx = new CSharpTextLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(2164); 
				match(TEXT_LITERAL);
				}
				break;
			case BOOLEAN_LITERAL:
				_localctx = new CSharpBooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(2165); 
				match(BOOLEAN_LITERAL);
				}
				break;
			case CHAR_LITERAL:
				_localctx = new CSharpCharacterLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(2166); 
				match(CHAR_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Csharp_identifierContext extends ParserRuleContext {
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(OParser.VARIABLE_IDENTIFIER, 0); }
		public TerminalNode SYMBOL_IDENTIFIER() { return getToken(OParser.SYMBOL_IDENTIFIER, 0); }
		public TerminalNode TYPE_IDENTIFIER() { return getToken(OParser.TYPE_IDENTIFIER, 0); }
		public TerminalNode BOOLEAN() { return getToken(OParser.BOOLEAN, 0); }
		public TerminalNode CHARACTER() { return getToken(OParser.CHARACTER, 0); }
		public TerminalNode TEXT() { return getToken(OParser.TEXT, 0); }
		public TerminalNode INTEGER() { return getToken(OParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(OParser.DECIMAL, 0); }
		public TerminalNode DATE() { return getToken(OParser.DATE, 0); }
		public TerminalNode TIME() { return getToken(OParser.TIME, 0); }
		public TerminalNode DATETIME() { return getToken(OParser.DATETIME, 0); }
		public TerminalNode PERIOD() { return getToken(OParser.PERIOD, 0); }
		public TerminalNode READ() { return getToken(OParser.READ, 0); }
		public TerminalNode WRITE() { return getToken(OParser.WRITE, 0); }
		public TerminalNode TEST() { return getToken(OParser.TEST, 0); }
		public Csharp_identifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_csharp_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).enterCsharp_identifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OParserListener ) ((OParserListener)listener).exitCsharp_identifier(this);
		}
	}

	public final Csharp_identifierContext csharp_identifier() throws RecognitionException {
		Csharp_identifierContext _localctx = new Csharp_identifierContext(_ctx, getState());
		enterRule(_localctx, 392, RULE_csharp_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(2169);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << CHARACTER) | (1L << TEXT) | (1L << INTEGER) | (1L << DECIMAL) | (1L << DATE) | (1L << TIME) | (1L << DATETIME) | (1L << PERIOD))) != 0) || ((((_la - 121)) & ~0x3f) == 0 && ((1L << (_la - 121)) & ((1L << (READ - 121)) | (1L << (TEST - 121)) | (1L << (WRITE - 121)) | (1L << (SYMBOL_IDENTIFIER - 121)) | (1L << (TYPE_IDENTIFIER - 121)) | (1L << (VARIABLE_IDENTIFIER - 121)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7: 
			return derived_list_sempred((Derived_listContext)_localctx, predIndex);
		case 15: 
			return native_category_binding_list_sempred((Native_category_binding_listContext)_localctx, predIndex);
		case 16: 
			return attribute_list_sempred((Attribute_listContext)_localctx, predIndex);
		case 34: 
			return else_if_statement_list_sempred((Else_if_statement_listContext)_localctx, predIndex);
		case 41: 
			return callable_parent_sempred((Callable_parentContext)_localctx, predIndex);
		case 43: 
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 44: 
			return an_expression_sempred((An_expressionContext)_localctx, predIndex);
		case 46: 
			return instance_expression_sempred((Instance_expressionContext)_localctx, predIndex);
		case 55: 
			return argument_assignment_list_sempred((Argument_assignment_listContext)_localctx, predIndex);
		case 62: 
			return declarations_sempred((DeclarationsContext)_localctx, predIndex);
		case 66: 
			return native_symbol_list_sempred((Native_symbol_listContext)_localctx, predIndex);
		case 67: 
			return category_symbol_list_sempred((Category_symbol_listContext)_localctx, predIndex);
		case 68: 
			return symbol_list_sempred((Symbol_listContext)_localctx, predIndex);
		case 72: 
			return expression_list_sempred((Expression_listContext)_localctx, predIndex);
		case 74: 
			return typedef_sempred((TypedefContext)_localctx, predIndex);
		case 80: 
			return type_identifier_list_sempred((Type_identifier_listContext)_localctx, predIndex);
		case 86: 
			return argument_list_sempred((Argument_listContext)_localctx, predIndex);
		case 92: 
			return any_type_sempred((Any_typeContext)_localctx, predIndex);
		case 93: 
			return member_method_declaration_list_sempred((Member_method_declaration_listContext)_localctx, predIndex);
		case 95: 
			return native_member_method_declaration_list_sempred((Native_member_method_declaration_listContext)_localctx, predIndex);
		case 100: 
			return module_token_sempred((Module_tokenContext)_localctx, predIndex);
		case 103: 
			return variable_identifier_list_sempred((Variable_identifier_listContext)_localctx, predIndex);
		case 106: 
			return native_statement_list_sempred((Native_statement_listContext)_localctx, predIndex);
		case 110: 
			return statement_list_sempred((Statement_listContext)_localctx, predIndex);
		case 111: 
			return assertion_list_sempred((Assertion_listContext)_localctx, predIndex);
		case 112: 
			return switch_case_statement_list_sempred((Switch_case_statement_listContext)_localctx, predIndex);
		case 113: 
			return catch_statement_list_sempred((Catch_statement_listContext)_localctx, predIndex);
		case 116: 
			return literal_list_literal_sempred((Literal_list_literalContext)_localctx, predIndex);
		case 124: 
			return expression_tuple_sempred((Expression_tupleContext)_localctx, predIndex);
		case 125: 
			return dict_entry_list_sempred((Dict_entry_listContext)_localctx, predIndex);
		case 129: 
			return assignable_instance_sempred((Assignable_instanceContext)_localctx, predIndex);
		case 130: 
			return is_expression_sempred((Is_expressionContext)_localctx, predIndex);
		case 134: 
			return new_token_sempred((New_tokenContext)_localctx, predIndex);
		case 135: 
			return key_token_sempred((Key_tokenContext)_localctx, predIndex);
		case 136: 
			return value_token_sempred((Value_tokenContext)_localctx, predIndex);
		case 137: 
			return symbols_token_sempred((Symbols_tokenContext)_localctx, predIndex);
		case 146: 
			return javascript_expression_sempred((Javascript_expressionContext)_localctx, predIndex);
		case 152: 
			return javascript_arguments_sempred((Javascript_argumentsContext)_localctx, predIndex);
		case 159: 
			return python_expression_sempred((Python_expressionContext)_localctx, predIndex);
		case 164: 
			return python_ordinal_argument_list_sempred((Python_ordinal_argument_listContext)_localctx, predIndex);
		case 165: 
			return python_named_argument_list_sempred((Python_named_argument_listContext)_localctx, predIndex);
		case 167: 
			return python_identifier_expression_sempred((Python_identifier_expressionContext)_localctx, predIndex);
		case 171: 
			return java_expression_sempred((Java_expressionContext)_localctx, predIndex);
		case 177: 
			return java_arguments_sempred((Java_argumentsContext)_localctx, predIndex);
		case 180: 
			return java_identifier_expression_sempred((Java_identifier_expressionContext)_localctx, predIndex);
		case 181: 
			return java_class_identifier_expression_sempred((Java_class_identifier_expressionContext)_localctx, predIndex);
		case 185: 
			return csharp_expression_sempred((Csharp_expressionContext)_localctx, predIndex);
		case 191: 
			return csharp_arguments_sempred((Csharp_argumentsContext)_localctx, predIndex);
		case 194: 
			return csharp_identifier_expression_sempred((Csharp_identifier_expressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean derived_list_sempred(Derived_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean native_category_binding_list_sempred(Native_category_binding_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean attribute_list_sempred(Attribute_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean else_if_statement_list_sempred(Else_if_statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean callable_parent_sempred(Callable_parentContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5: 
			return precpred(_ctx, 32);
		case 6: 
			return precpred(_ctx, 31);
		case 7: 
			return precpred(_ctx, 30);
		case 8: 
			return precpred(_ctx, 29);
		case 9: 
			return precpred(_ctx, 28);
		case 10: 
			return precpred(_ctx, 27);
		case 11: 
			return precpred(_ctx, 26);
		case 12: 
			return precpred(_ctx, 25);
		case 13: 
			return precpred(_ctx, 24);
		case 14: 
			return precpred(_ctx, 21);
		case 15: 
			return precpred(_ctx, 20);
		case 16: 
			return precpred(_ctx, 19);
		case 17: 
			return precpred(_ctx, 18);
		case 18: 
			return precpred(_ctx, 17);
		case 19: 
			return precpred(_ctx, 16);
		case 20: 
			return precpred(_ctx, 15);
		case 21: 
			return precpred(_ctx, 14);
		case 22: 
			return precpred(_ctx, 12);
		case 23: 
			return precpred(_ctx, 11);
		case 24: 
			return precpred(_ctx, 10);
		case 25: 
			return precpred(_ctx, 9);
		case 26: 
			return precpred(_ctx, 8);
		case 27: 
			return precpred(_ctx, 7);
		case 28: 
			return precpred(_ctx, 6);
		case 29: 
			return precpred(_ctx, 5);
		case 30: 
			return precpred(_ctx, 23);
		case 31: 
			return precpred(_ctx, 22);
		case 32: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean an_expression_sempred(An_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 33: 
			return this.willBeAOrAn();
		}
		return true;
	}
	private boolean instance_expression_sempred(Instance_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 34: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean argument_assignment_list_sempred(Argument_assignment_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 35: 
			return this.willNotBe(this.equalToken());
		case 36: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean declarations_sempred(DeclarationsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 37: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean native_symbol_list_sempred(Native_symbol_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 38: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean category_symbol_list_sempred(Category_symbol_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 39: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean symbol_list_sempred(Symbol_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 40: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_list_sempred(Expression_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 41: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean typedef_sempred(TypedefContext _localctx, int predIndex) {
		switch (predIndex) {
		case 42: 
			return precpred(_ctx, 3);
		case 43: 
			return precpred(_ctx, 2);
		case 44: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean type_identifier_list_sempred(Type_identifier_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 45: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean argument_list_sempred(Argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 46: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean any_type_sempred(Any_typeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 47: 
			return precpred(_ctx, 2);
		case 48: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean member_method_declaration_list_sempred(Member_method_declaration_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 49: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean native_member_method_declaration_list_sempred(Native_member_method_declaration_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 50: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean module_token_sempred(Module_tokenContext _localctx, int predIndex) {
		switch (predIndex) {
		case 51: 
			return this.isText(((Module_tokenContext)_localctx).i1,"module");
		}
		return true;
	}
	private boolean variable_identifier_list_sempred(Variable_identifier_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 52: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean native_statement_list_sempred(Native_statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 53: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean statement_list_sempred(Statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 54: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean assertion_list_sempred(Assertion_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 55: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean switch_case_statement_list_sempred(Switch_case_statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 56: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean catch_statement_list_sempred(Catch_statement_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 57: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean literal_list_literal_sempred(Literal_list_literalContext _localctx, int predIndex) {
		switch (predIndex) {
		case 58: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_tuple_sempred(Expression_tupleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 59: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean dict_entry_list_sempred(Dict_entry_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 60: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean assignable_instance_sempred(Assignable_instanceContext _localctx, int predIndex) {
		switch (predIndex) {
		case 61: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean is_expression_sempred(Is_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 62: 
			return this.willBeAOrAn();
		}
		return true;
	}
	private boolean new_token_sempred(New_tokenContext _localctx, int predIndex) {
		switch (predIndex) {
		case 63: 
			return this.isText(((New_tokenContext)_localctx).i1,"new");
		}
		return true;
	}
	private boolean key_token_sempred(Key_tokenContext _localctx, int predIndex) {
		switch (predIndex) {
		case 64: 
			return this.isText(((Key_tokenContext)_localctx).i1,"key");
		}
		return true;
	}
	private boolean value_token_sempred(Value_tokenContext _localctx, int predIndex) {
		switch (predIndex) {
		case 65: 
			return this.isText(((Value_tokenContext)_localctx).i1,"value");
		}
		return true;
	}
	private boolean symbols_token_sempred(Symbols_tokenContext _localctx, int predIndex) {
		switch (predIndex) {
		case 66: 
			return this.isText(((Symbols_tokenContext)_localctx).i1,"symbols");
		}
		return true;
	}
	private boolean javascript_expression_sempred(Javascript_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 67: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean javascript_arguments_sempred(Javascript_argumentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 68: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean python_expression_sempred(Python_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 69: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean python_ordinal_argument_list_sempred(Python_ordinal_argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 70: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean python_named_argument_list_sempred(Python_named_argument_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 71: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean python_identifier_expression_sempred(Python_identifier_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 72: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean java_expression_sempred(Java_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 73: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean java_arguments_sempred(Java_argumentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 74: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean java_identifier_expression_sempred(Java_identifier_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 75: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean java_class_identifier_expression_sempred(Java_class_identifier_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 76: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean csharp_expression_sempred(Csharp_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 77: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean csharp_arguments_sempred(Csharp_argumentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 78: 
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean csharp_identifier_expression_sempred(Csharp_identifier_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 79: 
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\u00a3\u087e\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\4r\tr\4s\ts\4t\tt\4u\tu\4v\tv\4"+
		"w\tw\4x\tx\4y\ty\4z\tz\4{\t{\4|\t|\4}\t}\4~\t~\4\177\t\177\4\u0080\t\u0080"+
		"\4\u0081\t\u0081\4\u0082\t\u0082\4\u0083\t\u0083\4\u0084\t\u0084\4\u0085"+
		"\t\u0085\4\u0086\t\u0086\4\u0087\t\u0087\4\u0088\t\u0088\4\u0089\t\u0089"+
		"\4\u008a\t\u008a\4\u008b\t\u008b\4\u008c\t\u008c\4\u008d\t\u008d\4\u008e"+
		"\t\u008e\4\u008f\t\u008f\4\u0090\t\u0090\4\u0091\t\u0091\4\u0092\t\u0092"+
		"\4\u0093\t\u0093\4\u0094\t\u0094\4\u0095\t\u0095\4\u0096\t\u0096\4\u0097"+
		"\t\u0097\4\u0098\t\u0098\4\u0099\t\u0099\4\u009a\t\u009a\4\u009b\t\u009b"+
		"\4\u009c\t\u009c\4\u009d\t\u009d\4\u009e\t\u009e\4\u009f\t\u009f\4\u00a0"+
		"\t\u00a0\4\u00a1\t\u00a1\4\u00a2\t\u00a2\4\u00a3\t\u00a3\4\u00a4\t\u00a4"+
		"\4\u00a5\t\u00a5\4\u00a6\t\u00a6\4\u00a7\t\u00a7\4\u00a8\t\u00a8\4\u00a9"+
		"\t\u00a9\4\u00aa\t\u00aa\4\u00ab\t\u00ab\4\u00ac\t\u00ac\4\u00ad\t\u00ad"+
		"\4\u00ae\t\u00ae\4\u00af\t\u00af\4\u00b0\t\u00b0\4\u00b1\t\u00b1\4\u00b2"+
		"\t\u00b2\4\u00b3\t\u00b3\4\u00b4\t\u00b4\4\u00b5\t\u00b5\4\u00b6\t\u00b6"+
		"\4\u00b7\t\u00b7\4\u00b8\t\u00b8\4\u00b9\t\u00b9\4\u00ba\t\u00ba\4\u00bb"+
		"\t\u00bb\4\u00bc\t\u00bc\4\u00bd\t\u00bd\4\u00be\t\u00be\4\u00bf\t\u00bf"+
		"\4\u00c0\t\u00c0\4\u00c1\t\u00c1\4\u00c2\t\u00c2\4\u00c3\t\u00c3\4\u00c4"+
		"\t\u00c4\4\u00c5\t\u00c5\4\u00c6\t\u00c6\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5"+
		"\2\u0194\n\2\3\2\3\2\5\2\u0198\n\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\5\6\u01b3"+
		"\n\6\3\6\3\6\3\6\3\6\3\6\5\6\u01ba\n\6\3\6\3\6\3\7\5\7\u01bf\n\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\5\7\u01c7\n\7\3\7\3\7\5\7\u01cb\n\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\5\b\u01d5\n\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u01df"+
		"\n\t\f\t\16\t\u01e2\13\t\3\n\3\n\3\n\5\n\u01e7\n\n\3\n\5\n\u01ea\n\n\3"+
		"\13\5\13\u01ed\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u01f6\n\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\5\f\u01fe\n\f\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u0206"+
		"\n\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0211\n\16\3\16\3"+
		"\16\3\16\5\16\u0216\n\16\3\16\3\16\3\17\5\17\u021b\n\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\5\17\u0224\n\17\3\17\3\17\3\17\5\17\u0229\n\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\7\21\u023b\n\21\f\21\16\21\u023e\13\21\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\7\22\u0246\n\22\f\22\16\22\u0249\13\22\3\23\3\23\5\23\u024d\n"+
		"\23\3\23\3\23\3\23\3\23\5\23\u0253\n\23\3\23\3\23\3\23\3\24\5\24\u0259"+
		"\n\24\3\24\3\24\3\24\3\24\5\24\u025f\n\24\3\24\3\24\3\24\5\24\u0264\n"+
		"\24\3\24\3\24\3\25\5\25\u0269\n\25\3\25\3\25\3\25\3\25\3\25\5\25\u0270"+
		"\n\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0287\n\26\3\27\3\27\3\27"+
		"\3\30\3\30\3\30\3\30\3\30\5\30\u0291\n\30\3\30\3\30\3\30\5\30\u0296\n"+
		"\30\3\31\3\31\3\31\3\31\3\31\5\31\u029d\n\31\5\31\u029f\n\31\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\5\32\u02b4\n\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\5\36\u02d1\n\36\5\36\u02d3\n\36\3\36\3\36\3"+
		"\37\3\37\3\37\3\37\5\37\u02db\n\37\3\37\3\37\3\37\3\37\3\37\5\37\u02e2"+
		"\n\37\5\37\u02e4\n\37\3 \3 \3 \3 \3 \3 \5 \u02ec\n \3 \3 \3 \3 \3 \3!"+
		"\3!\3!\5!\u02f6\n!\3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#"+
		"\3#\3#\3#\3#\5#\u030b\n#\3#\3#\5#\u030f\n#\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\7$\u0321\n$\f$\16$\u0324\13$\3%\3%\3%\3%\3&\3&\3"+
		"&\3&\3&\3&\5&\u0330\n&\3&\3&\5&\u0334\n&\3&\3&\3&\3&\3&\3&\5&\u033c\n"+
		"&\3&\5&\u033f\n&\3&\3&\3&\5&\u0344\n&\3&\5&\u0347\n&\3\'\3\'\3\'\3\'\3"+
		"\'\3\'\5\'\u034f\n\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\5\'\u035a\n\'"+
		"\3\'\3\'\5\'\u035e\n\'\3(\3(\5(\u0362\n(\3(\3(\3)\3)\3)\5)\u0369\n)\3"+
		")\3)\3*\3*\3*\3*\3*\5*\u0372\n*\3+\3+\3+\3+\3+\7+\u0379\n+\f+\16+\u037c"+
		"\13+\3,\3,\3,\3,\3,\3,\5,\u0384\n,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\5-\u039d\n-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		"-\3-\3-\3-\3-\3-\3-\3-\7-\u040a\n-\f-\16-\u040d\13-\3.\3.\3.\3.\3/\3/"+
		"\3\60\3\60\3\60\3\60\3\60\7\60\u041a\n\60\f\60\16\60\u041d\13\60\3\61"+
		"\3\61\3\61\3\61\3\61\3\61\5\61\u0425\n\61\3\62\3\62\3\62\3\62\3\63\3\63"+
		"\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\5\65\u0444\n\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\5\65\u0450\n\65\3\65\3\65\3\65"+
		"\5\65\u0455\n\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\5\65\u045f\n"+
		"\65\3\65\3\65\3\65\3\65\3\65\5\65\u0466\n\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\5\65\u046e\n\65\5\65\u0470\n\65\3\66\3\66\3\66\3\66\3\66\3\66\3"+
		"\66\3\66\5\66\u047a\n\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67"+
		"\3\67\3\67\3\67\5\67\u0488\n\67\38\58\u048b\n8\38\38\38\58\u0490\n8\3"+
		"8\38\39\39\39\39\39\59\u0499\n9\39\39\39\79\u049e\n9\f9\169\u04a1\139"+
		"\3:\3:\3:\3:\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3<\5<\u04b2\n<\3=\3=\3=\3="+
		"\3=\3>\3>\3?\5?\u04bc\n?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\7@\u04c8\n@\f@"+
		"\16@\u04cb\13@\3A\3A\3A\7A\u04d0\nA\fA\16A\u04d3\13A\3A\3A\3A\3A\3A\5"+
		"A\u04da\nA\3B\3B\3C\3C\5C\u04e0\nC\3D\3D\3D\3D\3D\3D\3D\7D\u04e9\nD\f"+
		"D\16D\u04ec\13D\3E\3E\3E\3E\3E\3E\3E\7E\u04f5\nE\fE\16E\u04f8\13E\3F\3"+
		"F\3F\3F\3F\3F\7F\u0500\nF\fF\16F\u0503\13F\3G\3G\3G\3G\3G\3G\3G\3G\3G"+
		"\3G\5G\u050f\nG\3H\3H\5H\u0513\nH\3H\3H\3I\3I\5I\u0519\nI\3I\3I\3J\3J"+
		"\3J\3J\3J\3J\7J\u0523\nJ\fJ\16J\u0526\13J\3K\3K\3K\3K\3K\3K\3L\3L\3L\3"+
		"L\3L\3L\3L\3L\3L\3L\3L\7L\u0539\nL\fL\16L\u053c\13L\3M\3M\5M\u0540\nM"+
		"\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\3N\5N\u0550\nN\3O\3O\3P\3P\3Q"+
		"\3Q\3Q\5Q\u0559\nQ\3R\3R\3R\3R\3R\3R\7R\u0561\nR\fR\16R\u0564\13R\3S\3"+
		"S\5S\u0568\nS\3T\3T\3T\5T\u056d\nT\3U\3U\3V\3V\3W\3W\3X\3X\3X\3X\3X\3"+
		"X\7X\u057b\nX\fX\16X\u057e\13X\3Y\3Y\5Y\u0582\nY\3Y\5Y\u0585\nY\3Z\3Z"+
		"\5Z\u0589\nZ\3[\3[\3[\5[\u058e\n[\3\\\3\\\3\\\3]\3]\5]\u0595\n]\3^\3^"+
		"\3^\3^\3^\3^\3^\3^\3^\7^\u05a0\n^\f^\16^\u05a3\13^\3_\3_\3_\3_\3_\3_\3"+
		"_\7_\u05ac\n_\f_\16_\u05af\13_\3`\3`\3`\3`\3`\5`\u05b6\n`\3a\3a\3a\3a"+
		"\3a\3a\3a\7a\u05bf\na\fa\16a\u05c2\13a\3b\3b\5b\u05c6\nb\3c\3c\3c\3c\3"+
		"c\3c\3c\3c\3c\3c\5c\u05d2\nc\3d\3d\5d\u05d6\nd\3e\3e\3e\3e\3e\3e\7e\u05de"+
		"\ne\fe\16e\u05e1\13e\3f\3f\3f\3g\3g\5g\u05e8\ng\3h\3h\3h\3h\5h\u05ee\n"+
		"h\3h\3h\3h\7h\u05f3\nh\fh\16h\u05f6\13h\3h\3h\5h\u05fa\nh\3i\3i\3i\3i"+
		"\3i\3i\7i\u0602\ni\fi\16i\u0605\13i\3j\3j\3j\3j\5j\u060b\nj\3k\3k\3l\3"+
		"l\3l\3l\3l\3l\3l\7l\u0616\nl\fl\16l\u0619\13l\3m\3m\3m\3m\3m\3m\3m\3m"+
		"\3m\3m\5m\u0625\nm\3n\3n\5n\u0629\nn\3n\5n\u062c\nn\3o\3o\5o\u0630\no"+
		"\3o\5o\u0633\no\3p\3p\3p\3p\3p\3p\3p\7p\u063c\np\fp\16p\u063f\13p\3q\3"+
		"q\3q\3q\3q\3q\3q\7q\u0648\nq\fq\16q\u064b\13q\3r\3r\3r\3r\3r\3r\3r\7r"+
		"\u0654\nr\fr\16r\u0657\13r\3s\3s\3s\3s\3s\3s\3s\7s\u0660\ns\fs\16s\u0663"+
		"\13s\3t\3t\3t\3t\3t\3t\3t\3t\3t\3t\3t\3t\3t\3t\5t\u0673\nt\3u\3u\3u\3"+
		"u\3u\3u\3u\3u\3u\3u\3u\3u\3u\5u\u0682\nu\3v\3v\3v\3v\3v\3v\7v\u068a\n"+
		"v\fv\16v\u068d\13v\3w\3w\3w\3w\5w\u0693\nw\3x\3x\3y\3y\3y\3y\3z\3z\5z"+
		"\u069d\nz\3{\3{\3{\3{\3{\5{\u06a4\n{\3|\3|\5|\u06a8\n|\3|\3|\3}\3}\5}"+
		"\u06ae\n}\3}\3}\3~\3~\3~\3~\3~\3~\7~\u06b8\n~\f~\16~\u06bb\13~\3\177\3"+
		"\177\3\177\3\177\3\177\3\177\7\177\u06c3\n\177\f\177\16\177\u06c6\13\177"+
		"\3\u0080\3\u0080\3\u0080\3\u0080\3\u0081\3\u0081\3\u0081\3\u0081\3\u0081"+
		"\3\u0081\3\u0081\3\u0081\3\u0081\5\u0081\u06d5\n\u0081\3\u0082\3\u0082"+
		"\3\u0082\3\u0082\3\u0083\3\u0083\3\u0083\3\u0083\3\u0083\7\u0083\u06e0"+
		"\n\u0083\f\u0083\16\u0083\u06e3\13\u0083\3\u0084\3\u0084\3\u0084\3\u0084"+
		"\5\u0084\u06e9\n\u0084\3\u0085\3\u0085\3\u0085\7\u0085\u06ee\n\u0085\f"+
		"\u0085\16\u0085\u06f1\13\u0085\3\u0086\3\u0086\3\u0086\7\u0086\u06f6\n"+
		"\u0086\f\u0086\16\u0086\u06f9\13\u0086\3\u0086\5\u0086\u06fc\n\u0086\3"+
		"\u0087\3\u0087\3\u0087\3\u0087\3\u0087\3\u0087\5\u0087\u0704\n\u0087\3"+
		"\u0088\3\u0088\3\u0088\3\u0089\3\u0089\3\u0089\3\u008a\3\u008a\3\u008a"+
		"\3\u008b\3\u008b\3\u008b\3\u008c\3\u008c\3\u008d\3\u008d\3\u008e\3\u008e"+
		"\3\u008f\3\u008f\3\u0090\3\u0090\3\u0091\3\u0091\3\u0092\3\u0092\3\u0093"+
		"\3\u0093\3\u0093\3\u0093\3\u0093\3\u0093\3\u0093\5\u0093\u0727\n\u0093"+
		"\3\u0094\3\u0094\3\u0094\3\u0094\3\u0094\7\u0094\u072e\n\u0094\f\u0094"+
		"\16\u0094\u0731\13\u0094\3\u0095\3\u0095\3\u0095\3\u0095\3\u0095\3\u0095"+
		"\3\u0095\5\u0095\u073a\n\u0095\3\u0096\3\u0096\3\u0097\3\u0097\3\u0097"+
		"\3\u0098\3\u0098\3\u0098\3\u0098\3\u0098\5\u0098\u0746\n\u0098\3\u0099"+
		"\3\u0099\3\u0099\5\u0099\u074b\n\u0099\3\u0099\3\u0099\3\u009a\3\u009a"+
		"\3\u009a\3\u009a\3\u009a\3\u009a\7\u009a\u0755\n\u009a\f\u009a\16\u009a"+
		"\u0758\13\u009a\3\u009b\3\u009b\3\u009b\3\u009b\3\u009c\3\u009c\3\u009c"+
		"\3\u009c\3\u009d\3\u009d\3\u009e\3\u009e\3\u009e\3\u009e\3\u009e\5\u009e"+
		"\u0769\n\u009e\3\u009f\3\u009f\3\u00a0\3\u00a0\3\u00a0\5\u00a0\u0770\n"+
		"\u00a0\3\u00a1\3\u00a1\3\u00a1\3\u00a1\3\u00a1\7\u00a1\u0777\n\u00a1\f"+
		"\u00a1\16\u00a1\u077a\13\u00a1\3\u00a2\3\u00a2\3\u00a2\3\u00a2\5\u00a2"+
		"\u0780\n\u00a2\3\u00a3\3\u00a3\3\u00a3\3\u00a3\3\u00a3\3\u00a3\5\u00a3"+
		"\u0788\n\u00a3\3\u00a4\3\u00a4\3\u00a4\5\u00a4\u078d\n\u00a4\3\u00a4\3"+
		"\u00a4\3\u00a5\3\u00a5\3\u00a5\3\u00a5\3\u00a5\3\u00a5\5\u00a5\u0797\n"+
		"\u00a5\3\u00a6\3\u00a6\3\u00a6\3\u00a6\3\u00a6\3\u00a6\7\u00a6\u079f\n"+
		"\u00a6\f\u00a6\16\u00a6\u07a2\13\u00a6\3\u00a7\3\u00a7\3\u00a7\3\u00a7"+
		"\3\u00a7\3\u00a7\3\u00a7\3\u00a7\3\u00a7\3\u00a7\3\u00a7\7\u00a7\u07af"+
		"\n\u00a7\f\u00a7\16\u00a7\u07b2\13\u00a7\3\u00a8\3\u00a8\3\u00a8\3\u00a8"+
		"\3\u00a9\3\u00a9\3\u00a9\5\u00a9\u07bb\n\u00a9\3\u00a9\3\u00a9\3\u00a9"+
		"\7\u00a9\u07c0\n\u00a9\f\u00a9\16\u00a9\u07c3\13\u00a9\3\u00aa\3\u00aa"+
		"\3\u00aa\3\u00aa\3\u00aa\5\u00aa\u07ca\n\u00aa\3\u00ab\3\u00ab\3\u00ac"+
		"\3\u00ac\3\u00ac\3\u00ac\3\u00ac\3\u00ac\3\u00ac\5\u00ac\u07d5\n\u00ac"+
		"\3\u00ad\3\u00ad\3\u00ad\3\u00ad\3\u00ad\7\u00ad\u07dc\n\u00ad\f\u00ad"+
		"\16\u00ad\u07df\13\u00ad\3\u00ae\3\u00ae\3\u00ae\3\u00ae\3\u00ae\5\u00ae"+
		"\u07e6\n\u00ae\3\u00af\3\u00af\3\u00b0\3\u00b0\3\u00b0\3\u00b1\3\u00b1"+
		"\3\u00b1\5\u00b1\u07f0\n\u00b1\3\u00b2\3\u00b2\3\u00b2\5\u00b2\u07f5\n"+
		"\u00b2\3\u00b2\3\u00b2\3\u00b3\3\u00b3\3\u00b3\3\u00b3\3\u00b3\3\u00b3"+
		"\7\u00b3\u07ff\n\u00b3\f\u00b3\16\u00b3\u0802\13\u00b3\3\u00b4\3\u00b4"+
		"\3\u00b4\3\u00b4\3\u00b5\3\u00b5\3\u00b5\3\u00b5\3\u00b6\3\u00b6\3\u00b6"+
		"\3\u00b6\3\u00b6\3\u00b6\7\u00b6\u0812\n\u00b6\f\u00b6\16\u00b6\u0815"+
		"\13\u00b6\3\u00b7\3\u00b7\3\u00b7\3\u00b7\3\u00b7\7\u00b7\u081c\n\u00b7"+
		"\f\u00b7\16\u00b7\u081f\13\u00b7\3\u00b8\3\u00b8\3\u00b8\3\u00b8\3\u00b8"+
		"\5\u00b8\u0826\n\u00b8\3\u00b9\3\u00b9\3\u00ba\3\u00ba\3\u00ba\3\u00ba"+
		"\3\u00ba\3\u00ba\3\u00ba\5\u00ba\u0831\n\u00ba\3\u00bb\3\u00bb\3\u00bb"+
		"\3\u00bb\3\u00bb\7\u00bb\u0838\n\u00bb\f\u00bb\16\u00bb\u083b\13\u00bb"+
		"\3\u00bc\3\u00bc\3\u00bc\3\u00bc\3\u00bc\5\u00bc\u0842\n\u00bc\3\u00bd"+
		"\3\u00bd\3\u00be\3\u00be\3\u00be\3\u00bf\3\u00bf\3\u00bf\5\u00bf\u084c"+
		"\n\u00bf\3\u00c0\3\u00c0\3\u00c0\5\u00c0\u0851\n\u00c0\3\u00c0\3\u00c0"+
		"\3\u00c1\3\u00c1\3\u00c1\3\u00c1\3\u00c1\3\u00c1\7\u00c1\u085b\n\u00c1"+
		"\f\u00c1\16\u00c1\u085e\13\u00c1\3\u00c2\3\u00c2\3\u00c2\3\u00c2\3\u00c3"+
		"\3\u00c3\3\u00c3\3\u00c3\3\u00c4\3\u00c4\3\u00c4\5\u00c4\u086b\n\u00c4"+
		"\3\u00c4\3\u00c4\3\u00c4\7\u00c4\u0870\n\u00c4\f\u00c4\16\u00c4\u0873"+
		"\13\u00c4\3\u00c5\3\u00c5\3\u00c5\3\u00c5\3\u00c5\5\u00c5\u087a\n\u00c5"+
		"\3\u00c6\3\u00c6\3\u00c6\2,\20 \"FTX^p~\u0086\u0088\u008a\u0092\u0096"+
		"\u00a2\u00ae\u00ba\u00bc\u00c0\u00d0\u00d6\u00de\u00e0\u00e2\u00e4\u00ea"+
		"\u00fa\u00fc\u0104\u0126\u0132\u0140\u014a\u014c\u0150\u0158\u0164\u016a"+
		"\u016c\u0174\u0180\u0186\u00c7\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082"+
		"\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a"+
		"\u009c\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2"+
		"\u00b4\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca"+
		"\u00cc\u00ce\u00d0\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0\u00e2"+
		"\u00e4\u00e6\u00e8\u00ea\u00ec\u00ee\u00f0\u00f2\u00f4\u00f6\u00f8\u00fa"+
		"\u00fc\u00fe\u0100\u0102\u0104\u0106\u0108\u010a\u010c\u010e\u0110\u0112"+
		"\u0114\u0116\u0118\u011a\u011c\u011e\u0120\u0122\u0124\u0126\u0128\u012a"+
		"\u012c\u012e\u0130\u0132\u0134\u0136\u0138\u013a\u013c\u013e\u0140\u0142"+
		"\u0144\u0146\u0148\u014a\u014c\u014e\u0150\u0152\u0154\u0156\u0158\u015a"+
		"\u015c\u015e\u0160\u0162\u0164\u0166\u0168\u016a\u016c\u016e\u0170\u0172"+
		"\u0174\u0176\u0178\u017a\u017c\u017e\u0180\u0182\u0184\u0186\u0188\u018a"+
		"\2\n\3\2\36\37\4\2\u0081\u0081\u0089\u0089\4\2EETT\4\2##kk\b\2\608{{\u0088"+
		"\u0088\u0092\u0092\u0097\u0099\u009b\u009b\b\2\608{{\u0081\u0081\u0088"+
		"\u0089\u0092\u0092\u0097\u0099\7\2\608{{\u0088\u0088\u0092\u0092\u0097"+
		"\u009b\7\2\608{{\u0088\u0088\u0092\u0092\u0097\u0099\u08f4\2\u018c\3\2"+
		"\2\2\4\u019d\3\2\2\2\6\u01a6\3\2\2\2\b\u01ac\3\2\2\2\n\u01b2\3\2\2\2\f"+
		"\u01be\3\2\2\2\16\u01ce\3\2\2\2\20\u01d8\3\2\2\2\22\u01e9\3\2\2\2\24\u01ec"+
		"\3\2\2\2\26\u01f9\3\2\2\2\30\u0201\3\2\2\2\32\u0209\3\2\2\2\34\u021a\3"+
		"\2\2\2\36\u022c\3\2\2\2 \u0232\3\2\2\2\"\u023f\3\2\2\2$\u024a\3\2\2\2"+
		"&\u0258\3\2\2\2(\u0268\3\2\2\2*\u0276\3\2\2\2,\u0288\3\2\2\2.\u028b\3"+
		"\2\2\2\60\u029e\3\2\2\2\62\u02b3\3\2\2\2\64\u02b5\3\2\2\2\66\u02bb\3\2"+
		"\2\28\u02c1\3\2\2\2:\u02c7\3\2\2\2<\u02e3\3\2\2\2>\u02e5\3\2\2\2@\u02f2"+
		"\3\2\2\2B\u02fe\3\2\2\2D\u0304\3\2\2\2F\u0310\3\2\2\2H\u0325\3\2\2\2J"+
		"\u0329\3\2\2\2L\u035d\3\2\2\2N\u035f\3\2\2\2P\u0365\3\2\2\2R\u0371\3\2"+
		"\2\2T\u0373\3\2\2\2V\u0383\3\2\2\2X\u039c\3\2\2\2Z\u040e\3\2\2\2\\\u0412"+
		"\3\2\2\2^\u0414\3\2\2\2`\u0424\3\2\2\2b\u0426\3\2\2\2d\u042a\3\2\2\2f"+
		"\u042e\3\2\2\2h\u046f\3\2\2\2j\u0471\3\2\2\2l\u0487\3\2\2\2n\u048a\3\2"+
		"\2\2p\u0498\3\2\2\2r\u04a2\3\2\2\2t\u04a6\3\2\2\2v\u04b1\3\2\2\2x\u04b3"+
		"\3\2\2\2z\u04b8\3\2\2\2|\u04bb\3\2\2\2~\u04c0\3\2\2\2\u0080\u04d1\3\2"+
		"\2\2\u0082\u04db\3\2\2\2\u0084\u04df\3\2\2\2\u0086\u04e1\3\2\2\2\u0088"+
		"\u04ed\3\2\2\2\u008a\u04f9\3\2\2\2\u008c\u050e\3\2\2\2\u008e\u0510\3\2"+
		"\2\2\u0090\u0516\3\2\2\2\u0092\u051c\3\2\2\2\u0094\u0527\3\2\2\2\u0096"+
		"\u052d\3\2\2\2\u0098\u053f\3\2\2\2\u009a\u054f\3\2\2\2\u009c\u0551\3\2"+
		"\2\2\u009e\u0553\3\2\2\2\u00a0\u0558\3\2\2\2\u00a2\u055a\3\2\2\2\u00a4"+
		"\u0567\3\2\2\2\u00a6\u056c\3\2\2\2\u00a8\u056e\3\2\2\2\u00aa\u0570\3\2"+
		"\2\2\u00ac\u0572\3\2\2\2\u00ae\u0574\3\2\2\2\u00b0\u0584\3\2\2\2\u00b2"+
		"\u0588\3\2\2\2\u00b4\u058a\3\2\2\2\u00b6\u058f\3\2\2\2\u00b8\u0594\3\2"+
		"\2\2\u00ba\u0596\3\2\2\2\u00bc\u05a4\3\2\2\2\u00be\u05b5\3\2\2\2\u00c0"+
		"\u05b7\3\2\2\2\u00c2\u05c5\3\2\2\2\u00c4\u05d1\3\2\2\2\u00c6\u05d3\3\2"+
		"\2\2\u00c8\u05d7\3\2\2\2\u00ca\u05e2\3\2\2\2\u00cc\u05e5\3\2\2\2\u00ce"+
		"\u05e9\3\2\2\2\u00d0\u05fb\3\2\2\2\u00d2\u060a\3\2\2\2\u00d4\u060c\3\2"+
		"\2\2\u00d6\u060e\3\2\2\2\u00d8\u0624\3\2\2\2\u00da\u0626\3\2\2\2\u00dc"+
		"\u062d\3\2\2\2\u00de\u0634\3\2\2\2\u00e0\u0640\3\2\2\2\u00e2\u064c\3\2"+
		"\2\2\u00e4\u0658\3\2\2\2\u00e6\u0672\3\2\2\2\u00e8\u0681\3\2\2\2\u00ea"+
		"\u0683\3\2\2\2\u00ec\u0692\3\2\2\2\u00ee\u0694\3\2\2\2\u00f0\u0696\3\2"+
		"\2\2\u00f2\u069c\3\2\2\2\u00f4\u06a3\3\2\2\2\u00f6\u06a5\3\2\2\2\u00f8"+
		"\u06ab\3\2\2\2\u00fa\u06b1\3\2\2\2\u00fc\u06bc\3\2\2\2\u00fe\u06c7\3\2"+
		"\2\2\u0100\u06d4\3\2\2\2\u0102\u06d6\3\2\2\2\u0104\u06da\3\2\2\2\u0106"+
		"\u06e8\3\2\2\2\u0108\u06ea\3\2\2\2\u010a\u06f2\3\2\2\2\u010c\u0703\3\2"+
		"\2\2\u010e\u0705\3\2\2\2\u0110\u0708\3\2\2\2\u0112\u070b\3\2\2\2\u0114"+
		"\u070e\3\2\2\2\u0116\u0711\3\2\2\2\u0118\u0713\3\2\2\2\u011a\u0715\3\2"+
		"\2\2\u011c\u0717\3\2\2\2\u011e\u0719\3\2\2\2\u0120\u071b\3\2\2\2\u0122"+
		"\u071d\3\2\2\2\u0124\u0726\3\2\2\2\u0126\u0728\3\2\2\2\u0128\u0739\3\2"+
		"\2\2\u012a\u073b\3\2\2\2\u012c\u073d\3\2\2\2\u012e\u0745\3\2\2\2\u0130"+
		"\u0747\3\2\2\2\u0132\u074e\3\2\2\2\u0134\u0759\3\2\2\2\u0136\u075d\3\2"+
		"\2\2\u0138\u0761\3\2\2\2\u013a\u0768\3\2\2\2\u013c\u076a\3\2\2\2\u013e"+
		"\u076f\3\2\2\2\u0140\u0771\3\2\2\2\u0142\u077f\3\2\2\2\u0144\u0787\3\2"+
		"\2\2\u0146\u0789\3\2\2\2\u0148\u0796\3\2\2\2\u014a\u0798\3\2\2\2\u014c"+
		"\u07a3\3\2\2\2\u014e\u07b3\3\2\2\2\u0150\u07ba\3\2\2\2\u0152\u07c9\3\2"+
		"\2\2\u0154\u07cb\3\2\2\2\u0156\u07d4\3\2\2\2\u0158\u07d6\3\2\2\2\u015a"+
		"\u07e5\3\2\2\2\u015c\u07e7\3\2\2\2\u015e\u07e9\3\2\2\2\u0160\u07ef\3\2"+
		"\2\2\u0162\u07f1\3\2\2\2\u0164\u07f8\3\2\2\2\u0166\u0803\3\2\2\2\u0168"+
		"\u0807\3\2\2\2\u016a\u080b\3\2\2\2\u016c\u0816\3\2\2\2\u016e\u0825\3\2"+
		"\2\2\u0170\u0827\3\2\2\2\u0172\u0830\3\2\2\2\u0174\u0832\3\2\2\2\u0176"+
		"\u0841\3\2\2\2\u0178\u0843\3\2\2\2\u017a\u0845\3\2\2\2\u017c\u084b\3\2"+
		"\2\2\u017e\u084d\3\2\2\2\u0180\u0854\3\2\2\2\u0182\u085f\3\2\2\2\u0184"+
		"\u0863\3\2\2\2\u0186\u086a\3\2\2\2\u0188\u0879\3\2\2\2\u018a\u087b\3\2"+
		"\2\2\u018c\u018d\7Z\2\2\u018d\u018e\7M\2\2\u018e\u0193\5\u00aaV\2\u018f"+
		"\u0190\7\22\2\2\u0190\u0191\5\"\22\2\u0191\u0192\7\23\2\2\u0192\u0194"+
		"\3\2\2\2\u0193\u018f\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0197\3\2\2\2\u0195"+
		"\u0196\7^\2\2\u0196\u0198\5\u00aaV\2\u0197\u0195\3\2\2\2\u0197\u0198\3"+
		"\2\2\2\u0198\u0199\3\2\2\2\u0199\u019a\7\26\2\2\u019a\u019b\5\u0088E\2"+
		"\u019b\u019c\7\27\2\2\u019c\3\3\2\2\2\u019d\u019e\7Z\2\2\u019e\u019f\5"+
		"\u00aaV\2\u019f\u01a0\7\22\2\2\u01a0\u01a1\5\u009aN\2\u01a1\u01a2\7\23"+
		"\2\2\u01a2\u01a3\7\26\2\2\u01a3\u01a4\5\u0086D\2\u01a4\u01a5\7\27\2\2"+
		"\u01a5\5\3\2\2\2\u01a6\u01a7\5\u00acW\2\u01a7\u01a8\7\22\2\2\u01a8\u01a9"+
		"\5p9\2\u01a9\u01aa\7\23\2\2\u01aa\u01ab\7\16\2\2\u01ab\7\3\2\2\2\u01ac"+
		"\u01ad\5\u00acW\2\u01ad\u01ae\7)\2\2\u01ae\u01af\5X-\2\u01af\u01b0\7\16"+
		"\2\2\u01b0\t\3\2\2\2\u01b1\u01b3\7\u0085\2\2\u01b2\u01b1\3\2\2\2\u01b2"+
		"\u01b3\3\2\2\2\u01b3\u01b4\3\2\2\2\u01b4\u01b5\7G\2\2\u01b5\u01b6\5\u00a8"+
		"U\2\u01b6\u01b7\7\r\2\2\u01b7\u01b9\5\u0096L\2\u01b8\u01ba\5\u008cG\2"+
		"\u01b9\u01b8\3\2\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc"+
		"\7\16\2\2\u01bc\13\3\2\2\2\u01bd\u01bf\7\u0085\2\2\u01be\u01bd\3\2\2\2"+
		"\u01be\u01bf\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0\u01c1\7M\2\2\u01c1\u01c6"+
		"\5\u00aaV\2\u01c2\u01c3\7\22\2\2\u01c3\u01c4\5\"\22\2\u01c4\u01c5\7\23"+
		"\2\2\u01c5\u01c7\3\2\2\2\u01c6\u01c2\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7"+
		"\u01ca\3\2\2\2\u01c8\u01c9\7^\2\2\u01c9\u01cb\5\20\t\2\u01ca\u01c8\3\2"+
		"\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u01cd\5\22\n\2\u01cd"+
		"\r\3\2\2\2\u01ce\u01cf\7\u0083\2\2\u01cf\u01d4\5\u00aaV\2\u01d0\u01d1"+
		"\7\22\2\2\u01d1\u01d2\5\"\22\2\u01d2\u01d3\7\23\2\2\u01d3\u01d5\3\2\2"+
		"\2\u01d4\u01d0\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6\u01d7"+
		"\5\22\n\2\u01d7\17\3\2\2\2\u01d8\u01d9\b\t\1\2\u01d9\u01da\5\u00aaV\2"+
		"\u01da\u01e0\3\2\2\2\u01db\u01dc\f\3\2\2\u01dc\u01dd\7\17\2\2\u01dd\u01df"+
		"\5\u00aaV\2\u01de\u01db\3\2\2\2\u01df\u01e2\3\2\2\2\u01e0\u01de\3\2\2"+
		"\2\u01e0\u01e1\3\2\2\2\u01e1\21\3\2\2\2\u01e2\u01e0\3\2\2\2\u01e3\u01ea"+
		"\7\16\2\2\u01e4\u01e6\7\26\2\2\u01e5\u01e7\5\u00bc_\2\u01e6\u01e5\3\2"+
		"\2\2\u01e6\u01e7\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01ea\7\27\2\2\u01e9"+
		"\u01e3\3\2\2\2\u01e9\u01e4\3\2\2\2\u01ea\23\3\2\2\2\u01eb\u01ed\5\u0096"+
		"L\2\u01ec\u01eb\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee"+
		"\u01ef\7u\2\2\u01ef\u01f0\5\u010c\u0087\2\u01f0\u01f1\7\22\2\2\u01f1\u01f2"+
		"\5\u00b2Z\2\u01f2\u01f3\7\23\2\2\u01f3\u01f5\7\26\2\2\u01f4\u01f6\5\u00de"+
		"p\2\u01f5\u01f4\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7"+
		"\u01f8\7\27\2\2\u01f8\25\3\2\2\2\u01f9\u01fa\7\u0082\2\2\u01fa\u01fb\5"+
		"\u00a8U\2\u01fb\u01fd\7\26\2\2\u01fc\u01fe\5\u00dep\2\u01fd\u01fc\3\2"+
		"\2\2\u01fd\u01fe\3\2\2\2\u01fe\u01ff\3\2\2\2\u01ff\u0200\7\27\2\2\u0200"+
		"\27\3\2\2\2\u0201\u0202\7c\2\2\u0202\u0203\5\u00a8U\2\u0203\u0205\7\26"+
		"\2\2\u0204\u0206\5\u00dep\2\u0205\u0204\3\2\2\2\u0205\u0206\3\2\2\2\u0206"+
		"\u0207\3\2\2\2\u0207\u0208\7\27\2\2\u0208\31\3\2\2\2\u0209\u020a\7m\2"+
		"\2\u020a\u020b\7}\2\2\u020b\u0210\5\u00aaV\2\u020c\u020d\7\22\2\2\u020d"+
		"\u020e\5\"\22\2\u020e\u020f\7\23\2\2\u020f\u0211\3\2\2\2\u0210\u020c\3"+
		"\2\2\2\u0210\u0211\3\2\2\2\u0211\u0212\3\2\2\2\u0212\u0213\7\26\2\2\u0213"+
		"\u0215\5\36\20\2\u0214\u0216\5\u00c0a\2\u0215\u0214\3\2\2\2\u0215\u0216"+
		"\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0218\7\27\2\2\u0218\33\3\2\2\2\u0219"+
		"\u021b\7\u0085\2\2\u021a\u0219\3\2\2\2\u021a\u021b\3\2\2\2\u021b\u021c"+
		"\3\2\2\2\u021c\u021d\7m\2\2\u021d\u021e\7M\2\2\u021e\u0223\5\u00aaV\2"+
		"\u021f\u0220\7\22\2\2\u0220\u0221\5\"\22\2\u0221\u0222\7\23\2\2\u0222"+
		"\u0224\3\2\2\2\u0223\u021f\3\2\2\2\u0223\u0224\3\2\2\2\u0224\u0225\3\2"+
		"\2\2\u0225\u0226\7\26\2\2\u0226\u0228\5\36\20\2\u0227\u0229\5\u00c0a\2"+
		"\u0228\u0227\3\2\2\2\u0228\u0229\3\2\2\2\u0229\u022a\3\2\2\2\u022a\u022b"+
		"\7\27\2\2\u022b\35\3\2\2\2\u022c\u022d\7M\2\2\u022d\u022e\7I\2\2\u022e"+
		"\u022f\7\26\2\2\u022f\u0230\5 \21\2\u0230\u0231\7\27\2\2\u0231\37\3\2"+
		"\2\2\u0232\u0233\b\21\1\2\u0233\u0234\5\u00c4c\2\u0234\u0235\7\16\2\2"+
		"\u0235\u023c\3\2\2\2\u0236\u0237\f\3\2\2\u0237\u0238\5\u00c4c\2\u0238"+
		"\u0239\7\16\2\2\u0239\u023b\3\2\2\2\u023a\u0236\3\2\2\2\u023b\u023e\3"+
		"\2\2\2\u023c\u023a\3\2\2\2\u023c\u023d\3\2\2\2\u023d!\3\2\2\2\u023e\u023c"+
		"\3\2\2\2\u023f\u0240\b\22\1\2\u0240\u0241\5\u00a8U\2\u0241\u0247\3\2\2"+
		"\2\u0242\u0243\f\3\2\2\u0243\u0244\7\17\2\2\u0244\u0246\5\u00a8U\2\u0245"+
		"\u0242\3\2\2\2\u0246\u0249\3\2\2\2\u0247\u0245\3\2\2\2\u0247\u0248\3\2"+
		"\2\2\u0248#\3\2\2\2\u0249\u0247\3\2\2\2\u024a\u024c\7?\2\2\u024b\u024d"+
		"\5\u0096L\2\u024c\u024b\3\2\2\2\u024c\u024d\3\2\2\2\u024d\u024e\3\2\2"+
		"\2\u024e\u024f\7i\2\2\u024f\u0250\5\u00a4S\2\u0250\u0252\7\22\2\2\u0251"+
		"\u0253\5\u00aeX\2\u0252\u0251\3\2\2\2\u0252\u0253\3\2\2\2\u0253\u0254"+
		"\3\2\2\2\u0254\u0255\7\23\2\2\u0255\u0256\7\16\2\2\u0256%\3\2\2\2\u0257"+
		"\u0259\5\u0096L\2\u0258\u0257\3\2\2\2\u0258\u0259\3\2\2\2\u0259\u025a"+
		"\3\2\2\2\u025a\u025b\7i\2\2\u025b\u025c\5\u00a4S\2\u025c\u025e\7\22\2"+
		"\2\u025d\u025f\5\u00aeX\2\u025e\u025d\3\2\2\2\u025e\u025f\3\2\2\2\u025f"+
		"\u0260\3\2\2\2\u0260\u0261\7\23\2\2\u0261\u0263\7\26\2\2\u0262\u0264\5"+
		"\u00dep\2\u0263\u0262\3\2\2\2\u0263\u0264\3\2\2\2\u0264\u0265\3\2\2\2"+
		"\u0265\u0266\7\27\2\2\u0266\'\3\2\2\2\u0267\u0269\5\u00b8]\2\u0268\u0267"+
		"\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026a\3\2\2\2\u026a\u026b\7m\2\2\u026b"+
		"\u026c\7i\2\2\u026c\u026d\5\u00a4S\2\u026d\u026f\7\22\2\2\u026e\u0270"+
		"\5\u00aeX\2\u026f\u026e\3\2\2\2\u026f\u0270\3\2\2\2\u0270\u0271\3\2\2"+
		"\2\u0271\u0272\7\23\2\2\u0272\u0273\7\26\2\2\u0273\u0274\5\u00d6l\2\u0274"+
		"\u0275\7\27\2\2\u0275)\3\2\2\2\u0276\u0277\7\u0088\2\2\u0277\u0278\7i"+
		"\2\2\u0278\u0279\7\u009c\2\2\u0279\u027a\7\22\2\2\u027a\u027b\7\23\2\2"+
		"\u027b\u027c\7\26\2\2\u027c\u027d\5\u00dep\2\u027d\u027e\7\27\2\2\u027e"+
		"\u0286\7\u008d\2\2\u027f\u0280\7\26\2\2\u0280\u0281\5\u00e0q\2\u0281\u0282"+
		"\7\27\2\2\u0282\u0287\3\2\2\2\u0283\u0284\5\u00acW\2\u0284\u0285\7\16"+
		"\2\2\u0285\u0287\3\2\2\2\u0286\u027f\3\2\2\2\u0286\u0283\3\2\2\2\u0287"+
		"+\3\2\2\2\u0288\u0289\5X-\2\u0289\u028a\7\16\2\2\u028a-\3\2\2\2\u028b"+
		"\u0290\5\u00b8]\2\u028c\u028d\7\22\2\2\u028d\u028e\5\"\22\2\u028e\u028f"+
		"\7\23\2\2\u028f\u0291\3\2\2\2\u0290\u028c\3\2\2\2\u0290\u0291\3\2\2\2"+
		"\u0291\u0292\3\2\2\2\u0292\u0295\5\u00a8U\2\u0293\u0294\7)\2\2\u0294\u0296"+
		"\5\u00f2z\2\u0295\u0293\3\2\2\2\u0295\u0296\3\2\2\2\u0296/\3\2\2\2\u0297"+
		"\u029f\5\62\32\2\u0298\u029c\7\26\2\2\u0299\u029a\5\u00dep\2\u029a\u029b"+
		"\7\27\2\2\u029b\u029d\3\2\2\2\u029c\u0299\3\2\2\2\u029c\u029d\3\2\2\2"+
		"\u029d\u029f\3\2\2\2\u029e\u0297\3\2\2\2\u029e\u0298\3\2\2\2\u029f\61"+
		"\3\2\2\2\u02a0\u02a1\5P)\2\u02a1\u02a2\7\16\2\2\u02a2\u02b4\3\2\2\2\u02a3"+
		"\u02b4\5t;\2\u02a4\u02b4\5x=\2\u02a5\u02b4\5\64\33\2\u02a6\u02b4\5N(\2"+
		"\u02a7\u02b4\5D#\2\u02a8\u02b4\5:\36\2\u02a9\u02b4\5> \2\u02aa\u02b4\5"+
		"B\"\2\u02ab\u02b4\5@!\2\u02ac\u02b4\5J&\2\u02ad\u02b4\5H%\2\u02ae\u02b4"+
		"\5f\64\2\u02af\u02b4\5\66\34\2\u02b0\u02b4\58\35\2\u02b1\u02b4\5&\24\2"+
		"\u02b2\u02b4\5\u00d4k\2\u02b3\u02a0\3\2\2\2\u02b3\u02a3\3\2\2\2\u02b3"+
		"\u02a4\3\2\2\2\u02b3\u02a5\3\2\2\2\u02b3\u02a6\3\2\2\2\u02b3\u02a7\3\2"+
		"\2\2\u02b3\u02a8\3\2\2\2\u02b3\u02a9\3\2\2\2\u02b3\u02aa\3\2\2\2\u02b3"+
		"\u02ab\3\2\2\2\u02b3\u02ac\3\2\2\2\u02b3\u02ad\3\2\2\2\u02b3\u02ae\3\2"+
		"\2\2\u02b3\u02af\3\2\2\2\u02b3\u02b0\3\2\2\2\u02b3\u02b1\3\2\2\2\u02b3"+
		"\u02b2\3\2\2\2\u02b4\63\3\2\2\2\u02b5\u02b6\7\u0086\2\2\u02b6\u02b7\7"+
		"\22\2\2\u02b7\u02b8\5\u0092J\2\u02b8\u02b9\7\23\2\2\u02b9\u02ba\7\16\2"+
		"\2\u02ba\65\3\2\2\2\u02bb\u02bc\7\u008e\2\2\u02bc\u02bd\7\22\2\2\u02bd"+
		"\u02be\5\u0102\u0082\2\u02be\u02bf\7\23\2\2\u02bf\u02c0\5\60\31\2\u02c0"+
		"\67\3\2\2\2\u02c1\u02c2\7\u008e\2\2\u02c2\u02c3\7\22\2\2\u02c3\u02c4\5"+
		"\u00aaV\2\u02c4\u02c5\7\23\2\2\u02c5\u02c6\5\60\31\2\u02c69\3\2\2\2\u02c7"+
		"\u02c8\7\u0087\2\2\u02c8\u02c9\7\22\2\2\u02c9\u02ca\5X-\2\u02ca\u02cb"+
		"\7\23\2\2\u02cb\u02cc\7\26\2\2\u02cc\u02d2\5\u00e2r\2\u02cd\u02ce\7R\2"+
		"\2\u02ce\u02d0\7\r\2\2\u02cf\u02d1\5\u00dep\2\u02d0\u02cf\3\2\2\2\u02d0"+
		"\u02d1\3\2\2\2\u02d1\u02d3\3\2\2\2\u02d2\u02cd\3\2\2\2\u02d2\u02d3\3\2"+
		"\2\2\u02d3\u02d4\3\2\2\2\u02d4\u02d5\7\27\2\2\u02d5;\3\2\2\2\u02d6\u02d7"+
		"\7K\2\2\u02d7\u02d8\5\u00e8u\2\u02d8\u02da\7\r\2\2\u02d9\u02db\5\u00de"+
		"p\2\u02da\u02d9\3\2\2\2\u02da\u02db\3\2\2\2\u02db\u02e4\3\2\2\2\u02dc"+
		"\u02dd\7K\2\2\u02dd\u02de\7e\2\2\u02de\u02df\5\u00e6t\2\u02df\u02e1\7"+
		"\r\2\2\u02e0\u02e2\5\u00dep\2\u02e1\u02e0\3\2\2\2\u02e1\u02e2\3\2\2\2"+
		"\u02e2\u02e4\3\2\2\2\u02e3\u02d6\3\2\2\2\u02e3\u02dc\3\2\2\2\u02e4=\3"+
		"\2\2\2\u02e5\u02e6\7a\2\2\u02e6\u02e7\7W\2\2\u02e7\u02e8\7\22\2\2\u02e8"+
		"\u02eb\5\u00a8U\2\u02e9\u02ea\7\17\2\2\u02ea\u02ec\5\u00a8U\2\u02eb\u02e9"+
		"\3\2\2\2\u02eb\u02ec\3\2\2\2\u02ec\u02ed\3\2\2\2\u02ed\u02ee\7e\2\2\u02ee"+
		"\u02ef\5X-\2\u02ef\u02f0\7\23\2\2\u02f0\u02f1\5\60\31\2\u02f1?\3\2\2\2"+
		"\u02f2\u02f3\7U\2\2\u02f3\u02f5\7\26\2\2\u02f4\u02f6\5\u00dep\2\u02f5"+
		"\u02f4\3\2\2\2\u02f5\u02f6\3\2\2\2\u02f6\u02f7\3\2\2\2\u02f7\u02f8\7\27"+
		"\2\2\u02f8\u02f9\7\u0091\2\2\u02f9\u02fa\7\22\2\2\u02fa\u02fb\5X-\2\u02fb"+
		"\u02fc\7\23\2\2\u02fc\u02fd\7\16\2\2\u02fdA\3\2\2\2\u02fe\u02ff\7\u0091"+
		"\2\2\u02ff\u0300\7\22\2\2\u0300\u0301\5X-\2\u0301\u0302\7\23\2\2\u0302"+
		"\u0303\5\60\31\2\u0303C\3\2\2\2\u0304\u0305\7d\2\2\u0305\u0306\7\22\2"+
		"\2\u0306\u0307\5X-\2\u0307\u0308\7\23\2\2\u0308\u030a\5\60\31\2\u0309"+
		"\u030b\5F$\2\u030a\u0309\3\2\2\2\u030a\u030b\3\2\2\2\u030b\u030e\3\2\2"+
		"\2\u030c\u030d\7X\2\2\u030d\u030f\5\60\31\2\u030e\u030c\3\2\2\2\u030e"+
		"\u030f\3\2\2\2\u030fE\3\2\2\2\u0310\u0311\b$\1\2\u0311\u0312\7X\2\2\u0312"+
		"\u0313\7d\2\2\u0313\u0314\7\22\2\2\u0314\u0315\5X-\2\u0315\u0316\7\23"+
		"\2\2\u0316\u0317\5\60\31\2\u0317\u0322\3\2\2\2\u0318\u0319\f\3\2\2\u0319"+
		"\u031a\7X\2\2\u031a\u031b\7d\2\2\u031b\u031c\7\22\2\2\u031c\u031d\5X-"+
		"\2\u031d\u031e\7\23\2\2\u031e\u031f\5\60\31\2\u031f\u0321\3\2\2\2\u0320"+
		"\u0318\3\2\2\2\u0321\u0324\3\2\2\2\u0322\u0320\3\2\2\2\u0322\u0323\3\2"+
		"\2\2\u0323G\3\2\2\2\u0324\u0322\3\2\2\2\u0325\u0326\7\u008a\2\2\u0326"+
		"\u0327\5X-\2\u0327\u0328\7\16\2\2\u0328I\3\2\2\2\u0329\u032a\7\u008c\2"+
		"\2\u032a\u032b\7\22\2\2\u032b\u032c\5\u00a8U\2\u032c\u032d\7\23\2\2\u032d"+
		"\u032f\7\26\2\2\u032e\u0330\5\u00dep\2\u032f\u032e\3\2\2\2\u032f\u0330"+
		"\3\2\2\2\u0330\u0331\3\2\2\2\u0331\u0333\7\27\2\2\u0332\u0334\5\u00e4"+
		"s\2\u0333\u0332\3\2\2\2\u0333\u0334\3\2\2\2\u0334\u033e\3\2\2\2\u0335"+
		"\u0336\7L\2\2\u0336\u0337\7\22\2\2\u0337\u0338\7C\2\2\u0338\u0339\7\23"+
		"\2\2\u0339\u033b\7\26\2\2\u033a\u033c\5\u00dep\2\u033b\u033a\3\2\2\2\u033b"+
		"\u033c\3\2\2\2\u033c\u033d\3\2\2\2\u033d\u033f\7\27\2\2\u033e\u0335\3"+
		"\2\2\2\u033e\u033f\3\2\2\2\u033f\u0346\3\2\2\2\u0340\u0341\7`\2\2\u0341"+
		"\u0343\7\26\2\2\u0342\u0344\5\u00dep\2\u0343\u0342\3\2\2\2\u0343\u0344"+
		"\3\2\2\2\u0344\u0345\3\2\2\2\u0345\u0347\7\27\2\2\u0346\u0340\3\2\2\2"+
		"\u0346\u0347\3\2\2\2\u0347K\3\2\2\2\u0348\u0349\7L\2\2\u0349\u034a\7\22"+
		"\2\2\u034a\u034b\5\u00acW\2\u034b\u034c\7\23\2\2\u034c\u034e\7\26\2\2"+
		"\u034d\u034f\5\u00dep\2\u034e\u034d\3\2\2\2\u034e\u034f\3\2\2\2\u034f"+
		"\u0350\3\2\2\2\u0350\u0351\7\27\2\2\u0351\u035e\3\2\2\2\u0352\u0353\7"+
		"L\2\2\u0353\u0354\7e\2\2\u0354\u0355\7\22\2\2\u0355\u0356\5\u008aF\2\u0356"+
		"\u0357\7\23\2\2\u0357\u0359\7\26\2\2\u0358\u035a\5\u00dep\2\u0359\u0358"+
		"\3\2\2\2\u0359\u035a\3\2\2\2\u035a\u035b\3\2\2\2\u035b\u035c\7\27\2\2"+
		"\u035c\u035e\3\2\2\2\u035d\u0348\3\2\2\2\u035d\u0352\3\2\2\2\u035eM\3"+
		"\2\2\2\u035f\u0361\7~\2\2\u0360\u0362\5X-\2\u0361\u0360\3\2\2\2\u0361"+
		"\u0362\3\2\2\2\u0362\u0363\3\2\2\2\u0363\u0364\7\16\2\2\u0364O\3\2\2\2"+
		"\u0365\u0366\5R*\2\u0366\u0368\7\22\2\2\u0367\u0369\5p9\2\u0368\u0367"+
		"\3\2\2\2\u0368\u0369\3\2\2\2\u0369\u036a\3\2\2\2\u036a\u036b\7\23\2\2"+
		"\u036bQ\3\2\2\2\u036c\u0372\5\u00a4S\2\u036d\u036e\5T+\2\u036e\u036f\7"+
		"\21\2\2\u036f\u0370\5\u00a4S\2\u0370\u0372\3\2\2\2\u0371\u036c\3\2\2\2"+
		"\u0371\u036d\3\2\2\2\u0372S\3\2\2\2\u0373\u0374\b+\1\2\u0374\u0375\5\u00a6"+
		"T\2\u0375\u037a\3\2\2\2\u0376\u0377\f\3\2\2\u0377\u0379\5V,\2\u0378\u0376"+
		"\3\2\2\2\u0379\u037c\3\2\2\2\u037a\u0378\3\2\2\2\u037a\u037b\3\2\2\2\u037b"+
		"U\3\2\2\2\u037c\u037a\3\2\2\2\u037d\u037e\7\21\2\2\u037e\u0384\5\u00a8"+
		"U\2\u037f\u0380\7\24\2\2\u0380\u0381\5X-\2\u0381\u0382\7\25\2\2\u0382"+
		"\u0384\3\2\2\2\u0383\u037d\3\2\2\2\u0383\u037f\3\2\2\2\u0384W\3\2\2\2"+
		"\u0385\u0386\b-\1\2\u0386\u0387\7\37\2\2\u0387\u039d\5X-$\u0388\u0389"+
		"\7\31\2\2\u0389\u039d\5X-#\u038a\u038b\7\22\2\2\u038b\u038c\5\u00b8]\2"+
		"\u038c\u038d\7\23\2\2\u038d\u038e\5X-\17\u038e\u039d\3\2\2\2\u038f\u039d"+
		"\5^\60\2\u0390\u039d\5`\61\2\u0391\u0392\7:\2\2\u0392\u0393\7\22\2\2\u0393"+
		"\u0394\5X-\2\u0394\u0395\7\23\2\2\u0395\u039d\3\2\2\2\u0396\u0397\7\\"+
		"\2\2\u0397\u0398\7\22\2\2\u0398\u0399\5\u00a8U\2\u0399\u039a\7\23\2\2"+
		"\u039a\u039d\3\2\2\2\u039b\u039d\5\\/\2\u039c\u0385\3\2\2\2\u039c\u0388"+
		"\3\2\2\2\u039c\u038a\3\2\2\2\u039c\u038f\3\2\2\2\u039c\u0390\3\2\2\2\u039c"+
		"\u0391\3\2\2\2\u039c\u0396\3\2\2\2\u039c\u039b\3\2\2\2\u039d\u040b\3\2"+
		"\2\2\u039e\u039f\f\"\2\2\u039f\u03a0\5\u0118\u008d\2\u03a0\u03a1\5X-#"+
		"\u03a1\u040a\3\2\2\2\u03a2\u03a3\f!\2\2\u03a3\u03a4\5\u011a\u008e\2\u03a4"+
		"\u03a5\5X-\"\u03a5\u040a\3\2\2\2\u03a6\u03a7\f \2\2\u03a7\u03a8\5\u011e"+
		"\u0090\2\u03a8\u03a9\5X-!\u03a9\u040a\3\2\2\2\u03aa\u03ab\f\37\2\2\u03ab"+
		"\u03ac\5\u011c\u008f\2\u03ac\u03ad\5X- \u03ad\u040a\3\2\2\2\u03ae\u03af"+
		"\f\36\2\2\u03af\u03b0\t\2\2\2\u03b0\u040a\5X-\37\u03b1\u03b2\f\35\2\2"+
		"\u03b2\u03b3\7&\2\2\u03b3\u040a\5X-\36\u03b4\u03b5\f\34\2\2\u03b5\u03b6"+
		"\7\'\2\2\u03b6\u040a\5X-\35\u03b7\u03b8\f\33\2\2\u03b8\u03b9\7$\2\2\u03b9"+
		"\u040a\5X-\34\u03ba\u03bb\f\32\2\2\u03bb\u03bc\7%\2\2\u03bc\u040a\5X-"+
		"\33\u03bd\u03be\f\27\2\2\u03be\u03bf\7g\2\2\u03bf\u03c0\7o\2\2\u03c0\u040a"+
		"\5X-\30\u03c1\u03c2\f\26\2\2\u03c2\u03c3\7g\2\2\u03c3\u040a\5X-\27\u03c4"+
		"\u03c5\f\25\2\2\u03c5\u03c6\7+\2\2\u03c6\u040a\5X-\26\u03c7\u03c8\f\24"+
		"\2\2\u03c8\u03c9\7*\2\2\u03c9\u040a\5X-\25\u03ca\u03cb\f\23\2\2\u03cb"+
		"\u03cc\7,\2\2\u03cc\u040a\5X-\24\u03cd\u03ce\f\22\2\2\u03ce\u03cf\7\35"+
		"\2\2\u03cf\u040a\5X-\23\u03d0\u03d1\f\21\2\2\u03d1\u03d2\7\33\2\2\u03d2"+
		"\u040a\5X-\22\u03d3\u03d4\f\20\2\2\u03d4\u03d5\7\30\2\2\u03d5\u03d6\5"+
		"X-\2\u03d6\u03d7\7\r\2\2\u03d7\u03d8\5X-\21\u03d8\u040a\3\2\2\2\u03d9"+
		"\u03da\f\16\2\2\u03da\u03db\7e\2\2\u03db\u040a\5X-\17\u03dc\u03dd\f\r"+
		"\2\2\u03dd\u03de\7P\2\2\u03de\u040a\5X-\16\u03df\u03e0\f\f\2\2\u03e0\u03e1"+
		"\7P\2\2\u03e1\u03e2\7@\2\2\u03e2\u040a\5X-\r\u03e3\u03e4\f\13\2\2\u03e4"+
		"\u03e5\7P\2\2\u03e5\u03e6\7C\2\2\u03e6\u040a\5X-\f\u03e7\u03e8\f\n\2\2"+
		"\u03e8\u03e9\7o\2\2\u03e9\u03ea\7e\2\2\u03ea\u040a\5X-\13\u03eb\u03ec"+
		"\f\t\2\2\u03ec\u03ed\7o\2\2\u03ed\u03ee\7P\2\2\u03ee\u040a\5X-\n\u03ef"+
		"\u03f0\f\b\2\2\u03f0\u03f1\7o\2\2\u03f1\u03f2\7P\2\2\u03f2\u03f3\7@\2"+
		"\2\u03f3\u040a\5X-\t\u03f4\u03f5\f\7\2\2\u03f5\u03f6\7o\2\2\u03f6\u03f7"+
		"\7P\2\2\u03f7\u03f8\7C\2\2\u03f8\u040a\5X-\b\u03f9\u03fa\f\31\2\2\u03fa"+
		"\u03fb\7g\2\2\u03fb\u03fc\7o\2\2\u03fc\u040a\5Z.\2\u03fd\u03fe\f\30\2"+
		"\2\u03fe\u03ff\7g\2\2\u03ff\u040a\5Z.\2\u0400\u0401\f\3\2\2\u0401\u0402"+
		"\7a\2\2\u0402\u0403\7W\2\2\u0403\u0404\7\22\2\2\u0404\u0405\5\u00a8U\2"+
		"\u0405\u0406\7e\2\2\u0406\u0407\5X-\2\u0407\u0408\7\23\2\2\u0408\u040a"+
		"\3\2\2\2\u0409\u039e\3\2\2\2\u0409\u03a2\3\2\2\2\u0409\u03a6\3\2\2\2\u0409"+
		"\u03aa\3\2\2\2\u0409\u03ae\3\2\2\2\u0409\u03b1\3\2\2\2\u0409\u03b4\3\2"+
		"\2\2\u0409\u03b7\3\2\2\2\u0409\u03ba\3\2\2\2\u0409\u03bd\3\2\2\2\u0409"+
		"\u03c1\3\2\2\2\u0409\u03c4\3\2\2\2\u0409\u03c7\3\2\2\2\u0409\u03ca\3\2"+
		"\2\2\u0409\u03cd\3\2\2\2\u0409\u03d0\3\2\2\2\u0409\u03d3\3\2\2\2\u0409"+
		"\u03d9\3\2\2\2\u0409\u03dc\3\2\2\2\u0409\u03df\3\2\2\2\u0409\u03e3\3\2"+
		"\2\2\u0409\u03e7\3\2\2\2\u0409\u03eb\3\2\2\2\u0409\u03ef\3\2\2\2\u0409"+
		"\u03f4\3\2\2\2\u0409\u03f9\3\2\2\2\u0409\u03fd\3\2\2\2\u0409\u0400\3\2"+
		"\2\2\u040a\u040d\3\2\2\2\u040b\u0409\3\2\2\2\u040b\u040c\3\2\2\2\u040c"+
		"Y\3\2\2\2\u040d\u040b\3\2\2\2\u040e\u040f\6.#\3\u040f\u0410\7\u0099\2"+
		"\2\u0410\u0411\5\u00b8]\2\u0411[\3\2\2\2\u0412\u0413\5\u00aaV\2\u0413"+
		"]\3\2\2\2\u0414\u0415\b\60\1\2\u0415\u0416\5\u00ecw\2\u0416\u041b\3\2"+
		"\2\2\u0417\u0418\f\3\2\2\u0418\u041a\5l\67\2\u0419\u0417\3\2\2\2\u041a"+
		"\u041d\3\2\2\2\u041b\u0419\3\2\2\2\u041b\u041c\3\2\2\2\u041c_\3\2\2\2"+
		"\u041d\u041b\3\2\2\2\u041e\u0425\5b\62\2\u041f\u0425\5h\65\2\u0420\u0425"+
		"\5d\63\2\u0421\u0425\5j\66\2\u0422\u0425\5P)\2\u0423\u0425\5n8\2\u0424"+
		"\u041e\3\2\2\2\u0424\u041f\3\2\2\2\u0424\u0420\3\2\2\2\u0424\u0421\3\2"+
		"\2\2\u0424\u0422\3\2\2\2\u0424\u0423\3\2\2\2\u0425a\3\2\2\2\u0426\u0427"+
		"\7;\2\2\u0427\u0428\7\22\2\2\u0428\u0429\7\23\2\2\u0429c\3\2\2\2\u042a"+
		"\u042b\7{\2\2\u042b\u042c\7b\2\2\u042c\u042d\5X-\2\u042de\3\2\2\2\u042e"+
		"\u042f\7\u0092\2\2\u042f\u0430\7\22\2\2\u0430\u0431\5X-\2\u0431\u0432"+
		"\7\23\2\2\u0432\u0433\7\u008b\2\2\u0433\u0434\5X-\2\u0434\u0435\7\16\2"+
		"\2\u0435g\3\2\2\2\u0436\u0437\7_\2\2\u0437\u0438\7\22\2\2\u0438\u0439"+
		"\5\u00a8U\2\u0439\u043a\7\23\2\2\u043a\u043b\7b\2\2\u043b\u043c\5X-\2"+
		"\u043c\u043d\7\u0090\2\2\u043d\u043e\5X-\2\u043e\u0470\3\2\2\2\u043f\u0440"+
		"\7_\2\2\u0440\u0441\7s\2\2\u0441\u0443\7\22\2\2\u0442\u0444\5\u009cO\2"+
		"\u0443\u0442\3\2\2\2\u0443\u0444\3\2\2\2\u0444\u0445\3\2\2\2\u0445\u0446"+
		"\7\23\2\2\u0446\u0447\7\u0090\2\2\u0447\u0448\7\22\2\2\u0448\u0449\5X"+
		"-\2\u0449\u044a\7\23\2\2\u044a\u0470\3\2\2\2\u044b\u045e\7_\2\2\u044c"+
		"\u044d\7@\2\2\u044d\u044f\7\22\2\2\u044e\u0450\5\u009cO\2\u044f\u044e"+
		"\3\2\2\2\u044f\u0450\3\2\2\2\u0450\u0451\3\2\2\2\u0451\u045f\7\23\2\2"+
		"\u0452\u0454\7\22\2\2\u0453\u0455\5\u009cO\2\u0454\u0453\3\2\2\2\u0454"+
		"\u0455\3\2\2\2\u0455\u0456\3\2\2\2\u0456\u0457\7\23\2\2\u0457\u0458\7"+
		"\u0080\2\2\u0458\u0459\7\22\2\2\u0459\u045a\5X-\2\u045a\u045b\7\u008b"+
		"\2\2\u045b\u045c\5X-\2\u045c\u045d\7\23\2\2\u045d\u045f\3\2\2\2\u045e"+
		"\u044c\3\2\2\2\u045e\u0452\3\2\2\2\u045f\u0465\3\2\2\2\u0460\u0461\7\u0090"+
		"\2\2\u0461\u0462\7\22\2\2\u0462\u0463\5X-\2\u0463\u0464\7\23\2\2\u0464"+
		"\u0466\3\2\2\2\u0465\u0460\3\2\2\2\u0465\u0466\3\2\2\2\u0466\u046d\3\2"+
		"\2\2\u0467\u0468\7w\2\2\u0468\u0469\7J\2\2\u0469\u046a\7\22\2\2\u046a"+
		"\u046b\5\u0108\u0085\2\u046b\u046c\7\23\2\2\u046c\u046e\3\2\2\2\u046d"+
		"\u0467\3\2\2\2\u046d\u046e\3\2\2\2\u046e\u0470\3\2\2\2\u046f\u0436\3\2"+
		"\2\2\u046f\u043f\3\2\2\2\u046f\u044b\3\2\2\2\u0470i\3\2\2\2\u0471\u0472"+
		"\7\u0084\2\2\u0472\u0473\7\22\2\2\u0473\u0479\5^\60\2\u0474\u0475\7\17"+
		"\2\2\u0475\u0476\5\u0110\u0089\2\u0476\u0477\7)\2\2\u0477\u0478\5^\60"+
		"\2\u0478\u047a\3\2\2\2\u0479\u0474\3\2\2\2\u0479\u047a\3\2\2\2\u047a\u047b"+
		"\3\2\2\2\u047b\u047c\7\23\2\2\u047ck\3\2\2\2\u047d\u047e\7\21\2\2\u047e"+
		"\u0488\5\u00a8U\2\u047f\u0480\7\24\2\2\u0480\u0481\5X-\2\u0481\u0482\7"+
		"\25\2\2\u0482\u0488\3\2\2\2\u0483\u0484\7\24\2\2\u0484\u0485\5\u0100\u0081"+
		"\2\u0485\u0486\7\25\2\2\u0486\u0488\3\2\2\2\u0487\u047d\3\2\2\2\u0487"+
		"\u047f\3\2\2\2\u0487\u0483\3\2\2\2\u0488m\3\2\2\2\u0489\u048b\7l\2\2\u048a"+
		"\u0489\3\2\2\2\u048a\u048b\3\2\2\2\u048b\u048c\3\2\2\2\u048c\u048d\5\u009c"+
		"O\2\u048d\u048f\7\22\2\2\u048e\u0490\5p9\2\u048f\u048e\3\2\2\2\u048f\u0490"+
		"\3\2\2\2\u0490\u0491\3\2\2\2\u0491\u0492\7\23\2\2\u0492o\3\2\2\2\u0493"+
		"\u0494\b9\1\2\u0494\u0495\5X-\2\u0495\u0496\69%\3\u0496\u0499\3\2\2\2"+
		"\u0497\u0499\5r:\2\u0498\u0493\3\2\2\2\u0498\u0497\3\2\2\2\u0499\u049f"+
		"\3\2\2\2\u049a\u049b\f\3\2\2\u049b\u049c\7\17\2\2\u049c\u049e\5r:\2\u049d"+
		"\u049a\3\2\2\2\u049e\u04a1\3\2\2\2\u049f\u049d\3\2\2\2\u049f\u04a0\3\2"+
		"\2\2\u04a0q\3\2\2\2\u04a1\u049f\3\2\2\2\u04a2\u04a3\5\u00a8U\2\u04a3\u04a4"+
		"\5\u0116\u008c\2\u04a4\u04a5\5X-\2\u04a5s\3\2\2\2\u04a6\u04a7\5\u0104"+
		"\u0083\2\u04a7\u04a8\5\u0116\u008c\2\u04a8\u04a9\5X-\2\u04a9\u04aa\7\16"+
		"\2\2\u04aau\3\2\2\2\u04ab\u04ac\7\21\2\2\u04ac\u04b2\5\u00a8U\2\u04ad"+
		"\u04ae\7\24\2\2\u04ae\u04af\5X-\2\u04af\u04b0\7\25\2\2\u04b0\u04b2\3\2"+
		"\2\2\u04b1\u04ab\3\2\2\2\u04b1\u04ad\3\2\2\2\u04b2w\3\2\2\2\u04b3\u04b4"+
		"\5\u00d0i\2\u04b4\u04b5\5\u0116\u008c\2\u04b5\u04b6\5X-\2\u04b6\u04b7"+
		"\7\16\2\2\u04b7y\3\2\2\2\u04b8\u04b9\7q\2\2\u04b9{\3\2\2\2\u04ba\u04bc"+
		"\5~@\2\u04bb\u04ba\3\2\2\2\u04bb\u04bc\3\2\2\2\u04bc\u04bd\3\2\2\2\u04bd"+
		"\u04be\5\u0120\u0091\2\u04be\u04bf\7\2\2\3\u04bf}\3\2\2\2\u04c0\u04c1"+
		"\b@\1\2\u04c1\u04c2\5\u0080A\2\u04c2\u04c9\3\2\2\2\u04c3\u04c4\f\3\2\2"+
		"\u04c4\u04c5\5\u0122\u0092\2\u04c5\u04c6\5\u0080A\2\u04c6\u04c8\3\2\2"+
		"\2\u04c7\u04c3\3\2\2\2\u04c8\u04cb\3\2\2\2\u04c9\u04c7\3\2\2\2\u04c9\u04ca"+
		"\3\2\2\2\u04ca\177\3\2\2\2\u04cb\u04c9\3\2\2\2\u04cc\u04cd\5\u00d4k\2"+
		"\u04cd\u04ce\5\u0122\u0092\2\u04ce\u04d0\3\2\2\2\u04cf\u04cc\3\2\2\2\u04d0"+
		"\u04d3\3\2\2\2\u04d1\u04cf\3\2\2\2\u04d1\u04d2\3\2\2\2\u04d2\u04d9\3\2"+
		"\2\2\u04d3\u04d1\3\2\2\2\u04d4\u04da\5\n\6\2\u04d5\u04da\5\u00a0Q\2\u04d6"+
		"\u04da\5\u0082B\2\u04d7\u04da\5\u0084C\2\u04d8\u04da\5\u00d2j\2\u04d9"+
		"\u04d4\3\2\2\2\u04d9\u04d5\3\2\2\2\u04d9\u04d6\3\2\2\2\u04d9\u04d7\3\2"+
		"\2\2\u04d9\u04d8\3\2\2\2\u04da\u0081\3\2\2\2\u04db\u04dc\5\32\16\2\u04dc"+
		"\u0083\3\2\2\2\u04dd\u04e0\5\2\2\2\u04de\u04e0\5\4\3\2\u04df\u04dd\3\2"+
		"\2\2\u04df\u04de\3\2\2\2\u04e0\u0085\3\2\2\2\u04e1\u04e2\bD\1\2\u04e2"+
		"\u04e3\5\b\5\2\u04e3\u04ea\3\2\2\2\u04e4\u04e5\f\3\2\2\u04e5\u04e6\5\u0122"+
		"\u0092\2\u04e6\u04e7\5\b\5\2\u04e7\u04e9\3\2\2\2\u04e8\u04e4\3\2\2\2\u04e9"+
		"\u04ec\3\2\2\2\u04ea\u04e8\3\2\2\2\u04ea\u04eb\3\2\2\2\u04eb\u0087\3\2"+
		"\2\2\u04ec\u04ea\3\2\2\2\u04ed\u04ee\bE\1\2\u04ee\u04ef\5\6\4\2\u04ef"+
		"\u04f6\3\2\2\2\u04f0\u04f1\f\3\2\2\u04f1\u04f2\5\u0122\u0092\2\u04f2\u04f3"+
		"\5\6\4\2\u04f3\u04f5\3\2\2\2\u04f4\u04f0\3\2\2\2\u04f5\u04f8\3\2\2\2\u04f6"+
		"\u04f4\3\2\2\2\u04f6\u04f7\3\2\2\2\u04f7\u0089\3\2\2\2\u04f8\u04f6\3\2"+
		"\2\2\u04f9\u04fa\bF\1\2\u04fa\u04fb\5\u00acW\2\u04fb\u0501\3\2\2\2\u04fc"+
		"\u04fd\f\3\2\2\u04fd\u04fe\7\17\2\2\u04fe\u0500\5\u00acW\2\u04ff\u04fc"+
		"\3\2\2\2\u0500\u0503\3\2\2\2\u0501\u04ff\3\2\2\2\u0501\u0502\3\2\2\2\u0502"+
		"\u008b\3\2\2\2\u0503\u0501\3\2\2\2\u0504\u0505\7e\2\2\u0505\u050f\5\u008e"+
		"H\2\u0506\u0507\7e\2\2\u0507\u050f\5\u0090I\2\u0508\u0509\7e\2\2\u0509"+
		"\u050f\5\u0094K\2\u050a\u050b\7h\2\2\u050b\u050f\7\u009c\2\2\u050c\u050d"+
		"\7h\2\2\u050d\u050f\5X-\2\u050e\u0504\3\2\2\2\u050e\u0506\3\2\2\2\u050e"+
		"\u0508\3\2\2\2\u050e\u050a\3\2\2\2\u050e\u050c\3\2\2\2\u050f\u008d\3\2"+
		"\2\2\u0510\u0512\7\24\2\2\u0511\u0513\5\u0092J\2\u0512\u0511\3\2\2\2\u0512"+
		"\u0513\3\2\2\2\u0513\u0514\3\2\2\2\u0514\u0515\7\25\2\2\u0515\u008f\3"+
		"\2\2\2\u0516\u0518\7&\2\2\u0517\u0519\5\u0092J\2\u0518\u0517\3\2\2\2\u0518"+
		"\u0519\3\2\2\2\u0519\u051a\3\2\2\2\u051a\u051b\7$\2\2\u051b\u0091\3\2"+
		"\2\2\u051c\u051d\bJ\1\2\u051d\u051e\5X-\2\u051e\u0524\3\2\2\2\u051f\u0520"+
		"\f\3\2\2\u0520\u0521\7\17\2\2\u0521\u0523\5X-\2\u0522\u051f\3\2\2\2\u0523"+
		"\u0526\3\2\2\2\u0524\u0522\3\2\2\2\u0524\u0525\3\2\2\2\u0525\u0093\3\2"+
		"\2\2\u0526\u0524\3\2\2\2\u0527\u0528\7\24\2\2\u0528\u0529\5X-\2\u0529"+
		"\u052a\7\20\2\2\u052a\u052b\5X-\2\u052b\u052c\7\25\2\2\u052c\u0095\3\2"+
		"\2\2\u052d\u052e\bL\1\2\u052e\u052f\5\u0098M\2\u052f\u053a\3\2\2\2\u0530"+
		"\u0531\f\5\2\2\u0531\u0539\7(\2\2\u0532\u0533\f\4\2\2\u0533\u0534\7\24"+
		"\2\2\u0534\u0539\7\25\2\2\u0535\u0536\f\3\2\2\u0536\u0537\7\26\2\2\u0537"+
		"\u0539\7\27\2\2\u0538\u0530\3\2\2\2\u0538\u0532\3\2\2\2\u0538\u0535\3"+
		"\2\2\2\u0539\u053c\3\2\2\2\u053a\u0538\3\2\2\2\u053a\u053b\3\2\2\2\u053b"+
		"\u0097\3\2\2\2\u053c\u053a\3\2\2\2\u053d\u0540\5\u009aN\2\u053e\u0540"+
		"\5\u009cO\2\u053f\u053d\3\2\2\2\u053f\u053e\3\2\2\2\u0540\u0099\3\2\2"+
		"\2\u0541\u0550\7\60\2\2\u0542\u0550\7\61\2\2\u0543\u0550\7\62\2\2\u0544"+
		"\u0550\7=\2\2\u0545\u0550\7\63\2\2\u0546\u0550\7\64\2\2\u0547\u0550\7"+
		";\2\2\u0548\u0550\7\65\2\2\u0549\u0550\7\67\2\2\u054a\u0550\7\66\2\2\u054b"+
		"\u0550\78\2\2\u054c\u0550\7:\2\2\u054d\u0550\7<\2\2\u054e\u0550\7>\2\2"+
		"\u054f\u0541\3\2\2\2\u054f\u0542\3\2\2\2\u054f\u0543\3\2\2\2\u054f\u0544"+
		"\3\2\2\2\u054f\u0545\3\2\2\2\u054f\u0546\3\2\2\2\u054f\u0547\3\2\2\2\u054f"+
		"\u0548\3\2\2\2\u054f\u0549\3\2\2\2\u054f\u054a\3\2\2\2\u054f\u054b\3\2"+
		"\2\2\u054f\u054c\3\2\2\2\u054f\u054d\3\2\2\2\u054f\u054e\3\2\2\2\u0550"+
		"\u009b\3\2\2\2\u0551\u0552\7\u0098\2\2\u0552\u009d\3\2\2\2\u0553\u0554"+
		"\7:\2\2\u0554\u009f\3\2\2\2\u0555\u0559\5\f\7\2\u0556\u0559\5\34\17\2"+
		"\u0557\u0559\5\16\b\2\u0558\u0555\3\2\2\2\u0558\u0556\3\2\2\2\u0558\u0557"+
		"\3\2\2\2\u0559\u00a1\3\2\2\2\u055a\u055b\bR\1\2\u055b\u055c\5\u00aaV\2"+
		"\u055c\u0562\3\2\2\2\u055d\u055e\f\3\2\2\u055e\u055f\7\17\2\2\u055f\u0561"+
		"\5\u00aaV\2\u0560\u055d\3\2\2\2\u0561\u0564\3\2\2\2\u0562\u0560\3\2\2"+
		"\2\u0562\u0563\3\2\2\2\u0563\u00a3\3\2\2\2\u0564\u0562\3\2\2\2\u0565\u0568"+
		"\5\u00a8U\2\u0566\u0568\5\u00aaV\2\u0567\u0565\3\2\2\2\u0567\u0566\3\2"+
		"\2\2\u0568\u00a5\3\2\2\2\u0569\u056d\5\u00a8U\2\u056a\u056d\5\u00aaV\2"+
		"\u056b\u056d\5\u00acW\2\u056c\u0569\3\2\2\2\u056c\u056a\3\2\2\2\u056c"+
		"\u056b\3\2\2\2\u056d\u00a7\3\2\2\2\u056e\u056f\7\u0099\2\2\u056f\u00a9"+
		"\3\2\2\2\u0570\u0571\7\u0098\2\2\u0571\u00ab\3\2\2\2\u0572\u0573\7\u0097"+
		"\2\2\u0573\u00ad\3\2\2\2\u0574\u0575\bX\1\2\u0575\u0576\5\u00b0Y\2\u0576"+
		"\u057c\3\2\2\2\u0577\u0578\f\3\2\2\u0578\u0579\7\17\2\2\u0579\u057b\5"+
		"\u00b0Y\2\u057a\u0577\3\2\2\2\u057b\u057e\3\2\2\2\u057c\u057a\3\2\2\2"+
		"\u057c\u057d\3\2\2\2\u057d\u00af\3\2\2\2\u057e\u057c\3\2\2\2\u057f\u0585"+
		"\5\u00b6\\\2\u0580\u0582\7l\2\2\u0581\u0580\3\2\2\2\u0581\u0582\3\2\2"+
		"\2\u0582\u0583\3\2\2\2\u0583\u0585\5\u00b2Z\2\u0584\u057f\3\2\2\2\u0584"+
		"\u0581\3\2\2\2\u0585\u00b1\3\2\2\2\u0586\u0589\5\u00b4[\2\u0587\u0589"+
		"\5.\30\2\u0588\u0586\3\2\2\2\u0588\u0587\3\2\2\2\u0589\u00b3\3\2\2\2\u058a"+
		"\u058d\5\u00a8U\2\u058b\u058c\7)\2\2\u058c\u058e\5\u00f2z\2\u058d\u058b"+
		"\3\2\2\2\u058d\u058e\3\2\2\2\u058e\u00b5\3\2\2\2\u058f\u0590\5\u009eP"+
		"\2\u0590\u0591\5\u00a8U\2\u0591\u00b7\3\2\2\2\u0592\u0595\5\u0096L\2\u0593"+
		"\u0595\5\u00ba^\2\u0594\u0592\3\2\2\2\u0594\u0593\3\2\2\2\u0595\u00b9"+
		"\3\2\2\2\u0596\u0597\b^\1\2\u0597\u0598\7C\2\2\u0598\u05a1\3\2\2\2\u0599"+
		"\u059a\f\4\2\2\u059a\u059b\7\24\2\2\u059b\u05a0\7\25\2\2\u059c\u059d\f"+
		"\3\2\2\u059d\u059e\7\26\2\2\u059e\u05a0\7\27\2\2\u059f\u0599\3\2\2\2\u059f"+
		"\u059c\3\2\2\2\u05a0\u05a3\3\2\2\2\u05a1\u059f\3\2\2\2\u05a1\u05a2\3\2"+
		"\2\2\u05a2\u00bb\3\2\2\2\u05a3\u05a1\3\2\2\2\u05a4\u05a5\b_\1\2\u05a5"+
		"\u05a6\5\u00be`\2\u05a6\u05ad\3\2\2\2\u05a7\u05a8\f\3\2\2\u05a8\u05a9"+
		"\5\u0122\u0092\2\u05a9\u05aa\5\u00be`\2\u05aa\u05ac\3\2\2\2\u05ab\u05a7"+
		"\3\2\2\2\u05ac\u05af\3\2\2\2\u05ad\u05ab\3\2\2\2\u05ad\u05ae\3\2\2\2\u05ae"+
		"\u00bd\3\2\2\2\u05af\u05ad\3\2\2\2\u05b0\u05b6\5\26\f\2\u05b1\u05b6\5"+
		"\30\r\2\u05b2\u05b6\5&\24\2\u05b3\u05b6\5$\23\2\u05b4\u05b6\5\24\13\2"+
		"\u05b5\u05b0\3\2\2\2\u05b5\u05b1\3\2\2\2\u05b5\u05b2\3\2\2\2\u05b5\u05b3"+
		"\3\2\2\2\u05b5\u05b4\3\2\2\2\u05b6\u00bf\3\2\2\2\u05b7\u05b8\ba\1\2\u05b8"+
		"\u05b9\5\u00c2b\2\u05b9\u05c0\3\2\2\2\u05ba\u05bb\f\3\2\2\u05bb\u05bc"+
		"\5\u0122\u0092\2\u05bc\u05bd\5\u00c2b\2\u05bd\u05bf\3\2\2\2\u05be\u05ba"+
		"\3\2\2\2\u05bf\u05c2\3\2\2\2\u05c0\u05be\3\2\2\2\u05c0\u05c1\3\2\2\2\u05c1"+
		"\u00c1\3\2\2\2\u05c2\u05c0\3\2\2\2\u05c3\u05c6\5\u00be`\2\u05c4\u05c6"+
		"\5(\25\2\u05c5\u05c3\3\2\2\2\u05c5\u05c4\3\2\2\2\u05c6\u00c3\3\2\2\2\u05c7"+
		"\u05c8\7\7\2\2\u05c8\u05d2\5\u016c\u00b7\2\u05c9\u05ca\7\b\2\2\u05ca\u05d2"+
		"\5\u0186\u00c4\2\u05cb\u05cc\7\t\2\2\u05cc\u05d2\5\u00c6d\2\u05cd\u05ce"+
		"\7\n\2\2\u05ce\u05d2\5\u00c6d\2\u05cf\u05d0\7\13\2\2\u05d0\u05d2\5\u00cc"+
		"g\2\u05d1\u05c7\3\2\2\2\u05d1\u05c9\3\2\2\2\u05d1\u05cb\3\2\2\2\u05d1"+
		"\u05cd\3\2\2\2\u05d1\u05cf\3\2\2\2\u05d2\u00c5\3\2\2\2\u05d3\u05d5\5\u00a6"+
		"T\2\u05d4\u05d6\5\u00c8e\2\u05d5\u05d4\3\2\2\2\u05d5\u05d6\3\2\2\2\u05d6"+
		"\u00c7\3\2\2\2\u05d7\u05d8\7b\2\2\u05d8\u05d9\5\u00caf\2\u05d9\u05da\7"+
		"\r\2\2\u05da\u05df\5\u00a6T\2\u05db\u05dc\7\21\2\2\u05dc\u05de\5\u00a6"+
		"T\2\u05dd\u05db\3\2\2\2\u05de\u05e1\3\2\2\2\u05df\u05dd\3\2\2\2\u05df"+
		"\u05e0\3\2\2\2\u05e0\u00c9\3\2\2\2\u05e1\u05df\3\2\2\2\u05e2\u05e3\7\u0099"+
		"\2\2\u05e3\u05e4\6f\65\3\u05e4\u00cb\3\2\2\2\u05e5\u05e7\5\u00a6T\2\u05e6"+
		"\u05e8\5\u00ceh\2\u05e7\u05e6\3\2\2\2\u05e7\u05e8\3\2\2\2\u05e8\u00cd"+
		"\3\2\2\2\u05e9\u05ea\7b\2\2\u05ea\u05eb\5\u00caf\2\u05eb\u05ed\7\r\2\2"+
		"\u05ec\u05ee\7!\2\2\u05ed\u05ec\3\2\2\2\u05ed\u05ee\3\2\2\2\u05ee\u05ef"+
		"\3\2\2\2\u05ef\u05f4\5\u013c\u009f\2\u05f0\u05f1\7!\2\2\u05f1\u05f3\5"+
		"\u013c\u009f\2\u05f2\u05f0\3\2\2\2\u05f3\u05f6\3\2\2\2\u05f4\u05f2\3\2"+
		"\2\2\u05f4\u05f5\3\2\2\2\u05f5\u05f9\3\2\2\2\u05f6\u05f4\3\2\2\2\u05f7"+
		"\u05f8\7\21\2\2\u05f8\u05fa\5\u013c\u009f\2\u05f9\u05f7\3\2\2\2\u05f9"+
		"\u05fa\3\2\2\2\u05fa\u00cf\3\2\2\2\u05fb\u05fc\bi\1\2\u05fc\u05fd\5\u00a8"+
		"U\2\u05fd\u0603\3\2\2\2\u05fe\u05ff\f\3\2\2\u05ff\u0600\7\17\2\2\u0600"+
		"\u0602\5\u00a8U\2\u0601\u05fe\3\2\2\2\u0602\u0605\3\2\2\2\u0603\u0601"+
		"\3\2\2\2\u0603\u0604\3\2\2\2\u0604\u00d1\3\2\2\2\u0605\u0603\3\2\2\2\u0606"+
		"\u060b\5$\23\2\u0607\u060b\5&\24\2\u0608\u060b\5(\25\2\u0609\u060b\5*"+
		"\26\2\u060a\u0606\3\2\2\2\u060a\u0607\3\2\2\2\u060a\u0608\3\2\2\2\u060a"+
		"\u0609\3\2\2\2\u060b\u00d3\3\2\2\2\u060c\u060d\7\6\2\2\u060d\u00d5\3\2"+
		"\2\2\u060e\u060f\bl\1\2\u060f\u0610\5\u00d8m\2\u0610\u0617\3\2\2\2\u0611"+
		"\u0612\f\3\2\2\u0612\u0613\5\u0122\u0092\2\u0613\u0614\5\u00d8m\2\u0614"+
		"\u0616\3\2\2\2\u0615\u0611\3\2\2\2\u0616\u0619\3\2\2\2\u0617\u0615\3\2"+
		"\2\2\u0617\u0618\3\2\2\2\u0618\u00d7\3\2\2\2\u0619\u0617\3\2\2\2\u061a"+
		"\u061b\7\7\2\2\u061b\u0625\5\u0156\u00ac\2\u061c\u061d\7\b\2\2\u061d\u0625"+
		"\5\u0172\u00ba\2\u061e\u061f\7\t\2\2\u061f\u0625\5\u00dan\2\u0620\u0621"+
		"\7\n\2\2\u0621\u0625\5\u00dan\2\u0622\u0623\7\13\2\2\u0623\u0625\5\u00dc"+
		"o\2\u0624\u061a\3\2\2\2\u0624\u061c\3\2\2\2\u0624\u061e\3\2\2\2\u0624"+
		"\u0620\3\2\2\2\u0624\u0622\3\2\2\2\u0625\u00d9\3\2\2\2\u0626\u0628\5\u013e"+
		"\u00a0\2\u0627\u0629\7\16\2\2\u0628\u0627\3\2\2\2\u0628\u0629\3\2\2\2"+
		"\u0629\u062b\3\2\2\2\u062a\u062c\5\u00c8e\2\u062b\u062a\3\2\2\2\u062b"+
		"\u062c\3\2\2\2\u062c\u00db\3\2\2\2\u062d\u062f\5\u0124\u0093\2\u062e\u0630"+
		"\7\16\2\2\u062f\u062e\3\2\2\2\u062f\u0630\3\2\2\2\u0630\u0632\3\2\2\2"+
		"\u0631\u0633\5\u00ceh\2\u0632\u0631\3\2\2\2\u0632\u0633\3\2\2\2\u0633"+
		"\u00dd\3\2\2\2\u0634\u0635\bp\1\2\u0635\u0636\5\62\32\2\u0636\u063d\3"+
		"\2\2\2\u0637\u0638\f\3\2\2\u0638\u0639\5\u0122\u0092\2\u0639\u063a\5\62"+
		"\32\2\u063a\u063c\3\2\2\2\u063b\u0637\3\2\2\2\u063c\u063f\3\2\2\2\u063d"+
		"\u063b\3\2\2\2\u063d\u063e\3\2\2\2\u063e\u00df\3\2\2\2\u063f\u063d\3\2"+
		"\2\2\u0640\u0641\bq\1\2\u0641\u0642\5,\27\2\u0642\u0649\3\2\2\2\u0643"+
		"\u0644\f\3\2\2\u0644\u0645\5\u0122\u0092\2\u0645\u0646\5,\27\2\u0646\u0648"+
		"\3\2\2\2\u0647\u0643\3\2\2\2\u0648\u064b\3\2\2\2\u0649\u0647\3\2\2\2\u0649"+
		"\u064a\3\2\2\2\u064a\u00e1\3\2\2\2\u064b\u0649\3\2\2\2\u064c\u064d\br"+
		"\1\2\u064d\u064e\5<\37\2\u064e\u0655\3\2\2\2\u064f\u0650\f\3\2\2\u0650"+
		"\u0651\5\u0122\u0092\2\u0651\u0652\5<\37\2\u0652\u0654\3\2\2\2\u0653\u064f"+
		"\3\2\2\2\u0654\u0657\3\2\2\2\u0655\u0653\3\2\2\2\u0655\u0656\3\2\2\2\u0656"+
		"\u00e3\3\2\2\2\u0657\u0655\3\2\2\2\u0658\u0659\bs\1\2\u0659\u065a\5L\'"+
		"\2\u065a\u0661\3\2\2\2\u065b\u065c\f\3\2\2\u065c\u065d\5\u0122\u0092\2"+
		"\u065d\u065e\5L\'\2\u065e\u0660\3\2\2\2\u065f\u065b\3\2\2\2\u0660\u0663"+
		"\3\2\2\2\u0661\u065f\3\2\2\2\u0661\u0662\3\2\2\2\u0662\u00e5\3\2\2\2\u0663"+
		"\u0661\3\2\2\2\u0664\u0665\7\24\2\2\u0665\u0666\5\u00e8u\2\u0666\u0667"+
		"\7\20\2\2\u0667\u0668\5\u00e8u\2\u0668\u0669\7\25\2\2\u0669\u0673\3\2"+
		"\2\2\u066a\u066b\7\24\2\2\u066b\u066c\5\u00eav\2\u066c\u066d\7\25\2\2"+
		"\u066d\u0673\3\2\2\2\u066e\u066f\7&\2\2\u066f\u0670\5\u00eav\2\u0670\u0671"+
		"\7$\2\2\u0671\u0673\3\2\2\2\u0672\u0664\3\2\2\2\u0672\u066a\3\2\2\2\u0672"+
		"\u066e\3\2\2\2\u0673\u00e7\3\2\2\2\u0674\u0682\7\u0095\2\2\u0675\u0682"+
		"\7\u0096\2\2\u0676\u0682\7\u009d\2\2\u0677\u0682\7\u009e\2\2\u0678\u0682"+
		"\7\u0094\2\2\u0679\u0682\7\u00a2\2\2\u067a\u0682\7\u00a1\2\2\u067b\u0682"+
		"\7\u009c\2\2\u067c\u0682\7\u009f\2\2\u067d\u0682\7\u00a0\2\2\u067e\u0682"+
		"\7\u0093\2\2\u067f\u0682\7\u00a3\2\2\u0680\u0682\5z>\2\u0681\u0674\3\2"+
		"\2\2\u0681\u0675\3\2\2\2\u0681\u0676\3\2\2\2\u0681\u0677\3\2\2\2\u0681"+
		"\u0678\3\2\2\2\u0681\u0679\3\2\2\2\u0681\u067a\3\2\2\2\u0681\u067b\3\2"+
		"\2\2\u0681\u067c\3\2\2\2\u0681\u067d\3\2\2\2\u0681\u067e\3\2\2\2\u0681"+
		"\u067f\3\2\2\2\u0681\u0680\3\2\2\2\u0682\u00e9\3\2\2\2\u0683\u0684\bv"+
		"\1\2\u0684\u0685\5\u00e8u\2\u0685\u068b\3\2\2\2\u0686\u0687\f\3\2\2\u0687"+
		"\u0688\7\17\2\2\u0688\u068a\5\u00e8u\2\u0689\u0686\3\2\2\2\u068a\u068d"+
		"\3\2\2\2\u068b\u0689\3\2\2\2\u068b\u068c\3\2\2\2\u068c\u00eb\3\2\2\2\u068d"+
		"\u068b\3\2\2\2\u068e\u0693\5\u00f0y\2\u068f\u0693\5\u00f2z\2\u0690\u0693"+
		"\5\u00a6T\2\u0691\u0693\5\u00eex\2\u0692\u068e\3\2\2\2\u0692\u068f\3\2"+
		"\2\2\u0692\u0690\3\2\2\2\u0692\u0691\3\2\2\2\u0693\u00ed\3\2\2\2\u0694"+
		"\u0695\t\3\2\2\u0695\u00ef\3\2\2\2\u0696\u0697\7\22\2\2\u0697\u0698\5"+
		"X-\2\u0698\u0699\7\23\2\2\u0699\u00f1\3\2\2\2\u069a\u069d\5\u00e8u\2\u069b"+
		"\u069d\5\u00f4{\2\u069c\u069a\3\2\2\2\u069c\u069b\3\2\2\2\u069d\u00f3"+
		"\3\2\2\2\u069e\u06a4\5\u0094K\2\u069f\u06a4\5\u008eH\2\u06a0\u06a4\5\u0090"+
		"I\2\u06a1\u06a4\5\u00f8}\2\u06a2\u06a4\5\u00f6|\2\u06a3\u069e\3\2\2\2"+
		"\u06a3\u069f\3\2\2\2\u06a3\u06a0\3\2\2\2\u06a3\u06a1\3\2\2\2\u06a3\u06a2"+
		"\3\2\2\2\u06a4\u00f5\3\2\2\2\u06a5\u06a7\7\22\2\2\u06a6\u06a8\5\u00fa"+
		"~\2\u06a7\u06a6\3\2\2\2\u06a7\u06a8\3\2\2\2\u06a8\u06a9\3\2\2\2\u06a9"+
		"\u06aa\7\23\2\2\u06aa\u00f7\3\2\2\2\u06ab\u06ad\7\26\2\2\u06ac\u06ae\5"+
		"\u00fc\177\2\u06ad\u06ac\3\2\2\2\u06ad\u06ae\3\2\2\2\u06ae\u06af\3\2\2"+
		"\2\u06af\u06b0\7\27\2\2\u06b0\u00f9\3\2\2\2\u06b1\u06b2\b~\1\2\u06b2\u06b3"+
		"\5X-\2\u06b3\u06b9\3\2\2\2\u06b4\u06b5\f\3\2\2\u06b5\u06b6\7\17\2\2\u06b6"+
		"\u06b8\5X-\2\u06b7\u06b4\3\2\2\2\u06b8\u06bb\3\2\2\2\u06b9\u06b7\3\2\2"+
		"\2\u06b9\u06ba\3\2\2\2\u06ba\u00fb\3\2\2\2\u06bb\u06b9\3\2\2\2\u06bc\u06bd"+
		"\b\177\1\2\u06bd\u06be\5\u00fe\u0080\2\u06be\u06c4\3\2\2\2\u06bf\u06c0"+
		"\f\3\2\2\u06c0\u06c1\7\17\2\2\u06c1\u06c3\5\u00fe\u0080\2\u06c2\u06bf"+
		"\3\2\2\2\u06c3\u06c6\3\2\2\2\u06c4\u06c2\3\2\2\2\u06c4\u06c5\3\2\2\2\u06c5"+
		"\u00fd\3\2\2\2\u06c6\u06c4\3\2\2\2\u06c7\u06c8\5X-\2\u06c8\u06c9\7\r\2"+
		"\2\u06c9\u06ca\5X-\2\u06ca\u00ff\3\2\2\2\u06cb\u06cc\5X-\2\u06cc\u06cd"+
		"\7\r\2\2\u06cd\u06ce\5X-\2\u06ce\u06d5\3\2\2\2\u06cf\u06d0\5X-\2\u06d0"+
		"\u06d1\7\r\2\2\u06d1\u06d5\3\2\2\2\u06d2\u06d3\7\r\2\2\u06d3\u06d5\5X"+
		"-\2\u06d4\u06cb\3\2\2\2\u06d4\u06cf\3\2\2\2\u06d4\u06d2\3\2\2\2\u06d5"+
		"\u0101\3\2\2\2\u06d6\u06d7\5\u00a8U\2\u06d7\u06d8\5\u0116\u008c\2\u06d8"+
		"\u06d9\5X-\2\u06d9\u0103\3\2\2\2\u06da\u06db\b\u0083\1\2\u06db\u06dc\5"+
		"\u00a8U\2\u06dc\u06e1\3\2\2\2\u06dd\u06de\f\3\2\2\u06de\u06e0\5v<\2\u06df"+
		"\u06dd\3\2\2\2\u06e0\u06e3\3\2\2\2\u06e1\u06df\3\2\2\2\u06e1\u06e2\3\2"+
		"\2\2\u06e2\u0105\3\2\2\2\u06e3\u06e1\3\2\2\2\u06e4\u06e5\6\u0084@\3\u06e5"+
		"\u06e6\7\u0099\2\2\u06e6\u06e9\5\u00b8]\2\u06e7\u06e9\5X-\2\u06e8\u06e4"+
		"\3\2\2\2\u06e8\u06e7\3\2\2\2\u06e9\u0107\3\2\2\2\u06ea\u06ef\5\u010a\u0086"+
		"\2\u06eb\u06ec\7\17\2\2\u06ec\u06ee\5\u010a\u0086\2\u06ed\u06eb\3\2\2"+
		"\2\u06ee\u06f1\3\2\2\2\u06ef\u06ed\3\2\2\2\u06ef\u06f0\3\2\2\2\u06f0\u0109"+
		"\3\2\2\2\u06f1\u06ef\3\2\2\2\u06f2\u06f7\5\u00a8U\2\u06f3\u06f4\7\21\2"+
		"\2\u06f4\u06f6\5\u00a8U\2\u06f5\u06f3\3\2\2\2\u06f6\u06f9\3\2\2\2\u06f7"+
		"\u06f5\3\2\2\2\u06f7\u06f8\3\2\2\2\u06f8\u06fb\3\2\2\2\u06f9\u06f7\3\2"+
		"\2\2\u06fa\u06fc\t\4\2\2\u06fb\u06fa\3\2\2\2\u06fb\u06fc\3\2\2\2\u06fc"+
		"\u010b\3\2\2\2\u06fd\u0704\7\36\2\2\u06fe\u0704\7\37\2\2\u06ff\u0704\5"+
		"\u0118\u008d\2\u0700\u0704\5\u011a\u008e\2\u0701\u0704\5\u011c\u008f\2"+
		"\u0702\u0704\5\u011e\u0090\2\u0703\u06fd\3\2\2\2\u0703\u06fe\3\2\2\2\u0703"+
		"\u06ff\3\2\2\2\u0703\u0700\3\2\2\2\u0703\u0701\3\2\2\2\u0703\u0702\3\2"+
		"\2\2\u0704\u010d\3\2\2\2\u0705\u0706\7\u0099\2\2\u0706\u0707\6\u0088A"+
		"\3\u0707\u010f\3\2\2\2\u0708\u0709\7\u0099\2\2\u0709\u070a\6\u0089B\3"+
		"\u070a\u0111\3\2\2\2\u070b\u070c\7\u0099\2\2\u070c\u070d\6\u008aC\3\u070d"+
		"\u0113\3\2\2\2\u070e\u070f\7\u0099\2\2\u070f\u0710\6\u008bD\3\u0710\u0115"+
		"\3\2\2\2\u0711\u0712\7)\2\2\u0712\u0117\3\2\2\2\u0713\u0714\7 \2\2\u0714"+
		"\u0119\3\2\2\2\u0715\u0716\7!\2\2\u0716\u011b\3\2\2\2\u0717\u0718\7\""+
		"\2\2\u0718\u011d\3\2\2\2\u0719\u071a\t\5\2\2\u071a\u011f\3\2\2\2\u071b"+
		"\u071c\3\2\2\2\u071c\u0121\3\2\2\2\u071d\u071e\3\2\2\2\u071e\u0123\3\2"+
		"\2\2\u071f\u0720\7~\2\2\u0720\u0721\5\u0126\u0094\2\u0721\u0722\7\16\2"+
		"\2\u0722\u0727\3\2\2\2\u0723\u0724\5\u0126\u0094\2\u0724\u0725\7\16\2"+
		"\2\u0725\u0727\3\2\2\2\u0726\u071f\3\2\2\2\u0726\u0723\3\2\2\2\u0727\u0125"+
		"\3\2\2\2\u0728\u0729\b\u0094\1\2\u0729\u072a\5\u0128\u0095\2\u072a\u072f"+
		"\3\2\2\2\u072b\u072c\f\3\2\2\u072c\u072e\5\u012e\u0098\2\u072d\u072b\3"+
		"\2\2\2\u072e\u0731\3\2\2\2\u072f\u072d\3\2\2\2\u072f\u0730\3\2\2\2\u0730"+
		"\u0127\3\2\2\2\u0731\u072f\3\2\2\2\u0732\u073a\5\u012a\u0096\2\u0733\u073a"+
		"\5\u012c\u0097\2\u0734\u073a\5\u0136\u009c\2\u0735\u073a\5\u0138\u009d"+
		"\2\u0736\u073a\5\u013a\u009e\2\u0737\u073a\5\u0130\u0099\2\u0738\u073a"+
		"\5\u0134\u009b\2\u0739\u0732\3\2\2\2\u0739\u0733\3\2\2\2\u0739\u0734\3"+
		"\2\2\2\u0739\u0735\3\2\2\2\u0739\u0736\3\2\2\2\u0739\u0737\3\2\2\2\u0739"+
		"\u0738\3\2\2\2\u073a\u0129\3\2\2\2\u073b\u073c\5\u00eex\2\u073c\u012b"+
		"\3\2\2\2\u073d\u073e\5\u010e\u0088\2\u073e\u073f\5\u0130\u0099\2\u073f"+
		"\u012d\3\2\2\2\u0740\u0741\7\21\2\2\u0741\u0746\5\u0130\u0099\2\u0742"+
		"\u0743\7\21\2\2\u0743\u0746\5\u013c\u009f\2\u0744\u0746\5\u0134\u009b"+
		"\2\u0745\u0740\3\2\2\2\u0745\u0742\3\2\2\2\u0745\u0744\3\2\2\2\u0746\u012f"+
		"\3\2\2\2\u0747\u0748\5\u013c\u009f\2\u0748\u074a\7\22\2\2\u0749\u074b"+
		"\5\u0132\u009a\2\u074a\u0749\3\2\2\2\u074a\u074b\3\2\2\2\u074b\u074c\3"+
		"\2\2\2\u074c\u074d\7\23\2\2\u074d\u0131\3\2\2\2\u074e\u074f\b\u009a\1"+
		"\2\u074f\u0750\5\u0126\u0094\2\u0750\u0756\3\2\2\2\u0751\u0752\f\3\2\2"+
		"\u0752\u0753\7\17\2\2\u0753\u0755\5\u0126\u0094\2\u0754\u0751\3\2\2\2"+
		"\u0755\u0758\3\2\2\2\u0756\u0754\3\2\2\2\u0756\u0757\3\2\2\2\u0757\u0133"+
		"\3\2\2\2\u0758\u0756\3\2\2\2\u0759\u075a\7\24\2\2\u075a\u075b\5\u0126"+
		"\u0094\2\u075b\u075c\7\25\2\2\u075c\u0135\3\2\2\2\u075d\u075e\7\22\2\2"+
		"\u075e\u075f\5\u0126\u0094\2\u075f\u0760\7\23\2\2\u0760\u0137\3\2\2\2"+
		"\u0761\u0762\5\u013c\u009f\2\u0762\u0139\3\2\2\2\u0763\u0769\7\u009d\2"+
		"\2\u0764\u0769\7\u009f\2\2\u0765\u0769\7\u009c\2\2\u0766\u0769\7\u0093"+
		"\2\2\u0767\u0769\7\u0094\2\2\u0768\u0763\3\2\2\2\u0768\u0764\3\2\2\2\u0768"+
		"\u0765\3\2\2\2\u0768\u0766\3\2\2\2\u0768\u0767\3\2\2\2\u0769\u013b\3\2"+
		"\2\2\u076a\u076b\t\6\2\2\u076b\u013d\3\2\2\2\u076c\u076d\7~\2\2\u076d"+
		"\u0770\5\u0140\u00a1\2\u076e\u0770\5\u0140\u00a1\2\u076f\u076c\3\2\2\2"+
		"\u076f\u076e\3\2\2\2\u0770\u013f\3\2\2\2\u0771\u0772\b\u00a1\1\2\u0772"+
		"\u0773\5\u0142\u00a2\2\u0773\u0778\3\2\2\2\u0774\u0775\f\3\2\2\u0775\u0777"+
		"\5\u0144\u00a3\2\u0776\u0774\3\2\2\2\u0777\u077a\3\2\2\2\u0778\u0776\3"+
		"\2\2\2\u0778\u0779\3\2\2\2\u0779\u0141\3\2\2\2\u077a\u0778\3\2\2\2\u077b"+
		"\u0780\5\u014e\u00a8\2\u077c\u0780\5\u0150\u00a9\2\u077d\u0780\5\u0152"+
		"\u00aa\2\u077e\u0780\5\u0146\u00a4\2\u077f\u077b\3\2\2\2\u077f\u077c\3"+
		"\2\2\2\u077f\u077d\3\2\2\2\u077f\u077e\3\2\2\2\u0780\u0143\3\2\2\2\u0781"+
		"\u0782\7\21\2\2\u0782\u0788\5\u0146\u00a4\2\u0783\u0784\7\24\2\2\u0784"+
		"\u0785\5\u0140\u00a1\2\u0785\u0786\7\25\2\2\u0786\u0788\3\2\2\2\u0787"+
		"\u0781\3\2\2\2\u0787\u0783\3\2\2\2\u0788\u0145\3\2\2\2\u0789\u078a\5\u0154"+
		"\u00ab\2\u078a\u078c\7\22\2\2\u078b\u078d\5\u0148\u00a5\2\u078c\u078b"+
		"\3\2\2\2\u078c\u078d\3\2\2\2\u078d\u078e\3\2\2\2\u078e\u078f\7\23\2\2"+
		"\u078f\u0147\3\2\2\2\u0790\u0797\5\u014a\u00a6\2\u0791\u0797\5\u014c\u00a7"+
		"\2\u0792\u0793\5\u014a\u00a6\2\u0793\u0794\7\17\2\2\u0794\u0795\5\u014c"+
		"\u00a7\2\u0795\u0797\3\2\2\2\u0796\u0790\3\2\2\2\u0796\u0791\3\2\2\2\u0796"+
		"\u0792\3\2\2\2\u0797\u0149\3\2\2\2\u0798\u0799\b\u00a6\1\2\u0799\u079a"+
		"\5\u0140\u00a1\2\u079a\u07a0\3\2\2\2\u079b\u079c\f\3\2\2\u079c\u079d\7"+
		"\17\2\2\u079d\u079f\5\u0140\u00a1\2\u079e\u079b\3\2\2\2\u079f\u07a2\3"+
		"\2\2\2\u07a0\u079e\3\2\2\2\u07a0\u07a1\3\2\2\2\u07a1\u014b\3\2\2\2\u07a2"+
		"\u07a0\3\2\2\2\u07a3\u07a4\b\u00a7\1\2\u07a4\u07a5\5\u0154\u00ab\2\u07a5"+
		"\u07a6\7)\2\2\u07a6\u07a7\5\u0140\u00a1\2\u07a7\u07b0\3\2\2\2\u07a8\u07a9"+
		"\f\3\2\2\u07a9\u07aa\7\17\2\2\u07aa\u07ab\5\u0154\u00ab\2\u07ab\u07ac"+
		"\7)\2\2\u07ac\u07ad\5\u0140\u00a1\2\u07ad\u07af\3\2\2\2\u07ae\u07a8\3"+
		"\2\2\2\u07af\u07b2\3\2\2\2\u07b0\u07ae\3\2\2\2\u07b0\u07b1\3\2\2\2\u07b1"+
		"\u014d\3\2\2\2\u07b2\u07b0\3\2\2\2\u07b3\u07b4\7\22\2\2\u07b4\u07b5\5"+
		"\u0140\u00a1\2\u07b5\u07b6\7\23\2\2\u07b6\u014f\3\2\2\2\u07b7\u07b8\b"+
		"\u00a9\1\2\u07b8\u07bb\7\u009b\2\2\u07b9\u07bb\5\u0154\u00ab\2\u07ba\u07b7"+
		"\3\2\2\2\u07ba\u07b9\3\2\2\2\u07bb\u07c1\3\2\2\2\u07bc\u07bd\f\3\2\2\u07bd"+
		"\u07be\7\21\2\2\u07be\u07c0\5\u0154\u00ab\2\u07bf\u07bc\3\2\2\2\u07c0"+
		"\u07c3\3\2\2\2\u07c1\u07bf\3\2\2\2\u07c1\u07c2\3\2\2\2\u07c2\u0151\3\2"+
		"\2\2\u07c3\u07c1\3\2\2\2\u07c4\u07ca\7\u009d\2\2\u07c5\u07ca\7\u009f\2"+
		"\2\u07c6\u07ca\7\u009c\2\2\u07c7\u07ca\7\u0093\2\2\u07c8\u07ca\7\u0094"+
		"\2\2\u07c9\u07c4\3\2\2\2\u07c9\u07c5\3\2\2\2\u07c9\u07c6\3\2\2\2\u07c9"+
		"\u07c7\3\2\2\2\u07c9\u07c8\3\2\2\2\u07ca\u0153\3\2\2\2\u07cb\u07cc\t\7"+
		"\2\2\u07cc\u0155\3\2\2\2\u07cd\u07ce\7~\2\2\u07ce\u07cf\5\u0158\u00ad"+
		"\2\u07cf\u07d0\7\16\2\2\u07d0\u07d5\3\2\2\2\u07d1\u07d2\5\u0158\u00ad"+
		"\2\u07d2\u07d3\7\16\2\2\u07d3\u07d5\3\2\2\2\u07d4\u07cd\3\2\2\2\u07d4"+
		"\u07d1\3\2\2\2\u07d5\u0157\3\2\2\2\u07d6\u07d7\b\u00ad\1\2\u07d7\u07d8"+
		"\5\u015a\u00ae\2\u07d8\u07dd\3\2\2\2\u07d9\u07da\f\3\2\2\u07da\u07dc\5"+
		"\u0160\u00b1\2\u07db\u07d9\3\2\2\2\u07dc\u07df\3\2\2\2\u07dd\u07db\3\2"+
		"\2\2\u07dd\u07de\3\2\2\2\u07de\u0159\3\2\2\2\u07df\u07dd\3\2\2\2\u07e0"+
		"\u07e6\5\u015c\u00af\2\u07e1\u07e6\5\u015e\u00b0\2\u07e2\u07e6\5\u0168"+
		"\u00b5\2\u07e3\u07e6\5\u016a\u00b6\2\u07e4\u07e6\5\u016e\u00b8\2\u07e5"+
		"\u07e0\3\2\2\2\u07e5\u07e1\3\2\2\2\u07e5\u07e2\3\2\2\2\u07e5\u07e3\3\2"+
		"\2\2\u07e5\u07e4\3\2\2\2\u07e6\u015b\3\2\2\2\u07e7\u07e8\5\u00eex\2\u07e8"+
		"\u015d\3\2\2\2\u07e9\u07ea\5\u010e\u0088\2\u07ea\u07eb\5\u0162\u00b2\2"+
		"\u07eb\u015f\3\2\2\2\u07ec\u07ed\7\21\2\2\u07ed\u07f0\5\u0162\u00b2\2"+
		"\u07ee\u07f0\5\u0166\u00b4\2\u07ef\u07ec\3\2\2\2\u07ef\u07ee\3\2\2\2\u07f0"+
		"\u0161\3\2\2\2\u07f1\u07f2\5\u0170\u00b9\2\u07f2\u07f4\7\22\2\2\u07f3"+
		"\u07f5\5\u0164\u00b3\2\u07f4\u07f3\3\2\2\2\u07f4\u07f5\3\2\2\2\u07f5\u07f6"+
		"\3\2\2\2\u07f6\u07f7\7\23\2\2\u07f7\u0163\3\2\2\2\u07f8\u07f9\b\u00b3"+
		"\1\2\u07f9\u07fa\5\u0158\u00ad\2\u07fa\u0800\3\2\2\2\u07fb\u07fc\f\3\2"+
		"\2\u07fc\u07fd\7\17\2\2\u07fd\u07ff\5\u0158\u00ad\2\u07fe\u07fb\3\2\2"+
		"\2\u07ff\u0802\3\2\2\2\u0800\u07fe\3\2\2\2\u0800\u0801\3\2\2\2\u0801\u0165"+
		"\3\2\2\2\u0802\u0800\3\2\2\2\u0803\u0804\7\24\2\2\u0804\u0805\5\u0158"+
		"\u00ad\2\u0805\u0806\7\25\2\2\u0806\u0167\3\2\2\2\u0807\u0808\7\22\2\2"+
		"\u0808\u0809\5\u0158\u00ad\2\u0809\u080a\7\23\2\2\u080a\u0169\3\2\2\2"+
		"\u080b\u080c\b\u00b6\1\2\u080c\u080d\5\u0170\u00b9\2\u080d\u0813\3\2\2"+
		"\2\u080e\u080f\f\3\2\2\u080f\u0810\7\21\2\2\u0810\u0812\5\u0170\u00b9"+
		"\2\u0811\u080e\3\2\2\2\u0812\u0815\3\2\2\2\u0813\u0811\3\2\2\2\u0813\u0814"+
		"\3\2\2\2\u0814\u016b\3\2\2\2\u0815\u0813\3\2\2\2\u0816\u0817\b\u00b7\1"+
		"\2\u0817\u0818\5\u016a\u00b6\2\u0818\u081d\3\2\2\2\u0819\u081a\f\3\2\2"+
		"\u081a\u081c\7\u009b\2\2\u081b\u0819\3\2\2\2\u081c\u081f\3\2\2\2\u081d"+
		"\u081b\3\2\2\2\u081d\u081e\3\2\2\2\u081e\u016d\3\2\2\2\u081f\u081d\3\2"+
		"\2\2\u0820\u0826\7\u009d\2\2\u0821\u0826\7\u009f\2\2\u0822\u0826\7\u009c"+
		"\2\2\u0823\u0826\7\u0093\2\2\u0824\u0826\7\u0094\2\2\u0825\u0820\3\2\2"+
		"\2\u0825\u0821\3\2\2\2\u0825\u0822\3\2\2\2\u0825\u0823\3\2\2\2\u0825\u0824"+
		"\3\2\2\2\u0826\u016f\3\2\2\2\u0827\u0828\t\b\2\2\u0828\u0171\3\2\2\2\u0829"+
		"\u082a\7~\2\2\u082a\u082b\5\u0174\u00bb\2\u082b\u082c\7\16\2\2\u082c\u0831"+
		"\3\2\2\2\u082d\u082e\5\u0174\u00bb\2\u082e\u082f\7\16\2\2\u082f\u0831"+
		"\3\2\2\2\u0830\u0829\3\2\2\2\u0830\u082d\3\2\2\2\u0831\u0173\3\2\2\2\u0832"+
		"\u0833\b\u00bb\1\2\u0833\u0834\5\u0176\u00bc\2\u0834\u0839\3\2\2\2\u0835"+
		"\u0836\f\3\2\2\u0836\u0838\5\u017c\u00bf\2\u0837\u0835\3\2\2\2\u0838\u083b"+
		"\3\2\2\2\u0839\u0837\3\2\2\2\u0839\u083a\3\2\2\2\u083a\u0175\3\2\2\2\u083b"+
		"\u0839\3\2\2\2\u083c\u0842\5\u0178\u00bd\2\u083d\u0842\5\u017a\u00be\2"+
		"\u083e\u0842\5\u0184\u00c3\2\u083f\u0842\5\u0186\u00c4\2\u0840\u0842\5"+
		"\u0188\u00c5\2\u0841\u083c\3\2\2\2\u0841\u083d\3\2\2\2\u0841\u083e\3\2"+
		"\2\2\u0841\u083f\3\2\2\2\u0841\u0840\3\2\2\2\u0842\u0177\3\2\2\2\u0843"+
		"\u0844\5\u00eex\2\u0844\u0179\3\2\2\2\u0845\u0846\5\u010e\u0088\2\u0846"+
		"\u0847\5\u017e\u00c0\2\u0847\u017b\3\2\2\2\u0848\u0849\7\21\2\2\u0849"+
		"\u084c\5\u017e\u00c0\2\u084a\u084c\5\u0182\u00c2\2\u084b\u0848\3\2\2\2"+
		"\u084b\u084a\3\2\2\2\u084c\u017d\3\2\2\2\u084d\u084e\5\u018a\u00c6\2\u084e"+
		"\u0850\7\22\2\2\u084f\u0851\5\u0180\u00c1\2\u0850\u084f\3\2\2\2\u0850"+
		"\u0851\3\2\2\2\u0851\u0852\3\2\2\2\u0852\u0853\7\23\2\2\u0853\u017f\3"+
		"\2\2\2\u0854\u0855\b\u00c1\1\2\u0855\u0856\5\u0174\u00bb\2\u0856\u085c"+
		"\3\2\2\2\u0857\u0858\f\3\2\2\u0858\u0859\7\17\2\2\u0859\u085b\5\u0174"+
		"\u00bb\2\u085a\u0857\3\2\2\2\u085b\u085e\3\2\2\2\u085c\u085a\3\2\2\2\u085c"+
		"\u085d\3\2\2\2\u085d\u0181\3\2\2\2\u085e\u085c\3\2\2\2\u085f\u0860\7\24"+
		"\2\2\u0860\u0861\5\u0174\u00bb\2\u0861\u0862\7\25\2\2\u0862\u0183\3\2"+
		"\2\2\u0863\u0864\7\22\2\2\u0864\u0865\5\u0174\u00bb\2\u0865\u0866\7\23"+
		"\2\2\u0866\u0185\3\2\2\2\u0867\u0868\b\u00c4\1\2\u0868\u086b\7\u009b\2"+
		"\2\u0869\u086b\5\u018a\u00c6\2\u086a\u0867\3\2\2\2\u086a\u0869\3\2\2\2"+
		"\u086b\u0871\3\2\2\2\u086c\u086d\f\3\2\2\u086d\u086e\7\21\2\2\u086e\u0870"+
		"\5\u018a\u00c6\2\u086f\u086c\3\2\2\2\u0870\u0873\3\2\2\2\u0871\u086f\3"+
		"\2\2\2\u0871\u0872\3\2\2\2\u0872\u0187\3\2\2\2\u0873\u0871\3\2\2\2\u0874"+
		"\u087a\7\u009d\2\2\u0875\u087a\7\u009f\2\2\u0876\u087a\7\u009c\2\2\u0877"+
		"\u087a\7\u0093\2\2\u0878\u087a\7\u0094\2\2\u0879\u0874\3\2\2\2\u0879\u0875"+
		"\3\2\2\2\u0879\u0876\3\2\2\2\u0879\u0877\3\2\2\2\u0879\u0878\3\2\2\2\u087a"+
		"\u0189\3\2\2\2\u087b\u087c\t\t\2\2\u087c\u018b\3\2\2\2\u00b8\u0193\u0197"+
		"\u01b2\u01b9\u01be\u01c6\u01ca\u01d4\u01e0\u01e6\u01e9\u01ec\u01f5\u01fd"+
		"\u0205\u0210\u0215\u021a\u0223\u0228\u023c\u0247\u024c\u0252\u0258\u025e"+
		"\u0263\u0268\u026f\u0286\u0290\u0295\u029c\u029e\u02b3\u02d0\u02d2\u02da"+
		"\u02e1\u02e3\u02eb\u02f5\u030a\u030e\u0322\u032f\u0333\u033b\u033e\u0343"+
		"\u0346\u034e\u0359\u035d\u0361\u0368\u0371\u037a\u0383\u039c\u0409\u040b"+
		"\u041b\u0424\u0443\u044f\u0454\u045e\u0465\u046d\u046f\u0479\u0487\u048a"+
		"\u048f\u0498\u049f\u04b1\u04bb\u04c9\u04d1\u04d9\u04df\u04ea\u04f6\u0501"+
		"\u050e\u0512\u0518\u0524\u0538\u053a\u053f\u054f\u0558\u0562\u0567\u056c"+
		"\u057c\u0581\u0584\u0588\u058d\u0594\u059f\u05a1\u05ad\u05b5\u05c0\u05c5"+
		"\u05d1\u05d5\u05df\u05e7\u05ed\u05f4\u05f9\u0603\u060a\u0617\u0624\u0628"+
		"\u062b\u062f\u0632\u063d\u0649\u0655\u0661\u0672\u0681\u068b\u0692\u069c"+
		"\u06a3\u06a7\u06ad\u06b9\u06c4\u06d4\u06e1\u06e8\u06ef\u06f7\u06fb\u0703"+
		"\u0726\u072f\u0739\u0745\u074a\u0756\u0768\u076f\u0778\u077f\u0787\u078c"+
		"\u0796\u07a0\u07b0\u07ba\u07c1\u07c9\u07d4\u07dd\u07e5\u07ef\u07f4\u0800"+
		"\u0813\u081d\u0825\u0830\u0839\u0841\u084b\u0850\u085c\u086a\u0871\u0879";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}