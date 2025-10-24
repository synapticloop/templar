package com.synapticloop.templar.token;

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

import java.util.List;
import java.util.StringTokenizer;

import com.synapticloop.templar.token.command.CommandLineToken;
import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.helper.CommandLineHelper;
import com.synapticloop.templar.helper.ParserHelper;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;

public class EvaluationToken extends CommandToken {
	private static final long serialVersionUID = -1330734609056279943L;
	private List<CommandLineToken> commandLineTokens;

	/**
	 * Build an evaluation token which should be in the form of full stop
	 * separated actions: e.g.: {to.be.evaluated}
	 *
	 * @param value the token value
	 * @param stringTokenizer the string tokenizer to read from 
	 * @param tokeniser the templar tokenizer
	 * 
	 * @throws ParseException if there was an error parsing the context
	 */
	public EvaluationToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(value);

		boolean foundEndToken = ParserHelper.consumeToEndToken(stringTokenizer, stringBuilder);

		if(!foundEndToken) {
			throw new ParseException("Could not find end token '}' for evaluation.", this);
		}
		this.commandLine = stringBuilder.toString();
		this.commandLineTokens = CommandLineHelper.parseCommandLine(commandLine);
	}


	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		StringBuilder stringBuilder = new StringBuilder();
		if(null != commandLineTokens) {
			for (CommandLineToken commandLineToken : commandLineTokens) {
				stringBuilder.append(commandLineToken.evaluate(templarContext));
			}
		}
		return (stringBuilder.toString());
	}

	@Override
	public String toString() {
		return(toString("EVAL", commandLine));
	}
}
