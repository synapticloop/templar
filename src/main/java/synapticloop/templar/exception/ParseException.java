package synapticloop.templar.exception;

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

import synapticloop.templar.token.BasePositionToken;
import synapticloop.templar.token.ParseExceptionToken;

/**
 * When there is a problem parsing the templar template.  This can also contain
 * the token that caused the exception.  If the constructor is used without an
 * exception token, then a generic ParseExceptionToken is generated, which
 * is used when parsing hasn't actually started, but there was an error.  For 
 * example, when a templar file was passed in but it does not exist.
 */
public class ParseException extends Exception {
	private static final long serialVersionUID = 2330577994861842050L;
	private BasePositionToken exceptionToken = null;

	public ParseException(String message) {
		super(message);
	}

	public ParseException(String message, BasePositionToken exceptionToken) {
		super(message);
		this.exceptionToken = exceptionToken;
	}

	public BasePositionToken getExceptionToken() {
		if(null == exceptionToken) {
			try {
				return(new ParseExceptionToken("FATAL", null));
			} catch (ParseException stepex) {
				return(null);
			}
		} else {
			return(exceptionToken);
		}
	}

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
