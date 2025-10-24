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

import java.util.StringTokenizer;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;


public class EndImportToken extends Token {
	private static final long serialVersionUID = 3292020620692967485L;

	public EndImportToken(StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super("", stringTokenizer, tokeniser);
	}

	@Override
	public String render(TemplarContext templarContext) {
		return("");
	}

	@Override
	public String toString() {
		return(super.toString("/IMPORT"));
	}
}
