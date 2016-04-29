package prompto.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import prompto.error.ReadWriteError;
import prompto.intrinsic.PromptoDocument;

public abstract class CSVReader {
	
	public static Iterator<PromptoDocument<String, Object>> iterator(String data, Character separator, Character encloser) throws IOException {
		BufferedReader reader = data==null ? null : new BufferedReader(new StringReader(data));
		return iterator(reader, separator, encloser);
	}
	
	public static Iterator<PromptoDocument<String, Object>> iterator(final BufferedReader reader, Character separator, Character encloser) {
		
		char sep = separator==null ? ',' : separator.charValue();
		char quote = encloser==null ? '"' : encloser.charValue();
		
		return new Iterator<PromptoDocument<String, Object>>() {
			
			BufferedReader buffered = reader;
			ArrayList<String> headers = null;
			String nextLine;
			
			@Override
			public boolean hasNext() {
				try {
					if(headers==null)
						parseHeaders();
					if(nextLine==null)
						nextLine = nextLine();
					return nextLine!=null;
				} catch (IOException e) {
					throw new ReadWriteError(e.getMessage());
				}
			}
			
			private String nextLine() throws IOException {
				if(buffered==null)
					return null;
				String line = buffered.readLine();
				if(line!=null)
					return line;
				buffered.close();
				buffered = null;
				return line;
			}

			private void parseHeaders() throws IOException {
				String line = nextLine();
				if(line!=null)
					headers = parseLine(line);
			}

			private ArrayList<String> parseLine(String line) {
				ArrayList<String> list = new ArrayList<>();
				char[] chars = line.toCharArray();
				int nextIdx = 0;
				while(nextIdx<chars.length)
					nextIdx = parseValue(chars, nextIdx, list);
				return list;
			}

			private int parseValue(char[] chars, int startIdx, ArrayList<String> list) {
				if(chars[startIdx]==sep) {
					list.add(null);
					return startIdx + 1;
				} else if(chars[startIdx]==quote)
					return parseQuotedValue(chars, startIdx + 1, list);
				else 
					return parseUnquotedValue(chars, startIdx, list);
			}

			private int parseQuotedValue(char[] chars, int startIdx, ArrayList<String> list) {
				int endIdx = parseValue(chars, startIdx, quote, list);
				// look for next sep
				while(endIdx<chars.length && chars[endIdx]!=sep)
					endIdx++;
				return endIdx + 1;
			}

			private int parseUnquotedValue(char[] chars, int startIdx, ArrayList<String> list) {
				return parseValue(chars, startIdx, sep, list);
			}
			
			private int parseValue(char[] chars, int startIdx, char endChar, ArrayList<String> list) {
				boolean escape = false;
				boolean found = false;
				int endIdx = startIdx;
				while(endIdx<chars.length) {
					if(chars[endIdx]==endChar) {
						found = true;
						break;
					}
					if(chars[endIdx]=='\\') {
						escape = true;
						endIdx++;
					}
					if(endIdx<=chars.length)
						endIdx++;
				}
				String value = escape ? 
						unescape(chars, startIdx, endIdx) :
						new String(chars, startIdx, endIdx - startIdx);
				list.add(value);
				return endIdx + (found ? 1 : 0); 
			}


			private String unescape(char[] chars, int startIdx, int endIdx) {
				StringBuilder sb = new StringBuilder();
				while(startIdx<endIdx) {
					if(chars[startIdx]=='\\')
						startIdx++;
					if(startIdx<endIdx)
						sb.append(chars[startIdx++]);
				}
				return sb.toString();
			}

			@Override
			public PromptoDocument<String, Object> next() {
				if(!hasNext())
					return null;
				String line = nextLine;
				nextLine = null;
				ArrayList<String> values = parseLine(line);
				PromptoDocument<String, Object> doc = new PromptoDocument<>();
				for(int i=0;i<headers.size();i++) {
					if(i<values.size())
						doc.put(headers.get(i), values.get(i));
					else
						doc.put(headers.get(i), null);
				}
				return doc;
			}
		};
	}
}