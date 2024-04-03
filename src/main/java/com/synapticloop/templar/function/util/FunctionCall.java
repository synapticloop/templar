package com.synapticloop.templar.function.util;

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

import com.synapticloop.templar.exception.FunctionException;
import com.synapticloop.templar.function.Function;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionCall extends Function {

	public FunctionCall() {
		super(1);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		String objectMethodCall = (String)args[0];

		if(null == objectMethodCall) {
			throw new FunctionException("Cannot function call with 'null' argument.");
		}
		// go through and see if we can find the function to call
		String[] split = objectMethodCall.split(".");
		if(split.length < 2) {
			throw new FunctionException("Cannot make function call on'" + objectMethodCall + "'");
		}
		return(null);
	}
}
