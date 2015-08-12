package synapticloop.templar.token.conditional;

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

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class FunctionToken extends ConditionalToken {
	private static final long serialVersionUID = 3323534849063017159L;

	private Object[] objectArgs = null;
	private String functionName = null;

	public FunctionToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);

		// now we want to go through and get the function
		// yay - we have a function - get the functionName
		// the value should look like: fn:somethingelse[3,4]

		int startArgs = value.indexOf("[");
		if(startArgs == -1) {
			throw new ParseException("Could not find function argument start token of '[' for function '" + value + "'.", this);
		}
		int endArgs = value.indexOf("]");
		if(endArgs == -1) {
			throw new ParseException("Could not find function argument end token of ']' for function '" + value + "'.", this);
		}

		// get the arguments as strings, then convert them into objects
		functionName = value.substring(3, startArgs);
		String[] args = value.substring(startArgs +1, endArgs).split(",");
		if(null == args || args.length == 0) {
			throw new ParseException("Could not parse null or empty arguments for function '" + value + "'.", this);
		}

		objectArgs = new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			String string = args[i];

			if(null != string) {
				string = string.trim();
			}

			objectArgs[i] = new ConditionalEvaluationToken(string, null, tokeniser);
		}
	}

	public Object evaluate(TemplarContext templarContext) throws RenderException {
		// go through and evaluate the parameters
		return(null);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<FN:");
		stringBuilder.append(functionName);
		stringBuilder.append("@");
		stringBuilder.append(lineNumber);
		stringBuilder.append(":");
		stringBuilder.append(characterNumber);
		stringBuilder.append(" [");

		for (int i = 0; i < objectArgs.length; i++) {
			Object object = objectArgs[i];
			stringBuilder.append(object.toString());
			if(i != (objectArgs.length - 1)) {
				stringBuilder.append(", ");
			}
		}

		stringBuilder.append("]/>");
		return(stringBuilder.toString());
	}
}
