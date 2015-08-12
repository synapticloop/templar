package synapticloop.templar.utils;

/*
 * Copyright (c) 2012-2015 synapticloop.
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtils {
	private HashUtils() {}

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
			// do nothing
		} catch (NoSuchAlgorithmException ex) {
			// do nothing
		} 
		return digest; 
	}
}
