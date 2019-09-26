package synapticloop.templar.token;

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

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.helper.StringHelper;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public abstract class Token extends BasePositionToken {
	private static final long serialVersionUID = -7987699180706299996L;

	private static final String FORMAT_SHORT = "<%s@%d:%d%s>";
	private static final String FORMAT_COMMAND_LINE = "<%s@%d:%d (%s) />";

	protected String value = "";
	protected List<Token> childTokens = new ArrayList<Token>();

	public Token(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		this.tokeniser = tokeniser;
		this.value = value;
		this.lineNumber = tokeniser.getTokeniserInfo().getLineNumber();
		this.characterNumber = tokeniser.getTokeniserInfo().getCharacterNumber();
		this.filePath = tokeniser.getTokeniserInfo().getFilePath();
		// do nothing with the string tokeniser - this may be used by the sub
		// classes
	}

	/**
	 * Render this token.  By default this just returns the value of the token, 
	 * and must be over-ridden in sub classes to output the correct information.
	 * 
	 * @param templarContext The templar context to use when rendering the token.
	 * 
	 * @return the rendered string
	 * 
	 * @throws RenderException if there was an error rendering the context
	 */
	public String render(TemplarContext templarContext) throws RenderException {
		return(value);
	}

	/**
	 * Render this token to an output stream.  By default this just returns the 
	 * value of the token, and must be over-ridden in sub classes to output the 
	 * correct information.
	 * 
	 * @param templarContext The templar context to use when rendering the output
	 * @param outputStream The output stream to write the content to
	 * 
	 * @throws RenderException If there was an error rendering the content
	 */
	public void renderToStream(TemplarContext templarContext, OutputStream outputStream) throws RenderException {
		try {
			outputStream.write(render(templarContext).getBytes());
		} catch (IOException ioex) {
			throw new RenderException("Could not wite to the output stream, message was:" + ioex.getMessage(), ioex);
		}
	}

	/**
	 * Get a list of all of the child tokens for this token
	 * 
	 * @return A list of all of the child tokens
	 */
	public List<Token> getChildren() {
		return(childTokens);
	}

	/**
	 * Add a child token to this token
	 * 
	 * @param token The token to add as a child
	 */
	public void addChildToken(Token token) {
		this.childTokens.add(token);
	}

	public void addChildTokens(List<Token> tokens) {
		this.childTokens.addAll(tokens);
	}

	@Override
	public String toString() {
		return(value);
	}

	/**
	 * Format the token output including a command line
	 * 
	 * @param name the name of the token
	 * @param commandLine the command line to output
	 * 
	 * @return The formatted string
	 */
	public String toString(String name, String commandLine) {
		return(String.format(FORMAT_COMMAND_LINE, name, lineNumber, characterNumber, commandLine));
	}

	public String toString(String name) {
		String ending = name.startsWith("/")? "" : " /";
		return(String.format(FORMAT_SHORT, name, lineNumber, characterNumber, ending));
	}

	public String toHtmlString() {
		return(StringHelper.escapeHtml(value));
	}
}
