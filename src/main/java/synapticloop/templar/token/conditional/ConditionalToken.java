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

import java.util.ArrayList;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.token.BasePositionToken;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public abstract class ConditionalToken extends BasePositionToken {
	protected String value = null;
	protected ArrayList<ConditionalToken> childConditionalTokens = null;

	public ConditionalToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		this.value = value;
		this.characterNumber = tokeniser.getTokeniserInfo().getCharacterNumber();
		this.lineNumber = tokeniser.getTokeniserInfo().getLineNumber();
		// do nothing with the string tokenizer
	}

	public abstract Object evaluate(TemplarContext templarContext) throws RenderException;

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append(this.getClass().getSimpleName());
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(value);
		stringBuilder.append(")>");

		if(null != childConditionalTokens) {
			for (ConditionalToken conditionalToken : childConditionalTokens) {
				stringBuilder.append(conditionalToken.toString());
			}
		}

		stringBuilder.append("</");
		stringBuilder.append(this.getClass().getSimpleName());
		stringBuilder.append(">");
		return(stringBuilder.toString());
	}
}
