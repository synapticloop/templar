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

import synapticloop.templar.exception.FunctionException;
import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.ObjectUtils;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.Tokeniser;

public class ConditionalEvaluationToken extends ConditionalToken {
	private static final long serialVersionUID = 3804189320649027359L;

	public ConditionalEvaluationToken(String value, StringTokenizer stringTokenizer, Tokeniser tokeniser) throws ParseException {
		super(value, stringTokenizer, tokeniser);
		value = value.trim();
	}

	public Object evaluate(TemplarContext templarContext) throws RenderException {
		// see whether the value exists in the context.
		Object object = null;
		if(value.startsWith("fn:")) {
			// yay - we have a function - get the functionName
			int startArgs = value.indexOf("[");
			if(startArgs == -1) {
				throw new RenderException("Could not find function argument start token of '[' for function '" + value + "'.");
			}
			int endArgs = value.indexOf("]");
			if(endArgs == -1) {
				throw new RenderException("Could not find function argument end token of ']' for function '" + value + "'.");
			}

			// get the arguments as strings, then convert them into objects
			String functionName = value.substring(3, startArgs);
			String[] args = value.substring(startArgs +1, endArgs).split(",");
			if(null == args || args.length == 0) {
				throw new RenderException("Could not parse arguments for function '" + value + "'.");
			}

			Object[] objectArgs = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				String string = args[i];

				if(null != string) {
					string = string.trim();
				}
				// at this point - evaluate the token
				//				objectArgs[i] = Utils.evaluateObject(string, templarContext);
				objectArgs[i] = string;
			}

			try {
				object = templarContext.invokeFunction(functionName, objectArgs, templarContext);
				return(object);
			} catch (FunctionException fex) {
				throw new RenderException("Command " + value + "' exception, " + fex.getMessage(), fex);
			}
		}

		if(value.contains(".")) {
			// means that we want to do a function of a function
			object = ObjectUtils.evaluateObject(value, templarContext);
		} else {
			// just to a lookup
			object = templarContext.get(value);
			// can't find the object in the context - probably (hopefully) they want
			// the value
			if(null == object) {
				object = value;
			}
		}
		return(object);
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if(!"".equals(value)) {
			stringBuilder.append("<EVAL");
			stringBuilder.append("@");
			stringBuilder.append(lineNumber);
			stringBuilder.append(":");
			stringBuilder.append(characterNumber);
			stringBuilder.append(" (");
			stringBuilder.append(value);
			stringBuilder.append(")/>");
		}
		return(stringBuilder.toString());
	}
}
