package synapticloop.templar.token;

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

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.utils.TemplarContext;

public class TextToken extends Token {

	public TextToken(String value, StringTokenizer stringTokenizer) throws ParseException {
		super(value, stringTokenizer);
	}

	public String render(TemplarContext templarContext) {
		if(value.equals("\n") && templarContext.getTemplarConfiguration().getExplicitNewLines()) {
			return("");
		}

		if(value.equals("\t") && templarContext.getTemplarConfiguration().getExplicitTabs()) {
			return("");
		}

		return(value);
	}
}
