package synapticloop.templar.token.conditional;

/*
 * Copyright (c) 2012-2014 synapticloop.
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
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class GroupToken extends ConditionalToken {

	public GroupToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		// now go through the stringTokenizer and see whether we have a )
		childConditionalTokens = tokeniser.tokeniseCommandLine(stringTokenizer);
	}

	public Object evaluate(TemplarContext templarContext) throws RenderException {
		for (ConditionalToken conditionalToken : childConditionalTokens) {
			conditionalToken.evaluate(templarContext);
		}
		return(null);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append("'('@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(" >");
		stringBuilder.append(characterNumber);
		if(null != childConditionalTokens) {
			for (ConditionalToken conditionalToken : childConditionalTokens) {
				stringBuilder.append(conditionalToken);
			}
		}
		stringBuilder.append("<')'>");
		return(stringBuilder.toString());
	}
}
