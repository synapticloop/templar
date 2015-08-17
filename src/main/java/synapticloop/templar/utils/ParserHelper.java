package synapticloop.templar.utils;

import java.util.StringTokenizer;

import synapticloop.templar.token.Token;

public class ParserHelper {
	public static boolean didFindEndToken(Token token, StringTokenizer stringTokenizer, StringBuilder stringBuilder) {
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

}
