package synapticloop.templar.utils;

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
import java.util.Vector;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.CommentToken;
import synapticloop.templar.token.DumpContextToken;
import synapticloop.templar.token.ElseToken;
import synapticloop.templar.token.EndIfToken;
import synapticloop.templar.token.EndLoopToken;
import synapticloop.templar.token.EvaluationToken;
import synapticloop.templar.token.IfToken;
import synapticloop.templar.token.LoopToken;
import synapticloop.templar.token.NewLineToken;
import synapticloop.templar.token.SetToken;
import synapticloop.templar.token.TabToken;
import synapticloop.templar.token.TextToken;
import synapticloop.templar.token.Token;
import synapticloop.templar.token.conditional.AndToken;
import synapticloop.templar.token.conditional.ConditionalEvaluationToken;
import synapticloop.templar.token.conditional.ConditionalToken;
import synapticloop.templar.token.conditional.FunctionToken;
import synapticloop.templar.token.conditional.GroupToken;
import synapticloop.templar.token.conditional.NotToken;
import synapticloop.templar.token.conditional.OrToken;

public class Tokeniser {

	public static Vector<Token> tokenise(String contents) throws ParseException {
		Vector<Token> tokens = new Vector<Token>();
		StringTokenizer stringTokenizer = new StringTokenizer(contents, " \n\t{}", true);
		tokens.addAll(tokenise(stringTokenizer));
		return(tokens);
	}

	public static Vector<Token> tokenise(StringTokenizer stringTokenizer) throws ParseException {
		Vector<Token> tokens = new Vector<Token>();

		while(stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			TokeniserInfo.incrementCharacter(token.length());

			if(token.equals("{")) {
				if(stringTokenizer.hasMoreTokens()) {
					token = stringTokenizer.nextToken();
					if(token.equals("{")) {
						// looks like we actually want to have a '{' character
						tokens.add(new TextToken(token, stringTokenizer));
					} else if(token.equals("--")) {
						tokens.add(new CommentToken(token, stringTokenizer));
					} else if(token.equals("nl")) {
						tokens.add(new NewLineToken(token, stringTokenizer));
					} else if(token.equals("\\n")) {
						tokens.add(new NewLineToken(token, stringTokenizer));
					} else if(token.equals("tab")) {
						tokens.add(new TabToken(token, stringTokenizer));
					} else if(token.equals("\\t")) {
						tokens.add(new TabToken(token, stringTokenizer));
					} else if(token.equals("set")) {
						tokens.add(new SetToken(token, stringTokenizer));
					} else if(token.equals("if")) {
						tokens.add(new IfToken(token, stringTokenizer));
					} else if(token.equals("loop")) {
						tokens.add(new LoopToken(token, stringTokenizer));
					} else if(token.equals("dumpcontext")) {
						tokens.add(new DumpContextToken("", stringTokenizer));
					} else if(token.equals("else")) {
						tokens.add(new ElseToken("", stringTokenizer));
						return(tokens);
					} else if(token.equals("endif")) {
						tokens.add(new EndIfToken("", stringTokenizer));
						return(tokens);
					} else if(token.equals("endloop")) {
						tokens.add(new EndLoopToken("", stringTokenizer));
						return(tokens);
					} else {
						tokens.add(new EvaluationToken(token, stringTokenizer));
					}
				} else {
					throw new ParseException("Found an '{' character, with no more tokens after it");
				}
			} else {
				if(token.equals("\n")) {
					TokeniserInfo.incrementLine();
				}
				tokens.add(new TextToken(token, stringTokenizer));
			}
		}

		return(tokens);
	}

	public static Vector<ConditionalToken> tokeniseCommandLine(StringTokenizer stringTokenizer) throws ParseException {
		// at this point we want to evaluate the if statement
		Vector<ConditionalToken> conditionalTokens = new Vector<ConditionalToken>();

		while(stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			if(token.equalsIgnoreCase("\n")) {
				TokeniserInfo.incrementLine();
			}
			token = token.trim();

			if(token.equals("")) {
				continue;
			}
			if(token.equals("(")) {
				// start of grouping
				conditionalTokens.add(new GroupToken(token, stringTokenizer));
			} else if(token.equals(")")) {
				// end of grouping
				return(conditionalTokens);
			} else if(token.equals("&")) {
				conditionalTokens.add(new AndToken(token, stringTokenizer));
			} else if(token.equals("!")) {
				conditionalTokens.add(new NotToken(token, stringTokenizer));
			} else if(token.equals("|")) {
				conditionalTokens.add(new OrToken(token, stringTokenizer));
			} else if(token.startsWith("fn:")) {
				// have the start of a function
				conditionalTokens.add(new FunctionToken(token, stringTokenizer));
			} else {
				// this is an evaluator
				conditionalTokens.add(new ConditionalEvaluationToken(token, stringTokenizer));
			}
		}

		return(conditionalTokens);
	}
}
