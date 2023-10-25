package synapticloop.templar.exception;

/*
 * Copyright (c) 2012-2023 synapticloop.
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

import synapticloop.templar.token.BasePositionToken;
import synapticloop.templar.token.ParseExceptionToken;
import synapticloop.templar.utils.Tokeniser;

import java.io.Serial;

/**
 * When there is a problem parsing the templar template.  This can also contain
 * the token that caused the exception.  If the constructor is used without an
 * exception token, then a generic ParseExceptionToken is generated, which
 * is used when parsing hasn't actually started, but there was an error.  For 
 * example, when a templar file was passed in but it does not exist.
 */
public class ParseException extends Exception {
	@Serial private static final long serialVersionUID = 2330577994861842050L;
	private BasePositionToken exceptionToken = null;

	/**
	 * Create a parse exception
	 * 
	 * @param message the message
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * Create a parse exception
	 * 
	 * @param message the message
	 * @param throwable the root cause
	 */
	public ParseException(String message, Throwable throwable) {
		super(message, throwable);
	}

	/**
	 * Create a parse exception with a token in which the exception occurred
	 * 
	 * @param message the message
	 * @param exceptionToken the token with that the parsing errored on
	 */
	public ParseException(String message, BasePositionToken exceptionToken) {
		super(message);
		this.exceptionToken = exceptionToken;
	}

	/**
	 * Get the exception token that the parsing errored on, of a default empty
	 * token if one was not given.
	 * 
	 * @return the exception token or a default one
	 */
	public BasePositionToken getExceptionToken() {
		if(null == exceptionToken) {
			try {
				return(new ParseExceptionToken("FATAL", null, new Tokeniser()));
			} catch (ParseException stepex) {
				return(null);
			}
		} else {
			return(exceptionToken);
		}
	}

	@Override
	public String getMessage() {
		StringBuilder stringBuilder = new StringBuilder();
		if(null != exceptionToken) {
			stringBuilder.append("[Line: ");
			stringBuilder.append(exceptionToken.getLineNumber());
			stringBuilder.append(", Character: ");
			stringBuilder.append(exceptionToken.getCharacterNumber());
			stringBuilder.append("] ");
		}
		stringBuilder.append(super.getMessage());
		return(stringBuilder.toString());
	}
}
