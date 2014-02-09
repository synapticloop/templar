package synapticloop.templar.token;

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
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;

public class EvaluationToken extends CommandToken {

	/**
	 * Build an evaluation token which should be in the form of {to.be.evaluated}
	 *
	 * @param value the token value
	 * @param stringTokenizer
	 */
	public EvaluationToken(String value, StringTokenizer stringTokenizer) throws ParseException {
		super(value, stringTokenizer);

		StringBuilder stringBuilder = new StringBuilder();
		// look for the next token which should be either if, list, otherwise it
		// is an evaluation token
		// @TODO unless of course it is a fn token
		stringBuilder.append(value);

		boolean foundEndToken = false;

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String token = stringTokenizer.nextToken();
				if(token.equals("}")) {
					foundEndToken = true;
					break;
				} else {
					stringBuilder.append(token);
				}
			}
		} else {
			throw new ParseException("Could not find any more tokens for evaluation.", this);
		}

		if(!foundEndToken) {
			throw new ParseException("Could not find end token '}' for evaluation.", this);
		}
		this.commandLine = stringBuilder.toString();
	}

	public String render(TemplarContext templarContext) throws RenderException {
		StringBuilder stringBuilder = new StringBuilder();
		Object object = null;
		try {
			if(commandLine.startsWith("fn:")) {
				object = ObjectUtils.parseAndExecuteCommandLine(templarContext, commandLine);
			} else {
				object = ObjectUtils.evaluateObject(commandLine, templarContext);
			}
			stringBuilder.append(object);
		} catch(RenderException rex){
			throw new RenderException("Token '" + this.toString() + "' said: '" + rex.getMessage() + "'.");
		}


		return (stringBuilder.toString());
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<EVAL");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(commandLine);
		stringBuilder.append(")>");
		return (stringBuilder.toString());
	}
}