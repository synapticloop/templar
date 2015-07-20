package synapticloop.templar.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import synapticloop.templar.token.Token;

public class ParserCache {
	private static Map<String, List<Token>> parser_cache = new ConcurrentHashMap<String, List<Token>>();

	private ParserCache() {}

	public static boolean getIsInCache(String md5Hash) {
		return(parser_cache.containsKey(md5Hash));
	}

	public static List<Token> getCached(String md5Hash) {
		return(parser_cache.get(md5Hash));
	}
}
