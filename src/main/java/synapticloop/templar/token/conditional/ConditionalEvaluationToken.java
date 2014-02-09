package synapticloop.templar.token.conditional;

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

import java.util.StringTokenizer;

import synapticloop.templar.exception.ParseException;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;
import synapticloop.templar.utils.ObjectUtils;

public class ConditionalEvaluationToken extends ConditionalToken {

	public ConditionalEvaluationToken(String value, StringTokenizer stringTokenizer) throws ParseException {
		super(value, stringTokenizer);
		value = value.trim();
	}

	public Object evaluate(TemplarContext templarContext) throws RenderException {
		// see whether the value exists in the context.
		Object object = null;
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
		if(!value.equals("")) {
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
