package prompto.literal;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

import prompto.runtime.Context;
import prompto.type.IType;
import prompto.type.TextType;
import prompto.value.Text;


public class TextLiteral extends Literal<Text> {

	public TextLiteral(String text) {
		super(text,unescape(text));
	}

	private static Text unescape(String text) {
		StreamTokenizer parser = new StreamTokenizer(new StringReader(text));
		try {
		  parser.nextToken();
		  return new Text(parser.sval);
		}
		catch (IOException e) {
		  throw new RuntimeException(e);
		}
	}

	@Override
	public IType check(Context context) {
		return TextType.instance();
	}
	

}