package synapticloop.templar.token;

/*
 * Copyright (c) 2012-2015 synapticloop.
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
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.ParserHelper;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class DumpContextToken extends CommandToken {
	private static final long serialVersionUID = -5395690577077526893L;

	public DumpContextToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		boolean foundEndToken = ParserHelper.didFindEndToken(stringTokenizer, stringBuilder);

		if(!foundEndToken) {
			throw new ParseException("Unable to find the closing dump context token '}'", this);
		}
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		if(null == templarContext) {
			templarContext = new TemplarContext();
		}

		// time to dump the templar context
		StringBuilder stringBuilder = new StringBuilder();

		// go through the context and grab all of the objects
		stringBuilder.append(templarContext.toString());

		return(stringBuilder.toString());
	}

	@Override
	public String toString() {
		return(super.toString("DUMPCONTEXT"));
	}

}
