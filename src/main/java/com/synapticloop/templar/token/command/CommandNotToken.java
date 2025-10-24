package com.synapticloop.templar.token.command;

/*
 * Copyright (c) 2012-2025 synapticloop.
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
import java.util.List;
import java.util.StringTokenizer;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;

public class CommandNotToken extends CommandLineToken {

	public CommandNotToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
		List<CommandLineToken> commandTokens = new ArrayList<CommandLineToken>();
		// at this point we are looking for either a function or an evaluation
		if(stringTokenizer.hasMoreElements()) {
			String nextToken = stringTokenizer.nextToken().trim();
			if(TOKEN_LOOKUP.containsKey(nextToken)) {
				switch(TOKEN_LOOKUP.get(nextToken)) {
				case EXCLAMATION_MARK:
					childTokens.add(new CommandNotToken(stringTokenizer));
					break;
				case DOUBLE_QUOTE:
				case SINGLE_QUOTE:
					throw new ParseException("Cannot have a '!' before a simgle quote (') or double quote (\")");
				case FUNCTION:
					childTokens.add(new CommandFunctionToken(stringTokenizer));
					break;
				default:
					break;
				}
			} else {
				childTokens.add(new CommandEvaluationToken(new StringTokenizer(nextToken)));
			}
		} else {
			throw new ParseException("Could not find next token after '!'");
		}
		commandTokens.add(this);
		this.evaluateCommand = "";
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		if(childTokens.size() != 1) {
			throw new RenderException("Can only evaluate the not '!' of one child token.");
		}

		CommandLineToken commandToken = childTokens.get(0);
		Object evaluate = commandToken.evaluate(templarContext);
		if(evaluate instanceof Boolean) {
			return(!((Boolean)evaluate).booleanValue());
		} else {
			// we couldn't invoke or lookup
			throw new RenderException("Can only evaluate the not '!' of boolean evaluation, type was: '" + evaluate.getClass().getName() + "', value was: '" + evaluate.toString() + "', command token: '" + commandToken + "'.");
		}
	}

}
