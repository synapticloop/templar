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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.token.command.CommandLineToken;
import synapticloop.templar.utils.CommandLineUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class SetToken extends CommandToken {
	private static final long serialVersionUID = 8666832881610826478L;

	public List<Token> childTokens = new ArrayList<Token>();
//	public List<Token> commandLineTokens = new ArrayList<Token>();

	private List<CommandLineToken> commandLineTokens;

	private String contextAs;

	public SetToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);

		StringBuilder stringBuilder = new StringBuilder();
		// look for the next token which should be either if, list, otherwise it
		// is an evaluation token

		boolean foundEndToken = false;

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String token = stringTokenizer.nextToken();
				if("}".equals(token)) {
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
		String[] commandSplit = commandLine.split(" as ");

		if(commandSplit.length != 2) {
			throw new ParseException("command in incorrect format '" + commandLine + "', should be 'something as somethingElse'.");
		}

		String setCommand = commandSplit[0].trim();

		// now make sure commandLine is correct
		if(!isCorrectAsStatement(commandLine)) {
			throw new ParseException(" Incorrect statement for '" + commandLine + "', could not find ' as ' token.  Format is {set something as somethingElse}.", this);
		}

		contextAs = commandSplit[1].trim();

		try {
			this.commandLineTokens = CommandLineUtils.parseCommandLine(setCommand);
		} catch (ParseException pex) {
			throw new ParseException("Could not parse command line '" + setCommand + "', message was: " + pex.getMessage(), pex);
		}

	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		for (CommandLineToken commandLineToken : commandLineTokens) {
			templarContext.add(contextAs, commandLineToken.evaluate(templarContext));
		}
		// we don't actually render anything for this...
		return("");
	}


	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<SET@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		if(null != commandLine) {
			stringBuilder.append(commandLine.trim());
		}
		stringBuilder.append(")>");
		return (stringBuilder.toString());
	}

}
