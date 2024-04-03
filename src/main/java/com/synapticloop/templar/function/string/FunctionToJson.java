package com.synapticloop.templar.function.string;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.Function;
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionToJson extends Function {

	public FunctionToJson() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);

		if(argZero instanceof String) {
			String contents = ((String)argZero).trim();
			char charAt = contents.charAt(0);
			switch (charAt) {
			case '{':
				try {
					return(new JSONObject(contents));
				} catch(JSONException ex) {
					// fall through
				}
			case '[':
				try {
					return(new JSONArray(contents));
				} catch(JSONException ex) {
					// fall through
				}
			default:
				// fall through
			}
		}
		throw new FunctionException("Could not parse JSON, arguments were: " + args[0] + ", values: " + argZero);
	}
}
