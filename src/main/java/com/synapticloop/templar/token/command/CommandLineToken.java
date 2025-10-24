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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.utils.TemplarContext;

public abstract class CommandLineToken {
	public static final Map<String, Integer> TOKEN_LOOKUP = new HashMap<String, Integer>();
	public static final int EXCLAMATION_MARK = 0;
	public static final int DOUBLE_QUOTE = 1;
	public static final int SINGLE_QUOTE = 2;
	public static final int COLON = 3;
	public static final int FUNCTION = 4;
	public static final int FUNCTION_START = 5;
	public static final int FUNCTION_END = 6;
	static {
		TOKEN_LOOKUP.put("!", EXCLAMATION_MARK);
		TOKEN_LOOKUP.put("\"", DOUBLE_QUOTE);
		TOKEN_LOOKUP.put("'", SINGLE_QUOTE);
		TOKEN_LOOKUP.put(":", COLON);
		TOKEN_LOOKUP.put("fn", FUNCTION);
		TOKEN_LOOKUP.put("[", FUNCTION_START);
		TOKEN_LOOKUP.put("]", FUNCTION_END);
	}

	protected List<CommandLineToken> childTokens = new ArrayList<CommandLineToken>();
	protected String evaluateCommand = null;

	public CommandLineToken(StringTokenizer stringTokenizer) throws ParseException {}

	/**
	 * Evaluate the token 
	 * 
	 * @param templarContext The templar context for any lookups that are required
	 * 
	 * @return The evaluated object
	 * 
	 * @throws RenderException If there was an error evaluating the command line
	 */
	public abstract Object evaluate(TemplarContext templarContext) throws RenderException;

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		String className = this.getClass().getSimpleName();
		stringBuilder.append(className);
		stringBuilder.append("(");
		stringBuilder.append(evaluateCommand);
		stringBuilder.append(")");
		stringBuilder.append(">");
		for (CommandLineToken commandToken : childTokens) {
			stringBuilder.append(commandToken.toString());
		}
		stringBuilder.append("</");
		stringBuilder.append(className);
		stringBuilder.append(">");
		return(stringBuilder.toString());
	}
}
