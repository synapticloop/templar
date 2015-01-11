package synapticloop.templar.utils;

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

import java.util.ArrayList;

/**
 * This class contains information about the parsed file. Be aware that as they
 * are static, they will not be thread safe...
 * 
 * @author synapticloop
 *
 */
public class TokeniserInfo {
	public static final String NEW_LINE = System.getProperty("line.separator");
	public int lineNumber = 1;
	public int characterNumber = 1;
	public ArrayList<String> lines = new ArrayList<String>();

	public void incrementLine() {
		lineNumber++;
		characterNumber = 1;
	}

	public void incrementCharacter(int num) {
		characterNumber += num;
	}

	public void addLine(String line) {
		lines.add(line);
	}

	public String getLine(int index) {
		return(lines.get(index -1));
	}

	public void reset() {
		lineNumber = 1;
		characterNumber = 1;
		lines = new ArrayList<String>();
	}

	public int getLineNumber() {
		return(lineNumber);
	}

	public int getCharacterNumber() {
		return(characterNumber);
	}
}
