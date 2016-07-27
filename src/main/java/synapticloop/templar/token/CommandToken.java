package synapticloop.templar.token;

/*
 * Copyright (c) 2012-2016 synapticloop.
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
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public abstract class CommandToken extends Token {
	private static final String EMPTY_STRING = "";
	private static final long serialVersionUID = -1347463380199454468L;
	protected String commandLine = null;
	protected StringTokenizer stringTokenizer;

	public CommandToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		this.stringTokenizer = stringTokenizer;
	}

	@Override
	public abstract String render(TemplarContext templarContext) throws RenderException;

	/**
	 * Return whether this token conforms to be a correct token that has an 'as'
	 * in it
	 *   e.g.: 
	 *     {loop something as somethingElse}, or 
	 *     {set something as somethingElse}
	 * 
	 * @param commandLine The command line to parse
	 * 
	 * @return whether this is a correct 'as' conforming token
	 */
	public boolean isCorrectAsStatement(String commandLine) {
		if(null == commandLine || EMPTY_STRING.equals(commandLine.trim())) {
			return(false);
		}

		String[] split = commandLine.split(" as ");
		if(null != split && split.length == 2) {
			// make sure that both are not empty
			if(EMPTY_STRING.equals(split[0].trim()) || EMPTY_STRING.equals(split[1].trim())) {
				return(false);
			}

			return(true);
		}

		return(false);
	}
}
