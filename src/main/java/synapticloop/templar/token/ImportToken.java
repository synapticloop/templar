package synapticloop.templar.token;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.utils.FileUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;


public class ImportToken extends Token {
	private static final String CLASSPATH_DEIGNATOR = "classpath:";
	String importLocation = null;

	public ImportToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String nextToken = stringTokenizer.nextToken();

				if(nextToken.equals("}")) {
					importLocation = stringBuilder.toString().trim();
					return;
				}
				stringBuilder.append(nextToken);
			}
			// if we are here - we could not find the end '}' for the import
			throw new ParseException("Could not find end token marker '}' for the import.", this);
		} else {
			throw new ParseException("Could not find end token marker '}' for the tab token.", this);
		}
	}

	public ArrayList<Token> getTokens() throws ParseException {
		// now we need to get the current contents
		// This is going to screw with the TokeniserInfo class - save the values

		int lineNumber = tokeniser.getTokeniserInfo().lineNumber;
		int characterNumber = tokeniser.getTokeniserInfo().characterNumber;
		ArrayList<String> lines = tokeniser.getTokeniserInfo().lines;

		tokeniser.getTokeniserInfo().lineNumber = 1;
		tokeniser.getTokeniserInfo().characterNumber = 1;
		tokeniser.getTokeniserInfo().lines = new ArrayList<String>();

		StringBuilder stringBuilder = new StringBuilder();

		if(importLocation.startsWith(CLASSPATH_DEIGNATOR)) {
			// try and get the resource as a stream
			String classpathImportLocation = importLocation.substring(CLASSPATH_DEIGNATOR.length());
			InputStream resourceAsStream = this.getClass().getResourceAsStream(classpathImportLocation);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
			String line = null;
			try {
				while((line = bufferedReader.readLine()) != null) {
					tokeniser.getTokeniserInfo().addLine(line);
					stringBuilder.append(line + "\n");
				}
			} catch(IOException jiioex) {
				throw new ParseException("IO Exception reading file '" + classpathImportLocation + "'");
			}
		} else {
			File templarFile  = new File(importLocation);
			if(!FileUtils.canReadFile(templarFile)) {
				throw new ParseException("Could not find the imported file '" + importLocation + "'.");
			}
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(templarFile));
				String line = null;
				while((line = bufferedReader.readLine()) != null) {
					tokeniser.getTokeniserInfo().addLine(line);
					stringBuilder.append(line + "\n");
				}
			} catch(IOException jiioex) {
				throw new ParseException("IO Exception reading file '" + templarFile.getPath() + "'");
			}
		}

		// load the contents
		ArrayList<Token> tokens = new ArrayList<Token>();
		StringTokenizer stringTokenizer = new StringTokenizer(stringBuilder.toString(), " \n\t{}", true);
		try {
			tokens.addAll(tokeniser.tokenise(stringTokenizer));
		} catch (ParseException pex) {
			throw new ParseException("Could not parse the imported file '" + importLocation + "', message was '" + pex.getMessage() + "'.");
		}

		// now reset the content
		tokeniser.getTokeniserInfo().lineNumber = lineNumber;
		tokeniser.getTokeniserInfo().characterNumber = characterNumber;
		tokeniser.getTokeniserInfo().lines = lines;

		// finally add the end import token
		tokens.add(new EndImportToken(importLocation, stringTokenizer, tokeniser));
		return(tokens);
	}

	public String render(TemplarContext templarContext) {
		return("");
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<IMPORT");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(importLocation);
		stringBuilder.append(")>");

		return(stringBuilder.toString());
	}
}
