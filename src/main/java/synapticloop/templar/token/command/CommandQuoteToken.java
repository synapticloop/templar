package synapticloop.templar.token.command;

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

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class CommandQuoteToken extends CommandLineToken {

	public CommandQuoteToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
	}

	protected void tokeniseQuote(String quotationMark, StringTokenizer stringTokenizer) throws ParseException {
		StringBuilder stringBuilder = new StringBuilder();
		boolean foundFinalQuote = false;
		boolean isEscaped = false;
		while(stringTokenizer.hasMoreElements()) {
			String nextToken = stringTokenizer.nextToken();
			if("\\".equals(nextToken) || nextToken.endsWith("\\")) {
				isEscaped = true;
			}

			if(quotationMark.equals(nextToken)) {
				if(isEscaped) {
					// we have an escape character quotation mark - ignore
					isEscaped = false;
				} else {
					foundFinalQuote = true;
					break;
				}
			}
			stringBuilder.append(nextToken);
		}

		if(!foundFinalQuote) {
			throw new ParseException("Could not find ending quotation mark (" + quotationMark + ")");
		}

		evaluateCommand = stringBuilder.toString();
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		return(evaluateCommand);
	}

}
