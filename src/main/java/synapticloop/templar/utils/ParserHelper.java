package synapticloop.templar.utils;

import java.util.StringTokenizer;

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

}
