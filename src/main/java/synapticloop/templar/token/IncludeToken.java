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
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.utils.FileUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;


public class IncludeToken extends Token {
	private static final long serialVersionUID = 5518293366922343901L;

	private static final String CLASSPATH_DESIGNATOR = "classpath:";
	String includeLocation = null;

	public IncludeToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String nextToken = stringTokenizer.nextToken();

				if("}".equals(nextToken)) {
					includeLocation = stringBuilder.toString().trim();
					return;
				}
				stringBuilder.append(nextToken);
			}
			// if we are here - we could not find the end '}' for the import
			throw new ParseException("Could not find end token marker '}' for the import.", this);
		} else {
			throw new ParseException("Could not find end token marker '}' for the import token.", this);
		}
	}

	public List<Token> getTokens() throws ParseException {
		// now we need to get the current contents
		// This is going to screw with the TokeniserInfo class - save the values

		int lineNumber = tokeniser.getTokeniserInfo().getLineNumber();
		int characterNumber = tokeniser.getTokeniserInfo().getCharacterNumber();
		List<String> lines = tokeniser.getTokeniserInfo().getLines();

		tokeniser.getTokeniserInfo().reset();

		StringBuilder stringBuilder = new StringBuilder();

		boolean foundFilePath = true;
		boolean foundClassPath = true;
		// try to find it on the filepath
		File templarFile  = new File(includeLocation);
		if(includeLocation.startsWith(CLASSPATH_DESIGNATOR) || !FileUtils.canReadFile(templarFile)) {
			foundFilePath = false;
		} else {
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new FileReader(templarFile));
				String line = null;
				while((line = bufferedReader.readLine()) != null) {
					tokeniser.getTokeniserInfo().addLine(line);
					stringBuilder.append(line + "\n");
				}
			} catch(IOException ioex) {
				throw new ParseException("IO Exception reading file '" + templarFile.getPath() + "'", ioex);
			} finally {
				if(null != bufferedReader) {
					try {
						bufferedReader.close();
					} catch (IOException ioex) {
						// do nothing
					}
					bufferedReader = null;
				}
			}
		}

		// try and find it on the classpath
		if(!foundFilePath) {
			// try and get the resource as a stream
			if(includeLocation.startsWith(CLASSPATH_DESIGNATOR)) {
				String classpathImportLocation = includeLocation.substring(CLASSPATH_DESIGNATOR.length());
				InputStream resourceAsStream = this.getClass().getResourceAsStream(classpathImportLocation);
				if(null != resourceAsStream) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
					String line = null;
					try {
						while((line = bufferedReader.readLine()) != null) {
							tokeniser.getTokeniserInfo().addLine(line);
							stringBuilder.append(line + "\n");
						}
					} catch(IOException jiioex) {
						foundClassPath = false;
					}
				} else {
					foundClassPath = false;
				}
			} else {
				foundClassPath = false;
			}
		}

		if(!foundClassPath && !foundFilePath) {
			throw new ParseException("IO Exception reading file from '" + includeLocation + "'");
		}

		// load the contents
		List<Token> tokens = new ArrayList<Token>();
		StringTokenizer stringTokenizer = new StringTokenizer(stringBuilder.toString(), " \n\t{}", true);
		try {
			tokens.addAll(tokeniser.tokenise(stringTokenizer));
		} catch (ParseException pex) {
			throw new ParseException("Could not parse the imported file '" + includeLocation + "', message was '" + pex.getMessage() + "'.");
		}

		// now reset the content
		tokeniser.getTokeniserInfo().restore(lineNumber, characterNumber, lines);

		// finally add the end import token
		tokens.add(new EndImportToken(includeLocation, stringTokenizer, tokeniser));
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
		stringBuilder.append(includeLocation);
		stringBuilder.append(")>");

		return(stringBuilder.toString());
	}
}
