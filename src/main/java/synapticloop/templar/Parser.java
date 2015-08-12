package synapticloop.templar;

/*
 * Copyright (c) 2012-2015 synapticloop.
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.token.Token;
import synapticloop.templar.utils.FileUtils;
import synapticloop.templar.utils.HashUtils;
import synapticloop.templar.utils.ParserCache;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class Parser {
	private File templarFile;
	private Tokeniser tokeniser = new Tokeniser();

	private List<Token> tokens = new ArrayList<Token>();

	/**
	 * Create a parser from the String as raw templar contents
	 * 
	 * @param contents the raw templar markup
	 * 
	 * @throws ParseException if parsing goes wrong
	 */
	public Parser(String contents) throws ParseException {
		if(null != contents) {
			this.parse(contents);
		} else {
			throw new ParseException("File path or contents cannot be null");
		}
	}

	/**
	 * Create a parser from the passed in file
	 * 
	 * @param file the file to be parsed
	 * @throws ParseException if the file doesn't exist, cannot be read or is not a file
	 */
	public Parser(File file) throws ParseException {
		if(null != file) {
			if(FileUtils.canReadFile(file)) {
				this.templarFile = file;
			} else {
				throw new ParseException("File '" + file.getPath() + "' does not exist, can not be read, or is not a file.");
			}
		} else {
			throw new ParseException("File cannot be null");
		}
		this.parse();
	}

	/**
	 * 
	 * @param inputStream
	 * @throws ParseException
	 */
	public Parser(InputStream inputStream) throws ParseException {
		if(null == inputStream) {
			throw new ParseException("Input stream cannot be null");
		}

		StringBuilder stringBuilder = new StringBuilder();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedRead = new BufferedReader(inputStreamReader);

		String line = null;
		try {
			while((line = bufferedRead.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append("\n");
			}
		} catch (IOException ioex) {
			throw new ParseException("There was a problem reading the input stream.", ioex);
		}

		String contents = stringBuilder.toString();
		String md5Hash = HashUtils.md5Hash(contents);
		if(null != md5Hash && ParserCache.getIsInCache(md5Hash)) {
			this.tokens = ParserCache.getCached(md5Hash);
			return;
		}

		// at this point - we don't yet have the tokens
		this.parse(contents);
	}

	/**
	 * Parse the contents of the passed in string
	 *
	 * @param contents the contents to be parsed
	 *
	 * @throws ParseException if something goes horribly wrong
	 */
	private void parse(String contents) throws ParseException {
		if(null == contents) {
			throw new ParseException("Cannot parse null contents.");
		}

		// add all of the lines to the constants ArrayList
		StringBuilder stringBuilder = new StringBuilder(contents);
		StringTokenizer stringTokenizer = new StringTokenizer(contents, "\n\r\f");
		while(stringTokenizer.hasMoreTokens()) {
			tokeniser.getTokeniserInfo().addLine(stringTokenizer.nextToken());
		}

		// now actually do the tokenising - resetting the constants to start with
		tokeniser.getTokeniserInfo().reset();
		String string = stringBuilder.toString();

		tokens = tokeniser.tokenise(string);
	}

	private void parse() throws ParseException {
		StringBuilder stringBuilder = new StringBuilder();

		// go through the parsing
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
			}
		}

		// at this point we have all of the file lines, go through and tokenise
		// them

		tokeniser.getTokeniserInfo().reset();
		tokens = tokeniser.tokenise(stringBuilder.toString());
	}

	/**
	 * Render the content with the default context and configuration
	 *
	 * @return the rendered contents
	 *
	 * @throws RenderException If there was a problem rendering the content
	 */
	public String render() throws RenderException {
		TemplarContext templarContext = new TemplarContext();
		return(render(templarContext));
	}

	/**
	 * Render the template, using the templarContext to substitute the variables
	 *
	 * @param templarContext The templar context
	 * @return the rendered string
	 * @throws RenderException if there was a problem rendering the content
	 */
	public String render(TemplarContext templarContext) throws RenderException {
		if(null == templarContext) {
			templarContext = new TemplarContext();
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (Token token : tokens) {
			stringBuilder.append(token.render(templarContext));
		}
		return (stringBuilder.toString());
	}

	/**
	 * Render the template to the file system.  This will attempt to create the
	 * file.
	 *
	 * @param templarContext the templar context for variable substitution
	 * @param outputFile the file to output the content to
	 *
	 * @throws RenderException if there was a problem rendering the content
	 */
	public void renderToFile(TemplarContext templarContext, File outputFile) throws RenderException {
		new File(outputFile.getParentFile().getAbsolutePath()).mkdirs();

		BufferedWriter bufferedWriter = null;

		try {
			bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
			bufferedWriter.write(this.render(templarContext));
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException ioex) {
			throw new RenderException(ioex.getMessage(), ioex);
		} catch (RenderException sterex) {
			throw sterex;
		} finally {
			if(null != bufferedWriter) {
				try {
					bufferedWriter.close();
				} catch (IOException ioex) {
					bufferedWriter = null;
				}
			}
		}
	}

	public void renderToStream(TemplarContext templarContext, OutputStream outputStream) throws RenderException {
		if(null == templarContext) {
			templarContext = new TemplarContext();
		}

		for (Token token : tokens) {
			token.renderToStream(templarContext, outputStream);
		}
	}

	/**
	 * Write out the parsed tokens with line numbers and characters in a nice HTML
	 * format
	 * 
	 * @return the parsed tokens in a nice html format
	 */

	public String toHtmlString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Token token : tokens) {
			stringBuilder.append(token.toHtmlString());
		}
		return (stringBuilder.toString());
	}

	/**
	 * Write out the parsed tokens with line numbers and characters
	 * 
	 * @return the parsed tokens in string format
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Token token : tokens) {
			stringBuilder.append(token.toString());
		}
		return (stringBuilder.toString());
	}

	public Tokeniser getTokeniser() { return(tokeniser); }
}
