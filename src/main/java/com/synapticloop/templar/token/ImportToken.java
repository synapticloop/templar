package com.synapticloop.templar.token;

/*
 * Copyright (c) 2012-2024 synapticloop.
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.helper.FileHelper;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;


/**
 * The import token imports another file and parses its tokens and appends it
 * to the current token list.
 */
public class ImportToken extends Token {
	private static final long serialVersionUID = 9019815748103868539L;

	private static final String CLASSPATH_DESIGNATOR = "classpath:";

	private static final Map<String, List<Token>> IMPORT_CACHE = new HashMap<String, List<Token>>();
	private static final Set<String> PRINT_CACHE = new HashSet<String>();

	String importLocation = null;

	public ImportToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String nextToken = stringTokenizer.nextToken();

				if("}".equals(nextToken)) {
					importLocation = stringBuilder.toString().trim();
					// now we need to get all of the sub tokens...
					if(!IMPORT_CACHE.containsKey(importLocation)) {
						IMPORT_CACHE.put(importLocation, null);
						// now we need to actually parse the tokens
						this.childTokens = getChildTokens();
						IMPORT_CACHE.put(importLocation, childTokens);
					}
					return;
				}
				stringBuilder.append(nextToken);
			}
		}

		// if we are here - we could not find the end '}' for the import
		throw new ParseException("Could not find end token marker '}' for the import token.", this);
	}

	private List<Token> getChildTokens() throws ParseException {

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
		File templarFile  = new File(importLocation);
		if(importLocation.startsWith(CLASSPATH_DESIGNATOR) || !FileHelper.canReadFile(templarFile)) {
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
			} catch(IOException jiioex) {
				throw new ParseException("IO Exception reading file '" + templarFile.getPath() + "'", jiioex);
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
			if(importLocation.startsWith(CLASSPATH_DESIGNATOR)) {
				String classpathImportLocation = importLocation.substring(CLASSPATH_DESIGNATOR.length());
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
			throw new ParseException("IO Exception reading file from '" + importLocation + "'");
		}

		// load the contents
		List<Token> tokens = new ArrayList<Token>();
		StringTokenizer stringTokenizer = new StringTokenizer(stringBuilder.toString(), " \n\t{}", true);
		try {
			// At this point we will be running into recursive problems...
			tokens.addAll(tokeniser.tokenise(stringTokenizer));
		} catch (ParseException pex) {
			throw new ParseException("Could not parse the imported file '" + importLocation + "', message was '" + pex.getMessage() + "'.");
		}

		// now reset the content
		tokeniser.getTokeniserInfo().restore(lineNumber, characterNumber, lines);

		// finally add the end import token
		tokens.add(new EndImportToken(stringTokenizer, tokeniser));

		return(tokens);
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		List<Token> tokenList = IMPORT_CACHE.get(importLocation);
		StringBuilder stringBuilder = new StringBuilder();
		for (Token token : tokenList) {
			String render = token.render(templarContext);
			stringBuilder.append(render);
		}
		return(stringBuilder.toString());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<IMPORT");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		boolean inPrintCache = PRINT_CACHE.contains(importLocation);
		if(inPrintCache) {
			stringBuilder.append(" *CACHED*");
		}
		stringBuilder.append(" (");
		stringBuilder.append(importLocation);

		stringBuilder.append(")");

		if(inPrintCache) {
			stringBuilder.append(" /");
		}
		stringBuilder.append(">");

		if(PRINT_CACHE.isEmpty() || !inPrintCache) {
			// all is good - this is the first print
			PRINT_CACHE.add(importLocation);
			for (Token childToken : childTokens) {
				stringBuilder.append(childToken.toString());
			}
		}

		PRINT_CACHE.clear();
		return(stringBuilder.toString());
	}
}
