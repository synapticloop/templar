package synapticloop.templar.token;

/*
 * Copyright (c) 2012-2013 synapticloop.
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

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.utils.TemplarContext;


public class TabToken extends Token {

	public TabToken(String value, StringTokenizer stringTokenizer) throws ParseException {
		super(value, stringTokenizer);
		if(stringTokenizer.hasMoreTokens()) {
			if(!stringTokenizer.nextToken().equals("}")) {
				// TODO add what the token found was
				throw new ParseException("Could not find end token marker '}' for the tab token.", this);
			}
		} else {
			throw new ParseException("Could not find end token marker '}' for the tab token.", this);
		}
	}

	public String render(TemplarContext templarContext) {
		return("\t");
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<TAB");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" />");

		return(stringBuilder.toString());
	}
}
