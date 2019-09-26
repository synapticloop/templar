package synapticloop.templar.helper;

/*
 * Copyright (c) 2012-2019 synapticloop.
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

public class StringHelper {
	private StringHelper() {}

	/**
	 * Escape a string to HTML
	 * 
	 * @param source the source to be escaped
	 * 
	 * @return the HTML escaped string
	 */
	public static String escapeHtml(String source) {
		if(source==null) {
			return ""; 
		}

		StringBuilder stringBuilder = new StringBuilder(""); 
		char[] chars = source.toCharArray(); 

		for(int i=0;i<chars.length;i++) {
			boolean found=true; 
			switch(chars[i]) {
				case 60:
					stringBuilder.append("&lt;"); // <
					break;
				case 62:
					stringBuilder.append("&gt;"); // >
					break;
				case 34:
					stringBuilder.append("&quot;"); // "
					break;
				case 38:
					stringBuilder.append("&amp;"); // &
					break;
				default:
					found=false;
					break;
			} 
			if(!found) {
				stringBuilder.append(chars[i]); 
			}

		} 
		return stringBuilder.toString();
	}
}
