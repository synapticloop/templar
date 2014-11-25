package synapticloop.templar.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtils {

	public static String md5Hash(String message) {
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
		} catch (UnsupportedEncodingException ex) { 
		} catch (NoSuchAlgorithmException ex) { 
		} 
		return digest; 
	}
}
