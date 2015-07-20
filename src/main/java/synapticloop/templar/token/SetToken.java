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

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class SetToken extends CommandToken {
	private static final long serialVersionUID = 8666832881610826478L;

	public List<Token> childTokens = new ArrayList<Token>();

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

		// now make sure commandLine is correct
		if(!isCorrectAsStatement(commandLine)) {
			throw new ParseException(" Incorrect statement for '" + commandLine + "', could not find ' as ' token.  Format is {set something as somethingElse}.", this);
		}
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		String[] commandSplit = commandLine.split(" as ");

		if(commandSplit.length != 2) {
			throw new RenderException("command in incorrect format '" + commandLine + "', should be 'something as somethingElse'.");
		}

		String setCommand = commandSplit[0].trim();

		Object object = null;
		if(setCommand.startsWith("fn:")) {
			// yay - we have a function - get the functionName
			int startArgs = setCommand.indexOf("[");
			if(startArgs == -1) {
				throw new RenderException("Could not find function argument start token of '[' for function '" + setCommand + "'.");
			}
			int endArgs = setCommand.indexOf("]");
			if(endArgs == -1) {
				throw new RenderException("Could not find function argument end token of ']' for function '" + setCommand + "'.");
			}

			// get the arguments as strings, then convert them into objects
			String functionName = setCommand.substring(3, startArgs);
			String[] args = setCommand.substring(startArgs +1, endArgs).split(",");

			if(null == args || args.length == 0) {
				throw new RenderException("Could not parse arguments for function '" + setCommand + "'.");
			}

			Object[] objectArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				String string = args[i];

				if(null != string) {
					string = string.trim();
				}

				// at this point - we may need to lookup as an evaluation token.
				// we do need to look up as evaluationToken
				Object evalObject = ObjectUtils.evaluateObjectToDefault(string, templarContext);

				if(null != evalObject) {
					objectArgs[i] = evalObject;
				} else {
					objectArgs[i] = string;
				}
			}

			try {
				object = templarContext.invokeFunction(functionName, objectArgs, templarContext);
			} catch (FunctionException fex) {
				throw new RenderException("Command " + commandLine + "' exception, " + fex.getMessage(), fex);
			}
		} else {
			object = ObjectUtils.evaluateObjectToDefault(setCommand, templarContext);
		}

		String contextAs = commandSplit[1].trim();
		templarContext.add(contextAs, object);

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
