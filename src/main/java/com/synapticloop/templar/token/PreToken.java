package com.synapticloop.templar.token;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import java.util.StringTokenizer;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;

public class PreToken extends Token {
	private static final long serialVersionUID = -1977612849877311984L;
	private String value = null;

	public PreToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			// discard the first token if it is a space or newline
			String nextToken = stringTokenizer.nextToken();
			if(!" ".equals(nextToken) && !"\n".equals(nextToken)) {
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
		if(stringBuilder.lastIndexOf(" ") == length -1 || stringBuilder.lastIndexOf("\n") == length -1) {
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
