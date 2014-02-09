package synapticloop.templar.utils;

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

import java.util.Vector;

/**
 * This class contains information about the parsed file. Be aware that as they
 * are static, they will not be thread safe...
 * 
 * @author synapticloop
 *
 */
public class TokeniserInfo {
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static int lineNumber = 1;
	public static int characterNumber = 1;
	public static Vector<String> lines = new Vector<String>();

	public static void incrementLine() {
		lineNumber++;
		characterNumber = 1;
	}

	public static void incrementCharacter(int num) {
		characterNumber += num;
	}

	public static void addLine(String line) {
		lines.add(line);
	}

	public static String getLine(int index) {
		return(lines.elementAt(index -1));
	}

	public static void reset() {
		lineNumber = 1;
		characterNumber = 1;
		lines = new Vector<String>();
	}

	public static int getLineNumber() {
		return(lineNumber);
	}

	public static int getCharacterNumber() {
		return(characterNumber);
	}
}
