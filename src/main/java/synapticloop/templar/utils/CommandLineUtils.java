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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.command.CommandDoubleQuoteToken;
import synapticloop.templar.token.command.CommandEvaluationToken;
import synapticloop.templar.token.command.CommandFunctionToken;
import synapticloop.templar.token.command.CommandLineToken;
import synapticloop.templar.token.command.CommandNotToken;
import synapticloop.templar.token.command.CommandSingleQuoteToken;

public class CommandLineUtils {
	public static List<CommandLineToken> parseCommandLine(String commandLine) throws ParseException {
		List<CommandLineToken> commandTokens= new ArrayList<CommandLineToken>();
		StringTokenizer stringTokenizer = new StringTokenizer(commandLine, "!:\"'[]", true);

		while(stringTokenizer.hasMoreElements()) {
			String nextToken = stringTokenizer.nextToken().trim();
			if(CommandLineToken.TOKEN_LOOKUP.containsKey(nextToken)) {
				switch (CommandLineToken.TOKEN_LOOKUP.get(nextToken)) {
				case CommandLineToken.EXCLAMATION_MARK:
					commandTokens.add(new CommandNotToken(stringTokenizer));
					break;
				case CommandLineToken.SINGLE_QUOTE:
					commandTokens.add(new CommandSingleQuoteToken(stringTokenizer));
					break;
				case CommandLineToken.DOUBLE_QUOTE:
					commandTokens.add(new CommandDoubleQuoteToken(stringTokenizer));
					break;
				case CommandLineToken.FUNCTION:
					commandTokens.add(new CommandFunctionToken(stringTokenizer));
					break;
				case CommandLineToken.FUNCTION_START:
				case CommandLineToken.FUNCTION_END:
				case CommandLineToken.COLON:
					throw new ParseException("Failure parsing command line, unexpected token '" + nextToken + "'.");
				default:
					commandTokens.add(new CommandEvaluationToken(new StringTokenizer(nextToken)));
					break;
				}
			} else {
				commandTokens.add(new CommandEvaluationToken(new StringTokenizer(nextToken)));
			}
		}
		return(commandTokens);
	}
}
