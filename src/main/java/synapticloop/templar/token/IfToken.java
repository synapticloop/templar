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

import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseCommandException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.token.command.CommandLineToken;
import synapticloop.templar.utils.CommandLineUtils;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class IfToken extends CommandToken {
	private static final long serialVersionUID = 3233938579186241318L;

	private List<Token> elseCondition = null;
	private boolean inverse = false;

	private List<CommandLineToken> commandLineTokens;

	/**
	 * The if token is a conditional token with a command line
	 * 
	 * @param value
	 * @param stringTokenizer
	 * @param tokeniser
	 * @throws ParseException
	 */
	public IfToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String token = stringTokenizer.nextToken();

				tokeniser.getTokeniserInfo().incrementCharacter(token.length());

				if(token.equals("\n")) {
					tokeniser.getTokeniserInfo().incrementLine();
				} else if(token.equals("}")) {
					// now we need to go through and tokenise the inner tokeniser...;
					this.commandLine = stringBuilder.toString().trim();
					try {
						this.commandLineTokens = CommandLineUtils.parseCommandLine(commandLine);
					} catch (ParseCommandException pcex) {
						throw new ParseException("Could not parse command line '" + commandLine + "', message was: " + pcex.getMessage(), pcex);
					}

					this.addChildTokens(tokeniser.tokenise(stringTokenizer));

					// here the next token should be a '}'
					if(stringTokenizer.hasMoreTokens()) {
						String endToken = stringTokenizer.nextToken();
						tokeniser.getTokeniserInfo().incrementCharacter(token.length());

						if(!endToken.equals("}")) {
							throw new ParseException("Expecting '}' but found '" + endToken + "'at line: " + tokeniser.getTokeniserInfo().getLineNumber() + ", character: " + tokeniser.getTokeniserInfo().getCharacterNumber(), this);
						}
					} else {
						throw new ParseException("Expecting '}' but no more tokens found at line: " + tokeniser.getTokeniserInfo().getLineNumber() + ", character: " + tokeniser.getTokeniserInfo().getCharacterNumber(), this);
					}

					// check to see what the ending token is
					Token endingToken = childTokens.get(childTokens.size() -1);
					if(endingToken instanceof ElseToken) {
						// need to parse again
						elseCondition = tokeniser.tokenise(stringTokenizer);
						// here the next token should be a '}'
						if(stringTokenizer.hasMoreTokens()) {
							String endToken = stringTokenizer.nextToken();
							if(!endToken.equals("}")) {
								throw new ParseException("Expecting '}' but found '" + endToken + "'", this);
							}
						} else {
							throw new ParseException("Expecting '}' but no more tokens found.", this);
						}
					} else if(endingToken instanceof EndIfToken) {
						return;
					} else {
						throw new ParseException("Expecting '{endif}' or '{else}' but found '" + endingToken.value + "' instead.", this);
					}
					return;
				} else {
					stringBuilder.append(token);
				}
			}
		} else {
			// we are in an if token - but we have no more tokens :(
			// something
		}

		throw new ParseException("Expecting if conditional statement but no more tokens found.", this);
	}

	public String render(TemplarContext templarContext) throws RenderException {
		StringBuilder stringBuilder = new StringBuilder();
		// what we need to to is to grab the key out of the context...

		Object object = null;
		for (CommandLineToken commandLineToken : commandLineTokens) {
			object = commandLineToken.evaluate(templarContext);
		}

		if(object instanceof Boolean) {
			Boolean test = (Boolean)object;
			boolean testValue = test.booleanValue();
			if(inverse) {
				testValue = !testValue;
			}

			if(testValue) {
				for (Token token : childTokens) {
					stringBuilder.append(token.render(templarContext));
				}
			} else {
				// do we have an else condition?
				if(null != elseCondition) {
					for(Token token : elseCondition) {
						stringBuilder.append(token.render(templarContext));
					}
				}
			}
		} else {
			throw new RenderException("object '" + object + "' is not a Boolean returned from '" + commandLine + "'.");
		}

		return (stringBuilder.toString());
	}

	private Object parseAndExecuteCommandLine(TemplarContext templarContext) throws RenderException {

		String parseableCommandline = commandLine;
		if(commandLine.startsWith("!")) {
			inverse = true;
			parseableCommandline = commandLine.substring(1);
		}

		Object object = null;
		if(parseableCommandline.startsWith("fn:")) {
			// yay - we have a function - get the functionName
			int startArgs = parseableCommandline.indexOf("[");
			if(startArgs == -1) {
				throw new RenderException("Could not find function argument start token of '[' for function '" + parseableCommandline + "'.");
			}
			int endArgs = parseableCommandline.indexOf("]");
			if(endArgs == -1) {
				throw new RenderException("Could not find function argument end token of ']' for function '" + parseableCommandline + "'.");
			}

			// get the arguments as strings, then convert them into objects
			String functionName = parseableCommandline.substring(3, startArgs);
			String[] args = parseableCommandline.substring(startArgs +1, endArgs).split(",");
			if(null == args || args.length == 0) {
				throw new RenderException("Could not parse arguments for function '" + commandLine + "'.");
			}

			Object[] objectArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				String string = args[i];

				if(null != string) {
					string = string.trim();
				}
				// at this point - evaluate the token
//				objectArgs[i] = Utils.evaluateObject(string, templarContext);
				objectArgs[i] = string;
			}

			try {
				object = templarContext.invokeFunction(functionName, objectArgs, templarContext);
			} catch (FunctionException fex) {
				throw new RenderException("Command " + parseableCommandline + "' exception, " + fex.getMessage(), fex);
			}
		} else {
			object = ObjectUtils.evaluateObject(parseableCommandline, templarContext);
		}

		return(object);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<IF");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(commandLine);
		stringBuilder.append(")>");
		for (Token token : this.childTokens) {
			stringBuilder.append(token.toString());
		}

		if(null != elseCondition) {
			for (Token token: elseCondition) {
				stringBuilder.append(token.toString());
			}
		}

		return (stringBuilder.toString());
	}

}
