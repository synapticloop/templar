package com.synapticloop.templar.token;

/*
 * Copyright (c) 2017-2025 synapticloop.
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
import java.util.StringTokenizer;

import com.synapticloop.templar.exception.ParseException;
import com.synapticloop.templar.exception.RenderException;
import com.synapticloop.templar.helper.FileHelper;
import com.synapticloop.templar.utils.TemplarContext;
import com.synapticloop.templar.utils.Tokeniser;


/**
 * The static token imports another file and just adds the output to the 
 * rendering
 */
public class StaticToken extends Token {
	private static final long serialVersionUID = 3320178201395093697L;

	private static final String CLASSPATH_DESIGNATOR = "classpath:";

	private StringBuilder staticContents = new StringBuilder();
	private String importLocation = null;

	public StaticToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		StringBuilder stringBuilder = new StringBuilder();

		if(stringTokenizer.hasMoreTokens()) {
			while(stringTokenizer.hasMoreTokens()) {
				String nextToken = stringTokenizer.nextToken();

				if("}".equals(nextToken)) {
					importLocation = stringBuilder.toString().trim();
					// now we need to get the contents of the file
					readContents();
					return;
				}
				stringBuilder.append(nextToken);
			}
		}

		// if we are here - we could not find the end '}' for the import
		throw new ParseException("Could not find end token marker '}' for the import token.", this);
	}

	private void readContents() throws ParseException {

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
					staticContents.append(line + "\n");
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
							staticContents.append(line + "\n");
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
	}

	@Override
	public String render(TemplarContext templarContext) throws RenderException {
		return(staticContents.toString());
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<STATIC");
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(importLocation);
		stringBuilder.append(") />");

		return(stringBuilder.toString());
	}
}
