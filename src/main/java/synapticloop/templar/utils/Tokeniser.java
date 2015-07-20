package synapticloop.templar.utils;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.token.CommentToken;
import synapticloop.templar.token.DumpContextToken;
import synapticloop.templar.token.DumpFunctionsToken;
import synapticloop.templar.token.ElseToken;
import synapticloop.templar.token.EndIfToken;
import synapticloop.templar.token.EndLoopToken;
import synapticloop.templar.token.EvaluationToken;
import synapticloop.templar.token.IfToken;
import synapticloop.templar.token.ImportToken;
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

public class Tokeniser implements Serializable {
	private static final long serialVersionUID = 4768014602151991781L;

	private TokeniserInfo tokeniserInfo = new TokeniserInfo();

	private static final int BEGIN_PARENTHESES = 0;
	private static final int DOUBLE_DASH = 1;
	private static final int NL = 2;
	private static final int ESCAPED_N = 3;
	private static final int TAB = 4;
	private static final int ESCAPED_T = 5;
	private static final int SET = 6;
	private static final int IF = 7;
	private static final int LOOP = 8;
	private static final int DUMPCONTEXT = 9;
	private static final int DUMPFUNCTIONS = 10;
	private static final int ELSE = 11;
	private static final int ENDIF = 12;
	private static final int ENDLOOP = 13;
	private static final int IMPORT = 14;

	private static final Map<String, Integer> TOKEN_MAP = new HashMap<String, Integer>();
	static {
		TOKEN_MAP.put("{", BEGIN_PARENTHESES);
		TOKEN_MAP.put("--", DOUBLE_DASH);
		TOKEN_MAP.put("nl", NL);
		TOKEN_MAP.put("\\n", ESCAPED_N);
		TOKEN_MAP.put("tab", TAB);
		TOKEN_MAP.put("\\t", ESCAPED_T);
		TOKEN_MAP.put("set", SET);
		TOKEN_MAP.put("if", IF);
		TOKEN_MAP.put("loop", LOOP);
		TOKEN_MAP.put("dumpcontext", DUMPCONTEXT);
		TOKEN_MAP.put("dumpfunctions", DUMPFUNCTIONS);
		TOKEN_MAP.put("else", ELSE);
		TOKEN_MAP.put("endif", ENDIF);
		TOKEN_MAP.put("endloop", ENDLOOP);
		TOKEN_MAP.put("import", IMPORT);
	}


	public List<Token> tokenise(String contents) throws ParseException {
		List<Token> tokens = new ArrayList<Token>();
		StringTokenizer stringTokenizer = new StringTokenizer(contents, " \n\t{}", true);
		tokens.addAll(tokenise(stringTokenizer));
		return(tokens);
	}

	public List<Token> tokenise(StringTokenizer stringTokenizer) throws ParseException {
		List<Token> tokens = new ArrayList<Token>();

		while(stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			tokeniserInfo.incrementCharacter(token.length());

			if("{".equals(token)) {
				if(stringTokenizer.hasMoreTokens()) {
					token = stringTokenizer.nextToken();
					if(!TOKEN_MAP.containsKey(token)) {
						tokens.add(new EvaluationToken(token, stringTokenizer, this));
						
					} else {
						// good to go
						switch (TOKEN_MAP.get(token)) {
						case BEGIN_PARENTHESES:
							// looks like we actually want to have a '{' character
							tokens.add(new TextToken(token, stringTokenizer, this));
							break;
						case DOUBLE_DASH:
							tokens.add(new CommentToken(token, stringTokenizer, this));
							break;
						case NL:
						case ESCAPED_N:
							tokens.add(new NewLineToken(token, stringTokenizer, this));
							break;
						case TAB:
						case ESCAPED_T:
							tokens.add(new TabToken(token, stringTokenizer, this));
							break;
						case SET:
							tokens.add(new SetToken(token, stringTokenizer, this));
							break;
						case IF:
							tokens.add(new IfToken(token, stringTokenizer, this));
							break;
						case LOOP:
							tokens.add(new LoopToken(token, stringTokenizer, this));
							break;
						case DUMPCONTEXT:
							tokens.add(new DumpContextToken("", stringTokenizer, this));
							break;
						case DUMPFUNCTIONS:
							tokens.add(new DumpFunctionsToken("", stringTokenizer, this));
							break;
						case ELSE:
							tokens.add(new ElseToken("", stringTokenizer, this));
							return(tokens);
						case ENDIF:
							tokens.add(new EndIfToken("", stringTokenizer, this));
							return(tokens);
						case ENDLOOP:
							tokens.add(new EndLoopToken("", stringTokenizer, this));
							return(tokens);
						case IMPORT:
							// this is a little bit special in that we want to include all of 
							// the generated tokens, rather than just the single token and leave
							// it to render time to parse
							ImportToken importToken = new ImportToken(token, stringTokenizer, this);
							tokens.add(importToken);
							tokens.addAll(importToken.getTokens());
							break;
						default:
							tokens.add(new EvaluationToken(token, stringTokenizer, this));
							break;
						}
					}
				} else {
					throw new ParseException("Found an '{' character, with no more tokens after it");
				}
			} else {
				if(token.equals("\n")) {
					tokeniserInfo.incrementLine();
				}
				tokens.add(new TextToken(token, stringTokenizer, this));
			}
		}

		return(tokens);
	}

	public List<ConditionalToken> tokeniseCommandLine(StringTokenizer stringTokenizer) throws ParseException {
		// at this point we want to evaluate the if statement
		ArrayList<ConditionalToken> conditionalTokens = new ArrayList<ConditionalToken>();

		while(stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			if("\n".equalsIgnoreCase(token)) {
				tokeniserInfo.incrementLine();
			}
			token = token.trim();

			if("".equals(token)) {
				continue;
			}

			if("(".equals(token)) {
				// start of grouping
				conditionalTokens.add(new GroupToken(token, stringTokenizer, this));
			} else if(")".equals(token)) {
				// end of grouping
				return(conditionalTokens);
			} else if("&".equals(token)) {
				conditionalTokens.add(new AndToken(token, stringTokenizer, this));
			} else if("!".equals(token)) {
				conditionalTokens.add(new NotToken(token, stringTokenizer, this));
			} else if("|".equals(token)) {
				conditionalTokens.add(new OrToken(token, stringTokenizer, this));
			} else if("fn:".equals(token)) {
				// have the start of a function
				conditionalTokens.add(new FunctionToken(token, stringTokenizer, this));
			} else {
				// this is an evaluator
				conditionalTokens.add(new ConditionalEvaluationToken(token, stringTokenizer, this));
			}
		}

		return(conditionalTokens);
	}

	public TokeniserInfo getTokeniserInfo() { return(tokeniserInfo); }
}
