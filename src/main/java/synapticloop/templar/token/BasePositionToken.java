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

import java.io.Serializable;

import synapticloop.templar.utils.Tokeniser;

/**
 * The BasePositionToken has methods to keep track of the line and character 
 * number for all of the tokens that are parsed.  This is generally used for 
 * error handling
 */
public abstract class BasePositionToken implements Serializable {
	private static final long serialVersionUID = -4285038550687054304L;

	protected int lineNumber = 0;
	protected int characterNumber = 0;
	protected Tokeniser tokeniser = null;
	protected String filePath = null;

	public int getLineNumber() { return(lineNumber); }

	public int getCharacterNumber() { return(characterNumber); }

	public String getFilePath() { return(filePath); }
}
