package synapticloop.templar.token;

/*
 * Copyright (c) 2012-2017 synapticloop.
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

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.helper.ParserHelper;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class NewLineToken extends Token {
	private static final long serialVersionUID = 7930180320542744794L;

	public NewLineToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		ParserHelper.parseToEndToken(this, stringTokenizer, "newline");
	}

	@Override
	public String render(TemplarContext templarContext) {
		return("\n");
	}

	@Override
	public String toString() {
		return(super.toString("NEWLINE"));
	}
}
