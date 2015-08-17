package synapticloop.templar.utils;

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.BasePositionToken;

public class ParserHelper {

	private ParserHelper() {}

	public static boolean didFindEndToken(StringTokenizer stringTokenizer, StringBuilder stringBuilder) {
		while(stringTokenizer.hasMoreTokens()) {
			String nextToken = stringTokenizer.nextToken();
			if("}".equals(nextToken)) {
				return(true);
			} else {
				stringBuilder.append(nextToken);
			}
		}
		return false;
	}

	public static void parseToEndToken(BasePositionToken basePositionToken, StringTokenizer stringTokenizer, String tokenName) throws ParseException {
		if(stringTokenizer.hasMoreTokens()) {
			String nextToken = stringTokenizer.nextToken();
			if(!"}".equals(nextToken)) {
				throw new ParseException("Could not find end token marker '}' for the " + tokenName + " token, found '" + nextToken + "'.", basePositionToken);
			}
		} else {
			throw new ParseException("Could not find end token marker '}' for the tab token.", basePositionToken);
		}
	}

}
