package synapticloop.templar.token;

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

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.StringUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public abstract class Token extends BasePositionToken {
	private static final long serialVersionUID = -7987699180706299996L;

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

	public String render(TemplarContext templarContext) throws RenderException {
		return(value);
	}

	public void renderToStream(TemplarContext templarContext, OutputStream outputStream) throws RenderException {
		try {
			outputStream.write(render(templarContext).getBytes());
		} catch (IOException ioex) {
			throw new RenderException("Could not wite to the output stream, message was:" + ioex.getMessage(), ioex);
		}
	}

	public List<Token> getChildren() {
		return(childTokens);
	}

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

	public String toString(String name, String commandLine) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append(name);
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" (");
		stringBuilder.append(commandLine);
		stringBuilder.append(")/>");
		return (stringBuilder.toString());
		
	}

	public String toString(String name) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		stringBuilder.append(name);
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		if(name.startsWith("/")) {
			stringBuilder.append(">");
		} else {
			stringBuilder.append(" />");
		}

		return(stringBuilder.toString());
	}

	public String toHtmlString() {
		return(StringUtils.escapeHtml(value));
	}
}
