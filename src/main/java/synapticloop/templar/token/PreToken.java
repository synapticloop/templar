package synapticloop.templar.token;

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class PreToken extends Token {
	private static final long serialVersionUID = -1977612849877311984L;
	private String value = null;

	public PreToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			// discard the first token if it is a space
			String nextToken = stringTokenizer.nextToken();
			if(!" ".equals(nextToken)) {
				stringBuilder.append(nextToken);
			}
		} else {
			throw new ParseException("Found a 'pre' token with no more tokens", this);
		}

		boolean foundEndToken = false;
		while(stringTokenizer.hasMoreTokens()) {
			String nextToken = stringTokenizer.nextToken();
			if("pre".equals(nextToken)) {
				if(stringTokenizer.hasMoreTokens()) {
					nextToken = stringTokenizer.nextToken();
					if("}".equals(nextToken)) {
						// we have found the end 'pre'
						foundEndToken = true;
						break;
					} else {
						stringBuilder.append("pre");
						stringBuilder.append(nextToken);
					}
				} else {
					// we found a 'pre' token, without any more tokens
					throw new ParseException("Found a 'pre' token with no more tokens", this);
				}
			} else {
				stringBuilder.append(nextToken);
			}
		}

		if(!foundEndToken) {
			throw new ParseException("Unable to find the closing pre token 'pre}'", this);
		}

		int length = stringBuilder.length();
		if(stringBuilder.lastIndexOf(" ") == length -1) {
			// if it ends with a space, remove it
			stringBuilder.deleteCharAt(length -1);
		}
		this.value = stringBuilder.toString();
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		return(value);
	}

	@Override
	public String toString() {
		return(toString("PRE", value));
	}

}
