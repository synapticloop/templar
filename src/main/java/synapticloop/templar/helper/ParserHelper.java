package synapticloop.templar.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.BasePositionToken;
import synapticloop.templar.token.Token;

public class ParserHelper {
	private static Map<String, List<Token>> cachedTokens = new ConcurrentHashMap<String, List<Token>>();

	private ParserHelper() {}

	/**
	 * Consume the string tokenizer, adding tokens until the end token is found.
	 * If the end token is found, return true, otherwise return false
	 * 
	 * @param stringTokenizer the string tokenizer to use
	 * @param stringBuilder the String builder to append tokens to
	 * 
	 * @return whether the end token was found
	 */
	public static boolean consumeToEndToken(StringTokenizer stringTokenizer, StringBuilder stringBuilder) {
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

	/**
	 * Parse the string tokenizer to the end token
	 * 
	 * @param basePositionToken The base position token for outputting if in error
	 * @param stringTokenizer The string tokenizer
	 * @param tokenName the name of the token for output debugging
	 * 
	 * @throws ParseException If there was an error parsing the token
	 */
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


	public static boolean getIsInCache(String md5Hash) {
		return(cachedTokens.containsKey(md5Hash));
	}

	public static List<Token> getCached(String md5Hash) {
		return(cachedTokens.get(md5Hash));
	}

	public static String md5Hash(String message) throws ParseException {
		String digest = null; 
		try { 
			MessageDigest messageDigest = MessageDigest.getInstance("MD5"); 
			byte[] hash = messageDigest.digest(message.getBytes("UTF-8")); 
			//converting byte array to Hexadecimal String 
			StringBuilder stringBuilder = new StringBuilder(2*hash.length); 
			for(byte b : hash){ 
				stringBuilder.append(String.format("%02x", b&0xff));
			} 
			digest = stringBuilder.toString(); 
		} catch (UnsupportedEncodingException ueex) {
			throw new ParseException("Unsupported encoding exception", ueex);
		} catch (NoSuchAlgorithmException nsaex) {
			throw new ParseException("No such algorithm exception", nsaex);
		} 
		return digest; 
	}

}
