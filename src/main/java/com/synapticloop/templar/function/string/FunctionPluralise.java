package com.synapticloop.templar.function.string;

/*
 * Copyright (c) 2012-2025 synapticloop.
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
import com.synapticloop.templar.helper.ObjectHelper;
import com.synapticloop.templar.utils.TemplarContext;

public class FunctionPluralise extends Function {

	public FunctionPluralise() {
		super(3);
	}

	@Override
	protected Object evaluateFunction(Object[] args, TemplarContext templarContext) throws FunctionException {
		Object argZero = ObjectHelper.evaluateObjectToDefault(args[0], templarContext);
		Object argOne = ObjectHelper.evaluateObjectToDefault(args[1], templarContext);
		Object argTwo = ObjectHelper.evaluateObjectToDefault(args[2], templarContext);
		if(null == argZero || null == argOne || null == argTwo) {
			throw new FunctionException("Could not evaluate arguments to Integer, String, and String, arguments were: " +
					args[0] +
					", " +
					args[1] +
					", " +
					args[2] +
					", values: " +
					argZero +
					", " +
					argOne +
					", " +
					argTwo);
		}

		if(argZero instanceof Integer) {
			if((Integer)argZero != 1) {
				return(argOne);
			} else {
				return(argTwo);
			}
		}

		throw new FunctionException("The argument '" + argZero + "' could not be evaluated to an integer");
	}

}
