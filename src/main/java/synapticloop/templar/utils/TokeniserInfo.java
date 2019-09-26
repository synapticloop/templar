package synapticloop.templar.utils;

/*
 * Copyright (c) 2012-2019 synapticloop.
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
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains information about the parsed file. Be aware that as they
 * are static, they will not be thread safe...
 * 
 * @author synapticloop
 *
 */
public class TokeniserInfo implements Serializable {
	private static final long serialVersionUID = 4881429648508997875L;

	public static final String NEW_LINE = System.getProperty("line.separator");
	private int lineNumber = 1;
	private int characterNumber = 1;
	private List<String> lines = new ArrayList<String>();
	private String filePath = null;

	public void incrementLine() {
		lineNumber++;
		characterNumber = 1;
	}

	public void reset() {
		lineNumber = 1;
		characterNumber = 1;
		lines = new ArrayList<String>();
	}

	public void restore(int lineNumber, int characterNumber, List<String> lines) {
		this.lineNumber = lineNumber;
		this.characterNumber = characterNumber;
		this.lines = lines;
		
	}

	public int getLineNumber() { return(lineNumber); }
	public int getCharacterNumber() { return(characterNumber); }
	public void incrementCharacter(int num) { characterNumber += num; }
	public void addLine(String line) { lines.add(line); }
	public String getLine(int index) { return(lines.get(index -1)); }
	public String getFilePath() { return this.filePath; }
	public void setFilePath(String filePath) { this.filePath = filePath; }

	public List<String> getLines() { return(this.lines); }
}
