package synapticloop.templar.utils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import synapticloop.templar.token.Token;

public class ParserCache {
	private static ConcurrentHashMap<String, ArrayList<Token>> PARSER_CACHE = new ConcurrentHashMap<String, ArrayList<Token>>();
	
	public static boolean getIsInCache(String md5Hash) {
		return(PARSER_CACHE.containsKey(md5Hash));
	}

	public static ArrayList<Token> getCached(String md5Hash) {
		return(PARSER_CACHE.get(md5Hash));
	}
}
