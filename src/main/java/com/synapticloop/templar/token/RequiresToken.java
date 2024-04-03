package com.synapticloop.templar.token;

/*
 * Copyright (c) 2012-2024 synapticloop.
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

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.helper.ParserHelper;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;

public class RequiresToken extends CommandToken {
	private static final long serialVersionUID = -5395690577077526893L;

	private String commandLine = null;

	public RequiresToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		boolean foundEndToken = ParserHelper.consumeToEndToken(stringTokenizer, stringBuilder);

		if(!foundEndToken) {
			throw new ParseException("Unable to find the closing requires token '}'", this);
		}

		this.commandLine = stringBuilder.toString().trim();
		
		if(commandLine.length() == 0) {
			throw new ParseException("Cannot have a requires token without a variable to require");
		}
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		if(null == templarContext) {
			templarContext = new TemplarContext();
		}

		ObjectHelper.evaluateObject(commandLine, templarContext);

		return("");
	}

	@Override
	public String toString() {
		return(super.toString("REQUIRES", commandLine));
	}

}
