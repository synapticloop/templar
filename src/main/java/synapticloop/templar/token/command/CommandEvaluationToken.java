package synapticloop.templar.token.command;

/*
 * Copyright (c) 2012-2017 synapticloop.
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

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.helper.ObjectHelper;
import synapticloop.templar.utils.TemplarContext;

public class CommandEvaluationToken extends CommandLineToken {

	public CommandEvaluationToken(StringTokenizer stringTokenizer) throws ParseException {
		super(stringTokenizer);
		StringBuilder stringBuilder = new StringBuilder();
		while (stringTokenizer.hasMoreTokens()) {
			stringBuilder.append(stringTokenizer.nextToken());
		}
		this.evaluateCommand = stringBuilder.toString();
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<");
		String className = this.getClass().getSimpleName();
		stringBuilder.append(className);
		stringBuilder.append("(");
		stringBuilder.append(evaluateCommand);
		stringBuilder.append(")");
		stringBuilder.append(" />");
		return(stringBuilder.toString());
	}

	@Override
	public Object evaluate(TemplarContext templarContext) throws RenderException {
		// if the evaluated command is quoted, then just return the de-quoted stuff
		if(ObjectHelper.isQuoted(evaluateCommand)) {
			return(ObjectHelper.deQuote(evaluateCommand));
		}

		try {
			return(ObjectHelper.evaluateObject(evaluateCommand, templarContext));
		} catch(RenderException ex) {
			throw new RenderException("Could not render line: " + evaluateCommand, ex);
		}
	}
}
